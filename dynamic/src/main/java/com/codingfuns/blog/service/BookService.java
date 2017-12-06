package com.codingfuns.blog.service;

import com.codingfuns.blog.controller.bean.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.codingfuns.blog.controller.BookController;
import com.codingfuns.blog.dao.BookDao;
import com.codingfuns.blog.dao.TagDao;
import com.codingfuns.blog.entity.Book;
import com.codingfuns.blog.entity.BookCondition;
import com.codingfuns.blog.entity.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.*;

@Service
public class BookService {
    private static Logger logger = LoggerFactory.getLogger(BookController.class);
    private static final String SEPARATOR = ",";
    private static final int MAX_WX_MSG_SIZE = 8;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TagDao tagDao;

    @Scheduled(fixedDelay = 1000 * 24)
    public void updateBook() {
        logger.info("[updateBook]start...");
        Tag oldestTag = tagDao.getOldest();
        if (oldestTag == null) {
            logger.info("[updateBook]no book!");
            return;
        }
        logger.info("[updateBook]oldestTag:" + oldestTag.getTagName());
        int newPageNum = oldestTag.getPageNum() + 1;
        DBSearchBookByTagResponse dbResponse = crawlAndSaveDoubanBooks(oldestTag.getTagName(), 100 * (newPageNum));
        updateOldestTag(oldestTag, dbResponse);
    }

    private void updateOldestTag(Tag tobeUpdatedTag, DBSearchBookByTagResponse dbResponse) {
        tobeUpdatedTag.setUpdateTime(new Date());
        if (dbResponse == null) {
            logger.info("[updateBook]crawl error,only update time:" + tobeUpdatedTag.getTagName());
            tagDao.updateByTagName(tobeUpdatedTag);
            return;
        }
        if (dbResponse.isTobeDeleteTagResp()) {
            logger.info("[updateBook]delete unImportant fuzzyTag:" + tobeUpdatedTag.getTagName() + ",dbResponse:" + dbResponse);
            tagDao.delete(tobeUpdatedTag);
            return;
        }
        int newPage = tobeUpdatedTag.getPageNum() + 1;
        if (dbResponse.isEndTagResp()) {
            newPage = -1;
            logger.info("[updateBook]all books for this fuzzyTag finish:" + tobeUpdatedTag.getTagName() + ",dbResponse:" + dbResponse);
        }
        tobeUpdatedTag.setPageNum(newPage);
        tagDao.updateByTagName(tobeUpdatedTag);
        logger.info("[updateBook]update:" + tobeUpdatedTag.getTagName());
        tagDao.deleteDuplicate(tobeUpdatedTag);
        logger.info("[updateBook]delete duplicate:" + tobeUpdatedTag.getTagName());
    }

    private void batchSave(List<DouBanBook> douBanBooks) {
        if (CollectionUtils.isEmpty(douBanBooks)) return;
        for (DouBanBook douBanBook : douBanBooks) {
            try {
                int doubanReaders = douBanBook.getRating().getNumRaters();
                int doubanScore = (int) (douBanBook.getRating().getAverage() * 10);
                Book book = new Book();
                book.setId(Integer.valueOf(douBanBook.getId()));
                book.setReadersNum(doubanReaders);
                book.setDoubanScore(doubanScore);
                book.setTitle(douBanBook.getTitle());
                book.setAuthor(StringUtils.join(douBanBook.getAuthor(), SEPARATOR));
                book.setTranslator(StringUtils.join(douBanBook.getTranslator(), SEPARATOR));
                String publisher = douBanBook.getPublisher();
                if (StringUtils.isNotEmpty(publisher) && publisher.length() > 100) {
                    publisher = publisher.substring(0, 99);
                }
                book.setPublisher(publisher);
                String imageId = StringUtils.substringBetween(douBanBook.getImage(), "/s", ".jpg");
                if (StringUtils.isNumeric(imageId)) {
                    book.setImageId(Integer.valueOf(imageId));
                }
                book.setIsbn13(douBanBook.getIsbn13());
                book.setPubDate(douBanBook.getPubdate());
                if (StringUtils.isNumeric(douBanBook.getPages())) {
                    book.setPages(Integer.valueOf(douBanBook.getPages()));
                }
                List<Tags> tags = Arrays.asList(douBanBook.getTags());
                tags.sort(Comparator.comparingInt(Tags::getCount).reversed());
                List<String> tagNameList = new LinkedList<>();
                for (Tags dbTag : tags) {
                    tagNameList.add(dbTag.getName() + "(" + dbTag.getCount() + ")");
                }
                String combinedTags = StringUtils.join(tagNameList, SEPARATOR);
                book.setCombinedTags(combinedTags);
                book.setUpdateTime(new Date());
                bookDao.save(book);
                tagDao.batchSave(tagNameList);
            } catch (Exception e) {
                logger.error("save book error:" + douBanBook, e);
            }
        }
    }

    private DBSearchBookByTagResponse crawlAndSaveDoubanBooks(String tagName, Integer start) {
        DBSearchBookByTagResponse dbResponse = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String url = "https://api.douban.com/v2/book/search?tag=" + URLEncoder.encode(tagName, "UTF-8") + "&count=100&start=" + start;
            Connection.Response res = Jsoup.connect(url).timeout(10 * 1000).ignoreHttpErrors(true).ignoreContentType(true).execute();
            if (res.statusCode() == 200) {
                dbResponse = objectMapper.readValue(res.body(), DBSearchBookByTagResponse.class);
                logger.info("doubanResponse:" + dbResponse);
                batchSave(dbResponse.getBooks());
            } else {
                logger.info("status code illegal:" + res.statusCode() + ",url:" + url + ",res body:" + res.body() + ",extra info:" + res.headers());
            }
        } catch (Exception e) {
            logger.error("crawl douban error", e);
        }
        return dbResponse;
    }

    public int getBookCount() {
        return bookDao.count();
    }

    public List<BookVo> getRandomBooks() {
        BookCondition randomCondition = new BookCondition();
        randomCondition.setOrderByRandom(true);
        randomCondition.setPageNum(1);
        randomCondition.setPageSize(25);
        randomCondition.setMinScore(80);
        List<Book> books = bookDao.pageQueryByCondition(randomCondition);
        return BookVo.create(books);
    }

    public List<BookVo> searchByTag(String fuzzyTag) {
        BookCondition tagCondition = new BookCondition();
        tagCondition.setFuzzyTag(fuzzyTag);
        tagCondition.setPageNum(1);
        tagCondition.setPageSize(50);
        tagCondition.setOrderByRandom(true);
        tagCondition.setMinScore(80);
        List<Book> books = bookDao.pageQueryByCondition(tagCondition);
        return BookVo.create(books);
    }

    public int getTagCount() {
        return tagDao.count();
    }

    public List<Article> queryByKeyWord(String keyword) {
        BookCondition titleCondition = new BookCondition();
        titleCondition.setOrderByReaderNumDesc(true);
        titleCondition.setTitle(keyword);
        titleCondition.setPageNum(1);
        titleCondition.setPageSize(MAX_WX_MSG_SIZE);
        List<Book> titleBooks = bookDao.pageQueryByCondition(titleCondition);
        if (titleBooks.size() != MAX_WX_MSG_SIZE) {
            BookCondition tagCondition = new BookCondition();
            tagCondition.setPageNum(1);
            tagCondition.setPageSize(MAX_WX_MSG_SIZE - titleBooks.size());
            tagCondition.setFuzzyTag(keyword);
            tagCondition.setOrderByReaderNumDesc(true);
            tagCondition.setMinScore(70);
            List<Book> tagBooks = bookDao.pageQueryByCondition(tagCondition);
            titleBooks.addAll(tagBooks);
        }
        return Article.create(titleBooks);
    }

    public Book find(int bookId) {
        return bookDao.find(bookId);
    }
}
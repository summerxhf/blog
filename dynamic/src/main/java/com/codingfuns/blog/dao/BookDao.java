package com.codingfuns.blog.dao;

import com.codingfuns.blog.entity.Book;
import com.github.pagehelper.PageHelper;
import com.codingfuns.blog.entity.BookCondition;
import com.codingfuns.blog.entity.BookExample;
import com.codingfuns.blog.mapper.BookMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BookDao {

    @Autowired
    private BookMapper bookMapper;

//    public List<Book> getRandomBooks(int number) {
//        BookExample example = new BookExample();
//        example.setOrderByClause("random()");
//        BookExample.Criteria criteria = example.createCriteria();
//        criteria.andDoubanScoreGreaterThan(80);
//        PageHelper.startPage(1, number, false);
//        return bookMapper.selectByExample(example);
//    }

    public void save(Book book) {
        Book bookInDB = bookMapper.selectByPrimaryKey(book.getId());
        if (bookInDB == null) {
            bookMapper.insert(book);
        } else {
            bookMapper.updateByPrimaryKey(book);
        }
    }

    public int count() {
        BookExample example = new BookExample();
        return (int) bookMapper.countByExample(example);
    }

//    public List<Book> searchByTag(String tag, int pageSize, int minScore) {
//        if (StringUtils.isEmpty(tag)) {
//            return new LinkedList<>();
//        }
//        BookExample example = new BookExample();
//        example.setOrderByClause("random()");
//        BookExample.Criteria criteria = example.createCriteria();
//        criteria.andDoubanScoreGreaterThanOrEqualTo(minScore);
//        criteria.andCombinedTagsLike("%" + tag + "%");
//        PageHelper.startPage(1, pageSize, false);
//        return bookMapper.selectByExample(example);
//    }

    public Book find(int bookId) {
        return bookMapper.selectByPrimaryKey(bookId);
    }

//    public List<Book> findByTitle(String title) {
//        BookExample example = new BookExample();
//        example.setOrderByClause("readers_num DESC");
//        BookExample.Criteria criteria = example.createCriteria();
//        criteria.andTitleEqualTo(title);
//        return bookMapper.selectByExample(example);
//    }

    public List<Book> pageQueryByCondition(BookCondition condition) {
        BookExample example = new BookExample();
        if (condition.isOrderByRandom()) {
            example.setOrderByClause("random()");
        }
        if (condition.isOrderByReaderNumDesc()) {
            example.setOrderByClause("readers_num DESC");
        }
        BookExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(condition.getFuzzyTag())) {
            criteria.andCombinedTagsLike("%" + condition.getFuzzyTag() + "%");
        }
        if (StringUtils.isNotEmpty(condition.getTitle())) {
            criteria.andTitleEqualTo(condition.getTitle());
        }
        if (condition.getMinScore() != 0) {
            criteria.andDoubanScoreGreaterThanOrEqualTo(condition.getMinScore());
        }
        if (condition.getPageNum() != 0 && condition.getPageSize() != 0) {
            PageHelper.startPage(condition.getPageNum(), condition.getPageSize(), false);
        }
        List<Book> books = bookMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(books)) {
            return new LinkedList<>();
        }
        return books;
    }
}

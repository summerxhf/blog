package com.codingfuns.blog.controller.bean;

import com.codingfuns.blog.entity.Book;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class BookVo implements Serializable {
    private static final long serialVersionUID = -5110927456746257834L;
    private int id;
    private String title;
    private int score;
    private int readerNum;
    private int imageId;
    private String translator;
    private int pages;
    private String author;
    private String publisher;
    private String pubDate;
    private String isbn13;
    private String tags;

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getReaderNum() {
        return readerNum;
    }

    public void setReaderNum(int readerNum) {
        this.readerNum = readerNum;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static List<BookVo> create(List<Book> books) {
        if (CollectionUtils.isEmpty(books)) {
            return new LinkedList<>();
        }
        List<BookVo> voList = new LinkedList<>();
        for (Book book : books) {
            voList.add(create(book));
        }
        return voList;
    }

    public static BookVo create(Book book) {
        BookVo vo = new BookVo();
        vo.setReaderNum(book.getReadersNum());
        vo.setTitle(book.getTitle());
        vo.setScore(book.getDoubanScore());
        vo.setTags(book.getCombinedTags());
        vo.setId(book.getId());
        vo.setImageId(book.getImageId() == null ? 0 : book.getImageId());
        vo.setTranslator(book.getTranslator());
        vo.setPages(book.getPages() == null ? 0 : book.getPages());
        vo.setIsbn13(book.getIsbn13());
        vo.setAuthor(book.getAuthor());
        vo.setPublisher(book.getPublisher());
        vo.setPubDate(book.getPubDate());
        return vo;
    }
}

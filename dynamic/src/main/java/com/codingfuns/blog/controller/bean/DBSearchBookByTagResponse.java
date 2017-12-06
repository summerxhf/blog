package com.codingfuns.blog.controller.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DBSearchBookByTagResponse implements Serializable {
    private static final long serialVersionUID = -5905992862420268567L;
    private int count;
    private int start;
    private int total;
    private List<DouBanBook> books = new LinkedList<>();

    public boolean isTobeDeleteTagResp() {
        return start == 0 && books.size() != 100;
    }

    public boolean isEndTagResp() {
        return start != 0 && books.size() != 100;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DouBanBook> getBooks() {
        return books;
    }

    public void setBooks(List<DouBanBook> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "DBSearchBookByTagResponse{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", books=" + books.size() +
                '}';
    }
}

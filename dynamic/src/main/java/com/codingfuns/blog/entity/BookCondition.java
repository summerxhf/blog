package com.codingfuns.blog.entity;

import java.io.Serializable;

public class BookCondition implements Serializable {
    private static final long serialVersionUID = -581983474374767103L;
    private String fuzzyTag;
    private String title;
    private int pageSize;
    private int pageNum;
    private boolean orderByRandom;
    private boolean orderByReaderNumDesc;
    private int minScore;

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public String getFuzzyTag() {
        return fuzzyTag;
    }

    public void setFuzzyTag(String fuzzyTag) {
        this.fuzzyTag = fuzzyTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isOrderByRandom() {
        return orderByRandom;
    }

    public void setOrderByRandom(boolean orderByRandom) {
        this.orderByRandom = orderByRandom;
    }

    public boolean isOrderByReaderNumDesc() {
        return orderByReaderNumDesc;
    }

    public void setOrderByReaderNumDesc(boolean orderByReaderNumDesc) {
        this.orderByReaderNumDesc = orderByReaderNumDesc;
    }
}

package com.codingfuns.blog.controller.bean;

import java.io.Serializable;

public class Series implements Serializable{
    private static final long serialVersionUID = 3040842875931152813L;
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
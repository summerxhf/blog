package com.codingfuns.blog.controller.bean;

import java.io.Serializable;

public class Tags implements Serializable {
    private static final long serialVersionUID = 2531870032847335570L;
    private String title;
    private int count;
    private String name;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "title='" + title + '\'' +
                ", count=" + count +
                ", name='" + name + '\'' +
                '}';
    }
}

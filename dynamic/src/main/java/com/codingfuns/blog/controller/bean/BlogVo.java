package com.codingfuns.blog.controller.bean;


public class BlogVo {
    private String id;
    private String create_date;
    private String update_date;
    private String title;
    private String title4url;
    private String content;
    private String ad;
    private String summary;
    private String prevTitle;
    private String prevTitle4url;
    private String nextTitle;
    private String nextTitle4url;

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle4url() {
        return title4url;
    }

    public void setTitle4url(String title4url) {
        this.title4url = title4url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getPrevTitle() {
        return prevTitle;
    }

    public void setPrevTitle(String prevTitle) {
        this.prevTitle = prevTitle;
    }

    public String getPrevTitle4url() {
        return prevTitle4url;
    }

    public void setPrevTitle4url(String prevTitle4url) {
        this.prevTitle4url = prevTitle4url;
    }

    public String getNextTitle() {
        return nextTitle;
    }

    public void setNextTitle(String nextTitle) {
        this.nextTitle = nextTitle;
    }

    public String getNextTitle4url() {
        return nextTitle4url;
    }

    public void setNextTitle4url(String nextTitle4url) {
        this.nextTitle4url = nextTitle4url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogVo blogVo = (BlogVo) o;

        return id.equals(blogVo.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

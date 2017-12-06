package com.codingfuns.blog.controller.bean;

import com.codingfuns.blog.entity.Blog;

public class RecommendBlogVo {
    public RecommendBlogVo() {
    }

    public RecommendBlogVo(BlogVo blogVo) {
        this.title = blogVo.getTitle();
        this.url = blogVo.getTitle4url();
        if (blogVo.getTitle() != null && blogVo.getTitle().length() > 50) {
            this.prettyTitle = blogVo.getTitle().substring(0, 50);
        } else {
            this.prettyTitle = blogVo.getTitle();
        }
    }

    public RecommendBlogVo(Blog blog) {
        this.title = blog.getTitle();
        this.url = blog.getTitle4url();
        if (blog.getTitle() != null && blog.getTitle().length() > 50) {
            this.prettyTitle = blog.getTitle().substring(0, 50);
        } else {
            this.prettyTitle = blog.getTitle();
        }
    }

    private String prettyTitle;
    private String title;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrettyTitle() {
        return prettyTitle;
    }

    public void setPrettyTitle(String prettyTitle) {
        this.prettyTitle = prettyTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecommendBlogVo that = (RecommendBlogVo) o;

        return url.equals(that.url);

    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}

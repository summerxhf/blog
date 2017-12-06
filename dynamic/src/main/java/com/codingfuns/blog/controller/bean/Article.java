package com.codingfuns.blog.controller.bean;

import com.codingfuns.blog.entity.Book;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Article {
    private static List<String> widePicUrls =
            Arrays.asList("https://cdn.cnbj1.fds.api.mi-img.com/xvideo-media/deb1c0a15f4d87de39047867e7b49ba8.jpg",
                    "https://cdn.cnbj1.fds.api.mi-img.com/xvideo-media/174ea3d91e58aaf35a6f597cfce60d12.jpg");
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "<item>\n" +
                "     <Title>\n" +
                "         <![CDATA[" + this.getTitle() + "]]>\n" +
                "     </Title>\n" +
                "     <PicUrl>\n" +
                "         <![CDATA[" + this.getPicUrl() + "]]>\n" +
                "     </PicUrl>\n" +
                "     <Url>\n" +
                "         <![CDATA[" + this.getUrl() + "]]>\n" +
                "     </Url>\n" +
                " </item>\n";
    }

    public static List<Article> create(List<Book> books) {
        Random random = new Random();
        List<Article> articles = new LinkedList<>();
        for (Book book : books) {
            Article article = new Article();
            String title = book.getTitle();
            if (book.getReadersNum() != -1) {
                title = book.getTitle() + "\n(" + book.getReadersNum() + "人读过" + ",评分:" + book.getDoubanScore()+")";
            }
            article.setTitle(title);
            article.setUrl("http://www.codingfuns.com/wxbook/" + book.getId());
            if (CollectionUtils.isEmpty(articles)) {
                article.setPicUrl(widePicUrls.get(random.nextInt(widePicUrls.size())));
            } else {
                article.setPicUrl("https://cdn.cnbj1.fds.api.mi-img.com/xvideo-media/9d1c4ee18c46ab7f7a7911cf7a08dd3f.jpg");
            }
            articles.add(article);
        }
        return articles;
    }
}

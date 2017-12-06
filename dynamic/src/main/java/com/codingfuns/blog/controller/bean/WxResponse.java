package com.codingfuns.blog.controller.bean;

import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class WxResponse implements Serializable {
    private static final long serialVersionUID = -6331456054529541174L;
    private String ToUserName;
    private String FromUserName;
    private List<Article> Articles = new LinkedList<>();

    private WxResponse() {
    }

    public WxResponse(WxRequest request) {
        this.ToUserName = request.getFromUserName();
        this.FromUserName = request.getToUserName();
    }

    public int getArticleCount() {
        return Articles.size();
    }

    public void addAllArticle(List<Article> articles) {
        this.Articles.addAll(articles);
    }

    @Override
    public String toString() {
        if (CollectionUtils.isEmpty(this.Articles)) {
            return "<xml>\n" +
                    "<ToUserName><![CDATA[" + this.ToUserName + "]]></ToUserName>\n" +
                    "<FromUserName><![CDATA["+this.FromUserName+"]]></FromUserName>\n" +
                    "<CreateTime>"+ System.currentTimeMillis() +"</CreateTime>\n" +
                    "<MsgType><![CDATA[text]]></MsgType>\n" +
                    "<Content><![CDATA[没有找到相关书籍,请尝试其他关键词!]]></Content>\n" +
                    "</xml>";
        }
        StringBuilder itemsStr = new StringBuilder();
        for (Article article : Articles) {
            itemsStr.append(article.toString());
        }
        return "<xml>\n" +
                "    <ToUserName>\n" +
                "        <![CDATA[" + this.ToUserName + "]]>\n" +
                "    </ToUserName>\n" +
                "    <FromUserName>\n" +
                "        <![CDATA[" + this.FromUserName + "]]>\n" +
                "    </FromUserName>\n" +
                "    <CreateTime>" + System.currentTimeMillis() + "</CreateTime>\n" +
                "    <MsgType>\n" +
                "        <![CDATA[news]]>\n" +
                "    </MsgType>\n" +
                "    <ArticleCount>" + this.Articles.size() + "</ArticleCount>\n" +
                "    <Articles>\n" + itemsStr.toString() +
                "    </Articles>\n" +
                "</xml>";
    }
}

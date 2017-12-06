package com.codingfuns.blog.controller.bean;

import java.io.Serializable;
import java.util.Arrays;

public class DouBanBook implements Serializable {
    private static final long serialVersionUID = 5158270645150217304L;
    private String origin_title;
    private String summary;
    private Series series;
    private String publisher;
    private String id;
    private String[] author;
    private String title;
    private String isbn10;
    private String alt;
    private String isbn13;
    private String alt_title;
    private Tags[] tags;
    private String ebook_url;
    private String pubdate;
    private String catalog;
    private String pages;
    private String author_intro;
    private String image;
    private String url;
    private String ebook_price;
    private String price;
    private String[] translator;
    private String subtitle;
    private Images images;
    private String binding;
    private Rating rating;

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public Tags[] getTags() {
        return tags;
    }

    public void setTags(Tags[] tags) {
        this.tags = tags;
    }

    public String getEbook_url() {
        return ebook_url;
    }

    public void setEbook_url(String ebook_url) {
        this.ebook_url = ebook_url;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEbook_price() {
        return ebook_price;
    }

    public void setEbook_price(String ebook_price) {
        this.ebook_price = ebook_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String[] getTranslator() {
        return translator;
    }

    public void setTranslator(String[] translator) {
        this.translator = translator;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "DouBanBook{" +
                "origin_title='" + origin_title + '\'' +
                ", summary='" + summary + '\'' +
                ", series=" + series +
                ", publisher='" + publisher + '\'' +
                ", id='" + id + '\'' +
                ", author=" + Arrays.toString(author) +
                ", title='" + title + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", alt='" + alt + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", alt_title='" + alt_title + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", ebook_url='" + ebook_url + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", catalog='" + catalog + '\'' +
                ", pages='" + pages + '\'' +
                ", author_intro='" + author_intro + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", ebook_price='" + ebook_price + '\'' +
                ", price='" + price + '\'' +
                ", translator=" + Arrays.toString(translator) +
                ", subtitle='" + subtitle + '\'' +
                ", images=" + images +
                ", binding='" + binding + '\'' +
                ", rating=" + rating +
                '}';
    }
}

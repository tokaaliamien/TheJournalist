package com.example.android.thejournalist.Models;

import java.io.Serializable;

import static com.example.android.thejournalist.Utilites.Helper.compare;


public class News implements Serializable {
    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String id;

    public News() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //TODO fix equals function
    @Override
    public boolean equals(Object obj) {
        News newsObject = (News) obj;
        if (!compare(this.getAuthor(), newsObject.getAuthor()))
            return false;
        else if (!compare(this.getTitle(), newsObject.getTitle()))
            return false;
        else if (!compare(this.getDescription(), newsObject.getDescription()))
            return false;
        else if (!compare(this.getUrlToImage(), newsObject.getUrlToImage()))
            return false;
        else if (!compare(this.getPublishedAt(), newsObject.getPublishedAt()))
            return false;
        else if (!compare(this.getUrl(), newsObject.getUrl()))
            return false;
        else if (!this.getSource().equals(newsObject.getSource()))
            return false;
        else
            return true;
    }


}

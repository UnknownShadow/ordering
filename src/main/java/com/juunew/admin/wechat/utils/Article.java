package com.juunew.admin.wechat.utils;

/**
 *图文消息实体类
 */
public class Article {

    private String title;
    private String description;
    private String picUrl;      //图片的路径地址
    private String url;         //要跳转的路径地址
    private String articleCount;    // 设置图文消息个数

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(String articleCount) {
        this.articleCount = articleCount;
    }
}

package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class RankEntity {

    private int id;
    private String rank;
    private String title;
    private String gift_url;
    private String gift_title;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGift_url() {
        return gift_url;
    }

    public void setGift_url(String gift_url) {
        this.gift_url = gift_url;
    }

    public String getGift_title() {
        return gift_title;
    }

    public void setGift_title(String gift_title) {
        this.gift_title = gift_title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

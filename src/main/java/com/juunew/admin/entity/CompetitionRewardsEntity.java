package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class CompetitionRewardsEntity {

    private int id;             // 主键ID
    private String title;           //物品中文
    private int count;           //物品数量
    private int get_type;        //领奖类型，1：线上发，2：手工发；
    private String create_at;       //
    private String gift_title;       //奖品标题
    private String gift_url;         //奖品图片
    private String rank;         //排名


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getGet_type() {
        return get_type;
    }

    public void setGet_type(int get_type) {
        this.get_type = get_type;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getGift_title() {
        return gift_title;
    }

    public void setGift_title(String gift_title) {
        this.gift_title = gift_title;
    }

    public String getGift_url() {
        return gift_url;
    }

    public void setGift_url(String gift_url) {
        this.gift_url = gift_url;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}

package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class ProductsEntity {

    private int id;
    private String title;
    private int diamond;
    private int pay_price;      //微信支付拿货价
    private String img_url;
    private String client_img_url;
    private String discount;        //折扣
    private String diamond_icon;        //钻石小图标
    private String room_card_price;        //钻石小图标
    private int original_price;        //原价

    private int show_diamond;        //商品钻石展示
    private double rebate_ratio;        //积分返利比例


    public double getRebate_ratio() {
        return rebate_ratio;
    }

    public void setRebate_ratio(double rebate_ratio) {
        this.rebate_ratio = rebate_ratio;
    }

    public int getShow_diamond() {
        return show_diamond;
    }

    public void setShow_diamond(int show_diamond) {
        this.show_diamond = show_diamond;
    }

    public int getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(int original_price) {
        this.original_price = original_price;
    }

    public String getRoom_card_price() {
        return room_card_price;
    }

    public void setRoom_card_price(String room_card_price) {
        this.room_card_price = room_card_price;
    }

    public String getDiamond_icon() {
        return diamond_icon;
    }

    public void setDiamond_icon(String diamond_icon) {
        this.diamond_icon = diamond_icon;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getClient_img_url() {
        return client_img_url;
    }

    public void setClient_img_url(String client_img_url) {
        this.client_img_url = client_img_url;
    }

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

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getPay_price() {
        return pay_price;
    }

    public void setPay_price(int pay_price) {
        this.pay_price = pay_price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}

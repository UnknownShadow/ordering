package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class OperationDailyEntity {

    private int id;
    private String daily_date;
    private int newly_added_num;
    private int activity_num;
    private int pay_num;        //当日付费人数
    private int pay_count;      //当日付费笔数
    private int pay_total;      //当日充值总金额
    private double pay_doubPrice;      //当日充值总金额(浮点数)
    private int three_days;
    private int five_days;      //连续5天活跃用户
    private int thirteen_count;
    private int fivek_count;
    private int fish_count;
    private int ios_new_size;
    private int android_new_size;
    private int ios_activity_size;
    private int android_activity_size;

    private int thirteen_club_count;    //上游俱乐部开房数量
    private int thirteen_other_count;   //上游散客开房数量
    private int mahjong_count;           //麻将开房次数
    private int mahjong_club_count;     //麻将俱乐部开房数量
    private int mahjong_other_count;       //麻将散客开房数量
    private int fivek_club_count;       //激K俱乐部开房数量
    private int fivek_other_count;      //激K散客开房数量

    public int getThirteen_club_count() {
        return thirteen_club_count;
    }

    public void setThirteen_club_count(int thirteen_club_count) {
        this.thirteen_club_count = thirteen_club_count;
    }

    public int getThirteen_other_count() {
        return thirteen_other_count;
    }

    public void setThirteen_other_count(int thirteen_other_count) {
        this.thirteen_other_count = thirteen_other_count;
    }

    public int getMahjong_count() {
        return mahjong_count;
    }

    public void setMahjong_count(int mahjong_count) {
        this.mahjong_count = mahjong_count;
    }

    public int getMahjong_club_count() {
        return mahjong_club_count;
    }

    public void setMahjong_club_count(int mahjong_club_count) {
        this.mahjong_club_count = mahjong_club_count;
    }

    public int getMahjong_other_count() {
        return mahjong_other_count;
    }

    public void setMahjong_other_count(int mahjong_other_count) {
        this.mahjong_other_count = mahjong_other_count;
    }

    public int getFivek_club_count() {
        return fivek_club_count;
    }

    public void setFivek_club_count(int fivek_club_count) {
        this.fivek_club_count = fivek_club_count;
    }

    public int getFivek_other_count() {
        return fivek_other_count;
    }

    public void setFivek_other_count(int fivek_other_count) {
        this.fivek_other_count = fivek_other_count;
    }

    public int getIos_new_size() {
        return ios_new_size;
    }

    public void setIos_new_size(int ios_new_size) {
        this.ios_new_size = ios_new_size;
    }

    public int getAndroid_new_size() {
        return android_new_size;
    }

    public void setAndroid_new_size(int android_new_size) {
        this.android_new_size = android_new_size;
    }

    public int getIos_activity_size() {
        return ios_activity_size;
    }

    public void setIos_activity_size(int ios_activity_size) {
        this.ios_activity_size = ios_activity_size;
    }

    public int getAndroid_activity_size() {
        return android_activity_size;
    }

    public void setAndroid_activity_size(int android_activity_size) {
        this.android_activity_size = android_activity_size;
    }

    public double getPay_doubPrice() {
        return pay_doubPrice;
    }

    public void setPay_doubPrice(double pay_doubPrice) {
        this.pay_doubPrice = pay_doubPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDaily_date() {
        return daily_date;
    }

    public void setDaily_date(String daily_date) {
        this.daily_date = daily_date;
    }

    public int getNewly_added_num() {
        return newly_added_num;
    }

    public void setNewly_added_num(int newly_added_num) {
        this.newly_added_num = newly_added_num;
    }

    public int getActivity_num() {
        return activity_num;
    }

    public void setActivity_num(int activity_num) {
        this.activity_num = activity_num;
    }

    public int getPay_num() {
        return pay_num;
    }

    public void setPay_num(int pay_num) {
        this.pay_num = pay_num;
    }

    public int getPay_count() {
        return pay_count;
    }

    public void setPay_count(int pay_count) {
        this.pay_count = pay_count;
    }

    public int getPay_total() {
        return pay_total;
    }

    public void setPay_total(int pay_total) {
        this.pay_total = pay_total;
    }

    public int getThree_days() {
        return three_days;
    }

    public void setThree_days(int three_days) {
        this.three_days = three_days;
    }

    public int getFive_days() {
        return five_days;
    }

    public void setFive_days(int five_days) {
        this.five_days = five_days;
    }

    public int getThirteen_count() {
        return thirteen_count;
    }

    public void setThirteen_count(int thirteen_count) {
        this.thirteen_count = thirteen_count;
    }

    public int getFivek_count() {
        return fivek_count;
    }

    public void setFivek_count(int fivek_count) {
        this.fivek_count = fivek_count;
    }

    public int getFish_count() {
        return fish_count;
    }

    public void setFish_count(int fish_count) {
        this.fish_count = fish_count;
    }
}

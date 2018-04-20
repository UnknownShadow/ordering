package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class Financial_DailyEntity {

    private int id;
    private int total_revenue;      //当日总营收
    private int apple_recharge;        //当日苹果充值数量
    private int wechat_recharge;        //当日微信充值数量
    private int platform_recharge;         //当日运营平台充值数量
    private int total_recharge;          //当日充值钻石总数量
    private int diamond_expend_total;         //当日钻石总消耗
    private int confidential_count;       //当日私房总次数
    private int diamond_flow_total;         //当日钻石流动总数
    private int thirteen_count;               //上游开房次数
    private int fivek_count;             //激k开房次数
    private int fish_count;                 //鱼虾蟹开房次数
    private String daily_date;             //日报时间
    private double wechatRecharge;             //
    private double platformRecharge;             //
    private double appleRecharge;             //
    private double totalRevenue;             // 浮点总营收


    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getWechatRecharge() {
        return wechatRecharge;
    }

    public void setWechatRecharge(double wechatRecharge) {
        this.wechatRecharge = wechatRecharge;
    }

    public double getPlatformRecharge() {
        return platformRecharge;
    }

    public void setPlatformRecharge(double platformRecharge) {
        this.platformRecharge = platformRecharge;
    }

    public double getAppleRecharge() {
        return appleRecharge;
    }

    public void setAppleRecharge(double appleRecharge) {
        this.appleRecharge = appleRecharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(int total_revenue) {
        this.total_revenue = total_revenue;
    }

    public int getApple_recharge() {
        return apple_recharge;
    }

    public void setApple_recharge(int apple_recharge) {
        this.apple_recharge = apple_recharge;
    }

    public int getWechat_recharge() {
        return wechat_recharge;
    }

    public void setWechat_recharge(int wechat_recharge) {
        this.wechat_recharge = wechat_recharge;
    }

    public int getPlatform_recharge() {
        return platform_recharge;
    }

    public void setPlatform_recharge(int platform_recharge) {
        this.platform_recharge = platform_recharge;
    }

    public int getTotal_recharge() {
        return total_recharge;
    }

    public void setTotal_recharge(int total_recharge) {
        this.total_recharge = total_recharge;
    }

    public int getDiamond_expend_total() {
        return diamond_expend_total;
    }

    public void setDiamond_expend_total(int diamond_expend_total) {
        this.diamond_expend_total = diamond_expend_total;
    }

    public int getConfidential_count() {
        return confidential_count;
    }

    public void setConfidential_count(int confidential_count) {
        this.confidential_count = confidential_count;
    }

    public int getDiamond_flow_total() {
        return diamond_flow_total;
    }

    public void setDiamond_flow_total(int diamond_flow_total) {
        this.diamond_flow_total = diamond_flow_total;
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

    public String getDaily_date() {
        return daily_date;
    }

    public void setDaily_date(String daily_date) {
        this.daily_date = daily_date;
    }
}

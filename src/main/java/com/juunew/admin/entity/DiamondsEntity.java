package com.juunew.admin.entity;

import java.util.Date;

/**
 * 2017/6/20.
 * 注释：diamonds表和orders表  实体类
 */
public class DiamondsEntity {

    private int id;
    private int user_id;
    private int old_val;          //旧的钻石数
    private int new_val;          //新的钻石数
    private int change_val;       //变动钻石数
    private int change_type;      //  1. 苹果充值 2. 微信充值  3. 平台充值
    private String extra;            //附加信息
    private String purpose;          //用途
    private String created_at;       //创建时间
    private String updated_at;
    private int price;
    private int room_consume;        //开房消耗
    private int treasure_consume;            //开宝箱消耗
    private int competition_consume;        //参赛消耗
    private int mystery_count;        //神秘宝箱开启次数
    private int mystery_consume;        //神秘宝箱钻石消耗
    private int count;        //次数
    private int consume;        //消耗
    private int thirteen_count;        //上游十三张报名次数
    private int diamond;
    private int pay_type;       //支付方式 ：1：苹果充值  2：微信充值  3：平台充值
    private int product_id;
    private String nickname;      //用户昵称
    private int recharge_diamond;      //充值的钻石数量
    private double doubPrice;      //充值钱数

    private String desc;      //充值描述


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getDoubPrice() {
        return doubPrice;
    }

    public void setDoubPrice(double doubPrice) {
        this.doubPrice = doubPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOld_val() {
        return old_val;
    }

    public void setOld_val(int old_val) {
        this.old_val = old_val;
    }

    public int getNew_val() {
        return new_val;
    }

    public void setNew_val(int new_val) {
        this.new_val = new_val;
    }

    public int getChange_val() {
        return change_val;
    }

    public void setChange_val(int change_val) {
        this.change_val = change_val;
    }

    public int getChange_type() {
        return change_type;
    }

    public void setChange_type(int change_type) {
        this.change_type = change_type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRoom_consume() {
        return room_consume;
    }

    public void setRoom_consume(int room_consume) {
        this.room_consume = room_consume;
    }

    public int getTreasure_consume() {
        return treasure_consume;
    }

    public void setTreasure_consume(int treasure_consume) {
        this.treasure_consume = treasure_consume;
    }

    public int getCompetition_consume() {
        return competition_consume;
    }

    public void setCompetition_consume(int competition_consume) {
        this.competition_consume = competition_consume;
    }

    public int getMystery_count() {
        return mystery_count;
    }

    public void setMystery_count(int mystery_count) {
        this.mystery_count = mystery_count;
    }

    public int getMystery_consume() {
        return mystery_consume;
    }

    public void setMystery_consume(int mystery_consume) {
        this.mystery_consume = mystery_consume;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public int getThirteen_count() {
        return thirteen_count;
    }

    public void setThirteen_count(int thirteen_count) {
        this.thirteen_count = thirteen_count;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRecharge_diamond() {
        return recharge_diamond;
    }

    public void setRecharge_diamond(int recharge_diamond) {
        this.recharge_diamond = recharge_diamond;
    }


}

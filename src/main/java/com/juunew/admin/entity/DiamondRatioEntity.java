package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class DiamondRatioEntity {

    private int id;
    private String grade;
    private String room_card;
    private String recharge_diamond;
    private String recharge_money;
    private int ratio;
    private int diamond;
    private int money;      //充值钱数、单位：分

    private int gift_diamond;


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getGift_diamond() {
        return gift_diamond;
    }

    public void setGift_diamond(int gift_diamond) {
        this.gift_diamond = gift_diamond;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRoom_card() {
        return room_card;
    }

    public void setRoom_card(String room_card) {
        this.room_card = room_card;
    }

    public String getRecharge_diamond() {
        return recharge_diamond;
    }

    public void setRecharge_diamond(String recharge_diamond) {
        this.recharge_diamond = recharge_diamond;
    }

    public String getRecharge_money() {
        return recharge_money;
    }

    public void setRecharge_money(String recharge_money) {
        this.recharge_money = recharge_money;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }
}

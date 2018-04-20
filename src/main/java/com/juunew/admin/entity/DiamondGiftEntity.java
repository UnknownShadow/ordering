package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class DiamondGiftEntity {

    private int id;
    private int user_id;
    private int diamond_ratio_id;
    private int recharge_diamond;
    private int give_diamond;
    private String ratio;


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

    public int getDiamond_ratio_id() {
        return diamond_ratio_id;
    }

    public void setDiamond_ratio_id(int diamond_ratio_id) {
        this.diamond_ratio_id = diamond_ratio_id;
    }

    public int getRecharge_diamond() {
        return recharge_diamond;
    }

    public void setRecharge_diamond(int recharge_diamond) {
        this.recharge_diamond = recharge_diamond;
    }

    public int getGive_diamond() {
        return give_diamond;
    }

    public void setGive_diamond(int give_diamond) {
        this.give_diamond = give_diamond;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}

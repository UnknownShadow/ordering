package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class Diamond_outputEntity {

    private int id;
    private String output_date;
    private int daily_output;
    private int sign_output;
    private int treasure_output;
    private int msg_count;
    private int msg_output;
    private int notice_id;
    private int treasure_count;
    private int sign_count;
    private int output_total;       //产出总量
    private int novice_boot;       //新手引导产出


    public int getNovice_boot() {
        return novice_boot;
    }

    public void setNovice_boot(int novice_boot) {
        this.novice_boot = novice_boot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDaily_output() {
        return daily_output;
    }

    public void setDaily_output(int daily_output) {
        this.daily_output = daily_output;
    }

    public int getSign_output() {
        return sign_output;
    }

    public void setSign_output(int sign_output) {
        this.sign_output = sign_output;
    }

    public int getTreasure_output() {
        return treasure_output;
    }

    public void setTreasure_output(int treasure_output) {
        this.treasure_output = treasure_output;
    }

    public int getMsg_count() {
        return msg_count;
    }

    public void setMsg_count(int msg_count) {
        this.msg_count = msg_count;
    }

    public int getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(int notice_id) {
        this.notice_id = notice_id;
    }

    public int getTreasure_count() {
        return treasure_count;
    }

    public void setTreasure_count(int treasure_count) {
        this.treasure_count = treasure_count;
    }

    public int getSign_count() {
        return sign_count;
    }

    public void setSign_count(int sign_count) {
        this.sign_count = sign_count;
    }

    public String getOutput_date() {
        return output_date;
    }

    public void setOutput_date(String output_date) {
        this.output_date = output_date;
    }

    public int getMsg_output() {
        return msg_output;
    }

    public void setMsg_output(int msg_output) {
        this.msg_output = msg_output;
    }

    public int getOutput_total() {
        return output_total;
    }

    public void setOutput_total(int output_total) {
        this.output_total = output_total;
    }
}

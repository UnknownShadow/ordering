package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class Diamond_consumeEntity {

    private int id;
    private String consume_date;
    private int consume_total;       //消耗总量
    private int room_consume;       //开房总消耗
    private int treasure_consume;       //开宝箱消耗
    private int competition_consume;        //参赛消耗
    private int mystery_treasures_count;        //神秘宝箱开启次数
    private int mystery_treasures_consume;      //神秘宝箱开启消耗
    private int gold_count;         //金宝箱开启次数
    private int gold_consume;       //金宝箱开启消耗
    private int silver_count;           //银宝箱开启次数
    private int silver_consume;     //银宝箱开启消耗
    private int thirteen_count;     //上游十三张报名次数
    private int fivek_count;
    private int fish_count;
    private int competition_count;     //三个游戏竞技报名次数的总和


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMystery_treasures_count() {
        return mystery_treasures_count;
    }

    public void setMystery_treasures_count(int mystery_treasures_count) {
        this.mystery_treasures_count = mystery_treasures_count;
    }

    public int getMystery_treasures_consume() {
        return mystery_treasures_consume;
    }

    public void setMystery_treasures_consume(int mystery_treasures_consume) {
        this.mystery_treasures_consume = mystery_treasures_consume;
    }

    public int getGold_count() {
        return gold_count;
    }

    public void setGold_count(int gold_count) {
        this.gold_count = gold_count;
    }

    public int getGold_consume() {
        return gold_consume;
    }

    public void setGold_consume(int gold_consume) {
        this.gold_consume = gold_consume;
    }

    public int getSilver_count() {
        return silver_count;
    }

    public void setSilver_count(int silver_count) {
        this.silver_count = silver_count;
    }

    public int getSilver_consume() {
        return silver_consume;
    }

    public void setSilver_consume(int silver_consume) {
        this.silver_consume = silver_consume;
    }

    public int getThirteen_count() {
        return thirteen_count;
    }

    public void setThirteen_count(int thirteen_count) {
        this.thirteen_count = thirteen_count;
    }

    public String getConsume_date() {
        return consume_date;
    }

    public void setConsume_date(String consume_date) {
        this.consume_date = consume_date;
    }

    public int getConsume_total() {
        return consume_total;
    }

    public void setConsume_total(int consume_total) {
        this.consume_total = consume_total;
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

    public int getCompetition_count() {
        return competition_count;
    }

    public void setCompetition_count(int competition_count) {
        this.competition_count = competition_count;
    }
}

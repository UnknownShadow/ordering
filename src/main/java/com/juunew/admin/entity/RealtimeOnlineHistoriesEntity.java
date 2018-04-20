package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class RealtimeOnlineHistoriesEntity {

    private int id;
    private int user_id;
    private String created_at;

//------------------------------------------------------

    private int total_club_games;   //今日开房次数 （只有代理有开房次数，玩家只有今日游戏次数）
    private int total_games;   //今日游戏次数 （玩家只有今日游戏次数，代理有可能是0，games表）
    private int often_play_games;   //今日常玩游戏
    private String nickname;
    private double recharge_money;      //充值总金额
    private int diamond;      //充值总金额
    private double integral;      //积分值（单位：元）
    private int user_status;      //身份




    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public double getRecharge_money() {
        return recharge_money;
    }

    public void setRecharge_money(double recharge_money) {
        this.recharge_money = recharge_money;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getOften_play_games() {
        return often_play_games;
    }

    public void setOften_play_games(int often_play_games) {
        this.often_play_games = often_play_games;
    }

    public int getTotal_club_games() {
        return total_club_games;
    }

    public void setTotal_club_games(int total_club_games) {
        this.total_club_games = total_club_games;
    }

    public int getTotal_games() {
        return total_games;
    }

    public void setTotal_games(int total_games) {
        this.total_games = total_games;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

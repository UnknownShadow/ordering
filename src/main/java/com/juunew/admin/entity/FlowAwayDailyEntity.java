package com.juunew.admin.entity;


public class FlowAwayDailyEntity {

   private int id;
   private int user_id;
   private String nickname;
   private int user_source;
   private int recharge_money;
   private int diamond;
   private int integral;     //总积分（单位：分）
   private int user_status;
   private String created_time;
   private String updated_time;
   private String flow_away_time;

   private double total_integral;   //总积分（单位：元）





    public double getTotal_integral() {
        return total_integral;
    }

    public void setTotal_integral(double total_integral) {
        this.total_integral = total_integral;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUser_source() {
        return user_source;
    }

    public void setUser_source(int user_source) {
        this.user_source = user_source;
    }

    public int getRecharge_money() {
        return recharge_money;
    }

    public void setRecharge_money(int recharge_money) {
        this.recharge_money = recharge_money;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getFlow_away_time() {
        return flow_away_time;
    }

    public void setFlow_away_time(String flow_away_time) {
        this.flow_away_time = flow_away_time;
    }
}

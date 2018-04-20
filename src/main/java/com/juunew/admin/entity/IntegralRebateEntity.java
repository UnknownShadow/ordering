package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class IntegralRebateEntity {

    //0：给上级代理返利获得积分，1：推广用户获得积分，2：马王之王 获得积分，
    // 3：俱乐部活动积分获得，6:代理特权活动打卡获得积分
    public final static int SourceRebate = 0;
    public final static int SourceSpread = 1;
    public final static int SourceMahjong = 2;
    //public final static int Source=3;
    public final static int SourceAgentPrivilege = 6;



    private int id;
    private int user_id;
    private String user_id_nickname;
    private int child_user_id;
    private String child_user_id_nickname;
    private int recharge_number;    //子代理充值钻石的数量
    private int rebate_number;  //积分返利数量（单位：分）
    private double rebate_num; //（单位：元）
    private int recharge_money; //子代理充值的钱数（单位：分）

    //积分来源  默认0：给上级代理返利获得积分，1：推广用户获得积分，3：马王之王 获得积分
    private int integral_source;

    private String created_time;
    private String updated_time;

    private int frozen_flag;    //冻结积分


    public int getRecharge_money() {
        return recharge_money;
    }

    public void setRecharge_money(int recharge_money) {
        this.recharge_money = recharge_money;
    }

    public double getRebate_num() {
        return rebate_num;
    }

    public void setRebate_num(double rebate_num) {
        this.rebate_num = rebate_num;
    }

    public int getIntegral_source() {
        return integral_source;
    }

    public void setIntegral_source(int integral_source) {
        this.integral_source = integral_source;
    }

    public int getFrozen_flag() {
        return frozen_flag;
    }

    public void setFrozen_flag(int frozen_flag) {
        this.frozen_flag = frozen_flag;
    }

    public String getUser_id_nickname() {
        return user_id_nickname;
    }

    public void setUser_id_nickname(String user_id_nickname) {
        this.user_id_nickname = user_id_nickname;
    }

    public String getChild_user_id_nickname() {
        return child_user_id_nickname;
    }

    public void setChild_user_id_nickname(String child_user_id_nickname) {
        this.child_user_id_nickname = child_user_id_nickname;
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

    public int getChild_user_id() {
        return child_user_id;
    }

    public void setChild_user_id(int child_user_id) {
        this.child_user_id = child_user_id;
    }

    public int getRecharge_number() {
        return recharge_number;
    }

    public void setRecharge_number(int recharge_number) {
        this.recharge_number = recharge_number;
    }

    public int getRebate_number() {
        return rebate_number;
    }

    public void setRebate_number(int rebate_number) {
        this.rebate_number = rebate_number;
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
}

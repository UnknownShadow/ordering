package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class WithdrawalsRecordEntity {

    private int id;
    private int user_id;
    private String wechat_number;           //提现的微信号
    private String phone;           //手机号
    private String order_number;               //提现单号
    private int withdrawal_limit;           //提现额度
    private int total_integral;           //现有总积分
    private int verification_status;        //审核状态，0：审核拒绝；1：审核通过；2：运营审核，3：财务审核，4：管理员审核；
    private int arrival;         //是否到账，1：已到账，0：默认
    private int withdrawals_cash;        //提现金额（单位：分）
    private Double cash;        //提现金额
    private String img_url;            //通过审核后，上传的转账截图
    private String created_time;
    private String updated_time;


    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public int getWithdrawals_cash() {
        return withdrawals_cash;
    }

    public void setWithdrawals_cash(int withdrawals_cash) {
        this.withdrawals_cash = withdrawals_cash;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public int getTotal_integral() {
        return total_integral;
    }

    public void setTotal_integral(int total_integral) {
        this.total_integral = total_integral;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat_number() {
        return wechat_number;
    }

    public void setWechat_number(String wechat_number) {
        this.wechat_number = wechat_number;
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

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public int getWithdrawal_limit() {
        return withdrawal_limit;
    }

    public void setWithdrawal_limit(int withdrawal_limit) {
        this.withdrawal_limit = withdrawal_limit;
    }

    public int getVerification_status() {
        return verification_status;
    }

    public void setVerification_status(int verification_status) {
        this.verification_status = verification_status;
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

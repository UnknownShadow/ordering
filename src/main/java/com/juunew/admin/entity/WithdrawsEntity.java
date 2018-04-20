package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class WithdrawsEntity {

    //审核状态，1：未处理 2：通过 3：拒绝
    public final static int Audit_None = 1;
    public final static int Audit_Pass = 2;
    public final static int Audit_Refuse = 3;

    //支付状态，1：成功 2：失败 0：未处理
    public final static int PayStatus_None = 0;
    public final static int PayStatus_Succ = 1;
    public final static int PayStatus_Fail = 2;

    private int id;
    private int user_id;
    private String nickname;
    private String user_status;
    private int score;      //提现积分   单位：分
    private int amount;     //提现金额  单位：分
    private int audit;      //审核状态，0：未处理 1：通过 2：拒绝
    private String openid;
    private String trade_no;
    private int pay_status;     //支付状态，1：成功 2：失败 0：未处理
    private String pay_desc;       //支付描述
    private Date created_at;
    private Date audit_time;     //打款时间
    private Date pay_time;       //打款时间
    private double cash;    //单位 元 保留小数位
    private int process_audit;    //默认2：运营审核，3：财务审核，4：管理审核，0：拒绝，1：通过；为0或1时改变audit状态
    private String created_time;    //方便前端显示
    private double scoreNum;    //单位：分




    public double getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(double scoreNum) {
        this.scoreNum = scoreNum;
    }

    public static int getAudit_Pass() {
        return Audit_Pass;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public int getProcess_audit() {
        return process_audit;
    }

    public void setProcess_audit(int process_audit) {
        this.process_audit = process_audit;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public static int getAudit_None() {
        return Audit_None;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_desc() {
        return pay_desc;
    }

    public void setPay_desc(String pay_desc) {
        this.pay_desc = pay_desc;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(Date audit_time) {
        this.audit_time = audit_time;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    /**
     * 正在审核中
     * @return
     */
    public boolean inReview() {
        return (this.getPay_status() <= WithdrawsEntity.PayStatus_None
                && this.getAudit() != WithdrawsEntity.Audit_Refuse );
    }
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class WithdrawsRecordResp {


    @ApiModelProperty(value = "提现积分主键ID")
    private int id;
    @ApiModelProperty(value = "提现积分")
    private double score;
    @ApiModelProperty(value = "提现金额")
    private double amount;
    @ApiModelProperty(value = "提现时间")
    private long createdTime;
    @ApiModelProperty(value = "提现状态描述")
    private String describe;


    @Override
    public String toString() {
        return "WithdrawsRecordResp{" +
                "id=" + id +
                ", score=" + score +
                ", amount=" + amount +
                ", createdTime=" + createdTime +
                ", describe='" + describe + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

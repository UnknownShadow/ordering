package com.juunew.admin.entity.api;


import com.juunew.admin.entity.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class RechargeDetailsResp {

    @ApiModelProperty(value = "ID")
    private int userId;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "充值时间")
    private String rechargeTime;
    @ApiModelProperty(value = "充值金额")
    private double rechargeNum;


    @Override
    public String toString() {
        return "RechargeDetailsResp{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", rechargeTime='" + rechargeTime + '\'' +
                ", rechargeNum=" + rechargeNum +
                '}';
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public double getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(double rechargeNum) {
        this.rechargeNum = rechargeNum;
    }
}

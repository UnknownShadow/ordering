package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralRecordsResp {

    @ApiModelProperty(value = "充值用户ID")
    private int userId;
    @ApiModelProperty(value = "充值用户昵称")
    private String nickname;
    @ApiModelProperty(value = "充值时间")
    private String createdTime;
    @ApiModelProperty(value = "充值金额")
    private double rechargeMoney;
    @ApiModelProperty(value = "入账积分")
    private double rebateIntegral;


    @Override
    public String toString() {
        return "IntegralRecordsResp{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", rechargeMoney=" + rechargeMoney +
                ", rebateIntegral=" + rebateIntegral +
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public double getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(double rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public double getRebateIntegral() {
        return rebateIntegral;
    }

    public void setRebateIntegral(double rebateIntegral) {
        this.rebateIntegral = rebateIntegral;
    }
}

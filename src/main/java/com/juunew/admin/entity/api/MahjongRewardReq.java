package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class MahjongRewardReq {

    @ApiModelProperty(value = "token：6c7e133d1d3ecf056a3f06a4cfd08fa9")
    private String token;
    @ApiModelProperty(value = "打卡用户UserId")
    private int userId;
    @ApiModelProperty(value = "打卡用户获得的积分值（单位：元）")
    private int integral;


    @Override
    public String toString() {
        return "MahjongRewardReq{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
                ", integral=" + integral +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}

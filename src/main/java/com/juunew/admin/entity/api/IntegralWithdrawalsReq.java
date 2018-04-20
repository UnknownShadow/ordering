package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralWithdrawalsReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "需要提现的积分")
    private String integral;


    @Override
    public String toString() {
        return "IntegralWithdrawalsReq{" +
                "token='" + token + '\'' +
                ", integral=" + integral +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}

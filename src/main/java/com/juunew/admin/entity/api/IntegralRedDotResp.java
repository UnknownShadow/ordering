package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralRedDotResp {

    @ApiModelProperty(value = "积分记录小红点")
    private List integral;
    @ApiModelProperty(value = "提现变动小红点")
    private List withdraws;
    @ApiModelProperty(value = "当前审核状态")
    private int type;


    @Override
    public String toString() {
        return "IntegralRedDotResp{" +
                "integral=" + integral +
                ", withdraws=" + withdraws +
                ", type=" + type +
                '}';
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List getIntegral() {
        return integral;
    }

    public void setIntegral(List integral) {
        this.integral = integral;
    }

    public List getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(List withdraws) {
        this.withdraws = withdraws;
    }
}

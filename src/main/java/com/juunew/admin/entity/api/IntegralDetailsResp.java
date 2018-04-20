package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralDetailsResp {

    @ApiModelProperty(value = "可用积分")
    private double totalIntegral;
    @ApiModelProperty(value = "审批完成的积分")
    private double withdrawalsIntegral;


    @Override
    public String toString() {
        return "IntegralDetailsResp{" +
                "totalIntegral=" + totalIntegral +
                ", withdrawalsIntegral=" + withdrawalsIntegral +
                '}';
    }


    public double getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(double totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public double getWithdrawalsIntegral() {
        return withdrawalsIntegral;
    }

    public void setWithdrawalsIntegral(double withdrawalsIntegral) {
        this.withdrawalsIntegral = withdrawalsIntegral;
    }
}

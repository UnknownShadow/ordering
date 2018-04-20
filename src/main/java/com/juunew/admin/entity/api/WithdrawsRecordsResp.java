package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class WithdrawsRecordsResp {

    @ApiModelProperty(value = "提现时间")
    private String createTime;
    @ApiModelProperty(value = "提现金额")
    private double withdrawalsMoney;
    @ApiModelProperty(value = "提现状态")
    private String desc;


    @Override
    public String toString() {
        return "WithdrawsRecordsResp{" +
                "createTime='" + createTime + '\'' +
                ", withdrawalsMoney=" + withdrawalsMoney +
                ", desc='" + desc + '\'' +
                '}';
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getWithdrawalsMoney() {
        return withdrawalsMoney;
    }

    public void setWithdrawalsMoney(double withdrawalsMoney) {
        this.withdrawalsMoney = withdrawalsMoney;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.juunew.admin.entity.api;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/25.
 */
public class ApplyProxyConditions {

    @ApiModelProperty(value = "充值金额")
    double money;
    @ApiModelProperty(value = "0：可以申请，-1：未达到申请条件； 满500元才能申请代理")
    int status;
    @ApiModelProperty(value = "描述体")
    String msg;

    @Override
    public String toString() {
        return "ApplyProxyConditions{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

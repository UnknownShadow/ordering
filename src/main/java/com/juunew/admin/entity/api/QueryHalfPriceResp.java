package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class QueryHalfPriceResp {

    @ApiModelProperty(value = "是否半价支付，0：全价，1：半价")
    private int status;
    @ApiModelProperty(value = "提示信息")
    private String msg;


    @Override
    public String toString() {
        return "QueryHalfPriceResp{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
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

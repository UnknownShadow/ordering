package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class AuthenticationResp {

    @ApiModelProperty(value = "状态码，0：您已经实名认证过，1：未进行实名认证")
    private int status;
    @ApiModelProperty(value = "消息体")
    private String msg;


    @Override
    public String toString() {
        return "AuthenticationResp{" +
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

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/25.
 */
public class ApplyProxyResp {

    @ApiModelProperty(value = "状态码；1：申请成功、0：申请失败")
    private int status;
    @ApiModelProperty(value = "提示信息")
    private String msg;

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

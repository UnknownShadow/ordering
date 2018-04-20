package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class QRCodeAddProxyReq {

    @ApiModelProperty(value = "扫码者token")
    private String token;
    @ApiModelProperty(value = "分享者UserID")
    private int userId;


    @Override
    public String toString() {
        return "AddProxyReq{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
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
}

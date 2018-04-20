package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class AddProxyReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "需要添加代理的用户ID")
    private String userId;


    @Override
    public String toString() {
        return "AddProxyReq{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

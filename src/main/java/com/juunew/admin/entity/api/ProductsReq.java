package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2017/6/20.
 */
public class ProductsReq {

    @ApiModelProperty(value = "token")
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

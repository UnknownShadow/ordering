package com.juunew.admin.entity;


import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2017/6/20.
 */
public class ClientPayReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "商品ID")
    private int id;


    @Override
    public String toString() {
        return "ClientPayReq{" +
                "token='" + token + '\'' +
                ", id=" + id +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

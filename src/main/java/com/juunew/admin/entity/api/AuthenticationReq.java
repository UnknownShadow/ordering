package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class AuthenticationReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "姓名")
    private String  name;
    @ApiModelProperty(value = "身份证号")
    private String  IDCard;

    @Override
    public String toString() {
        return "AuthenticationReq{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", IDCard='" + IDCard + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class SendDiamondReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "需要发送钻石的用户ID")
    private int userId;
    @ApiModelProperty(value = "需要发送的钻石数量")
    private String diamond;


    @Override
    public String toString() {
        return "SendDiamondReq{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
                ", diamond=" + diamond +
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

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }
}

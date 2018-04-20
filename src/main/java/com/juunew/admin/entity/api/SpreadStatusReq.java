package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class SpreadStatusReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "新增用户unionid")
    private String unionid;
    @ApiModelProperty(value = "新增用户userId")
    private int userId;


    @Override
    public String toString() {
        return "SpreadStatusReq{" +
                "token='" + token + '\'' +
                ", unionid='" + unionid + '\'' +
                ", userId=" + userId +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

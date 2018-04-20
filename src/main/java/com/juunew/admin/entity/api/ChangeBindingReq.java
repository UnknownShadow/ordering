package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class ChangeBindingReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @Override
    public String toString() {
        return "ChangeBindingReq{" +
                "token='" + token + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                '}';
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}

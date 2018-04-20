package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class InviteCodeResp {

    @ApiModelProperty(value = "邀请码，针对玩家有效")
    private int inviteCode;


    @Override
    public String toString() {
        return "InviteCodeResp{" +
                "inviteCode=" + inviteCode +
                '}';
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }
}

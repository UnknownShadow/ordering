package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class Invite {

    @ApiModelProperty(value = "状态码；1：代理邀请码，2：玩家绑定的邀请码，3：无邀请码")
    private int status;
    @ApiModelProperty(value = "邀请码")
    private int inviteCode;


    @Override
    public String toString() {
        return "Invite{" +
                "status=" + status +
                ", inviteCode=" + inviteCode +
                '}';
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }
}

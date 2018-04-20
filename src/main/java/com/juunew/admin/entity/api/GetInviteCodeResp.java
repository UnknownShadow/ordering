package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class GetInviteCodeResp {

    @ApiModelProperty(value = "当前用户的身份，1：总代理，2：代理，3：玩家，4：合伙人")
    private  int userStatus;
    @ApiModelProperty(value = "邀请码")
    private Invite invite;


    @Override
    public String toString() {
        return "GetInviteCodeResp{" +
                "userStatus=" + userStatus +
                ", invite=" + invite +
                '}';
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public Invite getInvite() {
        return invite;
    }

    public void setInvite(Invite invite) {
        this.invite = invite;
    }
}

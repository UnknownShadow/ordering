package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class InviteRecordResp {

    @ApiModelProperty(value = "邀请的用户ID")
    private int inviteUserId;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "用户身份")
    private int userStatus;
    @ApiModelProperty(value = "加入时间")
    private String joinTime;


    @Override
    public String toString() {
        return "InviteRecordResp{" +
                "inviteUserId=" + inviteUserId +
                ", nickname='" + nickname + '\'' +
                ", userStatus=" + userStatus +
                ", joinTime='" + joinTime + '\'' +
                '}';
    }

    public int getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(int inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }
}

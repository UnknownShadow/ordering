package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SendRecordResp {

    @ApiModelProperty(value = "用户ID")
    private int userId;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "用户身份")
    private String userStatus;
    @ApiModelProperty(value = "发钻数量")
    private int diamond;
    @ApiModelProperty(value = "发钻时间")
    private String sendTime;


    @Override
    public String toString() {
        return "SendRecordResp{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", diamond='" + diamond + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}

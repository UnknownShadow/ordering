package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MySubAgentResp {

    @ApiModelProperty(value = "子代理ID")
    private int userId;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "用户身份")
    private String userStatus;
    @ApiModelProperty(value = "加入时间")
    private String joinTime;



    @Override
    public String toString() {
        return "MySubAgentResp{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", userStatus=" + userStatus +
                ", joinTime='" + joinTime + '\'' +
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

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }
}

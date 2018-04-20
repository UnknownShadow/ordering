package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class UserSpreadListResp {

    @ApiModelProperty(value = "推广的用户ID")
    private int userId;
    @ApiModelProperty(value = "推广的用户昵称")
    private String nickName;
    @ApiModelProperty(value = "用户创建时间")
    private long createdTime;


    @Override
    public String toString() {
        return "UserSpreadListResp{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}

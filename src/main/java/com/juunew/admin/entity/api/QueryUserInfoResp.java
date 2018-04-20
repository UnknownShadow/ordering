package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/25.
 */
public class QueryUserInfoResp {

    @ApiModelProperty(value = "ID")
    private int user_id;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "用户身份")
    private String userStatus;
    @ApiModelProperty(value = "钻石数量")
    private int diamond;




    @Override
    public String toString() {
        return "QueryUserInfoResp{" +
                "user_id=" + user_id +
                ", nickName='" + nickName + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", diamond=" + diamond +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}

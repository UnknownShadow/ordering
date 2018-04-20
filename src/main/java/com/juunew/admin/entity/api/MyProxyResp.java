package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class MyProxyResp {

    @ApiModelProperty(value = "子代理ID")
    private int userId;
    @ApiModelProperty(value = "最后登陆时间")
    private long loginTime;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "钻石数量")
    private int diamond;


    @Override
    public String toString() {
        return "MyProxyResp{" +
                "userId=" + userId +
                ", loginTime='" + loginTime + '\'' +
                ", nickName='" + nickName + '\'' +
                ", diamond=" + diamond +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2018/1/28.
 */
public class SendDiamondRecordResp {

    @ApiModelProperty(value = "用户ID")
    private int userId;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "发送钻石的数量")
    private int diamond;
    @ApiModelProperty(value = "发送时间")
    private long sendTime;
    @ApiModelProperty(value = "0：发送；1：收入")
    private int status;


    @Override
    public String toString() {
        return "SendDiamondRecordResp{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", diamond=" + diamond +
                ", sendTime=" + sendTime +
                ", status=" + status +
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

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

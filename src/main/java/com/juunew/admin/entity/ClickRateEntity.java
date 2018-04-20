package com.juunew.admin.entity;


import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2017/6/20.
 */
public class ClickRateEntity {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "msgid")
    private int msgId;


    @Override
    public String toString() {
        return "ClickRateEntity{" +
                "token='" + token + '\'' +
                ", msgId=" + msgId +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }
}

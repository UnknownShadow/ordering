package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class MsgListInfoEntity {

    private boolean expired;//是否过期  :true,
    private int readStatus;//0未读  1.已读  2. 已领取
    private int msgTime; //1970后的时间戳，单位秒

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public int getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(int msgTime) {
        this.msgTime = msgTime;
    }
}

package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class SystemMsgRecordsEntity {
    public final static int SystemMsgRecord_ReadStatus_Unread = 0;
    public final static int SystemMsgRecord_ReadStatus_Readed = 1;
    public final static int SystemMsgRecord_ReadStatus_Geted = 2;

    private int id;    //
    private int user_id;    //
    private int system_msgs_id;    //
    private int read_status;    //读取状态；0：未读；1：已读；2：已领取；
    private int created_at;    //
    private int updated_at;    //
    private int show_times;
    private int hit_count;      //消息点击按钮进入次数


    public int getHit_count() {
        return hit_count;
    }

    public void setHit_count(int hit_count) {
        this.hit_count = hit_count;
    }

    public int getShow_times() {
        return show_times;
    }

    public void setShow_times(int show_times) {
        this.show_times = show_times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSystem_msgs_id() {
        return system_msgs_id;
    }

    public void setSystem_msgs_id(int system_msgs_id) {
        this.system_msgs_id = system_msgs_id;
    }

    public int getRead_status() {
        return read_status;
    }

    public void setRead_status(int read_status) {
        this.read_status = read_status;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }
}

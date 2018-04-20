package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class ControlMsgsEntity {

    private int id;
    private int user_id;
    private int system_msgs_id;
    private int times; //
    private String created_at;//跳转到网页的url


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSystem_msgs_id() {
        return system_msgs_id;
    }

    public void setSystem_msgs_id(int system_msgs_id) {
        this.system_msgs_id = system_msgs_id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

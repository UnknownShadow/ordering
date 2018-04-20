package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class Announcements_recordsEntity {

    private int user_id;
    private int announcement_id;
    private int send_times;
    private String created_at;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(int announcement_id) {
        this.announcement_id = announcement_id;
    }

    public int getSend_times() {
        return send_times;
    }

    public void setSend_times(int send_times) {
        this.send_times = send_times;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

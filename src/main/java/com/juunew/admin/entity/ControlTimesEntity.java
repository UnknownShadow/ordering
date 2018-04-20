package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class ControlTimesEntity {

    private int id;
    private int user_id;
    private int announcements_id;
    private int times;

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

    public int getAnnouncements_id() {
        return announcements_id;
    }

    public void setAnnouncements_id(int announcements_id) {
        this.announcements_id = announcements_id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}

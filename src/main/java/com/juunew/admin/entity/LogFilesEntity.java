package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class LogFilesEntity {

    private int id;
    private int user_id;
    private String created_date;
    private String event;
    private String content;
    private String target;
    private int diamond_changed;

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

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getDiamond_changed() {
        return diamond_changed;
    }

    public void setDiamond_changed(int diamond_changed) {
        this.diamond_changed = diamond_changed;
    }
}

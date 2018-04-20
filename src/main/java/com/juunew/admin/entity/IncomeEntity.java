package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class IncomeEntity {

    private int id;
    private int users_id;
    private String username;
    private String streaming_name;
    private String target_name;
    private String type;
    private int number;
    private int status;
    private String send_date;
    private int fatherproxy_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStreaming_name() {
        return streaming_name;
    }

    public void setStreaming_name(String streaming_name) {
        this.streaming_name = streaming_name;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public int getFatherproxy_id() {
        return fatherproxy_id;
    }

    public void setFatherproxy_id(int fatherproxy_id) {
        this.fatherproxy_id = fatherproxy_id;
    }
}

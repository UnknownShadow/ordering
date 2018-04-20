package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class IntegralsEntity {

    private int id;
    private int user_id;
    private int old_val;        //旧的可用积分值
    private int new_val;        //新的可用积分值
    private int change_val;     //积分变动值
    private int change_type;
    private String purpose;     //积分用途描述
    private Date created_at;
    private Date updated_at;


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

    public int getOld_val() {
        return old_val;
    }

    public void setOld_val(int old_val) {
        this.old_val = old_val;
    }

    public int getNew_val() {
        return new_val;
    }

    public void setNew_val(int new_val) {
        this.new_val = new_val;
    }

    public int getChange_val() {
        return change_val;
    }

    public void setChange_val(int change_val) {
        this.change_val = change_val;
    }

    public int getChange_type() {
        return change_type;
    }

    public void setChange_type(int change_type) {
        this.change_type = change_type;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

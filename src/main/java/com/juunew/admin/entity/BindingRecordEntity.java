package com.juunew.admin.entity;


public class BindingRecordEntity {

    private int id;
    private int user_id;
    private int invite_user_id;
    private String created_time;
    private String updated_time;
    private String desc;     //绑定状态；默认1：绑定，0：已解绑

    //-------------------------------------------------

    private String nickname;
    private int user_status;



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
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

    public int getInvite_user_id() {
        return invite_user_id;
    }

    public void setInvite_user_id(int invite_user_id) {
        this.invite_user_id = invite_user_id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
}

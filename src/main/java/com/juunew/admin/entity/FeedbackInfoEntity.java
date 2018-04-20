package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class FeedbackInfoEntity {

    //1：申请代理、2：问题反馈
    public final static int ApplyAgentType = 1;
    public final static int ProblemFeedbackType = 2;

    private int id;
    private int user_id;
    private String desc;
    private String phone;
    private int type;        //1：申请代理、2：问题反馈
    private String created_time;
    private String updated_time;
    private int connection_status;  //联系状态；1：已联系；0：未联系

    private String nickname;


    public int getConnection_status() {
        return connection_status;
    }

    public void setConnection_status(int connection_status) {
        this.connection_status = connection_status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

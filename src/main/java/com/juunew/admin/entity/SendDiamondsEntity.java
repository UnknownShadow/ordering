package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class SendDiamondsEntity {

    private int id;
    private int send_user_id;
    private int receive_user_id;
    private int diamond;
    private String created_time;
    private String updated_time;

    private String send_nickname;   //发送者昵称
    private String receive_nickname;    //收钻者你昵称



    public String getSend_nickname() {
        return send_nickname;
    }

    public void setSend_nickname(String send_nickname) {
        this.send_nickname = send_nickname;
    }

    public String getReceive_nickname() {
        return receive_nickname;
    }

    public void setReceive_nickname(String receive_nickname) {
        this.receive_nickname = receive_nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSend_user_id() {
        return send_user_id;
    }

    public void setSend_user_id(int send_user_id) {
        this.send_user_id = send_user_id;
    }

    public int getReceive_user_id() {
        return receive_user_id;
    }

    public void setReceive_user_id(int receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
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

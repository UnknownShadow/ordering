package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class PrizeFinishEntity {

    private int id;     //
    private int created_at;     //
    private int round;          //比赛轮次
    private String gift_title;  //奖品名称
    private int get_type;     //奖励类型
    private int nickname;     //获奖人昵称
    private int users_id;     //获奖人ID
    private int name;       //收件人
    private int address;         //
    private int phone;           //
    private int status;         //
    private int confirm_name;     //确认人


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getGet_type() {
        return get_type;
    }

    public void setGet_type(int get_type) {
        this.get_type = get_type;
    }

    public int getNickname() {
        return nickname;
    }

    public void setNickname(int nickname) {
        this.nickname = nickname;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getConfirm_name() {
        return confirm_name;
    }

    public void setConfirm_name(int confirm_name) {
        this.confirm_name = confirm_name;
    }

    public String getGift_title() {
        return gift_title;
    }

    public void setGift_title(String gift_title) {
        this.gift_title = gift_title;
    }
}

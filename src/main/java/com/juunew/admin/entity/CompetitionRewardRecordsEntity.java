package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class CompetitionRewardRecordsEntity {

    private int id;         //
    private String name;         //
    private String phone;         //
    private String address;         //
    private String created_at;         //
    private int status;         //0. 未处理  1. 已发放  2. 未填写   3. 已填写领取地址信息等待处理
    private int user_id;
    private String nickname;       //获奖昵称
    private String gift_title;  //奖品名称
    private int get_type;       //奖励类型
    private String confirm_content;     //确认收货人及单号
    private int competition_id;
    private int round;
    private String title;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGift_title() {
        return gift_title;
    }

    public void setGift_title(String gift_title) {
        this.gift_title = gift_title;
    }

    public int getGet_type() {
        return get_type;
    }

    public void setGet_type(int get_type) {
        this.get_type = get_type;
    }

    public String getConfirm_content() {
        return confirm_content;
    }

    public void setConfirm_content(String confirm_content) {
        this.confirm_content = confirm_content;
    }

    public int getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(int competition_id) {
        this.competition_id = competition_id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

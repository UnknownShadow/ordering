package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class ClubsActionEntity {

    private int id;
    private int club_id;
    private String club_code;
    private int creator_id;   //俱乐部创建者
    private String nickname;   //俱乐部创建者 昵称
    private int level;
    private int reward_integral;    //奖励积分值，根据满足条件获得
    private double integral;    //奖励积分值，根据满足条件获得
    private int audit;      //审核状态，默认2：审核中，1：通过，0：拒绝
    private int second_total_times;
    private int second_total_money;
    private Date created_at;
    private String created_time;
    private Date updated_at;


    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public int getSecond_total_times() {
        return second_total_times;
    }

    public void setSecond_total_times(int second_total_times) {
        this.second_total_times = second_total_times;
    }

    public int getSecond_total_money() {
        return second_total_money;
    }

    public void setSecond_total_money(int second_total_money) {
        this.second_total_money = second_total_money;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public String getClub_code() {
        return club_code;
    }

    public void setClub_code(String club_code) {
        this.club_code = club_code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getReward_integral() {
        return reward_integral;
    }

    public void setReward_integral(int reward_integral) {
        this.reward_integral = reward_integral;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
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

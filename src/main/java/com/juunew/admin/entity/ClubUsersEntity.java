package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class ClubUsersEntity {

    private int id;
    private int club_id;
    private int user_id;
    private int status;     //会员状态: 0 申请加入  1. 已成会员
    private int is_creator;     //是否为创建者
    private int game_times;     //游戏次数
    private int today_game_times;     //今日游戏次数
    private String created_at;
    private Date updated_at;

    private String nickname;


    public int getToday_game_times() {
        return today_game_times;
    }

    public void setToday_game_times(int today_game_times) {
        this.today_game_times = today_game_times;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_creator() {
        return is_creator;
    }

    public void setIs_creator(int is_creator) {
        this.is_creator = is_creator;
    }

    public int getGame_times() {
        return game_times;
    }

    public void setGame_times(int game_times) {
        this.game_times = game_times;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

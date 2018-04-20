package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class ClubsEntity {

    private int id;
    private String name;       //俱乐部名字
    private String announcement;    //公告
    private int gamekind;       //游戏类型
    private String game_kind;       //游戏类型文字
    private int creator_id;     //创建人ID
    private String code;           //俱乐部编号
    private int game_times;     //游戏次数
    private int status;     //俱乐部状态 0：正常，1：停封
    private String created_at;
    private Date updated_at;

    private String nickname;    //创建者昵称
    private int members;    //俱乐部总人数
    private int today_game_times;    //俱乐部今日局数
    private int sign_cost;    //游戏开房用钻


    public int getSign_cost() {
        return sign_cost;
    }

    public void setSign_cost(int sign_cost) {
        this.sign_cost = sign_cost;
    }

    public int getToday_game_times() {
        return today_game_times;
    }

    public void setToday_game_times(int today_game_times) {
        this.today_game_times = today_game_times;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGame_kind() {
        return game_kind;
    }

    public void setGame_kind(String game_kind) {
        this.game_kind = game_kind;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public int getGamekind() {
        return gamekind;
    }

    public void setGamekind(int gamekind) {
        this.gamekind = gamekind;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

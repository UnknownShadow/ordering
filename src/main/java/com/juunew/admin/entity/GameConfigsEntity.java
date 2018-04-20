package com.juunew.admin.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class GameConfigsEntity {

    private int id;
    private int game_kind;      //游戏类别: 1. 鱼虾蟹， 2. 上游  3. 激K， 99. 服务器配置，101. 代理特权活动配置
    private String config;     //游戏自有配置
    private int price;      //开房价格
    private Date created_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGame_kind() {
        return game_kind;
    }

    public void setGame_kind(int game_kind) {
        this.game_kind = game_kind;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}

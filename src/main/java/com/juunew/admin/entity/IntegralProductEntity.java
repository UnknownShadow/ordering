package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class IntegralProductEntity {

    //1、普通玩家价格；2、绑定代理玩家价格；3、代理价格； 4、总代理价格
    public final static int PlayerType = 1;
    public final static int BindingPlayerType = 2;
    public final static int AgentType = 3;
    public final static int GeneralAgentType = 4;


    private int id;
    private int money;      //金额
    private int diamond;       //钻石数量
    private int type;      //区分绑定的用户、玩家、积分换钻；1：绑定用户充值体系，2：玩家充值体系，3：积分换钻充值体系
    private String img_url;
    private String created_time;
    private String updated_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
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

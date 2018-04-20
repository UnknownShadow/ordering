package com.juunew.admin.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by juunew on 2017/6/20.
 */
public class CompetitionTemplatsEntity {

    private int id;             //主键ID
    private String title;       //标题
    private String name;        //副标题
    private String cost;        //报名费用和费用类型，1为钻石，2为金币；json类型
    private String rule;        //比赛规则，json类型
    private int user_limit;         //开赛人数
    private int type;           //比赛类型   游戏类型: 1. 大赛， 2. 常规循环
    private String start_time;      //比赛开始时间
    private String end_time;        //比赛结束时间
    private String sign_start_time;     //报名时间

    private int seq;         //排序
    private String rewards;
    private String num;         //报名费用
    private String rules;
    private int round;
    private int total_enrollment;
    private int status;     //比赛状态
    private String show_time;     //比赛展示时间
    private String pic_url;     //比赛展示图片
    private String password;     //比赛展示图片
    private String totalPeriod;     //比赛轮次
    private String settleType;     //比赛计分规则
    private String enable;     //是否终止比赛； 0：终止；1正常比赛


    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(String totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getShow_time() {
        return show_time;
    }

    public void setShow_time(String show_time) {
        this.show_time = show_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getUser_limit() {
        return user_limit;
    }

    public void setUser_limit(int user_limit) {
        this.user_limit = user_limit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSign_start_time() {
        return sign_start_time;
    }

    public void setSign_start_time(String sign_start_time) {
        this.sign_start_time = sign_start_time;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getTotal_enrollment() {
        return total_enrollment;
    }

    public void setTotal_enrollment(int total_enrollment) {
        this.total_enrollment = total_enrollment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class DataDetailsResp {

    @ApiModelProperty(value = "合伙人ID")
    private int userId;
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "充值金额")
    private double rechargeNum;
    @ApiModelProperty(value = "活跃人数")
    private int activeNum;
    @ApiModelProperty(value = "开房次数")
    private int gamesNum;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "充值的钻石数量")
    private int rechargeDiam;
    @ApiModelProperty(value = "开房用钻数量")
    private int gameConsumeDiam;


    @Override
    public String toString() {
        return "DataDetailsResp{" +
                "userId=" + userId +
                ", date='" + date + '\'' +
                ", rechargeNum=" + rechargeNum +
                ", activeNum=" + activeNum +
                ", gamesNum=" + gamesNum +
                ", nickname='" + nickname + '\'' +
                ", rechargeDiam=" + rechargeDiam +
                ", gameConsumeDiam=" + gameConsumeDiam +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(double rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public int getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(int activeNum) {
        this.activeNum = activeNum;
    }

    public int getGamesNum() {
        return gamesNum;
    }

    public void setGamesNum(int gamesNum) {
        this.gamesNum = gamesNum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRechargeDiam() {
        return rechargeDiam;
    }

    public void setRechargeDiam(int rechargeDiam) {
        this.rechargeDiam = rechargeDiam;
    }

    public int getGameConsumeDiam() {
        return gameConsumeDiam;
    }

    public void setGameConsumeDiam(int gameConsumeDiam) {
        this.gameConsumeDiam = gameConsumeDiam;
    }
}

package com.juunew.admin.entity.constitute;

/**
 * Created by juunew on 2018/4/4.
 */
public class PartnershipResp {

    private int userId;
    private int gameNum;   //开房数量
    private int rechargeDiam;   //充钻数量
    private int activeNum;  //活跃人数
    private int agentNum;   //代理人数
    private int playerNum;   //玩家人数
    private String nickname;   //玩家人数
    private int gameConsumeDiam;   //游戏开房用钻



    public int getGameConsumeDiam() {
        return gameConsumeDiam;
    }

    public void setGameConsumeDiam(int gameConsumeDiam) {
        this.gameConsumeDiam = gameConsumeDiam;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameNum() {
        return gameNum;
    }

    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }

    public int getRechargeDiam() {
        return rechargeDiam;
    }

    public void setRechargeDiam(int rechargeDiam) {
        this.rechargeDiam = rechargeDiam;
    }

    public int getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(int activeNum) {
        this.activeNum = activeNum;
    }

    public int getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(int agentNum) {
        this.agentNum = agentNum;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}

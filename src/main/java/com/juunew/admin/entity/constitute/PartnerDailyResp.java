package com.juunew.admin.entity.constitute;

/**
 * Created by juunew on 2018/4/4.
 */
public class PartnerDailyResp {

    private int userId;     //合伙人ID
    private int rechargeNum;    //充值金额  （自己和下级和下级的下级）
    private int activeNum;      //活跃人数（自己和下级和下级的下级）
    private int gamesNum;    //当日房间总数（自己和下级和下级的下级）
   // private int gamesNum;    //销售钻石（自己和下级和下级的下级）




    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(int rechargeNum) {
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
}

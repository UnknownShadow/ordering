package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class UserPrivilegeEntity {

    private int userId;
    private int currentDay;     //当前任务天数
    private String created_at;
    private String lashFinishTime;  //最后完成任务时间
    private String finishDay;   //完成任务天数

    private String nickname;   //昵称


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

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLashFinishTime() {
        return lashFinishTime;
    }

    public void setLashFinishTime(String lashFinishTime) {
        this.lashFinishTime = lashFinishTime;
    }

    public String getFinishDay() {
        return finishDay;
    }

    public void setFinishDay(String finishDay) {
        this.finishDay = finishDay;
    }
}

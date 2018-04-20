package com.juunew.admin.entity.constitute;

/**
 * Created by juunew on 2018/4/4.
 */
public class PrivilegedDetails {
    private int userId;
    private String nickname;
    private int day;   //第几天
    private int task;   //当天需要完成的任务
    private int reward;   //奖励积分值

    @Override
    public String toString() {
        return "PrivilegedDetails{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", day=" + day +
                ", task=" + task +
                ", reward=" + reward +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}

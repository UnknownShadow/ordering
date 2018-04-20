package com.juunew.admin.entity.constitute;

/**
 * Created by juunew on 2018/4/4.
 */
public class AgentPrivilege {
    private int userId;
    private String nickname;
    private String code;   //特权码
    private String bindingTime;   //特权码绑定时间
    private int day;   //天数
    private int task;   //当天总任务
    private double integral;   //打卡获得的积分值（单位：元）
    private int superiorAgentId;     //上级代理ID
    private String superiorAgentNickname;   //上级代理昵称
    private int progress;   //查询当前总完成进度
    private int today_progress;   //查询当天进度


    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public int getToday_progress() {
        return today_progress;
    }

    public void setToday_progress(int today_progress) {
        this.today_progress = today_progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getSuperiorAgentId() {
        return superiorAgentId;
    }

    public void setSuperiorAgentId(int superiorAgentId) {
        this.superiorAgentId = superiorAgentId;
    }

    public String getSuperiorAgentNickname() {
        return superiorAgentNickname;
    }

    public void setSuperiorAgentNickname(String superiorAgentNickname) {
        this.superiorAgentNickname = superiorAgentNickname;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBindingTime() {
        return bindingTime;
    }

    public void setBindingTime(String bindingTime) {
        this.bindingTime = bindingTime;
    }
}

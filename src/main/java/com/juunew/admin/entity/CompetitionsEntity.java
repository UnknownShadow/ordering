package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class CompetitionsEntity {

    private int id;
    private int round;  //比赛轮次
    private int competition_template_id;
    private int total_enrollment;
    private int status;     //比赛状态
    private int session;     //当前比赛轮次


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getCompetition_template_id() {
        return competition_template_id;
    }

    public void setCompetition_template_id(int competition_template_id) {
        this.competition_template_id = competition_template_id;
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

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }
}

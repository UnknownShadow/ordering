package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class TemporaryEntity {

    private int id;                  //
    private String insert_date;     //
    private int users_id;           //
    private String nickname;     //
    private int diamond;         //
    private int out_count;     //
    private int in_count;     //
    private int out_num;     //
    private double avg;     //
    private int in_num;     //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getOut_count() {
        return out_count;
    }

    public void setOut_count(int out_count) {
        this.out_count = out_count;
    }

    public int getIn_count() {
        return in_count;
    }

    public void setIn_count(int in_count) {
        this.in_count = in_count;
    }

    public int getOut_num() {
        return out_num;
    }

    public void setOut_num(int out_num) {
        this.out_num = out_num;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public int getIn_num() {
        return in_num;
    }

    public void setIn_num(int in_num) {
        this.in_num = in_num;
    }
}

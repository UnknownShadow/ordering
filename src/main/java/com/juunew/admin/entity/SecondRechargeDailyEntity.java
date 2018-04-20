package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class SecondRechargeDailyEntity {

    private int id;
    private int total;
    private int second_total_money;
    private int second_total_times;
    private String daily_date;
    private Date created_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSecond_total_money() {
        return second_total_money;
    }

    public void setSecond_total_money(int second_total_money) {
        this.second_total_money = second_total_money;
    }

    public int getSecond_total_times() {
        return second_total_times;
    }

    public void setSecond_total_times(int second_total_times) {
        this.second_total_times = second_total_times;
    }

    public String getDaily_date() {
        return daily_date;
    }

    public void setDaily_date(String daily_date) {
        this.daily_date = daily_date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}

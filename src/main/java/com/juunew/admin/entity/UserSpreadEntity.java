package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class UserSpreadEntity {

    private int id;
    private int spreader_user_id;
    private int downloader_user_id;
    private String downloader_unionid;
    private int spread_status;
    private double integral_proportion;
    private Date created_time;
    private Date updated_time;


    public int getDownloader_user_id() {
        return downloader_user_id;
    }

    public void setDownloader_user_id(int downloader_user_id) {
        this.downloader_user_id = downloader_user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpreader_user_id() {
        return spreader_user_id;
    }

    public void setSpreader_user_id(int spreader_user_id) {
        this.spreader_user_id = spreader_user_id;
    }

    public String getDownloader_unionid() {
        return downloader_unionid;
    }

    public void setDownloader_unionid(String downloader_unionid) {
        this.downloader_unionid = downloader_unionid;
    }

    public int getSpread_status() {
        return spread_status;
    }

    public void setSpread_status(int spread_status) {
        this.spread_status = spread_status;
    }

    public double getIntegral_proportion() {
        return integral_proportion;
    }

    public void setIntegral_proportion(double integral_proportion) {
        this.integral_proportion = integral_proportion;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }
}

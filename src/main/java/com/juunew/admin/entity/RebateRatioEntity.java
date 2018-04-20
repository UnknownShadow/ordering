package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class RebateRatioEntity {

    //1：一级代理返利比例、2：代理返利比例、3：一级充值给自己返利
    public final static int FirstRatio = 1;
    public final static int TwoRatio = 2;
    public final static int FirstSelfRatio = 3;


    private int id;
    private double ratio;      //返利比例
    private int type;       //代理的返利比例；1：一级代理返利比例、2：代理返利比例、3：一级充值给自己返利
    private String created_time;
    private String updated_time;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
}

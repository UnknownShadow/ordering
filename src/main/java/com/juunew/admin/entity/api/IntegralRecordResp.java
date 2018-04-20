package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralRecordResp {

    @ApiModelProperty(value = "当前记录的主键ID")
    private int id;
    @ApiModelProperty(value = "创建时间")
    private long createdTime;
    @ApiModelProperty(value = "积分来源")
    private String source;
    @ApiModelProperty(value = "积分数量,保留两位小数，单位：元")
    private double num;
    @ApiModelProperty(value = "冻结状态")
    private String state;

    @Override
    public String toString() {
        return "IntegralRecordResp{" +
                "id=" + id +
                ", createdTime=" + createdTime +
                ", source='" + source + '\'' +
                ", num=" + num +
                ", state='" + state + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

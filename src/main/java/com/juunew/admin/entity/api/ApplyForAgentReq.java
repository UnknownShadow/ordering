package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class ApplyForAgentReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "电话号码")
    private String phone;
    @ApiModelProperty(value = "称呼或问题描述")
    private String nameOrQuestion;
    @ApiModelProperty(value = "状态码；1：申请代理、2：问题反馈")
    private int status;


    @Override
    public String toString() {
        return "ApplyForAgentReq{" +
                "token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", nameOrQuestion='" + nameOrQuestion + '\'' +
                ", status=" + status +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNameOrQuestion() {
        return nameOrQuestion;
    }

    public void setNameOrQuestion(String nameOrQuestion) {
        this.nameOrQuestion = nameOrQuestion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

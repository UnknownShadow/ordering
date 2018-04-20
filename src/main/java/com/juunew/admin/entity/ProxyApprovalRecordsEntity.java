package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class ProxyApprovalRecordsEntity {

    private int id;
    private int user_id;
    private String nickname;
    private String name;
    private String phone;
    private int source;              //代理来源；1：微信公众号添加，2：后台手动添加
    private int proxy_hierarchy;         //代理层级 1:代理  2：至尊合伙人
    private String address;
    private int dispose_result;         //处理结果；1：通过；0：拒绝
    private String dispose_name;         //处理人
    private String approve_date;         //代理申请时间
    private String dispose_date;         //处理时间


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getProxy_hierarchy() {
        return proxy_hierarchy;
    }

    public void setProxy_hierarchy(int proxy_hierarchy) {
        this.proxy_hierarchy = proxy_hierarchy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDispose_result() {
        return dispose_result;
    }

    public void setDispose_result(int dispose_result) {
        this.dispose_result = dispose_result;
    }

    public String getDispose_name() {
        return dispose_name;
    }

    public void setDispose_name(String dispose_name) {
        this.dispose_name = dispose_name;
    }

    public String getApprove_date() {
        return approve_date;
    }

    public void setApprove_date(String approve_date) {
        this.approve_date = approve_date;
    }

    public String getDispose_date() {
        return dispose_date;
    }

    public void setDispose_date(String dispose_date) {
        this.dispose_date = dispose_date;
    }
}

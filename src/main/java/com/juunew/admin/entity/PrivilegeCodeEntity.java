package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class PrivilegeCodeEntity {

    private int id;
    private String code;   //特权码
    private int userId;
    private String created_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


}

package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class IntentProxyEntity {

    private int id;
    private String name;
    private String phone;
    private String created_time;
    private int connection_status;      //联系状态
    private String remark;          //备注


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public int getConnection_status() {
        return connection_status;
    }

    public void setConnection_status(int connection_status) {
        this.connection_status = connection_status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

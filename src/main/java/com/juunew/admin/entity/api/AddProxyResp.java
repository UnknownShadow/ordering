package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class AddProxyResp {

    @ApiModelProperty(value = "状态码；0：添加成功，其它为失败")
    private int status;
    @ApiModelProperty(value = "描述信息")
    private String msg;



    @Override
    public String toString() {
        return "AddProxyResp{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

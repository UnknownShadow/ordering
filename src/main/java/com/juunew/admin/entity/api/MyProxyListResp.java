package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class MyProxyListResp {

    @ApiModelProperty(value = "子代理集合")
    private List<MyProxyResp> data;
    @ApiModelProperty(value = "时code：0成功；-9失败")
    private int code;
    @ApiModelProperty(value = "错误信息")
    private String errMsg;


    @Override
    public String toString() {
        return "MyProxyListResp{" +
                "data=" + data +
                ", code=" + code +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }


    public List<MyProxyResp> getData() {
        return data;
    }

    public void setData(List<MyProxyResp> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class SpreadStatusRespBody {

    @ApiModelProperty(value = "内容体")
    private SpreadStatusResp Data;
    @ApiModelProperty(value = "错误码，0：成功，其它失败")
    private int code;
    @ApiModelProperty(value = "错误消息")
    private String errMsg;


    @Override
    public String toString() {
        return "SpreadStatusRespBody{" +
                "Data=" + Data +
                ", code=" + code +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }

    public SpreadStatusResp getData() {
        return Data;
    }

    public void setData(SpreadStatusResp data) {
        Data = data;
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

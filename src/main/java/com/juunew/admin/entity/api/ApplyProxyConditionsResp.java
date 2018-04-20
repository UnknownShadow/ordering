package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/25.
 */
public class ApplyProxyConditionsResp {


    @ApiModelProperty(value = "成功内容体")
    ApplyProxyConditions data;
    @ApiModelProperty(value = "状态码，成功为0， 其他错误")
    int code;
    @ApiModelProperty(value = "错误消息，仅在code不为0时出现")
    String errMsg;


    public ApplyProxyConditions getData() {
        return data;
    }

    public void setData(ApplyProxyConditions data) {
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

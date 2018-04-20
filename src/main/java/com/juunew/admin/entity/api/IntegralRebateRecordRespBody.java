package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralRebateRecordRespBody {

    @ApiModelProperty(value = "内容体")
    private List<IntegralRebateRecordResp> Data;
    @ApiModelProperty(value = "code，0：成功，其他失败")
    private int code;
    @ApiModelProperty(value = "错误信息提示")
    private String errMsg;


    @Override
    public String toString() {
        return "IntegralRebateRecordRespBody{" +
                "Data=" + Data +
                ", code=" + code +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }


    public List<IntegralRebateRecordResp> getData() {
        return Data;
    }

    public void setData(List<IntegralRebateRecordResp> data) {
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

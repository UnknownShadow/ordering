package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class QueryReviewResp {

    public static final int WithDrawStatus_In = 1;
    public static final int WithDrawStatus_Success = 2;
    public static final int WithDrawStatus_None = 3;


    @ApiModelProperty("提现状态: 1. 审核中 2. 通过可提现  3. 无申请")
    private int withDrawStatus;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("订单号： 如果可提现会传过来")
    private int withDrawId;

    public int getWithDrawId() {
        return withDrawId;
    }

    public void setWithDrawId(int withDrawId) {
        this.withDrawId = withDrawId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getWithDrawStatus() {
        return withDrawStatus;
    }

    public void setWithDrawStatus(int withDrawStatus) {
        this.withDrawStatus = withDrawStatus;
    }
}

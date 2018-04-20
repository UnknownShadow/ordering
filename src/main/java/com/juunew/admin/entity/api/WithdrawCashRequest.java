package com.juunew.admin.entity.api;

import com.juunew.admin.entity.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class WithdrawCashRequest extends ApiRequest{

    @ApiModelProperty("订单号")
    private int withDrawId;

    public int getWithDrawId() {
        return withDrawId;
    }

    public void setWithDrawId(int withDrawId) {
        this.withDrawId = withDrawId;
    }
}

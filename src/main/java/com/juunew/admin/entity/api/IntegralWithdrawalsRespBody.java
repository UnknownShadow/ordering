package com.juunew.admin.entity.api;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralWithdrawalsRespBody {

    @ApiModelProperty("消息")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

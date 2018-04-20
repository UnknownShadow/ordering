package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralRebateRecordResp {

    @ApiModelProperty(value = "内容体")
    private List<UserSpreadListResp> Data;
    @ApiModelProperty(value = "code，0：成功，其他失败")
    private int code;
    @ApiModelProperty(value = "错误信息提示")
    private String errMsg;




}

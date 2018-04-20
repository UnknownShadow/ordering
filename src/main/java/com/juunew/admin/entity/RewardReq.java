package com.juunew.admin.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RewardReq {
    @ApiModelProperty(value = "奖励消息ID")
    private int rewardRecordId;

    @ApiModelProperty(value = "token")
    private String token;


    public int getRewardRecordId() {
        return rewardRecordId;
    }

    public void setRewardRecordId(int rewardRecordId) {
        this.rewardRecordId = rewardRecordId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

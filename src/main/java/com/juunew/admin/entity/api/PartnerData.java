package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class PartnerData {

    @ApiModelProperty(value = "<合伙人>下级代理（总人数）")
    private int subAgentTotalNum;
    @ApiModelProperty(value = "<合伙人>我的总代理总人数")
    private int firstAgentNum;
    @ApiModelProperty(value = "<合伙人>我的代理总人数")
    private int twoAgentNum;
    /*@ApiModelProperty(value = "<合伙人>新增一级代理（人）")
    private int newFirstAgent ;
    @ApiModelProperty(value = "<合伙人>新增代理（人）")
    private int newTwoAgent;
    @ApiModelProperty(value = "<合伙人>下级充钻（颗）")
    private int subAgentRecharge;*/

    @Override
    public String toString() {
        return "PartnerData{" +
                "subAgentTotalNum=" + subAgentTotalNum +
                ", firstAgentNum=" + firstAgentNum +
                ", twoAgentNum='" + twoAgentNum + '\'' +
                '}';
    }

    public int getSubAgentTotalNum() {
        return subAgentTotalNum;
    }

    public void setSubAgentTotalNum(int subAgentTotalNum) {
        this.subAgentTotalNum = subAgentTotalNum;
    }

    public int getFirstAgentNum() {
        return firstAgentNum;
    }

    public void setFirstAgentNum(int firstAgentNum) {
        this.firstAgentNum = firstAgentNum;
    }

    public int getTwoAgentNum() {
        return twoAgentNum;
    }

    public void setTwoAgentNum(int twoAgentNum) {
        this.twoAgentNum = twoAgentNum;
    }
}

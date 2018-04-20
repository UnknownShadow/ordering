package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class FirstAgentData {

    @ApiModelProperty(value = "<总代理>我的下级代理总人数")
    private int subAgentTotalNum;
    @ApiModelProperty(value = "<总代理>我的绑定玩家总人数")
    private int bindingPlayerNun;
    /*@ApiModelProperty(value = "<总代理>新增下级代理（人）")
    private int newSubAgent;
    @ApiModelProperty(value = "<总代理>新增绑定玩家（人）")
    private int newBindingPlayer;
    @ApiModelProperty(value = "<总代理>下级充钻数量（钻）")
    private int subAgentRechargeNum;
    @ApiModelProperty(value = "<总代理>获得积分（分）")
    private int obtainIntegral;*/


    @Override
    public String toString() {
        return "FirstAgentData{" +
                "subAgentTotalNum=" + subAgentTotalNum +
                ", bindingPlayerNun=" + bindingPlayerNun +
                '}';
    }


    public int getSubAgentTotalNum() {
        return subAgentTotalNum;
    }

    public void setSubAgentTotalNum(int subAgentTotalNum) {
        this.subAgentTotalNum = subAgentTotalNum;
    }

    public int getBindingPlayerNun() {
        return bindingPlayerNun;
    }

    public void setBindingPlayerNun(int bindingPlayerNun) {
        this.bindingPlayerNun = bindingPlayerNun;
    }
}

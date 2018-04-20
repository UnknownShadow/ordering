package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class TwoAgentData {

    @ApiModelProperty(value = "<代理>我的绑定玩家总人数")
    private int bindingPlayerNun;
   /* @ApiModelProperty(value = "<代理>新增绑定玩家（人）")
    private int newBindingPlayer;
    @ApiModelProperty(value = "<代理>下级充钻数量（钻）")
    private int subAgentRechargeNum;
    @ApiModelProperty(value = "<代理>获得积分（分）")
    private int obtainIntegral;*/

    @Override
    public String toString() {
        return "TwoAgentData{" +
                "bindingPlayerNun=" + bindingPlayerNun +
                '}';
    }

    public int getBindingPlayerNun() {
        return bindingPlayerNun;
    }

    public void setBindingPlayerNun(int bindingPlayerNun) {
        this.bindingPlayerNun = bindingPlayerNun;
    }
}

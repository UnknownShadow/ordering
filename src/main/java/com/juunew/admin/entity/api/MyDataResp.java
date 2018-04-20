package com.juunew.admin.entity.api;


import com.juunew.admin.entity.GameUserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class MyDataResp {

    @ApiModelProperty(value = "合伙人显示的数据")
    private PartnerData partnerData;
    @ApiModelProperty(value = "总代理显示的数据")
    private FirstAgentData firstAgentData;
    @ApiModelProperty(value = "代理显示的数据")
    private TwoAgentData twoAgentData;

    @Override
    public String toString() {
        return "MyDataResp{" +
                "partnerData=" + partnerData +
                ", firstAgentData=" + firstAgentData +
                ", twoAgentData=" + twoAgentData +
                '}';
    }

    public PartnerData getPartnerData() {
        return partnerData;
    }

    public void setPartnerData(PartnerData partnerData) {
        this.partnerData = partnerData;
    }

    public FirstAgentData getFirstAgentData() {
        return firstAgentData;
    }

    public void setFirstAgentData(FirstAgentData firstAgentData) {
        this.firstAgentData = firstAgentData;
    }

    public TwoAgentData getTwoAgentData() {
        return twoAgentData;
    }

    public void setTwoAgentData(TwoAgentData twoAgentData) {
        this.twoAgentData = twoAgentData;
    }
}

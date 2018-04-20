package com.juunew.admin.entity.api;


import com.juunew.admin.entity.GameUserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class PersonalCenterResp {

    @ApiModelProperty(value = "当前用户的身份，1：总代理，2：代理，3：玩家，4：合伙人")
    private  int userStatus;
    @ApiModelProperty(value = "我的积分")
    private double integer;
    @ApiModelProperty(value = "邀请码")
    private Invite invite;
    @ApiModelProperty(value = "标签")
    private  Object label;
    @ApiModelProperty(value = "手机号")
    private  String phone;


    @Override
    public String toString() {
        return "PersonalCenterResp{" +
                "userStatus=" + userStatus +
                ", integer=" + integer +
                ", invite=" + invite +
                ", label=" + label +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Invite getInvite() {
        return invite;
    }

    public void setInvite(Invite invite) {
        this.invite = invite;
    }

    public double getInteger() {
        return integer;
    }

    public void setInteger(double integer) {
        this.integer = integer;
    }

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
}

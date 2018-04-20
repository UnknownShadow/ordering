package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/25.
 */
public class QueryUserResp {

    @ApiModelProperty(value = "ID")
    private int user_id;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "身份；2：代理、3：玩家")
    private String userStatus;
    @ApiModelProperty(value = "钻石数量")
    private int diamond;
    @ApiModelProperty(value = "绑定手机")
    private String phone;
    @ApiModelProperty(value = "创建时间")
    private long createTime;


    @Override
    public String toString() {
        return "QueryUserResp{" +
                "user_id=" + user_id +
                ", nickName='" + nickName + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", diamond=" + diamond +
                ", phone='" + phone + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

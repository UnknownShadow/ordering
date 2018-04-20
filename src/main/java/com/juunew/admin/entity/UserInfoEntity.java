package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/9/23.
 */
public class UserInfoEntity {

    private String headimgurl;
    private String openid;
    private int flag;
    private GameUserEntity userData;

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public GameUserEntity getUserData() {
        return userData;
    }

    public void setUserData(GameUserEntity userData) {
        this.userData = userData;
    }
}

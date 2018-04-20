package com.juunew.admin.controller;

import com.juunew.admin.dao.GameUserDao;
import com.juunew.admin.dao.ProxyApprovalRecordsDao;
import com.juunew.admin.dao.WechatInfoDao;
import com.juunew.admin.entity.GameUserEntity;
import com.juunew.admin.entity.ProxyApprovalRecordsEntity;
import com.juunew.admin.entity.UserInfoEntity;
import com.juunew.admin.entity.WechatInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by juunew on 2017/9/23.
 */
@Controller
public class UserInfoController {

    @Autowired
    WechatInfoDao wechatInfoDao;
    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    ProxyApprovalRecordsDao proxyApprovalRecordsDao;


    public UserInfoEntity UserInfo(int userId){

        UserInfoEntity userInfoEntity = new UserInfoEntity();

        System.out.println(userId+"UserInfo类中的userID");

        WechatInfoEntity wechatInfoEntity = wechatInfoDao.findByData(userId);

        String headimgurl="";
        String openid="";
        int flag=0;
        if(wechatInfoEntity!=null){
            headimgurl = wechatInfoEntity.getHeadimgurl();
            openid = wechatInfoEntity.getOpenid();


            //根据user_id 查询是否 为代理；
            GameUserEntity fatherProxyByUserId = gameUserDao.findFatherProxyByUserId(userId);

            if(fatherProxyByUserId!=null){
                flag = 1;
            }

            //查询是否   是审核中
            //select * from proxy_approval_records where user_id=45 and dispose_result is not null
            ProxyApprovalRecordsEntity dataByUserId = proxyApprovalRecordsDao.findByUserId(userId);

            if(dataByUserId!=null){
                flag=2;
            }

            //根据 user_id 查询玩家信息:  ID、昵称；
            GameUserEntity userData = gameUserDao.findDataByusersId(userId);


            //根据 user_id 查询玩家  钻石
            GameUserEntity userDiamond = gameUserDao.proxyPagingDiamondAndMoney(userId);

            System.out.println();

            if(userDiamond!=null) {
                userData.setDiamond(userDiamond.getDiamond());
            }

            userInfoEntity.setUserData(userData);
            userInfoEntity.setFlag(flag);
            userInfoEntity.setHeadimgurl(headimgurl);
        }
        return userInfoEntity;
    }
}

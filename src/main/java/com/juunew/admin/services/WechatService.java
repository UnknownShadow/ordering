package com.juunew.admin.services;


import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Service
public class WechatService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WechatsDao wechatsDao;

    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    ProxyApprovalRecordsDao proxyApprovalRecordsDao;
    @Autowired
    WechatInfoDao wechatInfoDao;
    @Autowired
    DiamondsDao diamondsDao;
    @Autowired
    ClubUsersDao clubUsersDao;
    @Autowired
    ClubsDao clubsDao;
    @Autowired
    UserSpreadDao userSpreadDao;


    public class UserDataStuct{
        public int flag=0;
        public GameUserEntity userData;
        public int proxy_flag;
        public int dispose_result;
    }


    public int auth(HttpSession session){
        int userId = 0;

        logger.debug("session id: {}",session.getAttribute("wechat.user.id"));
        if(session.getAttribute("wechat.user.id")!=null) {
            userId = (Integer) session.getAttribute("wechat.user.id");
        }

        return userId;
    }


    /**
     * 根据session中的信息来登录
     * @param session
     * @return
     */
    public ModelAndView getModelAndViewByUser(HttpSession session) {
        int userId = 0;

        logger.debug("session id: {}",session.getAttribute("wechat.user.id"));
        if(session.getAttribute("wechat.user.id")!=null) {
            userId = (Integer) session.getAttribute("wechat.user.id");
            logger.info("缓存中的user_id: {}",userId);
        }

        if (userId > 0) { //如果找到userId
            logger.info("从seesion中找到玩家ID: {}", userId);


            WechatInfoEntity wechatInfoEntity = wechatInfoDao.findByData(userId);
            if (wechatInfoEntity != null) {
                WechatService.UserDataStuct dataStuct = this.getDataByUnionId(wechatInfoEntity.getUnionid());

                if (dataStuct != null) {
                    ModelAndView mv = new ModelAndView();
                    mv.addObject("dispose_result", dataStuct.dispose_result);
                    mv.addObject("proxy_flag", dataStuct.proxy_flag);
                    mv.addObject("userData", dataStuct.userData);
                    mv.addObject("openid", wechatInfoEntity.getOpenid());
                    mv.addObject("flag", dataStuct.flag);//dataStuct.flag  2
                    mv.addObject("headimgurl", wechatInfoEntity.getHeadimgurl());
                    mv.setViewName("wechat_player_index");
                    return mv;
                }
            }
        }
        return null;
    }


    /**
     * 根据unionId, 找到两边系统的对应关系
     *
     * @param unionId
     * @return
     */
    public UserDataStuct getDataByUnionId(String unionId) {

        logger.info("根据unionId, 找到两边系统的对应关系 （开始）");

        UserDataStuct userDataStuct = new UserDataStuct();
        //根据得到的unionid 查询数据库中相对应的 users_id; (wechats表)
        WechatsEntity wechatsEntity = wechatsDao.findByUnionId(unionId);
        if (wechatsEntity == null) return null;
        int userId = wechatsEntity.getUser_id();     //根据uniondID 得到user_id


        //根据user_id 查询是否 为代理；   flag ：2为代理，3，0为玩家
        GameUserEntity fatherProxyByUserId = gameUserDao.findFatherProxyByUserId(userId);
        if (fatherProxyByUserId != null) {
            wechatInfoDao.updateFlagOther(userId);
            userDataStuct.flag = fatherProxyByUserId.getStatus();
        }else{
            userDataStuct.flag = 0;
        }

        //查询是否   是审核中
        /*ProxyApprovalRecordsEntity dataByUserId = proxyApprovalRecordsDao.findByUserId(userId);
        if (dataByUserId != null) {
            wechatInfoDao.updateFlag(userId);
            userDataStuct.flag = 2;
        }*/

        //查询充值RMB总数值：
        userDataStuct.proxy_flag = 0;
        DiamondsEntity payTotal = diamondsDao.findPayTotal(userId);
        if(payTotal!=null){
            int price = payTotal.getPrice()/100;
            if(price>=200){
                userDataStuct.proxy_flag = 1;
            }
        }


        //查询 代理申请记录：
        ProxyApprovalRecordsEntity disposeResult = proxyApprovalRecordsDao.findDisposeResult(userId);
        if(disposeResult!=null){
            userDataStuct.dispose_result = disposeResult.getDispose_result();
        }else{
            userDataStuct.dispose_result = 0;
        }


        //根据 user_id 查询玩家信息:  ID、昵称；
        GameUserEntity userData = gameUserDao.findDataByusersId(userId);



        //根据 user_id 查询玩家  钻石
        GameUserEntity userDiamond = gameUserDao.proxyPagingDiamondAndMoney(userId);
        if (userDiamond != null) {
            userData.setDiamond(userDiamond.getDiamond());
        }

        userDataStuct.userData = userData;

        logger.info("userDataStuct.proxy_flag（充值人民币是否超过200，1：超过200,0：不超过200） 值为：{}",userDataStuct.proxy_flag);
        logger.info("userDataStuct.flag （flag ：1为代理，2为审核中，0为玩家<可能是第一次登录，在user表中没有记录>）值为：{}",userDataStuct.flag);
        logger.info("userDataStuct.dispose_result（审核标识；1：审核中；0：审核拒绝） 值为：{}",userDataStuct.dispose_result);
        logger.info("根据unionId, 找到两边系统的对应关系 （结束）");

//      wechatInfoDao.insertWechatInfo(userId, openid, unionid, headimgurl);

        return userDataStuct;
    }





//-----------------------以下是俱乐部邀请页面中微信授权登录（缓存）-----------------------------------

    /**
     * 根据session中的信息来登录
     * @param session
     * @return
     */
    public ModelAndView getClubByUser(String clubCode,HttpSession session) {
        int userId = 0;

        logger.debug("session id: {}",session.getAttribute("wechat.user.id"));
        if(session.getAttribute("wechat.user.id")!=null) {
            userId = (Integer) session.getAttribute("wechat.user.id");
            logger.info("俱乐部邀请页面微信授权登录：（缓存中的user_id）: {}",userId);
        }

        if (userId > 0) { //如果找到userId
            logger.debug("(俱乐部邀请)从seesion中找到玩家ID: {}", userId);

            WechatInfoEntity wechatInfoEntity = wechatInfoDao.findByData(userId);
            if (wechatInfoEntity != null) {

                ModelAndView mv = new ModelAndView();


                //业务逻辑：
                int status = -1;    // -1表示不是游戏玩家
                int inviteUserId=0;
                String inviteNickname = "";
                String clubName = "";

                //根据clubCode查询 出 邀请人 User_id
                ClubsEntity getClub = clubsDao.findByCode(clubCode);
                if(getClub != null){
                    inviteUserId = getClub.getCreator_id();
                    clubName = getClub.getName();

                    //根据user_id 查询 nickname
                    GameUserEntity queryNickName = gameUserDao.findNickName(inviteUserId);
                    if(queryNickName != null){
                        inviteNickname = queryNickName.getNickname();
                    }

                    logger.info("当前发起俱乐部邀请的用户 user_id :{}",inviteUserId);
                    logger.info("当前发起俱乐部邀请的用户 昵称 :{}",inviteNickname);
                }else{
                    logger.error("无法通过俱乐部Code 获取数据！");
                }


                int beInvitedUserId=userId;     //当前登录的用户user_id （缓存中的数据）

                //根据当前登录的 用户user_id 和 俱乐部code 查询 数据，看该用户是否 已经加入该俱乐部，或其他状态；
                ClubUsersEntity getClubStatus = clubUsersDao.findByCodeAndUserId(clubCode, beInvitedUserId);
                if (getClubStatus != null) {
                    status = getClubStatus.getStatus();   // (表中status值：)会员状态: 0 申请加入  1. 已成会员
                } else {
                    //status: -1:不是游戏玩家，9:是游戏玩家但不是俱乐部成员，0：申请加入（审核中）1. 已成会员
                    status = 9;
                }

                String token="";
                //查询当前微信授权登录 用户的 token
                GameUserEntity queryToken = gameUserDao.findByUserId(beInvitedUserId);
                if(queryToken != null){
                    token = queryToken.getToken();
                }


                mv.addObject("clubCode", clubCode);
                mv.addObject("inviteNickname", inviteNickname);
                mv.addObject("clubName", clubName);
                mv.addObject("beInvitedUserId", beInvitedUserId);
                mv.addObject("status", status);
                mv.addObject("token", token);
                mv.setViewName("client_club_invite");
                return mv;
            }
        }
        return null;
    }



//------------------------------------以下是 推广用户 下载第二次登录 信息获取-------------------

    /**
     * 根据session中的信息来登录 ,此方法为 第二次登录
     * @param session
     * @return
     */
    public ModelAndView userSpreadInfo(HttpSession session,int spreaderUserId) {

        int userId = 0;

        logger.info("进入微信授权缓存：");

        if(session.getAttribute("wechat.user.unionid")!=null){

            //查询 此扫码者用户是否是老用户，如果是老用户，则不需要返利积分，数据中的则不生成数据
            WechatsEntity queryUnionId = wechatsDao.findByUnionId((String)session.getAttribute("wechat.user.unionid"));

            if (queryUnionId == null){      //数据为空，代表不是老用户
                logger.info("推广者user_id（"+spreaderUserId+"） 与 被推广者 unionid（"+(String)session.getAttribute("wechat.user.unionid")+"） 建立关系");

                //根据unionid删除重复数据
                userSpreadDao.delSameDownloader((String)session.getAttribute("wechat.user.unionid"));
                //将推广者 与 被推广者建立关系
                userSpreadDao.insertToUserSpread(spreaderUserId,(String)session.getAttribute("wechat.user.unionid"),0.1);
            }else{
                logger.info("用户unionid（"+queryUnionId.getUnionid()+"）--> UserId("+queryUnionId.getUser_id()+") 是老用户");
            }
            ModelAndView mv = new ModelAndView();
            mv.setViewName("jump_page");
            return mv;
        }

        return null;
    }
}

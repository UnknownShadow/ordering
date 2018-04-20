package com.juunew.admin.controller;

import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.services.WechatService;
import com.juunew.admin.services.WechatApiService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 用于获取微信用户详细信息
 */
@Controller
public class WechatCallbackController {
    @Autowired
    WechatApiService weixinUtil;
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




    @Value("${wechat.appid}")
    String APPID;

    @Value("${wechat.appsecret}")
    String APPSECRET;

    @Value("${wechat.env:prod}")
    String wechatEnv;

    @Value("${wechat.testunionid:oFmRVwh7P2lSxkSxWvN8H7y3kVnQ}")
    //@Value("${wechat.testunionid:oFmRVwo2t8XPO4t97EpaYc8OPU21}")
    String testUnionId;

    @Autowired
    WechatService wechatService;


    Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 微信公众号个人中心页面
     */
    @RequestMapping("/callBack")
    public ModelAndView callBacks(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
        ModelAndView mv = wechatService.getModelAndViewByUser(session);
        if (mv != null) return mv;


        mv = new ModelAndView();
        String code = req.getParameter("code");
        logger.debug("code: {}", code);

        //通过code换取网页授权access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID
                + "&secret=" + APPSECRET
                + "&code=" + code
                + "&grant_type=authorization_code";

        JSONObject jsonObject = weixinUtil.doGetStr(url);

        logger.debug("oauth2: {}", jsonObject);

        if (jsonObject.containsKey("errcode") && 40163 == jsonObject.getInt("errcode")){
            //这个属于code过期，重新认证下
            mv.setView(new RedirectView("/wxLogin", false));
            return mv;
        }

        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");

        //拉取用户信息(需scope为 snsapi_userinfo)
        String inforUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token
                + "&openid=" + openid
                + "&lang=zh_CN";
        JSONObject userInfo = weixinUtil.doGetStr(inforUrl);

        logger.debug("userinfo: {}", userInfo);

        String headimgurl = userInfo.getString("headimgurl");

        String unionid ="";
        if (userInfo.containsKey("unionid")){
            unionid = userInfo.getString("unionid");
        }

        logger.info("玩家登陆后的unionid：{}",unionid);

        if (StringUtils.isEmpty(unionid) && "test".equals(wechatEnv)){
            unionid = testUnionId;
        }

        /*if (StringUtils.isEmpty(unionid)){
            throw new Exception("找不到unionid");
        }*/


        int userId=0;
        //存入到数据库
        WechatService.UserDataStuct dataStuct = wechatService.getDataByUnionId(unionid);
        if (dataStuct!=null){
            userId = dataStuct.userData.getId();
            wechatInfoDao.deleteAllByUnionId(unionid);
            wechatInfoDao.insertWechatInfo(userId, openid, unionid, headimgurl);
        }else{
            logger.info("不是游戏玩家用户");
            //throw new Exception("找不到此玩家");
        }

        //将userId存入session
        session.setAttribute("wechat.user.id", userId);
        logger.info("登录的userId: {}", userId);

        if(userId != 0){
            mv.addObject("dispose_result", dataStuct.dispose_result);
            mv.addObject("proxy_flag", dataStuct.proxy_flag);
            mv.addObject("userData", dataStuct.userData);
            mv.addObject("flag", dataStuct.flag);
            mv.addObject("openid", openid);
            mv.addObject("headimgurl", headimgurl);
        }else{
            resp.sendRedirect("/game_download");
        }
        mv.setViewName("wechat_player_index");
        return mv;
    }


    /**
     * 以下是俱乐部 微信授权
     * @param clubCode      俱乐部ID
     */
    @RequestMapping("/callBackLogin")
    public ModelAndView callBackLogin(String clubCode,HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
        ModelAndView mv = wechatService.getClubByUser(clubCode,session);
        if (mv != null) return mv;

        mv = new ModelAndView();
        String code = req.getParameter("code");
        logger.debug("code: {}", code);

        //通过code换取网页授权access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID
                + "&secret=" + APPSECRET
                + "&code=" + code
                + "&grant_type=authorization_code";

        JSONObject jsonObject = weixinUtil.doGetStr(url);

        logger.debug("oauth2: {}", jsonObject);

        if (jsonObject.containsKey("errcode") && 40163 == jsonObject.getInt("errcode")){
            //这个属于code过期，重新认证下
            mv.setView(new RedirectView("/user/api/club_invite?clubCode="+clubCode, false));
            return mv;
        }

        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");

        //拉取用户信息(需scope为 snsapi_userinfo)
        String inforUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token
                + "&openid=" + openid
                + "&lang=zh_CN";
        JSONObject userInfo = weixinUtil.doGetStr(inforUrl);

        logger.debug("userinfo: {}", userInfo);

        String unionid ="";
        if (userInfo.containsKey("unionid")){
            unionid = userInfo.getString("unionid");
        }

        logger.info("玩家登陆后的unionid：{}",unionid);

        if (StringUtils.isEmpty(unionid) && "test".equals(wechatEnv)){
            unionid = testUnionId;
        }



        int status = -1;    // -1表示不是游戏玩家
        int inviteUserId=0;
        String inviteNickname = "";
        String clubName = "";
        String token="";

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



        int beInvitedUserId=0;
        WechatsEntity wechatsEntity = wechatsDao.findByUnionId(unionid);
        if (wechatsEntity != null){
            beInvitedUserId = wechatsEntity.getUser_id();     //根据uniondID 得到user_id
        }

        if (beInvitedUserId != 0){  //该 IF中 是查询出当前登录的用户 在该俱乐部中的状态

            //根据当前登录的 用户user_id 和 俱乐部code 查询 数据，看该用户是否 已经加入该俱乐部，或其他状态；
            ClubUsersEntity getClubStatus = clubUsersDao.findByCodeAndUserId(clubCode, beInvitedUserId);
            if (getClubStatus != null) {
                status = getClubStatus.getStatus();   // (表中status值：)会员状态: 0 申请加入  1. 已成会员
            } else {
                status = 9;
            }

            //查询当前微信授权登录 用户的 token
            GameUserEntity queryToken = gameUserDao.findByUserId(beInvitedUserId);
            if(queryToken != null){
                token = queryToken.getToken();
            }

            //将userId存入session
            session.setAttribute("wechat.user.id", beInvitedUserId);
            logger.info("登录的userId: {}", beInvitedUserId);
        }else{
            logger.info("不是游戏玩家用户");
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


    /**
     * 以下是推广用户 微信授权
     * @param spreaderUserId    推广者ID
     */
    @RequestMapping("/spreadCallBack")
    public ModelAndView spreadCallBack(int spreaderUserId,HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {

        ModelAndView mv = wechatService.userSpreadInfo(session,spreaderUserId);
        if (mv != null) return mv;

        mv = new ModelAndView();
        String code = req.getParameter("code");
        logger.info("code: {}", code);

        //通过code换取网页授权access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID
                + "&secret=" + APPSECRET
                + "&code=" + code
                + "&grant_type=authorization_code";

        JSONObject jsonObject = weixinUtil.doGetStr(url);

        logger.info("oauth2: {}", jsonObject);

        if (jsonObject.containsKey("errcode") && 40163 == jsonObject.getInt("errcode")){
            //这个属于code过期，重新认证下
            mv.setView(new RedirectView("/user/api/userSpread?userId="+spreaderUserId, false));
            return mv;
        }

        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");

        //拉取用户信息(需scope为 snsapi_userinfo)
        String inforUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token
                + "&openid=" + openid
                + "&lang=zh_CN";
        JSONObject userInfo = weixinUtil.doGetStr(inforUrl);

        logger.info("userinfo: {}", userInfo);

        String unionid ="";
        if (userInfo.containsKey("unionid")){
            unionid = userInfo.getString("unionid");
        }

        logger.info("玩家登陆后的unionid：{}",unionid);

        //用于测试
        if (StringUtils.isEmpty(unionid) && "test".equals(wechatEnv)){
            unionid = testUnionId;
        }


        //业务处理-------------------


        //查询 此扫码者用户是否是老用户，如果是老用户，则不需要返利积分，数据中的则不生成数据
        WechatsEntity queryUnionId = wechatsDao.findByUnionId(unionid);

        if (queryUnionId == null){      //数据为空，代表不是老用户

            logger.info("推广者user_id（"+spreaderUserId+"） 与 被推广者 unionid（"+unionid+"） 建立关系");

            //根据unionid删除重复数据
            userSpreadDao.delSameDownloader(unionid);
            //将推广者 与 被推广者建立关系
            userSpreadDao.insertToUserSpread(spreaderUserId,unionid,0.1);
        }

        //将unionid存入缓存
        session.setAttribute("wechat.user.unionid", unionid);

        mv.setViewName("jump_page");
        return mv;
    }




}

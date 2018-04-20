package com.juunew.admin.controller.rest;

import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.controller.BaseController;
import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.entity.api.*;
import com.juunew.admin.services.*;
import com.juunew.admin.utils.CommonUtil;
import com.juunew.admin.wechat.HttpReqUtil;
import com.juunew.admin.wechat.PayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "管理中心", description = "管理中心")
@RestController
@RequestMapping("user/api/")
public class ManagementCenterController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    MsgSendingService msgSendingService;
    @Autowired
    BindingRecordDao bindingRecordDao;
    @Autowired
    ExchangeDiamondsDao exchangeDiamondsDao;
    @Autowired
    UserBanksDao userBanksDao;
    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    BankService bankService;
    @Autowired
    SendDiamondsDao sendDiamondsDao;
    @Autowired
    IntegralProductDao integralProductDao;
    @Autowired
    IntegralRebateDao integralRebateDao;
    @Autowired
    WechatService wechatService;
    @Autowired
    ClubsDao clubsDao;
    @Autowired
    GetUserInfoService getUserInfoService;
    @Autowired
    WithdrawsDao withdrawsDao;
    @Autowired
    FeedbackInfoDao feedbackInfoDao;
    @Autowired
    LivesDao livesDao;
    @Autowired
    WechatRechargeDao wechatRechargeDao;
    @Autowired
    WechatPayService payService;
    @Autowired
    IntegralsDao integralsDao;
    @Autowired
    MsgPushService msgPushService;
    @Autowired
    ProxyWithdrawJudgeService proxyWithdrawJudgeService;
    @Autowired
    SystemMsgsDao systemMsgsDao;
    @Autowired
    SystemMsgRecordsDao systemMsgRecordsDao;
    @Autowired
    RechargeService rechargeService;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationDao authenticationDao;

    @Autowired
    RealtimeOnlineHistoriesDao realtimeOnlineHistoriesDao;


    //微信授权所需参数
    @Value("${wechat.appid}")
    String APPID;
    @Value("${wechat.appsecret}")
    String APPSECRET;
    @Value("${wechat.backUrl}")
    String backUrl;
    @Value("${server.token}")
    String serverToken;


    @ApiOperation(value = "个人中心标签页面的显示和隐藏", notes = "进入个人中心标签页面的显示和隐藏", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = PersonalCenterResp.class)
    @ResponseBody
    @RequestMapping(value = "personalCenter", method = RequestMethod.POST)
    public TestResp personalCenter(@RequestBody ApiRequest apiRequest) throws Exception {
        logger.info("客户端请求数据（/personalCenter）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        //内容体用于返回给客户端的数据格式
        PersonalCenterResp personalCenterResp = new PersonalCenterResp();
        Invite invite = new Invite();
        List list = new ArrayList();        //用于控制客户端标签的显示和隐藏

        int user_id = tokenAuth.getId();


        /**
         * 积分查询
         */
        GameUserEntity integralEntity = gameUserDao.findIntegralByUserId(user_id);
        //总积分
        double integral = 0;
        if (integralEntity != null) integral = integralEntity.getIntegral() * 1.0 / 100;

        personalCenterResp.setInteger(integral);


        /**
         * 邀请码获取
         */

        //根据user_id查询身份
        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        int user_status = 3;
        if (userStatus != null) {
            user_status = userStatus.getUser_status();

            //邀请码
            int invite_code = 0;
            if (user_status == GameUserEntity.Two_Level_Agent || user_status == GameUserEntity.First_Level_Agent) {

                invite_code = userStatus.getInvite_code();

                if (invite_code == 0) {

                    //随机获取邀请码
                    invite_code = getUserInfoService.getInviteCode();

                    //将邀请码新增到表中
                    gameUserDao.updateInviteCode(invite_code, user_id);
                }

                //代理邀请码
                invite.setInviteCode(invite_code);
                invite.setStatus(1);


                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObjectOne = new JSONObject();
                JSONObject jsonObjectTwo = new JSONObject();
                JSONObject jsonObjectThree = new JSONObject();
                List data = new ArrayList();
                List subAgent = new ArrayList();
                List diamonds = new ArrayList();
                List integrals = new ArrayList();

                data.add("我的数据");
                data.add("数据详情");
                data.add("充值详情");
                diamonds.add("发送钻石");
                diamonds.add("发钻记录");
                integrals.add("提现");
                integrals.add("积分记录");
                integrals.add("提现记录");
                integrals.add("积分换钻");

                if (user_status == GameUserEntity.First_Level_Agent) {
                    //总代理标签
                    subAgent.add("我的子代理");
                    subAgent.add("我绑定的玩家");
                    subAgent.add("添加子代理");
                } else {
                    //代理标签
                    subAgent.add("我绑定的玩家");
                }

                jsonObject.put("数据", data);
                jsonObjectOne.put("下级管理", subAgent);
                jsonObjectTwo.put("钻石", diamonds);
                jsonObjectThree.put("积分", integrals);

                list.add(jsonObject);
                list.add(jsonObjectOne);
                list.add(jsonObjectTwo);
                list.add(jsonObjectThree);


            } else if (user_status == GameUserEntity.Player) {

                //无邀请码
                invite.setInviteCode(0);
                invite.setStatus(3);

                //查询玩家绑定的邀请码  (可能为0)
                GameUserEntity queryProxy = gameUserDao.findProxy(user_id);
                if (queryProxy != null && queryProxy.getFatherproxy_id() != 0) {
                    int fatherproxy_id = queryProxy.getFatherproxy_id();

                    GameUserEntity userInfo = gameUserDao.findUserStatus(fatherproxy_id);
                    int inviteCode = userInfo.getInvite_code();
                    if (inviteCode > 0) {
                        //玩家绑定的邀请码
                        invite.setInviteCode(inviteCode);
                        invite.setStatus(2);
                    }
                }
            } else if (user_status == GameUserEntity.Partner) {
                //无邀请码
                invite.setInviteCode(0);
                invite.setStatus(3);

                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObjectOne = new JSONObject();

                //合伙人标签
                List data = new ArrayList();
                data.add("我的数据");
                data.add("数据详情");
                data.add("充值详情");
                List subAgent = new ArrayList();
                subAgent.add("我的总代理");
                subAgent.add("添加总代理");

                jsonObject.put("数据", data);
                jsonObjectOne.put("下级管理", subAgent);

                list.add(jsonObject);
                list.add(jsonObjectOne);
            }
        } else {
            //无邀请码
            invite.setInviteCode(0);
            invite.setStatus(3);
        }


        //查询用户的绑定手机号
        GameUserEntity gameUserEntity = gameUserDao.findByUserId(user_id);
        String phone = "";
        if (gameUserEntity.getPhone() != null) phone = gameUserEntity.getPhone();


        personalCenterResp.setUserStatus(user_status);
        personalCenterResp.setInvite(invite);
        personalCenterResp.setLabel(list);
        personalCenterResp.setPhone(phone);


        TestResp testResp = new TestResp();
        testResp.setData(personalCenterResp);
        testResp.setCode(0);

        logger.info("服务端返回数据：{}", personalCenterResp.toString());
        return testResp;
    }


    @ApiOperation(value = "我的总代理", notes = "我的总代理", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = MySubAgentResp.class)
    @ResponseBody
    @RequestMapping(value = "mySubAgent", method = RequestMethod.POST)
    public TestResp mySubAgent(@RequestBody ApiRequest apiRequest) throws Exception {
        logger.info("客户端请求数据（/mySubAgent）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        List list = new ArrayList();

        int user_id = tokenAuth.getId();

        //根据user_id查询我的子代理
        List<GameUserEntity> allProxy = gameUserDao.queryAllProxy(user_id);
        if (allProxy.size() != 0) {
            for (GameUserEntity gameUserEntity : allProxy) {

                //内容体用于返回给客户端的数据格式
                MySubAgentResp mySubAgentResp = new MySubAgentResp();

                int userId = gameUserEntity.getUsers_id();

                int user_status = gameUserEntity.getUser_status();
                String userStatus = "玩家";
                if (user_status == GameUserEntity.Partner) {
                    userStatus = "合伙人";
                } else if (user_status == GameUserEntity.First_Level_Agent) {
                    userStatus = "总代理";
                } else if (user_status == GameUserEntity.Two_Level_Agent) {
                    userStatus = "代理";
                }


                String fatherproxy_date = gameUserEntity.getFatherproxy_date();
                if (fatherproxy_date == null || fatherproxy_date.equals("")) {
                    fatherproxy_date = CommonUtil.creationDate("yyyy-MM-dd HH:mm:ss");
                    gameUserDao.updateSuperDate(userId);
                }
                if (fatherproxy_date.length() == 10) fatherproxy_date = fatherproxy_date + " 00:00:00";


                //查昵称
                GameUserEntity nickName = gameUserDao.findNickName(userId);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();

                mySubAgentResp.setUserId(userId);
                mySubAgentResp.setUserStatus(userStatus);
                mySubAgentResp.setNickname(nickname);
                mySubAgentResp.setJoinTime(fatherproxy_date);
                list.add(mySubAgentResp);
            }
        }


        TestResp testResp = new TestResp();
        testResp.setData(list);
        testResp.setCode(0);

        logger.info("服务端返回数据：{}", testResp.toString());
        return testResp;
    }


    @ApiOperation(value = "我邀请的玩家", notes = "我邀请的玩家", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = MySubAgentResp.class)
    @ResponseBody
    @RequestMapping(value = "inviteRecord", method = RequestMethod.POST)
    public TestResp inviteRecord(@RequestBody ApiRequest apiRequest) throws Exception {

        //我邀请的玩家：上级代理是我，并且该用户是玩家身份

        logger.info("客户端请求数据（/inviteRecord）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        List list = new ArrayList();

        int user_id = tokenAuth.getId();

        //根据user_id查询邀请记录
        List<GameUserEntity> allProxies = gameUserDao.queryAllInvite(user_id);
        if (allProxies.size() != 0) {
            for (GameUserEntity allProxy : allProxies) {

                //内容体用于返回给客户端的数据格式
                MySubAgentResp mySubAgentResp = new MySubAgentResp();

                int user_status = allProxy.getUser_status();
                String userStatus = "玩家";
                if (user_status == GameUserEntity.Partner) {
                    userStatus = "合伙人";
                } else if (user_status == GameUserEntity.First_Level_Agent) {
                    userStatus = "总代理";
                } else if (user_status == GameUserEntity.Two_Level_Agent) {
                    userStatus = "代理";
                }

                int userId = allProxy.getUsers_id();

                //查昵称
                GameUserEntity nickName = gameUserDao.findNickName(userId);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();

                mySubAgentResp.setUserId(userId);
                mySubAgentResp.setUserStatus(userStatus);
                mySubAgentResp.setNickname(nickname);
                mySubAgentResp.setJoinTime(allProxy.getFatherproxy_date());
                list.add(mySubAgentResp);
            }
        }

        TestResp testResp = new TestResp();
        testResp.setData(list);
        testResp.setCode(0);

        logger.info("服务端返回数据：{}", testResp.toString());
        return testResp;
    }


    @ApiOperation(value = "查询玩家", notes = "根据user_id查询玩家", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = QueryUserResp.class)
    @ResponseBody
    @RequestMapping(value = "queryUserInfo", method = RequestMethod.POST)
    public TestResp queryUserInfo(@RequestBody QueryUserReq queryUserReq) throws Exception {

        logger.info("客户端请求数据（user/api/queryUser）：{}", queryUserReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(queryUserReq.getToken());
        String userId = queryUserReq.getUserId();

        //判断 是否为数字
        int user_id = CommonUtil.isNumeric(userId);

        if (user_id <= 0) {
            throw new Exception("查询失败，请输入正确用户ID");
        }

        //查询请求用户是否为代理，4：合伙人，1：总代理，2：代理，3：玩家； 只有1、4才能够添加子代理
     /*   GameUserEntity queryUserStatus = gameUserDao.findByID(tokenAuth.getId());

        int user_status = 3;
        if (queryUserStatus != null) user_status = queryUserStatus.getUser_status();

        if (user_status != GameUserEntity.First_Level_Agent && user_status != GameUserEntity.Partner) {
            throw new Exception("查询失败，只有合伙人或总代理才能查询玩家信息");
        }
*/

        //查询客户端请求的user_id是否存在
        GameUserEntity user = gameUserDao.findByUserId(user_id);
        if (user == null) throw new Exception("无此玩家，请重新查询！");


        //服务端返回数据
        QueryUserInfoResp queryUserInfoResp = new QueryUserInfoResp();


        //根据user_id查询用户信息
        GameUserEntity userInfo = getUserInfoService.getUserInfo(user_id);

        //user表为空时，查询users表，users表有数据时，建立两表的关系
        if (userInfo == null) {

            ModelAndView mv = getUserInfoService.buildRelationships(user_id);
            Map<String, Object> model = mv.getModel();
            logger.info("无法从（user）表中获取数，建立user和users表关联，获得数据。");
            GameUserEntity userInf = (GameUserEntity) model.get("user");
            if (userInf != null) {
                userInfo = userInf;
            } else {
                throw new Exception("无法获取该用户的基本信息，请重新查询！");
            }
        }


        String userStatus = "玩家";
        if (userInfo.getUser_status() == GameUserEntity.Partner) {
            userStatus = "合伙人";
        } else if (userInfo.getUser_status() == GameUserEntity.First_Level_Agent) {
            userStatus = "总代理";
        } else if (userInfo.getUser_status() == GameUserEntity.Two_Level_Agent) {
            userStatus = "代理";
        }


        UserBanksEntity userBanksEntity = userBanksDao.queryTotalDiamond(user_id);
        int diamond = 0;
        if (userBanksEntity != null) diamond = userBanksEntity.getDiamond();


        //将查询出来的值封装返回
        queryUserInfoResp.setUser_id(userInfo.getUsers_id());
        queryUserInfoResp.setNickName(userInfo.getNickname());
        queryUserInfoResp.setDiamond(diamond);
        queryUserInfoResp.setUserStatus(userStatus);

        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(queryUserInfoResp);
        testResp.setErrMsg("");

        return testResp;
    }


    @ApiOperation(value = "添加子代理", notes = "添加玩家为自己的子代理", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = TestResp.class)
    @ResponseBody
    @RequestMapping(value = "addSubAgent", method = RequestMethod.POST)
    public TestResp addSubAgent(@RequestBody AddProxyReq addProxyReq) throws Exception {

        logger.info("客户端请求数据（/addSubAgent）：{}", addProxyReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(addProxyReq.getToken());
        int user_id = CommonUtil.isNumeric(addProxyReq.getUserId());


        //查询请求用户是否为代理，4：合伙人，1：总代理，2：代理，3：玩家； 只有1、4才能够添加子代理
        GameUserEntity queryUserStatus = gameUserDao.findByID(tokenAuth.getId());

        int user_status = 3;
        if (queryUserStatus != null) user_status = queryUserStatus.getUser_status();

        if (user_status != GameUserEntity.First_Level_Agent && user_status != GameUserEntity.Partner) {
            throw new Exception("添加失败，只有合伙人或总代理才能添加子代理");
        }


        //查询客户端请求的user_id是否存在
        GameUserEntity user = gameUserDao.findByUserId(user_id);
        if (user == null) {
            throw new Exception("添加失败，无此玩家，请重新查询！");
        } else {
            //查询（user）表中是否有数据
            GameUserEntity userInfo = gameUserDao.findUserByUserId(user_id);
            if (userInfo == null) {
                //建立关系
                ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
            }


            //查询是否是玩家
            GameUserEntity queryUser = gameUserDao.findUserStatus(user_id);
            if (queryUser != null) {
                if (queryUser.getUser_status() != GameUserEntity.Player) {
                    throw new Exception("添加失败，请通知该玩家绑定您的邀请码后再试。");
                }
            }

            //获取当前时间，
            String sysDate = CommonUtil.creationDate("yyyy-MM-dd HH:mm:ss");

            //查询是否已经成为代理了；
            GameUserEntity fatherProxyId = gameUserDao.findFatherProxyId(user_id);
            if (fatherProxyId == null || fatherProxyId.getFatherproxy_id() == 0) {

                //如果是合伙人，则只能添加用户为总代理、总代理只能添加用户为代理
                int userStatus = GameUserEntity.Two_Level_Agent;
                if (user_status == GameUserEntity.Partner) userStatus = GameUserEntity.First_Level_Agent;

                gameUserDao.addProxy(6, tokenAuth.getId(), user_id, sysDate, userStatus);    //调用添加代理的接口


                //查询发送前钻石
                GameUserEntity receiveUserOldDiamond = gameUserDao.findDiamByUserId(user_id);
                int old_val = 0;
                if (receiveUserOldDiamond != null) old_val = receiveUserOldDiamond.getDiamond();

                //首次绑定送4钻
                bankService.addDiamond(1, user_id, 4, 1, "首次绑定送4钻", 311);

                logger.info("用户（" + user_id + "）首次绑定用户（" + tokenAuth.getId() + "）送4钻石");


                //查询发送后钻石
                GameUserEntity receiveUserNewDiamond = gameUserDao.findDiamByUserId(user_id);
                int new_val = 0;
                if (receiveUserNewDiamond != null) new_val = receiveUserNewDiamond.getDiamond();


                //查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(tokenAuth.getId());
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();


                /**
                 * 首次绑定发送钻石消息
                 */
                String content = "恭喜你获得首次绑定用户4钻石";
                JSONObject contentObject = new JSONObject();
                contentObject.put("userId", user_id);
                contentObject.put("title", "钻石发送");
                contentObject.put("content", content);
                contentObject.put("oldDiamond", old_val);
                contentObject.put("newDiamond", new_val);

                //调用消息推送接口  cmd为12时 为代理给玩家发送钻石消息
                msgPushService.newPushSysMsg(12, contentObject, user_id);


                /**
                 * 添加成功后消息推送
                 */
                //--------------------------------------------参数处理-------------------------

                boolean immediateness = true;      //是否立即发送
                boolean needExit = false;       //是否需要退出 ,
                String picUrl = "";    //图片地址
                int picCmd = 1;
                int picPage = 1;
                String picToUrl = "";
                int showPlace = 0;
                int msgType = 1;    //消息类型  1：文本消息；2：纯图消息；3：图文消息
                int reward = 0; //奖励；0：无奖励, 其他奖励数量
                boolean btn_show = false;       //是否显示按钮
                int btnToPage = 1;
                int btnCmd = 0;
                boolean needScroller = true;
                boolean expired = false;    //是否过期
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = formatter.format(new Date());
                Date startTime = formatter.parse(format);
                int rewardType = 1;
                boolean sendAll = false;
                boolean needSave = true;       //是否保存到消息列表
                int showAgain = 1;


                JSONObject jsonObject = new JSONObject();
                List userIds = new ArrayList();
                userIds.add(user_id);


                //查询是 添加成了总代理还是代理，显示不同的提示
                GameUserEntity findUserStatus = gameUserDao.findUserStatus(user_id);
                int userStatusFlag = findUserStatus.getUser_status();

                String contentAndTitle = "恭喜您，已经被" + nickname + "（ID:" + tokenAuth.getId() + "）添加为尊贵的代理！";

                if (userStatusFlag == GameUserEntity.First_Level_Agent) {
                    contentAndTitle = "恭喜您，已经被系统添加为尊贵的总代！";
                }


                //结束时间处理
                long end = startTime.getTime() + 3 * 24 * 60 * 60 * 1000 * 1L;
                Date end_date = new Date(end);


                SystemMsgsEntity systemMsgsEntity = new SystemMsgsEntity();
                systemMsgsEntity.setTitle(contentAndTitle);
                systemMsgsEntity.setContent(contentAndTitle);
                systemMsgsEntity.setImmediateness(immediateness);
                systemMsgsEntity.setNeed_exit(needExit);
                systemMsgsEntity.setBtn_show(0);            //是否显示按钮
                systemMsgsEntity.setBtn_to_url("");
                systemMsgsEntity.setBtn_to_page(btnToPage);
                systemMsgsEntity.setBtn_cmd(btnCmd);
                systemMsgsEntity.setShow_place(showPlace);
                systemMsgsEntity.setMsg_type(msgType);
                systemMsgsEntity.setPic_url("");
                systemMsgsEntity.setPic_cmd(picCmd);
                systemMsgsEntity.setPic_to_page(picPage);
                systemMsgsEntity.setPic_to_url(picToUrl);
                systemMsgsEntity.setReward(reward);
                systemMsgsEntity.setBtn_title("");
                systemMsgsEntity.setStart_time(startTime);
                systemMsgsEntity.setEnd_time(end_date);
                systemMsgsEntity.setVersion("");
                systemMsgsEntity.setNeed_scroller(needScroller);
                systemMsgsEntity.setPlatfrom(0);
                systemMsgsEntity.setCmd(5);
                systemMsgsEntity.setSend_all(sendAll);
                systemMsgsEntity.setRaw_content("");
                systemMsgsEntity.setNeed_save(needSave);
                systemMsgsEntity.setShow_again(showAgain);
                systemMsgsEntity.setShow_times(3);

                logger.info("消息参数：{}", systemMsgsEntity.toString());

                //将数据写入数据库
                systemMsgsDao.insertToSysMsg(systemMsgsEntity);

                logger.info("获取新增消息数据的ID：{}", systemMsgsEntity.getId());


                int msgId = systemMsgsEntity.getId();      //消息ID

                SystemMsgRecordsEntity api = new SystemMsgRecordsEntity();
                systemMsgRecordsDao.insertToMsgRecords(user_id, msgId, 0, api);


                /*--------------------------------------------参数处理-----------------------*/


                msgSendingService.diposeMsg(contentAndTitle, contentAndTitle, "",
                        "", "", "", "0", "1",
                        picUrl, picCmd, picPage, picToUrl, showPlace, msgType, reward, btn_show, btnToPage,
                        btnCmd, needScroller, startTime, sendAll, userIds, 0, 0, jsonObject);

            } else {
                //如果是我自己绑定的玩家，则可以添加该用户为代理
                if (fatherProxyId.getUser_status() == 3 && fatherProxyId.getFatherproxy_id() == tokenAuth.getId()) {

                    logger.info("用户：" + fatherProxyId.getUsers_id() + " 是" + tokenAuth.getId() + "的绑定玩家，" +
                            "所有可以添加该用户为子代理");

                    gameUserDao.addProxy(6, tokenAuth.getId(), user_id, sysDate, 2);    //调用添加代理的接口
                } else {
                    throw new Exception("添加失败，请通知该玩家绑定您的邀请码后再试。");
                }
            }
        }


        //服务端返回数据
        AddProxyResp addProxyResp = new AddProxyResp();
        addProxyResp.setStatus(0);
        addProxyResp.setMsg("添加成功，用户（" + user_id + "）已经成为您的子代理");

        TestResp testResp = new TestResp();
        testResp.setData(addProxyResp);
        testResp.setCode(0);
        testResp.setErrMsg("");
        return testResp;
    }


    @ApiOperation(value = "玩家更改绑定和绑定", notes = "玩家更改绑定和绑定", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "changeBinding", method = RequestMethod.POST)
    public TestResp changeBinding(@RequestBody ChangeBindingReq changeBindingReq) throws Exception {
        logger.info("客户端请求数据（/changeBinding）：{}", changeBindingReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(changeBindingReq.getToken());
        int id = tokenAuth.getId();

        //邀请码
        int inviteCode = CommonUtil.isNumeric(changeBindingReq.getInviteCode());

        if (inviteCode <= 0) throw new Exception("绑定失败，邀请码输入不正确");

        //根据邀请码查询用户
        GameUserEntity queryInviteCode = gameUserDao.findByInviteCode(inviteCode);
        if (queryInviteCode == null) {
            throw new Exception("邀请码错误，请重新输入");
        } else if (queryInviteCode.getUser_status() == GameUserEntity.Player ||
                queryInviteCode.getUser_status() == GameUserEntity.Partner) {
            throw new Exception("邀请码错误，请重新输入");
        }

        //需要更换绑定的用户ID
        int user_id = queryInviteCode.getUsers_id();

        if (user_id == id) throw new Exception("不能自己绑定自己");

        String desc = "userId（" + id + "）绑定上级为userId（" + user_id + "）";

        //查询 绑定还是改绑 日志记录
        GameUserEntity userStatus = gameUserDao.findUserStatus(id);
        if (userStatus == null) {
            //建立关系
            ModelAndView modelView = getUserInfoService.buildRelationships(id);
        } else {
            if (userStatus.getUser_status() != GameUserEntity.Player) {
                throw new Exception("绑定失败，只有玩家才能绑定");
            }
        }

        GameUserEntity proxy = gameUserDao.findProxy(id);
        if (proxy != null) {
            desc = "userId（" + id + "）改绑上级为userId（" + user_id + "）";
        } else {

            //查询发送前钻石
            GameUserEntity receiveUserOldDiamond = gameUserDao.findDiamByUserId(user_id);
            int old_val = 0;
            if (receiveUserOldDiamond != null) old_val = receiveUserOldDiamond.getDiamond();

            //首次绑定送4钻
            bankService.addDiamond(1, id, 4, 1, "首次绑定送4钻", 311);

            logger.info("用户（" + id + "）首次绑定用户（" + user_id + "）送4钻石");


            //查询发送后钻石
            GameUserEntity receiveUserNewDiamond = gameUserDao.findDiamByUserId(id);
            int new_val = 0;
            if (receiveUserNewDiamond != null) new_val = receiveUserNewDiamond.getDiamond();


            //查询昵称
            GameUserEntity nickName = gameUserDao.findNickName(id);
            String nickname = "";
            if (nickName != null) nickname = nickName.getNickname();


            String content = "恭喜你获得首次绑定用户4钻石";
            JSONObject contentObject = new JSONObject();
            contentObject.put("userId", tokenAuth.getId());
            contentObject.put("title", "钻石发送");
            contentObject.put("content", content);
            contentObject.put("oldDiamond", old_val);
            contentObject.put("newDiamond", new_val);

            //调用消息推送接口  cmd为12时 为代理给玩家发送钻石消息
            msgPushService.newPushSysMsg(12, contentObject, id);

            //sendDiamondsDao.insertToSendDiamonds(tokenAuth.getId(), user_id, diamond);
        }

        logger.info(desc);

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = slf.format(new Date());

        gameUserDao.addProxy(6, user_id, id, now, 3);


        //更换绑定后的日志输出
        bindingRecordDao.insertToBindingRecord(id, user_id, desc);


        //查询昵称
        GameUserEntity nickName = gameUserDao.findNickName(user_id);
        String nickname = "";
        if (nickName != null) nickname = nickName.getNickname();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "恭喜成功绑定：" + nickname + "（" + user_id + "）");


        TestResp testResp = new TestResp();
        testResp.setData(jsonObject);
        testResp.setCode(0);

        logger.info("服务端返回数据：{}", testResp.toString());
        return testResp;
    }


    @ApiOperation(value = "代理钻石发送", notes = "总代理和代理钻石发送", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = InviteRecordResp.class)
    @ResponseBody
    @RequestMapping(value = "sendDiamonds", method = RequestMethod.POST)
    public TestResp SendDiamonds(@RequestBody SendDiamondReq sendDiamondReq) throws Exception {
        logger.info("客户端请求数据（/sendDiamonds）：{}", sendDiamondReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(sendDiamondReq.getToken());

        //收钻石用户的ID
        int user_id = sendDiamondReq.getUserId();
        //需要发送的钻石数量
        int diamond = CommonUtil.isNumeric(sendDiamondReq.getDiamond());

        if (user_id <= 0) throw new Exception("发送失败，收取钻石的用户ID不正确");
        if (diamond <= 0) throw new Exception("发送失败，钻石的数量不正确");

        //查询发钻者的身份
        GameUserEntity userInf = gameUserDao.findUserStatus(tokenAuth.getId());
        int userSt = 3;
        if (userInf != null) userSt = userInf.getUser_status();
        if (userSt != GameUserEntity.Two_Level_Agent && userSt != GameUserEntity.First_Level_Agent) {
            throw new Exception("发送失败，只有总代理和代理才能发送钻石");
        }


        //判断是否有该玩家，
        GameUserEntity userInfo = gameUserDao.findByUserId(user_id);
        if (userInfo == null) throw new Exception("发送失败，无此玩家");


        //只能发钻给玩家（不能绑定其他上级）或自己的下级
        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        if (userStatus != null) {
            int user_status = userStatus.getUser_status();

            //查询是否是自己的下级
            GameUserEntity gameUserEntity = gameUserDao.queryMyProxy(tokenAuth.getId(), user_id);

            if (gameUserEntity == null) {
                if (user_status == GameUserEntity.Player) {

                    //查询该玩家是否绑定了其他用户，
                    GameUserEntity fatherProxyId = gameUserDao.findFatherProxyId(user_id);
                    if (fatherProxyId != null) {
                        throw new Exception("只能给绑定自己的玩家或者绑定自己的代理发送钻石。");
                    }
                } else {
                    throw new Exception("只能给绑定自己的玩家或者绑定自己的代理发送钻石。");
                }
            }
        }

        //判断发送钻石的数量是否小于发送者的钻石数量
        UserBanksEntity sendUser = userBanksDao.queryTotalDiamond(tokenAuth.getId());
        int sendUserDiam = 0;
        if (sendUser != null) sendUserDiam = sendUser.getDiamond();

        if (sendUserDiam < diamond) {
            throw new Exception("发送的钻石数量大于自己已有的钻石数量");
        }

        //查询发送前钻石
        GameUserEntity receiveUserOldDiamond = gameUserDao.findDiamByUserId(user_id);
        int old_val = 0;
        if (receiveUserOldDiamond != null) old_val = receiveUserOldDiamond.getDiamond();


        //调用钻石发送接口
        bankService.addDiamond(tokenAuth.getId(), user_id, diamond, 1, "代理发送钻石", 300);

        logger.info("用户（" + tokenAuth.getId() + "）发送（" + diamond + "）钻石 给 用户（" + user_id + "）入库");

        sendDiamondsDao.insertToSendDiamonds(tokenAuth.getId(), user_id, diamond);

        //查询发送后钻石
        GameUserEntity receiveUserNewDiamond = gameUserDao.findDiamByUserId(user_id);
        int new_val = 0;
        if (receiveUserNewDiamond != null) new_val = receiveUserNewDiamond.getDiamond();


        //查询昵称
        GameUserEntity nickName = gameUserDao.findNickName(tokenAuth.getId());
        String nickname = "";
        if (nickName != null) nickname = nickName.getNickname();


        /**
         * 钻石发送成功后消息推送
         */
        //--------------------------------------------参数处理-------------------------

        boolean immediateness = true;      //是否立即发送
        boolean needExit = false;       //是否需要退出 ,
        String picUrl = "";    //图片地址
        int picCmd = 1;
        int picPage = 1;
        String picToUrl = "";
        int showPlace = 0;
        int msgType = 1;    //消息类型  1：文本消息；2：纯图消息；3：图文消息
        int reward = 0; //奖励；0：无奖励, 其他奖励数量
        boolean btn_show = false;       //是否显示按钮
        int btnToPage = 1;
        int btnCmd = 0;
        boolean needScroller = true;
        boolean expired = false;    //是否过期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(new Date());
        Date startTime = formatter.parse(format);
        int rewardType = 1;
        boolean sendAll = false;
        boolean needSave = true;       //是否保存到消息列表
        int showAgain = 1;


        JSONObject jsonObject = new JSONObject();
        List userIds = new ArrayList();
        userIds.add(user_id);


        String contentAndTitle = nickname + "（ID：" + tokenAuth.getId() + "）向您发送了" + diamond + "钻，已成功收取并加入您的钻石总数之中！";

        //结束时间处理
        long end = startTime.getTime() + 3 * 24 * 60 * 60 * 1000 * 1L;
        Date end_date = new Date(end);

        SystemMsgsEntity systemMsgsEntity = new SystemMsgsEntity();
        systemMsgsEntity.setTitle(contentAndTitle);
        systemMsgsEntity.setContent(contentAndTitle);
        systemMsgsEntity.setImmediateness(immediateness);
        systemMsgsEntity.setNeed_exit(needExit);
        systemMsgsEntity.setBtn_show(0);            //是否显示按钮
        systemMsgsEntity.setBtn_to_url("");
        systemMsgsEntity.setBtn_to_page(btnToPage);
        systemMsgsEntity.setBtn_cmd(btnCmd);
        systemMsgsEntity.setShow_place(showPlace);
        systemMsgsEntity.setMsg_type(msgType);
        systemMsgsEntity.setPic_url("");
        systemMsgsEntity.setPic_cmd(picCmd);
        systemMsgsEntity.setPic_to_page(picPage);
        systemMsgsEntity.setPic_to_url(picToUrl);
        systemMsgsEntity.setReward(reward);
        systemMsgsEntity.setBtn_title("");
        systemMsgsEntity.setStart_time(startTime);
        systemMsgsEntity.setEnd_time(end_date);
        systemMsgsEntity.setVersion("");
        systemMsgsEntity.setNeed_scroller(needScroller);
        systemMsgsEntity.setPlatfrom(0);
        systemMsgsEntity.setCmd(5);
        systemMsgsEntity.setSend_all(sendAll);
        systemMsgsEntity.setRaw_content("");
        systemMsgsEntity.setNeed_save(needSave);
        systemMsgsEntity.setShow_again(showAgain);
        systemMsgsEntity.setShow_times(3);

        logger.info("消息参数：{}", systemMsgsEntity.toString());

        //将数据写入数据库
        systemMsgsDao.insertToSysMsg(systemMsgsEntity);

        logger.info("获取新增消息数据的ID：{}", systemMsgsEntity.getId());


        int msgId = systemMsgsEntity.getId();      //消息ID

        SystemMsgRecordsEntity api = new SystemMsgRecordsEntity();
        systemMsgRecordsDao.insertToMsgRecords(user_id, msgId, 0, api);


                /*--------------------------------------------参数处理-----------------------*/


        msgSendingService.diposeMsg(contentAndTitle, contentAndTitle, "",
                "", "", "", "0", "1",
                picUrl, picCmd, picPage, picToUrl, showPlace, msgType, reward, btn_show, btnToPage,
                btnCmd, needScroller, startTime, sendAll, userIds, 0, 0, jsonObject);











        /*String content = "用户 " + nickname + "（" + tokenAuth.getId() + "） 给你发了" + diamond + "钻石！";
        JSONObject contentObject = new JSONObject();
        contentObject.put("userId", tokenAuth.getId());
        contentObject.put("title", "钻石发送");
        contentObject.put("content", content);
        contentObject.put("oldDiamond", old_val);
        contentObject.put("newDiamond", new_val);

        //调用消息推送接口  cmd为12时 为代理给玩家发送钻石消息
        msgPushService.newPushSysMsg(12, contentObject, user_id);
*/

        JSONObject json = new JSONObject();
        json.put("msg", "你成功向用户（" + user_id + "）发送了 " + diamond + "钻");


        TestResp testResp = new TestResp();
        testResp.setData(json);
        testResp.setCode(0);

        logger.info("服务端返回数据（/sendDiamonds）：{}", testResp.toString());
        return testResp;
    }


    @ApiOperation(value = "钻石发送记录", notes = "钻石发送记录", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = SendRecordResp.class)
    @ResponseBody
    @RequestMapping(value = "sendRecords", method = RequestMethod.POST)
    public TestResp sendDiamondRecord(@RequestBody SendDiamondRecordReq apiRequest) throws Exception {
        logger.info("客户端请求数据（/sendRecords）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        int user_id = tokenAuth.getId();
        int limit = apiRequest.getLimit();  //客户端请求一次获取多少条数据
        int page = apiRequest.getPage();    //客户端请求当前页数

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;
        int offset = page * limit;
        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();

        //根据user_id查询发送钻石的记录
        List<SendDiamondsEntity> sendDiamondsEntities = sendDiamondsDao.querySendRecord(user_id, offset, limit);
        if (sendDiamondsEntities.size() != 0) {

            for (SendDiamondsEntity sendDiamondsEntity : sendDiamondsEntities) {

                //内容体用于返回给客户端的数据格式
                SendRecordResp sendRecordResp = new SendRecordResp();

                int receive_user_id = sendDiamondsEntity.getReceive_user_id();

                GameUserEntity userInfo = gameUserDao.findUserStatus(receive_user_id);
                int user_status = 3;
                if (userInfo != null) user_status = userInfo.getUser_status();

                String userStatus = "玩家";
                if (user_status == GameUserEntity.Partner) {
                    userStatus = "合伙人";
                } else if (user_status == GameUserEntity.First_Level_Agent) {
                    userStatus = "总代理";
                } else if (user_status == GameUserEntity.Two_Level_Agent) {
                    userStatus = "代理";
                }

                //查昵称
                GameUserEntity nickName = gameUserDao.findNickName(receive_user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();

                //时间处理
                String created_time = sendDiamondsEntity.getCreated_time();
                created_time = created_time.substring(0, created_time.length() - 5);


                sendRecordResp.setUserId(receive_user_id);
                sendRecordResp.setUserStatus(userStatus);
                sendRecordResp.setNickname(nickname);
                sendRecordResp.setSendTime(created_time);
                sendRecordResp.setDiamond(sendDiamondsEntity.getDiamond());
                list.add(sendRecordResp);
            }

        }

        TestResp testResp = new TestResp();
        testResp.setData(list);
        testResp.setCode(0);

        logger.info("服务端返回数据：{}", testResp.toString());
        return testResp;
    }


    @ApiOperation(value = "可用积分、审批完成的积分", notes = "总可用积分、审批完成的积分", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = IntegralDetailsResp.class)
    @ResponseBody
    @RequestMapping(value = "integralDetails", method = RequestMethod.POST)
    public TestResp integralDetails(@RequestBody ApiRequest apiRequest) throws Exception {

        logger.info("客户端请求数据（/integralDetails）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        int user_id = tokenAuth.getId();

        logger.info("进入我的总积分（user/api/integralDetails） user_id：{}", user_id);

        GameUserEntity integral = gameUserDao.findIntegralByUserId(user_id);

        //总可用积分
        double totalIntegral = 0;
        if (integral != null) totalIntegral = integral.getIntegral() * 1.0 / 100;


        //审批通过的积分
        double withdrawalsIntegral = 0;

        WithdrawsEntity withdrawsEntity = withdrawsDao.queryAuditPass(user_id, WithdrawsEntity.Audit_Pass);
        if (withdrawsEntity != null) {
            withdrawalsIntegral = withdrawsEntity.getScore() * 1.0 / 100;
        }

        IntegralDetailsResp integralDetailsResp = new IntegralDetailsResp();
        integralDetailsResp.setTotalIntegral(totalIntegral);
        integralDetailsResp.setWithdrawalsIntegral(withdrawalsIntegral);


        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(integralDetailsResp);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/integralDetails）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "积分记录", notes = "积分返利记录", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = IntegralRecordsResp.class)
    @ResponseBody
    @RequestMapping(value = "integralRecords", method = RequestMethod.POST)
    public TestResp integralRecords(@RequestBody SendDiamondRecordReq apiRequest) throws Exception {

        logger.info("客户端请求数据（/integralRecords）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        int user_id = tokenAuth.getId();
        int limit = apiRequest.getLimit();  //客户端请求一次获取多少条数据
        int page = apiRequest.getPage();    //客户端请求当前页数

        logger.info("进入我的总积分（user/api/integralRecords） user_id：{}", user_id);

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;
        int offset = page * limit;
        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();

        List<IntegralRebateEntity> integralRebateEntities = integralRebateDao.queryRebateByUserId(user_id, offset, limit);
        if (integralRebateEntities.size() != 0) {
            for (IntegralRebateEntity integralRebateEntity : integralRebateEntities) {

                //参数组装
                IntegralRecordsResp integralRecordsResp = new IntegralRecordsResp();

                int child_user_id = integralRebateEntity.getChild_user_id();
                String nickname = "";
                if (child_user_id == 1) {
                    //参数组装
                    nickname = "系统";
                    child_user_id = 0;
                } else {
                    //查询昵称
                    GameUserEntity nickName = gameUserDao.findNickName(child_user_id);
                    if (nickName != null) nickname = nickName.getNickname();
                }

                //时间处理
                String created_time = integralRebateEntity.getCreated_time();
                created_time = created_time.substring(0, created_time.length() - 5);

                //积分、充值钱数处理
                double rechargeMoney = 0;
                double rebateIntegral = 0;
                int recharge_money = integralRebateEntity.getRecharge_money();
                int rebate_integral = integralRebateEntity.getRebate_number();
                rechargeMoney = recharge_money * 1.0 / 100;
                rebateIntegral = rebate_integral * 1.0 / 100;


                integralRecordsResp.setNickname(nickname);
                integralRecordsResp.setUserId(child_user_id);
                integralRecordsResp.setRechargeMoney(rechargeMoney);
                integralRecordsResp.setRebateIntegral(rebateIntegral);
                integralRecordsResp.setCreatedTime(created_time);

                list.add(integralRecordsResp);
            }
        }


        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(list);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/integralRecords）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "提现记录", notes = "提现记录", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = WithdrawsRecordsResp.class)
    @ResponseBody
    @RequestMapping(value = "withdrawalsRecord", method = RequestMethod.POST)
    public TestResp withdrawalsRecord(@RequestBody SendDiamondRecordReq apiRequest) throws Exception {

        logger.info("客户端请求数据（/withdrawalsRecord）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        int user_id = tokenAuth.getId();
        int limit = apiRequest.getLimit();  //客户端请求一次获取多少条数据
        int page = apiRequest.getPage();    //客户端请求当前页数

        logger.info("进入我的总积分（user/api/withdrawalsRecord） user_id：{}", user_id);

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;
        int offset = page * limit;
        logger.info("limit值：" + limit + "   page值：" + offset);

        List<WithdrawsRecordsResp> list = new ArrayList();

        /**
         * 提现记录
         */
        List<WithdrawsEntity> withdrawsEntities = withdrawsDao.findByUserId(user_id, offset, limit);
        if (withdrawsEntities.size() != 0) {
            for (WithdrawsEntity withdrawsEntity : withdrawsEntities) {

                //参数组装
                WithdrawsRecordsResp withdrawsRecordsResp = new WithdrawsRecordsResp();

                String desc = "";

                //审核状态
                int audit = withdrawsEntity.getAudit();
                //提款状态
                int pay_status = withdrawsEntity.getPay_status();

                if (audit == 1) {
                    desc = "审核中";
                } else if (audit == 2 && pay_status == 0) {
                    desc = "可提现";
                } else if (audit == 2 && pay_status == 1) {
                    desc = "已提现";
                } else if (audit == 3) {
                    desc = "审核拒绝";
                } else if (audit == 2 && pay_status == 2) {
                    desc = "提现失败";
                }


                //提款积分
                double withdrawalsMoney = 0;
                int score = withdrawsEntity.getScore();
                withdrawalsMoney = score * 1.0 / 100;

                //时间处理
                Date created_at = withdrawsEntity.getCreated_at();
                SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String createTime = slf.format(created_at);


                withdrawsRecordsResp.setDesc(desc);
                withdrawsRecordsResp.setCreateTime(createTime);
                withdrawsRecordsResp.setWithdrawalsMoney(withdrawalsMoney);


                list.add(withdrawsRecordsResp);
            }
        }

        /**
         * 积分换钻记录
         */
        List<ExchangeDiamondsEntity> queryExchangeRecords = exchangeDiamondsDao.findByUserId(user_id);
        if (queryExchangeRecords.size() != 0) {
            for (ExchangeDiamondsEntity queryExchangeRecord : queryExchangeRecords) {
                //参数组装
                WithdrawsRecordsResp withdrawsRecordsResp = new WithdrawsRecordsResp();

                //换钻用的积分数量
                int consume_integral = queryExchangeRecord.getConsume_integral();

                //积分换钻时间 处理
                String created_time = queryExchangeRecord.getCreated_time();
                created_time = created_time.substring(0, created_time.length() - 5);


                withdrawsRecordsResp.setWithdrawalsMoney(consume_integral * 1.0 / 100);
                withdrawsRecordsResp.setDesc("积分换钻");
                withdrawsRecordsResp.setCreateTime(created_time);
                list.add(withdrawsRecordsResp);
            }
        }


        //list中 根据 实体类中的日期进行排序
        CommonUtil.listSort(list);


        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(list);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/integralRecords）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "积分换钻石商品列表", notes = "积分换钻石商品列表", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = IntegralProductsResp.class)
    @ResponseBody
    @RequestMapping(value = "getIntegralProducts", method = RequestMethod.POST)
    public TestResp getIntegralProducts(@RequestBody ApiRequest apiRequest) throws Exception {

        logger.info("客户端请求数据（/getIntegralProducts）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        int user_id = tokenAuth.getId();

        logger.info("进入积分换钻石商品列表（user/api/getIntegralProducts） user_id：{}", user_id);

        List list = new ArrayList();

        //根据user_id 查询 是否绑定上级
        GameUserEntity querySuperior = gameUserDao.findFatherProxyId(user_id);

        Boolean flag = false;
        if (querySuperior != null) flag = true;

        List<IntegralProductEntity> integralProductEntities = integralProductDao.queryAllProduct(IntegralProductEntity.PlayerType);
        if (flag) {
            int user_status = querySuperior.getUser_status();
            if (user_status == GameUserEntity.Player) {
                integralProductEntities = integralProductDao.queryAllProduct(IntegralProductEntity.BindingPlayerType);
            } else if (user_status == GameUserEntity.Two_Level_Agent) {
                integralProductEntities = integralProductDao.queryAllProduct(IntegralProductEntity.AgentType);
            } else if (user_status == GameUserEntity.First_Level_Agent) {
                integralProductEntities = integralProductDao.queryAllProduct(IntegralProductEntity.GeneralAgentType);
            }
        }


        for (IntegralProductEntity integralProductEntity : integralProductEntities) {
            //参数组装
            IntegralProductsResp integralProductsResp = new IntegralProductsResp();

            double money = integralProductEntity.getMoney() * 1.0 / 100;
            int diamond = integralProductEntity.getDiamond();
            String img_url = integralProductEntity.getImg_url();
            int id = integralProductEntity.getId();

            integralProductsResp.setDiamond(diamond);
            integralProductsResp.setImgUrl(img_url);
            integralProductsResp.setMoney(money);
            integralProductsResp.setId(id);

            list.add(integralProductsResp);
        }


        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(list);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/getIntegralProducts）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "积分换钻石", notes = "积分换钻石", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = IntegralProductsResp.class)
    @ResponseBody
    @RequestMapping(value = "exchangeDiamonds", method = RequestMethod.POST)
    public TestResp exchangeDiamonds(@RequestBody ExchangeDiamondsReq exchangeDiamondsReq) throws Exception {

        logger.info("客户端请求数据（/exchangeDiamonds）：{}", exchangeDiamondsReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(exchangeDiamondsReq.getToken());

        //需要兑换商品的用户ID
        int user_id = tokenAuth.getId();

        //需要兑换的商品ID
        int id = exchangeDiamondsReq.getId();
        if (id <= 0) throw new Exception("兑换失败，无效的商品");

        logger.info("进入积分换钻石（user/api/exchangeDiamonds） user_id：{}", user_id);


        //只有 总代理、代理 才能够积分换钻石
        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        Boolean flag = true;
        if (userStatus != null) {
            if (userStatus.getUser_status() == GameUserEntity.First_Level_Agent ||
                    userStatus.getUser_status() == GameUserEntity.Two_Level_Agent) {
                flag = false;
            }
        }
        if (flag) throw new Exception("兑换失败，只有总代理和代理才能积分换钻石");


        //查询商品是否存在
        IntegralProductEntity integralProductEntity = integralProductDao.queryById(id);
        int diamond = 0;
        int integral = 0;
        if (integralProductEntity != null) {
            diamond = integralProductEntity.getDiamond();
            integral = integralProductEntity.getMoney();
        } else {
            throw new Exception("兑换失败，无效的商品");
        }

        String purpose = "用户（" + user_id + "）用 （" + integral + "）积分兑换（" + diamond + "）钻石";

        logger.info(purpose);


        //查询 兑换者的可用积分 是否 大于等于 当前兑换所需的积分
        int totalIntegral = userStatus.getIntegral();
        if (totalIntegral < integral) {
            throw new Exception("兑换失败，您当前积分不足");
        }


        //扣除前总积分
        int old_val = totalIntegral;

        //扣除相应积分
        gameUserDao.deductionIntegral(integral, user_id);

        GameUserEntity newIntegral = gameUserDao.findIntegralByUserId(user_id);
        int new_val = 0;
        if (newIntegral != null) new_val = newIntegral.getIntegral();


        //记录积分操作
        integralsDao.insertToIntegrals(user_id, old_val, new_val, integral, 0, purpose);


        //积分兑换钻石接口调用  （306：用户积分换钻）
        bankService.addDiamond(1, user_id, diamond, 1, "用户积分换钻", 306);


        //积分兑换钻石记录入库
        exchangeDiamondsDao.insertToExchangeDiamond(user_id, id, integral, diamond);


        /**
         * 查询该充值的用户是玩家还是绑定用户，如果是绑定用户，则给上级  <积分返利>
         *  玩家充值不返利
         *  如果是绑定玩家充值，则给二级返利（50%），给一级返利（20%）
         *  如果是二级充值，则给自己返利（50%），给一级返利（20%）
         *  如果是一级充值，则给自己返利（70%）
         */
        GameUserEntity querySuperior = gameUserDao.findFatherProxyId(user_id);
        if (querySuperior != null) {
            int user_status = querySuperior.getUser_status();

            //得到上级代理id  （返利积分给上级代理用户）
            int superior_id = querySuperior.getFatherproxy_id();

            if (user_status == GameUserEntity.Two_Level_Agent) {

                //返利给自己
                rechargeService.integralRebate(diamond, user_id, integral, user_id, RebateRatioEntity.TwoRatio);

                //查询上级（代理的上级：总代理）
                GameUserEntity firstAgent = gameUserDao.findFatherProxyId(user_id);
                if (firstAgent != null && firstAgent.getFatherproxy_id() != 1) {
                    //返积分给总代理
                    rechargeService.integralRebate(diamond, user_id, integral, firstAgent.getFatherproxy_id(), RebateRatioEntity.FirstRatio);
                }

            } else if (user_status == GameUserEntity.First_Level_Agent) {

                //返利给自己
                rechargeService.integralRebate(diamond, user_id, integral, user_id, RebateRatioEntity.FirstSelfRatio);
            }
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "兑换成功， " + diamond + "钻石已到账，请注意查收。");

        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(jsonObject);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/exchangeDiamonds）服务端返回数据：{}", testResp.toString());

        return testResp;
    }

    @ApiOperation(value = "积分提取现金", notes = "积分提取现金", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "withdrawsCash", method = RequestMethod.POST)
    public Ajax withdrawsCash(@RequestBody ApiRequest apiRequest, HttpServletRequest request) throws Exception {
        logger.info("客户端请求数据（/withdrawsCash）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());
        int user_id = tokenAuth.getId();


        int pay_status = WithdrawsEntity.PayStatus_Fail;
        String desc = "";

        List<WithdrawsEntity> withdrawsEntities = withdrawsDao.queryAuditPassByUserId(user_id, WithdrawsEntity.Audit_Pass);
        if (withdrawsEntities.size() != 0) {

            for (WithdrawsEntity withdrawsEntity : withdrawsEntities) {
                try {
                    //调用打款接口
                    String ip = HttpReqUtil.getRemortIP(request);
                    Map result = payService.transferPay(withdrawsEntity.getOpenid(), withdrawsEntity.getAmount(), "积分提现", withdrawsEntity.getTrade_no(), ip);
                    Object state = result.get("state");
                    Object err_code = result.get("err_code");// 获取错误代码
                    String err_code_des = (String) result.get("err_code_des");// 获取错误描述

                    logger.info("result：{}", result);
                    logger.info("state: {}", state);
                    logger.info("err_code: {}", err_code);
                    logger.info("err_code_des: {}", err_code_des);

                    if ("SUCCESS".equals(state)) {
                        logger.info("打款成功");
                        pay_status = WithdrawsEntity.PayStatus_Succ;
                    } else {
                        logger.info("打款错误信息：{}", result);
                        desc = err_code_des != null ? err_code_des : "";
                    }
                } catch (Exception e) {
                    logger.error("微信打款接口错误信息：{}", e);
                    desc = e.getMessage().substring(0, 200);
                }


                withdrawsDao.updateWithdraws(withdrawsEntity.getId(), withdrawsEntity.getTrade_no(), desc, pay_status);

                if (pay_status != 1) {
                    throw new Exception("付款失败: " + desc);
                }
            }
        } else {
            throw new Exception("订单号不存在");
        }

        logger.info("打款成功");

        return new Ajax(0, "打款成功");
    }


    @ApiOperation(value = "充值详情", notes = "充值详情列表（包括自己充值和所有下级充值的记录）", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = RechargeDetailsResp.class)
    @ResponseBody
    @RequestMapping(value = "rechargeDetails", method = RequestMethod.POST)
    public TestResp withdrawalsRecordes(@RequestBody RechargeDetailsReq apiRequest) throws Exception {

        logger.info("客户端请求数据（/rechargeDetails）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        int user_id = tokenAuth.getId();
        int limit = apiRequest.getLimit();  //客户端请求一次获取多少条数据
        int page = apiRequest.getPage();    //客户端请求当前页数
        //int days = apiRequest.getNumberOfDays();    //显示最近多少天的数据

        logger.info("进入充值详情（user/api/rechargeDetails） user_id：{}", user_id);


        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;
        int offset = page * limit;
        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();
       /*
        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
        String end_time = slf.format(new Date());
        Date endTime = slf.parse(end_time);

        long start = endTime.getTime() - (days-1) * 24 * 60 * 60 * 1000 * 1L;
        Date startTime = new Date(start);
        String start_time = slf.format(startTime);

        start_time = start_time + " 00:00:00";
        end_time = end_time + " 23:59:59";

        logger.info("开始时间：{}", start_time);
        logger.info("结束时间：{}", end_time);*/


        String allSubAgents = getUserInfoService.getStringAllSubAgent(user_id);
        logger.info("user_id（" + user_id + "）的所有下级：{}", allSubAgents);


        List<WechatRechargeEntity> wechatRechargeEntities = wechatRechargeDao.queryPaymentUser(allSubAgents, offset, limit);
        if (wechatRechargeEntities.size() != 0) {
            for (WechatRechargeEntity wechatRechargeEntity : wechatRechargeEntities) {

                int payment_user_id = wechatRechargeEntity.getUser_id();

                //查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(payment_user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();

                //时间处理
                String created_date = wechatRechargeEntity.getCreated_date();
                created_date = created_date.substring(0, created_date.length() - 5);


                //参数封装
                RechargeDetailsResp rechargeDetails = new RechargeDetailsResp();
                rechargeDetails.setNickname(nickname);
                rechargeDetails.setUserId(payment_user_id);
                rechargeDetails.setRechargeTime(created_date);
                rechargeDetails.setRechargeNum(wechatRechargeEntity.getRecharge_money() * 1.0 / 100);

                list.add(rechargeDetails);
            }
        }

        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(list);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/integralRecords）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "我的数据", notes = "我的数据", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = MyDataResp.class)
    @ResponseBody
    @RequestMapping(value = "myData", method = RequestMethod.POST)
    public TestResp myData(@RequestBody ApiRequest apiRequest) throws Exception {

        logger.info("客户端请求数据（/myData）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        int user_id = tokenAuth.getId();

        logger.info("进入我的数据（user/api/myData） user_id：{}", user_id);

        MyDataResp myDataResp = new MyDataResp();
        List list = new ArrayList();


        //先查询请求用户的身份
        GameUserEntity userStatus = gameUserDao.queryUserStatus(user_id);
        if (userStatus != null) {

            //用户身份
            int user_status = userStatus.getUser_status();

            if (user_status == GameUserEntity.Partner) {
                /**
                 * 下级代理（总人数） subAgentTotalNum
                 * 我的总代理总人数  firstAgentNum
                 * 我的代理总人数  twoAgentNum
                 */
                int firstAgentNum = gameUserDao.querySuperAgentTotal(user_id);

                int twoAgentNum = gameUserDao.querySubAgentTotal(user_id);

                int subAgentTotalNum = firstAgentNum + twoAgentNum;

                //参数封装
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", "我的代理（总人数）");
                jsonObject.put("value", subAgentTotalNum);
                list.add(jsonObject);

                JSONObject jsonObjectOne = new JSONObject();
                jsonObjectOne.put("name", "我的总代理总人数");
                jsonObjectOne.put("value", firstAgentNum);
                list.add(jsonObjectOne);

                JSONObject jsonObjectTwo = new JSONObject();
                jsonObjectTwo.put("name", "我的代理总人数");
                jsonObjectTwo.put("value", twoAgentNum);
                list.add(jsonObjectTwo);


                //myDataResp.setPartnerData(partnerData);
            } else if (user_status == GameUserEntity.First_Level_Agent) {
                /**
                 * 我的下级代理总人数  subAgentTotalNum
                 * 我的绑定玩家总人数  bindingPlayerNun
                 */
                int subAgentTotalNum = 0;
                int bindingPlayerNun = 0;

                //FirstAgentData firstAgentData = new FirstAgentData();

                //查询我的下级代理
                List<GameUserEntity> gameUserEntities = gameUserDao.allSubAgent(user_id);
                if (gameUserEntities.size() != 0) {

                    for (GameUserEntity gameUserEntity : gameUserEntities) {

                        int users_id = gameUserEntity.getUsers_id();

                        if (gameUserEntity.getUser_status() == GameUserEntity.Two_Level_Agent) {
                            subAgentTotalNum++;
                        } else if (gameUserEntity.getUser_status() == GameUserEntity.Player) {
                            bindingPlayerNun++;
                        }

                        //根据id查询子代理的子代理（防止测试数据（玩家有子代理）所以，玩家不统计有下级）
                        if (gameUserEntity.getUser_status() != GameUserEntity.Player) {
                            List<GameUserEntity> totalSubAgent = gameUserDao.allSubAgent(users_id);
                            if (totalSubAgent.size() != 0) {
                                for (GameUserEntity userEntity : totalSubAgent) {
                                    if (userEntity.getUser_status() == 2) {
                                        subAgentTotalNum++;
                                    } else if (userEntity.getUser_status() == 3) {
                                        bindingPlayerNun++;
                                    }
                                }
                            }
                        }

                    }
                }

                //参数封装
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", "我的下级代理总人数");
                jsonObject.put("value", subAgentTotalNum);
                list.add(jsonObject);

                JSONObject jsonObjectOne = new JSONObject();
                jsonObjectOne.put("name", "我的绑定玩家总人数");
                jsonObjectOne.put("value", bindingPlayerNun);
                list.add(jsonObjectOne);

            } else if (user_status == GameUserEntity.Two_Level_Agent) {
                /**
                 *我的绑定玩家总人数  bindingPlayerNun
                 */
                //TwoAgentData twoAgentData = new TwoAgentData();

                int bindingPlayerNun = gameUserDao.queryBindingUser(user_id);

                //参数封装
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", "我的绑定玩家总人数");
                jsonObject.put("value", bindingPlayerNun);
                list.add(jsonObject);
            } else {
                throw new Exception("查询失败，没有权限获取信息");
            }

        } else {
            throw new Exception("查询失败，玩家没有权限获取信息");
        }


        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(list);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/myData）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "数据详情", notes = "数据详情", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = DataDetailsResp.class)
    @ResponseBody
    @RequestMapping(value = "dataDetails", method = RequestMethod.POST)
    public TestResp dataDetails(@RequestBody SendDiamondRecordReq apiRequest) throws Exception {
        logger.info("客户端请求数据（/dataDetails）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        long begin = System.currentTimeMillis();   //用于统计所耗时间

        int user_id = tokenAuth.getId();
        int limit = apiRequest.getLimit();  //客户端请求一次获取多少条数据
        int page = apiRequest.getPage();    //客户端请求当前页数

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;
        int offset = page * limit;
        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
        String end_time = slf.format(new Date());
        Date endTime = slf.parse(end_time);


        List<GameUserEntity> subAgentList = new ArrayList();
        //先查询请求者 的身份
        GameUserEntity userStatus = gameUserDao.queryUserStatus(user_id);
        if (userStatus != null) {
            if (userStatus.getUser_status() == GameUserEntity.Partner) {
                subAgentList = gameUserDao.queryPartnerSubordinate(user_id);
            } else {
                //查询 user_id 的所有子代理ID（包括子代理的子代理）
                subAgentList = gameUserDao.queryAllSubordinate(user_id);
            }
        } else {
            throw new Exception("玩家无权限查看");
        }


        //User_id 的下级 （包括自己ID）
        String allSubAgent = user_id + ",";
        for (GameUserEntity userEntity : subAgentList) {
            allSubAgent = allSubAgent + userEntity.getUsers_id() + ",";
        }
        allSubAgent = allSubAgent.substring(0, allSubAgent.length() - 1);
        logger.info("UserID（" + user_id + "）的所有下级用户：{}", allSubAgent);
        String[] splitSubAgent = allSubAgent.split(",");


        for (int i = 0; i < 3; i++) {
            long date = endTime.getTime() - i * 24 * 60 * 60 * 1000 * 1L;
            Date start_date = new Date(date);

            String dateTime = slf.format(start_date);
            String startDate = dateTime + " 00:00:00";
            String endDate = dateTime + " 23:59:59";

            int rechargeNum = 0; //充值金额
            int activeNum = 0;  //活跃用户
            int gamesNum = 0;  //开房次数
            int rechargeDiam = 0;  //充钻数量


            /**
             * 根据时间查询充值金额（包含自己的充值）
             */
            WechatRechargeEntity wechatRechargeEntity = wechatRechargeDao.queryMySubRecharge(startDate, endDate, allSubAgent);
            if (wechatRechargeEntity != null) {
                rechargeNum = wechatRechargeEntity.getRecharge_money();
                rechargeDiam = wechatRechargeEntity.getRecharge_number();
            }

            /**
             * 根据时间查询活跃人数
             */
            activeNum = realtimeOnlineHistoriesDao.queryMyActiveUserCount(startDate, endDate, allSubAgent);


            //根据时间查询开房次数    俱乐部和私房 开房 分开统计
            Map map = getUserInfoService.getGamesNum(splitSubAgent, startDate, endDate);
            gamesNum = (int) map.get("gameConsumeDiam") / 4;


            //参数封装
            DataDetailsResp dataDetailsResp = new DataDetailsResp();
            dataDetailsResp.setDate(dateTime);
            dataDetailsResp.setRechargeNum(rechargeNum * 1.0 / 100);
            dataDetailsResp.setActiveNum(activeNum);
            dataDetailsResp.setGamesNum(gamesNum);
            dataDetailsResp.setRechargeDiam(rechargeDiam);

            list.add(dataDetailsResp);
        }

        //日志输出：
        long end = System.currentTimeMillis();
        logger.info("数据详情请求接口，共耗时：[" + (end - begin) + "]毫秒");


        TestResp testResp = new TestResp();
        testResp.setData(list);
        testResp.setCode(0);

        logger.info("服务端返回数据（/dataDetails）：{}", testResp.toString());
        return testResp;
    }


    @ApiOperation(value = "获取邀请码", notes = "获取邀请码", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = GetInviteCodeResp.class)
    @ResponseBody
    @RequestMapping(value = "getInviteCode", method = RequestMethod.POST)
    public TestResp getInviteCode(@RequestBody ApiRequest apiRequest) throws Exception {
        logger.info("客户端请求数据（/getInviteCode）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        int user_id = tokenAuth.getId();

        //内容体用于返回给客户端的数据格式
        GetInviteCodeResp inviteCodeResp = new GetInviteCodeResp();
        Invite invite = new Invite();


        /**
         * 邀请码获取
         */

        //根据user_id查询身份
        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        int user_status = 3;
        if (userStatus != null) {
            user_status = userStatus.getUser_status();

            //邀请码
            int invite_code = 0;
            if (user_status == GameUserEntity.Two_Level_Agent || user_status == GameUserEntity.First_Level_Agent) {

                invite_code = userStatus.getInvite_code();

                if (invite_code == 0) {

                    //随机获取邀请码
                    invite_code = getUserInfoService.getInviteCode();

                    //将邀请码新增到表中
                    gameUserDao.updateInviteCode(invite_code, user_id);
                }

                //代理邀请码
                invite.setInviteCode(invite_code);
                invite.setStatus(1);
            } else if (user_status == GameUserEntity.Player) {
                //无邀请码
                invite.setInviteCode(0);
                invite.setStatus(3);

                //查询玩家绑定的邀请码  (可能为0)
                GameUserEntity queryProxy = gameUserDao.findProxy(user_id);
                if (queryProxy != null && queryProxy.getFatherproxy_id() != 0) {
                    int fatherproxy_id = queryProxy.getFatherproxy_id();

                    GameUserEntity userInfo = gameUserDao.findUserStatus(fatherproxy_id);
                    int inviteCode = userInfo.getInvite_code();
                    if (inviteCode > 0) {
                        //玩家绑定的邀请码
                        invite.setInviteCode(inviteCode);
                        invite.setStatus(2);
                    }
                }
            } else if (user_status == GameUserEntity.Partner) {
                //无邀请码
                invite.setInviteCode(0);
                invite.setStatus(3);
            }
        } else {
            //无邀请码
            invite.setInviteCode(0);
            invite.setStatus(3);
        }


        inviteCodeResp.setInvite(invite);
        inviteCodeResp.setUserStatus(user_status);

        TestResp testResp = new TestResp();
        testResp.setData(inviteCodeResp);
        testResp.setCode(0);

        logger.info("服务端返回数据（/getInviteCode）：{}", testResp.toString());
        return testResp;
    }


    @ApiOperation(value = "申请代理和问题反馈", notes = "申请代理和问题反馈", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = IntegralWithdrawalsRespBody.class)
    @ResponseBody
    @RequestMapping(value = "feedbackInfo", method = RequestMethod.POST)
    public TestResp feedbackInfo(@RequestBody ApplyForAgentReq applyForAgentReq) throws Exception {

        logger.info("客户端请求数据（/feedbackInfo）：{}", applyForAgentReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(applyForAgentReq.getToken());
        int user_id = tokenAuth.getId();

        String phone = applyForAgentReq.getPhone();
        String desc = applyForAgentReq.getNameOrQuestion();
        int status = applyForAgentReq.getStatus();

        if (phone.equals("") || desc.equals("")) throw new Exception("请完整填写信息");


        IntegralWithdrawalsRespBody applyAgentResp = new IntegralWithdrawalsRespBody();


        //1：申请代理、2：问题反馈
        if (status == FeedbackInfoEntity.ApplyAgentType) {

            feedbackInfoDao.insertToFeedbackInfo(user_id, desc, phone, FeedbackInfoEntity.ApplyAgentType);
            applyAgentResp.setMsg("提交成功！请保持电话畅通，我们将尽快与您取得联系！");
        } else if (status == FeedbackInfoEntity.ProblemFeedbackType) {

            feedbackInfoDao.insertToFeedbackInfo(user_id, desc, phone, FeedbackInfoEntity.ProblemFeedbackType);
            applyAgentResp.setMsg("提交成功！感谢您的反馈！");
        } else {

            applyAgentResp.setMsg("状态码错误，提交失败！");
        }


        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(applyAgentResp);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/feedbackInfo）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "代理特权活动积分获得", notes = "代理特权活动积分获得",
            httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = MahjongRewardResp.class)
    @ResponseBody
    @RequestMapping(value = "cardScore", method = RequestMethod.POST)
    public Ajax cardScore(@RequestBody MahjongRewardReq mahjongRewardReq) throws Exception {

        logger.info("客户端请求数据：{}", mahjongRewardReq.toString());

        //token认证6c7e133d1d3ecf056a3f06a4cfd08fa9
        String token = mahjongRewardReq.getToken();
        if (!token.equals("6c7e133d1d3ecf056a3f06a4cfd08fa9")) throw new Exception("请求失败，token错误");


        logger.info("进入 代理特权活动积分获得（user/api/cardScore）token：{}", mahjongRewardReq.getToken());

        int integral = mahjongRewardReq.getIntegral();
        int user_id = mahjongRewardReq.getUserId();
        if (integral <= 0 || user_id <= 0) throw new Exception("请求失败，积分值或打卡用户ID不正确");

        integral = integral * 100;

        GameUserEntity queryByUserId = gameUserDao.findByUserId(user_id);
        if (queryByUserId == null) throw new Exception("请求失败，打卡用户ID不正确");


        //查询该用户是否存在 user表中，如果没有，则建立关系
        GameUserEntity user = gameUserDao.findUserByUserId(user_id);
        if (user == null) {
            //建立关系
            ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
        }


        String purpose = "user_id（" + user_id + "）打卡所获得的积分值：（" + integral + "）分";

        logger.info(purpose);


        //记录 积分获取记录
        integralRebateDao.insertToRebateTwo(user_id, 1, 0, integral, 0, 6);


        //代理特权活动 积分积分增加 记录入库
        userService.integralAddRecord(user_id, integral, 0, purpose);


        MahjongRewardResp respBody = new MahjongRewardResp();
        respBody.setStatus(0);
        respBody.setMsg("成功获得打卡" + integral / 100 + "积分");
        logger.info("接口：（user/api/cardScore）服务端返回数据：{}", respBody);

        return new Ajax(respBody);
    }


    @ApiOperation(value = "实名认证", notes = "实名认证",
            httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = AuthenticationResp.class)
    @ResponseBody
    @RequestMapping(value = "authentication", method = RequestMethod.POST)
    public Ajax authentication(@RequestBody AuthenticationReq authenticationReq) throws Exception {

        logger.info("客户端请求数据  实名认证（/authentication）：{}", authenticationReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(authenticationReq.getToken());
        int user_id = tokenAuth.getId();

        String name = authenticationReq.getName();  //姓名
        String idCard = authenticationReq.getIDCard();  //身份证号

        if (name.equals("") || idCard.equals("")) throw new Exception("请完整填写信息");


        //先查询是否已经认证过了
        AuthenticationEntity authenticationEntity = authenticationDao.queryByUserId(user_id);
        if (authenticationEntity != null) {
            throw new Exception("您已经实名认证过了！");
        }

        authenticationDao.insertToAuthentication(user_id, name, idCard);

        //请求返回体
        IntegralWithdrawalsRespBody respBody = new IntegralWithdrawalsRespBody();
        respBody.setMsg("感谢您，您已经完成了实名认证！");


        logger.info("接口：（user/api/authentication）服务端返回数据：{}", respBody);

        return new Ajax(respBody);
    }

    @ApiOperation(value = "判断是否已经进行过实名认证", notes = "判断是否已经进行过实名认证",
            httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = AuthenticationResp.class)
    @ResponseBody
    @RequestMapping(value = "needAuth", method = RequestMethod.POST)
    public Ajax needAuth(@RequestBody ApiRequest apiRequest) throws Exception {

        logger.info("客户端请求数据  实名认证（/needAuth）：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());
        int user_id = tokenAuth.getId();


        //请求返回体
        AuthenticationResp respBody = new AuthenticationResp();


        //先查询是否已经认证过了
        AuthenticationEntity authenticationEntity = authenticationDao.queryByUserId(user_id);
        if (authenticationEntity != null) {
            respBody.setStatus(0);
            respBody.setMsg("您已经实名认证过了！");
        } else {
            respBody.setStatus(1);
            respBody.setMsg("需要进行实名认证！");
        }


        logger.info("接口：（user/api/needAuth）服务端返回数据：{}", respBody);

        return new Ajax(respBody);
    }

}

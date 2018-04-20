package com.juunew.admin.controller.rest;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.controller.BaseController;
import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.entity.api.*;
import com.juunew.admin.services.BankService;
import com.juunew.admin.services.WechatApiService;
import com.juunew.admin.services.WechatPayService;
import com.juunew.admin.services.WechatService;
import com.juunew.admin.utils.MD5Util;
import com.juunew.admin.wechat.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 自定义API接口
 */

@Api(value = "user", description = "用户管理", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    /**
     * 推送消息类型
     */
    public static final int Push_Msg_Invite = 1; //邀请
    public static final int Push_Msg_Again = 2;  //再来一局
    public static final int Push_Msg_LevelChange = 3;  //等级变动
    public static final int Push_Msg_PushLog = 4;  //通知客户端把日志上传
    public static final int Push_Msg_ServerNotice = 5; //服务器通知消息    //奖励
    public static final int Push_Msg_BeginGame = 6; //开赛时间到
    public static final int Push_Msg_StartGame = 7; //匹配完成，开始游戏
    public static final int Push_Msg_Reward = 8;    //暂未定
    public static final int Push_Msg_Competition_TimeOut = 9; //比赛超时结束
    public static final int Push_Msg_Diamond_Withdrawals = 11; //提现申请消息
    public static final int Push_Msg_Diamond_Transmit = 12; //
    public static final int Push_Msg_Diamond_Integral = 13; //积分变动消息


    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    Diamond_outputDao diamond_outputDao;
    @Autowired
    AnnouncementsDao announcementsDao;
    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    Announcements_recordsDao announcements_recordsDao;
    @Autowired
    MsgPushRecordDao msgPushRecordDao;
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    BankService bankService;
    @Autowired
    CompetitionsDao competitionsDao;
    @Autowired
    SystemMsgsDao systemMsgsDao;
    @Autowired
    SystemMsgRecordsDao systemMsgRecordsDao;
    @Autowired
    HalfPriceDao halfPriceDao;
    @Autowired
    GamesDao gamesDao;
    @Autowired
    IntegralProductDao integralProductDao;
    //ProductsDao productsDao;
    @Autowired
    WechatRechargeDao wechatRechargeDao;
    @Autowired
    GameConfigsDao gameConfigsDao;
    @Autowired
    DiamondGiftDao diamondGiftDao;
    @Autowired
    ClubUsersDao clubUsersDao;
    @Autowired
    WechatService wechatService;
    @Autowired
    WechatApiService weixinUtil;
    @Autowired
    TimeConfigDao timeConfigDao;


    //微信授权所需参数
    @Value("${wechat.appid}")
    String APPID;
    @Value("${wechat.appsecret}")
    String APPSECRET;
    @Value("${wechat.backUrl}")
    String backUrl;

    //app支付所需参数
    @Value("${wechat.appPayNotify}")
    String appPayNotify;
    @Value("${wechat.appPayAppid}")
    String appPayAppid;


    @ApiOperation(value = "公告", notes = "客户端公告信息接口", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/noticeDropDown", method = RequestMethod.POST)
    public Notice noticeDrop(@RequestBody NoticeDropDown noticeDropDown) throws Exception {
        logger.debug("客户端请求数据：{}", noticeDropDown.toString());

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date nowDate = df.parse(sysDate);


        //将date类型转为long类型
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = formatter.parse(sysDate);
        long now_date = now.getTime() / 1000;


        //token认证
        GameUserEntity userEntity = auth(noticeDropDown.getToken());
        Notice notice = new Notice();

        String openId = userEntity.getOpenid();
        if (openId != null) {
            logger.debug("- - - openid - - - " + openId);
        }


        //if (!openId.equals("123456")) {   //游客 获取不到公告

        int user_id = userEntity.getId();

        List list = new ArrayList();

        //查询所有平台发送的公告信息 msg_type=0
        List<AnnouncementsEntity> announcementsListsTypeOne = announcementsDao.findAnnouncementsByMsType(now_date);

        //查  Announcements 表 中的 msg_type=1 的记录，
        List<AnnouncementsEntity> announcementsListsTypeZero = announcementsDao.findAnnouncementsByCondition(now_date);

        if (announcementsListsTypeOne.size() != 0) {

              /*  List filterList = new ArrayList();
                //处理 根据版本号 发送公告
                for (AnnouncementsEntity announcementsEntity : announcementsListsTypeOne) {
                    String ver = announcementsEntity.getVersion();
                    if(ver.equals("") || version.equals(ver)){
                        filterList.add(announcementsEntity);
                    }
                }
                list = pubAnnounce(filterList, user_id, nowDate);*/
            list = pubAnnounce(announcementsListsTypeOne, user_id, nowDate);
        }


        List listOther = new ArrayList();
        if (announcementsListsTypeZero.size() != 0) {
            listOther = pubAnnounce(announcementsListsTypeZero, user_id, nowDate);
        }

        if (listOther.size() != 0) {
            list.addAll(listOther);
        }


        notice.setData(list);
        //}

        logger.debug("noticeDropDown返回: {}", notice.toString());

        return notice;
    }

    //公告处理类
    private List pubAnnounce(List<AnnouncementsEntity> announcementsLists, int user_id, Date nowDate) {

        List list = new ArrayList();

        for (AnnouncementsEntity announcementsList : announcementsLists) {
            int announcementsID = announcementsList.getId();

            //先根据  announcementsID 查询  announcement_records 表中的内容
            Announcements_recordsEntity dataByID = announcements_recordsDao.findDataByID(announcementsID, user_id);


            //判断是否为空，如果为空,则代表该用户没有访问过此条公告，如果不为空，则  times加1
            if (dataByID != null) {
                //先判断是否超过访问次数  查询announcements_records表 和 announcements表 中的times
                int send_times = dataByID.getSend_times();
                int times = announcementsList.getTimes();


                if (send_times < times) {

                    NoticeDropDown announcementsDatas = new NoticeDropDown();

                    announcementsDatas.setContent(announcementsList.getContent());
                    announcementsDatas.setDisplay_position(announcementsList.getDisplay_position());
                    announcementsDatas.setExit(announcementsList.getExit());
                    announcementsDatas.setJump_url(announcementsList.getJump_url());
                    announcementsDatas.setTitle(announcementsList.getTitle());
                    announcementsDatas.setTimes(announcementsList.getTimes());
                    announcementsDatas.setId(announcementsList.getId());
                    announcementsDatas.setImgurl(announcementsList.getImgurl());
                    announcementsDatas.setMsg_type(announcementsList.getMsg_type());
                    announcementsDatas.setPage_type(announcementsList.getPage_type());

                    list.add(announcementsDatas);


                    //不为空, times加1
                    announcements_recordsDao.updateSendTimes(announcementsID, user_id);
                }


            } else {
                NoticeDropDown announcementsDatas = new NoticeDropDown();

                announcementsDatas.setContent(announcementsList.getContent());
                announcementsDatas.setDisplay_position(announcementsList.getDisplay_position());
                announcementsDatas.setExit(announcementsList.getExit());
                announcementsDatas.setJump_url(announcementsList.getJump_url());
                announcementsDatas.setTitle(announcementsList.getTitle());
                announcementsDatas.setTimes(announcementsList.getTimes());
                announcementsDatas.setId(announcementsList.getId());
                announcementsDatas.setImgurl(announcementsList.getImgurl());
                announcementsDatas.setMsg_type(announcementsList.getMsg_type());
                announcementsDatas.setPage_type(announcementsList.getPage_type());

                list.add(announcementsDatas);

                //如果有公告，但没有在announcements_records表中，则建立相对应的关系；（新建一条数据）
                announcements_recordsDao.insertToRecords(announcementsID, user_id, 1, nowDate);
            }
        }
        return list;
    }


    /**
     * 客户端弹出框消息拉取<最新消息列表接口>
     *
     * @param msgsReq 客户端请求参数
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "消息列表", notes = "客户端消息下拉信息获取", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/getMsgs", method = RequestMethod.POST)
    public MsgBody getMsgs(@RequestBody MsgsRequest msgsReq) throws Exception {
        logger.info("客户端请求数据：{}", msgsReq.toString());

        //token认证
        GameUserEntity user = auth(msgsReq.getToken());
        MsgBody msgBody = new MsgBody();
        List list = new ArrayList();

        String openId = user.getOpenid();
        logger.info("userID（" + user.getId() + "）的openId：{}", openId);
        if (!openId.equals("123456")) {  //游客 无消息

            int build = msgsReq.getBuild();     //客户端请求build号

            int buildVer = 0;     //需要过滤的版本号；

            //查询出App Store审核版本，将正在审核的版本中的消息弹出过滤掉（game_configs表）
            //根据game_kind=99 查询App Store审核的版本号
            GameConfigsEntity queryHiddenBuild = gameConfigsDao.findHiddenBuildversByGameKind();
            if (queryHiddenBuild != null) {
                List buildVers = getBuildVer(queryHiddenBuild);
                if (buildVers.size() != 0) {
                    for (Object version : buildVers) {
                        logger.info("从数据库中获取 过滤的buildVersion值：{}", version);
                        if ((int) version == build) {
                            buildVer = -1;
                        }
                    }
                }
            }


            int page = msgsReq.getPage();  //客户端请求当前页数
            int limit = msgsReq.getLimit();  //客户端请求一次获取多少条数据

            if (page <= 0) page = 0;
            if (limit <= 0) limit = 50;

            int offset = page * limit;
            logger.info("limit值：" + limit + "   page值：" + offset);

            //用于查询邀请再来一局的消息分页
            int inviteAndAgainLimit = 10;
            limit = limit - 10;
            if (limit <= 0) limit = 1;

            if (buildVer != -1) {

                //查询未读消息
                List<SystemMsgsEntity> systemMsgs = systemMsgsDao.getUnread(user.getId());

                List<SystemMsgsEntity> lists = systemMsgsDao.queryMessages(user.getId(), build, offset, limit);
                if (lists.size() != 0) {
                    for (SystemMsgsEntity systemMsgsEntity : lists) {
                        boolean flag = true;
                        if (systemMsgs.size() != 0) {
                            for (SystemMsgsEntity systemMsg : systemMsgs) {
                                if (systemMsg.getId() == systemMsgsEntity.getId()) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        if (flag) systemMsgs.add(systemMsgsEntity);
                    }
                }

                for (SystemMsgsEntity systemMsg : systemMsgs) {

                    Integer readStatus = systemMsg.getRead_status();

                    if (systemMsg.getRead_status() == SystemMsgRecordsEntity.SystemMsgRecord_ReadStatus_Unread) {
                        int msg_id = systemMsg.getId();
                        int user_id = systemMsg.getUser_id();

                        //查询表中是否有记录，没有则新增，否则修改读取状态
                        SystemMsgRecordsEntity systemMsgRecordsEntity = systemMsgRecordsDao.queryByMsgId(msg_id, user_id);
                        if (systemMsgRecordsEntity != null) {
                            systemMsgRecordsDao.readed(msg_id, user_id);
                        } else {
                            SystemMsgRecordsEntity systemMsgRecords = saveSystemMsgRecordsEntity(1, systemMsg.getId(), user.getId());
                        }
                    }


                    JSONObject jsonObject = msgTypeDispose(systemMsg, readStatus, user.getId());
                    if (jsonObject != null) list.add(jsonObject);
                }


                //只查询邀请和再来一局的消息（需要查询该邀请消息是否有效，影响速度，所有分开查询）
                List<SystemMsgsEntity> listsTwo = systemMsgsDao.queryInviteAndAgain(user.getId(), build, offset, inviteAndAgainLimit);
                for (SystemMsgsEntity systemMsgsEntity : listsTwo) {
                    int msg_id = systemMsgsEntity.getId();
                    int user_id = systemMsgsEntity.getUser_id();
                    //读取状态
                    int read_status = systemMsgsEntity.getRead_status();

                    //将未读改为已读
                    if (read_status == 0) systemMsgRecordsDao.readed(msg_id, user_id);

                    JSONObject jsonObject = msgTypeDispose(systemMsgsEntity, read_status, user.getId());
                    if (jsonObject != null) list.add(jsonObject);
                }

            } else {
                logger.info("客户端请求的版本号和过滤的版本号一致");
            }
        } else {
            logger.info("游客不显示消息");
        }


        msgBody.setData(list);
        logger.info("服务端返回数据：{}", msgBody.toString());

        return msgBody;
    }

    /**
     * 处理查询出来为JSON类型的数据处理
     *
     * @param queryHiddenBuild
     * @return
     */
    private List getBuildVer(GameConfigsEntity queryHiddenBuild) {
        int buildVer = 0;   //需要过滤的版本号
        String hiddenBuild = queryHiddenBuild.getConfig();
        JSONObject jsonObject = JSONObject.parseObject(hiddenBuild);
        String hidden_build_obj = jsonObject.getString("hidden.buildvers");

        List list = new ArrayList();

        if (hidden_build_obj != null) {

            JSONArray hidden_build = JSONObject.parseArray(hidden_build_obj);
            if (hidden_build.size() != 0) {
                for (Object hideBul : hidden_build) {
                    JSONObject json = JSONObject.parseObject(hideBul + "");
                    if (json != null) {
                        String buildV = json.getString("buildVer");
                        if (!buildV.equals("")) buildVer = Integer.parseInt(buildV);
                        logger.info("在（game_configs表）中获得 buildVer 值：{}", buildVer);

                        //如果为0 则不过滤
                        if (buildVer != 0) list.add(buildVer);
                    }
                }
            } else {
                logger.error("无法将（game_configs）表中的（hidden.buildvers）属性值转换为JSONArray类型");
            }
        } else {
            logger.error("game_configs表中无 hidden.buildvers 属性值>>>返回值:-1");
        }
        logger.info("返回的过滤版本号为：{}", list);
        return list;
    }

    /**
     * 根据消息类型 处理
     *
     * @param systemMsg  需要处理的消息实体类 数据
     * @param readStatus 读取状态
     * @return
     * @throws Exception
     */
    private JSONObject msgTypeDispose(SystemMsgsEntity systemMsg, int readStatus, int user_id) throws Exception {
        int msgTime = (int) (systemMsg.getStart_time().getTime() / 1000);


        //解析比赛消息（json类型）
        String result = systemMsg.getRaw_content();


        JSONObject jsonObject = null;
        JSONObject contentObject = null;


        boolean expired = false;  //消息是否过期 ，没过期

        switch (systemMsg.getCmd()) {
            case Push_Msg_Invite:
            case Push_Msg_Again:

                jsonObject = JSONObject.parseObject(result);
                contentObject = jsonObject.getJSONObject("content");
                logger.info("result- - -:{}", result);

                String liveCode = contentObject.getString("liveCode");

                logger.info("邀请中的liveCode: {}", liveCode);

                expired = !queryGameValid(liveCode);
                JSONObject msgListInfo = getMsgListInfoObject(msgTime, readStatus, expired);
                contentObject.put("msgListInfo", msgListInfo);

                if (expired) { //过期不显示
                    jsonObject = null;
                    contentObject = null;
                }
                break;

            case Push_Msg_BeginGame:
            case Push_Msg_StartGame:
                jsonObject = JSONObject.parseObject(result);
                contentObject = jsonObject.getJSONObject("content");
                int competitionId = contentObject.getIntValue("competitionId");
                boolean b = queryCompetitionValid(competitionId);
                expired = false;  //消息是否过期 ，没过期
                if (!b) {
                    expired = true;
                }

                msgListInfo = getMsgListInfoObject(msgTime, readStatus, expired);
                contentObject.put("msgListInfo", msgListInfo);

                if (expired) { //过期不显示
                    jsonObject = null;
                    contentObject = null;
                }
                break;

            case Push_Msg_ServerNotice:     //cmd:5     <需要特殊处理>

                jsonObject = new JSONObject();
                contentObject = new JSONObject();
                jsonObject.put("content", contentObject);

                boolean needExit = systemMsg.isNeed_exit();
                int btnShow = systemMsg.getBtn_show();

                boolean btn_show = false;
                if (btnShow == 1) {
                    String btnUrl = systemMsg.getBtn_to_url();
                    int btn_to_pages = systemMsg.getBtn_to_page();
                    int cmd = systemMsg.getBtn_cmd();

                    if (btnUrl == null) {
                        btnUrl = "";
                    }
                    //page定义:  1：消息；2：任务；3：比赛列表 4：宝箱；5：充值界面；6：好友界面
                    //btnAction中Cmd值：0. 关闭窗口 1. 跳转到网页 2. 跳转到页面  3. 领取奖励  4. 退出应用
                    btn_show = true;
                    JSONObject actionObject = getActionObject(btnUrl, btn_to_pages, cmd);
                    contentObject.put("btnAction", actionObject);
                }
                contentObject.put("btnShow", btn_show);


                int showPlace = systemMsg.getShow_place();
                int msgType = systemMsg.getMsg_type();  //消息类型  1：文本消息；2：纯图消息；3：图文消息
                int cmd = systemMsg.getCmd();
                int msgId = systemMsg.getId();


                String picUrl = systemMsg.getPic_url();    //图片地址
                if (StringUtils.isNotEmpty(picUrl)) {
                    int pic_cmds = systemMsg.getPic_cmd();
                    int pic_pages = systemMsg.getPic_to_page();
                    String pic_to_urls = systemMsg.getPic_to_url();
                    if (pic_to_urls == null) {
                        pic_to_urls = "";
                    }

                    JSONObject actionObject = getActionObject(pic_to_urls, pic_pages, pic_cmds);
                    JSONObject picContentObject = new JSONObject();
                    picContentObject.put("picUrl", picUrl);
                    picContentObject.put("picAction", actionObject);

                    contentObject.put("picContent", picContentObject);
                }


                String btn_tl = systemMsg.getBtn_title();  //按钮标题
                if (btn_tl == null) {
                    btn_tl = "";
                }
                int reward = systemMsg.getReward();     //奖励；0：无奖励, 其他奖励数量

                jsonObject.put("cmd", cmd);
                contentObject.put("msgType", msgType);
                contentObject.put("msgId", msgId);
                contentObject.put("rewardRecordId", msgId);
                contentObject.put("title", systemMsg.getTitle());
                contentObject.put("content", systemMsg.getContent());
                contentObject.put("rewardType", 1);
                contentObject.put("rewardNum", reward);
                contentObject.put("btnTitle", btn_tl);

                //是否显示跑马灯
                boolean need_scroller = systemMsg.isNeed_scroller();
                if (need_scroller) {
                    contentObject.put("needScroller", need_scroller);
                    contentObject.put("scrollerTime", 3);
                }

                //处理再次弹出：
                int show_again = systemMsg.getShow_again();
                if (show_again == 1) {      //每次登陆弹出
                    contentObject.put("showPlace", 1);
                } else {      //2 :每次打开特定界面弹出
                    contentObject.put("showPlace", showPlace);
                }


                expired = false;  //消息是否过期 ，没过期
                expired = !(systemMsg.isExpired());

                //处理是否过期、读取状态、开始时间
                JSONObject msgListInfoObject = getMsgListInfoObject(msgTime, readStatus, expired);
                contentObject.put("msgListInfo", msgListInfoObject);

                if (expired) { //过期不显示
                    jsonObject = null;
                    contentObject = null;
                }

                break;

            case Push_Msg_Competition_TimeOut:
                jsonObject = JSONObject.parseObject(result);
                contentObject = jsonObject.getJSONObject("content");
                msgListInfoObject = getMsgListInfoObject(msgTime, readStatus, true);
                contentObject.put("msgListInfo", msgListInfoObject);
                break;
            case Push_Msg_Diamond_Withdrawals:
            case Push_Msg_Diamond_Transmit:
            case Push_Msg_Diamond_Integral:
                jsonObject = JSONObject.parseObject(result);
                contentObject = jsonObject.getJSONObject("content");
                msgListInfoObject = getMsgListInfoObject(msgTime, readStatus, true);
                contentObject.put("msgListInfo", msgListInfoObject);

                Date start_time = systemMsg.getStart_time();
                long end_time = start_time.getTime() + 6 * 24 * 60 * 60 * 1000 * 1l;
                Date date = new Date();
                if (!(date.getTime() >= start_time.getTime() && date.getTime() <= end_time)) { //过期不显示
                    jsonObject = null;
                    contentObject = null;
                }
                break;
        }
        return jsonObject;
    }

    private JSONObject getActionObject(String btnUrl, int btn_to_pages, int cmd) {
        JSONObject actionObject = new JSONObject();
        actionObject.put("cmd", cmd);
        actionObject.put("page", btn_to_pages);
        actionObject.put("url", btnUrl);
        return actionObject;
    }

    private JSONObject getMsgListInfoObject(int msgTime, int readStatus, boolean expired) {
        JSONObject msgListInfo = new JSONObject();
        msgListInfo.put("expired", expired);
        msgListInfo.put("readStatus", readStatus);
        msgListInfo.put("msgTime", msgTime);
        return msgListInfo;
    }


    /**
     * 客户端弹出框消息拉取 <最新客户端弹出消息>
     *
     * @param
     * @return
     * @throws Exception
     */

    @ApiOperation(value = "弹出框消息", notes = "客户端启动时信息获取", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/clientMsg", method = RequestMethod.POST)
    public MsgBody clientMsg(@RequestBody MsgsRequest msgsReq) throws Exception {
        logger.info("客户端请求数据：{}", msgsReq.toString());

        //token认证
        GameUserEntity user = auth(msgsReq.getToken());
        int user_id = user.getId();

        MsgBody msgBody = new MsgBody();
        List list = new ArrayList();


        String openId = user.getOpenid();
        logger.info("userID（" + user.getId() + "）的openId：{}", openId);
        if (!openId.equals("123456")) {  //游客 无消息

            int build = msgsReq.getBuild();     //客户端请求的build号

            int buildVer = 0;     //需要过滤的版本号；

            //查询出App Store审核版本，将正在审核的版本中的消息弹出过滤掉（game_configs表）
            //根据game_kind=99 查询App Store审核的版本号
            GameConfigsEntity queryHiddenBuild = gameConfigsDao.findHiddenBuildversByGameKind();
            if (queryHiddenBuild != null) {
                List buildVers = getBuildVer(queryHiddenBuild);
                if (buildVers.size() != 0) {
                    for (Object version : buildVers) {
                        logger.info("从数据库中获取 过滤的buildVersion值：{}", version);
                        if ((int) version == build) {
                            buildVer = -1;
                        }
                    }
                }
            }
       /* GameConfigsEntity queryHiddenBuild = gameConfigsDao.findHiddenBuildversByGameKind();
        if(queryHiddenBuild != null){
            buildVer = getBuildVer(queryHiddenBuild);
            logger.info("从数据库中获取 过滤的buildVersion值：{}",buildVer);
        }
*/

            if (buildVer != -1) {
                //根据当前时间查询
                List<SystemMsgsEntity> dataLists = systemMsgsDao.findAllData(user.getId(), build);

                for (SystemMsgsEntity msgsEntity : dataLists) {
                    int system_msgs_id = msgsEntity.getId();

                    //根据 user_id, system_msgs_id 查询 record_id
                    SystemMsgRecordsEntity recordsEntity = systemMsgRecordsDao.findByUserIdSysMsgId(user_id, system_msgs_id);

                    if (msgsEntity.isSend_all()) {
                        if (recordsEntity == null) {
                            recordsEntity = saveSystemMsgRecordsEntity(1, msgsEntity.getId(), user.getId());
                        }
                    }

                    if (recordsEntity != null) {

                        long now = System.currentTimeMillis();
                        if (now > msgsEntity.getEnd_time().getTime()) {
                            int times = msgsEntity.getShow_times();

                            //已经过期了，将没有弹出的消息 次数 置位 该消息的显示次数
                            systemMsgRecordsDao.updateTimes(user_id, recordsEntity.getId(), times);
                        } else if (recordsEntity.getShow_times() < msgsEntity.getShow_times()) {
                            //次数加1
                            systemMsgRecordsDao.addShowTime(recordsEntity.getId());
                            JSONObject jsonObject = msgTypeDispose(msgsEntity, recordsEntity.getRead_status(), user_id);
                            list.add(jsonObject);
                        }
                    }
                }
            }
        }


        msgBody.setData(list);

        logger.info("服务端返回数据：{}", msgBody.toString());
        return msgBody;
    }

    private SystemMsgRecordsEntity saveSystemMsgRecordsEntity(int readStatus, int msgId, int userId) {
        SystemMsgRecordsEntity recordsEntity = new SystemMsgRecordsEntity();
        recordsEntity.setRead_status(readStatus);
        recordsEntity.setSystem_msgs_id(msgId);
        recordsEntity.setUser_id(userId);
        systemMsgRecordsDao.save(recordsEntity);
        return recordsEntity;
    }


    //消息接口重写
    @ApiOperation(value = "消息列表", notes = "客户端消息下拉信息获取", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/getMsg", method = RequestMethod.POST)
    public MsgBody getMsg(@RequestBody MsgRequest msgRequest) throws Exception {

        logger.info("客户端请求数据：{}", msgRequest.toString());


        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date now = df.parse(sysDate);


        //token认证
        GameUserEntity usersIDByToken = auth(msgRequest.getToken());

        MsgBody msgBody = new MsgBody();

        if (!usersIDByToken.getOpenid().equals("123456")) {

            int page = msgRequest.getPage();  //客户端请求当前页数
            int limits = msgRequest.getLimit();  //客户端请求一次获取多少条数据

            if (page <= 0) page = 0;
            if (limits <= 0) limits = 20;


            int xx = page * limits;

            logger.info("limit值：" + limits + "   page值：" + xx);

            //根据时间条件查询
            List<NoticeEntity> noticeDatas = msgPushRecordDao.findPushMsgByUserId(usersIDByToken.getId(), xx, limits);
            List list = new ArrayList();

            if (noticeDatas != null) {
                for (NoticeEntity noticeData : noticeDatas) {
                    MsgResponse msgResponse = new MsgResponse();

                    String result = noticeData.getContent();

                    JSONObject jsonObject = null;
                    String json = "";
                    switch (noticeData.getCmd()) {
                        case Push_Msg_Invite:
                        case Push_Msg_Again:
                            jsonObject = JSONObject.parseObject(result);
                            String liveCode = (String) jsonObject.get("liveCode");
                            String gameKind = (String) jsonObject.get("gameKind");
                            String name = (String) jsonObject.get("name");
                            if (!queryGameValid(liveCode)) {
                                msgResponse.setExpired(1);
                            }
                            json = "{\"liveCode\":\"" + liveCode + "\",\"name\":\"" + name + "\",\"gameKind\":\"" + gameKind + "\"}";
                            msgResponse.setJson(json);
                            msgResponse.setTitle("邀请再来一局");
                            break;

                        case Push_Msg_BeginGame:
                        case Push_Msg_StartGame:
                            jsonObject = JSONObject.parseObject(result);
                            String competition_id = (String) jsonObject.get("competitionId");
                            int competitionId = Integer.parseInt(competition_id);
                            boolean b = queryCompetitionValid(competitionId);
                            if (!b) {
                                msgResponse.setExpired(1);
                            }
                            json = "{\"competitionId\":\"" + competitionId + "\"}";
                            msgResponse.setJson(json);
                            msgResponse.setTitle("比赛竞技");
                            break;

                        case Push_Msg_ServerNotice:
                            int needExit = noticeData.getSign_out();
                            json = "{\"needExit\":\"" + needExit + "\"}";
                            msgResponse.setJson(json);
                            break;

                        case Push_Msg_Reward:
                            //根据notice_id  和 user_id查询 read_status
                            MsgPushRecordEntity readStatus = msgPushRecordDao.findMsgExpired(usersIDByToken.getId(), noticeData.getNotice_id(), sysDate);
                            if (readStatus == null) {
                                msgResponse.setExpired(1);
                            }

                            int reward_type = noticeData.getReward_type();
                            int reward_number = noticeData.getReward_number();
                            json = "{\"reward_type\":\"" + reward_type + "\",\"reward_number\":\"" + reward_number + "\"}";
                            msgResponse.setJson(json);
                            break;

                        case Push_Msg_Competition_TimeOut:
                            jsonObject = JSONObject.parseObject(result);
                            String competitionid = (String) jsonObject.get("competitionId");
                            int competid = Integer.parseInt(competitionid);
                            json = "{\"competitionId\":\"" + competid + "\"}";
                            msgResponse.setJson(json);
                            msgResponse.setTitle("比赛消息");
                            break;
                    }

                    //根据notice_id  和 user_id查询 read_status
                    MsgPushRecordEntity readStatus = msgPushRecordDao.findReadStatus(usersIDByToken.getId(), noticeData.getNotice_id());
                    if (readStatus != null) {
                        msgResponse.setRead_status(readStatus.getRead_status());
                    }

                    //去掉  时间后面的.0
                    String start_date = noticeData.getStart_date();
                    String startDate = start_date.replace(".0", "");
                    msgResponse.setStart_date(startDate);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date nowDate = formatter.parse(startDate);

                    //System.out.println("时间戳："+nowDate.getTime() / 1000);
                    int now_date = (int) (nowDate.getTime() / 1000);
                    msgResponse.setStart_time(now_date);

                    //根据notice_id  和 user_id查询 read_status
                    if (readStatus.getRead_status() == 0) {
                        //相应的将read_status的值改为 1
                        msgPushRecordDao.updateReadStatus(usersIDByToken.getId(), noticeData.getNotice_id(), 1);
                    }


                    //将cmd不是 5 和 8 的消息 title 放到content中
                    if (noticeData.getCmd() != 5 && noticeData.getCmd() != 8) {
                        msgResponse.setContent(noticeData.getTitle());
                    } else {
                        msgResponse.setContent(noticeData.getContent());
                        msgResponse.setTitle(noticeData.getTitle());
                    }
                    msgResponse.setDisplay_position(noticeData.getDisplay_position());
                    msgResponse.setCmd(noticeData.getCmd());
                    msgResponse.setMsg_id(noticeData.getNotice_id());


                    list.add(msgResponse);
                }
            }
            msgBody.setData(list);
        }

        logger.info("服务端返回数据：{}", msgBody.toString());

        return msgBody;
    }


    /**
     * 查询游戏是否可用
     *
     * @param liveCode
     * @return
     */
    private boolean queryGameValid(String liveCode) {

        //根据liveCode查询 (lives表) <查询(games)表中的status>    status==1 表示 未开始，所以没过期
        GamesEntity byLiveCode = gamesDao.findByLiveCode(liveCode);

        return byLiveCode != null && byLiveCode.getStatus() == 1;
    }


    /**
     * 查询竞赛是否可用
     *
     * @param competitionId
     * @return
     */
    private boolean queryCompetitionValid(int competitionId) {
        //根据competitionId 查询比赛是否已结束（competitions表） 4：已结束；将设置为已过期；
        CompetitionRewardRecordsEntity byCompetitionId = competitionsDao.findByCompetitionId(competitionId);
        if (byCompetitionId != null) {
            if (byCompetitionId.getStatus() != 4) {
                return true;
            }
        }
        return false;
    }

    @ApiOperation(value = "消息列表", notes = "客户端消息下拉信息获取", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/msgAcquisition", method = RequestMethod.POST)
    public Msg noticeDrop(@RequestBody MsgAcquisition msgAcquisition) throws Exception {

        logger.debug("客户端请求数据：{}", msgAcquisition.toString());


        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date now = df.parse(sysDate);


        //token认证
        GameUserEntity usersIDByToken = auth(msgAcquisition.getToken());

        Msg msg = new Msg();

        if (!usersIDByToken.getOpenid().equals("123456")) {

            int page = msgAcquisition.getPage();  //客户端请求当前页数
            int limits = msgAcquisition.getLimit();  //客户端请求一次获取多少条数据

            if (page <= 0) {
                page = 0;
            }
            if (limits <= 0) {
                limits = 20;
            }

            int xx = page * limits;


            //根据时间条件查询
            List<NoticeEntity> noticeDatas = msgPushRecordDao.findPushMsgByUserId(usersIDByToken.getId(), xx, limits);
            List list = new ArrayList();

            if (noticeDatas != null) {
                for (NoticeEntity noticeData : noticeDatas) {
                    MsgAcquisition noticeList = new MsgAcquisition();
                    String result = noticeData.getContent();

                    JSONObject jsonObject = null;

                    switch (noticeData.getCmd()) {
                        case Push_Msg_Invite:
                        case Push_Msg_Again:
                            jsonObject = JSONObject.parseObject(result);
                            String liveCode = (String) jsonObject.get("liveCode");
                            if (!queryGameValid(liveCode)) {
                                noticeList.setExpired(1);
                            }
                            break;

                        case Push_Msg_BeginGame:
                        case Push_Msg_StartGame:
                            jsonObject = JSONObject.parseObject(result);
                            String competition_id = (String) jsonObject.get("competitionId");
                            int competitioinId = Integer.parseInt(competition_id);
                            boolean b = queryCompetitionValid(competitioinId);
                            if (!b) {
                                noticeList.setExpired(1);
                            }
                            break;
                        case Push_Msg_Reward:
                            //根据notice_id  和 user_id查询 read_status
                            MsgPushRecordEntity readStatus = msgPushRecordDao.findMsgExpired(usersIDByToken.getId(), noticeData.getNotice_id(), sysDate);
                            if (readStatus == null) {
                                noticeList.setExpired(1);
                            }
                            break;
                    }


                    //根据notice_id  和 user_id查询 read_status
                    MsgPushRecordEntity readStatus = msgPushRecordDao.findReadStatus(usersIDByToken.getId(), noticeData.getNotice_id());
                    if (readStatus != null) {
                        noticeList.setRead_status(readStatus.getRead_status());
                    }
                    noticeList.setReward_id(noticeData.getNotice_id());

                    //去掉  时间后面的.0
                    String start_date = noticeData.getStart_date();
                    String startDate = start_date.replace(".0", "");
                    noticeList.setStart_date(startDate);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date nowDate = formatter.parse(startDate);

                    //System.out.println("时间戳："+nowDate.getTime() / 1000);
                    int now_date = (int) (nowDate.getTime() / 1000);
                    noticeList.setStart_time(now_date);

                    //根据notice_id  和 user_id查询 read_status
                    if (readStatus.getRead_status() == 0) {
                        //相应的将read_status的值改为 1
                        msgPushRecordDao.updateReadStatus(usersIDByToken.getId(), noticeData.getNotice_id(), 1);
                    }

                    noticeList.setContent(noticeData.getContent());
                    noticeList.setTitle(noticeData.getTitle());
                    noticeList.setDisplay_position(noticeData.getDisplay_position());
                    noticeList.setReward_type(noticeData.getReward_type());
                    noticeList.setReward_number(noticeData.getReward_number());
                    noticeList.setSign_out(noticeData.getSign_out());
                    noticeList.setReward_id(noticeData.getNotice_id());
                    noticeList.setId(noticeData.getNotice_id());
                    noticeList.setCmd(noticeData.getCmd());


                    list.add(noticeList);
                }
            }
            msg.setData(list);
        }

        logger.info("服务端返回数据：{}", msg.toString());

        return msg;
    }


    @ApiOperation(value = "奖励消息领取", notes = "客户端奖励消息领取接口", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/getRewards", method = RequestMethod.POST)
    public Ajax getRewards(@RequestBody RewardReq rewardReq) throws Exception {
        logger.debug("客户端请求数据:" + rewardReq.toString());
        //token认证
        GameUserEntity user = auth(rewardReq.getToken());

        //从客户端获取notice_id
        int rewardRecordId = rewardReq.getRewardRecordId();       //消息ID

        if (rewardRecordId <= 0) {
            throw new Exception("reward_record_id为必传项");
        }

        //根据 msg_id 获取 表中的数据
        SystemMsgsEntity getMsg = systemMsgsDao.findById(rewardRecordId);

        if (getMsg == null) {
            throw new Exception("消息不存在");
        }

        int system_msgs_id = rewardRecordId;

        //根据 user_id, system_msgs_id 查询 record_id
        SystemMsgRecordsEntity recordsEntity = systemMsgRecordsDao.findByUserIdSysMsgId(user.getId(), system_msgs_id);

        if (getMsg.isSend_all()) {
            if (recordsEntity == null) {
                //如果是全员发送，记录表中没有 数据 则建立关联
                recordsEntity = saveSystemMsgRecordsEntity(1, getMsg.getId(), user.getId());
            }
        }

        if (recordsEntity != null) {

            //判断是否 是奖励消息；如果是，调用加钻石金币接口，限制只能领取一次
            if (recordsEntity.getRead_status() != 2 && getMsg.getReward() > 0) {

                try {
                    int diamond_s = bankService.addDiamond(1, user.getId(), getMsg.getReward(), 1, "奖励消息钻石的领取", 303);
                    logger.info("API调用返回结果：返回为总钻石数量：：{}", diamond_s);

                    //相应的将read_status的值改为 2(已领取)
                    systemMsgRecordsDao.updateReadStatusTwo(user.getId(), recordsEntity.getId());
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            } else {
                return new Ajax(-1, "已经领取过了");
            }
        }

        return new Ajax(0, "操作成功");
    }


    @ApiOperation(value = "奖励消息领取", notes = "客户端奖励消息领取接口", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/getReward", method = RequestMethod.POST)
    public Ajax noticeDrop(@RequestBody MsgRewardRequest msgReward) throws Exception {
        //token认证
        GameUserEntity usersIDByToken = auth(msgReward.getToken());

        //从客户端获取notice_id
        int notice_id = msgReward.getMsg_id();        //消息ID (notice_id)


        if (notice_id <= 0) {
            throw new Exception("Msg_id为必传项");
        }


        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date now = df.parse(sysDate);


        logger.debug("客户端请求数据:" + msgReward.toString());


        //根据Notice_id获取notices表中的数据
        MsgAcquisition noticeData = noticeDao.findNoticeById(notice_id, now);

        if (noticeData == null) {
            return new Ajax(-1, "消息不存在");
        }

        //根据notice_id  和 user_id查询 read_status
        MsgPushRecordEntity readStatus = msgPushRecordDao.findReadStatus(usersIDByToken.getId(), notice_id);
        if (readStatus == null) {
            return new Ajax(-1, "记录不存在");
        }

        //判断是否 是奖励消息；如果是，调用加钻石金币接口，限制只能领取一次
        if (noticeData.getReward_type() != 0 && readStatus.getRead_status() != 2) {
            if (noticeData.getReward_type() == 1) {
                int diamond_s = bankService.addDiamond(1, usersIDByToken.getId(), noticeData.getReward_number(), 1, "奖励消息钻石的领取", 303);
                logger.debug("API调用返回结果：返回为总钻石数量：：" + diamond_s);
            }

            if (noticeData.getReward_type() == 2) {
                int money_s = bankService.addMoney(1, usersIDByToken.getId(), noticeData.getReward_number(), 2);
            }

            //相应的将read_status的值改为 1
            msgPushRecordDao.updateReadStatus(usersIDByToken.getId(), notice_id, 2);
        }


        return new Ajax(0, "操作成功");
    }


    @Deprecated
    @ApiOperation(value = "奖励消息有效处理", notes = "客户端奖励消息判断是否失效", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/getRewardStatus", method = RequestMethod.POST)
    public RewardStatus rewardStatus(@RequestBody RewardStatus rewardStatus) throws Exception {

        logger.info("客户端请求数据:{}", rewardStatus.toString());

        //token认证
        GameUserEntity usersIDByToken = auth(rewardStatus.getToken());

        //从客户端获取notice_id
        int notice_id = rewardStatus.getMsg_id();        //消息ID (notice_id)

        if (notice_id <= 0) {
            throw new Exception("notice_id为必传项");
        }


        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date now = df.parse(sysDate);


        //根据Notice_id获取notices表中的数据
        MsgAcquisition noticeData = noticeDao.findNoticeById(notice_id, now);

        if (noticeData != null) {
            rewardStatus.setRewardStatus(1);
        } else {
            rewardStatus.setRewardStatus(0);
        }

        return rewardStatus;
    }


    /**
     * 从客户端获取消息点击率
     *
     * @param clickRate
     * @throws Exception
     */
    @ApiOperation(value = "消息点击次数", notes = "客户端消息点击率", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/clickRate", method = RequestMethod.POST)
    public Ajax clickRateCount(@RequestBody ClickRateEntity clickRate) throws Exception {

        logger.info("客户端请求数据:{}", clickRate.toString());

        //token认证
        GameUserEntity usersIDByToken = auth(clickRate.getToken());

        if (clickRate.getMsgId() <= 0) {
            return new Ajax(-1, "找不到msgId");
        }

        //根据user_id 和 msgId 将点击次数加 1
        systemMsgRecordsDao.updateHitCount(usersIDByToken.getId(), clickRate.getMsgId());

        MsgBody msgBody = new MsgBody();
        msgBody.setCode(0);

        return new Ajax(0, "操作成功");
    }


    /**
     * 客户端app支付请求商品列表
     *
     * @param productsReq
     * @throws Exception
     */
    @ApiOperation(value = "app支付请求商品列表", notes = "客户端获取商品列表", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, response = IntegralProductsResp.class)
    @ResponseBody
    @RequestMapping("/api/getProductList")
    public Msg productList(@RequestBody ProductsReq productsReq) throws Exception {

        logger.info("客户端App支付（获取商品列表） 请求参数: {}", productsReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(productsReq.getToken());

        Msg msg = new Msg();
        List list = new ArrayList();

        //根据user_id 查询 是否绑定上级
        GameUserEntity querySuperior = gameUserDao.findFatherProxyId(tokenAuth.getId());

        Boolean flag = false;
        if (querySuperior != null) flag = true;

        //查询配置的商品表（products）
        //默认玩家充值价格体系  type=1 为玩家充值
        List<IntegralProductEntity> products = integralProductDao.queryAllProduct(IntegralProductEntity.PlayerType);
        if (flag) {
            int user_status = querySuperior.getUser_status();
            if (user_status == GameUserEntity.Player) {
                products = integralProductDao.queryAllProduct(IntegralProductEntity.BindingPlayerType);
            } else if (user_status == GameUserEntity.Two_Level_Agent) {
                products = integralProductDao.queryAllProduct(IntegralProductEntity.AgentType);
            } else if (user_status == GameUserEntity.First_Level_Agent) {
                products = integralProductDao.queryAllProduct(IntegralProductEntity.GeneralAgentType);
            }
        }

        if (products.size() != 0) {
            for (IntegralProductEntity product : products) {
                //参数组装
                IntegralProductsResp integralProductsResp = new IntegralProductsResp();

                double money = product.getMoney() * 1.0 / 100;
                int diamond = product.getDiamond();
                String img_url = product.getImg_url();
                int id = product.getId();

                integralProductsResp.setDiamond(diamond);
                integralProductsResp.setImgUrl(img_url);
                integralProductsResp.setMoney(money);
                integralProductsResp.setId(id);

                list.add(integralProductsResp);
            }

            msg.setData(list);
            msg.setCode(0);
            msg.setErrMsg("操作成功");
        } else {
            msg.setCode(-1);
            msg.setErrMsg("获取商品列表失败");
        }
        logger.info("服务端返回商品列表信息：{}", list);
        return msg;
    }


    /**
     * App支付
     *
     * @param clientPayReq 客户端请求参数
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "App支付", notes = "客户端获取支付签名参数", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "api/appPay", method = RequestMethod.POST)
    public JSONObject appPay(@RequestBody ClientPayReq clientPayReq, HttpServletRequest request) throws Exception {

        logger.info("客户端App支付（用订单号生成签名）请求数据：{}", clientPayReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(clientPayReq.getToken());

        int user_id = tokenAuth.getId();

        JSONObject jsonObject = new JSONObject();   //用于返回给客户端的数据格式

        int id = clientPayReq.getId();
        if (id <= 0) {
            logger.info("无法获取商品ID");
            jsonObject.put("code", -1);
            jsonObject.put("errMsg", "无法获取商品ID，请退出当前页面重新进入");
            return jsonObject;
        }


        //根据user_id 查询 是否绑定上级（安全考虑）
        GameUserEntity querySuperior = gameUserDao.findFatherProxyId(tokenAuth.getId());

        Boolean flag = false;
        if (querySuperior != null) flag = true;

        //根据客户端请求的商品ID查询 该商品的 支付价格
        IntegralProductEntity product = null;

        if (flag) {
            int user_status = querySuperior.getUser_status();
            if (user_status == GameUserEntity.Player) {
                product = integralProductDao.queryProductById(id, IntegralProductEntity.BindingPlayerType);
            } else if (user_status == GameUserEntity.Two_Level_Agent) {
                product = integralProductDao.queryProductById(id, IntegralProductEntity.AgentType);
            } else if (user_status == GameUserEntity.First_Level_Agent) {
                product = integralProductDao.queryProductById(id, IntegralProductEntity.GeneralAgentType);
            } else {
                product = integralProductDao.queryProductById(id, IntegralProductEntity.PlayerType);
            }
        } else {
            product = integralProductDao.queryProductById(id, IntegralProductEntity.PlayerType);
        }


        if (product == null) {
            logger.info("无法获取商品ID");
            jsonObject.put("code", -1);
            jsonObject.put("errMsg", "无法获取商品ID，请退出当前页面重新进入");
            return jsonObject;
        }


        int pay_price = product.getMoney();     //充值金额
        int diamond = product.getDiamond();     //充值获得的钻石数量

        String total_fee = pay_price + ""; // 产品价格（全价）

        // 统一下单
        String out_trade_no = PayUtil.createOutTradeNo();

        //用于测试
        if (user_id == 781 || user_id == 33 || user_id == 154)
            total_fee = "1";

        String spbill_create_ip = HttpReqUtil.getRemortIP(request);     //客户端支付IP

        logger.info("正在支付的user_id：{}", user_id);
        logger.info("需要支付的钱数：{}", total_fee);
        logger.info("支付成功后得到的钻石数量：{}", diamond);
        logger.info("客户端支付IP：" + spbill_create_ip);

        String nonce_str = UUID.randomUUID().toString().replaceAll("-", ""); // 随机数据

        String body = "钻石 " + diamond + " 个";

        //参数：开始生成签名
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", appPayAppid);//公众账号ID
        parameters.put("mch_id", "1494325552"); //商户号
        parameters.put("nonce_str", nonce_str);
        parameters.put("body", body);
        parameters.put("out_trade_no", out_trade_no);   //商户订单号
        parameters.put("total_fee", total_fee); //标价金额total_fee
        parameters.put("notify_url", appPayNotify);    //通知地址
        parameters.put("trade_type", "APP");
        parameters.put("spbill_create_ip", spbill_create_ip);   //终端IP

        //将订单信息存入数据库中：out_trade_no 订单号；total_fee：金额；user_id
        wechatRechargeDao.toWechatRechargeFromAppPay(user_id, out_trade_no, diamond, pay_price, "", 0, 2, product.getId());


        String sign = this.createSign(parameters);   //签名
        logger.info("第一次签名：{}", sign);

        parameters.put("sign", sign);          //将签名put到对象中

        // 统一下单 请求的Xml(正常的xml格式)
        String requestXML = WechatPayService.getRequestXml(parameters);     ////签名并入service

        logger.info("统一下单 请求的Xml数据：{}", requestXML);


        // 返回<![CDATA[SUCCESS]]>格式的XML
        String result = CommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);

        logger.info("返回<![CDATA[SUCCESS]]>格式的XML： {}", result.toString());

        Map<String, String> map = new HashMap<String, String>();

        map = XMLUtil.xmlToMap(result);     //将xml转为map

        logger.info("将xml转换为map后的结果：{}", map);

        //获取时间戳
        String timeStamp = PayUtil.createTimeStamp();

        //再次签名
        SortedMap<Object, Object> payMap = new TreeMap<Object, Object>();
        payMap.put("appid", appPayAppid);
        payMap.put("partnerid", "1494325552");
        payMap.put("prepayid", map.get("prepay_id"));
        payMap.put("noncestr", nonce_str);
        payMap.put("timestamp", timeStamp);
        payMap.put("package", "Sign=WXPay");

        String paySign = this.createSign(payMap);       //再次签名是

        logger.info("second sign = {}", paySign);

        payMap.put("sign", paySign);     //将再次签名put进去


        //---------------将参数改为驼峰式命名传给客户端
        SortedMap<Object, Object> payMaps = new TreeMap<Object, Object>();
        payMaps.put("appId", appPayAppid);
        payMaps.put("partnerId", "1494325552");
        payMaps.put("prepayId", payMap.get("prepayid"));
        payMaps.put("nonceStr", payMap.get("noncestr"));
        payMaps.put("timeStamp", payMap.get("timestamp"));
        payMaps.put("package", payMap.get("package"));
        payMaps.put("signType", "MD5");
        payMaps.put("sign", payMap.get("sign"));

        jsonObject.put("data", payMaps);
        jsonObject.put("code", 0);

        logger.info("服务端返回参数（订单签名数据）：{}", payMaps);

        return jsonObject;
    }

    public String createSign(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + ConfigUtil.API_KEY_OTHER);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        return sign;
    }


    /**
     * 客户端 俱乐部页面
     *
     * @param clubCode 俱乐部ID
     * @param session
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @RequestMapping("api/club_invite")
    public ModelAndView clubInvite(String clubCode, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        logger.info("clubCode: {}", clubCode);
        String backUrls = backUrl + "/callBackLogin?clubCode=" + clubCode;
        ModelAndView mv = wechatService.getClubByUser(clubCode, session);
        if (mv != null) return mv;

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID
                + "&redirect_uri=" + URLEncoder.encode(backUrls)
                + "&response_type=code"
                + "&scope=snsapi_userinfo&state=1#wechat_redirect";
        return new ModelAndView("redirect:" + url);
    }


    /**
     * 客户端 客服页面
     *
     * @return
     */
    @RequestMapping("api/customer_service")
    public ModelAndView customerService() {
        return new ModelAndView("client_customer_service");
    }

    /**
     * 客户端 意向代理填写手机号码页面
     *
     * @return
     */
    @RequestMapping("api/intent_proxy")
    public ModelAndView intentProxy() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("client_intent_proxy");
        return mv;
    }


    /**
     * 移动端页面测试
     *
     * @return
     */
    @RequestMapping("api/mobile")
    public ModelAndView mobileTest() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mobile");
        return mv;
    }

    /**
     * 客户端 麻将游戏规则页面
     *
     * @return
     */
    @RequestMapping("api/mahjong_rules")
    public ModelAndView mahjongRules() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("client_mahjong_rules");
        return mv;
    }


    //分享下载
    @RequestMapping("api/share_download")
    public ModelAndView shareDownload() {
        return new ModelAndView("share_download");
    }

}
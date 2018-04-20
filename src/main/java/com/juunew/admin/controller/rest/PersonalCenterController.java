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

@Api(value = "个人中心", description = "个人中心")
@RestController
@RequestMapping("user/api/")
public class PersonalCenterController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    DiamondsDao diamondsDao;
    @Autowired
    ProxyApprovalRecordsDao proxyApprovalRecordsDao;
    @Autowired
    LivesDao livesDao;
    @Autowired
    GetUserInfoService getUserInfoService;
    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    BankService bankService;
    @Autowired
    IncomeDao incomeDao;
    @Autowired
    ClubsActionDao clubsActionDao;
    @Autowired
    IntegralRebateDao integralRebateDao;
    @Autowired
    WechatService wechatService;
    @Autowired
    UserSpreadDao userSpreadDao;
    @Autowired
    ClubsDao clubsDao;
    @Autowired
    ClubUsersDao clubUsersDao;
    @Autowired
    HalfPriceDao halfPriceDao;
    @Autowired
    WithdrawsDao withdrawsDao;
    @Autowired
    WechatInfoDao wechatInfoDao;
    @Autowired
    ProductsDao productsDao;
    @Autowired
    WechatsDao wechatsDao;
    @Autowired
    WechatPayService payService;
    @Autowired
    IntegralsDao integralsDao;
    @Autowired
    MsgPushService msgPushService;
    @Autowired
    ProxyWithdrawJudgeService proxyWithdrawJudgeService;

    @Autowired
    UserService userService;
    @Autowired
    TimeConfigDao timeConfigDao;


    //微信授权所需参数
    @Value("${wechat.appid}")
    String APPID;
    @Value("${wechat.appsecret}")
    String APPSECRET;
    @Value("${wechat.backUrl}")
    String backUrl;
    @Value("${server.token}")
    String serverToken;


    @ApiOperation(value = "申请代理条件", notes = "申请代理需要金额满400元", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "applyProxyConditions", method = RequestMethod.POST)
    public TestResp applyProxyConditions(@RequestBody ApiRequest apiRequest) throws Exception {
        logger.info("客户端请求数据：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());
        int user_id = tokenAuth.getId();


        //内容体用于返回给客户端的数据格式
        ApplyProxyConditions respBody = new ApplyProxyConditions();
/*        respBody.setStatus(-1);
        respBody.setMoney(0);
        respBody.setMsg("充值金额满400元，即可申请代理！");*/

        respBody.setStatus(0);
        respBody.setMoney(0);
        respBody.setMsg("即刻申请");

        int price = 0;
        //根据user_id查询该用户是否满足申请代理条件
        //查询充值RMB总数值：
        DiamondsEntity payTotal = diamondsDao.findPayTotal(user_id);
        if (payTotal != null) price = payTotal.getPrice() / 100;


        //查询该用户是否已经成为代理，如果是代理，则不操作数据库
        GameUserEntity queryStatus = gameUserDao.findUserStatus(user_id);
        if (queryStatus != null) {
            boolean flag = false;
            GameUserEntity queryProxy = gameUserDao.findProxy(user_id);
            if (queryProxy != null) flag = true;

            if (queryStatus.getSuperior_proxy_status() == 0 && flag) {
                //查询用户充值是否满200
                if (price < 200) throw new Exception("充值金额需满200，才可能申请代理");
            } else {
                if (price < 400) {
                    throw new Exception("充值金额需满400，才可能申请代理");
                }
            }
        } else {
            if (price < 400) {
                throw new Exception("充值金额需满400，才可能申请代理");
            }
        }


        TestResp testResp = new TestResp();
        testResp.setData(respBody);
        testResp.setCode(0);

        logger.info("服务端返回数据：{}", respBody.toString());
        return testResp;
    }


    @ApiOperation(value = "申请代理", notes = "玩家申请代理信息提交", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "applyProxy", method = RequestMethod.POST)
    public TestResp applyProxy(@RequestBody ApplyProxyReq applyProxyReq) throws Exception {

        logger.info("客户端请求数据：{}", applyProxyReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(applyProxyReq.getToken());
        int user_id = tokenAuth.getId();

        logger.info("进入玩家申请代理信息提交（personal-center/api/applyProxy） user_id：{}", user_id);

        //服务端返回数据
        ApplyProxyResp applyProxyResp = new ApplyProxyResp();


        String address = applyProxyReq.getAddress();
        String name = applyProxyReq.getName();
        String phone = applyProxyReq.getPhone();


        //将得到的数据新增到 proxy_approval_records 表中
        //proxyApprovalRecordsDao.insertToProxyApproval(user_id, "", name, phone, 1, 1, address);

        //查询（user）表中是否有对应的关系
        GameUserEntity user = gameUserDao.findUserByUserId(user_id);
//        try {
        if (user == null) {
            //建立关系
            ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
        }

        //查询该用户是否已经成为代理，如果是代理，则不操作数据库
        GameUserEntity queryStatus = gameUserDao.findUserStatus(user_id);
        if (queryStatus.getUser_status() == 2 && queryStatus.getSuperior_proxy_status() == 1) {
            logger.info("已经是代理");
            applyProxyResp.setMsg("申请失败，您已经是代理！");
            applyProxyResp.setStatus(0);
        } else {
            int price = 0;
            //查询充值RMB总数值：
            DiamondsEntity payTotal = diamondsDao.findPayTotal(user_id);
            if (payTotal != null) price = payTotal.getPrice() / 100;

            if (queryStatus.getSuperior_proxy_status() == 0 && queryStatus.getFatherproxy_id() > 0) {
                logger.info("代理分享申请");
                //查询用户充值是否满200
                if (price < 200) throw new Exception("充值金额需满200，才可能申请代理");
            } else {
                if (price < 400) {
                    throw new Exception("充值金额需满400，才可能申请代理");
                }
            }

            //将该玩家调整为代理：
            gameUserDao.updateUserStatus(2, user_id);
            applyProxyResp.setMsg("申请成功，您已成为代理！");
            applyProxyResp.setStatus(1);
        }
       /* } catch (Exception e) {
            logger.error("接口（api/applyProxy）错误信息：{}", e);
            applyProxyResp.setMsg("申请失败，数据错误！");
            applyProxyResp.setStatus(0);
        }
*/
        TestResp testResp = new TestResp();
        testResp.setData(applyProxyResp);
        testResp.setCode(0);
        return testResp;
    }


    @ApiOperation(value = "查询玩家", notes = "根据user_id查询玩家", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "proxyQueryUser", method = RequestMethod.POST)
    public TestResp proxyQueryUser(@RequestBody QueryUserReq queryUserReq) throws Exception {

        logger.info("客户端请求数据：{}", queryUserReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(queryUserReq.getToken());
        String userId = queryUserReq.getUserId();

        //判断 是否为数字
        int user_id = CommonUtil.isNumeric(userId);

        if (user_id <= 0) {
            personalError("查询失败，请输入正确用户ID", -1);
        }

        //查询请求用户是否为代理，只有代理才能查询用户信息
        GameUserEntity queryUserStatus = gameUserDao.findByID(tokenAuth.getId());
        if (queryUserStatus == null || queryUserStatus.getUser_status() != 2) {
            personalError("查询失败，只有代理才能查询用户信息", -1);
        }

        //查询客户端请求的user_id是否存在
        GameUserEntity user = gameUserDao.findByUserId(user_id);
        if (user == null) personalError("无此玩家，请重新查询！", -1);


        logger.info("进入查询玩家（user/api/queryUser） user_id：{}", tokenAuth.getId());


        //服务端返回数据
        QueryUserResp queryUserResp = new QueryUserResp();


        //根据user_id查询用户信息
        GameUserEntity userInfo = getUserInfoService.getUserInfo(user_id);

        if (userInfo == null) {   //user表为空时，查询users表，users表有数据时，建立两表的关系
            ModelAndView mv = getUserInfoService.buildRelationships(user_id);
            Map<String, Object> model = mv.getModel();
            logger.info("无法从（user）表中获取数，建立user和users表关联，获得数据。");
            GameUserEntity userInf = (GameUserEntity) model.get("user");
            if (userInf != null) {
                userInfo = userInf;
            } else {
                personalError("无法获取该用户的基本信息，请重新查询！", -1);
            }
        }

        String userStatus = "玩家";
        if (userInfo.getUser_status() == 2) {
            userStatus = "代理";
        }
        String phone = "";
        if (userInfo.getPhone() != null && userInfo.getPhone() != "") {
            phone = userInfo.getPhone();
        }


        String created_time = userInfo.getCreated_at();
        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = slf.parse(created_time);


        //将查询出来的值封装返回
        queryUserResp.setCreateTime(parse.getTime() / 1000);
        queryUserResp.setUser_id(userInfo.getUsers_id());
        queryUserResp.setNickName(userInfo.getNickname());
        queryUserResp.setDiamond(userInfo.getDiamond());
        queryUserResp.setPhone(phone);
        queryUserResp.setUserStatus(userStatus);

        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(queryUserResp);
        testResp.setErrMsg("");

        return testResp;
    }


    @ApiOperation(value = "扫码添加代理前查询用户信息", notes = "根据token查询玩家ID、昵称", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "queryUser", method = RequestMethod.POST)
    public TestResp queryUser(@RequestBody QueryUserReq queryUserReq) throws Exception {

        logger.info("客户端请求数据：{}", queryUserReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(queryUserReq.getToken());

        String userId = queryUserReq.getUserId();


        //判断 是否为数字
        int user_id = CommonUtil.isNumeric(userId);

        if (user_id <= 0) {
            personalError("查询失败，请输入正确用户ID", -1);
        }

        GameUserEntity usersInfo = gameUserDao.findUsersInfo(user_id);
        if (usersInfo == null) {
            personalError("失败，无效的用户ID", -1);
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", usersInfo.getId());
        jsonObject.put("nickName", usersInfo.getNickname());

        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(jsonObject);
        testResp.setErrMsg("");

        return testResp;
    }


    @ApiOperation(value = "添加子代理", notes = "添加玩家为自己的子代理", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "addProxy", method = RequestMethod.POST)
    public TestResp addProxy(@RequestBody AddProxyReq addProxyReq) throws Exception {

        logger.info("客户端请求数据：{}", addProxyReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(addProxyReq.getToken());
        int user_id = CommonUtil.isNumeric(addProxyReq.getUserId());

        logger.info("进入添加子代理（user/api/addProxy） user_id：{}", tokenAuth.getId());
        logger.info("需要添加的子代理userId：{}", user_id);


        //查询客户端请求的user_id是否存在
        GameUserEntity user = gameUserDao.findByUserId(user_id);
        if (user == null) {
            personalError("添加失败，无此玩家，请重新查询！", -1);
        } else {
            //查询（user）表中是否有数据
            GameUserEntity userInfo = gameUserDao.findUserByUserId(user_id);
            if (userInfo == null) {
                //建立关系
                ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
            }
        }


        //查询请求用户是否为代理，2代理，3为玩家，是代理才能够添加子代理
        GameUserEntity queryUserStatus = gameUserDao.findByID(tokenAuth.getId());
        if (queryUserStatus == null || queryUserStatus.getUser_status() != 2) {
            personalError("添加失败，您当前还不是代理", -1);
        }


        //查询是否已经成为代理了；
        GameUserEntity fatherProxyId = gameUserDao.findFatherProxyId(user_id);
        if (fatherProxyId == null || fatherProxyId.getFatherproxy_id() == 0) {

            //获取当前时间，录入，
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String sysDate = df.format(new Date());

            gameUserDao.addProxy(6, tokenAuth.getId(), user_id, sysDate, 3);    //调用添加代理的接口


            //将该玩家后台权限调成代理 并将 login_status 改为 1
            //gameUserDao.updateLoginStatus(user_id);
        } else {
            personalError("添加失败，该用户已经是代理", -1);
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


    @ApiOperation(value = "我的子代理", notes = "返回所有我的子代理", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "myProxy", method = RequestMethod.POST)
    public MyProxyListResp myProxy(@RequestBody MyProxyReq myProxyReq) throws Exception {
        //MyProxyResp
        logger.info("客户端请求数据：{}", myProxyReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(myProxyReq.getToken());

        logger.info("进入添加子代理（user/api/myProxy） user_id：{}", tokenAuth.getId());

        int user_id = tokenAuth.getId();
        int limit = myProxyReq.getLimit();  //客户端请求一次获取多少条数据
        int page = myProxyReq.getPage();    //客户端请求当前页数

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;

        int offset = page * limit;

        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();

        //查询所有我的子代理
        List<GameUserEntity> myProxies = gameUserDao.proxyPaging(user_id, offset, limit);
        if (myProxies.size() != 0) {
            for (GameUserEntity myProxy : myProxies) {
                MyProxyResp myProxyResp = new MyProxyResp();

                myProxyResp.setUserId(myProxy.getUsers_id());
                myProxyResp.setNickName(myProxy.getNickname());

                //根据ID查询 钻石数量
                GameUserEntity diamond = gameUserDao.findByDiamondAndMoney(myProxy.getUsers_id());
                int diamonds = 0;
                if (diamond != null) diamonds = diamond.getDiamond();
                myProxyResp.setDiamond(diamonds);

                //查询最近登录时间
                GameUserEntity updatedAt = gameUserDao.findUsersInfo(myProxy.getUsers_id());
                long loginTime = 0;
                String updated_at = updatedAt.getUpdated_at();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                Date nowDate = df.parse(updated_at);
                if (updatedAt != null) {
                    loginTime = nowDate.getTime() / 1000;
                }
                myProxyResp.setLoginTime(loginTime);

                //封装数据
                list.add(myProxyResp);
            }
        }

        MyProxyListResp myProxyListResp = new MyProxyListResp();

        myProxyListResp.setData(list);
        myProxyListResp.setCode(0);

        if (list.size() != 0) {
            myProxyListResp.setErrMsg("成功");
        } else {
            myProxyListResp.setErrMsg("暂无数据");
        }

        return myProxyListResp;
    }


    @ApiOperation(value = "发送钻石", notes = "给玩家发送钻石", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "sendDiamond", method = RequestMethod.POST)
    public TestResp sendDiamond(@RequestBody SendDiamondReq sendDiamondReq) throws Exception {

        logger.info("客户端请求数据：{}", sendDiamondReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(sendDiamondReq.getToken());
        int user_id = sendDiamondReq.getUserId();
        int diamond = CommonUtil.isNumeric(sendDiamondReq.getDiamond());

        if (diamond <= 0) {
            personalError("请输入正确的钻石数量", -1);
        }

        logger.info("进入发送钻石（user/api/sendDiamond） user_id：{}", tokenAuth.getId());
        logger.info("需要发送钻石 user_id：{}", user_id);
        logger.info("发送钻石数量：{}", user_id);

        //查询请求用户是否为代理，2代理，3为玩家，是代理才能够添加子代理
        GameUserEntity queryUserStatus = gameUserDao.findByID(tokenAuth.getId());
        if (queryUserStatus == null || queryUserStatus.getUser_status() != 2) {
            personalError("发送失败，您当前还不是代理", -1);
        }


        //查询该用户是否 是有效UserId
        GameUserEntity usersInfo = gameUserDao.findUsersInfo(user_id);
        if (usersInfo == null) {
            personalError("发送失败，无效的ID", -1);
        }
        //查询该用户是否在（user）表中，没有数据则建立关系
        GameUserEntity userInfo = gameUserDao.findUserStatus(user_id);
        if (userInfo == null) {
            //建立关系，
            ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
        } else if (userInfo.getUser_status() != 3) {    //只能给玩家发送钻石，
            personalError("发送失败，该用户不是玩家", -1);
        }


        //判断发送者的钻石是否大于当前发送的钻石数量
        GameUserEntity diamByUserId = gameUserDao.findDiamByUserId(tokenAuth.getId());
        if (diamByUserId != null) {
            if (diamByUserId.getDiamond() < diamond) {
                personalError("发送失败，发送的钻石数量不能超过自己已有的", -1);
            }
        } else {
            personalError("无法获取您的钻石数量", -1);
        }


        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        try {
            GameUserEntity receiveUserOldDiamond = gameUserDao.findDiamByUserId(user_id);
            int old_val = 0;
            if (receiveUserOldDiamond != null) old_val = receiveUserOldDiamond.getDiamond();

            //钻石转发
            bankService.addDiamond(tokenAuth.getId(), user_id, diamond, 1, "app代理钻石的发送", 310);
            logger.info("（" + tokenAuth.getId() + "）给（" + user_id + "）成功转发 （" + diamond + "）钻石！");

            GameUserEntity receiveUserNewDiamond = gameUserDao.findDiamByUserId(user_id);
            int new_val = 0;
            if (receiveUserNewDiamond != null) new_val = receiveUserNewDiamond.getDiamond();


            String content = "用户 \"" + tokenAuth.getNickname() + "\"（" + tokenAuth.getId() + "） 给你发了" + diamond + "钻石！";
            JSONObject contentObject = new JSONObject();
            contentObject.put("userId", tokenAuth.getId());
            contentObject.put("title", "钻石发送");
            contentObject.put("content", content);
            contentObject.put("oldDiamond", old_val);
            contentObject.put("newDiamond", new_val);


            //调用消息推送接口  cmd为12时 为代理给玩家发送钻石消息
            msgPushService.newPushSysMsg(12, contentObject, user_id);


            //将钻石的发送信息记录：<发送出去的>
            incomeDao.IncomeUpdate_out(user_id, usersInfo.getNickname(), "钻石", tokenAuth.getId(), diamond, sysDate, "");

            //将钻石的收入信息记录：<收入进来的>
            incomeDao.IncomeUpdate_in(user_id, tokenAuth.getNickname(), "钻石", tokenAuth.getId(), diamond, sysDate, "");
        } catch (Exception e) {
            logger.error("应用内钻石转发接口错误信息：{}" + e);
            personalError("发送失败，内部服务错误", -1);
        }

        MyProxyListResp myProxyListResp = new MyProxyListResp();

        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData("钻石发送成功");
        testResp.setErrMsg("发送成功");

        return testResp;
    }


    @ApiOperation(value = "发送钻石记录", notes = "代理给玩家转发的钻石记录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "sendDiamondRecord", method = RequestMethod.POST)
    public TestResp sendDiamondRecord(@RequestBody SendDiamondRecordReq sendDiamondRecordReq) throws Exception {

        logger.info("客户端请求数据：{}", sendDiamondRecordReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(sendDiamondRecordReq.getToken());
        logger.info("进入发送钻石记录（user/api/sendDiamondRecord） user_id：{}", tokenAuth.getId());

        int user_id = tokenAuth.getId();
        int limit = sendDiamondRecordReq.getLimit();  //客户端请求一次获取多少条数据
        int page = sendDiamondRecordReq.getPage();    //客户端请求当前页数

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;
        int offset = page * limit;
        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();

        //分页查询发送钻石的记录
        List<IncomeEntity> incomeLists = incomeDao.findIncomeOther("", user_id, 2, offset, limit);
        if (incomeLists.size() != 0) {
            for (IncomeEntity incomeList : incomeLists) {
                SendDiamondRecordResp sendRecord = new SendDiamondRecordResp();

                int status = 0;     //0：发送；1：收入
                if (incomeList.getStreaming_name().equals("收入")) status = 1;

                //将时间改为时间戳
                String send_date = incomeList.getSend_date();

                //获取当前时间，录入，
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                Date nowDate = df.parse(send_date);


                sendRecord.setStatus(status);
                sendRecord.setUserId(incomeList.getUsers_id());
                sendRecord.setDiamond(incomeList.getNumber());
                sendRecord.setNickName(incomeList.getTarget_name());
                sendRecord.setSendTime(nowDate.getTime() / 1000);

                list.add(sendRecord);
            }
        }

        TestResp testResp = new TestResp();
        testResp.setData(list);
        testResp.setCode(0);
        testResp.setErrMsg("");

        return testResp;
    }


    @ApiOperation(value = "扫码添加子代理", notes = "app内扫码添加子代理", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "QRCodeAddProxy", method = RequestMethod.POST)
    public TestResp QRCodeAddProxy(@RequestBody QRCodeAddProxyReq qrCodeAddProxyReq) throws Exception {

        logger.info("客户端请求数据：{}", qrCodeAddProxyReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(qrCodeAddProxyReq.getToken());
        logger.info("进入扫码添加子代理（user/api/QRCodeAddProxy）扫码者user_id：{}", tokenAuth.getId());
        logger.info("分享者user_id：{}", qrCodeAddProxyReq.getUserId());


        if (qrCodeAddProxyReq.getUserId() <= 0) {
            personalError("用户ID错误，请重新生成二维码", -1);
        }


        //根据user_id查询该用户是否满足申请代理条件
        //查询充值RMB总数值：
        DiamondsEntity payTotal = diamondsDao.findPayTotal(tokenAuth.getId());
        int price = 0;
        if (payTotal != null) {
            price = payTotal.getPrice() / 100;
            if (price < 200) {
                logger.info("充值金额：{}", price);
                // personalError("充值未满200",-1);
            }
        }/*else{
            personalError("充值未满200",-1);
        }*/

        logger.info("（" + tokenAuth.getId() + "）充值金额：{}", price);


        //查询客户端请求的user_id是否存在
        GameUserEntity user = gameUserDao.findByUserId(tokenAuth.getId());
        if (user == null) {
            personalError("添加失败，无此玩家，请重新获取二维码", -1);
        } else {
            //查询（user）表中是否有数据
            GameUserEntity userInfo = gameUserDao.findUserByUserId(tokenAuth.getId());
            if (userInfo == null) {
                //建立关系
                ModelAndView modelView = getUserInfoService.buildRelationships(tokenAuth.getId());
            }
        }


        //查询请求用户是否为代理，2代理，3为玩家，是代理才能够添加子代理
        GameUserEntity queryUserStatus = gameUserDao.findByID(qrCodeAddProxyReq.getUserId());
        if (queryUserStatus == null || queryUserStatus.getUser_status() != 2) {
            personalError("添加失败，分享者当前还不是代理", -1);
        }

        //查询当前添加的用户是否为玩家 2代理，3为玩家，是玩家才能够被添加为子代理
        queryUserStatus = gameUserDao.findByID(tokenAuth.getId());
        if (queryUserStatus != null && queryUserStatus.getUser_status() != 3) {
            personalError("添加失败，您当前已经是代理", -1);
        }


        //查询是否已经成为代理了；
        GameUserEntity fatherProxyId = gameUserDao.findFatherProxyId(tokenAuth.getId());
        if (fatherProxyId == null || fatherProxyId.getFatherproxy_id() == 0 || fatherProxyId.getSuperior_proxy_status() == 0) {

            //获取当前时间，
            String sysDate = CommonUtil.creationDate("yyyy-MM-dd");

            if (price >= 200) {
                gameUserDao.QRAddProxy(6, qrCodeAddProxyReq.getUserId(), tokenAuth.getId(), sysDate, 2, 1);    //调用添加代理的接口，充值金额未满200，无效代理关系
            } else {
                gameUserDao.QRAddProxy(6, qrCodeAddProxyReq.getUserId(), tokenAuth.getId(), sysDate, 3, 0);    //调用添加代理的接口
            }

            //将该玩家后台权限调成代理 并将 login_status 改为 1
            // gameUserDao.updateLoginStatus(tokenAuth.getId());
        } else {
            personalError("添加失败，您当前已经是其他玩家的子代理", -1);
        }

        String nickName = "";
        GameUserEntity queryNickname = gameUserDao.findNickName(qrCodeAddProxyReq.getUserId());
        if (queryNickname != null) nickName = queryNickname.getNickname();


        //服务端返回数据
        AddProxyResp addProxyResp = new AddProxyResp();
        addProxyResp.setStatus(0);
        addProxyResp.setMsg("恭喜你成为 “" + nickName + "”（ID：" + qrCodeAddProxyReq.getUserId() + "）的子代理");
        if (price < 200) {
            addProxyResp.setMsg("恭喜你成为（" + qrCodeAddProxyReq.getUserId() + "）的子代理，充值金额满200才能开通代理功能");
        }


        TestResp testResp = new TestResp();
        testResp.setData(addProxyResp);
        testResp.setCode(0);
        testResp.setErrMsg("");

        return testResp;
    }


    @ApiOperation(value = "用户推广", notes = "微信授权推广新用户", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "userSpread", method = RequestMethod.GET)
    public ModelAndView userSpread(int userId, HttpSession session) throws Exception {

        int spreaderUserId = userId; //推广者 user_Id
        if (userId <= 0) {
            personalError("无效的推广者ID", -1);
        }

        //查询客户端请求的userID 是否 存在于数据库中
        GameUserEntity usersInfo = gameUserDao.findUsersInfo(userId);
        if (usersInfo == null) {
            personalError("无效的推广者ID", -1);
        }

        logger.info("进入 用户推广（user/api/userSpread） user_id：{}", spreaderUserId);


        //微信授权部分
        String backUrls = backUrl + "/spreadCallBack?spreaderUserId=" + spreaderUserId;
        ModelAndView mv = wechatService.userSpreadInfo(session, spreaderUserId);
        if (mv != null) return mv;

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID
                + "&redirect_uri=" + URLEncoder.encode(backUrls)
                + "&response_type=code"
                + "&scope=snsapi_userinfo&state=1#wechat_redirect";
        return new ModelAndView("redirect:" + url);
    }


    @ApiOperation(value = "修改推广者状态", notes = "修改推广者状态", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "changeSpreadStatus", method = RequestMethod.POST)
    public SpreadStatusRespBody changeSpreadStatus(@RequestBody SpreadStatusReq spreadStatusReq) throws Exception {

        logger.info("客户端请求数据：{}", spreadStatusReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(spreadStatusReq.getToken());

        logger.info("进入 修改推广者状态（user/api/changeSpreadStatus）token：{}", spreadStatusReq.getToken());

        if (spreadStatusReq.getUserId() <= 0) personalError("用户ID错误", -1);

        String unionid = spreadStatusReq.getUnionid();
        int userId = spreadStatusReq.getUserId();


        //先根据unionid查询是否有数据
        UserSpreadEntity userSpread = userSpreadDao.queryUserSpread(unionid);
        if (userSpread == null) {
            personalError("该用户不是通过 扫码推广 下载的用户", -1);
        }

        logger.info("user_id（" + userId + "） / unionid（" + unionid + "）关联推广者ID 入库");

        //修改数据
        userSpreadDao.updateUserSpread(unionid, userId);

        //服务端返回数据
        SpreadStatusResp spreadStatusResp = new SpreadStatusResp();
        spreadStatusResp.setStatus(0);
        spreadStatusResp.setMsg("修改成功");

        SpreadStatusRespBody testResp = new SpreadStatusRespBody();
        testResp.setData(spreadStatusResp);
        testResp.setCode(0);
        testResp.setErrMsg("");

        return testResp;
    }


    @ApiOperation(value = "推广列表", notes = "用户推广列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "userSpreadList", method = RequestMethod.POST)
    public UserSpreadListRespBody userSpreadList(@RequestBody UserSpreadListReq userSpreadListReq) throws Exception {

        logger.info("客户端请求数据：{}", userSpreadListReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(userSpreadListReq.getToken());

        logger.info("进入用户推广列表（user/api/userSpreadList） user_id：{}", tokenAuth.getId());

        int user_id = tokenAuth.getId();
        int limit = userSpreadListReq.getLimit();  //客户端请求一次获取多少条数据
        int page = userSpreadListReq.getPage();    //客户端请求当前页数

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;

        int offset = page * limit;

        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();
        UserSpreadListRespBody spreadListBody = new UserSpreadListRespBody();

        //查询该用户成功推广的用户信息
        List<UserSpreadEntity> userSpreads = userSpreadDao.findPagingByUserId(user_id, offset, limit);
        if (userSpreads.size() != 0) {
            for (UserSpreadEntity userSpread : userSpreads) {
                int downloader_user_id = userSpread.getDownloader_user_id();

                String nickname = "";
                GameUserEntity nickName = gameUserDao.findNickName(downloader_user_id);
                if (nickName != null) nickname = nickName.getNickname();

                UserSpreadListResp spreadListResp = new UserSpreadListResp();
                spreadListResp.setUserId(downloader_user_id);
                spreadListResp.setNickName(nickname);
                spreadListResp.setCreatedTime(userSpread.getUpdated_time().getTime() / 1000);
                list.add(spreadListResp);
            }
            spreadListBody.setData(list);
            spreadListBody.setCode(0);
            spreadListBody.setErrMsg("成功");
        } else {
            spreadListBody.setData(list);
            spreadListBody.setCode(0);
            spreadListBody.setErrMsg("暂无数据");
        }

        logger.info("接口：（user/api/userSpreadList）服务端返回数据：{}", spreadListBody.toString());

        return spreadListBody;
    }


    @ApiOperation(value = "我的总积分", notes = "我的总可用积分，不包括冻结积分", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "myIntegral", method = RequestMethod.POST)
    public TestResp myIntegral(@RequestBody ApiRequest apiRequest) throws Exception {

        logger.info("客户端请求数据：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        logger.info("进入我的总积分（user/api/myIntegral） user_id：{}", tokenAuth.getId());


        GameUserEntity integral = gameUserDao.findIntegralByUserId(tokenAuth.getId());

        //总可用积分
        double totalIntegral = 0;
        if (integral != null) totalIntegral = integral.getIntegral() * 1.0 / 100;

        //冻结积分
        double frozenIntegral = 0;
        IntegralRebateEntity queryFrozenIntegral = integralRebateDao.findTotalFrozenIntegral(tokenAuth.getId(), 1);
        if (queryFrozenIntegral != null) frozenIntegral = queryFrozenIntegral.getRebate_number() * 1.0 / 100;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalIntegral", totalIntegral);
        jsonObject.put("frozenIntegral", frozenIntegral);

        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(jsonObject);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/myIntegral）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "判断是玩家或代理", notes = "判断是玩家或代理", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "isProxy", method = RequestMethod.POST)
    public TestResp isProxy(@RequestBody ApiRequest apiRequest) throws Exception {

        logger.info("客户端请求数据：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());

        logger.info("进入判断是玩家或代理（user/api/isProxy） user_id：{}", tokenAuth.getId());


        JSONObject jsonObject = new JSONObject();

        GameUserEntity userStatus = gameUserDao.findUserStatus(tokenAuth.getId());
        if (userStatus != null && (userStatus.getUser_status() == 2 || userStatus.getUser_status() == 1)) {
            jsonObject.put("status", 1);
        } else {
            jsonObject.put("status", 0);
        }


        TestResp testResp = new TestResp();
        testResp.setCode(0);
        testResp.setData(jsonObject);
        testResp.setErrMsg("");

        logger.info("接口：（user/api/isProxy）服务端返回数据：{}", testResp.toString());

        return testResp;
    }


    @ApiOperation(value = "积分记录", notes = "积分来源记录列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "integralRecord", method = RequestMethod.POST)
    public IntegralRecordRespBody integralRecord(@RequestBody IntegralRecordReq integralRecordReq) throws Exception {

        logger.info("客户端请求数据：{}", integralRecordReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(integralRecordReq.getToken());

        logger.info("进入积分记录（user/api/integralRecord） user_id：{}", tokenAuth.getId());

        int user_id = tokenAuth.getId();
        int limit = integralRecordReq.getLimit();  //客户端请求一次获取多少条数据
        int page = integralRecordReq.getPage();    //客户端请求当前页数

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;

        int offset = page * limit;

        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();

        List<IntegralRebateEntity> rebateLists = integralRebateDao.findRebatePaging(user_id, offset, limit);

        for (IntegralRebateEntity rebateList : rebateLists) {
            IntegralRecordResp integralRecord = new IntegralRecordResp();
            String source = "充值返利";
            int integral_source = rebateList.getIntegral_source();
            if (integral_source == 1) {
                source = "推广返利";
            } else if (integral_source == 2) {
                source = "马王红包";
            } else if (integral_source == 3) {
                source = "俱乐部活动红包";
            }

            String dateTime = "已解冻";

            //转换为时间戳
            String created_time = rebateList.getCreated_time();
            SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date createdTime = slf.parse(created_time);

            if (rebateList.getFrozen_flag() == 1) {     //为冻结积分
                created_time = created_time.substring(0, created_time.length() - 2);
                logger.info("积分返利时间：{}", created_time);

                Date now = new Date();
                String now_date = slf.format(now);
                logger.info("现在时间：{}", now_date);

                //获取积分创建时间的后七天时间
                Date sevenDays = new Date(createdTime.getTime() + 7 * 24 * 60 * 60 * 1000);
                String sevenDaysLater = slf.format(sevenDays);
                logger.info("积分创建第七天日期：{}", sevenDaysLater);

                //积分返利时间的后七天时间和现在时间比较，大于现在时间，则解冻积分
                long d = createdTime.getTime() - sevenDays.getTime();
                logger.info("积分返利时间的后七天时间戳：{}", sevenDays.getTime() / 1000);
                logger.info("现在时间戳：{}", now.getTime() / 1000);


                long seconds = sevenDays.getTime() / 1000 - now.getTime() / 1000;
                if (seconds >= 0) {     //表示积分还没到解冻时间
                    long day = 0, hours = 0, minutes = 0;
                    day = seconds / (60 * 60 * 24);
                    seconds -= day * 60 * 60 * 24;
                    hours = seconds / (60 * 60);
                    seconds -= hours * 60 * 60;
                    minutes = seconds / 60;

                    if (day != 0 || hours != 0 || minutes != 0) dateTime = "";

                    if (day != 0) {
                        dateTime = dateTime + day + "天";
                    } else if (hours != 0) {
                        dateTime = dateTime + hours + "小时";
                    } else if (minutes != 0) {
                        dateTime = dateTime + minutes + "分";
                    }

                    dateTime += "后解冻";

                    System.out.println("总共" + day + "天" + hours + "小时" + minutes + "分");
                    System.out.println(dateTime);
                } else {      //将已解冻的积分flag设置为：0

                    //查询该用户是否存在 user表中，如果没有，则建立关系
                    GameUserEntity user = gameUserDao.findUserByUserId(rebateList.getUser_id());
                    if (user == null) {
                        //建立关系
                        ModelAndView modelView = getUserInfoService.buildRelationships(rebateList.getUser_id());
                    }

                    //根据user_id 增加积分值<总可用积分>积分解冻
                    String purpose = "用户（" + rebateList.getChild_user_id() + "）给用户（" + rebateList.getUser_id() + "）的" + source + "积分：（" + rebateList.getRebate_number() + "）解冻";
                    logger.info(purpose);

                    //将已解冻的积分flag设置为：0
                    integralRebateDao.updateFrozenFlag(rebateList.getUser_id(), rebateList.getCreated_time(), 0);

                    //积分新增 记录入库
                    userService.integralAddRecord(rebateList.getUser_id(), rebateList.getRebate_number(), 0, purpose);

                    logger.info("冻结积分解冻成功！");
                    dateTime = "已解冻";
                }
            }

            long create_time = createdTime.getTime() / 1000;
            //根据id查询update时间，如果有，则替换创建时间
            int id = rebateList.getId();
            IntegralRebateEntity queryUpdateTime = integralRebateDao.findById(id);
            if (queryUpdateTime != null) {
                Date updatedTime = slf.parse(queryUpdateTime.getUpdated_time());
                create_time = updatedTime.getTime() / 1000;
                source = "拒绝提现";
            }


            //参数封装
            integralRecord.setId(rebateList.getId());
            integralRecord.setSource(source);
            integralRecord.setCreatedTime(create_time);
            integralRecord.setState(dateTime);
            integralRecord.setNum(rebateList.getRebate_number() * 1.0 / 100);
            list.add(integralRecord);
        }


        IntegralRecordRespBody integralRecordBody = new IntegralRecordRespBody();
        integralRecordBody.setData(list);
        integralRecordBody.setCode(0);
        integralRecordBody.setErrMsg("");

        logger.info("接口：（user/api/integralRecord）服务端返回数据：{}", integralRecordBody.toString());

        return integralRecordBody;
    }


    @ApiOperation(value = "微信扫码", notes = "微信扫码跳转页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "QRCodeAddProxy", method = RequestMethod.GET)
    public ModelAndView jump() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("middle_page");
        return mv;
    }


    @ApiOperation(value = "积分提现申请", notes = "积分提现申请", httpMethod = "POST",
            response = IntegralWithdrawalsRespBody.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "integralWithdrawals", method = RequestMethod.POST)
    public Ajax integralWithdrawals(@RequestBody IntegralWithdrawalsReq integralWithdrawalsReq) throws Exception {

        logger.info("客户端请求数据：{}", integralWithdrawalsReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(integralWithdrawalsReq.getToken());
        int user_id = tokenAuth.getId();

        String openId = getUserOpenId(user_id);

        logger.info("进入积分提现申请（user/api/integralWithdrawals） user_id：{}", user_id);


        //申请提现的如果是代理，则有如下限制
        /*GameUserEntity userInfo = gameUserDao.findUserStatus(user_id);
        if (userInfo != null && userInfo.getUser_status() == 2) {

            //获取当前时间
            SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
            String createTime = slf.format(new Date());

            //判断该提现用户是否七天内是否有充值
            Boolean rechargeFlag = proxyWithdrawJudgeService.WithinSevenDaysRecharge(createTime + " 23:59:59", user_id);

            if (!rechargeFlag) throw new Exception("申请失败，您最近7天内没有充值过");

            //判断该提现用户是否最近5天连续登录过
            Boolean loginFlag = proxyWithdrawJudgeService.fiveDaysActivity(createTime, user_id);
            if (!loginFlag) throw new Exception("申请失败，您连续登录游戏不超过五天");
        }*/


        String integralStr = integralWithdrawalsReq.getIntegral();

        double cashDouble = 0.0;   //提现金额（单位：元）
        try {
            cashDouble = Double.parseDouble(integralStr);
        } catch (Exception e) {
            throw new Exception("请输入正确的提现金额！");
        }

        if (cashDouble <= 0) throw new Exception("提现积分输入不正确，请重新输入！");

        logger.info("积分转换后的double类型：{}", cashDouble);

        int integral = 0;
        if (cashDouble > 0) integral = (int) (cashDouble * 100);    //乘100 是表示 将 单位 元改为分


        //int integral = isNumeric(integralStr);
        //if (integral < 100) throw new Exception("申请失败，提现积分必须大于等于100");

        int total_integral = 0;

        //根据user_id查询总积分
        GameUserEntity totalIntegral = gameUserDao.findIntegralByUserId(user_id);
        if (totalIntegral != null) total_integral = totalIntegral.getIntegral();

        if (integral > total_integral) {
            throw new Exception("申请失败，提现积分不能超过自己当前可用积分");
        }

        //查询是否有正在审核的提现积分，audit:0 审核中
       /* WithdrawsEntity withdrawsEntity = withdrawsDao.getLastWithDraw(user_id);
        if (withdrawsEntity != null &&
                withdrawsEntity.inReview()
                ) {
            throw new Exception("申请失败，您当前有一笔提现记录");
        }*/

        int amount = integral;

        logger.info("提现的user_id：{}", user_id);
        logger.info("提现的积分：{}", integral);
        logger.info("现有总积分：{}", total_integral);
        logger.info("提现金额（单位：分）：{}", amount);

        String out_trade_no = PayUtil.createOutTradeNo();
        withdrawsDao.insertToWithdraws(user_id, integral, amount, out_trade_no, openId);

        GameUserEntity oldIntegral = gameUserDao.findIntegralByUserId(user_id);
        int old_val = 0;
        if (oldIntegral != null) old_val = oldIntegral.getIntegral();

        //扣除相应积分
        gameUserDao.deductionIntegral(integral, user_id);

        GameUserEntity newIntegral = gameUserDao.findIntegralByUserId(user_id);
        int new_val = 0;
        if (newIntegral != null) new_val = newIntegral.getIntegral();

        //记录积分操作
        integralsDao.insertToIntegrals(user_id, old_val, new_val, integral, 0, "申请提现积分扣除");

        IntegralWithdrawalsRespBody respBody = new IntegralWithdrawalsRespBody();
        respBody.setMsg("您的积分提现申请已经提交，请耐心等待审核。");

        logger.info("接口：（user/api/integralWithdrawals）服务端返回数据：{}", respBody);
        return new Ajax(respBody);
    }


    @ApiOperation(value = "查询积分提现是否审核中", notes = "判断积分提现是否审核中",
            httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = QueryReviewResp.class
    )
    @ResponseBody
    @RequestMapping(value = "queryReview", method = RequestMethod.POST)
    public Ajax queryReview(@RequestBody ApiRequest apiRequest) throws Exception {
        logger.info("客户端请求数据：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());
        int user_id = tokenAuth.getId();

        //默认无申请
        int status = QueryReviewResp.WithDrawStatus_None;
        String msg = "无提现申请";

        QueryReviewResp respBody = new QueryReviewResp();

        WithdrawsEntity withDraw = withdrawsDao.getLastWithDraw(user_id);
        if (withDraw != null) {
            int integral = withDraw.getScore();
            int audit = withDraw.getAudit();
            int pay_status = withDraw.getPay_status();

            if (pay_status == WithdrawsEntity.PayStatus_None) { //未进行支付
                status = QueryReviewResp.WithDrawStatus_In; //处理中
                switch (audit) {
                    case WithdrawsEntity.Audit_None:
                        msg = "您当前有一笔" + integral * 1.0 / 100 + "积分提现的申请正在处理，我们将马上为你处理，请耐心等待。";
                        break;
                    case WithdrawsEntity.Audit_Pass:
                        msg = "您当前已有一笔" + integral * 1.0 / 100 + "积分提现的申请已经通过，您点击收款，" +
                                "对应款项将立即自动打入您的登录微信。";
                        respBody.setWithDrawId(withDraw.getId());
                        status = QueryReviewResp.WithDrawStatus_Success; //处理中
                        break;
                    case WithdrawsEntity.Audit_Refuse:
                        status = QueryReviewResp.WithDrawStatus_None; //处理中
                        break;
                }
            }
        }

        respBody.setWithDrawStatus(status);
        respBody.setMsg(msg);
        logger.info("接口：（user/api/queryReview）服务端返回数据：{}", respBody);

        return new Ajax(respBody);
    }

    @ApiOperation(value = "积分提取现金", notes = "积分提取现金", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "withdrawCash", method = RequestMethod.POST)
    public Ajax withdrawCash(@RequestBody WithdrawCashRequest apiRequest, HttpServletRequest request) throws Exception {
        logger.info("客户端请求数据：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());
        int user_id = tokenAuth.getId();

        WithdrawsEntity withdrawsEntity = withdrawsDao.find(apiRequest.getWithDrawId());
        if (withdrawsEntity == null) {
            throw new Exception("订单号不存在");
        }
        int pay_status = WithdrawsEntity.PayStatus_Fail;
        String desc = "";

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

        return new Ajax(0, "打款成功");
    }

    private String getUserOpenId(int userId) throws Exception {
        //查询用户是否绑定关系
        WechatsEntity wechatsEntity = wechatsDao.findByUserId(userId);

        if (wechatsEntity == null) throw new Exception("您还不是游戏玩家，无法提现");
        String unionid = wechatsEntity.getUnionid();

        //根据unionid查询关联数据
        WechatInfoEntity wechatInfoEntity = wechatInfoDao.findByUnionid(unionid);
        if (wechatInfoEntity == null) {
            throw new Exception("提现通过微信转账，首次提现需要您关注公众号【聚牛潮尚玩】并点击个人中心绑定游戏ID");
        }

        return wechatInfoEntity.getOpenid();
    }


    @ApiOperation(value = "积分提现列表", notes = "积分提现列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "withdrawsRecord", method = RequestMethod.POST)
    public WithdrawsRecordRespBody withdrawsRecord(@RequestBody WithdrawsRecordReq withdrawsRecordReq) throws Exception {

        logger.info("客户端请求数据：{}", withdrawsRecordReq.toString());

        //token认证
        GameUserEntity tokenAuth = auth(withdrawsRecordReq.getToken());

        logger.info("进入积分提现列表（user/api/withdrawsRecord） user_id：{}", tokenAuth.getId());

        int user_id = tokenAuth.getId();
        int limit = withdrawsRecordReq.getLimit();  //客户端请求一次获取多少条数据
        int page = withdrawsRecordReq.getPage();    //客户端请求当前页数

        if (page <= 0) page = 0;
        if (limit <= 0) limit = 500;

        int offset = page * limit;

        logger.info("limit值：" + limit + "   page值：" + offset);

        List list = new ArrayList();


        List<WithdrawsEntity> queryWithdraws = withdrawsDao.findByUserId(user_id, offset, limit);

        if (queryWithdraws.size() != 0) {
            for (WithdrawsEntity withdraw : queryWithdraws) {
                WithdrawsRecordResp withdrawsRecord = new WithdrawsRecordResp();
                int score = withdraw.getScore();
                int amount = withdraw.getAmount();
                Date created_at = withdraw.getCreated_at();


                int audit = withdraw.getAudit();
                int pay_status = withdraw.getPay_status();

                String describe = "审核中";
                switch (pay_status) {
                    case WithdrawsEntity.PayStatus_None:

                        if (audit == WithdrawsEntity.Audit_None) {
                            describe = "审核中";
                        } else if (audit == WithdrawsEntity.Audit_Pass) {
                            describe = "审核通过";
                        } else if (audit == WithdrawsEntity.Audit_Refuse) {
                            describe = "审核拒绝";
                        }

                        break;
                    case WithdrawsEntity.PayStatus_Succ:
                        describe = "提现成功";
                        break;
                    case WithdrawsEntity.PayStatus_Fail:
                        describe = "提现失败";
                        break;
                }
                //修改小红点状态
                withdrawsDao.updateRedDot(0, withdraw.getUser_id(), withdraw.getTrade_no());


                withdrawsRecord.setId(withdraw.getId());
                withdrawsRecord.setScore(score * 1.0 / 100);
                withdrawsRecord.setAmount(amount);
                withdrawsRecord.setCreatedTime(created_at.getTime() / 1000);
                withdrawsRecord.setDescribe(describe);
                list.add(withdrawsRecord);
            }
        }

        WithdrawsRecordRespBody withdrawsRecordBody = new WithdrawsRecordRespBody();

        withdrawsRecordBody.setData(list);
        withdrawsRecordBody.setCode(0);

        if (list.size() != 0) {
            withdrawsRecordBody.setErrMsg("成功");
        } else {
            withdrawsRecordBody.setErrMsg("暂无数据");
        }

        logger.info("接口：（user/api/withdrawsRecord）服务端返回数据：{}", withdrawsRecordBody.toString());

        return withdrawsRecordBody;
    }


    @ApiOperation(value = "马王红包积分新增", notes = "马王红包积分新增",
            httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = MahjongRewardResp.class)
    @ResponseBody
    @RequestMapping(value = "mahjongReward", method = RequestMethod.POST)
    public Ajax mahjongReward(@RequestBody MahjongRewardReq mahjongRewardReq) throws Exception {

        logger.info("客户端请求数据：{}", mahjongRewardReq.toString());

        //token认证
        GameUserEntity queryToken = gameUserDao.findToken(1);
        String token = queryToken.getToken();
        if (!token.equals(mahjongRewardReq.getToken())) {
            throw new Exception("请求失败，token错误");
        }

        logger.info("进入 马王红包积分新增（user/api/mahjongReward）token：{}", mahjongRewardReq.getToken());

        int integral = mahjongRewardReq.getIntegral();
        int user_id = mahjongRewardReq.getUserId();
        if (integral <= 0 || user_id <= 0) throw new Exception("请求失败，积分值 或 获得马王红包用户ID不正确");

        String purpose = "user_id（" + user_id + "）的马王红包积分值：（" + integral + "）分：冻结中";

        logger.info(purpose);

        //积分新增 记录入库
        userService.integralAddRecord(user_id, integral, 0, purpose);

        MahjongRewardResp respBody = new MahjongRewardResp();
        respBody.setStatus(0);
        respBody.setMsg("马王红包积分新增成功");
        logger.info("接口：（user/api/mahjongReward）服务端返回数据：{}", respBody);

        return new Ajax(respBody);
    }


    @ApiOperation(value = "积分记录小红点", notes = "积分记录小红点",
            httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = MahjongRewardResp.class)
    @ResponseBody
    @RequestMapping(value = "integralRedDot", method = RequestMethod.POST)
    public Ajax integralRedDot(@RequestBody ApiRequest apiRequest) throws Exception {

        logger.info("客户端请求数据：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());
        int user_id = tokenAuth.getId();

        logger.info("进入 积分记录小红点（user/api/integralRedDot）token：{}", apiRequest.getToken());


        List integralDot = new ArrayList();
        List<IntegralRebateEntity> frozenIntegrals = integralRebateDao.queryFrozenIntegral(user_id);

        if (frozenIntegrals.size() != 0) {
            for (IntegralRebateEntity frozenIntegral : frozenIntegrals) {
                IntegralRecordResp integralRecord = new IntegralRecordResp();

                //得到积分创建时间
                String created_time = frozenIntegral.getCreated_time();
                SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date createdTime = slf.parse(created_time);

                created_time = created_time.substring(0, created_time.length() - 2);
                logger.info("积分返利时间：{}", created_time);

                Date now = new Date();
                String now_date = slf.format(now);
                logger.info("现在时间：{}", now_date);

                //获取积分创建时间的后七天时间
                Date sevenDays = new Date(createdTime.getTime() + 7 * 24 * 60 * 60 * 1000);
                String sevenDaysLater = slf.format(sevenDays);
                logger.info("积分创建第七天日期：{}", sevenDaysLater);

                //积分返利时间的后七天时间和现在时间比较，大于现在时间，则解冻积分
                long d = createdTime.getTime() - sevenDays.getTime();
                logger.info("积分返利时间的后七天时间戳：{}", sevenDays.getTime() / 1000);
                logger.info("现在时间戳：{}", now.getTime() / 1000);

                long seconds = sevenDays.getTime() / 1000 - now.getTime() / 1000;

                if (seconds < 0) {  //有积分可以解冻
                    integralDot.add(frozenIntegral.getId());   //将可以解冻的积分记录主键id返回给客户端
                    logger.info("有积分可以解冻");
                }

            }
        }


        //提现小红点
        List withdrawDot = new ArrayList();

        //根据user_id查询 审核通过或拒绝的数据
        List<WithdrawsEntity> queryWithdraws = withdrawsDao.queryWithdraws(user_id);
        if (queryWithdraws.size() != 0) {
            for (WithdrawsEntity withdraw : queryWithdraws) {
                withdrawDot.add(withdraw.getId());
            }
        }

        //查询最后一条审核数据  audit!=1 and red_dot=1
        WithdrawsEntity withdrawsEntity = withdrawsDao.queryAuditStatus(user_id, 1);
        int status = 3;
        if (withdrawsEntity != null) {
            status = withdrawsEntity.getAudit();
        }


        IntegralRedDotResp integralRedDotResp = new IntegralRedDotResp();
        integralRedDotResp.setIntegral(integralDot);
        integralRedDotResp.setWithdraws(withdrawDot);
        integralRedDotResp.setType(status);


        logger.info("接口：（user/api/integralRedDot）服务端返回数据：{}", integralRedDotResp);

        return new Ajax(integralRedDotResp);
    }


    @Deprecated
    @ApiOperation(value = "充值活动半价查询", notes = "充值活动半价查询",
            httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = QueryHalfPriceResp.class)
    @ResponseBody
    @RequestMapping(value = "queryHalfPrice", method = RequestMethod.POST)
    public Ajax queryHalfPrice(@RequestBody ClientPayReq clientPayReq) throws Exception {

        logger.info("客户端请求数据：{}", clientPayReq.toString());

        //token认证
        /*GameUserEntity tokenAuth = auth(clientPayReq.getToken());
        int user_id = tokenAuth.getId();

        logger.info("进入 充值活动半价查询（user/api/queryHalfPrice）token：{}", clientPayReq.getToken());
        logger.info("进入 充值活动半价查询（user/api/queryHalfPrice）商品ID：{}", clientPayReq.getId());


        int products_id = clientPayReq.getId();
        if (products_id <= 0) throw new Exception("错误的商品ID");


        //查询是玩家还是代理
        //根据user_id 查询 user_status
        GameUserEntity user_status = gameUserDao.findUserStatus(tokenAuth.getId());

        //根据 客户端请求的user_id 查询是否为代理
        int userStatus = 3;
        if (user_status != null) {
            userStatus = user_status.getUser_status();
        }

        //根据 客户端请求的商品ID 查询是否存在 该商品
        ProductsEntity product = null;
        if (userStatus == 3) {
            product = productsDao.findById(products_id, 0);   //玩家充值价格体系  type=0 为玩家充值
        } else {
            product = productsDao.findById(products_id, 1);   //代理充值价格体系  type=1 为代理充值
        }

        if (product == null) throw new Exception("错误的商品ID");
*/

        QueryHalfPriceResp halfPriceResp = new QueryHalfPriceResp();
        halfPriceResp.setMsg("全价支付");
        halfPriceResp.setStatus(0);


        //获取配置的活动开始时间  默认1：二次充值半价活动时间，2：俱乐部活动时间，3：马王之王活动时间
       /* TimeConfigEntity timeConfig = timeConfigDao.queryActionTimeByType(1);
        Date start_time = timeConfig.getStart_time();
        Date end_time = timeConfig.getEnd_time();

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = slf.format(new Date());
        Date now_time = slf.parse(nowTime);



        //查询活动状态，是否半价
        HalfPriceEntity actionStatus = halfPriceDao.queryActionStatus(user_id, products_id);

        if (actionStatus != null) {
            int status = actionStatus.getStatus();
            //在活动时间内 有效
            if (now_time.getTime()/1000 >= start_time.getTime()/1000 && now_time.getTime()/1000 <= end_time.getTime()/1000) {
                if (status == 1) { //半价
                    if (user_id != 5219 && user_id != 31) {
                        halfPriceResp.setMsg("您当前充值本档金额享受第二次半价\n" +
                                "当前价格为：" + product.getPay_price() * 1.0 / 2 / 100 + "元");
                        halfPriceResp.setStatus(1);
                    }
                }
            }
        }*/


        logger.info("接口：（user/api/queryHalfPrice）服务端返回数据：{}", halfPriceResp.toString());

        return new Ajax(halfPriceResp);
    }


    @ApiOperation(value = "俱乐部活动查询", notes = "俱乐部活动查询",
            httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = QueryClubActionResp.class)
    @ResponseBody
    @RequestMapping(value = "queryClubAction", method = RequestMethod.POST)
    public Ajax queryClubAction(@RequestBody ApiRequest apiRequest) throws Exception {

        logger.info("客户端请求数据：{}", apiRequest.toString());

        //token认证
        GameUserEntity tokenAuth = auth(apiRequest.getToken());
        int user_id = tokenAuth.getId();

        logger.info("进入 俱乐部活动查询（user/api/queryHalfPrice）token：{}", apiRequest.getToken());


        //查询活动时间   1：二次充值半价活动时间，2：俱乐部活动时间，3：马王之王活动时间
        TimeConfigEntity timeConfig = timeConfigDao.queryActionTimeByType(2);
        Date start_time = timeConfig.getStart_time();
        Date end_time = timeConfig.getEnd_time();


        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = slf.format(start_time);
        String endTime = slf.format(end_time);
        String nowTime = slf.format(new Date());
        Date now_time = slf.parse(nowTime);

        //现在时间如果活动开始时间，则将活动开始时间置为 循环开始时间，
        if (now_time.getTime() < start_time.getTime()) {
            now_time = start_time;
        }

        int total_member = 0;   //总成员
        int total_games = 0;    //7天房间(总局数)
        int club_id = 0;    //俱乐部主键ID
        String club_code = "";    //俱乐部Code
        int club_creator = 0;    //俱乐部创建者

        int integral = 0;
        String level = "不满足条件";
        int grade = 0;


        //根据请求的 userId 查询所有俱乐部
        List<ClubsEntity> clubs = clubsDao.queryClubByCreator(user_id);
        if (clubs.size() != 0) {
            for (ClubsEntity club : clubs) {
                //根据俱乐部主键ID查询当前有多少人，
                int member = clubUsersDao.findAllMembersByClubId(club.getId());
                int games_num = 0;

                logger.info("userId（" + club.getCreator_id() + "）的俱乐部ID（" + club.getId() + "）：总人数：" + member);

                if (member >= 20) {
                    //now_time
                    for (long i = start_time.getTime(); i <= end_time.getTime() - 6 * 24 * 60 * 60 * 1000; i += 24 * 60 * 60 * 1000) {

                        int q = 0;
                        int w = 0;
                        int r = 0;
                        int t = 0;

                        for (long j = i; j <= i + 6 * 24 * 60 * 60 * 1000; j += 24 * 60 * 60 * 1000) {
                            long start = j * 1L;
                            long end = j * 1L;

                            if (end >= end_time.getTime()) end = end_time.getTime();

                            Date start_date = new Date(start);
                            Date end_date = new Date(end);

                            String startDate = slf.format(start_date);
                            String endDate = slf.format(end_date);


                            logger.info("开始时间：" + startDate);
                            logger.info("结束时间：" + endDate);


                            //根据ClubID查询当日局数（live）表
                            int gameTimes = livesDao.findTotal(club.getId(), startDate + " 00:00:00", endDate + " 23:59:59");

                            if (gameTimes > 120) {
                                q++;
                                w++;
                                r++;
                                t++;
                            } else if (gameTimes > 90) {
                                w++;
                                r++;
                                t++;
                            } else if (gameTimes > 60) {
                                r++;
                                t++;
                            } else if (gameTimes > 30) {
                                t++;
                            }

                            logger.info("当日房间数：{}", gameTimes);
                        }

                        int l = 0;
                        if (q >= 7) {
                            l = 1;
                            club_id = club.getId();
                            club_code = club.getCode();
                            club_creator = club.getCreator_id();
                        } else if (w >= 7) {
                            l = 2;
                            club_id = club.getId();
                            club_code = club.getCode();
                            club_creator = club.getCreator_id();
                        } else if (r >= 7) {
                            l = 3;
                            club_id = club.getId();
                            club_code = club.getCode();
                            club_creator = club.getCreator_id();
                        } else if (t >= 7) {
                            l = 4;
                            club_id = club.getId();
                            club_code = club.getCode();
                            club_creator = club.getCreator_id();
                        }

                        logger.info("成员数量（member）：" + member + "，t：" + t + "，r：" + r + "，w：" + w + "，q：" + q + "，l：" + l);

                        if (member > 50 && l == 1) {
                            level = "钻石奖";
                            if (integral < 500) integral = 500;
                            if (grade > 1 || grade == 0) grade = 1;
                        } else if (member > 40 && l == 2) {
                            level = "铂金奖";
                            if (integral < 338) integral = 338;
                            if (grade > 2 || grade == 0) grade = 2;
                        } else if (member > 30 && l == 3) {
                            level = "黄金奖";
                            if (integral < 168) integral = 168;
                            if (grade > 3 || grade == 0) grade = 3;
                        } else if (member > 20 && l == 4) {
                            level = "白银奖";
                            if (integral < 88) integral = 88;
                            if (grade == 0) grade = 4;
                        } else {
                            //获取当前时间，录入，
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                            String date = df.format(new Date(i));
                            logger.info("userId（" + club.getCreator_id() + "）的俱乐部ID（" + club.getId() + "）小于30个房间、时间：{}", date);
                        }
                    }
                } else {
                    logger.info("userId（" + club.getCreator_id() + "）的俱乐部ID（" + club.getId() + "）的成员数不在20个以上，");
                }

            }
        }


        logger.info("userId（" + user_id + "）俱乐部最高总成员数：" + total_member + "，" +
                "俱乐部最高房间数：" + total_games + "档次：" + level);

        logger.info("俱乐部主键ID：（" + club_id + "）俱乐部Code：" + club_code + "，俱乐部创建者：" + club_creator);


        QueryClubActionResp clubActionResp = new QueryClubActionResp();

        if (integral != 0) {

            logger.info(club_creator + "：的俱乐部活动获得积分：" + integral);

            //根据今天时间查询数据，删除重复数据
            //clubsActionDao.delByCreateTime(nowTime + " 00:00:00", nowTime + " 23:59:59");


            //根据俱乐部创建者ID查询活动获得积分，用于判断当前活动获得的积分值是否大于表中已经获得的积分值
            ClubsActionEntity clubsActionEntity = clubsActionDao.queryHighestRewardIntegral(club_creator);
            int reward_integral = 0;
            if (clubsActionEntity != null) {
                reward_integral = clubsActionEntity.getReward_integral();
            }

            if (integral > reward_integral) {
                logger.info(club_creator + "获得的活动积分值大于当前表中存在的积分值");
                if (clubsActionEntity != null) {

                    //数据库中已有数据，直接update
                    clubsActionDao.updateClubsAction(clubsActionEntity.getId(), club_id, club_code, club_creator, grade, integral * 100);
                } else {
                    //将满足俱乐部活动条件的数据存入数据库中
                    clubsActionDao.insertToClubsAction(club_id, club_code, club_creator, grade, integral * 100, 2);

                }

            }


            clubActionResp.setContent("您的俱乐部目前符合" + level + "，可获得奖金：" + integral);
            clubActionResp.setStatus(1);
            ClubsActionEntity clubsAction = clubsActionDao.queryHighestRewardIntegral(club_creator);
            if (clubsAction != null) {
                int lev = clubsAction.getLevel();
                String levStr = "不满足条件";
                if (lev == 1) {
                    levStr = "钻石奖";
                } else if (lev == 2) {
                    levStr = "铂金奖";
                } else if (lev == 3) {
                    levStr = "黄金奖";
                } else if (lev == 4) {
                    levStr = "白银奖";
                }
                clubActionResp.setContent("您的俱乐部目前符合" + levStr + "，可获得奖金：" + clubsAction.getReward_integral() * 1.0 / 100);
                clubActionResp.setStatus(1);
            } else {
                clubActionResp.setContent("对不起，您暂未符合领取条件。");
                clubActionResp.setStatus(0);
            }
        } else {
            clubActionResp.setContent("对不起，您暂未符合领取条件。");
            clubActionResp.setStatus(0);
        }


        logger.info("接口：（user/api/queryClubAction）服务端返回数据：{}", clubActionResp.toString());

        return new Ajax(clubActionResp);
    }


}

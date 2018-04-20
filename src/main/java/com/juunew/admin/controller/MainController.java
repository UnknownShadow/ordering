package com.juunew.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.entity.api.*;
import com.juunew.admin.services.MsgRecordsProcessor;
import com.juunew.admin.services.*;
import com.juunew.admin.utils.CommonUtil;
import com.juunew.admin.utils.EhcacheUtils;
import com.juunew.admin.utils.MD5Util;
import com.juunew.admin.wechat.HttpReqUtil;
import io.swagger.annotations.ApiOperation;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class MainController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;
    @Autowired
    UserDao userdao;
    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    AllotDao allotDao;
    @Autowired
    BankService bankService;
    @Autowired
    LocalUserDao localUserDao;
    @Autowired
    IncomeDao incomeDao;
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    Status_EditorDao status_editorDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    TemporaryDao temporaryDao;
    @Autowired
    Financial_AuditDao financial_auditDao;
    @Autowired
    MsgPushService msgPush;
    @Autowired
    SmsVerificationService smsVerification;
    @Autowired
    MsgPushRecordDao msgPushRecordDao;
    @Autowired
    AnnouncementsDao announcementsDao;
    @Autowired
    ControlTimesDao controlTimesDao;
    @Autowired
    DiamondsDao diamondsDao;
    @Autowired
    ClubUsersDao clubUsersDao;
    @Autowired
    ClubsDao clubsDao;
    @Autowired
    HalfPriceDao halfPriceDao;
    @Autowired
    ProxyApprovalRecordsDao proxyApprovalRecordsDao;

    @Autowired
    IntegralsDao integralsDao;

    @Autowired
    ProductsDao productsDao;
    @Autowired
    MsgRecordsProcessor msgRecordsProcessor;
    @Autowired
    LivesDao livesDao;

    @Autowired
    WechatRechargeDao wechatRechargeDao;
    @Autowired
    WechatInfoDao wechatInfoDao;
    @Autowired
    WechatsDao wechatsDao;
    @Autowired
    RealtimeOnlineHistoriesDao realtimeOnlineHistoriesDao;
    @Autowired
    LogFilesDao logFilesDao;
    @Autowired
    SystemMsgsDao systemMsgsDao;
    @Autowired
    SystemMsgRecordsDao systemMsgRecordsDao;
    @Autowired
    IntegralRebateDao integralRebateDao;
    @Autowired
    WithdrawalsRecordDao withdrawalsRecordDao;
    @Autowired
    DiamondRatioDao diamondRatioDao;
    @Autowired
    DiamondGiftDao diamondGiftDao;
    @Autowired
    WechatService wechatService;
    @Autowired
    JoinClubService joinClubService;

    @Autowired
    ChannelDao channelDao;
    @Autowired
    FeedbackInfoDao feedbackInfoDao;

    @Autowired
    GetUserInfoService getUserInfoService;

    @Autowired
    MsgSendingService msgSendingService;



    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(String username, String password, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception, ServletException {
        ModelAndView mv = new ModelAndView();
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "E10ADC3949BA";
        }
        logger.info("用户名：{}", username);

        String pass = MD5Util.MD5(password);

        UserEntity user = userdao.findByUserNameAndPassword(username, pass);

        mv.setViewName("login");
        mv.addObject("error", "用户名或密码错误,请重新输入！");
        mv.addObject("username", username);

        if (user != null) {
            if (user.getUsername().equals(username) && user.getPassword().equals(pass)) {
                session.setAttribute("users_id", user.getUsers_id());
                session.setAttribute("username", user.getUsername());
                logger.info("登录成功: {}", username);

                //登录成功后将登录时间记录下来
                gameUserDao.updateLoginTime(user.getUsers_id());

                //查询登录用户前端页面显示
                DisplayControlEntity displayControl = displayControl(user.getUsers_id(), username);


                //意向代理和问题反馈小红点
                int applyAgent = feedbackInfoDao.queryTrackPointByType(FeedbackInfoEntity.ApplyAgentType, 1);
                int problemFeedback = feedbackInfoDao.queryTrackPointByType(FeedbackInfoEntity.ProblemFeedbackType, 1);


                mv.addObject("applyAgent", applyAgent);
                mv.addObject("problemFeedback", problemFeedback);

                mv.addObject("role", displayControl.getRole());
                mv.addObject("userStatus", displayControl.getUser_status());
                mv.setViewName("index");
                return mv;
            } else {
                mv.addObject("error", "用户名或密码错误,请重新输入！");
                mv.setViewName("login");
                return mv;
            }
        }
        return mv;
    }

    //处理前端显示控制
    private DisplayControlEntity displayControl(int users_id, String username) {
        DisplayControlEntity displayControl = new DisplayControlEntity();

        //查询数据库中是否有该页面的访问权限
        RoleEntity getRole = roleDao.findByRole(username);
        int role = 6;
        if (getRole != null) role = getRole.getId();

        //根据users_id查询出该登录的用户是什么游戏用户身份权限(user_status)
        GameUserEntity user_status = gameUserDao.findUserStatus(users_id);
        int userStatus = 3;
        if (user_status != null) userStatus = user_status.getUser_status();

        logger.info("（" + users_id + "）访问权限为：{}", getRole.getId());

        displayControl.setRole(role);
        displayControl.setUser_status(userStatus);

        return displayControl;
    }

    //进入首页
    @RequestMapping(value = {"/", "index"})
    public ModelAndView index(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView();

        int users_id = (int) session.getAttribute("users_id");
        String username = (String) session.getAttribute("username");

        //查询登录用户前端页面显示
        DisplayControlEntity displayControl = displayControl(users_id, username);

        mv.addObject("role", displayControl.getRole());
        mv.addObject("userStatus", displayControl.getUser_status());
        mv.setViewName("index");
        return mv;
    }

    //跳首页
    @RequestMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("main");
    }

    /**
     * 进入记录查询界面
     */
    @RequestMapping("record")
    public String record(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
        //得到登录的用户名
        String username = (String) session.getAttribute("username");

        //查询数据库中是否有该页面的访问权限
        List<PermissionEntity> permissions = permissionDao.all(username);
        for (PermissionEntity permission : permissions) {
            logger.debug("访问权限为：" + permission.getUrl());
            if (permission.getUrl().equals("record")) {
                return "record";
            }
        }

        req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
        return null;
    }


    /**
     * 记录查询页面的模分查询
     */
    @RequestMapping("search_record")
    public ModelAndView search_record(Integer page, String user_id, String compositor, String proxy_date, String proxy_date_end, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        //获取用户名；
        String username = (String) session.getAttribute("username");

        //根据用户名查users_id
        UserEntity usersId = userdao.findUsersId(username);
        int self_id = usersId.getUsers_id();

        int composit = Integer.parseInt(compositor);    //排序

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int limit = 10;
        if (page == null) page = 1;

        mv.addObject("user_id", user_id);
        mv.addObject("proxy_date", proxy_date);
        mv.addObject("proxy_date_end", proxy_date_end);
        mv.addObject("compositor", compositor);

        String ordered = "";    //排序方式；
        if (composit == 1) {
            ordered = "user_id";
        } else if (composit == 2) {
            ordered = "send_date";
        } else if (composit == 3) {
            ordered = "number";
        }


        //必须使用模糊查询得到数据条数：total；分两种情况；分为有日期和没日期的
        List<IncomeEntity> incomeTotal = incomeDao.findIncomeTotalOther(user_id, self_id);
        if (proxy_date != "" && proxy_date_end != "") {
            incomeTotal = incomeDao.findIncomeTotal(user_id, proxy_date, proxy_date_end, self_id);
        }

        int sum = incomeTotal.size();           //查询所有子代理个数

        int offset = (page - 1) * limit;

        //对所有子代理数据进行模糊加分页查询；

        List<IncomeEntity> incomeList = incomeDao.findIncomeOther(user_id, self_id, composit, offset, limit);
        if (proxy_date != "" && proxy_date_end != "") {
            incomeList = incomeDao.findIncome(user_id, proxy_date, proxy_date_end, self_id, composit, offset, limit);
        }

        mv.addObject("incomeList", incomeList);


        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台


        mv.setViewName("record");
        return mv;
    }


    /**
     * 进入玩家结果查询页面
     */
    @RequestMapping("/games")
    public ModelAndView gamerPage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //得到登录的用户名
        /*String username = (String) session.getAttribute("username");

        //查询数据库中是否有该页面的访问权限
        List<PermissionEntity> permissions = permissionDao.all(username);
        for (PermissionEntity permission : permissions) {
            logger.debug("访问权限为：" + permission.getUrl());
            if (permission.getUrl().equals("games")) {
                return "gamer";
            }
        }*/
        //req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
        return new ModelAndView("gamer");
    }

    /**
     * 进入创建公告页面
     */
    @RequestMapping("/notices")
    public String noticePage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //得到登录的用户名
        String username = (String) session.getAttribute("username");

        //查询数据库中是否有该页面的访问权限
        List<PermissionEntity> permissions = permissionDao.all(username);
        for (PermissionEntity permission : permissions) {
            logger.debug("访问权限为：" + permission.getUrl());
            if (permission.getUrl().equals("notices")) {
                return "notice";
            }
        }

        req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
        return null;
    }

    /**
     * 进入创建消息奖励页面
     */
    @RequestMapping("/messages")
    public ModelAndView messagesPage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView();
       /* //得到登录的用户名
        String username = (String) session.getAttribute("username");

        //查询数据库中是否有该页面的访问权限
        List<PermissionEntity> permissions = permissionDao.all(username);
        for (PermissionEntity permission : permissions) {
            logger.debug("访问权限为：" + permission.getUrl());
            if (permission.getUrl().equals("messages")) {
                return "messages";
            }
        }

        req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
        return null;*/
        mv.setViewName("messages");
        return mv;
    }


    /**
     * 进入代理管理页面
     */
    @RequestMapping("/proxy")
    public ModelAndView proxyPage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView();
       /* //得到登录的用户名
        String username = (String) session.getAttribute("username");

        //查询数据库中是否有该页面的访问权限
        List<PermissionEntity> permissions = permissionDao.all(username);
        for (PermissionEntity permission : permissions) {
            logger.debug("访问权限为：" + permission.getUrl());
            if (permission.getUrl().equals("proxy")) {
                //查询用户基本信息
                List<GameUserEntity> userList = gameUserDao.findByUsername(username);

                mv.addObject("userstatus", userList.get(0).getUser_status());

                mv.setViewName("proxy-ad");
                return mv;
            }
        }
*/
        mv.setViewName("proxy-ad");
        return mv;
    }


    /**
     * 进入代理发送钻石或金币页面
     */
    @RequestMapping("/sends")
    public ModelAndView send(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ModelAndView mv = new ModelAndView();
        //得到登录的用户名
        String username = (String) session.getAttribute("username");

        int id = (int) session.getAttribute("users_id");

        //查询数据库中是否有该页面的访问权限
        List<PermissionEntity> permissions = permissionDao.all(username);
        for (PermissionEntity permission : permissions) {
            logger.debug("访问权限为：" + permission.getUrl());
            if (permission.getUrl().equals("sends")) {

                //查询当前登录的用户基本信息
                GameUserEntity currentUser = getUserInfoService.getUserInfo(id);

                mv.addObject("currentUser", currentUser);
                mv.setViewName("sends");
                return mv;
            }
        }
        req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
        return null;
    }

    //代理中子代理信息及添加子代理
    @RequestMapping("child_proxy")
    public ModelAndView findUse() {
        return new ModelAndView("child_proxy");
    }


    //所有页面中我的资料
    @RequestMapping("information_all")
    public ModelAndView infor(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView();
        //得到登录的用户名
        String username = (String) session.getAttribute("username");

        //查询用户基本信息
        List<GameUserEntity> userList = gameUserDao.findByUsername(username);

        for (GameUserEntity userlists : userList) {
            GameUserEntity diamondAndMoney = gameUserDao.findByDiamondAndMoney(userlists.getUsers_id());
            userlists.setDiamond(diamondAndMoney.getDiamond());
            userlists.setMoney(diamondAndMoney.getMoney());
        }

        int id = userList.get(0).getUsers_id();
        System.out.println();

        //查询子代理个数
        List<GameUserEntity> childproxy = gameUserDao.findchildproxy(id);

        //查询用户权限
        GameUserEntity perm = gameUserDao.findPermisssion(id);

        mv.addObject("userList", userList.get(0));
        mv.addObject("childproxy", childproxy.size());
        mv.addObject("permission", perm);

        logger.info("子代理数: {}", childproxy.size());

        mv.setViewName("information_all");
        return mv;

    }


    /**
     * 代理中我的资料
     */
    @RequestMapping("information")
    public ModelAndView info(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
        ModelAndView modelView = new ModelAndView();

        int id = (int) session.getAttribute("users_id");

        //查询用户信息
        GameUserEntity userInfo = getUserInfoService.getUserInfo(id);

        modelView.addObject("userInfo", userInfo);

        modelView.setViewName("information");
        return modelView;
    }

    /**
     * 代理中我的资料  <充值自动升级>
     */
  /*  @RequestMapping("information")
    public ModelAndView info(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
        ModelAndView modelView = new ModelAndView();

        //得到登录的用户名
        String username = (String) session.getAttribute("username");


        //查询用户基本信息
        List<GameUserEntity> userList = gameUserDao.findByUsername(username);

        int id = userList.get(0).getUsers_id();

        //查询用户身份
        int user_status = userList.get(0).getUser_status();

        //根据身份进行判断  如果是代理 则根据升级条件进行自动升级为至尊合伙人，
        //如果是至尊合伙人 则根据升级条件 往上升级
        if (user_status == 2) {   // 2: 代理

            //查询该用户所有充值的钻石数量
            DiamondsEntity byRecharge = diamondsDao.findByRecharge(id);

            if (byRecharge != null) {
                int diamond = byRecharge.getDiamond();

                int condition = 500000;

                if (condition - diamond > 0) {  //相减为正 代表不满足升级条件，为负 可以将代理升级为至尊5；
                    modelView.addObject("condition", condition - diamond);
                } else {
                    //将代理升级为至尊5
                    gameUserDao.updateUserStatus(1, id);
                }

                logger.debug("用户总充值数：" + diamond);
            } else {
                modelView.addObject("condition", 500000);
            }

        } else if (user_status == 1) {   // 1: 至尊5

            //查询总充值钱数；满足条件则往上升级；对(orders 表) 操作
            DiamondsEntity byOrdersPrice = diamondsDao.findByOrdersPrice(id);

            //按月查询总充值钱数
            List<DiamondsEntity> ordersMonthPrice = diamondsDao.findByOrdersMonthPrice(id);

            if (byOrdersPrice != null) {
                int price = byOrdersPrice.getPrice() / 100;

                if (price >= 50000) {
                    //升级成为 至尊4
                    gameUserDao.updateUserStatus(4, id);
                }

                logger.debug("用户身份为至尊5时：总充值的钱数：" + price);
            }

            if (ordersMonthPrice.size() != 0) {
                for (int i = 0; i < ordersMonthPrice.size(); i++) {

                    int mothPrice = ordersMonthPrice.get(i).getPrice() / 100;
                    logger.debug("按月查询总充值数：" + mothPrice);

                    if (mothPrice >= 20000) {
                        //升级成为 至尊4
                        gameUserDao.updateUserStatus(4, id);
                        break;
                    }

                }
            }

        } else if (user_status == 4) {   // 4: 至尊4

            //查询总充值钱数；满足条件则往上升级；对(orders 表) 操作
            DiamondsEntity byOrdersPrice = diamondsDao.findByOrdersPrice(id);

            //按月查询总充值钱数
            List<DiamondsEntity> ordersMonthPrice = diamondsDao.findByOrdersMonthPrice(id);

            if (byOrdersPrice != null) {
                int price = byOrdersPrice.getPrice() / 100;

                if (price >= 80000) {
                    //升级成为 至尊4
                    gameUserDao.updateUserStatus(5, id);
                }

                logger.debug("用户身份为至尊5时：总充值的钱数：" + price);
            }

            if (ordersMonthPrice.size() != 0) {
                for (int i = 0; i < ordersMonthPrice.size(); i++) {

                    int mothPrice = ordersMonthPrice.get(i).getPrice() / 100;
                    logger.debug("按月查询总充值数：" + mothPrice);

                    if (mothPrice >= 30000) {
                        //升级成为 至尊4
                        gameUserDao.updateUserStatus(5, id);
                        break;
                    }

                }
            }

        } else if (user_status == 5) {   // 5: 至尊3

            //查询总充值钱数；满足条件则往上升级；对(orders 表) 操作
            DiamondsEntity byOrdersPrice = diamondsDao.findByOrdersPrice(id);

            //按月查询总充值钱数
            List<DiamondsEntity> ordersMonthPrice = diamondsDao.findByOrdersMonthPrice(id);

            if (byOrdersPrice != null) {
                int price = byOrdersPrice.getPrice() / 100;

                if (price >= 100000) {
                    //升级成为 至尊4
                    gameUserDao.updateUserStatus(6, id);
                }

                logger.debug("用户身份为至尊5时：总充值的钱数：" + price);
            }

            if (ordersMonthPrice.size() != 0) {
                for (int i = 0; i < ordersMonthPrice.size(); i++) {

                    int mothPrice = ordersMonthPrice.get(i).getPrice() / 100;
                    logger.debug("按月查询总充值数：" + mothPrice);

                    if (mothPrice >= 40000) {
                        //升级成为 至尊4
                        gameUserDao.updateUserStatus(6, id);
                        break;
                    }

                }
            }

        } else if (user_status == 6) {   // 6: 至尊2

            //查询总充值钱数；满足条件则往上升级；对(orders 表) 操作
            DiamondsEntity byOrdersPrice = diamondsDao.findByOrdersPrice(id);

            //按月查询总充值钱数
            List<DiamondsEntity> ordersMonthPrice = diamondsDao.findByOrdersMonthPrice(id);

            if (byOrdersPrice != null) {
                int price = byOrdersPrice.getPrice() / 100;

                if (price >= 120000) {
                    //升级成为 至尊4
                    gameUserDao.updateUserStatus(7, id);
                }

                logger.debug("用户身份为至尊5时：总充值的钱数：" + price);
            }

            if (ordersMonthPrice.size() != 0) {
                for (int i = 0; i < ordersMonthPrice.size(); i++) {

                    int mothPrice = ordersMonthPrice.get(i).getPrice() / 100;
                    logger.debug("按月查询总充值数：" + mothPrice);

                    if (mothPrice >= 50000) {
                        //升级成为 至尊4
                        gameUserDao.updateUserStatus(7, id);
                        break;
                    }

                }
            }

        } else if (user_status == 7) {   // 7: 至尊1

            //查询总充值钱数；满足条件则往上升级；对(orders 表) 操作
            DiamondsEntity byOrdersPrice = diamondsDao.findByOrdersPrice(id);

            //按月查询总充值钱数
            List<DiamondsEntity> ordersMonthPrice = diamondsDao.findByOrdersMonthPrice(id);

            if (byOrdersPrice != null) {
                int price = byOrdersPrice.getPrice() / 100;

                if (price >= 250000) {
                    //升级成为 至尊4
                    gameUserDao.updateUserStatus(8, id);
                }

                logger.debug("用户身份为至尊5时：总充值的钱数：" + price);
            }

            if (ordersMonthPrice.size() != 0) {
                for (int i = 0; i < ordersMonthPrice.size(); i++) {

                    int mothPrice = ordersMonthPrice.get(i).getPrice() / 100;
                    logger.debug("按月查询总充值数：" + mothPrice);

                    if (mothPrice >= 100000) {
                        //升级成为 至尊4
                        gameUserDao.updateUserStatus(8, id);
                        break;
                    }

                }
            }

        }


        //查询修改后的信息将其显示在前端页面
        userList = gameUserDao.findByUsername(username);

        for (GameUserEntity userlists : userList) {
            GameUserEntity diamondAndMoney = gameUserDao.findByDiamondAndMoney(userlists.getUsers_id());
            userlists.setDiamond(diamondAndMoney.getDiamond());
            userlists.setMoney(diamondAndMoney.getMoney());
        }

        id = userList.get(0).getUsers_id();

        //查询子代理个数
        List<GameUserEntity> childproxy = gameUserDao.findchildproxy(id);

        //查询用户后台权限
        GameUserEntity perm = gameUserDao.findPermisssion(id);

        //查询用户身份
        user_status = userList.get(0).getUser_status();


        modelView.addObject("userList", userList.get(0));
        modelView.addObject("childproxy", childproxy.size());
        modelView.addObject("permission", perm);

        modelView.setViewName("information");
        return modelView;
    }*/


    /**
     * 进入子代理销售查询页面
     */
    @RequestMapping("sale_check")
    public String sale() {
        return "sale_check";
    }

    /**
     * 子代理销售查询
     */
    @RequestMapping("searches")
    public ModelAndView check(String proxy_date, String proxy_date_end, String compositor, HttpSession session) {

        int users_id = (int) session.getAttribute("users_id");        //得到当前登录用户ID

        List lists = new ArrayList();

        //先查出所有子代理的ID，
        List<GameUserEntity> byProxyId = userdao.findByProxyId(users_id);
        for (GameUserEntity proxy : byProxyId) {
            //根据users_id查询出该用户的钻石
            GameUserEntity proxyDiamond = gameUserDao.findByDiamondAndMoney(proxy.getId());
            int diamond = 0;
            if (proxyDiamond != null) {
                diamond = proxyDiamond.getDiamond();
            }

            //根据子代理ID查询income表中的数据
            List<IncomeEntity> incomeDataout = incomeDao.findoutcomeData(proxy.getId(), "发送");
            List<IncomeEntity> incomeDatain = incomeDao.findIncomeData(proxy.getId(), "收入");

            //如果有日期，就按日期与时间段查询
            if (proxy_date != "" && proxy_date_end != "") {
                //根据子代理ID查询income表中的数据
                List<IncomeEntity> income_out = incomeDao.findoutcomeData_date(proxy.getId(), "发送", proxy_date, proxy_date_end);
                List<IncomeEntity> income_in = incomeDao.findIncomeData_date(proxy.getId(), "收入", proxy_date, proxy_date_end);

                if (incomeDatain.size() != 0 || incomeDataout.size() != 0) {

                    Map map = new HashMap();
                    map.put("users_id", proxy.getId());        //子代理id
                    map.put("nickname", proxy.getNickname());    //昵称
                    map.put("diamond", diamond);                    //当前钻石总数
                    map.put("out_count", income_out.size());        //发送钻石笔数
                    map.put("in_count", income_in.size());        //收入钻石笔数
                    int out_num = 0;
                    for (IncomeEntity income : income_out) {
                        out_num += income.getNumber();
                    }
                    map.put("out_num", out_num);                //发送钻石总数
                    double s = 1;
                    if (incomeDataout.size() != 0) {
                        s = incomeDataout.size() * 1.0;
                    }
                    int sd = (int) (((out_num * 1.0) / s) * 100);
                    double sd1 = sd / 100.0;
                    map.put("avg", sd1);        //发送钻石的平均值

                    int in_num = 0;
                    for (IncomeEntity income : income_in) {
                        in_num += income.getNumber();
                    }
                    map.put("in_num", in_num);                //收入钻石总数
                    System.out.println("时间段查询");

                    lists.add(map);
                }

            } else if (incomeDatain.size() != 0 || incomeDataout.size() != 0) {
                //根据子代理ID查出该子代理有多少钻石
                //Integer diamond = gameUserDao.diamond(incomeDataout.get(0).getUsers_id());
                Map map = new HashMap();

                map.put("users_id", proxy.getId());        //子代理id
                map.put("nickname", proxy.getNickname());    //昵称
                map.put("diamond", diamond);                    //当前钻石总数
                map.put("out_count", incomeDataout.size());        //发送钻石笔数
                map.put("in_count", incomeDatain.size());        //收入钻石笔数
                int out_num = 0;
                if (incomeDataout.size() != 0) {
                    for (IncomeEntity income : incomeDataout) {
                        out_num += income.getNumber();
                    }
                }
                map.put("out_num", out_num);                //发送钻石总数
                double s = 1;
                if (incomeDataout.size() != 0) {
                    s = incomeDataout.size() * 1.0;
                }
                int sd = (int) (((out_num * 1.0) / s) * 100);
                double sd1 = sd / 100.0;
                map.put("avg", sd1);        //发送钻石的平均值

                int in_num = 0;
                for (IncomeEntity income : incomeDatain) {
                    in_num += income.getNumber();
                }

                map.put("in_num", in_num);                //收入钻石总数

                lists.add(map);
            }
        }

        //获取当前时间，录入，
        String sysDate = CommonUtil.creationDate("yyyy-MM-dd HH:mm:ss");

        for (Object list : lists) {
            int in_num = 0;
            double avg = 0.0;
            int out_num = 0;
            int in_count = 0;
            int out_count = 0;

            if (((Map) list).get("in_num") != null) {
                in_num = (int) ((Map) list).get("in_num");
            }
            if (((Map) list).get("avg") != null) {
                avg = (double) ((Map) list).get("avg");
            }
            if (((Map) list).get("out_num") != null) {
                out_num = (int) ((Map) list).get("out_num");
            }
            if (((Map) list).get("in_count") != null) {
                in_count = (int) ((Map) list).get("in_count");
            }
            if (((Map) list).get("out_count") != null) {
                out_count = (int) ((Map) list).get("out_count");
            }
            temporaryDao.insertToTemp(sysDate, (int) ((Map) list).get("users_id"), (String) ((Map) list).get("nickname"), (int) ((Map) list).get("diamond"), out_count, in_count, out_num, avg, in_num);
        }


        List<TemporaryEntity> tempByDates = temporaryDao.findTempByDate(sysDate, Integer.parseInt(compositor));

        //将临时表中的数据删除
        temporaryDao.delTempByDate(sysDate);
        ModelAndView mv = new ModelAndView();
        mv.addObject("compositor", compositor);
        mv.addObject("proxy_date", proxy_date);
        mv.addObject("proxy_date_end", proxy_date_end);
        mv.addObject("lists", lists);
        mv.setViewName("sale_check");
        return mv;
    }


    /**
     * 进入收入查询页面
     */
    @RequestMapping("/proceeds")
    public String proceedsPage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //得到登录的用户名
        String username = (String) session.getAttribute("username");

        //查询数据库中是否有该页面的访问权限
        List<PermissionEntity> permissions = permissionDao.all(username);
        for (PermissionEntity permission : permissions) {
            logger.debug("访问权限为：" + permission.getUrl());
            if (permission.getUrl().equals("proceeds")) {
                return "proceeds-ad";
            }
        }

        //在数据库中查询出username，再判断是否有权限操作；

        req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
        return null;
    }


    /**
     * 根据游戏玩家id查询数据后台处理
     */
    @RequestMapping("/gamer")
    public ModelAndView search(String user_id, HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView();
        int id = Integer.parseInt(user_id);
        List<GameUserEntity> user = gameUserDao.findById(id);
        GameUserEntity permisssion = gameUserDao.findPermisssion(id);
        int s = 0;
        if (user.size() != 0) {
            for (int i = 0; i < user.size(); i++) {
                if (user.get(i).getNickname() != null) {
                    mv.addObject("permisssion", permisssion);
                    mv.addObject("userList", user.get(i));
                } else {
                    s++;
                }
            }
        }

        if (s >= user.size() || user == null) {
            GameUserEntity usersmsg = gameUserDao.findusers(id);
            if (usersmsg != null) {

                String pass = MD5Util.MD5("123456");
                gameUserDao.insertData(id + "", pass, id);
                List<GameUserEntity> use = gameUserDao.findById(id);
                GameUserEntity perm = gameUserDao.findPermisssion(id);
                if (use.size() != 0) {
                    for (int j = 0; j < use.size(); j++) {
                        if (use.get(j).getNickname() != null) {
                            mv.addObject("permisssion", perm);
                            mv.addObject("userList", use.get(j));
                        }
                    }
                }
            } else {
                user.clear();
                mv.addObject("error", "对不起，没有查询到您输入的ID，请正确输入！");
            }
        }

        mv.setViewName("gamer");
        return mv;
    }

    /**
     * 此方法为游戏玩家查询保存处理
     */
    @RequestMapping("/save")
    public void saveDate(@RequestParam(value = "id") String id, @RequestParam(value = "diamond") String diamond,
                         @RequestParam(value = "nickname") String nickname, @RequestParam(value = "RMB") String RMB,
                         @RequestParam(value = "pay_status") String pay_status, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView();

        int send_id = (int) session.getAttribute("users_id");

        int users_id = Integer.parseInt(id);        //游戏玩家id

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间


        String jsons = "";

        if (!RMB.equals("") && !diamond.equals("")) {

            int payStatus = 1;
            if (pay_status.equals("2")) {
                payStatus = 0;
            }

            int rmb = Integer.parseInt(RMB);

            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//用来充当ID
            String serial_number = df1.format(new Date());// new Date()为获取当前系统时间
            serial_number = serial_number.replaceAll("-", "");
            serial_number = serial_number + "0000";


            int diamonds = Integer.parseInt(diamond);

            jsons = diamond;

            int financial_audit_id = 0;
            //用于获取新增的ID号   serial_number,
            Financial_DailyEntity api = new Financial_DailyEntity();

            if (diamonds > 0) {

                //将数据插入到财务审批的数据表中
                financial_auditDao.insertFinancial_Audit(send_id, "系统", users_id, nickname, diamonds, rmb, 2, sysDate, payStatus, api);


                //日期+ID 组成 流水号：
                long ser = Long.parseLong(serial_number);
                long numb = api.getId();
                serial_number = (ser + numb) + "";
                financial_auditDao.updateFinancial_Audit(serial_number, api.getId());
                financial_audit_id = api.getId();
            } else {

                try {
                    int diamond_s = bankService.addDiamond(1, users_id, diamonds, 1, "平台充值", 301);

                    //将数据插入到财务审批的数据表中
                    financial_auditDao.insertFinancial_Audit(send_id, "系统", users_id, nickname, diamonds, rmb, 1, sysDate, payStatus, api);

                    //日期+ID 组成 流水号：
                    long ser = Long.parseLong(serial_number);
                    long numb = api.getId();
                    serial_number = (ser + numb) + "";
                    financial_auditDao.updateFinancial_Audit(serial_number, api.getId());
                    financial_audit_id = api.getId();

                    //根据user_id 查钻石
                    int diams = 0;
                    Integer diamon = gameUserDao.diamond(users_id);
                    if (diamon != null) {
                        diams = (int) diamon;
                    }

                    //将发送钻石日志记录到数据库中
                    logFilesDao.insertToLog(users_id, "钻石充值", "" + diamonds, "系统", diams);

                } catch (Exception e) {
                    logger.error("接口错误信息：{}", e);
                }
            }

            //将充值数量与金额存入数据库中  (orders表)
            diamondsDao.insertToOrders(financial_audit_id, users_id, rmb * 100, 3, "平台充值", sysDate);
        } else {
            throw new Exception("传入的钻石数值或者钱数值为NULL");
        }

        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 添加代理页面；系统手动添加代理
     */
    @RequestMapping("add_proxy")
    public void addProxy(String id, String status, HttpServletResponse resp, HttpSession session) throws Exception {

        int user_id = Integer.parseInt(id);        //游戏玩家id

        String jsons = status;

        int user_status = Integer.parseInt(status);    //用户身份

        //先将身份查询出来，如果不是玩家，则不用改动，如果是玩家，则需要改成代理
        GameUserEntity userInfo = gameUserDao.findUserStatus(user_id);
        int role_id = 6;
        if (userInfo != null) role_id = userInfo.getRole_id();


        //判断是否改为玩家，如果是 则将 父级代理数据清空
        if (user_status == 3) {   //玩家
            gameUserDao.cleanProxy(user_id, user_status);  //特殊处理
        } else {
            if (role_id == 6) { //如果 等于 6 代表没有开通后台登陆 权限
                gameUserDao.updateRoleStatus(user_id, user_status);     // 将后台权限开通为代理
            } else {
                //根据ID修改权限，身份，操作,  需要将对应的后台权限设置为代理
                gameUserDao.revise(user_id, user_status);
            }
        }

        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 添加代理页面；系统手动添加代理
     */
    @RequestMapping("change_role")
    public void addProxy(String id, String role, HttpServletResponse resp) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        int users_id = Integer.parseInt(id);        //游戏玩家id

        String jsons = role;


        int role_id = Integer.parseInt(role);    //用户身份
        //根据ID修改身份，操作
        gameUserDao.reviseOther(users_id, role_id);


        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


   /* *//**
     * ajax分页查询
     *//*
    @RequestMapping("ajaxmfen")
    public void ajaxmfen(@RequestParam(value = "carId") String carId, HttpServletResponse resp, HttpSession session) throws IOException {

        int id = (int) session.getAttribute("users_id");

        //先查询总页数；每页显示3条数据
        int CurrentPage = 1;

        //查询所有子代理个数
        int pageCount = gameUserDao.findAllProxy(id).size();
        int y = 10;

        if (pageCount % y == 0) {
            pageCount = pageCount / y;
        } else {
            pageCount = pageCount / y + 1;
        }

        //对page和name 和 y进行封装
        CurrentPage = (CurrentPage - 1) * y + 1;
        int limit = CurrentPage * y;

        //查询所有子代理的数据
        List<GameUserEntity> proxyDatas = gameUserDao.proxyPaging(id, CurrentPage - 1, limit);

        StringBuffer sbf = new StringBuffer();
        String json = "";
        for (GameUserEntity proxyData : proxyDatas) {

            //根据ID查询diamond和金币
            GameUserEntity diamondAndMoney = gameUserDao.findByDiamondAndMoney(proxyData.getUsers_id());

            //根据users_id和fatherproxy_id查询我为该子代理所发送的所有钻石数量
            int fatherproxy_id = proxyData.getFatherproxy_id();
            int users_id = proxyData.getUsers_id();
            Integer fatherProxyNumTotal = incomeDao.findFatherProxyNumTotal(users_id, id);
            if (fatherProxyNumTotal == null) {
                fatherProxyNumTotal = 0;
            }

            //根据users_id查询出此用户发送的出去的所有钻石数量
            Integer proxyNumTotal = incomeDao.findProxyNumTotal(users_id);
            if (proxyNumTotal == null) {
                proxyNumTotal = 0;
            }


            json = "{\"id\":\"" + proxyData.getUsers_id() + "\",\"name\":\"" + proxyData.getNickname() + "\",\"proxy_date\":\"" + proxyData.getFatherproxy_date() + "\",\"diamond\":\""
                    + diamondAndMoney.getDiamond() + "\",\"proxyNumTotal\":\"" + proxyNumTotal + "\",\"fatherProxyNumTotal\":\"" + fatherProxyNumTotal + "\",\"CurrentPage\":\"" + CurrentPage + "\",\"pageCount\":\"" + pageCount + "\"},";
            sbf.append(json);
        }


        //把字符串拼接成json数组
        String jsons = "[" + sbf + "]";

        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }*/

    /**
     * @RequestBody WebMyProxyReq myProxyReq,
     * ajax分页查询
     */
    @ApiOperation(value = "ajax分页查询", notes = "返回所有我的子代理", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "ajaxmfen", method = RequestMethod.POST)
    public WebPageResp ajaxmfen(HttpServletResponse resp, HttpSession session) throws Exception {

        int id = (int) session.getAttribute("users_id");

        //先查询总页数；每页显示3条数据
        int CurrentPage = 1;

        //查询所有子代理个数
        int pageCount = gameUserDao.findAllProxy(id).size();
        int y = 10;

        if (pageCount % y == 0) {
            pageCount = pageCount / y;
        } else {
            pageCount = pageCount / y + 1;
        }

        //对page和name 和 y进行封装
        CurrentPage = (CurrentPage - 1) * y + 1;
        int limit = CurrentPage * y;

        //查询所有子代理的数据
        List<GameUserEntity> proxyDatas = gameUserDao.proxyPaging(id, CurrentPage - 1, limit);

        List list = new ArrayList();

        for (GameUserEntity proxyData : proxyDatas) {

            //根据ID查询diamond和金币
            GameUserEntity diamondAndMoney = gameUserDao.findByDiamondAndMoney(proxyData.getUsers_id());

            //根据users_id和fatherproxy_id查询我为该子代理所发送的所有钻石数量
            int fatherproxy_id = proxyData.getFatherproxy_id();
            int users_id = proxyData.getUsers_id();
            Integer fatherProxyNumTotal = incomeDao.findFatherProxyNumTotal(users_id, id);
            if (fatherProxyNumTotal == null) {
                fatherProxyNumTotal = 0;
            }

            //根据users_id查询出此用户发送的出去的所有钻石数量
            Integer proxyNumTotal = incomeDao.findProxyNumTotal(users_id);
            if (proxyNumTotal == null) proxyNumTotal = 0;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", proxyData.getUsers_id());
            jsonObject.put("name", proxyData.getNickname());
            jsonObject.put("proxy_date", proxyData.getFatherproxy_date());
            jsonObject.put("diamond", diamondAndMoney.getDiamond());
            jsonObject.put("proxyNumTotal", proxyNumTotal);
            jsonObject.put("fatherProxyNumTotal", fatherProxyNumTotal);
            jsonObject.put("CurrentPage", CurrentPage);
            jsonObject.put("pageCount", pageCount);

            list.add(jsonObject);
        }

        WebPageResp pageResp = new WebPageResp();
        pageResp.setCode(0);
        pageResp.setData(list);
        pageResp.setErrMsg("");

        logger.info("接口：（/ajaxmfen）服务端返回数据：{}", pageResp.toString());
        return pageResp;
    }

    /**
     * ajax点击处理
     */
    @RequestMapping("ajaxOnclick")
    public void ajaxOnclick(@RequestParam(value = "page") String page, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException {
        int id = (int) session.getAttribute("users_id");
        int page1 = Integer.parseInt(page);

        //查询所有子代理个数
        int pageCount = gameUserDao.findAllProxy(id).size();
        int y = 10;

        if (pageCount % y == 0) {
            pageCount = pageCount / y;
        } else {
            pageCount = pageCount / y + 1;
        }

        int xx = (page1 - 1) * y;
        int limit = page1 * y;
        //查询所有子代理的数据
        List<GameUserEntity> proxyDatas = gameUserDao.proxyPaging(id, xx, limit);

        StringBuffer sbf = new StringBuffer();
        String json = "";

        for (GameUserEntity proxyData : proxyDatas) {

            //根据users_id和fatherproxy_id查询我为该子代理所发送的所有钻石数量
            //select SUM(number) from chaowan_admin.income where users_id=42 and fatherproxy_id=55
            int fatherproxy_id = proxyData.getFatherproxy_id();
            int users_id = proxyData.getUsers_id();
            Integer fatherProxyNumTotal = incomeDao.findFatherProxyNumTotal(users_id, id);
            if (fatherProxyNumTotal == null) {
                fatherProxyNumTotal = 0;
            }

            //根据users_id查询出此用户发送的出去的所有钻石数量
            Integer proxyNumTotal = incomeDao.findProxyNumTotal(users_id);
            if (proxyNumTotal == null) {
                proxyNumTotal = 0;
            }

            json = "{\"id\":\"" + proxyData.getUsers_id() + "\",\"proxyNumTotal\":\"" + proxyNumTotal + "\",\"fatherProxyNumTotal\":\"" + fatherProxyNumTotal + "\",\"name\":\"" + proxyData.getNickname() + "\",\"proxy_date\":\"" + proxyData.getFatherproxy_date() + "\",\"diamond\":\""
                    + proxyData.getDiamond() + "\"},";
            sbf.append(json);
        }

        //把字符串拼接成json数组
        String jsons = "[" + sbf + "]";

        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 代理中的发钻石；
     */
    @RequestMapping("change")
    public void sendDiam(String proxy_id, String diamond, HttpServletResponse resp, HttpSession session, HttpServletRequest req) throws Exception {
        int user_id = 0;

        user_id = wechatService.auth(session);

        if (user_id <= 0) {
            //可能在运营后台
            user_id = auth(session);
        }

        if (user_id <= 0) throw new Exception("认证失败");


        //根据当前用户id查询nickname
        GameUserEntity nickNameEntity = gameUserDao.findNickName(user_id);

        int proxyid = Integer.parseInt(proxy_id);        //要发送钻石或金币的ID；子代理用户ID

        //根据ID查询users表中的nicKname,用于将nickname插入到钻石发送信息记录的表中，
        GameUserEntity proxy_nickName = gameUserDao.findNickName(proxyid);    //游戏中子代理代理的nickname
        GameUserEntity user_nickName = gameUserDao.findNickName(user_id);    //游戏中当前登录用户的nickname

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        String jsons = "[{\"flag\":\"1\"},{\"ID\":\"" + proxy_id + "\"}]";

        if (!diamond.equals("")) {
            //获取当前时间，录入，
            Date nowDate = df.parse(sysDate);

            try {
                int diam = Integer.parseInt(diamond);
                //添加钻石操作
                bankService.addDiamond(user_id, proxyid, diam, 1, "平台代理钻石的发送", 300);

                //将钻石的发送信息记录：<发送出去的>
                incomeDao.IncomeUpdate_out(proxyid, proxy_nickName.getNickname(), "钻石", user_id, diam, sysDate, nickNameEntity.getNickname());

                //将钻石的收入信息记录：<收入进来的>
                incomeDao.IncomeUpdate_in(proxyid, user_nickName.getNickname(), "钻石", user_id, diam, sysDate, nickNameEntity.getNickname());


                //根据user_id 查钻石
                int diams = 0;
                Integer diamonds = gameUserDao.diamond(user_id);
                if (diamonds != null) {
                    diams = (int) diamonds;
                }
                //将发送钻石日志记录到数据库中
                logFilesDao.insertToLog(user_id, "发送钻石", proxy_id + "(ID)", "系统", diams);
                logFilesDao.insertToLog(proxyid, "收到钻石", user_id + "(ID)", "系统", diams);
            } catch (Exception e) {
                jsons = "[{\"flag\":\"0\"},{\"ID\":\"" + proxy_id + "\"}]";
                logger.error("接口错误信息：{}" + e);
            }
        }


        req.setAttribute("ID", proxy_id);
        //返回1代表成功
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * modifyPassword修改密码操作；
     */
    @RequestMapping("/modifyPassword")
    public void modifyPass(@RequestParam(value = "pass") String pass, HttpSession session, HttpServletResponse resp) throws Exception {

        int id = (int) session.getAttribute("users_id");

        String password = MD5Util.MD5(pass);
        //修改密码
        localUserDao.modifyPassword(id, password);

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date nowDate = df.parse(sysDate);

        //根据user_id 查钻石
        int diam = 0;
        Integer diamond = gameUserDao.diamond(id);
        if (diamond != null) {
            diam = (int) diamond;
        }
        //将修改密码日志记录到数据库中
        logFilesDao.insertToLog(id, "修改密码", "修改密码", "系统", diam);

        String jsons = "1";
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 退出登录操作
     */
    @RequestMapping("/loginout")
    public void LoginOut(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView andView = new ModelAndView();
        HttpSession session = req.getSession(false);//防止创建Session
        if (session != null) {
            session.removeAttribute("username");
        }
        PrintWriter out = resp.getWriter();
        out.print("1");
    }


    /**
     * 代理发送钻石或金币页面中根据ID查找用户
     */
    @RequestMapping("/finduser")
    public ModelAndView findUser(@RequestParam(value = "user_id") String user_id, @RequestParam(value = "status") String status) throws Exception {

        ModelAndView modelView = new ModelAndView();
        int id = Integer.parseInt(user_id);

        //根据user_id查询用户信息
        GameUserEntity userInfo = getUserInfoService.getUserInfo(id);

        if (userInfo == null) {
            //user表 与 users表 建立关系
            modelView = getUserInfoService.buildRelationships(id);
        } else {
            modelView.addObject("user", userInfo);
        }


        modelView.setViewName("proxy_diamond_send");
        if (status.equals("1")) {
            modelView.setViewName("proxy_diamond_send");
        } else if (status.equals("3")) {
            modelView.setViewName("proxy_manage");
        }

        return modelView;
    }


    /**
     * 后台权限管理页面中根据ID查找用户
     */
    @RequestMapping("/gamerFinduser")
    public ModelAndView gamerFindusers(@RequestParam(value = "user_id") String user_id,
                                       @RequestParam(value = "status") String status, HttpSession session) throws Exception {

        int userId = Integer.parseInt(user_id);

        int selfId = wechatService.auth(session);

        if (selfId <= 0) {
            //可能在运营后台
            selfId = auth(session);
        }
        if (selfId <= 0) {
            throw new Exception("无法获取自己的ID");
        }


        int id = userId;
        ModelAndView mv = new ModelAndView();

        //根据user_id查询用户信息
        GameUserEntity userInfo = getUserInfoService.getUserInfo(id);
        if (userInfo == null) {   //user表为空时，查询users表，users表有数据时，建立两表的关系
            mv = getUserInfoService.buildRelationships(id);
        } else {
            mv.addObject("user", userInfo);
        }


        //根据user_id查询钻石数量：
        GameUserEntity findDiamond = gameUserDao.findByDiamondAndMoney(id);


        int diamond = -1;
        if (findDiamond != null) {
            diamond = findDiamond.getDiamond();
        }

        if (status.equals("2")) {           //后台权限管理页面
            mv.setViewName("gamer");
        } else if (status.equals("1")) {        //微信钻石发送页面
            mv.addObject("user_id", selfId);
            mv.addObject("diamond", diamond);

            //查询当前登录的ID信息
            GameUserEntity currentUser = getUserInfoService.getUserInfo(selfId);

            mv.addObject("currentUser", currentUser);
            mv.setViewName("wechat_send_diamonds");
        } else {

            mv.addObject("user_id", selfId);
            mv.addObject("diamond", diamond);
            mv.setViewName("wechat_add_proxy");
        }
        return mv;
    }


    /**
     * 代理后台模块，发送钻石页面中根据ID查找用户
     */
    @RequestMapping("/sendsFinduser")
    public ModelAndView sendsFindusers(@RequestParam(value = "user_id") String user_id,
                                       @RequestParam(value = "status") String status, HttpSession session) throws Exception {

        int id = 0;
        if (!user_id.equals("")) id = Integer.parseInt(user_id);

        int selfId = auth(session);

        logger.info("当前登录的用户ID : {}", selfId);
        logger.info("需要查询的用户ID : {}", user_id);

        if (selfId <= 0) throw new Exception("认证失败");

        ModelAndView mv = new ModelAndView();

        //根据user_id查询用户信息
        GameUserEntity userInfo = getUserInfoService.getUserInfo(id);
        if (userInfo == null) {   //user表为空时，查询users表，users表有数据时，建立两表的关系
            //user表 与 users表建立关系
            mv = getUserInfoService.buildRelationships(id);
        } else {
            mv.addObject("user", userInfo);
        }


        //查询当前登录用户的信息
        GameUserEntity currentUser = getUserInfoService.getUserInfo(selfId);

        mv.addObject("currentUser", currentUser);
        mv.setViewName("sends");
        return mv;
    }


    /**
     * 添加子代理中的查询按钮
     */
    @RequestMapping("/proxyAdfinduser")
    public ModelAndView proxyAdfindusers(@RequestParam(value = "user_id") String user_id,
                                         @RequestParam(value = "status") String status) throws Exception {

        int id = Integer.parseInt(user_id);

        ModelAndView mv = new ModelAndView();

        //查询用户信息
        GameUserEntity userInfo = getUserInfoService.getUserInfo(id);

        //后台登陆（user表） 与 游戏表（users表）建立关系
        if (userInfo == null) {
            mv = getUserInfoService.buildRelationships(id);
        } else {
            mv.addObject("user", userInfo);
        }

        mv.setViewName("proxy-ad");
        return mv;
    }


    /*
     * 代理后台模块，添加子代理页面
     */
    @RequestMapping("/addproxy")
    public void addChildProxy(@RequestParam(value = "status") String status, @RequestParam(value = "proxy_id") String proxy_id, HttpServletResponse resp, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView();
        int p_id = Integer.parseInt(proxy_id);    //代理ID
        int u_id = 0;

        u_id = wechatService.auth(session);     //微信中登录

        int loginPlatf = 1;     //登录平台，默认为微信平台登录；

        if (u_id <= 0) {
            u_id = auth(session);       //运营平台上登录
            loginPlatf = 2;
        }

        logger.info("当前用户: " + u_id + " 进入代理后台模块中的添加子代理页面");

        if (u_id <= 0) {
            throw new Exception("认证失败");
        }

        int user_status = Integer.parseInt(status);

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        //查询子代理个数
        List<GameUserEntity> proxy = gameUserDao.findchildproxy(u_id);

        //根据登陆的ID查用户的身份信息，1代表至尊合伙人，2代理，4为玩家
        GameUserEntity id = gameUserDao.findByID(u_id);
        int userStatus = id.getUser_status();
        String jsons = "";
        //查询是否已经成为代理了；
        GameUserEntity fatherProxyId = gameUserDao.findFatherProxyId(p_id);


        if (fatherProxyId == null || fatherProxyId.getFatherproxy_id() == 0) {

            gameUserDao.addProxy(5, u_id, p_id, sysDate, user_status);    //调用添加代理的接口

            //得到nickname
            GameUserEntity nickName = gameUserDao.findNickName(p_id);
            String nickname = "";
            if (nickName != null) {
                nickname = nickName.getNickname();
            }

            String username = (String) session.getAttribute("username");

            Date now = df.parse(sysDate);

            //将代理申请放入数据库中
            proxyApprovalRecordsDao.insertToRecords(p_id, nickname, "", "", loginPlatf, 1, "", 1, username, now, now);

            //将该玩家后台权限调成代理 并将 login_status 改为 1
            gameUserDao.updateLoginStatus(p_id);

            jsons = "1";    //返回1代表成功
        } else {
            jsons = "0";     //返回0代表失败
        }
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


/**------------------以下是消息页面后台处理---------------------*/


    /**
     * 新增消息
     */
    @RequestMapping("publish_msg")
    public ModelAndView systemMsg(String title, String content, String immediate, String need_exit, String btnShow, String btnUrl,
                                  String btn_to_page, String btn_cmd, String show_place, String msg_type, String pic_url,
                                  String pic_cmd, String pic_page, String pic_to_url, String reward_num, String btn_title,
                                  String start_time, String end_time, String version, String specific_user, String need_scroller,
                                  String show_times, String platform, String send_all, String reward_type, String need_save, String show_again,
                                  Integer status, HttpServletResponse resp) throws Exception {

        ModelAndView mv = new ModelAndView();

        //--------------------------------------------参数处理-------------------------

        boolean immediateness = false;      //是否立即发送
        if (immediate.equals("1")) {
            immediateness = true;
        }

        boolean needExit = false;       //是否需要退出 ,
        if (btn_cmd.equals("4") || pic_cmd.equals("4")) {
            needExit = true;
        }

        String picUrl = pic_url;    //图片地址
        int picCmd = Integer.parseInt(pic_cmd);
        int picPage = Integer.parseInt(pic_page);
        String picToUrl = pic_to_url;

        int showPlace = Integer.parseInt(show_place);
        int msgType = Integer.parseInt(msg_type);    //消息类型  1：文本消息；2：纯图消息；3：图文消息

        int reward = 0;
        if (btn_cmd.equals("3")) {
            reward = Integer.parseInt(reward_num);     //奖励；0：无奖励, 其他奖励数量
        }

        boolean btn_show = false;       //是否显示按钮
        int btnToPage = Integer.parseInt(btn_to_page);
        int btnCmd = Integer.parseInt(btn_cmd);

        boolean needScroller = false;
        if (need_scroller.equals("1")) {
            needScroller = true;
        }

        boolean expired = false;    //是否过期

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = formatter.parse(start_time);
        Date endTime = formatter.parse(end_time);
        int times = Integer.parseInt(show_times);
        int platf = Integer.parseInt(platform);
        int rewardType = Integer.parseInt(reward_type);

        boolean sendAll = false;
        if (send_all.equals("1")) {
            sendAll = true;
        }

        boolean needSave = false;       //是否保存到消息列表
        if (need_save.equals("1")) {
            needSave = true;
        }

        int showAgain = Integer.parseInt(show_again);
        //--------------------------------------------参数处理-----------------------


        SystemMsgsEntity systemMsgsEntity = new SystemMsgsEntity();
        systemMsgsEntity.setTitle(title);
        systemMsgsEntity.setContent(content);
        systemMsgsEntity.setImmediateness(immediateness);
        systemMsgsEntity.setNeed_exit(needExit);
        systemMsgsEntity.setBtn_show(Integer.parseInt(btnShow));            //是否显示按钮
        systemMsgsEntity.setBtn_to_url(btnUrl);
        systemMsgsEntity.setBtn_to_page(btnToPage);
        systemMsgsEntity.setBtn_cmd(btnCmd);
        systemMsgsEntity.setShow_place(showPlace);
        systemMsgsEntity.setMsg_type(msgType);
        systemMsgsEntity.setPic_url(pic_url);
        systemMsgsEntity.setPic_cmd(picCmd);
        systemMsgsEntity.setPic_to_page(picPage);
        systemMsgsEntity.setPic_to_url(pic_to_url);
        systemMsgsEntity.setReward(reward);
        systemMsgsEntity.setBtn_title(btn_title);
        systemMsgsEntity.setStart_time(startTime);
        systemMsgsEntity.setEnd_time(endTime);
        systemMsgsEntity.setVersion(version);
        systemMsgsEntity.setNeed_scroller(needScroller);
        systemMsgsEntity.setPlatfrom(platf);
        systemMsgsEntity.setCmd(5);
        systemMsgsEntity.setSend_all(sendAll);
        systemMsgsEntity.setRaw_content("");
        systemMsgsEntity.setNeed_save(needSave);
        systemMsgsEntity.setShow_again(showAgain);
        systemMsgsEntity.setShow_times(times);
        if (immediateness) {
            systemMsgsEntity.setShow_times(--times);
        }

        logger.info("消息参数：{}", systemMsgsEntity.toString());


        //将数据写入数据库
        systemMsgsDao.insertToSysMsg(systemMsgsEntity);

        logger.info("获取新增消息数据的ID：{}", systemMsgsEntity.getId());


        JSONObject jsonObject = new JSONObject();

        //用来存存储特定用户ID
        List userIds = new ArrayList();

        int msgId = systemMsgsEntity.getId();      //消息ID

        SystemMsgRecordsEntity api = new SystemMsgRecordsEntity();

        //判断是否为奖励奖励消息，如果是，则在 system_msg_records 表中新增一条 记录
        if ((rewardType == 1 && reward > 0 && !specific_user.equals("")) || (!specific_user.equals("") && !sendAll)) {
            specific_user = specific_user.trim();
            String[] specific = specific_user.split(",");

            for (int i = 0; i < specific.length; i++) {
                boolean isNum = StringUtils.isNumeric(specific[i]);
                if (isNum) {
                    int useId = Integer.parseInt(specific[i]);
                    systemMsgRecordsDao.insertToMsgRecords(useId, msgId, 0, api);
                    logger.info("数据新增到 <system_msg_records> 表中；ID 为：{}", api.getId());
                    userIds.add(useId);
                    logger.info("用户ID： {}", specific[i]);
                } else {
                    logger.info("新增消息特定用户中输入了错误ID：{}", specific[i]);
                }
            }
        }


        /**
         * 全员推送处理
         */
        if (sendAll) {
            msgRecordsProcessor.setMsgId(msgId);
            Thread thread = new Thread(msgRecordsProcessor);
            thread.start();
        }


        if (immediateness) {      //如果是 及时发送，就推送该消息
            msgSendingService.diposeMsg(title, content, btnShow, btnUrl, btn_title, version, platform, reward_type,
                    picUrl, picCmd, picPage, picToUrl, showPlace, msgType, reward, btn_show, btnToPage,
                    btnCmd, needScroller, startTime, sendAll, userIds, msgId, msgId, jsonObject);
        }

        ModelAndView search = new ModelAndView();
        if (status != null) {
            //根据上面查询方法重新查询调用，
            search = editor("1", "20", 1);
            return search;
        }
        search = editor("1", "20", null);
        return search;
    }


    /**
     * 根据ID 查看消息
     *
     * @param msg_id 需要查看的 消息ID
     * @return
     */
    @RequestMapping("message_browse")
    public ModelAndView msgBrowse(String msg_id, Integer status) {
        ModelAndView mlv = new ModelAndView();

        int id = 0;
        if (msg_id != null && !msg_id.equals("")) {
            id = Integer.parseInt(msg_id);
        }

        //根据ID 查询数据
        SystemMsgsEntity msgRecord = systemMsgsDao.findById(id);

        if (msgRecord != null) {

            boolean send_all = msgRecord.isSend_all();
            String specific_user = "";
            if (!send_all) {
                //根据msgID 查询数据
                int msgId = msgRecord.getId();
                List<SystemMsgRecordsEntity> recordByMsgId = systemMsgRecordsDao.findRecordByMsgId(msgId);
                if (recordByMsgId.size() != 0) {
                    for (SystemMsgRecordsEntity systemMsgRecord : recordByMsgId) {
                        int userId = systemMsgRecord.getUser_id();
                        specific_user = specific_user + userId + ",";
                    }
                    specific_user = specific_user.substring(0, specific_user.length() - 1);
                }
            }

            //转换时间为String 类型
            Date start_time = msgRecord.getStart_time();
            Date end_time = msgRecord.getEnd_time();

            SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = slf.format(start_time);
            String endTime = slf.format(end_time);
            logger.info("开始时间：{}", startTime);
            logger.info("结束时间：{}", endTime);

            msgRecord.setStartTime(startTime);
            msgRecord.setEndTime(endTime);

            msgRecord.setSpecific_user(specific_user);
            mlv.addObject("msgRecord", msgRecord);
        }
        if (status != null) {
            mlv.setViewName("customer-service/customer_message_browse");
        } else {
            mlv.setViewName("message_browse");
        }
        return mlv;
    }


    //公告再次编辑
    @RequestMapping("/againEditor")
    public ModelAndView again(String id, String status) {
        ModelAndView mv = new ModelAndView();
        int notice_id = Integer.parseInt(id);

        //根据ID查询announcements_id
        Status_EditorEntity announcementsId = status_editorDao.findByAnnouncementsId(notice_id);

        //根据announcements_id查询数据
        AnnouncementsEntity announcementsList = announcementsDao.findById(announcementsId.getAnnouncements_id());

        NoticeEntity oneDate = noticeDao.findById(notice_id);

        if (status.equals("1")) {
            if (oneDate.getIs_all() == 0) {
                String special_user = "";

                //根据notice_id 查询user_id（特定用户）
                List<MsgPushRecordEntity> userId = msgPushRecordDao.findUserId(oneDate.getId());
                for (MsgPushRecordEntity userid : userId) {
                    special_user = special_user + userid.getUser_id() + ",";
                }

                special_user = special_user.substring(0, special_user.length() - 1);

                oneDate.setSpecific_user(special_user);
            }
        }

        if (status.equals("1")) {
            mv.addObject("oneDate", oneDate);            //获得数据，用于数据回填用；
            mv.setViewName("messages_editor_again");
        } else if (status.equals("0")) {
            mv.addObject("announcementsList", announcementsList);
            mv.addObject("end_date", announcementsId.getEnd_date());
            mv.addObject("start_date", announcementsId.getStart_date());
            mv.setViewName("notice_editor_again");
        }

        return mv;
    }


    /**
     * 此方法为保存为草稿和发布
     */
    @RequestMapping("/publish")
    public void publ(String status, String title, String content, String type, String number, String start_date,
                     String end_date, String platform, String version, String specific_user, String sign_out,
                     String display_position, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ModelAndView mv = new ModelAndView();
        int num = 0;
        if (!number.equals("")) num = Integer.parseInt(number);

        int platforms = 0;
        if (platform.equals("IOS")) {
            platforms = 1;
        } else if (platform.equals("Android")) {
            platforms = 2;
        }

        int display_posit = 0;
        if (display_position.equals("消息界面")) {
            display_posit = 1;
        } else if (display_position.equals("弹窗")) {
            display_posit = 2;
        }

        int typed = 0;
        if (type.equals("钻石")) {
            typed = 1;
        } else if (type.equals("金币")) {
            typed = 2;
        }

        NoticeEntity api = new NoticeEntity();
        int notice_id = 0;
        int cmd = 8;
        if (typed == 0) {
            cmd = 5;
        }
        if (specific_user.equals("")) {

            //将消息奖励内容存入数据库中
            noticeDao.saveAsNotice(title, content, start_date, end_date, platforms, version, typed, num, display_posit, Integer.parseInt(sign_out), 1, cmd, api);

            notice_id = api.getId();
            //如果是全员推送，将所有用户ID查询出来，插入到msg_push_record表中
            List<GameUserEntity> allUsersID = gameUserDao.findAllUsersID();
            for (GameUserEntity findUserID : allUsersID) {
                msgPushRecordDao.insertMsgPushRecord(findUserID.getId(), 0, notice_id);
            }
        } else {
            //将消息奖励内容存入数据库中
            noticeDao.saveAsNotice(title, content, start_date, end_date, platforms, version, typed, num, display_posit, Integer.parseInt(sign_out), 0, cmd, api);

            notice_id = api.getId();

            String[] specific = specific_user.split(",");

            for (int i = 0; i < specific.length; i++) {
                int use = Integer.parseInt(specific[i]);
                logger.debug("用户ID： " + specific[i]);
                msgPushRecordDao.insertMsgPushRecord(use, 0, notice_id);
            }
        }


        //获取当前时间，
        String sysDate = CommonUtil.creationDate("yyyy-MM-dd HH:mm:ss");

        //将数据存入数据库status_editor表中，status中1表示发布，0表示草稿
        status_editorDao.insertEditor(title, status, sysDate, start_date, end_date, notice_id, 0);


        if (status.equals("1")) {
            int disp_position = 0;
            if (display_position.equals("消息界面")) {
                disp_position = 1;
            } else if (display_position.equals("弹窗")) {
                disp_position = 2;
            }

            boolean flag = true;

            JSONObject msg = new JSONObject();
            msg.put("cmd", 8);
            msg.put("title", title);
            msg.put("content", content);
            msg.put("reward_type", typed);
            msg.put("reward_number", num);
            msg.put("sign_out", Integer.parseInt(sign_out));
            msg.put("display_position", disp_position);
            msg.put("start_time", start_date);
            msg.put("id", notice_id);

            List userIds = new ArrayList();

            if (!specific_user.equals("")) {
                String[] specific = specific_user.split(",");

                for (int i = 0; i < specific.length; i++) {
                    int use = Integer.parseInt(specific[i]);
                    userIds.add(use);
                    logger.debug("用户ID： " + specific[i]);
                }
                flag = false;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            date = formatter.parse(start_date);

            msgPush.msgPush(msg, userIds, flag, date.getTime(), version, Integer.parseInt(platform));
            logger.debug("消息推送成功！ ");
        }


        resp.sendRedirect("/status_editor?page=1");
    }


    /**
     * 此方法为消息奖励发布并修改
     */
    @RequestMapping("/pulishandchange")
    public void pulishAndChangeReward(String status, String title, String content, String type, String number,
                                      String start_date, String end_date, String platform, String version,
                                      String specific_user, String display_position, String sign_out, String id,
                                      HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int notice_id = Integer.parseInt(id);
        int num = 0;
        if (!number.equals("")) {
            num = Integer.parseInt(number);
        }

        int reward_type = 0;
        if (type.equals("钻石")) {
            reward_type = 1;
        } else if (type.equals("金币")) {
            reward_type = 2;
        }


        int display_posit = 0;
        if (display_position.equals("消息界面")) {
            display_posit = 1;
        } else if (display_position.equals("弹窗")) {
            display_posit = 2;
        }

        int platforms = 0;
        if (platform.equals("IOS")) {
            platforms = 1;
        } else if (platform.equals("Android")) {
            platforms = 2;
        }


        int cmd = 8;
        if (reward_type == 0) cmd = 5;

        if (specific_user.equals("")) {

            //如果是全员推送，根据notice_id修改msg_push_record表中所有数据
            msgPushRecordDao.updateNotices(title, content, display_posit, Integer.parseInt(sign_out), reward_type, num, start_date, end_date, platforms, version, cmd, notice_id);

        } else {
            String[] specific = specific_user.split(",");

            for (int i = 0; i < specific.length; i++) {
                int use = Integer.parseInt(specific[i]);
                logger.debug("用户--ID： " + specific[i]);

                //先根据notice_id 删除msg_push_record表中的数据
                msgPushRecordDao.deleteByIdMsgPushRecord(notice_id);

                //再将数据插入到数据库中
                msgPushRecordDao.insertMsgPushRecord(use, 0, notice_id);
            }
        }


        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

//------------------------------------------------------------------------//


        //将奖励公告消息内容存入数据库中
        //noticeDao.saveAsNoticeChange(title, content, start_date, end_date, platforms, version, reward_type, Integer.parseInt(number),display_posit,Integer.parseInt(sign_out),Integer.parseInt(id));


        //将数据存入数据库status_editor表中，status中1表示发布，0表示草稿
        status_editorDao.updateEditor(title, status, sysDate, start_date, end_date, Integer.parseInt(id));


        if (status.equals("1")) {
            int typed = 0;
            if (type.equals("钻石")) {
                typed = 1;
            } else if (type.equals("金币")) {
                typed = 2;
            }

            boolean flag = true;

            JSONObject msg = new JSONObject();
            msg.put("cmd", 8);
            msg.put("title", title);
            msg.put("content", content);
            msg.put("type", typed);
            msg.put("number", num);

            List userIds = new ArrayList();

            if (!specific_user.equals("")) {
                String[] specific = specific_user.split(",");

                for (int i = 0; i < specific.length; i++) {
                    int use = Integer.parseInt(specific[i]);
                    userIds.add(use);
                    logger.debug("用户ID--" + specific[i]);
                }
                flag = false;
            }


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            date = formatter.parse(start_date);

            msgPush.msgPush(msg, userIds, flag, date.getTime(), version, 0);
            logger.debug("消息推送成功！");
        }


        resp.sendRedirect("/status_editor?page=1");
    }


    /**
     * 此方法为公告新增与编辑发布并修改
     */
    @RequestMapping("/pulishAndChangeMsg")
    public void pulishAndChange(String status, String title, String content, String start_date, String end_date, String location, String platform, String version, String sign_out, String times, String url, String id, HttpServletResponse resp, HttpServletRequest req) throws Exception {


        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start_date1 = formatter.parse(start_date);
        Date end_date1 = formatter.parse(end_date);


        int platforms = 0;
        if (platform.equals("IOS")) {
            platforms = 1;
        } else if (platform.equals("Android")) {
            platforms = 2;
        }
        int announcements_id = 0;
        if (!(id.equals("") || id.equals("0"))) {
            announcements_id = Integer.parseInt(id);
        }


        if (id.equals("") || id.equals("0")) {

            AnnouncementsEntity api = new AnnouncementsEntity();

            announcementsDao.insertAnnouncements(1, 1, content, start_date1.getTime() / 1000, end_date1.getTime() / 1000, sysDate, Integer.parseInt(sign_out), title, Integer.parseInt(times), version, platforms, Integer.parseInt(location), url, "", 0, 0, api);


            announcements_id = api.getId();

            //将数据存入数据库status_editor表中，status中1表示发布，0表示草稿
            status_editorDao.insertEditor(title, "1", sysDate, start_date, end_date, 0, announcements_id);

            //
            List<GameUserEntity> allUsersID = gameUserDao.findAllUsersID();
            for (GameUserEntity userID : allUsersID) {
                controlTimesDao.insertControlTimes(userID.getId(), announcements_id, Integer.parseInt(times));
            }


        }


        //根据id修改announcements表数据
        announcementsDao.updateById(announcements_id, 1, 1, content, start_date1.getTime() / 1000, end_date1.getTime() / 1000, sysDate, Integer.parseInt(sign_out), title, Integer.parseInt(times), version, platforms, Integer.parseInt(location), url);

        //根据id修改status_editor表中的数据
        status_editorDao.updateEditorOther(title, "1", sysDate, start_date, end_date, announcements_id);


        resp.sendRedirect("/notice_list?page=1");
    }


    //公告列表中根据ID进行删除
    @RequestMapping("/deletByNotice")
    public ModelAndView delet(String id, String status, String limit, Integer flag, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int msg_id = Integer.parseInt(id);

        systemMsgsDao.delById(msg_id);      //删除消息
        systemMsgRecordsDao.delById(msg_id);       //删除消息记录中的数据


        ModelAndView delete = new ModelAndView();
        if (flag != null) {
            //根据上面查询方法重新查询调用，
            delete = editor("1", "20", 1);
            return delete;
        }
        delete = editor("1", "20", null);
        return delete;
    }


    /**
     * 此方法为保存为草稿和发布
     */
    @RequestMapping("/pub")
    public void publi(String status, String title, String content, String start_date, String end_date, String location,
                      String platform, String version, String sign_out, String times, String url, String imgurl,
                      String page_type, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int msg_type = 0;
        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date now = df.parse(sysDate);

        content = content.replaceAll("<b>", "\n");


        int platforms = 0;
        if (platform.equals("IOS")) {
            platforms = 1;
        } else if (platform.equals("Android")) {
            platforms = 2;
        }

        if (status.equals("1")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date start_date1 = formatter.parse(start_date);
            Date end_date1 = formatter.parse(end_date);

            AnnouncementsEntity api = new AnnouncementsEntity();

            if (!imgurl.equals("")) {
                msg_type = 2;
            }

            announcementsDao.insertAnnouncements(1, 1, content, start_date1.getTime() / 1000, end_date1.getTime() / 1000, sysDate, Integer.parseInt(sign_out), title, Integer.parseInt(times), version, platforms, Integer.parseInt(location), url, imgurl, msg_type, Integer.parseInt(page_type), api);


            int announcements_id = api.getId();

            //将数据存入数据库status_editor表中，status中1表示发布，0表示草稿
            status_editorDao.insertEditor(title, status, sysDate, start_date, end_date, 0, announcements_id);

            /*//将数据存入announcement_records表中
            List<GameUserEntity> allUsersID = gameUserDao.findAllUsersID();
            for (GameUserEntity userID : allUsersID){
                announcements_recordsDao.insertToRecords(announcements_id,userID.getId(),0,now);
            }*/

        } else if (status.equals("0")) {

            if (start_date.equals("")) start_date = sysDate;
            if (end_date.equals("")) end_date = sysDate;

            int timeses = 0;
            if (!times.equals("")) timeses = Integer.parseInt(times);

            NoticeDraftEntity api = new NoticeDraftEntity();
            announcementsDao.insertNoticeDraft(content, start_date, end_date, sysDate, Integer.parseInt(sign_out), title, timeses, version, platforms, Integer.parseInt(location), url, imgurl, api);


            //将数据存入数据库status_editor表中，status中1表示发布，0表示草稿
            status_editorDao.insertEditor(title, status, sysDate, start_date, end_date, 0, api.getId());

        }


        resp.sendRedirect("/notice_list?page=1");

    }

    /**------------------以上是消息页面后台处理---------------------*/


    /**
     * 手机短信验证
     */
    @RequestMapping("/smsVerification")
    public void msgSend(String code, String phone, HttpServletResponse resp, HttpSession session) throws Exception {

        //得到登录的用户ID
        int users_id = (int) session.getAttribute("users_id");

        //根据登录用户ID查询手机号码，用于修改密码；
        GameUserEntity byPhone = gameUserDao.findByPhone(users_id);

        String jsons = "1";
        if (byPhone != null) {
            try {
                smsVerification.msgService(Integer.parseInt(code), byPhone.getPhone());
            } catch (Exception e) {
                logger.error("手机短信验证码错误信息：" + e);
            }
        } else {
            jsons = "0";
        }

        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 微信代理申请页面中的手机短信验证
     */
    @RequestMapping("/weChatVerification")
    public void weChatVerifications(String code, String phone, HttpServletResponse resp) throws Exception {

        String jsons = "1";
        if (!phone.equals("")) {
            try {

            } catch (Exception e) {
                logger.error("微信手机验证码错误信息：" + e);
            }
            smsVerification.msgService(Integer.parseInt(code), phone);
        } else {
            jsons = "0";
        }

        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    //------------------以下是消息页面后台处理---------------------


    /**
     * 消息列表
     */
    @RequestMapping("/status_editor")
    public ModelAndView editor(String page, String limit, Integer status) {
        ModelAndView mv = new ModelAndView();
        int pages = Integer.parseInt(page);
        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y = 20;
        if (!limit.equals("")) y = Integer.parseInt(limit);

        //查询总条数
        int sum = systemMsgsDao.findAllMsgTotal();

        int xx = (pages - 1) * y;

        //分页查询；
        List<SystemMsgsEntity> msgsList = systemMsgsDao.findPaging(xx, y);
        for (SystemMsgsEntity msg : msgsList) {
            Date start_time = msg.getStart_time();
            String startTime = df.format(start_time);
            msg.setStartTime(startTime);

            //根据msg_id 查询展示的次数 和 点击次数
            int msg_id = msg.getId();
            SystemMsgRecordsEntity showTimesByMsgId = systemMsgRecordsDao.findShowTimesByMsgId(msg_id);
            if (showTimesByMsgId != null) {
                msg.setHit_count(showTimesByMsgId.getHit_count());
                msg.setShow_times(showTimesByMsgId.getShow_times());
            } else {
                msg.setShow_times(0);
                msg.setHit_count(0);
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, y);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);
        mv.addObject("limit", limit);


        mv.addObject("nowDate", sysDate);
        mv.addObject("msgsList", msgsList);

        mv.setViewName("status_editor");
        if (status != null) mv.setViewName("customer-service/customer_status_editor");
        return mv;
    }


    /**
     * 公告列表
     */
    @RequestMapping("/notice_list")
    public ModelAndView Neditor(String page) {
        ModelAndView mv = new ModelAndView();

        int pages = Integer.parseInt(page);


        //y为自己定义分为一页显示几条数据；这里一页显示10条数据；
        int y = 10;

        //对Status_editor表中公告进行模分查询
        List<Status_EditorEntity> msgNotices = status_editorDao.findByMsgNotice();


        int sum = msgNotices.size();

        int xx = (pages - 1) * y;
        int yy = pages * y;

        //分页查询；
        List<Status_EditorEntity> msgNoticePaging = status_editorDao.findByMsgNoticePaging(xx, 10);


        //处理 总页数
        int total = disposeTotal(sum, y);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台


        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间


        mv.addObject("nowDate", sysDate);
        mv.addObject("msgNoticePaging", msgNoticePaging);

        mv.setViewName("notice_editor");
        return mv;
    }


    /**
     * 进入添加代理页面
     */
    @RequestMapping("/proxy_manage")
    public ModelAndView manage(HttpSession session, HttpServletRequest request) {
        String ip_address = HttpReqUtil.getRemortIP(request);

        int users_id = (int) session.getAttribute("users_id");

        //根据userId查询nickname
        String nickname = "";
        GameUserEntity username = gameUserDao.findNickName(users_id);
        if (username != null) {
            nickname = username.getNickname();
        }

        userdao.insertLogCheck("进入代理页面", users_id, ip_address, nickname);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("proxy_manage");
        return mv;
    }

    /**
     * 进入钻石发送页面
     */
    @RequestMapping("/proxy_diamond_send")
    public ModelAndView proxyDiamondSend(HttpSession session, HttpServletRequest request) {
        String ip_address = HttpReqUtil.getRemortIP(request);
        int users_id = (int) session.getAttribute("users_id");

        //根据userId查询nickname
        String nickname = "";
        GameUserEntity username = gameUserDao.findNickName(users_id);
        if (username != null) {
            nickname = username.getNickname();
        }

        userdao.insertLogCheck("进入钻石发送页面", users_id, ip_address, nickname);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("proxy_diamond_send");
        return mv;
    }


    /**
     * 进入后台总览
     */
    @RequestMapping("total_preview")
    public ModelAndView totalPreview(String page, String limit, HttpSession session) {
        ModelAndView mvs = new ModelAndView();
        int user_id = (int) session.getAttribute("users_id");

        int pages = Integer.parseInt(page);

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y = 20;
        if (!limit.equals("") && limit != null) y = Integer.parseInt(limit);

        //查询总条数
        List<GameUserEntity> allRoleUserTotal = gameUserDao.findAllRoleUser();

        int sum = allRoleUserTotal.size();

        int xx = (pages - 1) * y;

        //分页查询；
        List<GameUserEntity> allRoleUser = gameUserDao.findAllRoleUserPaging(xx, y);

        for (GameUserEntity roleUser : allRoleUser) {
            //根据user_id查询nickname
            GameUserEntity nickName = gameUserDao.findNickname(roleUser.getUsers_id());
            String nkname = "";
            if (nickName != null) {
                nkname = nickName.getNickname();
            }
            roleUser.setNickname(nkname);
            if (roleUser.getLogin_time() == null) {
                roleUser.setLogin_time("未登录过");
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, y);

        mvs.addObject("total", total);
        mvs.addObject("page", page);//将page传到前台
        mvs.addObject("user_id", user_id);//将user_id传到前台
        mvs.addObject("limit", limit);//将user_id传到前台
        mvs.addObject("count", sum);//将user_id传到前台

        mvs.addObject("allRoleUser", allRoleUser);
        mvs.setViewName("total_preview");
        return mvs;
    }

    /**
     * 管理员修改用户登陆密码
     *
     * @param id       用户ID
     * @param password
     * @return
     */
    @RequestMapping("changePassword")
    public void changePass(String id, String password, HttpServletResponse resp) throws IOException {
        logger.info("ID: {}", id);
        logger.info("password: {}", password);
        int user_id = Integer.parseInt(id);

        password = MD5Util.MD5(password);

        //根据ID修改密码
        gameUserDao.updatePassword(user_id, password);

        String jsons = "1";    //返回1代表成功
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }

    /**
     * 根据user_id将后台权限取消
     */
    @RequestMapping("cancel_role")
    public void cancelRole(String id, HttpServletResponse resp) throws Exception {
        //根据user_id将role_id改为6；
        gameUserDao.updateRole(Integer.parseInt(id));

        resp.sendRedirect("/total_preview?page=1&limit=20");
    }


    /**
     * 代理总览页面
     */
    @RequestMapping("proxy_preview")
    public ModelAndView proxyPreview(Integer page, String userId, Integer limit, HttpSession session, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 0;

        int xx = (page - 1) * limit;

        Map map = new HashMap();
        map.put("user_id", userId);


        //将所有代理查询出来
        List<GameUserEntity> proxyListsTotal = gameUserDao.findProxyList(map);
        int sum = proxyListsTotal.size();           //查询所有子代理个数


        map.put("xx", xx);
        map.put("yy", limit);

        //将所有代理查询出来
        List<GameUserEntity> proxyLists = gameUserDao.findProxyListPaging(map);

        if (proxyLists.size() != 0) {
            for (GameUserEntity proxyList : proxyLists) {
                int user_id = proxyList.getUsers_id();
                int proxy_id = proxyList.getFatherproxy_id();

                //根据user_id查询钻石数量
                GameUserEntity diamondData = gameUserDao.findByDiamondAndMoney(user_id);
                if (diamondData != null) {
                    int diamond = diamondData.getDiamond();
                    proxyList.setDiamond(diamond);
                } else {
                    proxyList.setDiamond(0);
                }

                //根据 proxy_id 查询 由用户添加的代理 昵称
                GameUserEntity qureyNickName = gameUserDao.findNickName(proxy_id);

                if (qureyNickName != null) {
                    String proxyNickname = qureyNickName.getNickname();
                    proxyList.setProxyNickname(proxyNickname);
                } else {
                    proxyList.setProxyNickname("");
                }

                double total_integral = 0;
                //处理总积分：除以100 单位变为元
                int integral = proxyList.getIntegral();
                if (integral != 0) {
                    total_integral = integral * 1.0 / 100;
                }
                proxyList.setTotal_integral(total_integral);
            }
        }

        //查询总代理数
        List<GameUserEntity> gameUserEntities = gameUserDao.queryProxyList();
        int count = gameUserEntities.size();

        //处理 总页数
        int total = disposeTotal(sum, limit);


        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("limit", limit);
        mv.addObject("userId", userId);
        mv.addObject("count", count);

        mv.addObject("proxyLists", proxyLists);
        mv.setViewName("proxy_preview");
        return mv;
    }


    //设置充值钻石 的赠送比例
    @RequestMapping("bestowal_adjustment")
    public ModelAndView dsf(Integer user_id) {

        ModelAndView mv = new ModelAndView();
        if (user_id == null) user_id = 0;

        List<DiamondRatioEntity> diamondRatios = diamondRatioDao.findAll();
        for (DiamondRatioEntity diamondRatio : diamondRatios) {
            int diamond = diamondRatio.getDiamond();

            //根据user_id查询 和diamond 查询 数据
            DiamondGiftEntity diamondGift = diamondGiftDao.findByUserIdAndDiamond(user_id, diamond);

            if (diamondGift != null) {
                diamondRatio.setGift_diamond(diamondGift.getGive_diamond());

                if (!diamondGift.getRatio().equals("")) {
                    diamondRatio.setRoom_card(diamondGift.getRatio());
                }
            } else {
                diamondRatio.setGift_diamond(0);
            }
        }


        mv.addObject("user_id", user_id);
        mv.addObject("diamondRatios", diamondRatios);
        mv.setViewName("bestowal_adjustment");
        return mv;
    }

    //设置用户的充值后，钻石的赠送比例
    @RequestMapping("setting_ratios")
    public void settingRatios(String id, String diamond, HttpServletResponse resp, HttpSession session) throws IOException {

        int myUserId = auth(session);
        //查询昵称
        GameUserEntity userName = gameUserDao.findUsername(myUserId);
        String username = "";
        if (userName != null) username = userName.getUsername();

        int user_id = 0;
        String json = "1";
        if (!id.equals("")) {
            user_id = Integer.parseInt(id);
        } else {
            json = "0";
            return;
        }


        if (!diamond.equals("")) {
            String[] diam = diamond.split(",");
            int diamond_ratio_id = 0;
            int diamNum = 0;
            int rechargeDiamond = 0;

            for (int i = 0; i < diam.length; i += 3) {
                diamond_ratio_id = Integer.parseInt(diam[i]);     //id
                diamNum = Integer.parseInt(diam[i + 1]);          //赠送的钻石数量
                rechargeDiamond = Integer.parseInt(diam[i + 2]);  //充值的钻石数量

                String ratio = "";

                //根据ratio_id 查询
                DiamondRatioEntity diamondRatio = diamondRatioDao.findById(diamond_ratio_id);
                if (diamondRatio != null) {

                    double roomCard = (diamNum + rechargeDiamond) / 10;  //房卡  :价格/房卡数量=房卡单价/10钻
                    int money = diamondRatio.getMoney();
                    logger.info("充值的钱数：{}", money);

                    double d = money * 1.0 / roomCard;
                    logger.info("价格/房卡数量：{}", d);

                    ratio = "￥" + String.format("%.2f", d);   //四舍五入保留小数
                    logger.info("最终的值： {}", ratio);
                }

                diamondGiftDao.insertToDiamondGift(user_id, diamond_ratio_id, rechargeDiamond, diamNum, ratio);
            }

            //根据user_id写入当前登录的用户昵称，（调整赠送比例者）
            gameUserDao.updateAdjuster(username, user_id);

        } else {
            json = "0";
        }

        PrintWriter out = resp.getWriter();
        out.print(json);
    }


//--------------------------------------------//

    /**
     * 代理申请页面
     */
    @RequestMapping("wechat_proxy_apply")
    public ModelAndView wechatProxyApply(String userID, String nickname) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("userID", userID);
        mv.addObject("nickname", nickname);
        mv.setViewName("wechat_proxy_apply");
        return mv;
    }


    /**
     * 代理申请页面中的提交操作
     */
    @RequestMapping("proxy_apply")
    public void wechatProxyApply(String name, String phone, String addr, String userID, String nickname, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView();

        logger.info("进入微信个人中心 申请代理的user_id为：{}", userID);
        logger.info("进入微信个人中心 申请代理的手机号为：{}", phone);

        int user_id = Integer.parseInt(userID);


        //将得到的数据插入到 proxy_approval_records 表中
        proxyApprovalRecordsDao.insertToProxyApproval(user_id, nickname, name, phone, 1, 1, addr);


        //先查询user表中是否有该user_id的数据记录，如果没有 则建立关系
        GameUserEntity userInfo = getUserInfoService.getUserInfo(user_id);
        if (userInfo == null) {
            //user表 与 users表 建立关系
            ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
            Map<String, Object> model = modelView.getModel();
            logger.info("从modelViem中得到的map值：{}", model);
            GameUserEntity user = (GameUserEntity) model.get("user");
            if (user != null) {
                logger.info("从modelViem中得到的map值转为GameUserEntity类型的user_id：{}", user.getUsers_id());
                gameUserDao.updateRoleStatus(user_id, 2);    //user表中有该ID的数据，则直接 改为代理
            } else {
                logger.error("无此玩家！");
            }
        } else {
            gameUserDao.updateUserStatus(2, user_id);    //user表中有该ID的数据，则直接 改为代理
        }


        //将该玩家调整为代理：
        gameUserDao.updateUserStatus(2, user_id);


        resp.sendRedirect("/wxLogin");
    }


    /**
     * 待审核代理页面
     */
    @RequestMapping("wechat_proxy_approval")
    public ModelAndView wechatProxyApproval(Integer page) throws Exception {
        ModelAndView mv = new ModelAndView();

        if (page == null) page = 0;
        int limit = 10;

        //查询总条数
        List<ProxyApprovalRecordsEntity> approvalDate = proxyApprovalRecordsDao.findApprovalDate();

        int sum = approvalDate.size();

        int offset = (page - 1) * limit;

        //分页查询；
        List<ProxyApprovalRecordsEntity> approvalDates = proxyApprovalRecordsDao.findApprovalDatePaging(offset, limit);


        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("approvalDates", approvalDates);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台

        mv.setViewName("wechat_proxy_approval");
        return mv;
    }


    //待审核代理页面中的     通过/拒绝  操作
    @RequestMapping("application")
    public void applicationStatus(String id, String status, HttpSession session, HttpServletResponse resp) throws Exception {

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date nowDate = df.parse(sysDate);

        int approval_records_id = Integer.parseInt(id);

        String username = (String) session.getAttribute("username");

        if (status.equals("0")) {
            //将proxy_approval_records 表中的记录修改为拒绝状态
            proxyApprovalRecordsDao.updateApprovalRecords(nowDate, 0, username, approval_records_id);

        } else if (status.equals("1")) {
            //将proxy_approval_records 表中的记录修改为通过状态
            proxyApprovalRecordsDao.updateApprovalRecords(nowDate, 1, username, approval_records_id);

            //根据   proxy_approval_records 查询出user_id
            ProxyApprovalRecordsEntity userIdById = proxyApprovalRecordsDao.findUserIdById(approval_records_id);
            int user_id = userIdById.getUser_id();

            //分两种情况；1；user表中有 该user_id 这个用户；只需要修改 user_status 等即可 2：新增该user_id 数据
            //查询user表中是否存在该用户；
            GameUserEntity userData = gameUserDao.findUserStatus(user_id);
            if (userData != null) {
                //修改 user_status 等数据
                gameUserDao.updateProxy(sysDate, user_id);

            } else {
                //将数据插入到user表中（user表中新增一条该用户的数据）
                gameUserDao.insertProxy(user_id + "", "E10ADC3949BA59ABBE56E057F20F883E", 5, 2, user_id, sysDate);
            }

        }
        resp.sendRedirect("/wechat_approval_records?page=1");
    }


    /**
     * 代理审批记录
     */
    @RequestMapping("wechat_approval_records")
    public ModelAndView approvalRecords(Integer page, Integer limit) {
        ModelAndView mv = new ModelAndView();

        if (page == null) page = 0;
        if (limit == null) limit = 20;

        //查询总条数
        List<ProxyApprovalRecordsEntity> approvalAllDate = proxyApprovalRecordsDao.findAllDate();

        int sum = approvalAllDate.size();

        int offset = (page - 1) * limit;

        //分页查询；
        List<ProxyApprovalRecordsEntity> approvalAllDates = proxyApprovalRecordsDao.findAllDatePaging(offset, limit);


        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("approvalAllDates", approvalAllDates);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);
        mv.addObject("limit", limit);
        mv.setViewName("wechat_approval_records");

        return mv;
    }


    /**
     * 微信首页
     */
    @RequestMapping("wechat_player_index")
    public String playerIndex() {
        return "wechat_player_index";
    }


    /**
     * 微信玩家充值页面  <微信充值商品列表>
     *
     * @param openid
     * @param session
     * @return
     */
    @RequestMapping("wechat_player_recharge")
    public ModelAndView playerRecharge(String openid, HttpSession session) {

        int user_id = wechatService.auth(session);
        int users_id = 0;
        if (user_id > 0) {
            users_id = user_id;
        }
        int user_status = 3;

        //根据user_id查询是否为代理
        GameUserEntity findUserStatus = gameUserDao.findByID(users_id);
        if (findUserStatus != null) {
            user_status = findUserStatus.getUser_status();
        }

        /*
        ModelAndView mvs = new ModelAndView();
        //查询配置的商品表（products）
        List<ProductsEntity> productDatas = productsDao.findAll();
        //当商品有七件时：
        if (user_status!=3){
            productDatas = productsDao.findTypeOne();
        }*/

        ModelAndView mvs = new ModelAndView();
        //查询配置的商品表（products）
        List<ProductsEntity> productDatas = productsDao.findAll(); //玩家充值价格体系  type=0 为玩家充值
        if (user_status != 3) {
            productDatas = productsDao.findTypeOneAndLimitSix();  //代理充值价格体系  type=1 为代理充值
        }


        for (ProductsEntity productData : productDatas) {

            int diamond = productData.getDiamond();
            int show_diamond = 0;     //用于商品展示 钻石数量

            //根据 diamond和user_id 查询是否调整赠送钻石数量（diamond_gift表），
            // 如果有对该用户调整则进行钻石增加显示
            DiamondGiftEntity diamondGift = diamondGiftDao.findByUserIdAndDiamond(users_id, diamond);
            if (diamondGift != null) {
                show_diamond = diamondGift.getGive_diamond();

                if (!diamondGift.getRatio().equals("")) {
                    productData.setRoom_card_price(diamondGift.getRatio() + "/10钻石");
                }
            }
            show_diamond = show_diamond + diamond;
            productData.setShow_diamond(show_diamond);
        }


        String uuid = UUID.randomUUID().toString().replace("-", "");
        String dataVoucher = openid + uuid;


        mvs.addObject("productDatas", productDatas);    //商品信息

        mvs.addObject("user_status", user_status);
        mvs.addObject("openid", openid);
        mvs.addObject("userID", user_id);
        mvs.addObject("dataVoucher", dataVoucher);
        mvs.setViewName("wechat_player_recharge");
        return mvs;
    }

    /**
     * 微信充值成功页面
     */
    @RequestMapping("wechat_recharge_success")
    public ModelAndView rechargeSuccess(HttpSession session) throws Exception {

        int userId = wechatService.auth(session);

        if (userId <= 0) {
            throw new Exception("非法操作，无法获取userId");
        }

        ModelAndView mav = new ModelAndView();

        //根据userId 查询最新一条充值记录
        WechatRechargeEntity order = wechatRechargeDao.findByUserId(userId);

        if (order != null) {
            mav.addObject("order", order);
            int money = order.getRecharge_money();
            mav.addObject("money", money * 1.0 / 100);

            int diam = order.getRecharge_number();
            mav.addObject("diam", diam);
            mav.addObject("user_id", order.getUser_id());
        }

        mav.setViewName("wechat_recharge_success");
        return mav;
    }


    //微信发送钻石
    @RequestMapping("wechat_send_diamonds")
    public ModelAndView sendDiamond(String user_id, HttpSession session, HttpServletResponse resp) throws Exception {
        ModelAndView m = new ModelAndView();

        int users_id = wechatService.auth(session);
        GameUserEntity userStatus = gameUserDao.findUserStatus(users_id);
        if (userStatus == null || userStatus.getUser_status() == 3) {
            resp.sendRedirect("/wxLogin");
        }

        m.addObject("user_id", user_id);
        m.setViewName("wechat_send_diamonds");
        return m;
    }

    //微信添加代理
    @RequestMapping("wechat_add_proxy")
    public ModelAndView addProxy(String user_id, HttpSession session, HttpServletResponse resp) throws Exception {
        ModelAndView m = new ModelAndView();

        int users_id = wechatService.auth(session);
        GameUserEntity userStatus = gameUserDao.findUserStatus(users_id);
        if (userStatus == null || userStatus.getUser_status() == 3) {
            resp.sendRedirect("/wxLogin");
        }

        m.addObject("user_id", user_id);
        m.setViewName("wechat_add_proxy");
        return m;
    }

    //微信钻石发送记录
    @RequestMapping("wechat_send_records")
    public ModelAndView sendRecord(HttpSession session, HttpServletResponse resp) throws IOException {
        ModelAndView mv = new ModelAndView();

        int user_id = wechatService.auth(session);

        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        if (userStatus == null || userStatus.getUser_status() == 3) {
            resp.sendRedirect("/wxLogin");
        }
        mv.setViewName("wechat_send_records");
        return mv;
    }

    //微信钻石返利记录
    @RequestMapping("wechat_diamond_rebate")
    public ModelAndView diamRebate(HttpSession session, HttpServletResponse resp) throws IOException {
        ModelAndView mv = new ModelAndView();

        int user_id = wechatService.auth(session);

        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        if (userStatus == null || userStatus.getUser_status() == 3) {
            resp.sendRedirect("/wxLogin");
        }

        mv.setViewName("wechat_diamond_rebate");
        return mv;
    }

    //申请提现页面
    @RequestMapping("application_withdrawals")
    public ModelAndView applicationWithdrawals(HttpSession session, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView();

        int user_id = wechatService.auth(session);
        if (user_id <= 0) {
            throw new Exception("进入申请提现页面失败，无法获取 user_id");
        }

        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        if (userStatus == null || userStatus.getUser_status() == 3) {
            resp.sendRedirect("/wxLogin");
        }


        //查询当前有效积分：
        GameUserEntity totalIntegral = gameUserDao.findIntegralByUserId(user_id);
        int integralNum = 0;
        if (totalIntegral != null) {
            integralNum = totalIntegral.getIntegral();
        }

        mv.addObject("integralNum", integralNum);
        mv.setViewName("wechat_application_withdrawals");
        return mv;
    }

    //提现记录
    @RequestMapping("withdrawals_cash_record")
    public ModelAndView withdrawalsRecord(HttpSession session) throws Exception {

        int user_id = wechatService.auth(session);
        if (user_id <= 0) {
            throw new Exception("进入（/withdrawals_cash_record）提现记录页面失败，无法获取 user_id");
        }

        ModelAndView mv = new ModelAndView();

        mv.setViewName("wechat_withdrawals_cash");
        return mv;
    }

    //提现申请中安全验证页面
    @RequestMapping("phone_verification")
    public ModelAndView phoneVerification(String integral, HttpSession session) throws Exception {

        int user_id = wechatService.auth(session);
        if (user_id <= 0) {
            throw new Exception("进入（/phone_verification）无法获取user_id");
        }

        ModelAndView mv = new ModelAndView();

        int flag = 1;   //是否为第一次提现；1：第一次提现，

        //判断是否是第一次 申请提现
        WithdrawalsRecordEntity withdrawsRecord = withdrawalsRecordDao.findWithdrawsRecord(user_id);
        if (withdrawsRecord != null) {
            flag = 0;
        }

        mv.addObject("flag", flag);
        mv.addObject("integral", integral);
        mv.setViewName("wechat_phone_verification");
        return mv;
    }


    //从数据库中查询，判断是否有该手机号码；
    @RequestMapping("getPhone")
    public void queryPhone(String phone, HttpServletResponse resp) throws IOException {

        String jsons = "";

        //根据手机号码查询记录
        GameUserEntity userPhone = gameUserDao.qureyPhone(phone);
        if (userPhone != null) {
            jsons = "1";
        } else {
            jsons = "0";
        }

        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    //提交确认页面
    @RequestMapping("confirmation_submission")
    public ModelAndView confirmationSubmission(String integral, String wechatNumber, HttpSession session) throws Exception {

        int user_id = wechatService.auth(session);
        if (user_id <= 0) {
            throw new Exception("进入（/confirmation_submission）无法获取user_id");
        }

        ModelAndView mv = new ModelAndView();

        //根据user_id 查询 提现记录表（withdrawals_record）中的微信号

        String wechatNum = "";
        if (wechatNumber.equals("")) {   //不是首次积分提现
            WithdrawalsRecordEntity withdrawsRecord = withdrawalsRecordDao.findWithdrawsRecord(user_id);
            if (withdrawsRecord != null) {
                wechatNum = withdrawsRecord.getWechat_number();
                logger.info("获取提现的微信号为：", wechatNum);
            } else {
                logger.error("无法获取微信号");
                mv.addObject("err", "无法获取微信号,请重新提交申请提现！");
            }
        } else {      //首次积分提现
            wechatNum = wechatNumber;
        }

        mv.addObject("wechatNum", wechatNum);
        mv.addObject("integral", integral);
        mv.setViewName("wechat_confirmation_submission");
        return mv;
    }

    //申请提现审核提交
    @RequestMapping("withdrawals_audit")
    public void withdrawalsudit(String integral, String wechatNum, HttpSession session, HttpServletResponse resp) throws Exception {

        int user_id = wechatService.auth(session);
        if (user_id <= 0) {
            throw new Exception("进入（/withdrawals_audit）无法获取user_id");
        }
        ModelAndView mv = new ModelAndView();

        int withdrawal_limit = 0;
        if (!integral.equals("")) {
            withdrawal_limit = Integer.parseInt(integral);
        }
        //生成订单号
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sf.format(new Date());
        String orderNum = nowDate.replaceAll(":", "");
        orderNum = orderNum.replaceAll(" ", "");
        orderNum = orderNum.replaceAll("-", "");
        orderNum = orderNum + user_id;

        String phone = "";
        //根据user_id查询手机号
        GameUserEntity queryPhone = gameUserDao.findByPhone(user_id);
        if (queryPhone != null) {
            phone = queryPhone.getPhone();
        }

        int total_integral = 0;
        //根据user_id查询总积分
        GameUserEntity totalIntegral = gameUserDao.findIntegralByUserId(user_id);
        if (totalIntegral != null) {
            total_integral = totalIntegral.getIntegral();
        }

        int withdrawals_cash = Integer.parseInt(integral) * 10;


        logger.info("提现的user_id：{}", user_id);
        logger.info("单号：{}", orderNum);
        logger.info("手机号：{}", phone);
        logger.info("提现的微信号：{}", wechatNum);
        logger.info("提现的积分：{}", integral);
        logger.info("现有总积分：{}", total_integral);
        logger.info("提现金额（单位：分）：{}", withdrawals_cash);

        //将记录新增到数据库中（withdrawals_record）
        withdrawalsRecordDao.insertToWR(user_id, wechatNum, orderNum, withdrawal_limit, 2, phone, total_integral, withdrawals_cash);


        resp.sendRedirect("/withdrawals_success?orderNum=" + orderNum);
    }

    //进入提交成功页面
    @RequestMapping("withdrawals_success")
    public ModelAndView withdrawalsSuccess(String orderNum) {
        ModelAndView mv = new ModelAndView();

        mv.addObject("orderNum", orderNum);
        mv.setViewName("wechat_withdrawals_success");
        return mv;
    }

    //绑定微信号
    @RequestMapping("binding_wechat")
    public ModelAndView df(String integral) {
        ModelAndView mv = new ModelAndView();

        mv.addObject("integral", integral);
        mv.setViewName("wechat_binding_wechat");
        return mv;
    }


    //微信代理功能
    @RequestMapping("wechat_proxy_func")
    public ModelAndView wechatProxyFunc(HttpSession session, HttpServletResponse resp) throws IOException {

        int user_id = wechatService.auth(session);

        ModelAndView m = new ModelAndView();

        GameUserEntity userSt = gameUserDao.findUserStatus(user_id);
        if (userSt == null || userSt.getUser_status() == 3) {
            resp.sendRedirect("/wxLogin");
        }

        int user_status = 0;
        //根据userID查询是否为至尊
        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        if (userStatus != null) {
            user_status = userStatus.getUser_status();
        }

        logger.info("进入（/wechat_proxy_func）user_status身份值: {}", user_status);

        m.addObject("user_status", user_status);
        m.addObject("user_id", user_id);
        m.setViewName("wechat_proxy_func");
        return m;
    }

    //微信返利
    @RequestMapping("wechat_rebate_func")
    public ModelAndView rebateFunc(HttpSession session, HttpServletResponse resp) throws IOException, ParseException {

        int user_id = wechatService.auth(session);

        GameUserEntity userSt = gameUserDao.findUserStatus(user_id);
        if (userSt == null || userSt.getUser_status() == 3) {
            resp.sendRedirect("/wxLogin");
        }

        ModelAndView mv = new ModelAndView();

        //根据user_id查询 返钻 总数
        int totalRebateNum = 0;
        GameUserEntity totalIntegral = gameUserDao.findIntegralByUserId(user_id);
        if (totalIntegral != null) {
            totalRebateNum = totalIntegral.getIntegral();
        }

        int withdrawalsIntegral = 0;
        //根据user_id 查询是否有提现积分 (并且 是审核中的积分提现)
        WithdrawalsRecordEntity auditingIntegral = withdrawalsRecordDao.queryAutingIntegral(user_id);
        if (auditingIntegral != null) {
            withdrawalsIntegral = auditingIntegral.getWithdrawal_limit();
        }


        //7天积分(解冻)触发；
        // 根据userId查询冻结积分，是否达到7天，达到后解冻，并将积分增加到总可用积分中（user表）
        List<IntegralRebateEntity> frozenIntegrals = integralRebateDao.findFrozenIntegralByFlag(user_id, 1);
        if (frozenIntegrals.size() != 0) {
            for (IntegralRebateEntity frozenIntegral : frozenIntegrals) {

                String source = "充值返利";
                int integral_source = frozenIntegral.getIntegral_source();
                if (integral_source == 1) {
                    source = "推广返利";
                } else if (integral_source == 2) {
                    source = "马王红包";
                }


                String created_time = frozenIntegral.getCreated_time();
                created_time = created_time.substring(0, created_time.length() - 2);
                logger.info("积分返利时间：{}", created_time);


                SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                String now_date = slf.format(now);
                logger.info("现在时间：{}", now_date);

                //获取积分创建时间的后七天时间
                Date createdTime = slf.parse(created_time);
                Date sevenDays = new Date(createdTime.getTime() + 7 * 24 * 60 * 60 * 1000);
                String sevenDaysLater = slf.format(sevenDays);

                logger.info("积分创建第七天日期：{}", sevenDaysLater);

                //积分返利时间的后七天时间和现在时间比较，大于现在时间，则解冻积分
                long d = createdTime.getTime() - sevenDays.getTime();
                logger.info("积分返利时间的后七天时间戳：{}", sevenDays.getTime());
                logger.info("现在时间戳：{}", now.getTime());
                if (now.getTime() - sevenDays.getTime() >= 7) {

                    //根据user_id 增加积分值<总可用积分>积分解冻
                    String purpose = "用户（" + frozenIntegral.getChild_user_id() + "）给用户（" + frozenIntegral.getUser_id() + "）的" + source + "积分：" +
                            "（" + frozenIntegral.getRebate_number() + "）解冻";
                    logger.info(purpose);

                    //将已解冻的积分flag设置为：0
                    integralRebateDao.updateFrozenFlag(frozenIntegral.getUser_id(), frozenIntegral.getCreated_time(), 0);

                    //冻结积分解冻 积分新增  记录入库
                    userService.integralAddRecord(frozenIntegral.getUser_id(),frozenIntegral.getRebate_number(),0,purpose);

                } else {
                    logger.info("积分返利时间的后七天时间小于当前时间，无法解冻返利积分。");
                }
            }
        }


        logger.info("当前登录的用户 " + user_id + " 的积分返利总数：{}", totalRebateNum);

        mv.addObject("withdrawalsIntegral", withdrawalsIntegral);
        mv.addObject("totalRebateNum", totalRebateNum);
        mv.setViewName("wechat_rebate_func");
        return mv;
    }

    //我的积分
    @RequestMapping("wechat_my_rebate")
    public ModelAndView myRebate(HttpSession session) {

        int user_id = wechatService.auth(session);

        ModelAndView mv = new ModelAndView();


        //根据user_id查询 返钻 总数
        int totalRebateNum = 0;
        GameUserEntity totalIntegral = gameUserDao.findIntegralByUserId(user_id);
        if (totalIntegral != null) {
            totalRebateNum = totalIntegral.getIntegral();
        }

        int withdrawalsIntegral = 0;
        //根据user_id 查询是否有提现积分 (并且 是审核中的积分提现)
        WithdrawalsRecordEntity auditingIntegral = withdrawalsRecordDao.queryAutingIntegral(user_id);
        if (auditingIntegral != null) {
            withdrawalsIntegral = auditingIntegral.getWithdrawal_limit();
        }


        //查询总冻结积分
        IntegralRebateEntity frozenIntegral = integralRebateDao.findTotalFrozenIntegral(user_id, 1);
        int frozen_integral = 0;
        if (frozenIntegral != null) frozen_integral = frozenIntegral.getRebate_number();


        logger.info("当前登录的用户 " + user_id + " 的积分返利总数：{}", totalRebateNum);

        mv.addObject("withdrawalsIntegral", withdrawalsIntegral);
        mv.addObject("totalRebateNum", totalRebateNum);
        mv.addObject("frozen_integral", frozen_integral);
        mv.setViewName("wechat_my_rebate");
        return mv;
    }


    //游戏下载
    @RequestMapping("game_download")
    public ModelAndView gameDownload(@RequestParam(value = "channelId", defaultValue = "1") int channelId) throws Exception {
        ModelAndView m = new ModelAndView();
        Channel channel = channelDao.find(channelId);

        boolean isAppstoreVersion = false;
        if (!channel.getIos_url().startsWith("itms-services")) {
            isAppstoreVersion = true;
        }

        m.addObject("is_appstore", isAppstoreVersion);
        m.addObject("ios_url", channel.getIos_url());
        m.addObject("android_url", channel.getAndroid_url());
        m.addObject("channelId", channelId);
        m.setViewName("game_download");

        Channel channel1 = channelDao.find(99);
        if (channel1 != null) {
            m.addObject("ios_url99", channel1.getIos_url());
            m.addObject("android_url99", channel1.getAndroid_url());
        }

        return m;
    }


    /**
     * 微信钻石发送记录查询
     */
    @RequestMapping("wechatSendRecords")
    public void WSR(Integer page, String size, HttpSession session, HttpServletResponse resp) throws Exception {
        int self_id = wechatService.auth(session);

        if (page == null) page = 1;
        int limit = Integer.parseInt(size);
        int offset = (page - 1) * limit;


        String jsons = "[{\"title\":\"用户昵称\",\"date\":\"2017-12-12\"},{\"title\":\"Meizu 魅族 魅蓝note 移动4G手机16G版 蓝色 双卡双待\",\"date\":\"2017-12-01\"}]";

        List<IncomeEntity> incomeLists = incomeDao.findIncomeOther("", self_id, 2, offset, limit);

        StringBuffer sbf = new StringBuffer();
        String json = "";
        if (incomeLists.size() != 0) {
            for (IncomeEntity incomeList : incomeLists) {

                String picUrl = "assets/img/in.png";
                String bttn = "display:none;";
                if (incomeList.getStreaming_name().equals("发送")) {
                    picUrl = "assets/img/out.png";
                    bttn = "";
                }

                json = "{\"title\":\"" + incomeList.getTarget_name() + "\",\"date\":\"" + incomeList.getSend_date() + "\"," +
                        "\"diam\":\"" + incomeList.getNumber() + "\",\"id\":\"" + incomeList.getUsers_id() + "\"," +
                        "\"picUrl\":\"" + picUrl + "\",\"bttn\":\"" + bttn + "\"},";
                sbf.append(json);
            }
        } else {
            sbf.append(",");
        }

        String js = sbf.substring(0, sbf.length() - 1);
        //把字符串拼接成json数组
        jsons = "[" + js + "]";
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 微信积分返利记录 <只有至尊身份的用户才能看到>
     *
     * @param page
     * @param size
     * @param session
     * @param resp
     * @throws Exception
     */
    @RequestMapping("wechatDiamondRebate")
    public void WDR(String page, String size, HttpSession session, HttpServletResponse resp) throws Exception {

        int user_id = wechatService.auth(session);
        int pages = Integer.parseInt(page);
        int limit = Integer.parseInt(size);

        int offset = (pages - 1) * limit;

        List<IntegralRebateEntity> rebateLists = integralRebateDao.findRebatePaging(user_id, offset, limit);

        StringBuffer sbf = new StringBuffer();
        String json = "";
        if (rebateLists.size() != 0) {
            for (IntegralRebateEntity rebateList : rebateLists) {

                //根据user_id查询nickname
                GameUserEntity userNickName = gameUserDao.findNickName(rebateList.getChild_user_id());
                String nickName = "";
                if (userNickName != null) {
                    nickName = userNickName.getNickname();
                }

                String dateTime = rebateList.getCreated_time();
                dateTime = dateTime.substring(0, dateTime.length() - 2);
                json = "{\"user_id\":\"" + rebateList.getChild_user_id() + "\",\"nickname\":\"" + nickName + "\"," +
                        "\"dateTime\":\"" + dateTime + "\",\"rechargeNum\":\"" + rebateList.getRecharge_number() + "\"," +
                        "\"rebateNum\":\"" + rebateList.getRebate_number() + "\"},";
                sbf.append(json);
            }
        } else {
            sbf.append(",");
        }

        String js = sbf.substring(0, sbf.length() - 1);
        //把字符串拼接成json数组
        String jsons = "[" + js + "]";
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }

    /**
     * 微信积分提现记录
     *
     * @param page
     * @param size
     * @param session
     * @param resp
     */
    @RequestMapping("withdrawalsRecord")
    public void wRecord(String page, String size, HttpSession session, HttpServletResponse resp) throws IOException {
        int user_id = wechatService.auth(session);
        int pages = Integer.parseInt(page);
        int limit = Integer.parseInt(size);

        int offset = (pages - 1) * limit;

        List<WithdrawalsRecordEntity> WRecords = withdrawalsRecordDao.findWithdrawalsRecordPaging(user_id, offset, limit);

        StringBuffer sbf = new StringBuffer();
        String json = "";
        if (WRecords.size() != 0) {
            for (WithdrawalsRecordEntity WRecord : WRecords) {

                //时间 单号 提现额度 状态
                String dateTime = WRecord.getCreated_time();
                dateTime = dateTime.substring(0, dateTime.length() - 2);
                String verificationStatus = "审核中";
                int status = WRecord.getVerification_status();
                if (status == 0) {
                    verificationStatus = "审核拒绝";
                } else if (status == 1) {
                    verificationStatus = "审核通过";
                }
                double money = WRecord.getWithdrawal_limit() * 1.0 / 10;

                json = "{\"dateTime\":\"" + dateTime + "\",\"orderNum\":\"" + WRecord.getOrder_number() + "\"," +
                        "\"WithdrawalLimit\":\"" + money + "\",\"verificationStatus\":\"" + verificationStatus + "\"},";
                sbf.append(json);
            }
        } else {
            sbf.append(",");
        }

        String js = sbf.substring(0, sbf.length() - 1);
        //把字符串拼接成json数组
        String jsons = "[" + js + "]";
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    //进入微信新增比赛页面
    @RequestMapping("wechat_new_competition")
    public ModelAndView wechatNewCompetition() {
        return new ModelAndView("message");
    }


    /**
     * 申请加入俱乐部
     *
     * @param token 申请人token
     * @param code  俱乐部ID
     * @throws Exception
     */
    @RequestMapping("join_club")
    public void joinClub(String token, String code, HttpServletResponse resp) throws IOException {
        String json = "0";
        try {
            joinClubService.joinClub(token, code);

            //根据Code查询俱乐部创建者
            ClubsEntity queryByCode = clubsDao.findByCode(code);
            int user_id = queryByCode.getCreator_id();

            //根据token查询 申请者userId
            GameUserEntity queryUserId = gameUserDao.findUsersIDByToken(token);
            int apply_user_id = queryUserId.getId();


            /**
             * 俱乐部成员申请 消息推送
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
            GameUserEntity nickName = gameUserDao.findNickName(user_id);
            String nickname = "";
            if (nickName != null) nickname = nickName.getNickname();

            String contentAndTitle = nickname + "（ID：" + apply_user_id + "）申请加入您的俱乐部，请您尽快处理！";


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

        } catch (Exception e) {
            json = "-1";
            logger.error("加入俱乐部失败错误信息：{}", e);
        }

        PrintWriter out = resp.getWriter();
        out.print(json);
    }


//--------------------------------------------------//

    //用于临时测试日志查询
    @RequestMapping("log_check")
    public ModelAndView logCheck(HttpSession session, String page, HttpServletRequest request) throws Exception {


        String ip_address = HttpReqUtil.getRemortIP(request);
        int users_id = (int) session.getAttribute("users_id");

        //根据userId查询nickname
        String nickname = "";
        GameUserEntity username = gameUserDao.findNickName(users_id);
        if (username != null) {
            nickname = username.getNickname();
        }

        userdao.insertLogCheck("进入日志查询页面", users_id, ip_address, nickname);
        ModelAndView m = new ModelAndView();

        int pages = Integer.parseInt(page);

        //y为自己定义分为一页显示几条数据；这里一页显示16条数据；
        int y = 16;

        //查询日志；
        List<UserEntity> totalpage = userdao.findAll();

        int sum = totalpage.size();

        int xx = (pages - 1) * y;
        int yy = pages * y;

        //对所有子代理数据进行模糊加分页查询；2：代表 按时间排序
        List<UserEntity> datas = userdao.findAllPaging(xx, 16);

        //处理 总页数
        int total = disposeTotal(sum, y);

        m.addObject("total", total);
        m.addObject("page", page);//将page传到前台

        m.addObject("datas", datas);
        m.setViewName("log_check");
        return m;
    }


    /**
     * 关键词过滤
     *
     * @param keywords
     * @return
     */
    @RequestMapping("add_filtra")
    public ModelAndView addTiltration(String keywords) {
        ModelAndView mv = new ModelAndView();

        System.out.println("关键词：" + keywords);

        userdao.insertKeywords(keywords);

        List<UserEntity> keywordsList = userdao.findKeywords();


        mv.addObject("keywordsList", keywordsList);
        mv.setViewName("filtration");
        return mv;
    }


    /**
     * 游戏下载
     */
    @RequestMapping("download")
    public ModelAndView down(@RequestParam(value = "channelId", defaultValue = "1") int channelId) {
        ModelAndView mv = new ModelAndView();
        Channel channel = channelDao.find(channelId);

        mv.addObject("ios_url", channel.getIos_url());

        mv.setViewName("download");
        return mv;
    }


    /**
     * 游戏下载
     */
    @RequestMapping("jump_page")
    public ModelAndView jumpPage(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView("jump_page");
    }


    //用于临时测试关键词过滤
    @RequestMapping("filtration")
    public ModelAndView filtrations(HttpSession session, HttpServletRequest request) throws Exception {

        String ip_address = HttpReqUtil.getRemortIP(request);
        int users_id = (int) session.getAttribute("users_id");

        //根据userId查询nickname
        String nickname = "";
        GameUserEntity username = gameUserDao.findNickName(users_id);
        if (username != null) {
            nickname = username.getNickname();
        }

        userdao.insertLogCheck("进入关键词过滤页面", users_id, ip_address, nickname);
        ModelAndView m = new ModelAndView();


        List<UserEntity> keywords = userdao.findKeywords();

        m.addObject("keywordsList", keywords);
        m.setViewName("filtration");
        return m;
    }


    //用于临时测试一些网页元素
    @RequestMapping("test")
    public ModelAndView test(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView m = new ModelAndView();

        /*resp.sendRedirect("/share_download?user_id=45");*/
        m.addObject("user_id", "45");
        m.setViewName("share_download");
        return m;
    }


    /**
     * 测试用
     *
     * @return
     */
    @RequestMapping(value = "zhi", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String test(Integer userId, String createTime) throws Exception {

        int user_id = 0;
        if (userId != null) user_id = userId;


        String stringUserId = user_id + "";


        GameUserEntity userInfo = null;

        /*if(userId != null){

            userInfo = gameUserDao.findUserStatus(user_id);

            if(userInfo != null){
                int users_id = userInfo.getUsers_id();
                int user_status = userInfo.getUser_status();

                System.out.println(users_id+" - - - "+user_status);
            }else{
                System.out.println(" userInfo 为空 ");
            }

            EhcacheUtils.getInstance().put("myCache","two",userInfo);
        }else{
            userInfo = (GameUserEntity)EhcacheUtils.getInstance().get("myCache", "two");
            if(userInfo != null){
                int users_id = userInfo.getUsers_id();
                int user_status = userInfo.getUser_status();
                System.out.println(users_id+" >>> "+user_status);
            }else{
                System.out.println(" userInfo 为空 ");
            }
        }*/
        List<Map> list = (List) EhcacheUtils.getInstance().get("myCache", "allSubAgents");
        if (list != null && list.size() != 0) {
            for (Map map : list) {
                System.out.println(map.toString());
            }
        }

        //String stringUserId = transactionalService.get("myCache");

       /*事务的控制
        try {
            transactionalService.sd();
        } catch (Exception e) {
            logger.error("错误信息：{}",e+"");
        }*/

       /*for(int i=10000;i<100000;i++){
           shopDao.insertToShop(i+1,"name");
       }*/

        /*long invite_code = (int) ((Math.random() * 9 + 1) * 10000000);
        List<Long> list = new ArrayList();
        list.add(invite_code);

        for (int i = 1; i < 120; i++) {
            invite_code = (int) ((Math.random() * 9 + 1) * 10000000);

            Boolean flag = true;
            for (Long aLong : list) {
                if (invite_code == aLong) {
                    flag = false;
                    break;
                }
            }
            if(flag){
                list.add(invite_code);
            }
        }

        for (Long aLong : list) {
            System.out.println(aLong);
        }*/
        /*String str = CommonUtil.readtxt("c:/120.txt");
        for (int i = 1; i <= str.length(); i++) {
            if(i%8==0){
                System.out.println(str.substring(i-8,i));
            }
        }*/


        String nickname = "中文输出";

        return stringUserId;
    }


}

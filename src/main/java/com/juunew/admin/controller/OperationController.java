package com.juunew.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.entity.api.DataDetailsResp;
import com.juunew.admin.entity.constitute.AgentPrivilege;
import com.juunew.admin.entity.constitute.PartnershipResp;
import com.juunew.admin.entity.constitute.PrivilegedDetails;
import com.juunew.admin.services.CloudFileService;
import com.juunew.admin.services.GetUserInfoService;
import com.juunew.admin.services.HighchartsService;
import com.juunew.admin.services.MsgPushService;
import com.juunew.admin.utils.CommonUtil;
import com.juunew.admin.utils.EhcacheUtils;
import com.juunew.admin.wechat.HttpReqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by juunew on 2017/7/10.
 */
@Controller
public class OperationController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CompetitionTemplatsDao competitionTemplatsDao;
    @Autowired
    private CompetitionRewardsDao competitionRewardsDao;
    @Autowired
    private CompetitionRewardRecordsDao competitionRewardRecordsDao;
    @Autowired
    private GameUserDao gameUserDao;
    @Autowired
    SendDiamondsDao sendDiamondsDao;
    @Autowired
    private VersionsDao versionsDao;
    @Autowired
    private CompetitionsDao competitionsDao;
    @Autowired
    ClubsActionDao clubsActionDao;
    @Autowired
    DiamondsDao diamondsDao;
    @Autowired
    Financial_AuditDao financial_auditDao;
    @Autowired
    UserSpreadDao userSpreadDao;
    @Autowired
    WithdrawsDao withdrawsDao;
    @Autowired
    UserDao userDao;
    @Autowired
    OperationDailyDao operationDailyDao;
    @Autowired
    DIamondAndIncomeDao diamondAndIncomeDao;
    @Autowired
    IncomeDao incomeDao;
    @Autowired
    IntentProxyDao intentProxyDao;
    @Autowired
    WithdrawalsRecordDao withdrawalsRecordDao;
    @Autowired
    IntegralRebateDao integralRebateDao;
    @Autowired
    ClubsDao clubsDao;
    @Autowired
    GamesDao gamesDao;
    @Autowired
    ClubUsersDao clubUsersDao;
    @Autowired
    LivesDao livesDao;
    @Autowired
    PrivilegeCodeDao privilegeCodeDao;
    @Autowired
    UserPrivilegeDao userPrivilegeDao;
    @Autowired
    ClubGamesDao clubGamesDao;
    @Autowired
    GameConfigsDao gameConfigsDao;
    @Autowired
    IntegralProductDao integralProductDao;
    @Autowired
    WechatRechargeDao wechatRechargeDao;
    @Autowired
    RealtimeOnlineHistoriesDao realtimeOnlineHistoriesDao;
    @Autowired
    HighchartsService highchartsService;
    @Autowired
    IntegralsDao integralsDao;
    @Autowired
    AuthenticationDao authenticationDao;
    @Autowired
    FlowAwayDailyDao flowAwayDailyDao;
    @Autowired
    UserBanksDao userBanksDao;
    @Autowired
    FeedbackInfoDao feedbackInfoDao;
    @Autowired
    MsgPushService msgPushService;

    @Autowired
    GetUserInfoService getUserInfoService;


    @Autowired
    CloudFileService fileService;

    @Value("${upload.path}")
    String uploadPath;

    @Value("${ucloud.prefix}")
    String ucloudPrefix;


    /**---------------以下为运营模块部分--------------**/

    /**
     * 进入已完成奖品详情页面
     */
    @RequestMapping("prize_details")
    public ModelAndView prizeDetail(String page, String proxy_date, String proxy_date_end, String user_id, String opt) throws Exception {

        ModelAndView m = new ModelAndView();

        int pages = Integer.parseInt(page);

        if (proxy_date.equals("")) {
            proxy_date = "2000-01-01 01:00:00";
        }
        if (proxy_date_end.equals("")) {
            proxy_date_end = "2030-12-01 23:00:00";
        }

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date start_date = df.parse(proxy_date);
        Date end_date = df.parse(proxy_date_end);

        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y = 16;
        Map map = new HashMap();
        map.put("start_date", start_date);
        map.put("end_date", end_date);
        map.put("user_id", user_id);

        //必须使用模糊查询得到数据条数：total；
        List<CompetitionRewardRecordsEntity> addressIsNotNull = competitionRewardRecordsDao.findByRewardRecords(map);

        if (opt.equals("2")) {
            addressIsNotNull = competitionRewardRecordsDao.findByAddressNotNull(map);
        } else if (opt.equals("3")) {
            addressIsNotNull = competitionRewardRecordsDao.findByAddressNull(map);
        }

        int sum = addressIsNotNull.size();

        int xx = (pages - 1) * y;
        int yy = pages * y;

        map.put("xx", xx);
        map.put("yy", 16);

        //对所有子代理数据进行模糊加分页查询；

        //查询competition_reward_records表中所有已经发货（实物）的数据；
        List<CompetitionRewardRecordsEntity> addressSendeds = competitionRewardRecordsDao.findByAddressPaging(map);
        if (opt.equals("2")) {
            addressSendeds = competitionRewardRecordsDao.findByAddressNotNullPaging(map);
        } else if (opt.equals("3")) {
            addressSendeds = competitionRewardRecordsDao.findByAddressNullPaging(map);
        }


        for (CompetitionRewardRecordsEntity addressSended : addressSendeds) {

            //先得到competition_id，在根据competition_id 获得 templates_id
            int competition_id = addressSended.getCompetition_id();

            //SELECT * FROM competition_templates WHERE id IN (SELECT competition_template_id FROM competitions WHERE id = #{})
            CompetitionRewardRecordsEntity findTileAndRound = competitionsDao.findTileAndRound(competition_id);

            int templates_id = findTileAndRound.getId();

            addressSended.setTitle(findTileAndRound.getTitle());

            //根据templates_ID查询：已经报名人数，和比赛轮次
            CompetitionsEntity byRound = competitionsDao.findByThisRound(templates_id, competition_id);
            if (byRound != null) {
                addressSended.setRound(byRound.getSession());
            } else {
                addressSended.setRound(0);
            }


            int records_id = addressSended.getId();

            //根据ID查询address_info中的name,phone,address
            CompetitionRewardRecordsEntity addresses = competitionRewardRecordsDao.findAddresses(records_id);

            String name = "";
            String phone = "";
            String address = "";
            if (addresses != null) {
                name = addresses.getName();
                address = addresses.getAddress();
                phone = addresses.getPhone();
                name = name.replaceAll("\"", "");
                address = address.replaceAll("\"", "");
                phone = name.replaceAll("\"", "");
            }

            addressSended.setAddress(address);
            addressSended.setName(name);
            addressSended.setPhone(phone);


            //根据ID查询nickname
            GameUserEntity nickName = gameUserDao.findNickName(addressSended.getUser_id());
            addressSended.setNickname(nickName.getNickname());

            //根据当前ID在competition_rewards表中查询奖品名称等信息
            CompetitionRewardsEntity rewardsGift = competitionRewardsDao.findByRewardsGift(records_id);

            if (rewardsGift != null) {
                addressSended.setGift_title(rewardsGift.getGift_title());
                addressSended.setGet_type(rewardsGift.getGet_type());
            }
        }


        //处理 总页数
        int total = disposeTotal(sum, y);

        m.addObject("total", total);
        m.addObject("page", page);//将page传到前台
        m.addObject("user_id", user_id);
        m.addObject("opt", opt);

        if ((!proxy_date.equals("2000-01-01 01:00:00")) && (!proxy_date_end.equals("2030-12-01 23:00:00"))) {
            m.addObject("proxy_date", proxy_date);
            m.addObject("proxy_date_end", proxy_date_end);
        }
        m.addObject("addressSendeds", addressSendeds);
        m.addObject("count", sum);


        m.setViewName("prize_details");
        return m;
    }


    /**
     * 进入待确认比赛发奖信息页面
     */
    @RequestMapping("reward_pending")
    public ModelAndView pending(Integer page, String start_date, String end_date, String user_id, HttpServletRequest req) {

        ModelAndView mavw = new ModelAndView();

        if (page == null) page = 1;

        if (start_date.equals("")) start_date = "2000-01-01 01:00:00";
        if (end_date.equals("")) end_date = "2030-12-01 23:00:00";


        //获取当前时间，
        String sysDate = CommonUtil.creationDate("yyyy-MM-dd HH:mm:ss");


        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y = 16;

        Map map = new HashMap();
        map.put("start_date", start_date);
        map.put("end_date", end_date);
        map.put("user_id", user_id);

        //必须使用模糊查询得到数据条数：total；
        List<CompetitionRewardRecordsEntity> rewardRecordAll = competitionRewardRecordsDao.findRewardRecordByAddressInfo(map);  //实物赛


        int sum = rewardRecordAll.size();

        int xx = (page - 1) * y;

        map.put("xx", xx);
        map.put("yy", 16);

        //对所有子代理数据进行模糊加分页查询；
        List<CompetitionRewardRecordsEntity> rewardRecordPagings = competitionRewardRecordsDao.findRewardRecordPaging(map);


        for (CompetitionRewardRecordsEntity rewardRecordPaging : rewardRecordPagings) {

            //先得到competition_id，在根据competition_id 获得 templates_id
            int competition_id = rewardRecordPaging.getCompetition_id();

            //SELECT * FROM competition_templates WHERE id IN (SELECT competition_template_id FROM competitions WHERE id = #{})
            CompetitionRewardRecordsEntity findTileAndRound = competitionsDao.findTileAndRound(competition_id);

            int templates_id = findTileAndRound.getId();

            rewardRecordPaging.setTitle(findTileAndRound.getTitle());


            int records_id = rewardRecordPaging.getId();

            //根据ID查询address_info中的name,phone,address
            CompetitionRewardRecordsEntity addresses = competitionRewardRecordsDao.findAddresses(records_id);

            String name = "";
            String phone = "";
            String address = "";
            if (addresses != null) {
                name = addresses.getName();
                address = addresses.getAddress();
                phone = addresses.getPhone();
            }

            rewardRecordPaging.setAddress(address);
            rewardRecordPaging.setName(name);
            rewardRecordPaging.setPhone(phone);

            //根据ID查询nickname
            GameUserEntity nickName = gameUserDao.findNickName(rewardRecordPaging.getUser_id());
            rewardRecordPaging.setNickname(nickName.getNickname());


            //根据当前ID在competition_rewards表中查询奖品名称等信息
            CompetitionRewardsEntity rewardsGift = competitionRewardsDao.findByRewardsGift(records_id);
            if (rewardsGift != null) {
                rewardRecordPaging.setGift_title(rewardsGift.getGift_title());
                rewardRecordPaging.setGet_type(rewardsGift.getGet_type());
            }

            System.out.println("");
        }

        req.setAttribute("rewardRecordPagings", rewardRecordPagings);

        mavw.addObject("nowDate", sysDate);
        mavw.addObject("rewardRecordPagings", rewardRecordPagings);


        //处理 总页数
        int total = disposeTotal(sum, y);

        if ((!start_date.equals("2000-01-01 01:00:00")) && (!end_date.equals("2030-12-01 23:00:00"))) {
            mavw.addObject("start_date", start_date);
            mavw.addObject("end_date", end_date);
        }
        mavw.addObject("total", total);
        mavw.addObject("page", page);//将page传到前台
        mavw.addObject("user_id", user_id);
        mavw.addObject("count", sum);

        mavw.setViewName("reward_pending");
        return mavw;
    }


    /**
     * 待确认比赛发奖信息页面中的已发货
     */
    @RequestMapping("send_goods")
    public void sendGoods(String id, String status, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = (String) session.getAttribute("username");

        //根据ID修改competition_reward_records表中的状态
        competitionRewardRecordsDao.updateRewardRecords(Integer.parseInt(id), Integer.parseInt(status), username);


        //把字符串拼接成json数组
        String jsons = "1";

        //返回1代表成功
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 待确认比赛发奖信息页面中的  批量处理(已完成、已发货)
     */
    @RequestMapping("batch_processing")
    public void batchProcessing(String[] arrayObj, String status, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ModelAndView mdv = new ModelAndView();

        String username = (String) session.getAttribute("username");

        if (arrayObj != null) {
            for (int i = 0; i < arrayObj.length; i++) {
                //根据ID修改competition_reward_records表中的状态
                competitionRewardRecordsDao.updateRewardRecords(Integer.parseInt(arrayObj[i]), Integer.parseInt(status), username);
            }
        }

        resp.sendRedirect("/prize_details?page=1&proxy_date=&proxy_date_end=&user_id=&opt=1");
    }


    //进入比赛新增与编辑页面
    @RequestMapping("competition_editor")
    public ModelAndView competitionEditor() {
        return new ModelAndView("competition_editor");
    }


    /**
     * 比赛新增与编辑页面中的发布后台
     */
    @RequestMapping("competition_publish")
    public void competitionPublish(String seq, String competition_type, String title, String subheading, String cost, String cost_type, String registration_time, String start_date, String end_date, String rule, String participant_num, String[] strs, String show_time, String compPicUrl, String password, String round, String settle, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //获取当前时间，录入，
        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date showTime = st.parse(show_time);


        String rank = ""; //发奖励排名；

        rule = rule.replaceAll("<br>", "\\\\\\n");

        int rounds = 3;
        if (!round.equals("")) {
            rounds = Integer.parseInt(round);   //比赛轮次
        }
        int settleType = Integer.parseInt(settle);  //计分规则

        String costs = "{\"num\":" + cost + ", \"type\":1}";
        String rules = "{\"rule\": {\"firstType\": 2, \"settleType\": \"1\", \"requireColor\": false,\"totalPeriod\":\"" + rounds + "\"}, \"title\": \"" + rule + "\"}";

        //1：按张计算；有轮次； 2：计分规则,没有轮次
        if (settle.equals("2")) {
            rules = "{\"rule\": {\"firstType\": 2, \"settleType\": \"2\", \"requireColor\": false}, \"title\": \"" + rule + "\"}";
        }


        int type = Integer.parseInt(competition_type);
        int user_limit = Integer.parseInt(participant_num);


        //用于获取自增ID
        CompetitionTemplatsEntity api = new CompetitionTemplatsEntity();
        //比赛新增
        competitionTemplatsDao.insertCompetitionTemp(type, subheading, title, user_limit, costs, rules, registration_time, start_date, end_date, Integer.parseInt(seq), showTime, compPicUrl, password, api);


        int j = 2;
        String titles = "";
        int count = 0;
        String gift_title = "";
        String url = "";
        int s = 0;


        for (int i = 0; i < strs.length; i++) {

            String rewardType = "";
            if (j == i) {
                rewardType = strs[i + 1];
                if (rewardType.equals("钻石")) {
                    count += Integer.parseInt(strs[i]);
                } else if (rewardType.equals("实物")) {
                    count = 1;
                    url = strs[i + 2].replaceAll("<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">", "");
                    url = url.replaceAll("</pre>", "");
                    url = url.replaceAll("<pre>", "");
                }


                if (strs[i + 1].equals("实物")) {
                    gift_title += strs[i];
                } else {
                    gift_title += strs[i] + "" + strs[i + 1];
                }

                if (strs[j - 2].equals(strs[j - 1])) {
                    String names = "";
                    if (!strs[j + 1].equals("实物")) {
                        names = strs[j + 1];
                    }
                    titles = "第" + strs[j - 2] + "名" + strs[j] + "" + names;
                } else {
                    String names = "";
                    if (!strs[j + 1].equals("实物")) {
                        names = strs[j + 1];
                    }
                    titles = "第" + strs[j - 2] + "~" + strs[j - 1] + "名" + strs[j] + "" + names;
                }
                j += 5;
            }


            if ((i + 1) % 5 == 0) {
                //获取当前时间
                String sysDate = CommonUtil.creationDate("yyyy-MM-dd HH:mm:ss");

                if (strs[i - 4].equals(strs[i - 3])) {
                    rank = "[" + strs[i - 4] + "]";
                } else {
                    rank = "[" + strs[i - 4] + "," + strs[i - 3] + "]";
                }


                //判断是线上发放（1），手工发放（2）；
                int get_type = 1;
                if (!url.equals("")) {
                    get_type = 2;
                }
                s++;


                //logger.info("-------"+rank+","+1+","+titles+","+count+","+get_type+","+s+","+sysDate+","+api.getId()+","+gift_title+","+url);


                //奖励新增
                competitionRewardsDao.insertRewards(rank, 1, titles, count, get_type, s, sysDate, api.getId(), gift_title, url);
                titles = "";
                gift_title = "";
                count = 0;
                url = "";
            }
        }


        resp.sendRedirect("/competition_list?page=1&proxy_date=&proxy_date_end=");
    }


    /**
     * 比赛新增与编辑页面中的发布后台
     */
    @RequestMapping("competition_change")
    public void competitionPublish(String title, String subheading, String rule, String template_id, String urls, String rewardNames, String pic_url, String[] strs, HttpServletRequest req, HttpServletResponse resp) throws Exception {


        rule = rule.replaceAll("<br>", "\\\\\\n");

        String rules = "{\"rule\": {\"firstType\": 2, \"settleType\": 2, \"requireColor\": false}, \"title\": \"" + rule + "\"}";


        //根据template_id修改数据
        //比赛新增的修改
        competitionTemplatsDao.updateCompetitionTemp(subheading, title, rules, pic_url, Integer.parseInt(template_id));


        if (urls != "") {
            //1、将urls第一个逗号去掉
            String URL = urls.substring(1, urls.length());

            //2、将urls：split分割出来
            String[] url = URL.split(",");

            //根据template_id 修改 实物图片  1:先查出有实物奖励的图片url地址 的id
            List<CompetitionRewardsEntity> idByTemplateId = competitionRewardsDao.findIdByTemplateId(Integer.parseInt(template_id));

            for (int i = 0; i < url.length; i++) {

                if (idByTemplateId.size() != 0) {

                    int id = idByTemplateId.get(i).getId();

                    //根据 id 修改 gift_url 地址
                    competitionRewardsDao.updateByGiftUrl(url[i], id);
                }
            }
        }

        if (rewardNames != "") {
            //1、将urls第一个逗号去掉
            String rewardName = rewardNames.substring(1, rewardNames.length());

            //2、将urls：split分割出来
            String[] reward_name = rewardName.split(",");

            //根据template_id 修改 实物名称
            List<CompetitionRewardsEntity> ByTemplateIdData = competitionRewardsDao.findIdByTemplateIdData(Integer.parseInt(template_id));

            String titles = "";
            if (ByTemplateIdData.size() != 0) {
                for (int i = 0; i < reward_name.length; i++) {

                    int id = ByTemplateIdData.get(i).getId();

                    logger.info("奖励名次：{}", reward_name[i]);

                    //根据 id 修改 奖励名称reward_name[i]
                    competitionRewardsDao.updateTitle(reward_name[i], id);
                }
            }
        }
        resp.sendRedirect("/competition_list?page=1&proxy_date=&proxy_date_end=");
    }


    /**
     * 比赛列表页面
     */
    @RequestMapping("competition_list")
    public ModelAndView competitionList(Integer page, String proxy_date, String proxy_date_end, Integer limit) {

        ModelAndView mv = new ModelAndView();

        if (page == null) page = 1;
        if (limit == null) limit = 20;

        //获取当前时间
        String sysDate = CommonUtil.creationDate("yyyy-MM-dd HH:mm:ss");


        //必须使用模糊查询得到数据条数：total；
        List<CompetitionTemplatsEntity> allCompetitionTemplates = competitionTemplatsDao.findAll();

        //判断是否有时间，有的话就按另一种方式查询
        if (!proxy_date.equals("")) {
            allCompetitionTemplates = competitionTemplatsDao.findByEndTime(proxy_date, proxy_date_end);
        }

        int sum = allCompetitionTemplates.size();

        int offset = (page - 1) * limit;

        //对所有子代理数据进行模糊加分页查询；
        List<CompetitionTemplatsEntity> competitionTempLists = competitionTemplatsDao.findByPaging(offset, limit);

        //判断是否有时间，有的话就按另一种方式查询
        if (!proxy_date.equals("")) {
            competitionTempLists = competitionTemplatsDao.findPagingByEndTime(proxy_date, proxy_date_end, offset, limit);
        }

        for (CompetitionTemplatsEntity competitionTempList : competitionTempLists) {
            int id = competitionTempList.getId();

            //根据ID查询cost中的num
            CompetitionTemplatsEntity costNum = competitionTemplatsDao.findByCostNum(id);
            competitionTempList.setCost(costNum.getNum());

            String title = "";

            //根据ID查询Competition_rewards表中的奖品名称
            List<CompetitionRewardsEntity> rewards = competitionRewardsDao.findByRewards(id);
            if (rewards != null) {
                for (CompetitionRewardsEntity reward : rewards) {
                    title = title + reward.getTitle() + ",";
                }
            }
            competitionTempList.setRewards(title);


            //根据ID查询：已经报名人数，和比赛轮次
            CompetitionsEntity byRound = competitionsDao.findByRound(id);
            if (byRound != null) {
                competitionTempList.setRound(byRound.getRound() - 1);
            } else {
                competitionTempList.setRound(0);
            }

            CompetitionsEntity byTotalEnrollment = competitionsDao.findByTotalEnrollment(id);
            if (byTotalEnrollment != null) {
                competitionTempList.setTotal_enrollment(byTotalEnrollment.getTotal_enrollment());
            } else {
                competitionTempList.setTotal_enrollment(0);
            }
        }


        mv.addObject("nowDate", sysDate);
        mv.addObject("competitionTempLists", competitionTempLists);
        mv.addObject("proxy_date", proxy_date);
        mv.addObject("proxy_date_end", proxy_date_end);


        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("limit", limit);
        mv.addObject("count", sum);

        mv.setViewName("competition_list");
        return mv;
    }

    /**
     * 上传
     */
    @RequestMapping("fileUpload")
    public void u(@RequestParam(value = "id") String id, @RequestParam(value = "file") CommonsMultipartFile file, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String key = "reward/";

        String filePath = uploadPath;
        String avatarFile = "/" + key;
        avatarFile += new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        File localFile = new File(filePath + avatarFile);
        String picName = "";
        try {
            file.transferTo(localFile);
            fileService.putFile(localFile.getAbsolutePath(), ucloudPrefix + "/" + key);
            picName = "http://yxx.ufile.ucloud.com.cn/" + ucloudPrefix + "/" + key + localFile.getName();
        } catch (Exception e) {
            picName = e + "";
            e.printStackTrace();
        }

        PrintWriter out = resp.getWriter();
        out.print(picName);
    }


    /**
     * 比赛列表中的再次编辑 重新新建比赛
     */
    @RequestMapping("new_editor")
    public ModelAndView Neweditor(String id, String competition_status, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ModelAndView mv = new ModelAndView();

        //根据ID查询单一数据，进行再次编辑；
        CompetitionTemplatsEntity competitionTemplat = competitionTemplatsDao.findById(Integer.parseInt(id));

        CompetitionTemplatsEntity costNumAndRule = competitionTemplatsDao.findByCostNum(Integer.parseInt(id));

        RankEntity rankEntity = new RankEntity();

        String totalPeriod = "";
        //Null 处理
        if (costNumAndRule.getTotalPeriod() != null) {
            int totalPeriodLength = costNumAndRule.getTotalPeriod().length();
            if (totalPeriodLength != 1) {
                totalPeriod = costNumAndRule.getTotalPeriod().substring(1, costNumAndRule.getTotalPeriod().length() - 1);
            } else {
                totalPeriod = costNumAndRule.getTotalPeriod();
            }
        }

        String settleType = "";
        //Null 处理
        if (costNumAndRule.getSettleType() != null) {
            int settleTypeLength = costNumAndRule.getSettleType().length();

            if (settleTypeLength != 1) {
                settleType = costNumAndRule.getSettleType().substring(1, costNumAndRule.getSettleType().length() - 1);
            } else {
                settleType = costNumAndRule.getSettleType();
            }
        }

        String rules = costNumAndRule.getRules().substring(1, costNumAndRule.getRules().length() - 1);

        rules = rules.replace("\\", "");
        rules = rules.replace("n", "\n");

        competitionTemplat.setNum(costNumAndRule.getNum());
        competitionTemplat.setRule(rules);
        competitionTemplat.setTotalPeriod(totalPeriod);
        competitionTemplat.setSettleType(settleType);


        mv.addObject("competition_status", competition_status);
        mv.addObject("rankEntity", rankEntity);
        mv.addObject("competitionTemplat", competitionTemplat);
        mv.setViewName("competition_new_editor");
        return mv;
    }


    /**
     * 比赛列表中的再次编辑
     */
    @RequestMapping("editor_agian")
    public ModelAndView editorAgian(String id, String competition_status, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        ModelAndView mv = new ModelAndView();

        //根据ID查询单一数据，进行再次编辑；
        CompetitionTemplatsEntity competitionTemplat = competitionTemplatsDao.findById(Integer.parseInt(id));

        CompetitionTemplatsEntity costNumAndRule = competitionTemplatsDao.findByCostNum(Integer.parseInt(id));


        RankEntity rankEntity = new RankEntity();


        String totalPeriod = "";
        //Null 处理
        if (costNumAndRule.getTotalPeriod() != null) {
            int totalPeriodLength = costNumAndRule.getTotalPeriod().length();
            if (totalPeriodLength != 1) {
                totalPeriod = costNumAndRule.getTotalPeriod().substring(1, costNumAndRule.getTotalPeriod().length() - 1);
            } else {
                totalPeriod = costNumAndRule.getTotalPeriod();
                System.out.println();
            }
        }

        String settleType = "";
        //Null 处理
        if (costNumAndRule.getSettleType() != null) {
            int settleTypeLength = costNumAndRule.getSettleType().length();

            if (settleTypeLength != 1) {
                settleType = costNumAndRule.getSettleType().substring(1, costNumAndRule.getSettleType().length() - 1);
            } else {
                settleType = costNumAndRule.getSettleType();
                System.out.println();
            }
        }

        String rules = costNumAndRule.getRules().substring(1, costNumAndRule.getRules().length() - 1);

        rules = rules.replace("\\", "");
        rules = rules.replace("n", "\n");

        competitionTemplat.setNum(costNumAndRule.getNum());
        competitionTemplat.setRule(rules);
        competitionTemplat.setTotalPeriod(totalPeriod);


        mv.addObject("competition_status", competition_status);
        mv.addObject("rankEntity", rankEntity);
        mv.addObject("competitionTemplat", competitionTemplat);
        mv.setViewName("competition_editor_agian");
        return mv;
    }


    /**
     * 比赛奖励数据回填
     */
    @RequestMapping("competitionReward")
    public void competReward(String id, HttpServletResponse resp) throws Exception {

        //查询名次奖励
        List<CompetitionRewardsEntity> byRewards = competitionRewardsDao.findByRewards(Integer.parseInt(id));


        StringBuffer sbf = new StringBuffer();
        String json = "";
        for (int i = 0; i < byRewards.size(); i++) {
            String rank = byRewards.get(i).getRank().replace("[", "");
            rank = rank.replace("]", "");


            //将名次分离出来
            String[] ranks = rank.split(",");

            String rankOne = ranks[0];
            String rankTwo = ranks[0];
            for (int c = 1; c < ranks.length; c++) {
                rankTwo = ranks[c];
            }


            int counts = byRewards.get(i).getCount();

            String count = counts + "";

            String title = byRewards.get(i).getGift_title();
            title = title.substring(title.length() - 2, title.length());


            if (!title.equals("钻石") && !title.equals("金币")) {
                title = "实物";
                count = byRewards.get(i).getGift_title();
            }


            json = "{\"type\":\"" + title + "\",\"count\":\"" + count + "\",\"rankone\":\"" + rankOne + "\",\"ranktwo\":\"" + rankTwo + "\",\"url\":\""
                    + byRewards.get(i).getGift_url() + "\"},";
            sbf.append(json);

        }

        //把字符串拼接成json数组
        String jsons = "[" + sbf + "]";

        //返回1代表成功
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 立即终止比赛
     */
    @RequestMapping("shopCompetition")
    public void shopComp(String id, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int competition_id = Integer.parseInt(id);

        //根据ID立即终止比赛
        competitionTemplatsDao.updateEnable(competition_id, 0);

        resp.sendRedirect("/competition_list?page=1&proxy_date=&proxy_date_end=");
    }


    /**
     * 代理日志查询
     *
     * @return
     */
    @RequestMapping("proxy_log")
    public ModelAndView proxyLog(Integer page, String user_id, Integer limit, Integer status) throws Exception {
        ModelAndView mv = new ModelAndView();

        //limit为自己定义分为一页显示几条数据；
        if (limit == null) limit = 20;
        if (page == null) page = 1;

        int userId = 0;
        if (!user_id.equals("")) userId = Integer.parseInt(user_id);

        //根据user_id 查询详细信息
        GameUserEntity userInfo = getUserInfoService.getUserInfo(userId);


        //必须使用模糊查询得到数据条数：total；
        int sum = sendDiamondsDao.totalCount(user_id);

        int offset = (page - 1) * limit;

        //根据userId 查询 income 表
        List<SendDiamondsEntity> sendDiamondsEntities = sendDiamondsDao.queryPagingByUserId(user_id, offset, limit);

        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("limit", limit);
        mv.addObject("userInfo", userInfo);
        mv.addObject("user_id", user_id);
        mv.addObject("sendDiamondsEntities", sendDiamondsEntities);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);//将page传到前台

        if (status != null) {
            logger.info("进入客服工具中的代理日志查询");
            mv.setViewName("customer-service/customer_proxy_log");
        } else {
            logger.info("进入运营后台中的代理日志查询");
            mv.setViewName("proxy_log");
        }
        return mv;
    }


    /**
     * 版本配置
     */
    @RequestMapping("version_configuration")
    public ModelAndView version_config(HttpSession session, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();

        String ip_address = HttpReqUtil.getRemortIP(request);
        int users_id = (int) session.getAttribute("users_id");

        //根据userId查询nickname
        String nickname = "";
        GameUserEntity username = gameUserDao.findNickName(users_id);
        if (username != null) {
            nickname = username.getNickname();
        }

        userDao.insertLogCheck("进入版本配置页面", users_id, ip_address, nickname);

        mv.setViewName("version_configuration");
        return mv;
    }

    /**
     * 版本的发布
     */
    @RequestMapping("publish_version")
    public void publishVersion(String version, String platform, String download, String content, String force, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int user_id = (int) session.getAttribute("users_id");

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间


        versionsDao.insertVersions(Integer.parseInt(force), download, version, content, Integer.parseInt(platform), sysDate, user_id);

        resp.sendRedirect("/version_history?page=1");
    }


    /**
     * 进入历史版本查看页面
     */
    @RequestMapping("version_history")
    public ModelAndView versionHistory(String page, HttpSession session, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();

        int users_id = (int) session.getAttribute("users_id");
        String ip_address = HttpReqUtil.getRemortIP(request);

        //根据userId查询nickname
        String nickname = "";
        GameUserEntity username = gameUserDao.findNickName(users_id);
        if (username != null) {
            nickname = username.getNickname();
        }

        userDao.insertLogCheck("进入历史版本查询页面", users_id, ip_address, nickname);
        int pages = Integer.parseInt(page);


        //y为自己定义分为一页显示几条数据；这里一页显示3条数据；
        int y = 10;

        //必须使用模糊查询得到数据条数：total；
        List<VersionsEntity> byVersions = versionsDao.findByVersions();

        int sum = byVersions.size();

        int xx = (pages - 1) * y;
        int yy = pages * y;

        //对所有子代理数据进行模糊加分页查询；
        List<VersionsEntity> versionsList = versionsDao.findByVersionsPaging(xx, 20);

        for (VersionsEntity versionslist : versionsList) {
            int user_id = versionslist.getUser_id();
            //根据user_id查询登录的用户名

            GameUserEntity usernames = gameUserDao.findUsername(user_id);
            if (usernames != null) {
                versionslist.setNickName(usernames.getUsername());
            } else {
                versionslist.setNickName("");
            }

        }


        mv.addObject("versionsList", versionsList);

        //处理 总页数
        int total = disposeTotal(sum, y);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台

        mv.setViewName("version_history");
        return mv;
    }


    /**
     * 版本的删除
     */
    @RequestMapping("version_del")
    public void dd(String id, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int version_id = Integer.parseInt(id);

        //根据version_id删除数据
        versionsDao.delVersionsById(version_id);
        resp.sendRedirect("/version_history?page=1");
    }


    //运营日报  <用datatables重构运营日报页面>实现页面字段排序
    @RequestMapping("operation_daily")
    public ModelAndView operaDaily() {
        return new ModelAndView("operation_daily");
    }

    //ajax调用实现查询，分页，等
    @RequestMapping(value = "/operationDailyByPage")
    @ResponseBody
    public String operationDaily(HttpServletRequest request) throws Exception {
        //直接返回前台
        String draw = request.getParameter("draw");
        //数据起始位置
        String startIndex = request.getParameter("startIndex");
        //每页显示的条数
        String pageSize = request.getParameter("pageSize");
        //获取排序字段
        String orderColumn = request.getParameter("orderColumn");
        //排序列为空时，默认按时间排序
        if (orderColumn == null) orderColumn = "daily_date";
        //获取排序方式
        String orderDir = request.getParameter("orderDir");
        if (orderDir == null) orderDir = "desc";

        //排序方式
        String orders = " order by " + orderColumn + " " + orderDir + " ";  //orderColumn
        Map map = new HashMap();
        map.put("order", orders);    //排序字段  startIndex 从0开始
        int xx = Integer.parseInt(startIndex) * Integer.parseInt(pageSize);
        map.put("xx", xx);
        map.put("yy", Integer.parseInt(pageSize));


        //必须使用模糊查询得到数据条数：total；
        List<OperationDailyEntity> allDataTotal = operationDailyDao.findAllData();
        int sum = allDataTotal.size();

        //对所有子代理数据进行模糊加分页查询；
        List<OperationDailyEntity> dailyDatas = operationDailyDao.findAllDataPaging(map);

        double doubPrice = 0.0;
        for (OperationDailyEntity dailyData : dailyDatas) {
            int price = dailyData.getPay_total();
            if (price != 0) {
                doubPrice = price * 1.0 / 100;
                dailyData.setPay_doubPrice(doubPrice);
            }
        }

        Map<Object, Object> info = new HashMap<Object, Object>();
        info.put("pageData", dailyDatas);
        info.put("total", sum);
        info.put("draw", draw);
        return JSONObject.toJSONString(info) + "";
    }


    /**
     * 进入玩家日志查询页面
     *
     * @param user_id
     * @param status  进入客服玩家日志查询或运营中的玩家之日查询的标识，为 1 表示客服
     */
    @RequestMapping("log_output")
    public ModelAndView LogOutput(Integer page, String user_id, Integer limit, Integer status) throws Exception {
        ModelAndView ml = new ModelAndView();

        String pn = user_id;
        if (user_id.equals("")) pn = "-1";

        int userId = 0;

        if (page == null) page = 1;
        if (limit == null) limit = 20;


        //根据手机号或昵称查询userID
        GameUserEntity gameUser = gameUserDao.findByPhoneOrNickname(pn);
        if (gameUser != null) userId = gameUser.getId();


        //根据user_id 查询详细信息
        GameUserEntity userInfo = null;
        if (userId > 0) {
            userInfo = getUserInfoService.getUserInfo(userId);
            if (userInfo == null) {
                getUserInfoService.buildRelationships(userId);
                userInfo = getUserInfoService.getUserInfo(userId);
            }
        }


        //必须使用模糊查询得到数据条数：total；
        int sum = diamondAndIncomeDao.findTotal(userId);

        int offset = (page - 1) * limit;

        List<DiamondAndIcomeEntity> logFiles = diamondAndIncomeDao.findByUserId(userId, offset, limit);

        //处理 总页数
        int total = disposeTotal(sum, limit);

        ml.addObject("limit", limit);
        ml.addObject("user", userInfo);
        ml.addObject("user_id", user_id);
        ml.addObject("logFiles", logFiles);
        ml.addObject("total", total);
        ml.addObject("page", page);//将page传到前台
        ml.addObject("count", sum);//将page传到前台

        if (status != null) {
            logger.info("进入客服工具标签中的玩家日志查询");
            ml.setViewName("customer-service/customer_log_output");
        } else {
            logger.info("进入运营后台标签中的玩家日志查询");
            ml.setViewName("log_output");
        }
        return ml;
    }


    //用Datatables重构《付费玩家记录页面》
    @RequestMapping("paid_records")
    public ModelAndView paidRecords() {
        return new ModelAndView("paid_gamer_records");
    }

    //ajax调用实现查询，分页，等
    @RequestMapping(value = "/getCarrierByPage", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCarrierByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {


        //获取需要搜索的userId
        String user_id = request.getParameter("user_id");

        //直接返回前台
        String draw = request.getParameter("draw");
        //数据起始位置
        String startIndex = request.getParameter("startIndex");
        //每页显示的条数
        String pageSize = request.getParameter("pageSize");
        //获取排序字段
        String orderColumn = request.getParameter("orderColumn");
        if (orderColumn == null) {
            orderColumn = "id";
        }
        //获取排序方式
        String orderDir = request.getParameter("orderDir");
        if (orderDir == null) orderDir = "desc";

        //排序方式
        String orders = " order by " + orderColumn + " " + orderDir + " ";  //orderColumn
        Map map = new HashMap();
        map.put("order", orders);    //排序字段  startIndex 从0开始
        int xx = Integer.parseInt(startIndex) * Integer.parseInt(pageSize);
        map.put("xx", xx);
        map.put("yy", Integer.parseInt(pageSize));

        map.put("user_id", user_id);
        map.put("start_date", "2010-01-01 01:00:00");
        map.put("end_date", "2030-01-01 01:00:00");


        //从 orders 表中查询 付费玩家信息
        List<DiamondsEntity> ordersData = diamondsDao.findOrders(map);
        int sum = ordersData.size();    //查询总条数
        logger.info("ordersData.size()值：{}", sum);


        //查询数据
        List<DiamondsEntity> ordersDatas = diamondsDao.findOrdersPaging(map);
        if (ordersDatas.size() != 0) {
            for (DiamondsEntity order : ordersDatas) {

                int userId = order.getUser_id();
                int pay_type = order.getPay_type();

                int price = order.getPrice();

                double doubPrice = 0.0;
                if (price != 0) {
                    doubPrice = price * 1.0 / 100;
                }
                order.setDoubPrice(doubPrice);


                if (pay_type == 1) {    //苹果充值，查询products表
                    int product_id = order.getProduct_id();

                    DiamondsEntity products = diamondsDao.findForProducts(product_id);
                    if (products != null) {
                        order.setRecharge_diamond(products.getDiamond());
                    } else {
                        order.setRecharge_diamond(0);
                    }

                } else if (pay_type == 2) {    //微信充值，查询（自己数据库中）products表

                    int product_id = order.getProduct_id();

                    IntegralProductEntity products = integralProductDao.queryById(product_id);
                    if (products != null) {
                        order.setRecharge_diamond(products.getDiamond());
                    } else {
                        order.setRecharge_diamond(0);
                    }

                    //充值描述
                } else if (pay_type == 3) {    //平台充值，product 表中的product_id 是 financial_audit 表中的id

                    int product_id = order.getProduct_id();

                    //根据product_id 查询 financial_audit 表
                    Financial_AuditEntity diamonds = financial_auditDao.findById(product_id);

                    if (diamonds != null) {
                        logger.info("平台充值user_id（" + order.getUser_id() + "） 钻石数量：{}", diamonds.getDiamond());
                        order.setRecharge_diamond(diamonds.getDiamond());
                    } else {
                        order.setRecharge_diamond(0);
                    }
                }

                //根据user_id查询 昵称
                GameUserEntity getNickname = diamondsDao.findNickname(userId);
                if (getNickname != null) {
                    String nickname = getNickname.getNickname();
                    //nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
                    order.setNickname(nickname);
                }


                //根据user_id查询当前钻石数量；findDiamond
                Integer getDiamond = gameUserDao.diamond(userId);
                if (getDiamond != null) {
                    order.setDiamond(getDiamond);
                }


                //将时间最后 .0 去掉
                String created_at = order.getCreated_at();
                created_at = created_at.substring(0, created_at.length() - 2);
                order.setCreated_at(created_at);
            }
        }

        Map<Object, Object> info = new HashMap<Object, Object>();
        info.put("pageData", ordersDatas);
        info.put("total", sum);
        info.put("draw", draw);
        return JSONObject.toJSONString(info) + "";
    }


    /**
     * 代理总积分记录页面
     *
     * @param page
     * @return
     */
    @RequestMapping("proxy_integral_record")
    public ModelAndView integralRecord(Integer page, String user_id, Integer limit) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;


        //必须使用模糊查询得到数据条数：total；
        int sum = integralRebateDao.findTotal(user_id);

        int offset = (page - 1) * limit;

        //模糊加分页查询；
        List<IntegralRebateEntity> rebates = integralRebateDao.findRebatePagingVague(user_id, offset, limit);
//        List<IntegralRebateEntity> rebates = integralRebateDao.findRebatePagingAll();
        for (IntegralRebateEntity rebate : rebates) {

            //根据user_id查询 子代理昵称
            int userId = rebate.getUser_id();
            GameUserEntity userNickName = gameUserDao.findNickName(userId);
            if (userNickName != null) {
                rebate.setUser_id_nickname(userNickName.getNickname());
            } else {
                rebate.setUser_id_nickname("");
            }

            //查询上级代理昵称
            int childUserId = rebate.getChild_user_id();
            GameUserEntity childUserNickName = gameUserDao.findNickName(childUserId);
            if (childUserNickName != null) {
                rebate.setChild_user_id_nickname(childUserNickName.getNickname());
            } else {
                rebate.setChild_user_id_nickname("");
            }
            double rebateNum = 0;
            int rebate_number = rebate.getRebate_number();
            if (rebate_number > 0) rebateNum = rebate_number * 1.0 / 100;
            rebate.setRebate_num(rebateNum);
        }

        mv.addObject("rebates", rebates);

        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("limit", limit);
        mv.addObject("user_id", user_id);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);//总数据条数

        mv.setViewName("proxy_integral_record");
        return mv;
    }

    /*//datatables服务端分页
    @RequestMapping("p")
    public String datatables(String page,String user_id,String limit){

        int pages = Integer.parseInt(page);

        int y = 20;
        if(!limit.equals("")) y = Integer.parseInt(limit);


        //必须使用模糊查询得到数据条数：total；
        int sum = integralRebateDao.findTotal(user_id);

        int xx = (pages - 1) * y;

        //模糊加分页查询；
        List<IntegralRebateEntity> rebates = integralRebateDao.findRebatePagingVague(user_id, xx, y);
        for (IntegralRebateEntity rebate : rebates) {

            //根据user_id查询 子代理昵称
            int userId = rebate.getUser_id();
            GameUserEntity userNickName = gameUserDao.findNickName(userId);
            if(userNickName != null){
                rebate.setUser_id_nickname(userNickName.getNickname());
            }else{
                rebate.setUser_id_nickname("");
            }

            //查询上级代理昵称
            int childUserId = rebate.getChild_user_id();
            GameUserEntity childUserNickName = gameUserDao.findNickName(childUserId);
            if(childUserNickName!=null){
                rebate.setChild_user_id_nickname(childUserNickName.getNickname());
            }else{
                rebate.setChild_user_id_nickname("");
            }
        }

        mv.addObject("rebates", rebates);

        //处理 总页数
        int total = disposeTotal(sum,y);

        mv.addObject("limit", limit);
        mv.addObject("user_id", user_id);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);//总数据条数

        return "";
    }
*/

    /**
     * 查看代理积分
     *
     * @param page
     * @return
     */
    @RequestMapping("view_proxy_integral")
    public ModelAndView viewProxyIntegral(String page) {
        ModelAndView mv = new ModelAndView();

        List list = new ArrayList();

        //查询所有返利记录 的 user_id
        List<IntegralRebateEntity> rebateRecords = integralRebateDao.findAll();
        if (rebateRecords.size() != 0) {
            for (IntegralRebateEntity rebateRecord : rebateRecords) {
                ViewIntegralEntity viewIntegralEntity = new ViewIntegralEntity();

                int user_id = rebateRecord.getUser_id();

                viewIntegralEntity.setUser_id(user_id);


                //查询返利子代理个数
                List<IntegralRebateEntity> rebateChildTotal = integralRebateDao.findRebateChildTotal(user_id);
                int childTotal = rebateChildTotal.size();
                viewIntegralEntity.setRebate_child_total(childTotal);

                //子代理返利笔数
                int childTotalCount = integralRebateDao.findChildTotalCount(user_id);
                viewIntegralEntity.setChild_total_count(childTotalCount);

                //总返利积分
                IntegralRebateEntity totalRebate = integralRebateDao.findTotalRebate(user_id);
                int total_rebate = 0;
                if (totalRebate != null) {
                    total_rebate = totalRebate.getRebate_number();
                }
                viewIntegralEntity.setTotal_rebate(total_rebate);

                //提现积分
                WithdrawalsRecordEntity withdrawalRebate = withdrawalsRecordDao.findWithdrawalRebate(user_id);
                int withdrawal_rebate = 0;
                if (withdrawalRebate != null) {
                    withdrawal_rebate = withdrawalRebate.getWithdrawal_limit();
                }
                viewIntegralEntity.setWithdrawal_rebate(withdrawal_rebate);

                //当前积分
                GameUserEntity presentRebate = gameUserDao.findIntegralByUserId(user_id);
                int present_rebate = 0;
                if (presentRebate != null) {
                    present_rebate = presentRebate.getIntegral();
                }
                viewIntegralEntity.setPresent_rebate(present_rebate);
                list.add(viewIntegralEntity);
            }
        }

        mv.addObject("list", list);
        mv.setViewName("view_proxy_integral");
        return mv;
    }

    //判断是否为数字
    public static int isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return 0;
            }
        }
        return Integer.parseInt(str);
    }

    /**
     * 俱乐部管理页面
     */
    @RequestMapping("club_management")
    public ModelAndView clubManagement(String queryId, Integer limit, String queryStatus, Integer page, Integer status) {
        ModelAndView mv = new ModelAndView();

        int query_id = 0;

        if (limit == null) limit = 20;
        if (page == null) page = 1;


        //必须使用模糊查询得到数据条数：total；
        int sum = clubsDao.findTotal();

        if (!queryId.equals("")) {
            query_id = isNumeric(queryId);  //判断是否为数字

            if (queryStatus.equals("1")) {
                sum = clubsDao.findTotalById(query_id);
            } else {
                sum = clubsDao.findTotalByClubId(query_id);
            }
        }


        int offset = (page - 1) * limit;

        //模糊加分页查询；
        List<ClubsEntity> clubs = clubsDao.findClubPaging(offset, limit);
        if (!queryId.equals("")) {
            if (queryStatus.equals("1")) {
                clubs = clubsDao.findClubByUserId(query_id, offset, limit);
            } else {
                clubs = clubsDao.findClubByClubId(query_id, offset, limit);
            }
        }

        for (ClubsEntity club : clubs) {
            int id = club.getId();

            int user_id = club.getCreator_id();


            //根据俱乐部ID查询该俱乐部的总人数
            int members = clubUsersDao.findAllMembersByClubId(id);
            club.setMembers(members);


            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(new Date());
            String start_time = date + " 00:00:00";
            String end_time = date + " 23:59:59";

            //根据ClubID查询当日局数（live）表
            int todayGameTimes = livesDao.findTotal(id, start_time, end_time);
            club.setToday_game_times(todayGameTimes);
        }


        //对 实体类中的 todayGameTimes 进行排序操作
        CommonUtil.listIntSort(clubs);


        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台

        mv.addObject("queryStatus", queryStatus);
        mv.addObject("userId", queryId);
        mv.addObject("clubs", clubs);
        mv.addObject("count", sum);

        if (status != null) {
            mv.setViewName("customer-service/customer_club_management");
        } else {
            mv.setViewName("club_management");
        }
        return mv;
    }

    //俱乐部详细信息查看
    @RequestMapping("club_details")
    public ModelAndView clubDetails(String club_id, Integer page, String limit) {
        ModelAndView mv = new ModelAndView();

        logger.info("俱乐部ID: {}", club_id);

        int id = 0;
        if (!club_id.equals("")) id = Integer.parseInt(club_id);

        int club_status = 0;    //俱乐部封停状态

        //根据俱乐部ID查询数据
        ClubsEntity queryClub = clubsDao.findById(id);
        if (queryClub != null) {

            int gamekind = queryClub.getGamekind();
            String game_kind = "";
            if (gamekind == 2) {
                game_kind = "上游";
            } else if (gamekind == 3) {
                game_kind = "激K";
            } else {
                game_kind = "麻将";
            }
            queryClub.setGame_kind(game_kind);

            //查询总人数
            int totalMember = clubUsersDao.findAllMembersByClubId(id);
            queryClub.setMembers(totalMember);

            //处理创建时间
            String created_time = queryClub.getCreated_at();
            created_time = created_time.substring(0, created_time.length() - 2);
            queryClub.setCreated_at(created_time);


            //根据俱乐部ID查询封停状态
            club_status = queryClub.getStatus();
        }


        //----------上是俱乐部信息，下是该俱乐部成员信息-----------

        if (page == null) page = 1;

        int y = 20;
        if (!limit.equals("")) y = Integer.parseInt(limit);

        //必须使用模糊查询得到数据条数：total；
        int sum = clubUsersDao.findAllMembersByClubId(id);

        int offset = (page - 1) * y;


        //根据俱乐部ID查询俱乐部成员
        List<ClubUsersEntity> members = clubUsersDao.findAllMembers(id, offset, y);
        for (ClubUsersEntity member : members) {
            int user_id = member.getUser_id();

            //查昵称
            GameUserEntity nickName = gameUserDao.findNickName(user_id);
            String nickname = "";
            if (nickName != null) nickname = nickName.getNickname();

            member.setNickname(nickname);
        }


        //处理 总页数
        int total = disposeTotal(sum, y);

        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        if (queryClub != null) mv.addObject("club", queryClub);
        mv.addObject("clubStatus", club_status);
        mv.addObject("members", members);
        mv.addObject("count", sum);
        mv.setViewName("club_details");
        return mv;
    }

    //俱乐部踢出成员
    @RequestMapping("kick_member")
    public void kickMember(String clubUserId, HttpServletResponse resp) throws IOException {

        int id = Integer.parseInt(clubUserId);

        //根据ID 删除 (clubUser表)中数据
        clubUsersDao.delById(id);
        String json = "1";

        PrintWriter out = resp.getWriter();
        out.print(json);
    }

    /**
     * 修改俱乐部公告或名称
     *
     * @param clubId 俱乐部ID
     * @param status 1：修改公告，2：修改名称
     */
    @RequestMapping("reviseClub")
    public void reviseClubs(String clubId, String status, String announcement, String name, HttpServletResponse resp) throws IOException {

        String json = "1";
        int id = 0;
        if (!clubId.equals("")) id = Integer.parseInt(clubId);

        if (announcement.equals("") || name.equals("") || id == 0) {
            json = "0";
        } else {
            if (status.equals("1")) {     //修改公告
                clubsDao.updateAnnouncement(id, announcement);
            } else {     //修改名称
                clubsDao.updateName(id, name);
            }
        }

        PrintWriter out = resp.getWriter();
        out.print(json);
    }

    //封停和解封俱乐部
    @RequestMapping("changeClubStatus")
    public void changeStatus(String clubStatus, String clubId, HttpServletResponse resp) throws IOException {

        int id = 0;
        int status = -1;
        if (!clubId.equals("")) id = Integer.parseInt(clubId);
        if (!clubStatus.equals("")) status = Integer.parseInt(clubStatus);
        String json = "1";
        if (clubStatus.equals("0")) json = "2";   //为俱乐部解封操作

        if (status != -1 && id != 0) {
            //改变俱乐部状态
            clubsDao.updateStatus(id, status);
        } else {
            json = "0";
        }

        PrintWriter out = resp.getWriter();
        out.print(json);
    }


    /**
     * 意向代理页面，从客户端获得数据
     *
     * @return
     */
    @RequestMapping("intent_proxy")
    public ModelAndView intentProxy(String page) {
        ModelAndView mv = new ModelAndView();

        int pages = Integer.parseInt(page);

        int y = 16;

        //total；
        int sum = intentProxyDao.getTotal();

        int xx = (pages - 1) * y;
        int yy = pages * y;

        //对所有子代理数据进行模糊加分页查询；
        List<IntentProxyEntity> intentProxys = intentProxyDao.findIntentProxyPaging(xx, 16);

        //处理 总页数
        int total = disposeTotal(sum, y);

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);//将page传到前台

        mv.addObject("intentProxys", intentProxys);
        mv.setViewName("intent_proxy");
        return mv;
    }

    /**
     * 将意向代理中的备注存储
     */
    @RequestMapping("saveRemark")
    public void saveRemark(String id, String remark, HttpServletResponse resp) throws IOException {

        //把字符串拼接成json数组
        String jsons = remark;

        //将备注新增到数据库中
        intentProxyDao.updateRemark(Integer.parseInt(id), remark);

        //返回1代表成功
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }

    @RequestMapping("changeConnection")
    public void changeConnection(String id, HttpServletResponse resp, HttpServletRequest req) throws IOException {

        //根据id改为 已联系
        intentProxyDao.updateConnection(Integer.parseInt(id));
        resp.sendRedirect("/intent_proxy?page=1");
    }

    /**
     * 将意向代理信息存入数据库
     *
     * @param name
     * @param phone
     * @return
     */
    @RequestMapping("saveIntentInfo")
    public void saveIntent(String name, String phone, HttpServletResponse resp) throws IOException {

        logger.info("客户端填写name: {}", name);
        logger.info("客户端填写phone: {}", phone);

        //将信息存入数据库
        intentProxyDao.insertToIntentProxy(name, phone);

        String jsons = "1";
        //返回1代表成功
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 积分提现审核<财务>
     *
     * @param page 第几页
     * @return
     */
    @RequestMapping("integral_withdrawals")
    public ModelAndView integralWithdrawals(Integer page, Integer limit) throws Exception {
        ModelAndView mv = new ModelAndView();

        if (page == null) page = 1;
        if (limit == null) limit = 20;

        //total；
        int sum = withdrawsDao.getTotal();

        int offset = (page - 1) * limit;

        //对所有子代理数据进行模糊加分页查询；
        List<WithdrawsEntity> WRecords = withdrawsDao.findWRAllPaging(offset, limit);
        if (WRecords.size() != 0) {
            for (WithdrawsEntity wRecord : WRecords) {
                int amount = wRecord.getAmount();
                Double cash = amount * 1.0 / 100;
                wRecord.setCash(cash);

                Date created_at = wRecord.getCreated_at();
                SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String created_time = slf.format(created_at);
                wRecord.setCreated_time(created_time);


                int user_id = wRecord.getUser_id();
                //查询（user）表中是否有对应的关系 //根据user_id 查询 昵称和身份
                GameUserEntity user = gameUserDao.findUserByUserId(user_id);
                if (user == null) {
                    //建立关系
                    ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
                }
                GameUserEntity userInfo = gameUserDao.findusers(user_id);
                String nickname = userInfo.getNickname();
                int user_status = userInfo.getUser_status();

                String userStatus = "玩家";
                if (user_status == 2) {
                    userStatus = "代理";
                } else if (user_status == 1) {
                    userStatus = "总代理";
                } else if (user_status == 4) {
                    userStatus = "合伙人";
                }
                wRecord.setNickname(nickname);
                wRecord.setUser_status(userStatus);

                double scoreNum = 0;
                int score_num = wRecord.getScore();
                if (score_num > 0) scoreNum = score_num * 1.0 / 100;

                wRecord.setScoreNum(scoreNum);
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("WRecords", WRecords);//将page传到前台

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);
        mv.addObject("limit", limit);

        mv.setViewName("integral_withdrawals");
        return mv;
    }

    /**
     * 积分提现审核<运营>
     *
     * @param page 第几页
     * @return
     */
    @RequestMapping("integral_withdrawals_operation")
    public ModelAndView integralWithdrawalsOp(String page, String limit) throws Exception {
        ModelAndView mv = new ModelAndView();

        int pages = Integer.parseInt(page);

        int y = 20;
        if (!limit.equals("")) y = Integer.parseInt(limit);

        //total；
        int sum = withdrawsDao.getTotal();


        int offset = (pages - 1) * y;

        //对所有子代理数据进行模糊加分页查询；
        List<WithdrawsEntity> WRecords = withdrawsDao.findWRAllPaging(offset, y);
        if (WRecords.size() != 0) {
            for (WithdrawsEntity wRecord : WRecords) {
                int amount = wRecord.getAmount();
                double cash = amount * 1.0 / 100;
                wRecord.setCash(cash);

                Date created_at = wRecord.getCreated_at();
                SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String created_time = slf.format(created_at);
                wRecord.setCreated_time(created_time);


                int user_id = wRecord.getUser_id();
                //查询（user）表中是否有对应的关系 //根据user_id 查询 昵称和身份
                GameUserEntity user = gameUserDao.findUserByUserId(user_id);
                if (user == null) {
                    //建立关系
                    ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
                }
                GameUserEntity userInfo = gameUserDao.findusers(user_id);
                String nickname = userInfo.getNickname();
                int user_status = userInfo.getUser_status();

                String userStatus = "玩家";
                if (user_status == 2) {
                    userStatus = "代理";
                } else if (user_status == 1) {
                    userStatus = "总代理";
                } else if (user_status == 4) {
                    userStatus = "合伙人";
                }
                wRecord.setNickname(nickname);
                wRecord.setUser_status(userStatus);


                double scoreNum = 0;
                int score_num = wRecord.getScore();
                if (score_num > 0) {
                    scoreNum = score_num * 1.0 / 100;
                }

                logger.info(score_num + "、" + scoreNum);

                wRecord.setScoreNum(scoreNum);
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, y);

        mv.addObject("WRecords", WRecords);//将page传到前台

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);
        mv.addObject("limit", y);

        mv.setViewName("integral_withdrawals_operation");
        return mv;
    }

    /**
     * 积分提现审核<管理员审核>
     *
     * @param page 第几页
     * @return
     */
    @RequestMapping("integral_withdrawals_admin")
    public ModelAndView integralWithdrawalsAdmin(Integer page, Integer limit) throws Exception {
        ModelAndView mv = new ModelAndView();

        if (page == null) page = 1;
        if (limit == null) limit = 20;

        //total；
        int sum = withdrawsDao.getTotal();

        int offset = (page - 1) * limit;

        //对所有子代理数据进行模糊加分页查询；
        List<WithdrawsEntity> WRecords = withdrawsDao.findWRAllPaging(offset, limit);
        if (WRecords.size() != 0) {
            for (WithdrawsEntity wRecord : WRecords) {
                int amount = wRecord.getAmount();
                Double cash = amount * 1.0 / 100;
                wRecord.setCash(cash);

                Date created_at = wRecord.getCreated_at();
                SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String created_time = slf.format(created_at);
                wRecord.setCreated_time(created_time);


                int user_id = wRecord.getUser_id();
                //查询（user）表中是否有对应的关系 //根据user_id 查询 昵称和身份
                GameUserEntity user = gameUserDao.findUserByUserId(user_id);
                if (user == null) {
                    //建立关系
                    ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
                }
                GameUserEntity userInfo = gameUserDao.findusers(user_id);
                String nickname = userInfo.getNickname();
                int user_status = userInfo.getUser_status();

                String userStatus = "玩家";
                if (user_status == 2) {
                    userStatus = "代理";
                } else if (user_status == 1) {
                    userStatus = "总代理";
                } else if (user_status == 4) {
                    userStatus = "合伙人";
                }
                wRecord.setNickname(nickname);
                wRecord.setUser_status(userStatus);

                double scoreNum = 0;
                int score_num = wRecord.getScore();
                if (score_num > 0) scoreNum = score_num * 1.0 / 100;
                wRecord.setScoreNum(scoreNum);
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("WRecords", WRecords);//将page传到前台

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);
        mv.addObject("limit", limit);

        mv.setViewName("integral_withdrawals_admin");
        return mv;
    }

    /**
     * 积分提现审核<超过500RMB需通过老板审核>
     *
     * @param page 第几页
     * @return
     */
    @RequestMapping("integral_withdrawals_supervisor")
    public ModelAndView integralWithdrawalsSuper(String page) throws Exception {
        ModelAndView mv = new ModelAndView();

        int pages = Integer.parseInt(page);

        int y = 16;

        //total；
        int sum = withdrawalsRecordDao.getTotal();

        int offset = (pages - 1) * y;
        int yy = pages * y;

        //对所有子代理数据进行模糊加分页查询；
        List<WithdrawalsRecordEntity> WRecords = withdrawalsRecordDao.findWRPaging(offset, 16, 5);
        if (WRecords.size() != 0) {
            for (WithdrawalsRecordEntity wRecord : WRecords) {

                int wCash = wRecord.getWithdrawals_cash();
                Double cash = wCash * 1.0 / 100;
                wRecord.setCash(cash);
            }
        }


        //处理 总页数
        int total = disposeTotal(sum, y);

        mv.addObject("WRecords", WRecords);//将page传到前台

        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台

        mv.setViewName("integral_withdrawals_supervisor");
        return mv;
    }

    /**
     * 积分提现审核 <通过与拒绝>，
     *
     * @param status   3 4：审核通过，0：审核拒绝；2：已入账
     * @param trade_no 申请的订单号
     * @param ID
     */
    @RequestMapping("integral_audit")
    public void audit(String byWho, String status, String ID, String trade_no, String page, String limit, HttpServletResponse resp) throws Exception {

        int pages = 1;
        int limits = 20;
        if (page != null && !"".equals(page)) pages = Integer.parseInt(page);
        if (limit != null && !"".equals(limit)) limits = Integer.parseInt(limit);

        int id = 0;
        if (!ID.equals("")) id = Integer.parseInt(ID);
        if (trade_no.equals("")) {
            throw new Exception("无法获取提现审核的订单号！");
        }

        //根据id 将（withdrawals_record）表中的 verification_status 审核状态 修改
        if (status.equals("1")) {

            //调用审核通过方法：
            ReviewAndPassThrough(id, trade_no);
            resp.sendRedirect("/integral_withdrawals_supervisor?page=" + pages + "&limit=" + limit);
        } else if (status.equals("0")) {       //审核拒绝


            //提现申请审核拒绝  <将状态置位审核通过：3>
            withdrawsDao.updateAudit(id, 3, trade_no);
            //修改小红点状态
            withdrawsDao.updateRedDot(1, id, trade_no);


            WithdrawsEntity refuseWithdraws = withdrawsDao.findRefuseWithdraws(id, 3, trade_no);
            int integral = 0;
            if (refuseWithdraws != null) {
                integral = refuseWithdraws.getScore();
            } else {
                throw new Exception("无法查询到审核拒绝的用户提现信息");
            }

            GameUserEntity queryIntegral = gameUserDao.findIntegralByUserId(id);
            int old_val = 0, new_val = 0;
            if (queryIntegral != null) {
                old_val = queryIntegral.getIntegral();
                new_val = old_val;
            }

            //记录积分操作
            integralsDao.insertToIntegrals(id, old_val, new_val, integral, 0, "拒绝申请提现积分扣除，并冻结24小时，单号：" + trade_no);


            //时间处理操作
            SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = slf.format(new Date());
            Date createdTime = slf.parse(format);

            //获取积分创建时间的后七天时间
            Date sevenDays = new Date(createdTime.getTime() - 6 * 24 * 60 * 60 * 1000);
            String sixDaysAgo = slf.format(sevenDays);


            //冻结积分
            integralRebateDao.insertToRebateHours(id, 1, 0, integral, sixDaysAgo, 1);


            JSONObject contentObject = new JSONObject();
            contentObject.put("title", "拒绝申请提现");
            contentObject.put("content", "您的提现申请已被客服拒绝，积分将冻结24小时后返回给您。");
            contentObject.put("orderId", refuseWithdraws.getId());      //订单id
            contentObject.put("type", 2);     //通知类型 1. 申请通过  2. 申请未通过
            //调用消息推送接口  cmd为11时 为积分审核通过
            msgPushService.newPushSysMsg(11, contentObject, id);


            //判断是哪个审核人员拒绝的：进去哪个权限相对应的页面
            if (byWho.equals("2")) {
                resp.sendRedirect("/integral_withdrawals_operation?page=" + pages + "&limit=" + limits);
            } else if (byWho.equals("3")) {
                resp.sendRedirect("/integral_withdrawals?page=" + pages + "&limit=" + limits);
            } else if (byWho.equals("4")) {
                resp.sendRedirect("/integral_withdrawals_admin?page=" + pages + "&limit=" + limits);
            } else if (byWho.equals("5")) {
                resp.sendRedirect("/integral_withdrawals_supervisor?page=" + pages + "&limit=" + limits);
            }
        } else if (status.equals("3")) {   //运营审核通过

            withdrawsDao.updateProcessAudit(id, 3, trade_no);
            resp.sendRedirect("/integral_withdrawals_operation?page=" + pages + "&limit=" + limits);
        } else if (status.equals("4")) {   //财务审核通过

            withdrawsDao.updateProcessAudit(id, 4, trade_no);
            resp.sendRedirect("/integral_withdrawals?page=" + pages + "&limit=" + limits);
        } else if (status.equals("5")) {   //管理审核通过

            //调用审核通过处理方法
            ReviewAndPassThrough(id, trade_no);

            resp.sendRedirect("/integral_withdrawals_admin?page=" + pages + "&limit=" + limits);
        }
    }

    /**
     * 审核通过时，将调用此方法
     *
     * @param id 为（withdraws表）中的主键 ID
     * @throws Exception
     */
    private void ReviewAndPassThrough(int id, String trade_no) throws Exception {
        int integral = 0;
        int user_id = 0;

        //将（user）表中的积分值扣除,先查询提现的积分值
        WithdrawsEntity auditInfo = withdrawsDao.findAuditInfor(id, 1);
        if (auditInfo != null) {
            integral = auditInfo.getScore();
            user_id = auditInfo.getUser_id();
        } else {
            throw new Exception("积分提现审核（/integral_audit）无法获取 提现用户的 提现记录");
        }

        if (integral != 0) {

            /*GameUserEntity oldIntegral = gameUserDao.findIntegralByUserId(user_id);
            int old_val = 0;
            if (oldIntegral != null) old_val = oldIntegral.getIntegral();

            if (old_val < integral) throw new Exception("提现提分大于当前可用积分");

            //扣除相应积分
            gameUserDao.deductionIntegral(integral, user_id);

            GameUserEntity newIntegral = gameUserDao.findIntegralByUserId(user_id);
            int new_val = 0;
            if (newIntegral != null) new_val = newIntegral.getIntegral();

            //记录积分操作
            integralsDao.insertToIntegrals(user_id, old_val, new_val, integral, 0, "提现申请通过，扣除积分");
*/

            //提现申请审核通过  <将状态置位审核通过：1>
            withdrawsDao.updateAudit(user_id, 2, trade_no);
            //修改小红点状态
            withdrawsDao.updateRedDot(1, user_id, trade_no);


            JSONObject contentObject = new JSONObject();
            contentObject.put("title", "申请提现通过");
            contentObject.put("content", "恭喜，您的积分体现申请已经审核通过，马上进入游戏点击即可提现！");
            contentObject.put("orderId", auditInfo.getId());      //订单id
            contentObject.put("type", 1);     //通知类型 1. 申请通过  2. 申请未通过

            //调用消息推送接口  cmd为11时 为积分审核通过
            msgPushService.newPushSysMsg(11, contentObject, user_id);

        } else {
            throw new Exception("无法查询 用户提现积分");
        }

    }


    /**
     * 积分审核通过后，截图上传后存入数据库
     *
     * @param img 图片地址
     */
    @RequestMapping("saveImgToWithdrawals")
    public void uploadImgToWithdrawals(String img, String uploadId, HttpServletResponse resp) throws IOException {

        logger.info("截图上传后的图片地址：{}", img);

        int id = Integer.parseInt(uploadId);

        //将图片存入数据库中：
        withdrawalsRecordDao.updateImg(id, img);

        //把字符串拼接成json数组
        String jsons = img;

        //返回1代表成功
        PrintWriter out = resp.getWriter();
        out.print(jsons);
    }


    /**
     * 新增消息页面
     *
     * @return
     */
    @RequestMapping("message_system")
    public ModelAndView msgSystem(Integer status) {
        if (status != null) {
            return new ModelAndView("customer-service/customer_message_system");
        }
        return new ModelAndView("message_system");
    }


    /**
     * 客户端 客服页面
     *
     * @return
     */
    @RequestMapping("customer_service")
    public ModelAndView customerService() {
        return new ModelAndView("client_customer_service");
    }


//----------------------------------------以下是统计图相关页面-----------------------


    //进入统计图页面
    @RequestMapping("chart")
    public ModelAndView newUsersPage(HttpSession session) {
        logger.info("进入（/chart）页面");
        session.removeAttribute("dateSession");
        return new ModelAndView("statistical_chart");
    }


    //进入付费笔数统计图页面
    @RequestMapping("paid_count")
    public ModelAndView paidCountPage(HttpSession session) {
        logger.info("进入（/paid_count）页面");
        session.removeAttribute("dateSession");
        return new ModelAndView("paid_count");
    }

    /**
     * 付费笔数折线图数据展示
     *
     * @param resp
     * @throws IOException
     */
    @RequestMapping("paidCountStatistics")
    @ResponseBody
    public void paidUser(String date, HttpServletResponse resp, HttpSession session) throws IOException, ParseException {
        logger.info("进入（/paidCountStatistics）");

        //先从缓存中取数据，是为了新增的时间轴一直存在
        List<String> dateSession = (List) session.getAttribute("dateSession");

        //处理时间轴的List
        List<String> dates = highchartsService.getDateList(date, session, dateSession);

        String json = "";
        for (String disposeDate : dates) {
            //根据时间付费笔数
            List<WechatRechargeEntity> payCount = wechatRechargeDao.findPayCountByDate(disposeDate + " 00:00:00", disposeDate + " 23:59:59");

            //处理Data数据的封装
            String data = highchartsService.disposedHighchartsData(disposeDate, payCount, 2);
            json = json + "{\"name\":\"" + disposeDate + "\",\"data\":" + data + "},";
        }
        json = json.substring(0, json.length() - 1);   //去掉最后一个逗号

        PrintWriter out = resp.getWriter();
        String jsons = "[" + json + "]";

        out.println(jsons);
        out.flush();
        out.close();
    }


    //进入付费金额统计图页面
    @RequestMapping("pay_amount")
    public ModelAndView paidUserPage(HttpSession session) {
        logger.info("进入（/pay_amount）页面");
        session.removeAttribute("dateSession");
        return new ModelAndView("pay_amount");
    }

    /**
     * 付费金额折线图数据展示
     *
     * @param resp
     * @throws IOException
     */
    @RequestMapping("payAmountStatistics")
    @ResponseBody
    public void payAmount(String date, HttpServletResponse resp, HttpSession session) throws IOException, ParseException {
        logger.info("进入（/payAmountStatistics）");

        //先从缓存中取数据，是为了新增的时间轴一直存在
        List<String> dateSession = (List) session.getAttribute("dateSession");

        //处理时间轴的List
        List<String> dates = highchartsService.getDateList(date, session, dateSession);

        String json = "";
        for (String disposeDate : dates) {
            //根据时间付费用户
            List<WechatRechargeEntity> payCount = wechatRechargeDao.findPayCountByDate(disposeDate + " 00:00:00", disposeDate + " 23:59:59");

            //处理Data数据的封装
            String data = highchartsService.disposedHighchartsData(disposeDate, payCount, 3);

            json = json + "{\"name\":\"" + disposeDate + "\",\"data\":" + data + "},";
        }
        json = json.substring(0, json.length() - 1);   //去掉最后一个逗号

        PrintWriter out = resp.getWriter();
        String jsons = "[" + json + "]";

        out.println(jsons);
        out.flush();
        out.close();
    }


    //进入活跃玩家统计图页面
    @RequestMapping("active_user")
    public ModelAndView activeUserPage(HttpSession session) {
        logger.info("进入（/active_user）页面");
        session.removeAttribute("dateSession");
        ModelAndView mv = new ModelAndView("active_user");
        return mv;
    }

    /**
     * 活跃玩家折线图数据展示
     *
     * @param resp
     * @throws IOException
     */
    @RequestMapping("activeUserStatistics")
    @ResponseBody
    public void activeUser(String date, HttpServletResponse resp, HttpSession session) throws IOException, ParseException {
        logger.info("进入（/activeUserStatistics）");

        //先从缓存中取数据，是为了新增的时间轴一直存在
        List<String> dateSession = (List) session.getAttribute("dateSession");

        //处理时间轴的List
        List<String> dates = highchartsService.getDateList(date, session, dateSession);

        List activeUserList = new ArrayList();
        String json = "";
        for (String disposeDate : dates) {
            //根据时间活跃玩家
            List<RealtimeOnlineHistoriesEntity> activeUsers = realtimeOnlineHistoriesDao.findActiveUserByDate(disposeDate + " 00:00:00", disposeDate + " 23:59:59");
            if (activeUsers.size() != 0) {

                //移除 activeUserList 中的所有元素，目的是为了当选择相同日期时activeUserList不会保存上一次值；
                activeUserList.removeAll(activeUserList);

                for (RealtimeOnlineHistoriesEntity activeUser : activeUsers) {
                    int user_id = activeUser.getUser_id();

                    //根据UserID做一天活跃用户的去重处理
                    RealtimeOnlineHistoriesEntity activeUse = realtimeOnlineHistoriesDao.findActiveUserByUserId(disposeDate + " 00:00:00", disposeDate + " 23:59:59", user_id);
                    if (activeUse != null) activeUserList.add(activeUse);
                }
            }

            //处理Data数据的封装
            String data = highchartsService.disposedHighchartsData(disposeDate, activeUserList, 4);

            json = json + "{\"name\":\"" + disposeDate + "\",\"data\":" + data + "},";
        }
        json = json.substring(0, json.length() - 1);   //去掉最后一个逗号

        PrintWriter out = resp.getWriter();
        String jsons = "[" + json + "]";

        out.println(jsons);
        out.flush();
        out.close();
    }

    //进入分时统计页面
    @RequestMapping("add_users")
    public ModelAndView timeSharingStatistics(HttpSession session) {
        logger.info("进入（/add_users）页面");
        session.removeAttribute("dateSession");
        return new ModelAndView("add_users");
    }

    /**
     * 折线图数据展示
     *
     * @param resp
     * @throws IOException
     */
    @RequestMapping("testCases")
    @ResponseBody
    public void highcharts(String date, HttpServletResponse resp, HttpSession session) throws IOException, ParseException {
        logger.info("进入（/testCases）");

        //先从缓存中取数据，是为了新增时间后的时间轴一直存在
        List<String> dateSession = (List) session.getAttribute("dateSession");

        //处理时间轴的List
        List<String> dates = highchartsService.getDateList(date, session, dateSession);

        String json = "";

        for (String disposeDate : dates) {
            //根据时间查询用户新增数量
            List<GameUserEntity> newUsers = gameUserDao.findAddUserByDate(disposeDate + " 00:00:00", disposeDate + " 23:59:59");

            //处理Data数据的封装,status:表示不同的统计数据：1：用户新增数量，2：付费笔数，
            String data = highchartsService.disposedHighchartsData(disposeDate, newUsers, 1);

            json = json + "{\"name\":\"" + disposeDate + "\",\"data\":" + data + "},";
        }
        json = json.substring(0, json.length() - 1);   //去掉最后一个逗号

        PrintWriter out = resp.getWriter();
        String jsons = "[" + json + "]";

        out.println(jsons);
        out.flush();
        out.close();
    }



   /* //进入付费用户统计图页面
    @RequestMapping("paid_user")
    public ModelAndView paidUserPage(HttpSession session){
        session.removeAttribute("dateSession");
        ModelAndView mv = new ModelAndView("paid_user");
        return mv;
    }
    */

    /**
     * 付费用户折线图数据展示
     *
     * @param
     * @throws IOException
     *//*
    @RequestMapping("paidUserStatistics")
    @ResponseBody
    public void paidCount(String date,HttpServletResponse resp,HttpSession session) throws IOException, ParseException {

        //先从缓存中取数据，是为了新增的时间轴一直存在
        List<String> dateSession = (List)session.getAttribute("dateSession");
        List<String> dates =null;
        if (dateSession != null){
            dates = dateSession;
            if(!date.equals("")) dates.add(date);
        }else{
            dates = new ArrayList<String>();
            dates.add("2017-09-05");        //默认数据  今天
            dates.add("2017-09-06");        //昨天
            dates.add("2017-09-07");        //前天
            if(!date.equals("")) dates.add(date);
            session.setAttribute("dateSession",dates);
        }

        String json ="";
        for (String disposeDate : dates) {
            //根据时间付费用户
            List<WechatRechargeEntity> payCount = wechatRechargeDao.findPayCountByDate(disposeDate + " 00:00:00", disposeDate + " 23:59:59");

            //处理Data数据的封装
            String data = disposedHighchartsData(disposeDate, payCount,3);

            json = json + "{\"name\":\""+disposeDate+"\",\"data\":"+data+"},";
        }
        json = json.substring(0,json.length()-1);   //去掉最后一个逗号

        PrintWriter out = resp.getWriter();
        String jsons = "["+json+"]";

        out.println(jsons);
        out.flush();
        out.close();
    }*/


    //修改密码页面
    @RequestMapping("horse_config")
    public ModelAndView horseConfig() {
        return new ModelAndView("horse_config");
    }


    //修改密码页面
    @RequestMapping("change_password")
    public ModelAndView changePass() {
        return new ModelAndView("change_password");
    }


    //达标俱乐部活动，
    @RequestMapping("clubs_action")
    public ModelAndView clubsAction(String page, String limit) {

        ModelAndView mv = new ModelAndView();

        int y = 20;
        int pages = 1;
        if (!limit.equals("")) y = Integer.parseInt(limit);
        if (!page.equals("")) pages = Integer.parseInt(page);

        //必须使用模糊查询得到数据条数：total；
        int sum = clubsActionDao.queryTotal();

        int xx = (pages - 1) * y;


        List<ClubsActionEntity> clubsCreators = clubsActionDao.queryCreatorId();
        if (clubsCreators.size() != 0) {
            for (ClubsActionEntity clubsCreator : clubsCreators) {
                int creator_id = clubsCreator.getCreator_id();

                //根据 创建者ID 查询最高一个 活动获得的积分
                ClubsActionEntity clubsActionEntity = clubsActionDao.queryHighestRewardIntegral(creator_id);
                clubsCreator.setCreated_time(clubsActionEntity.getCreated_time());

                //查询昵称
                String nickname = "";
                GameUserEntity nickName = gameUserDao.findNickName(creator_id);
                if (nickName != null) nickname = nickName.getNickname();
                clubsCreator.setNickname(nickname);

                //处理时间
                Date created_at = clubsActionEntity.getCreated_at();
                SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String created_time = slf.format(created_at);
                clubsCreator.setCreated_time(created_time);

                clubsCreator.setAudit(clubsActionEntity.getAudit());
                clubsCreator.setClub_code(clubsActionEntity.getClub_code());
                clubsCreator.setId(clubsActionEntity.getId());
                clubsCreator.setLevel(clubsActionEntity.getLevel());
                clubsCreator.setIntegral(clubsActionEntity.getReward_integral() * 1.0 / 100);
            }
        }


        //处理 总页数
        int total = disposeTotal(sum, y);

        mv.addObject("limit", limit);
        mv.addObject("clubsActions", clubsCreators);
        mv.addObject("total", total);
        mv.addObject("page", page);//将page传到前台
        mv.addObject("count", sum);//将page传到前台


        mv.setViewName("clubs_action");
        return mv;
    }


    /**
     * 俱乐部活动获得积分审核，
     *
     * @param status 审核状态；1：通过，0：拒绝
     * @param ID
     */
    @RequestMapping("clubs_action_audit")
    public void clubsActionAudit(String status, String ID, HttpServletResponse resp) throws Exception {

        String json = "";

        int id = 0;
        if (!ID.equals("")) id = Integer.parseInt(ID);

        if (status.equals("1")) {
            //修改审核状态
            clubsActionDao.changeAudit(1, id);

            //根据ID 查询数据（clubs_action）表
            ClubsActionEntity clubsActionEntity = clubsActionDao.findById(id);
            if (clubsActionEntity != null) {

                int user_id = clubsActionEntity.getCreator_id();
                int integral = clubsActionEntity.getReward_integral();


                //查询该用户是否存在 user表中，如果没有，则建立关系
                GameUserEntity user = gameUserDao.findUserByUserId(user_id);
                if (user == null) {
                    //建立关系
                    ModelAndView modelView = getUserInfoService.buildRelationships(user_id);
                }


                //根据user_id 增加积分值<总可用积分>积分解冻
                String purpose = "用户（" + user_id + "）获得俱乐部活动积分：" + integral + "，冻结中";

                logger.info(purpose);


                //查询修改前的积分值
                GameUserEntity queryIntegral = gameUserDao.findIntegralByUserId(user_id);
                int old_val = 0, new_val = 0;
                if (queryIntegral != null) {
                    old_val = queryIntegral.getIntegral();
                    new_val = old_val;
                }

                integralRebateDao.insertToRebateTwo(user_id, 1, 0, integral, 1, 3);

                //记录积分操作
                integralsDao.insertToIntegrals(user_id, old_val, new_val, integral, 0, purpose);

                json = "1";
            } else {
                logger.error("（clubs_action_audit）（俱乐部活动获得积分运营后台审核）无法获取数据");
                json = "0";
            }
        } else {
            //审核拒绝
            clubsActionDao.changeAudit(0, id);
            json = "1";
        }


        PrintWriter out = resp.getWriter();
        out.print(json);
    }


    //玩家积分管理
    @RequestMapping("integral_manage")
    public ModelAndView integralManage(Integer status) {
        if (status != null) {
            return new ModelAndView("customer-service/customer_integral_manage");
        }
        return new ModelAndView("integral_manage");
    }

    //ajax调用实现查询，分页，等<玩家积分管理>
    @RequestMapping(value = "/integralManageByAjax", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String integralManageByAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {


        //获取需要搜索的userId
        String user_id = request.getParameter("user_id");


        //直接返回前台
        String draw = request.getParameter("draw");
        //数据起始位置
        String startIndex = request.getParameter("startIndex");
        //每页显示的条数
        String pageSize = request.getParameter("pageSize");
        //获取排序字段
        String orderColumn = request.getParameter("orderColumn");
        if (orderColumn == null) {
            orderColumn = "integral";
        }
        //获取排序方式
        String orderDir = request.getParameter("orderDir");
        if (orderDir == null) orderDir = "desc";

        //排序方式
        String orders = " order by " + orderColumn + " " + orderDir + " ";  //orderColumn
        Map map = new HashMap();
        map.put("order", orders);    //排序字段  startIndex 从0开始
        int xx = Integer.parseInt(startIndex) * Integer.parseInt(pageSize);
        map.put("xx", xx);
        map.put("yy", Integer.parseInt(pageSize));
        map.put("user_id", user_id);


        //查询总条数
        int sum = gameUserDao.totalEffectiveIntegral(user_id);
        logger.info("ordersData.size()值：{}", sum);


        //查询积分大于0的所有用户
        List<GameUserEntity> gameUserEntities = gameUserDao.allEffectiveIntegral(map);
        if (gameUserEntities.size() != 0) {
            for (GameUserEntity gameUserEntity : gameUserEntities) {
                int id = gameUserEntity.getUsers_id();

                //根据ID查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();

                //根据ID查询 用户来源（是否是推广而来的用户）
                UserSpreadEntity userSource = userSpreadDao.findByUserId(id);
                int user_source = 1;    //1：默认，2：被推广用户
                if (userSource != null) user_source = 2;

                //根据ID查询 付费金额  <不包括运营平台充值>
                WechatRechargeEntity wechatRechargeEntity = wechatRechargeDao.totalRechargeByUserId(id);
                int total_recharge = 0;
                if (wechatRechargeEntity != null) total_recharge = wechatRechargeEntity.getRecharge_money();

                //根据ID查询 当前钻石
                UserBanksEntity userBanksEntity = userBanksDao.queryTotalDiamond(id);
                int diamond = 0;
                if (userBanksEntity != null) diamond = userBanksEntity.getDiamond();


                gameUserEntity.setNickname(nickname);
                gameUserEntity.setUser_source(user_source);
                gameUserEntity.setTotal_recharge(total_recharge * 1.0 / 100);
                gameUserEntity.setDiamond(diamond);
                gameUserEntity.setTotal_integral(gameUserEntity.getIntegral() * 1.0 / 100);
            }
        }

        Map<Object, Object> info = new HashMap<Object, Object>();
        info.put("pageData", gameUserEntities);
        info.put("total", sum);
        info.put("draw", draw);
        return JSONObject.toJSONString(info) + "";
    }


    //游戏开房数据详情
    @RequestMapping("room_details")
    public ModelAndView roomDetails(String page, String limit, String chooseDate) {
        ModelAndView mv = new ModelAndView();

        int y = 20;
        int pages = 1;
        if (!limit.equals("")) y = Integer.parseInt(limit);
        if (!page.equals("")) pages = Integer.parseInt(page);

        int xx = (pages - 1) * y;

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
        String date = slf.format(new Date());
        if (!chooseDate.equals("")) date = chooseDate;

        //测试日期
        //date = "2018-02-01";

        String start_time = date + " 00:00:00";
        String end_time = date + " 23:59:59";

        //logger.info("查询游戏开房详情的日期为：{}", date);

        //必须使用模糊查询得到数据条数：total；
        int sum = gamesDao.queryTotalByTime(start_time, end_time);

        List<GamesEntity> gamesEntities = gamesDao.queryByTime(start_time, end_time, xx, y);
        if (gamesEntities != null) {
            for (GamesEntity gamesEntity : gamesEntities) {

                //游戏类型
                int gamekinds_id = gamesEntity.getGamekinds_id();


                //房间战绩，游戏分数详情
                String desc = "";

                String result = gamesEntity.getResult();
                if (result != null) {
                    JSONArray results = JSONObject.parseArray(result);
                    if (results.size() != 0) {
                        for (Object rts : results) {

                            String res = "";

                            JSONObject json = JSONObject.parseObject(rts + "");
                            int userId = json.getInteger("userId");
                            int score = 0;
                            if (gamekinds_id == 4) {    //4：麻将
                                score = json.getInteger("totalScore");
                            } else {
                                score = json.getInteger("score");
                            }

                            //根据user_id 查询昵称
                            GameUserEntity nickName = gameUserDao.findNickName(userId);
                            String nickname = "";
                            if (nickName != null) nickname = nickName.getNickname();

                            res = nickname + "：" + score;
                            desc = desc + res + "<br>";
                        }
                    } else {
                        logger.error("result中json数据为空");
                    }
                } else {
                    desc = "游戏已开始，第一局未打完";
                }


                //时间去掉 .0
                String created_at = gamesEntity.getCreated_at();
                created_at = created_at.substring(0, created_at.length() - 2);
                String end_at = gamesEntity.getEnd_at();
                end_at = end_at.substring(0, end_at.length() - 2);

                //获取昵称
                int user_id = gamesEntity.getAnchor_id();
                GameUserEntity nickName = gameUserDao.findNickName(user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();

                //查询房间类型（根据lives_id查询lives表中的club_id为0则是私房，否则为俱乐部）
                int id = gamesEntity.getLives_id();
                LivesEntity lives = livesDao.findById(id);
                int club_id = -1;
                String code = "无法获取code房间号";
                if (lives != null) {
                    club_id = lives.getClub_id();
                    code = lives.getCode();
                }

                String root_type = "无法获取livesID";
                if (club_id > 0) root_type = "俱乐部";
                if (club_id == 0) root_type = "私房";


                int num = 0;    //人数

                //查询房间人数 （games表中players 中userId不等于0）
                String players = gamesEntity.getPlayers();

                if (players != null) {
                    JSONArray player = JSONObject.parseArray(players);
                    if (player.size() != 0) {
                        for (Object ply : player) {
                            JSONObject json = JSONObject.parseObject(ply + "");
                            int userId = json.getInteger("userId");
                            if (userId > 0) num++;
                        }
                    } else {
                        logger.error("players中json数据为空");
                    }
                }


                gamesEntity.setDesc(desc);
                gamesEntity.setCreated_at(created_at);
                gamesEntity.setEnd_at(end_at);
                gamesEntity.setNickname(nickname);
                gamesEntity.setRoom_type(root_type);
                gamesEntity.setCode(code);
                gamesEntity.setNum(num);
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, y);

        if (gamesEntities.size() == 0) {
            mv.addObject("error", "暂无数据");
        } else {
            mv.addObject("error", "");
        }


        mv.addObject("gamesEntities", gamesEntities);
        mv.addObject("date", date);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.setViewName("room_details");
        return mv;
    }


    //当日新增
    @RequestMapping("add_details")
    public ModelAndView addDetails(String page, String limit, String chooseDate) {
        ModelAndView mv = new ModelAndView();

        int y = 20;
        int pages = 1;
        if (!limit.equals("")) y = Integer.parseInt(limit);
        if (!page.equals("")) pages = Integer.parseInt(page);

        int xx = (pages - 1) * y;

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
        String date = slf.format(new Date());
        if (!chooseDate.equals("")) date = chooseDate;

        //测试日期
        //date = "2017-07-27";

        String start_time = date + " 00:00:00";
        String end_time = date + " 23:59:59";

        //logger.info("查询当日新增详情的日期为：{}", date);

        //必须使用模糊查询得到数据条数：total；
        int sum = gameUserDao.queryTotalByTime(start_time, end_time);


        //根据日期查询所有新增用户
        List<GameUserEntity> gameUserEntities = gameUserDao.addUserByDatePaging(start_time, end_time, xx, y);
        if (gameUserEntities.size() != 0) {
            for (GameUserEntity gameUserEntity : gameUserEntities) {
                int user_id = gameUserEntity.getId();

                //查询上级代理的 ID 和 昵称
                GameUserEntity mySuperior = gameUserDao.queryMySuperior(user_id);
                int superior_user_id = 0;
                String superior_user_nickname = "";
                if (mySuperior != null) {
                    superior_user_id = mySuperior.getId();
                    superior_user_nickname = mySuperior.getNickname();
                    if (superior_user_id == 1) superior_user_nickname = "系统代理";
                }


                String gameDetails = "";

                //查询新增用户的当天游戏局数
                List<GamesEntity> gamesEntities = gamesDao.queryAddUserGameTimes(start_time, end_time, user_id);
                if (gamesEntities.size() != 0) {
                    for (GamesEntity gamesEntity : gamesEntities) {

                        //游戏类型
                        int gamekinds_id = gamesEntity.getGamekinds_id();
                        //游戏次数
                        int times = gamesEntity.getTimes();

                        String gameType = "上游";
                        if (gamekinds_id == 3) {
                            gameType = "激K";
                        } else if (gamekinds_id == 4) {
                            gameType = "麻将";
                        }

                        gameDetails = gameDetails + gameType + "：" + times + "局 <br>";
                    }
                } else {
                    gameDetails = "暂无游戏纪录";
                }


                //根据ID查询 付费金额  <不包括运营平台充值>
                WechatRechargeEntity wechatRechargeEntity = wechatRechargeDao.totalRechargeByUserId(user_id);
                int total_recharge = 0;
                if (wechatRechargeEntity != null) total_recharge = wechatRechargeEntity.getRecharge_money();


                //查询当前钻石
                UserBanksEntity userBanksEntity = userBanksDao.queryTotalDiamond(user_id);
                int diamond = 0;
                if (userBanksEntity != null) diamond = userBanksEntity.getDiamond();


                //查询当前积分和身份
                GameUserEntity queryUserInfo = gameUserDao.findUserStatus(user_id);

                int integral = 0;
                int user_status = 3;    //2：代理、3：玩家

                if (queryUserInfo != null) {
                    user_status = queryUserInfo.getUser_status();
                    integral = queryUserInfo.getIntegral();
                }


                //时间去掉 .0
                String created_at = gameUserEntity.getCreated_at();
                created_at = created_at.substring(0, created_at.length() - 2);


                gameUserEntity.setTotal_recharge(total_recharge * 1.0 / 100);
                gameUserEntity.setDiamond(diamond);
                gameUserEntity.setTotal_integral(integral * 1.0 / 100);
                gameUserEntity.setUser_status(user_status);
                gameUserEntity.setCreated_at(created_at);
                gameUserEntity.setSuperior_user_id(superior_user_id);
                gameUserEntity.setSuperior_user_nickname(superior_user_nickname);
                gameUserEntity.setGameDetails(gameDetails);
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, y);

        if (gameUserEntities.size() == 0) {
            mv.addObject("error", "暂无数据");
        } else {
            mv.addObject("error", "");
        }

        mv.addObject("gameUserEntities", gameUserEntities);
        mv.addObject("date", date);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.setViewName("add_details");
        return mv;
    }


    //付费数据详情
    @RequestMapping("payment_details")
    public ModelAndView paymentDetails(String page, String limit, String chooseDate) {
        ModelAndView mv = new ModelAndView();

        int y = 20;
        int pages = 1;
        if (!limit.equals("")) y = Integer.parseInt(limit);
        if (!page.equals("")) pages = Integer.parseInt(page);

        int xx = (pages - 1) * y;

        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
        String date = slf.format(new Date());
        if (!chooseDate.equals("")) date = chooseDate;

        //测试日期
        //date = "2017-09-07";

        String start_time = date + " 00:00:00";
        String end_time = date + " 23:59:59";

        logger.info("查询当日付费数据详情的日期为：{}", date);

        //必须使用模糊查询得到数据条数：total；
        int sum = wechatRechargeDao.queryTotalPayByDate(start_time, end_time);

        List<WechatRechargeEntity> wechatRechargeEntities = wechatRechargeDao.queryPayByDate(start_time, end_time, xx, y);
        if (wechatRechargeEntities.size() != 0) {
            for (WechatRechargeEntity wechatRechargeEntity : wechatRechargeEntities) {

                int user_id = wechatRechargeEntity.getUser_id();

                //查询当前钻石
                UserBanksEntity userBanksEntity = userBanksDao.queryTotalDiamond(user_id);
                int diamond = 0;
                if (userBanksEntity != null) diamond = userBanksEntity.getDiamond();


                //查询当前积分和身份
                GameUserEntity queryUserInfo = gameUserDao.findUserStatus(user_id);

                int integral = 0;
                int user_status = 3;    //2：代理、3：玩家

                if (queryUserInfo != null) {
                    user_status = queryUserInfo.getUser_status();
                    integral = queryUserInfo.getIntegral();
                }


                //查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();


                //付费时间去掉 .0
                String created_date = wechatRechargeEntity.getCreated_date();
                created_date = created_date.substring(0, created_date.length() - 2);

                int recharge_money = wechatRechargeEntity.getRecharge_money();
                double money = recharge_money * 1.0 / 100;


                wechatRechargeEntity.setDiamond(diamond);
                wechatRechargeEntity.setTotal_integral(integral * 1.0 / 100);
                wechatRechargeEntity.setUser_status(user_status);
                wechatRechargeEntity.setCreated_date(created_date);
                wechatRechargeEntity.setNickname(nickname);
                wechatRechargeEntity.setMoney(money);
            }
        }


        //处理 总页数
        int total = disposeTotal(sum, y);

        if (wechatRechargeEntities.size() == 0) {
            mv.addObject("error", "暂无数据");
        } else {
            mv.addObject("error", "");
        }

        mv.addObject("wechatRechargeEntities", wechatRechargeEntities);
        mv.addObject("date", date);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.setViewName("payment_details");
        return mv;
    }


    //活跃数据详情
    @RequestMapping("active_details")
    public ModelAndView activeDetail(Integer page, Integer limit, String chooseDate) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;

        int offset = (page - 1) * limit;

        String date = CommonUtil.creationDate("yyyy-MM-dd");
        if (!chooseDate.equals("")) date = chooseDate;

        //测试日期
        //date = "2018-02-01";

        String start_time = date + " 00:00:00";
        String end_time = date + " 23:59:59";

        logger.info("查询当日活跃数据详情的日期为：{}", date);


        //必须使用模糊查询得到数据条数：total；
        int sum = realtimeOnlineHistoriesDao.queryOnlineCountByTime(start_time, end_time);

        List<RealtimeOnlineHistoriesEntity> realtimeOnlineHistoriesEntities = realtimeOnlineHistoriesDao.queryOnlineByTime(start_time, end_time, offset, limit);
        if (realtimeOnlineHistoriesEntities.size() != 0) {
            for (RealtimeOnlineHistoriesEntity realtimeOnlineHistoriesEntity : realtimeOnlineHistoriesEntities) {
                int user_id = realtimeOnlineHistoriesEntity.getUser_id();

                int total_club_games = 0;   //今日开房次数 （只有代理有开房次数，玩家只有今日游戏次数）
                int total_games = 0;   //今日游戏次数 （玩家只有今日游戏次数，代理有可能是0，games表）

                //查询开房次数 （只有代理有开房次数，玩家只有今日游戏次数  games表）
                List<GamesEntity> gamesEntities = gamesDao.totalGamesToday(start_time, end_time, user_id);
                if (gamesEntities.size() != 0) {
                    for (GamesEntity gamesEntity : gamesEntities) {
                        int lives_id = gamesEntity.getLives_id();

                        //根据lives_id 查询lives表;club_id 不为0 代表今日开房次数（俱乐部开房，不是私房）
                        LivesEntity lives = livesDao.findById(lives_id);

                        if (lives != null) {
                            int club_id = lives.getClub_id();
                            if (club_id != 0) {
                                total_club_games++;
                            } else {
                                total_games++;
                            }
                        }
                    }
                }


                //今日常玩游戏
                List<GamesEntity> oftenPlayGames = gamesDao.queryOftenPlayGames(start_time, end_time, user_id);

                int game_kind = 0;    //游戏类型， 2. 上游  3. 激K  4.麻将
                int times = 0;  //游戏次数

                for (GamesEntity oftenPlayGame : oftenPlayGames) {
                    if (oftenPlayGame.getTimes() > times) {
                        times = oftenPlayGame.getTimes();
                        game_kind = oftenPlayGame.getGamekinds_id();
                    }
                }


                //查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();


                //查询当日付费金额
                WechatRechargeEntity wechatRechargeEntity = wechatRechargeDao.queryTotalRechargeMoneyByDate(start_time, end_time, user_id);
                int recharge_money = 0;
                if (wechatRechargeEntity != null) recharge_money = wechatRechargeEntity.getRecharge_money();


                //查询当前钻石
                UserBanksEntity userBanksEntity = userBanksDao.queryTotalDiamond(user_id);
                int diamond = 0;
                if (userBanksEntity != null) diamond = userBanksEntity.getDiamond();


                //查询当前积分和身份
                GameUserEntity queryUserInfo = gameUserDao.findUserStatus(user_id);

                int integral = 0;
                int user_status = 3;    //2：代理、3：玩家

                if (queryUserInfo != null) {
                    user_status = queryUserInfo.getUser_status();
                    integral = queryUserInfo.getIntegral();
                }


                realtimeOnlineHistoriesEntity.setTotal_club_games(total_club_games);
                realtimeOnlineHistoriesEntity.setTotal_games(total_games);
                realtimeOnlineHistoriesEntity.setOften_play_games(game_kind);
                realtimeOnlineHistoriesEntity.setNickname(nickname);
                realtimeOnlineHistoriesEntity.setRecharge_money(recharge_money * 1.0 / 100);
                realtimeOnlineHistoriesEntity.setDiamond(diamond);
                realtimeOnlineHistoriesEntity.setIntegral(integral * 1.0 / 100);
                realtimeOnlineHistoriesEntity.setUser_status(user_status);
                realtimeOnlineHistoriesEntity.setCreated_at(date);
            }
        }


        //处理 总页数
        int total = disposeTotal(sum, limit);

        if (realtimeOnlineHistoriesEntities.size() == 0) {
            mv.addObject("error", "暂无数据");
        } else {
            mv.addObject("error", "");
        }

        mv.addObject("realtimeOnlineHistoriesEntities", realtimeOnlineHistoriesEntities);
        mv.addObject("date", date);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.setViewName("active_details");
        return mv;
    }


    //流失率数据详情
    @RequestMapping("flow_away_details")
    public ModelAndView flowAwayDetails(Integer page, Integer limit, String chooseDate) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;
        int offset = (page - 1) * limit;

        String date = CommonUtil.creationDate("yyyy-MM-dd");
        if (!chooseDate.equals("")) date = chooseDate;

        //测试日期
        //date = "2018-02-01";

        logger.info("查询流失率数据详情的日期为：{}", date);


        //查询总条数
        int sum = flowAwayDailyDao.queryTotal(date);


        List<FlowAwayDailyEntity> flowAwayDailyEntities = flowAwayDailyDao.findByDate(date, offset, limit);
        if (flowAwayDailyEntities.size() != 0) {
            for (FlowAwayDailyEntity flowAwayDailyEntity : flowAwayDailyEntities) {

                int user_id = flowAwayDailyEntity.getUser_id();

                //查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(user_id);

                String nickname = "";
                if (nickName != null) {
                    nickname = nickName.getNickname();
                }

                //处理积分
                int integral = flowAwayDailyEntity.getIntegral();
                double total_integral = integral * 1.0 / 100;


                flowAwayDailyEntity.setNickname(nickname);
                flowAwayDailyEntity.setTotal_integral(total_integral);
            }
        }


        //处理 总页数
        int total = disposeTotal(sum, limit);

        if (flowAwayDailyEntities.size() == 0) {
            mv.addObject("error", "暂无数据");
        } else {
            mv.addObject("error", "");
        }

        mv.addObject("flowAwayDailyEntities", flowAwayDailyEntities);
        mv.addObject("date", date);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.setViewName("flow_away_details");
        return mv;
    }


    //新增统计图
    @RequestMapping("add_statistics")
    public ModelAndView addStatistics() {
        return new ModelAndView("add_statistics");
    }

    /**
     * 折线图数据展示
     *
     * @param resp
     * @throws IOException
     */
    @RequestMapping("add_statistics_line")
    @ResponseBody
    public void addStatisticsLine(String start_date, String end_date, HttpServletResponse resp, HttpSession session) throws IOException, ParseException {
        logger.info("进入（/add_statistics_line）");

        //时间转化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = dateFormat.format(new Date());

        if (start_date.equals("")) {
            Date start = dateFormat.parse(now);
            Date date = new Date(start.getTime() - 6 * 24 * 60 * 60 * 1000);
            start_date = dateFormat.format(date);
        }
        if (end_date.equals("")) end_date = now;


        logger.info("开始时间：{}", start_date);
        logger.info("结束时间：{}", end_date);


        Date startDate = dateFormat.parse(start_date);
        Date endDate = dateFormat.parse(end_date);

        String json = "";
        String data = "";

        for (long i = startDate.getTime(); i <= endDate.getTime(); i += 24 * 60 * 60 * 1000) {
            long start = i * 1L;
            Date date = new Date(start);
            String formatDate = dateFormat.format(date);
            logger.info("遍历选择的时间段：{}", formatDate);

            //根据时间查询用户新增数量
            List<GameUserEntity> newUsers = gameUserDao.findAddUserByDate(formatDate + " 00:00:00", formatDate + " 23:59:59");

            long time = date.getTime() + 24 * 60 * 60 * 1000;
            data = data + "[" + time + "," + newUsers.size() + "],";
        }

        if (!data.equals("")) {
            data = data.substring(0, data.length() - 1);   //去掉最后一个逗号
        }

        data = "[" + data + "]";

        json = json + "{\"name\":\"" + start_date + "~" + end_date + "\",\"data\":" + data + "}";


        PrintWriter out = resp.getWriter();
        String jsons = "[" + json + "]";

        out.println(jsons);
        out.flush();
        out.close();
    }


    //活跃统计图
    @RequestMapping("active_statistics")
    public ModelAndView activeStatistics() {
        return new ModelAndView("active_statistics");
    }

    /**
     * 折线图数据展示
     *
     * @param resp
     * @throws IOException
     */
    @RequestMapping("active_statistics_line")
    @ResponseBody
    public void activeStatisticsLine(String start_date, String end_date, HttpServletResponse resp, HttpSession session) throws IOException, ParseException {
        logger.info("进入（/active_statistics_line）");

        //时间转化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = dateFormat.format(new Date());

        if (start_date.equals("")) {
            Date start = dateFormat.parse(now);
            Date date = new Date(start.getTime() - 6 * 24 * 60 * 60 * 1000);
            start_date = dateFormat.format(date);
        }
        if (end_date.equals("")) end_date = now;


        logger.info("开始时间：{}", start_date);
        logger.info("结束时间：{}", end_date);


        Date startDate = dateFormat.parse(start_date);
        Date endDate = dateFormat.parse(end_date);

        String json = "";
        String data = "";

        for (long i = startDate.getTime(); i <= endDate.getTime(); i += 24 * 60 * 60 * 1000) {
            long start = i * 1L;
            Date date = new Date(start);
            String formatDate = dateFormat.format(date);
            logger.info("遍历选择的时间段：{}", formatDate);

            //根据时间查询活跃用户数量
            List<RealtimeOnlineHistoriesEntity> onlineUser = realtimeOnlineHistoriesDao.queryRealtimeUser(formatDate + " 00:00:00", formatDate + " 23:59:59");

            long time = date.getTime() + 24 * 60 * 60 * 1000;
            data = data + "[" + time + "," + onlineUser.size() + "],";
        }

        if (!data.equals("")) {
            data = data.substring(0, data.length() - 1);   //去掉最后一个逗号
        }

        data = "[" + data + "]";

        json = json + "{\"name\":\"" + start_date + "~" + end_date + "\",\"data\":" + data + "}";


        PrintWriter out = resp.getWriter();
        String jsons = "[" + json + "]";

        out.println(jsons);
        out.flush();
        out.close();
    }


    //问题反馈页面
    @RequestMapping("problem_feedback")
    public ModelAndView problemFeedback(Integer page, Integer limit) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;
        int offset = (page - 1) * limit;

        feedbackInfoDao.read(FeedbackInfoEntity.ProblemFeedbackType);

        int sum = feedbackInfoDao.queryTotalCountByType(FeedbackInfoEntity.ProblemFeedbackType);

        List<FeedbackInfoEntity> feedbackInfoEntities = feedbackInfoDao.findPaging(FeedbackInfoEntity.ProblemFeedbackType, offset, limit);

        if (feedbackInfoEntities.size() != 0) {
            for (FeedbackInfoEntity feedbackInfoEntity : feedbackInfoEntities) {
                int user_id = feedbackInfoEntity.getUser_id();

                //查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();
                feedbackInfoEntity.setNickname(nickname);


                //时间处理
                String created_time = feedbackInfoEntity.getCreated_time();
                created_time = created_time.substring(0, created_time.length() - 2);
                feedbackInfoEntity.setCreated_time(created_time);
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("feedbackInfoEntities", feedbackInfoEntities);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.addObject("count", sum);
        mv.setViewName("problem_feedback");
        return mv;
    }


    //代理申请页面
    @RequestMapping("apply_agent")
    public ModelAndView applyAgent(Integer page, Integer limit) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;
        int offset = (page - 1) * limit;

        feedbackInfoDao.read(FeedbackInfoEntity.ApplyAgentType);

        int sum = feedbackInfoDao.queryTotalCountByType(FeedbackInfoEntity.ApplyAgentType);

        List<FeedbackInfoEntity> feedbackInfoEntities = feedbackInfoDao.findPaging(FeedbackInfoEntity.ApplyAgentType, offset, limit);

        if (feedbackInfoEntities.size() != 0) {
            for (FeedbackInfoEntity feedbackInfoEntity : feedbackInfoEntities) {
                int user_id = feedbackInfoEntity.getUser_id();

                //查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();
                feedbackInfoEntity.setNickname(nickname);


                //时间处理
                String created_time = feedbackInfoEntity.getCreated_time();
                created_time = created_time.substring(0, created_time.length() - 2);
                feedbackInfoEntity.setCreated_time(created_time);
            }
        }

        //处理 总页数
        int total = disposeTotal(sum, limit);

        mv.addObject("feedbackInfoEntities", feedbackInfoEntities);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.addObject("count", sum);
        mv.setViewName("apply_agent");
        return mv;
    }


    /**
     * 代理特权活动统计
     *
     * @return
     */
    /*@RequestMapping("privileged_statistics")
    public ModelAndView privilegedStatistics(Integer page, Integer limit, Integer userId) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;
        int offset = (page - 1) * limit;

        //条件查询
        String condition = "";
        if (userId != null) condition = " and user_id = " + userId;


        List agentPrivilegeResp = new ArrayList();


        //查询打卡获得积分的记录总条数
        int sum = integralRebateDao.queryTotalCount(IntegralRebateEntity.SourceAgentPrivilege, condition);


        //获取打卡获得积分的代理
        List<IntegralRebateEntity> integralRebateEntities = integralRebateDao.queryByIntegralSource(IntegralRebateEntity.SourceAgentPrivilege, offset, limit, condition);
        if (integralRebateEntities.size() != 0) {
            for (IntegralRebateEntity integralRebateEntity : integralRebateEntities) {

                //打卡获得者的ID
                int user_id = integralRebateEntity.getUser_id();

                //已获得活动积分
                int rebate_number = integralRebateEntity.getRebate_number();

                //获取昵称
                GameUserEntity nickName = gameUserDao.findNickName(user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();

                //查询绑定时间（user_privilege表）
                String bindingTime = "无绑定时间";
                UserPrivilegeEntity userPrivilegeEntity = userPrivilegeDao.queryBindingTime(user_id);
                if (userPrivilegeEntity != null) {
                    bindingTime = userPrivilegeEntity.getCreated_at();
                    bindingTime = bindingTime.substring(0, bindingTime.length() - 2);
                }


                //查询激活码 （user_privilege表）
                String code = "无激活码";
                PrivilegeCodeEntity privilegeCodeByUserId = privilegeCodeDao.findPrivilegeCodeByUserId(user_id);
                if (privilegeCodeByUserId != null) {

                    //激活码（特权码）
                    code = privilegeCodeByUserId.getCode();
                }


                int day = 0;
                //查询当前天数
                GameConfigsEntity gameConfigsEntity = gameConfigsDao.queryByGameConfigs(101);
                if (gameConfigsEntity != null) {

                    String config = gameConfigsEntity.getConfig();
                    JSONArray agentPrivilegeConfigs = JSONObject.parseArray(config);

                    if (agentPrivilegeConfigs.size() != 0) {
                        for (Object agentPrivilegeConfig : agentPrivilegeConfigs) {

                            JSONObject json = JSONObject.parseObject(agentPrivilegeConfig + "");
                            if (json != null) {
                                int d = json.getIntValue("day");       //天数
                                int reward = json.getIntValue("reward");    //奖励积分值

                                reward = reward * 100;
                                //获得的打卡积分和 任务奖励积分比对，相等则表示相对应的第几天
                                if (rebate_number == reward) {
                                    day = d;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    logger.info("无法获取 （game_kind 类型：101） 的配置");
                }

                //查询上级代理
                GameUserEntity userStatus = gameUserDao.findFatherProxyId(user_id);
                String superior_agent_nickname = "无上级代理";
                int superior_agent_id = 0;
                if (userStatus != null) {
                    superior_agent_id = userStatus.getFatherproxy_id();
                    if (superior_agent_id == 1) {
                        superior_agent_nickname = "系统代理";
                    } else {
                        //查询上级代理昵称
                        GameUserEntity superiorAgent = gameUserDao.findNickname(superior_agent_id);
                        if (superiorAgent != null) superior_agent_nickname = superiorAgent.getNickname();
                    }
                }

                String time = CommonUtil.creationDate("yyyy-MM-dd");

                //查询当前完成总进度
                int progress = clubGamesDao.queryProgressByUserId(user_id, time);

                //查询当前进度
                UserPrivilegeEntity todayProgress = userPrivilegeDao.queryTodayProgress(user_id);
                int today_progress = 0;
                if(todayProgress != null) today_progress = todayProgress.getCurrentDay();

                *//*if (todayProgress != null) {
                    String lashFinishTime = todayProgress.getLashFinishTime();
                    lashFinishTime = lashFinishTime.substring(0, lashFinishTime.length() - 11);

                    if (lashFinishTime.equals(time)) {
                        today_progress = Integer.parseInt(todayProgress.getFinishDay()) + 1;
                        //logger.info("当前进度为今天");
                    } else {
                        today_progress = Integer.parseInt(todayProgress.getFinishDay());
                    }
                }*//*


                //参数封装
                AgentPrivilege agentPrivilege = new AgentPrivilege();
                agentPrivilege.setUserId(user_id);
                agentPrivilege.setNickname(nickname);
                agentPrivilege.setCode(code);
                agentPrivilege.setBindingTime(bindingTime);
                agentPrivilege.setDay(day);
                agentPrivilege.setIntegral(rebate_number * 1.0 / 100);
                agentPrivilege.setSuperiorAgentId(superior_agent_id);
                agentPrivilege.setSuperiorAgentNickname(superior_agent_nickname);
                agentPrivilege.setProgress(progress);
                agentPrivilege.setToday_progress(today_progress);


                agentPrivilegeResp.add(agentPrivilege);
            }
        }

        //查询激活人数
        int activationNum = userPrivilegeDao.totalCount();


        //处理 总页数
        int total = disposeTotal(sum, limit);


        mv.addObject("agentPrivilegeResp", agentPrivilegeResp);
        mv.addObject("activationNum", activationNum);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.addObject("count", sum);
        mv.addObject("userId", userId);
        mv.setViewName("privileged_statistics");
        return mv;
    }*/

    /**
     * 代理活动特权重构
     */
    @RequestMapping("privileged_statistics")
    public ModelAndView privilegedStatistics(Integer page, Integer limit, Integer userId) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;
        int offset = (page - 1) * limit;

        //条件查询
        String totalCondition = "";
        String condition = "";
        if (userId != null) {
            condition = " and a.userId = " + userId;
            totalCondition = " where userId = " + userId;
        }


        List agentPrivilegeResp = new ArrayList();


        //查询总条数
        int sum = userPrivilegeDao.queryTotalCount(totalCondition);

        List<UserPrivilegeEntity> userPrivilegeEntities = userPrivilegeDao.queryPaging(offset, limit, condition);
        if (userPrivilegeEntities.size() != 0) {
            for (UserPrivilegeEntity userPrivilegeEntity : userPrivilegeEntities) {

                //打卡用户ID
                int user_id = userPrivilegeEntity.getUserId();


                //查询激活码 （user_privilege表）
                String code = "无激活码";
                PrivilegeCodeEntity privilegeCodeByUserId = privilegeCodeDao.findPrivilegeCodeByUserId(user_id);
                if (privilegeCodeByUserId != null) code = privilegeCodeByUserId.getCode();

                //时间处理
                String bindingTime = userPrivilegeEntity.getCreated_at();
                bindingTime = bindingTime.substring(0, bindingTime.length() - 2);


                //查询已获得的积分
                IntegralRebateEntity integralRebateEntity = integralRebateDao.queryTotalByIntegralSource(IntegralRebateEntity.SourceAgentPrivilege, user_id);
                double integral = 0;
                if (integralRebateEntity != null) integral = integralRebateEntity.getRebate_number() * 1.0 / 100;


                //查询上级代理
                GameUserEntity userStatus = gameUserDao.findFatherProxyId(user_id);
                String superior_agent_nickname = "无上级代理";
                int superior_agent_id = 0;
                if (userStatus != null) {
                    superior_agent_id = userStatus.getFatherproxy_id();
                    if (superior_agent_id == 1) {
                        superior_agent_nickname = "系统代理";
                    } else {
                        //查询上级代理昵称
                        GameUserEntity superiorAgent = gameUserDao.findNickname(superior_agent_id);
                        if (superiorAgent != null) superior_agent_nickname = superiorAgent.getNickname();
                    }
                }


                //查询当天完成总进度
                String time = CommonUtil.creationDate("yyyy-MM-dd");
                int progress = clubGamesDao.queryProgressByUserId(user_id, time);


                //查询当天总任务进度
                UserPrivilegeEntity todayProgress = userPrivilegeDao.queryTodayProgress(user_id);
                int today_progress = 0;
                if (todayProgress != null) today_progress = todayProgress.getCurrentDay();


                //查询当天总任务
                int task = 0;
                GameConfigsEntity gameConfigsEntity = gameConfigsDao.queryByGameConfigs(101);
                if (gameConfigsEntity != null) {

                    String config = gameConfigsEntity.getConfig();
                    JSONArray agentPrivilegeConfigs = JSONObject.parseArray(config);

                    if (agentPrivilegeConfigs.size() != 0) {
                        for (Object agentPrivilegeConfig : agentPrivilegeConfigs) {

                            JSONObject json = JSONObject.parseObject(agentPrivilegeConfig + "");
                            if (json != null) {
                                int day = json.getIntValue("day");       //天数
                                int reward = json.getIntValue("reward");    //奖励积分值
                                int tasks = json.getIntValue("task");    //奖励积分值

                                //获得的打卡积分和 任务奖励积分比对，相等则表示相对应的第几天
                                if (today_progress == day) {
                                    task = tasks;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    logger.info("无法获取 （game_kind 类型：101） 的配置");
                }


                //参数封装
                AgentPrivilege agentPrivilege = new AgentPrivilege();
                agentPrivilege.setUserId(user_id);
                agentPrivilege.setNickname(userPrivilegeEntity.getNickname());
                agentPrivilege.setCode(code);
                agentPrivilege.setBindingTime(bindingTime);
                agentPrivilege.setTask(task);
                agentPrivilege.setIntegral(integral);
                agentPrivilege.setSuperiorAgentId(superior_agent_id);
                agentPrivilege.setSuperiorAgentNickname(superior_agent_nickname);
                agentPrivilege.setProgress(progress);
                agentPrivilege.setToday_progress(today_progress);


                agentPrivilegeResp.add(agentPrivilege);
            }
        }

        //查询激活人数
        int activationNum = userPrivilegeDao.totalCount();


        //处理 总页数
        int total = disposeTotal(sum, limit);


        mv.addObject("agentPrivilegeResp", agentPrivilegeResp);
        mv.addObject("activationNum", activationNum);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.addObject("count", sum);
        mv.addObject("userId", userId);
        mv.setViewName("privileged_statistics");
        return mv;
    }

    /**
     * 特权用户详情
     *
     * @param userId
     * @return
     */
    @RequestMapping("privileged_details")
    public ModelAndView privilegedDetails(Integer userId) {
        ModelAndView mv = new ModelAndView();

        //参数封装
        List<PrivilegedDetails> privilegedDetailsResp = new ArrayList();

        //根据 UserId 查询数据
        UserPrivilegeEntity userPrivilegeEntity = userPrivilegeDao.queryByUserId(userId);

        int user_id = userPrivilegeEntity.getUserId();
        String nickname = userPrivilegeEntity.getNickname();


        String lashFinishTime = "";
        int finishDay = 0;
        if (userPrivilegeEntity != null) {

            finishDay = Integer.parseInt(userPrivilegeEntity.getFinishDay());

            GameConfigsEntity gameConfigsEntity = gameConfigsDao.queryByGameConfigs(101);
            if (gameConfigsEntity != null) {

                String config = gameConfigsEntity.getConfig();
                JSONArray agentPrivilegeConfigs = JSONObject.parseArray(config);

                if (agentPrivilegeConfigs.size() != 0) {
                    for (Object agentPrivilegeConfig : agentPrivilegeConfigs) {

                        JSONObject json = JSONObject.parseObject(agentPrivilegeConfig + "");
                        if (json != null) {
                            int day = json.getIntValue("day");       //天数
                            int reward = json.getIntValue("reward");    //奖励积分值
                            int task = json.getIntValue("task");    //奖励积分值

                            //获得的打卡积分和 任务奖励积分比对，相等则表示相对应的第几天
                            if (finishDay >= day) {
                                PrivilegedDetails privilegedDetails = new PrivilegedDetails();

                                privilegedDetails.setDay(day);
                                privilegedDetails.setTask(task);
                                privilegedDetails.setReward(reward);
                                privilegedDetails.setUserId(user_id);
                                privilegedDetails.setNickname(nickname);

                                logger.info("day：{}", day);
                                logger.info("reward：{}", reward);
                                logger.info("task：{}", task);

                                privilegedDetailsResp.add(privilegedDetails);
                            }
                        }
                    }
                }
            } else {
                logger.info("无法获取 （game_kind 类型：101） 的配置");
            }
        } else {
            logger.info("暂无记录");
        }


        for (PrivilegedDetails details : privilegedDetailsResp) {
            logger.info("list中的数据：{}", details.toString());
        }

        mv.addObject("privilegedDetailsResp", privilegedDetailsResp);
        mv.setViewName("privileged_details");
        return mv;
    }


    /**
     * 根据时间查询合伙人下级的详情数据
     *
     * @return
     */
    @RequestMapping(value = "partner_details", produces = "application/json;charset=utf-8")
    public ModelAndView partnerDaily(String date) throws Exception {

        if ("".equals(date) || date == null) {
            SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
            date = slf.format(new Date());
        }

        //合伙人查询自己所有的下级（总代理、代理、绑定的玩家）包括合伙人自己
        //从缓存中拿数据
        List<Map> allSubAgents = (List) EhcacheUtils.getInstance().get("myCache", "allSubAgents");


        List list = new ArrayList();


        //查询所有的合伙人
        List<GameUserEntity> gameUserEntities = gameUserDao.queryByUserStatus(4);

        if (gameUserEntities.size() != 0) {
            for (GameUserEntity gameUserEntity : gameUserEntities) {

                //合伙人ID
                int user_id = gameUserEntity.getUsers_id();

                //获取 User_id 的所有下级（包含自己的ID）
                String allSubAgent = getAllSubAgent(allSubAgents, gameUserEntity);

                String[] splitSubAgent = allSubAgent.split(",");
                logger.info("子代理分割后的值：{}", splitSubAgent);


                logger.info("UserID（" + user_id + "）的所有下级用户：{}", allSubAgent);

                String startDate = date + " 00:00:00";
                String endDate = date + " 23:59:59";

                int rechargeNum = 0; //充值金额
                int activeNum = 0;  //活跃用户
                int gamesNum = 0;  //开房次数
                int rechargeDiam = 0;  //充钻数量
                int gameConsumeDiam = 0;  //游戏开房用钻


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


                //根据时间查询开房次数    俱乐部和私房 开房 分开统计（开房数=开房用钻/4）
                Map map = getUserInfoService.getGamesNum(splitSubAgent, startDate, endDate);
                gamesNum = (int) map.get("gameConsumeDiam") / 4;
                gameConsumeDiam = (int) map.get("gameConsumeDiam");


                DataDetailsResp dataDetailsResp = new DataDetailsResp();
                dataDetailsResp.setDate(date);
                dataDetailsResp.setRechargeNum(rechargeNum * 1.0 / 100);
                dataDetailsResp.setActiveNum(activeNum);
                dataDetailsResp.setGamesNum(gamesNum);
                dataDetailsResp.setUserId(user_id);
                dataDetailsResp.setNickname(gameUserEntity.getNickname());
                dataDetailsResp.setRechargeDiam(rechargeDiam);
                dataDetailsResp.setGameConsumeDiam(gameConsumeDiam);

                list.add(dataDetailsResp);
            }
        }


        ModelAndView mv = new ModelAndView();
        mv.setViewName("details");
        mv.addObject("list", list);
        mv.addObject("date", date);
        return mv;
    }

    private String getAllSubAgent(List<Map> allSubAgents, GameUserEntity gameUserEntity) {

        //返回值
        String allSubAgent = "";

        int user_id = gameUserEntity.getUsers_id();

        //从缓存中获得的数据不为空，则遍历（缓存中的数据包含自己ID）
        if (allSubAgents != null && allSubAgents.size() != 0) {

            for (int i = 0; i < allSubAgents.size(); i++) {
                Map map = (Map) allSubAgents.get(i);
                allSubAgent = (String) map.get(user_id);
                if (allSubAgent != null && !"".equals(allSubAgent)) break;
            }
            logger.info("（myCache）缓存中的数据：{}", allSubAgents.toString());
        } else {

            logger.info("（myCache）缓存中没有数据");

            //如果缓存中没有数据，则从数据库中查询
            List<GameUserEntity> allSubordinate = gameUserDao.queryPartnerSubordinate(user_id);
            allSubAgent = user_id + ",";    //包含自己

            for (GameUserEntity userEntity : allSubordinate) {
                allSubAgent = allSubAgent + userEntity.getUsers_id() + ",";
            }
            allSubAgent = allSubAgent.substring(0, allSubAgent.length() - 1);
        }

        return allSubAgent;
    }


    /**
     * 改为已联系状态
     *
     * @param id     申请代理或问题反馈的主键ID
     * @param status 1：申请代理，2：问题反馈
     * @return
     */
    @RequestMapping("contacted")
    public ModelAndView partnerDetails(Integer id, Integer status) {
        ModelAndView mv = new ModelAndView();

        feedbackInfoDao.contacted(id);

        mv = applyAgent(1, 20);
        if (status == 2) mv = problemFeedback(1, 20);
        return mv;
    }

    /**
     * 实名认证记录
     *
     * @return
     */
    @RequestMapping("authentication_record")
    public ModelAndView authenticationRecord(Integer page, Integer limit) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;
        int offset = (page - 1) * limit;

        //总条数
        int sum = authenticationDao.totalCount();

        List<AuthenticationEntity> authenticationEntities = authenticationDao.queryByPaging(offset, limit);
        if (authenticationEntities.size() != 0) {
            for (AuthenticationEntity authenticationEntity : authenticationEntities) {

                //查询昵称
                GameUserEntity nickName = gameUserDao.findNickName(authenticationEntity.getUser_id());
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();

                //时间处理
                String created_time = authenticationEntity.getCreated_time();
                created_time = created_time.substring(0, created_time.length() - 2);


                authenticationEntity.setNickname(nickname);
                authenticationEntity.setCreated_time(created_time);
            }
        }

        //处理总页数
        int total = disposeTotal(sum, limit);


        mv.addObject("total", total);
        mv.addObject("limit", limit);
        mv.addObject("count", sum);
        mv.addObject("page", page);
        mv.addObject("authenticationEntities", authenticationEntities);
        mv.setViewName("authentication_record");
        return mv;
    }


    /**
     * 合伙人下级详情
     *
     * @return
     */
    @RequestMapping("partnership")
    public ModelAndView partnership(Integer page, Integer limit, Integer userId, String chooseDate) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;
        if (userId == null) userId = 0;
        int offset = (page - 1) * limit;

        if ("".equals(chooseDate) || chooseDate == null) chooseDate = CommonUtil.creationDate("yyyy-MM-dd");

        String startDate = chooseDate + " 00:00:00";
        String endDate = chooseDate + " 23:59:59";

        //需要查询总代理昵称、房间数、钻石销量、活跃人数、代理人数、玩家人数


        //参数封装的list
        List<PartnershipResp> partnership = new ArrayList();

        int sum = gameUserDao.queryTotalCount(userId, GameUserEntity.First_Level_Agent);

        //查询当前userId的下级总代理
        List<GameUserEntity> gameUserEntities = gameUserDao.findByFatherProxyId(userId, GameUserEntity.First_Level_Agent);
        if (gameUserEntities.size() != 0) {
            for (GameUserEntity gameUserEntity : gameUserEntities) {

                //总代理用户id
                int user_id = gameUserEntity.getUsers_id();

                //查昵称
                GameUserEntity nickName = gameUserDao.findNickName(user_id);
                String nickname = "";
                if (nickName != null) nickname = nickName.getNickname();


                //总代理查询自己的所有子代理ID（包括子代理的子代理），包括绑定玩家（不包括自己在内）
                List<GameUserEntity> allSubordinate = gameUserDao.queryAllSubordinate(user_id);

                //输出 User_id 的下级
                String allSubAgent = user_id + ",";
                for (GameUserEntity userEntity : allSubordinate) {
                    allSubAgent = allSubAgent + userEntity.getUsers_id() + ",";
                }
                allSubAgent = allSubAgent.substring(0, allSubAgent.length() - 1);
                logger.info("总代理 UserID（" + user_id + "）的所有下级用户：{}", allSubAgent);
                String[] splitSubAgent = allSubAgent.split(",");


                int activeNum = 0;  //活跃用户
                int gamesNum = 0;  //开房次数
                int rechargeDiam = 0;  //充钻数量
                int gameConsumeDiam = 0;  //游戏开房用钻


                /**
                 * 根据时间查询充值金额（包含自己的充值）
                 */
                WechatRechargeEntity wechatRechargeEntity = wechatRechargeDao.queryMySubRecharge(startDate, endDate, allSubAgent);
                if (wechatRechargeEntity != null) rechargeDiam = wechatRechargeEntity.getRecharge_number();


                /**
                 * 根据时间查询活跃人数
                 */
                activeNum = realtimeOnlineHistoriesDao.queryMyActiveUserCount(startDate, endDate, allSubAgent);


                //根据时间查询开房次数    俱乐部和私房 开房 分开统计
                Map map = getUserInfoService.getGamesNum(splitSubAgent, startDate, endDate);
                gamesNum = (int) map.get("gameConsumeDiam") / 4;
                gameConsumeDiam = (int) map.get("gameConsumeDiam");


                //查代理人数
                int agentNum = gameUserDao.findByFatherProxyId(user_id, GameUserEntity.Two_Level_Agent).size();

                //总代理查询自己所有绑定的玩家（包括下级的下级绑定的玩家）
                int playerNum = gameUserDao.queryAllBindingPlayer(user_id).size();


                PartnershipResp partnershipResp = new PartnershipResp();
                partnershipResp.setUserId(user_id);
                partnershipResp.setNickname(nickname);
                partnershipResp.setActiveNum(activeNum);
                partnershipResp.setGameNum(gamesNum);
                partnershipResp.setRechargeDiam(rechargeDiam);
                partnershipResp.setAgentNum(agentNum);
                partnershipResp.setPlayerNum(playerNum);
                partnershipResp.setGameConsumeDiam(gameConsumeDiam);

                partnership.add(partnershipResp);
            }
        }


        int total = disposeTotal(sum, limit);


        if (userId != 0) mv.addObject("userId", userId);
        mv.addObject("count", sum);
        mv.addObject("page", page);
        mv.addObject("limit", limit);
        mv.addObject("total", total);
        mv.addObject("chooseDate", chooseDate);
        mv.addObject("partnership", partnership);
        mv.setViewName("partnership");
        return mv;
    }


    //用Datatables重构《代理总览》
    @RequestMapping("proxy_overview")
    public ModelAndView proxyOverview() {
        return new ModelAndView("proxy_overview");
    }

    //代理总览数据获取
    @RequestMapping(value = "getProxyOverview", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getProxyOverview(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取需要搜索的userId
        String user_id = request.getParameter("user_id");

        //直接返回前台
        String draw = request.getParameter("draw");
        //数据起始位置
        String startIndex = request.getParameter("startIndex");
        //每页显示的条数
        String pageSize = request.getParameter("pageSize");
        //获取排序字段
        String orderColumn = request.getParameter("orderColumn");
        if (orderColumn == null) {
            orderColumn = "users_id";
        }
        //获取排序方式
        String orderDir = request.getParameter("orderDir");
        if (orderDir == null) orderDir = "desc";

        //排序方式
        String orders = " order by " + orderColumn + " " + orderDir + " ";  //orderColumn
        Map map = new HashMap();
        map.put("order", orders);    //排序字段  startIndex 从0开始
        int offset = Integer.parseInt(startIndex) * Integer.parseInt(pageSize);
        map.put("offset", offset);
        map.put("limit", Integer.parseInt(pageSize));
        map.put("user_id", user_id);


        //将所有代理查询出来 总条数
        int sum = gameUserDao.queryProxyTotalCount(map);

        //将所有代理查询出来
        List<GameUserEntity> proxyLists = gameUserDao.queryProxyListPaging(map);
        if (proxyLists.size() != 0) {
            for (GameUserEntity proxyList : proxyLists) {

                int userId = proxyList.getUsers_id();
                int proxy_id = proxyList.getFatherproxy_id();   //上级代理ID

                //根据user_id查询钻石数量
                GameUserEntity diamondData = gameUserDao.findByDiamondAndMoney(userId);
                if (diamondData != null) {
                    proxyList.setDiamond(diamondData.getDiamond());
                } else {
                    proxyList.setDiamond(0);
                }

                //根据 user_id 查询 昵称
                GameUserEntity nickName = gameUserDao.findNickName(userId);
                if (nickName != null) {
                    proxyList.setNickname(nickName.getNickname());
                } else {
                    proxyList.setNickname("");
                }


                //查询上级代理昵称
                String desc = "（ID：" + proxy_id + "）";
                if (proxy_id != 1) {
                    //根据 proxy_id 查询 昵称
                    GameUserEntity qureyNickName = gameUserDao.findNickName(proxy_id);
                    String nickname = "";
                    if (qureyNickName != null) nickname = qureyNickName.getNickname();
                    desc = nickname + desc;
                } else {
                    desc = "系统代理";
                }
                proxyList.setDesc(desc);


                int user_status = proxyList.getUser_status();
                String userStatus = "玩家";
                if (user_status == 4) {
                    userStatus = "合伙人";
                } else if (user_status == 1) {
                    userStatus = "总代理";
                } else if (user_status == 2) {
                    userStatus = "代理";
                }
                proxyList.setUserStatus(userStatus);

                //处理总积分：除以100 单位变为元
                int integral = proxyList.getIntegral();
                proxyList.setTotal_integral(integral * 1.0 / 100);
            }
        }


        Map<Object, Object> info = new HashMap<Object, Object>();
        info.put("pageData", proxyLists);
        info.put("total", sum);
        info.put("draw", draw);
        return JSONObject.toJSONString(info) + "";
    }


    /**
     * 进入分页模板
     *
     * @return
     */
    @RequestMapping("textw")
    public ModelAndView text(Integer page, Integer limit) {
        ModelAndView mv = new ModelAndView();

        if (limit == null) limit = 20;
        if (page == null) page = 1;
        int offset = (page - 1) * limit;

        mv.setViewName("customer-service/test_we_ui");
        return mv;
    }


}

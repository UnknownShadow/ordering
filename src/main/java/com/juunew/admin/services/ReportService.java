package com.juunew.admin.services;


import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.utils.CommonUtil;
import com.juunew.admin.utils.EhcacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    GameUserDao gameUserDao;

    @Autowired
    RealtimeOnlineHistoriesDao realtimeOnlineHistoriesDao;

    @Autowired
    OperationDailyDao operationDailyDao;

    @Autowired
    DiamondsDao diamondsDao;

    @Autowired
    Financial_DailyDao financial_dailyDao;

    @Autowired
    ClubsDao clubsDao;

    @Autowired
    LivesDao livesDao;

    @Autowired
    GamesDao gamesDao;

    @Autowired
    SecondRechargeDailyDao secondRechargeDailyDao;

    @Autowired
    ClubsActionDao clubsActionDao;

    @Autowired
    FlowAwayDailyDao flowAwayDailyDao;

    @Autowired
    WechatRechargeDao wechatRechargeDao;

    @Autowired
    UserBanksDao userBanksDao;

    @Autowired
    UserSpreadDao userSpreadDao;


    /**
     * 处理 游戏开房次数统计 解散房间的ID条件（不算在开房次数内）<<<已废除>>>
     */
    public String getRoomCondition(String start_date, String end_date) {

        String condition = "0";

        List<DiamondsEntity> purposeByDate = diamondsDao.findPurposeByDate(start_date, end_date);

        if (purposeByDate.size() != 0) {
            for (DiamondsEntity purpose : purposeByDate) {
                String purposeValue = purpose.getPurpose();
                //得到purpose值，截取：后面的字符串
                String value = purposeValue.substring(purposeValue.indexOf(" ") + 1, purposeValue.length());
                condition = condition + value + ",";
            }
        }

        if (!condition.equals("0")) {
            //将最后一个 逗号去掉
            condition = condition.substring(0, condition.length() - 1);
        }
        return condition;
    }


    /**
     * 日报统计
     *
     * @param date 需要统计的天, 格式为  2011-01-11
     */
    public void operationDaily(String date) throws ParseException {

        //获取当前时间，录入，
        String sysDate = CommonUtil.creationDate("yyyy-MM-dd HH:mm:ss");

        //获取当前时间，录入，
        String start_date = date + " 00:00:00";
        String end_date = date + " 23:59:58";

        logger.info("统计运营日报时间：" + sysDate);

        long begin = System.currentTimeMillis();    //用于统计所耗时间


        //查询当日新增人数
        int newly_added_num = gameUserDao.countNewUsers(start_date, end_date);

        //查询苹果日新增人数
        int ios_new_size = gameUserDao.countNewUsersForIos(start_date, end_date);

        //安卓日新增人数
        int android_new_size = gameUserDao.countNewUsersForAndroid(start_date, end_date);


        //苹果日活沃
        int ios_activity_size = realtimeOnlineHistoriesDao.countActivityByIos(start_date, end_date);

        //安卓日活沃
        int android_activity_size = realtimeOnlineHistoriesDao.countActivityByAndroid(start_date, end_date);

        //查询当日活跃人数     //ios_activity_size + android_activity_size;
        int activity_num = realtimeOnlineHistoriesDao.countActivity(start_date, end_date);
        logger.info("当日活跃人数：{}", activity_num);


        //查询当日付费人数
        List<DiamondsEntity> payNum = diamondsDao.findPayNumByDate(start_date, end_date);
        int pay_num = payNum.size();
        logger.info("当日付费人数：{}", pay_num);


        //查询当日付费笔数
        List<DiamondsEntity> payCount = diamondsDao.findPayCountByDate(start_date, end_date);
        int pay_count = payCount.size();
        logger.info("当日付费笔数：{}", pay_count);


        //查询当日付费金额 （过滤了测试user_id 33和781）
        DiamondsEntity payTotal = diamondsDao.findPayTotalByDate(start_date, end_date);
        int pay_total = 0;
        if (payTotal != null) pay_total = payTotal.getPrice();
        logger.info("当日付费金额：{}", pay_total);


        //得到解散房间的ID
        //String condition = getRoomCondition(start_date, end_date);

        //统计鱼虾蟹开房次数；
        int fish_count = 0;//financial_dailyDao.countByCreateGameForYxx(start_date, end_date);
        logger.info("- - - 鱼虾蟹开房次数：{}", fish_count);

        //统计上游十三张开房次数；
        int thirteen_count = 0;
        GamesEntity thirteenCount = financial_dailyDao.countByCreateGameThirteen(start_date, end_date);
        if (thirteenCount != null) thirteen_count = thirteenCount.getSign_cost() / 4;
        logger.info("- - - 上游十三张开房次数：{}", thirteen_count);

        //统计激k开房次数；
        int fivek_count = 0;
        GamesEntity fivekCount = financial_dailyDao.countByCreateGame(3, start_date, end_date);
        if (fivekCount != null) fivek_count = fivekCount.getSign_cost() / 4;
        logger.info("- - - 激k开房次数：{}", fivek_count);


        //查询连续 三天 活跃用户 数量
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        Date processDate = dateFormat.parse(date);

        String twoDaysAgo = dateFormat.format(new Date(processDate.getTime() - 24 * 60 * 60 * 1000));
        String threeDaysAgo = dateFormat.format(new Date(processDate.getTime() - 2 * 24 * 60 * 60 * 1000));
        String fourDaysAgo = dateFormat.format(new Date(processDate.getTime() - 3 * 24 * 60 * 60 * 1000));
        String fiveDaysAgo = dateFormat.format(new Date(processDate.getTime() - 4 * 24 * 60 * 60 * 1000));

        String twoDaysAgo_start = twoDaysAgo + " 00:00:00";
        String twoDaysAgo_end = twoDaysAgo + " 23:59:58";

        String threeDaysAgo_start = threeDaysAgo + " 00:00:00";
        String threeDaysAgo_end = threeDaysAgo + " 23:59:58";

        logger.info("第一天日期：" + date);
        logger.info("第二天日期：" + twoDaysAgo);
        logger.info("第三天日期：" + threeDaysAgo);

        int three_days = realtimeOnlineHistoriesDao.findThreeDaysByDate(start_date, end_date, twoDaysAgo_start,
                twoDaysAgo_end, threeDaysAgo_start, threeDaysAgo_end);
        logger.debug("三天连续活沃： {}", three_days);

        //查询 统计连续 五天 活跃用户数量
        String fourDaysAgo_start = fourDaysAgo + " 00:00:00";
        String fourDaysAgo_end = fourDaysAgo + " 23:59:58";

        String fiveDaysAgo_start = fiveDaysAgo + " 00:00:00";
        String fiveDaysAgo_end = fiveDaysAgo + " 23:59:58";

        int five_days = realtimeOnlineHistoriesDao.findFiveDaysByDate(start_date, end_date, twoDaysAgo_start,
                twoDaysAgo_end, threeDaysAgo_start, threeDaysAgo_end, fourDaysAgo_start, fourDaysAgo_end,
                fiveDaysAgo_start, fiveDaysAgo_end);


        //统计游俱乐部开房总数：   先根据游戏类型查俱乐部；根据ClubID查询当日局数（live）表
        // 1.鱼虾蟹  2. 上游  3. 激K  4.麻将
        int thirteen_club_count = getClubCount(start_date, end_date, 2);     //上游俱乐部开房总数
        logger.info("上游俱乐部开房总数：{}", thirteen_club_count);

        int fivek_club_count = getClubCount(start_date, end_date, 3);     //激K俱乐部开房总数
        logger.info("激K俱乐部开房总数：{}", fivek_club_count);

        int mahjong_club_count = getClubCount(start_date, end_date, 4);     //麻将俱乐部开房总数
        logger.info("麻将俱乐部开房总数：{}", mahjong_club_count);


        //麻将开房次数
        int mahjong_count = 0;
        GamesEntity mahjongCount = financial_dailyDao.countMahjong(4, start_date, end_date);
        if (mahjongCount != null) mahjong_count = mahjongCount.getSign_cost() / 4;
        logger.info("麻将开房次数：{}", mahjong_count);


        logger.info("当日新增人数：{}", newly_added_num);
        logger.info("当日新增人数苹果：{}", ios_new_size);
        logger.info("当日新增人数安卓：{}", android_new_size);
        logger.info("当日活跃人数苹果：{}", ios_activity_size);
        logger.info("当日活跃人数安卓：{}", android_activity_size);
        logger.info("连续三天活跃用户数量：{}", three_days);
        logger.info("连续五天活跃用户数量：{}", five_days);


        //将查询出的数据存入数据表中
        operationDailyDao.deleteByDate(date);
        operationDailyDao.insertToOperationDaily(date, newly_added_num, activity_num, pay_num, pay_count, pay_total,
                three_days, five_days, thirteen_count, fivek_count, fish_count,
                ios_new_size, android_new_size, ios_activity_size, android_activity_size,
                thirteen_club_count, fivek_club_count, mahjong_club_count, mahjong_count);


        //日志输出：
        long end = System.currentTimeMillis();
        logger.info("运营日报统计结束，共耗时：[" + (end - begin) / 1000 + "]秒");
    }

    /**
     * 根据游戏类型查询开房总数
     *
     * @param start_date
     * @param end_date
     * @param gameKind   游戏类型
     * @return
     */
    private int getClubCount(String start_date, String end_date, int gameKind) {
        int club_count = 0;
        List<ClubsEntity> queryClubs = clubsDao.findByGameKind(gameKind);

        for (ClubsEntity club : queryClubs) {

            int id = club.getId();

            int todayGameTimes = 0;

            List<LivesEntity> livesIds = livesDao.findId(id, start_date, end_date);
            for (LivesEntity livesId : livesIds) {
                //根据 lives_id 查询 games表，将开房没打解散的排除  finished=1
                GamesEntity gamesEntity = gamesDao.findByLivesId(livesId.getId());

                //将消耗的钻石数量累加（开房次数 = 消耗钻石数 / 4）
                if (gamesEntity != null) todayGameTimes += gamesEntity.getSign_cost();
            }
            club_count = club_count + todayGameTimes;
        }
        club_count = club_count / 4;
        return club_count;
    }


    /**
     * 流失用户日报统计
     *
     * @param date 需要统计的天, 格式为  2011-01-11
     */
    public void flowAwayDaily(String date) throws ParseException {

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date now_date = df.parse(date);

        //得到七天前的时间  将时间戳转为 String 类型
        String sevenDaysAgo = df.format(new Date(now_date.getTime() - 6 * 24 * 60 * 60 * 1000));


        //获取当前时间，录入，
        String start_time = sevenDaysAgo + " 00:00:00";
        String end_time = date + " 23:59:58";

        logger.info("现在日期：{}", start_time);
        logger.info("七天前日期：{}", end_time);


        long begin = System.currentTimeMillis();    //用于统计所耗时间


        //查询出七天内在线的用户
        List<RealtimeOnlineHistoriesEntity> realtimeOnlineHistoriesEntities = realtimeOnlineHistoriesDao.queryRealtimeUser(start_time, end_time);
        String id = "1,";
        if (realtimeOnlineHistoriesEntities.size() != 0) {
            for (RealtimeOnlineHistoriesEntity realtimeOnlineHistoriesEntity : realtimeOnlineHistoriesEntities) {
                int user_id = realtimeOnlineHistoriesEntity.getUser_id();
                id = id + user_id + ",";
            }
        }

        //查询流失的用户ID
        List<FlowAwayDailyEntity> flowAwayDailyEntities = flowAwayDailyDao.findUserId();
        if (flowAwayDailyEntities.size() != 0) {
            for (FlowAwayDailyEntity flowAwayDailyEntity : flowAwayDailyEntities) {
                int user_id = flowAwayDailyEntity.getUser_id();
                id = id + user_id + ",";
            }
        }

        if (!id.equals("")) id = id.substring(0, id.length() - 1);

        logger.info("需要去除的UserId为：{}", id);


        /*//总条数
        int sum = gameUserDao.queryFlowAwayTotal(id);

        int limit = 50; //显示200条数据

        //处理 总页数
        int total = disposeTotal(sum, limit);

        logger.info("总页数：{}",total);

        for (int i = 1; i <= total; i++) {

            //处理分页值
            int offset = (i - 1) * limit;
        }*/
        String sql = "";

        List<GameUserEntity> gameUserEntities = gameUserDao.queryFlowAwayByDate(id);

        for (GameUserEntity gameUserEntity : gameUserEntities) {
            int user_id = gameUserEntity.getId();

            //查询当前积分和身份
            GameUserEntity queryUserInfo = gameUserDao.findUserStatus(user_id);

            int integral = 0;
            int user_status = 3;    //2：代理、3：玩家

            if (queryUserInfo != null) {
                user_status = queryUserInfo.getUser_status();
                integral = queryUserInfo.getIntegral();
            }


            //查询总付费金额
            WechatRechargeEntity wechatRechargeEntity = wechatRechargeDao.totalRechargeMoneyByUserId(user_id);
            int recharge_money = 0;
            if (wechatRechargeEntity != null) recharge_money = wechatRechargeEntity.getRecharge_money();


            //查询当前钻石
            UserBanksEntity userBanksEntity = userBanksDao.queryTotalDiamond(user_id);
            int diamond = 0;
            if (userBanksEntity != null) diamond = userBanksEntity.getDiamond();


            //根据ID查询 用户来源（是否是推广而来的用户）
            UserSpreadEntity userSource = userSpreadDao.findByUserId(user_id);
            int user_source = 1;    //1：默认，2：被推广用户
            if (userSource != null) user_source = 2;

            //入库
            sql = sql + "(" + user_id + ", " + user_source + ", " + recharge_money + ", " + diamond + ", " + integral + ", " + user_status + ",now(),\"" + date + "\"),";
            //flowAwayDailyDao.insertToFlowAwayDaily(user_id, user_source, recharge_money, diamond, integral, user_status, date);
        }
        if (!sql.equals("")) {
            sql = sql.substring(0, sql.length() - 1);
            flowAwayDailyDao.insertTo(sql);
        }


        //日志输出：
        long end = System.currentTimeMillis();
        logger.info("流失用户统计结束，共耗时：[" + (end - begin) / 1000 + "]秒");
    }


    /**
     * 查询合伙人所有子代理
     */
    public void partnerData() {
        logger.info("查询合伙人所有子代理");

        long begin = System.currentTimeMillis();    //用于统计所耗时间

        List<Map> allSubAgents = new ArrayList();

        //查询所有的合伙人
        List<GameUserEntity> gameUserEntities = gameUserDao.queryByUserStatus(4);
        if (gameUserEntities.size() != 0) {
            for (GameUserEntity gameUserEntity : gameUserEntities) {
                int user_id = gameUserEntity.getUsers_id();

                //输出 User_id 的下级 （包含自己）
                String allSubAgent = user_id + ",";

                //合伙人查询自己所有的下级（总代理、代理、绑定的玩家）不包括合伙人自己
                List<GameUserEntity> allSubordinate = gameUserDao.queryPartnerSubordinate(user_id);
                for (GameUserEntity userEntity : allSubordinate) {
                    int users_id = userEntity.getUsers_id();
                    allSubAgent = allSubAgent + users_id + ",";
                }
                allSubAgent = allSubAgent.substring(0, allSubAgent.length() - 1);

                Map map = new HashMap();
                map.put(user_id, allSubAgent);
                allSubAgents.add(map);
            }
        }

        EhcacheUtils.getInstance().put("myCache", "allSubAgents", allSubAgents);

        //日志输出：
        long end = System.currentTimeMillis();
        logger.info("查询合伙人所有子代理，共耗时：[" + (end - begin) + "]毫秒");
    }

}

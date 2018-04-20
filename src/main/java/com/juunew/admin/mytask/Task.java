package com.juunew.admin.mytask;

import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.services.ReportService;
import com.juunew.admin.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by juunew on 2017/8/17.
 */
@Component
public class Task {
    @Autowired
    DiamondsDao diamondsDao;
    @Autowired
    Financial_DailyDao financial_dailyDao;
    @Autowired
    IncomeDao incomeDao;
    @Autowired
    Diamond_consumeDao diamondConsumeDao;
    @Autowired
    Diamond_outputDao diamondOutputDao;
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    RealtimeOnlineHistoriesDao realtimeOnlineHistoriesDao;
    @Autowired
    OperationDailyDao operationDailyDao;


    @Autowired
    ReportService reportService;


    private Logger logger = LoggerFactory.getLogger(getClass());



    @Scheduled(cron = "50 59 23 ? * *")
    public void statisticalFinancialDaily(){
        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        //获取当前时间，录入，
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = now.format(new Date());// new Date()为获取当前系统时间

        String date_start = date+" 00:00:00";
        String date_end = date+" 23:59:58";




        logger.info("统计财务日报时间："+sysDate);
        long begin = System.currentTimeMillis();


        //统计 1:苹果充值、2:微信充值、3:平台充值（orders表）RMB的统计
        DiamondsEntity appleRecharge = diamondsDao.findRecharge(1, date_start, date_end);
        int apple_recharge = 0;
        if(appleRecharge!=null){
            apple_recharge = appleRecharge.getPrice();
        }
        DiamondsEntity wechatRecharge = diamondsDao.findRecharge(2, date_start, date_end);
        int wechat_recharge = 0;
        if(wechatRecharge!=null){
            wechat_recharge = wechatRecharge.getPrice();
        }
        DiamondsEntity platformRecharge = diamondsDao.findRecharge(3, date_start, date_end);
        int platform_recharge = 0;
        if(platformRecharge!=null){
            platform_recharge = platformRecharge.getPrice();
        }

        //充值汇总
        int total_revenue = apple_recharge+wechat_recharge+platform_recharge;



        //用于统计充值的钻石数量
        //int apple_diamond=0;
        //int wechat_diamond=0;
        //int platform_diamond=0;
        int total_recharge = 0;


        //统计钻石总充值数
        DiamondsEntity rechargeTotal = diamondsDao.findDiamondRecharge(date_start, date_end);
        if(rechargeTotal!=null){
            total_recharge = rechargeTotal.getDiamond();
        }


        //得到解散房间的ID
        //String condition = reportService.getRoomCondition("2017-10-17 00:00:00","2017-10-17 23:59:00");



        //统计钻石流动总数
        IncomeEntity byDateForNumber = incomeDao.findByDateForNumber("钻石", "发送", date_start, date_end);
        int diamond_flow_total = 0;
        if(byDateForNumber!=null){
            diamond_flow_total=byDateForNumber.getNumber();
        }


        //统计当日私房总次数
        int confidential_count = 0;

        //统计钻石消耗
        //统计开房总消耗
        DiamondsEntity roomConsume = diamondsDao.findRoomConsume(date_start, date_end);
        int room_consume =0;
        if(roomConsume!=null){
            room_consume = roomConsume.getRoom_consume();
        }
        //统计开宝箱消耗
        DiamondsEntity treasureConsume = diamondsDao.findTreasureConsume(date_start, date_end);
        int treasure_consume = 0 ;
        if(treasureConsume!=null){
            treasure_consume = treasureConsume.getTreasure_consume();
        }
        //统计参赛消耗
        DiamondsEntity competitionConsume = diamondsDao.findCompetitionConsume(date_start,date_end);
        int competition_consume = 0;
        if(competitionConsume!=null){
            competition_consume = competitionConsume.getCompetition_consume();
        }

        //钻石消耗统计：
        int diamond_expend_total = room_consume+treasure_consume+competition_consume;



        financial_dailyDao.updateFinancialDaily(date,total_revenue,apple_recharge,wechat_recharge,platform_recharge,total_recharge,diamond_expend_total,confidential_count,diamond_flow_total,0,0,0);


        //日志输出：
        long end = System.currentTimeMillis();
        logger.info("财务日报统计结束，共耗时：[" + (end-begin) / 1000 + "]秒");
    }






    //0 */2 * ? * *
    //统计钻石消耗详情 50 59 23 ? * *
    @Scheduled(cron = "50 59 23 ? * *")
    public void diamondConsume(){
        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        //获取当前时间，录入，
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = now.format(new Date());// new Date()为获取当前系统时间

        String date_start = date+" 00:00:00";
        String date_end = date+" 23:59:58";


        logger.info("统计钻石消耗详情时间："+sysDate);
        long begin = System.currentTimeMillis();



        //统计开房总消耗
        DiamondsEntity roomConsume = diamondsDao.findRoomConsume(date_start, date_end);
        int room_consume =0;
        if(roomConsume!=null){
            room_consume = roomConsume.getRoom_consume();
        }


        //统计开宝箱消耗
        DiamondsEntity treasureConsume = diamondsDao.findTreasureConsume(date_start, date_end);
        int treasure_consume = 0 ;
        if(treasureConsume!=null){
            treasure_consume = treasureConsume.getTreasure_consume();
        }

        //统计参赛消耗
        DiamondsEntity competitionConsume = diamondsDao.findCompetitionConsume(date_start, date_end);
        int competition_consume = 0;
        if(competitionConsume!=null){
            competition_consume = competitionConsume.getCompetition_consume();
        }


        //统计神秘宝箱开启消耗 和开启次数
        DiamondsEntity mysteryTreasuresConsume = diamondsDao.findMysteryTreasuresConsume(1,date_start, date_end);
        int mystery_treasures_count = 0;
        int mystery_treasures_consume =0;
        if(mysteryTreasuresConsume!=null){
            mystery_treasures_count = mysteryTreasuresConsume.getCount();
            mystery_treasures_consume = mysteryTreasuresConsume.getConsume();
        }


        //统计金宝箱开启消耗 和开启次数
        DiamondsEntity goldConsume = diamondsDao.findMysteryTreasuresConsume(2,date_start, date_end);
        int gold_count = 0;
        int gold_consume = 0;
        if(goldConsume!=null){
            gold_count = goldConsume.getCount();
            gold_consume = goldConsume.getConsume();
        }

        //统计银宝箱开启消耗 和开启次数
        DiamondsEntity silverConsume = diamondsDao.findMysteryTreasuresConsume(3,date_start, date_end);
        int silver_count = 0;
        int silver_consume = 0;
        if(silverConsume!=null){
            silver_count = silverConsume.getCount();
            silver_consume = silverConsume.getConsume();
        }


        //统计上游十三张报名次数
        DiamondsEntity thirteenCount = diamondsDao.findThirteenCount(date_start, date_end);
        int thirteen_count = 0;
        if(thirteenCount!=null){
            thirteen_count = thirteenCount.getThirteen_count();
        }

        int consume_total =room_consume+treasure_consume+competition_consume;
        //int consume_total =treasure_consume+competition_consume+mystery_treasures_consume+gold_consume+silver_consume;
        int fish_count = 0;
        int fivek_count = 0;
        int competition_count = 0;
        competition_count = thirteen_count+fish_count+fivek_count;

        logger.info(room_consume+"--"+treasure_consume+"--"+competition_consume+"--"+mystery_treasures_count+"--"+mystery_treasures_consume+"--"+gold_count+gold_consume+"--"+silver_count+"--"+silver_consume+"--"+thirteen_count);
        diamondConsumeDao.insertConsumeTotal(date,consume_total,room_consume,treasure_consume,competition_consume,mystery_treasures_count,mystery_treasures_consume,gold_count,gold_consume,silver_count,silver_consume,thirteen_count,fivek_count,fish_count,competition_count);



        //日志输出：
        long end = System.currentTimeMillis();
        logger.info("统计钻石消耗详情结束，共耗时：[" + (end-begin) / 1000 + "]秒");
    }






    //钻石产生详情0 */1 * ? * *
    @Scheduled(cron = "50 59 23 ? * *")
    public void diamondoutput(){
        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        //获取当前时间，录入，
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = now.format(new Date());// new Date()为获取当前系统时间

        String date_start = date+" 00:00:00";
        String date_end = date+" 23:59:58";


        logger.info("统计钻石产生详情时间："+sysDate);
        long begin = System.currentTimeMillis();


        int daily_output = 0;
        int sign_count = 0;
        int sign_output = 0;
        int treasure_output = 0;
        int treasure_count = 0;
        int msg_count = 0;
        int msg_output = 0;
        int novice_boot = 0;        //新手引导产出



        //查询日常赛产出
        Diamond_outputEntity dailyOutput = diamondOutputDao.dailyOutput(date_start, date_end);
        if(dailyOutput!=null){
            daily_output = dailyOutput.getDaily_output();
        }


        //签到产出  和 签到次数
        Diamond_outputEntity signOutput = diamondOutputDao.signOutput(date_start, date_end);
        if(signOutput!=null){
            sign_count = signOutput.getSign_count();
            sign_output = signOutput.getSign_output();
        }


        //宝箱产出
        Diamond_outputEntity treasureOutput = diamondOutputDao.treasureOutput(date_start, date_end);
        if(treasureOutput!=null){
            treasure_output = treasureOutput.getTreasure_output();
            treasure_count = treasureOutput.getTreasure_count();
        }


        //奖励消息总产出（钻石）
        DiamondsEntity msgDiamOutPut = diamondsDao.totalOutput(303,date_start, date_end);
        if(msgDiamOutPut!=null){
            msg_output = msgDiamOutPut.getChange_val();
        }


        //完成新手引导总产出（钻石）
        DiamondsEntity noviceBootOutput = diamondsDao.totalOutput(101, date_start, date_end);
        if(noviceBootOutput!=null){
            novice_boot = noviceBootOutput.getChange_val();
        }


        //统计产出总量：
        int output_total = msg_output + treasure_output + sign_output + daily_output;


        //将统计的数据存入数据库中
        diamondOutputDao.insertDiamondOutput(date,daily_output,sign_count,sign_output,treasure_output,treasure_count,msg_count,msg_output,output_total,novice_boot);


        //日志输出：
        long end = System.currentTimeMillis();
        logger.info("钻石产生详情统计结束，共耗时：[" + (end-begin) / 1000 + "]秒");
    }



    //统计运营日报  每隔一分钟统计一次 0 */1 * ? * *
    //每隔一小时统计一次 0 0 */1 ? * *
    @Scheduled(cron = "0 */57 * ? * *")
    public void operationDaily()  {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        try {
            reportService.operationDaily(date);
        } catch (ParseException e) {
            logger.error("operationDaily 报表统计错误：",e);
        }
    }



    // 0 * */1 ? * *统计第二次充值半价  0 */1 * ? * *
    @Scheduled(cron = "50 59 23 ? * *")
    public void halfPrice()  {
        String date = CommonUtil.creationDate("yyyy-MM-dd");
        try {
            reportService.flowAwayDaily(date);
        } catch (ParseException e) {
            logger.error("operationDaily 报表统计错误：",e);
        }
    }


    //查询合伙人下的所有下级  0 */1 * ? * *
    @Scheduled(cron = "0 */5 * ? * *")
    public void partnerData()  {
        reportService.partnerData();
    }


    // 0 */2 * ? * *    每两分钟执行一次
  /*  public final static long ONE_DAY = 24 * 60 * 60 * 1000;
    public final static long ONE_HOUR =  60 * 60 * 1000;
    @Scheduled(fixedRate = ONE_DAY)
    public void scheduledTask() {
        System.out.println(" 我是一个每隔一天就会执行一次的调度任务");
    }
*/
}

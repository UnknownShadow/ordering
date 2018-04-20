package com.juunew.admin.services;

import com.juunew.admin.dao.DiamondsDao;
import com.juunew.admin.dao.RealtimeOnlineHistoriesDao;
import com.juunew.admin.entity.DiamondsEntity;
import com.juunew.admin.entity.RealtimeOnlineHistoriesEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by juunew on 2018/2/5.
 */
@Service
public class ProxyWithdrawJudgeService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RealtimeOnlineHistoriesDao realtimeOnlineHistoriesDao;

    @Autowired
    DiamondsDao diamondsDao;



    /**
     * 查询七天内该用户是否有充值
     * @param create_time   当前提现申请时间  yyyy-MM-dd hh:mm:ss
     * @param user_id      需要判断的用户
     * @return
     */
    public Boolean WithinSevenDaysRecharge(String create_time,int user_id) throws ParseException {

        logger.info("查询userId（"+user_id+"）七天内是否有充值过");

        Boolean flag = false;

        //转换为时间戳
        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date createdTime = slf.parse(create_time);


        //得到七天前的日期
        Date sevenDays = new Date(createdTime.getTime() - 7 * 24 * 60 * 60 * 1000);
        String sevenDaysAgo = slf.format(sevenDays);
        logger.info("得到七天前的日期：{}", sevenDaysAgo);


        //根据时间区间查询是否有充值
        DiamondsEntity payTotal = diamondsDao.withinSevenDaysRecharge(user_id,sevenDaysAgo,create_time);

        int price = 0;

        if (payTotal != null) price = payTotal.getPrice() / 100;
        if(price>0){
            flag = true;
            logger.info("userId（"+user_id+"）充值的金额：{}",price);
        }else{
            logger.info("userId（"+user_id+"）七天内没有充值过");
        }

        return flag;
    }



    /**
     *  判断该用户是否最近5天连续登录过
     * @param create_time 当前提现申请时间 yyyy-MM-dd
     * @param user_id   需要判断的用户
     * @return
     * @throws ParseException
     */
    public Boolean fiveDaysActivity(String create_time,int user_id) throws ParseException {

        logger.info("查询userId（"+user_id+"）是否最近5天连续登录过");

        boolean flag = false;

        //转换为时间戳
        SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd");
        Date createdTime = slf.parse(create_time);


        String twoDaysAgo = slf.format(new Date(createdTime.getTime() - 24 * 60 * 60 * 1000));
        String threeDaysAgo = slf.format(new Date(createdTime.getTime() - 2 * 24 * 60 * 60 * 1000));
        String fourDaysAgo = slf.format(new Date(createdTime.getTime() - 3 * 24 * 60 * 60 * 1000));
        String fiveDaysAgo = slf.format(new Date(createdTime.getTime() - 4 * 24 * 60 * 60 * 1000));


        String today_start = create_time+" 00:00:00";
        String today_end = create_time+" 23:59:58";

        String twoDaysAgo_start = twoDaysAgo+" 00:00:00";
        String twoDaysAgo_end = twoDaysAgo+" 23:59:58";

        String threeDaysAgo_start = threeDaysAgo+" 00:00:00";
        String threeDaysAgo_end = threeDaysAgo+" 23:59:58";

        String fourDaysAgo_start = fourDaysAgo+" 00:00:00";
        String fourDaysAgo_end = fourDaysAgo+" 23:59:58";

        String fiveDaysAgo_start = fiveDaysAgo+" 00:00:00";
        String fiveDaysAgo_end = fiveDaysAgo+" 23:59:58";


        logger.info("第二天日期：" + twoDaysAgo);
        logger.info("第三天日期：" + threeDaysAgo);
        logger.info("第四天日期：" + fourDaysAgo);
        logger.info("第五天日期：" + fiveDaysAgo);


        RealtimeOnlineHistoriesEntity activeOne = realtimeOnlineHistoriesDao.queryTodayOnline(today_start, today_end, user_id);
        RealtimeOnlineHistoriesEntity activeTwo = realtimeOnlineHistoriesDao.queryTodayOnline(twoDaysAgo_start, twoDaysAgo_end, user_id);
        RealtimeOnlineHistoriesEntity activeThree = realtimeOnlineHistoriesDao.queryTodayOnline(threeDaysAgo_start, threeDaysAgo_end, user_id);
        RealtimeOnlineHistoriesEntity activeFour = realtimeOnlineHistoriesDao.queryTodayOnline(fourDaysAgo_start, fourDaysAgo_end, user_id);
        RealtimeOnlineHistoriesEntity activeFive = realtimeOnlineHistoriesDao.queryTodayOnline(fiveDaysAgo_start, fiveDaysAgo_end, user_id);

        logger.info("时间：{}",fiveDaysAgo_start);

        if(activeOne!=null && activeTwo!=null && activeThree!=null &&
                activeFour!=null && activeFour!=null) {
            flag = true;
        }

        return flag;
    }

}

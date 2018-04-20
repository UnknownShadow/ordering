package com.juunew.admin.services;

import com.juunew.admin.entity.GameUserEntity;
import com.juunew.admin.entity.RealtimeOnlineHistoriesEntity;
import com.juunew.admin.entity.WechatRechargeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by juunew on 2018/1/12.
 */
@Service
public class HighchartsService {

    Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 处理data
     *
     * @param date   //日期，遍历一天时间
     * @param list   //统计数据表：如用户新增、付费笔数
     * @param status //表示不同的统计数据：1：用户新增数量，2：付费笔数，3：付费金额
     * @return
     * @throws ParseException
     */
    public String disposedHighchartsData(String date, List list, int status) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = sdf.parse(date + " 00:00:00");
        Date end = sdf.parse(date + " 23:59:59");

        String data = "[";        //封装Data数据
        int peoples = 0;
        long y = 0;
        long stamp = 1440000L;    //1440000 : 是用于时间段的跨度，如：2:00~2:30 区间

        for (long x = start.getTime(); x <= end.getTime(); x += stamp) {
            //logger.info("时间戳：{}",x);

            long start_time = x * 1L;
            Date loop_date = new Date(start_time);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String loopDate = sDateFormat.format(loop_date);
            //logger.info("遍历出来的时间：{}",loopDate);

            int count = 0;
            //得到前一时间戳，用于时间戳与时间戳的遍历
            y = x + stamp;
            for (; y > x; y -= 60000) {        //60000遍历时 不遍历秒，
                Date dateOther = new Date(y);
                String dateTime = sDateFormat.format(dateOther);
                //logger.info("得到前一时间戳中的遍历值dateTime：{}",dateTime);

                //处理时间段中的总数量，
                count = count + getCount(list, dateTime, status);
            }
            peoples = peoples + count;  //统计当天总新增人数
            data = data + count + ",";
        }

        logger.info(date + "新增总人数为：{}", peoples);
        data = data.substring(0, data.length() - 1);
        data = data + "]";

        return data;
    }


    /**
     * 处理时间段中的总数量，
     *
     * @param list
     * @param dateTime
     * @param status
     * @return
     */
    public int getCount(List list, String dateTime, int status) {
        int count = 0;

        if (status == 1) {      //新增用户
            for (Object newUsers : list) {
                GameUserEntity user = (GameUserEntity) newUsers;
                String created_at = user.getCreated_at();    //用户新增时间
                created_at = created_at.substring(0, created_at.length() - 4);     //去掉秒，比较时，秒不参与
                created_at = created_at + "00";
                //logger.info("新增用户的时间：{}",created_at);
                if (created_at.equals(dateTime)) {
                    logger.info("用户userID:（" + user.getId() + "）新增时间与遍历时间相同；时间值：{}", created_at);
                    count++;
                }
            }
        } else if (status == 2) {    //付费笔数
            for (Object payUsers : list) {
                WechatRechargeEntity payUser = (WechatRechargeEntity) payUsers;
                String created_at = payUser.getCreated_date();    //用户付费时间
                created_at = created_at.substring(0, created_at.length() - 4);     //去掉秒，比较时，秒不参与
                created_at = created_at + "00";
                //logger.info("新增用户的时间：{}",created_at);
                if (created_at.equals(dateTime)) {
                    logger.info("用户userID:（" + payUser.getUser_id() + "）付费时间与遍历时间相同；时间值：{}", created_at);
                    count++;
                }
            }
        } else if (status == 3) {  //付费金额
            for (Object payCounts : list) {
                WechatRechargeEntity payCount = (WechatRechargeEntity) payCounts;
                String created_at = payCount.getCreated_date();    //用户付费时间
                created_at = created_at.substring(0, created_at.length() - 4);     //去掉秒，比较时，秒不参与
                created_at = created_at + "00";
                //logger.info("新增用户的时间：{}",created_at);
                if (created_at.equals(dateTime)) {
                    logger.info("用户userID:（" + payCount.getUser_id() + "）付费时间与遍历时间相同；时间值：{}", created_at);
                    int pay = payCount.getRecharge_money() / 100;
                    count = count + pay;
                }
            }
        } else if (status == 4) {  //活跃用户数量
            for (Object activeUsers : list) {
                RealtimeOnlineHistoriesEntity activeUser = (RealtimeOnlineHistoriesEntity) activeUsers;
                String created_at = activeUser.getCreated_at();    //用户付费时间
                created_at = created_at.substring(0, created_at.length() - 4);     //去掉秒，比较时，秒不参与
                created_at = created_at + "00";
                //logger.info("新增用户的时间：{}",created_at);
                if (created_at.equals(dateTime)) {
                    logger.info("用户userID:（" + activeUser.getUser_id() + "）付费时间与遍历时间相同；时间值：{}", created_at);
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * 处理时间轴的List
     *
     * @param date        从前端页面中获取的时间
     * @param session     缓存
     * @param dateSession 从缓存中获取的时间list，
     * @return
     * @throws ParseException
     */
    public List<String> getDateList(String date, HttpSession session, List<String> dateSession) throws ParseException {

        List<String> dates;

        if (dateSession != null) {
            dates = dateSession;
            if (!date.equals("")) dates.add(date);
        } else {
            //获取 今天、昨天、前天 日期
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String today = dateFormat.format(new Date());
            Date processDate = dateFormat.parse(today);
            String twoDaysAgo = dateFormat.format(new Date(processDate.getTime() - 24 * 60 * 60 * 1000));
            String threeDaysAgo = dateFormat.format(new Date(processDate.getTime() - 2 * 24 * 60 * 60 * 1000));

            logger.info("今天日期：{}", today);
            logger.info("昨天日期：{}", twoDaysAgo);
            logger.info("前天日期：{}", threeDaysAgo);

            dates = new ArrayList<String>();
            dates.add(today);        //HighChats中的默认数据  今天
            dates.add(twoDaysAgo);        //昨天
            dates.add(threeDaysAgo);        //前天
            if (!date.equals("")) dates.add(date);
            session.setAttribute("dateSession", dates);
        }
        return dates;
    }


}

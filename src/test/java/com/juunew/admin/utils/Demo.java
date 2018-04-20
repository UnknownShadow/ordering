package com.juunew.admin.utils;

import com.juunew.admin.controller.BaseController;
import com.juunew.admin.entity.GameUserEntity;
import com.juunew.admin.entity.VersionsEntity;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Demo extends BaseController {

    private static List<Date> dateSplit(Date startDate, Date endDate)
            throws Exception {
        if (!startDate.before(endDate))
            throw new Exception("开始时间应该在结束时间之后");
        Long spi = endDate.getTime() - startDate.getTime();
        Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(endDate);
        for (int i = 1; i <= step; i++) {
            dateList.add(new Date(dateList.get(i - 1).getTime()
                    - (24 * 60 * 60 * 1000)));// 比上一天减一
        }
        return dateList;
    }


   /* public static void main(String[] args) throws ParseException {

        //遍历时间段
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date start = sdf.parse("00:00");
        Date end = sdf.parse("23:59");

        for(long i=start.getTime()/1000;i<=end.getTime()/1000;i+=1){
            System.out.println("值："+i);

            long start_time = i*1000L;
            Date date = new Date(start_time);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            System.out.println("遍历出来的时间："+sDateFormat.format(date));
        }


       *//* Date start = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            start = sdf.parse("2018-1-20 18:10");
            Date end = sdf.parse("2018-1-20 18:20");
            List<Date> lists = dateSplit(start, end);
            if (!lists.isEmpty()) {
                for (Date date : lists) {
                    System.out.println(sdf.format(date));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*//*

    }
*/



}

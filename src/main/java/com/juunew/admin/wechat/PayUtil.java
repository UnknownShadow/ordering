package com.juunew.admin.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by juunew on 2017/9/2.
 */
public class PayUtil {

    public static String createOutTradeNo(){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        String deal= sysDate.replaceAll("-","");

        String with= deal.replaceAll(":","");

        String dealWith = with.replaceAll(" ","");

        int frontRandom = (int)(Math.random()*9000+1000);

        int afterRandom = (int)(Math.random()*9000+1000);

        String orderNumber = frontRandom + dealWith + afterRandom;

        return orderNumber;
    }




    //获取时间戳
    public static String createTimeStamp(){


        long timeStamp = System.currentTimeMillis();
        long stamp = timeStamp/1000;

     /*   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        String deal= sysDate.replaceAll("-","");

        String with= deal.replaceAll(":","");

        String dealWith = with.replaceAll(" ","");*/

        return stamp+"";
    }
}

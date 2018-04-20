package com.juunew.admin.controller;

import com.juunew.admin.dao.ControlTimesDao;
import com.juunew.admin.dao.DiamondsDao;
import com.juunew.admin.entity.ControlTimesEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by juunew on 2017/8/18.
 */
@Component
public class TimedTaskController implements Job{
    @Autowired
    DiamondsDao diamondsDao;

    @Autowired
    ControlTimesDao controlTimesDao;


    private Logger logger = LoggerFactory.getLogger(getClass());



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
//        logger.debug("统计财务日报时间："+sysDate);


       /*List<DiamondsEntity> appleRecharges = diamondsDao.findAppleRecharge(1, "2017-07-26 00:00:00", "2017-07-26 23:59:59");

        if(appleRecharges!=null){
            for (DiamondsEntity appleRecharge : appleRecharges){
                System.out.println("变动钻石数："+appleRecharge.getChange_val());
            }
        }else{
            System.out.println("数量为0");
        }*/
        List<ControlTimesEntity> byTimes = controlTimesDao.findByTimes(23);
        for (ControlTimesEntity times : byTimes){
            System.out.println(times);
        }


    }
}

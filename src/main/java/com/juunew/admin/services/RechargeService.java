package com.juunew.admin.services;

import com.juunew.admin.dao.*;
import com.juunew.admin.entity.GameUserEntity;
import com.juunew.admin.entity.RebateRatioEntity;
import com.juunew.admin.entity.UserSpreadEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RechargeService {

    Logger logger = LoggerFactory.getLogger(RechargeService.class);


    @Autowired
    IntegralRebateDao integralRebateDao;

    @Autowired
    UserSpreadDao userSpreadDao;

    @Autowired
    GameUserDao gameUserDao;

    @Autowired
    IntegralsDao integralsDao;

    @Autowired
    RebateRatioDao rebateRatioDao;

    @Autowired
    UserService userService;



    /**
     * 返积分给上级
     *
     * @param num         充值的钻石数量
     * @param user_id     用户ID
     * @param money       充值金额
     * @param superior_id 上级ID
     * @param type        返利比例类型
     */
    public void integralRebate(int num, int user_id, int money, int superior_id, int type) {

        String purpose = "用户（" + user_id + "）充值给上级代理（" + superior_id + "）返利";

        //查询返利比例
        RebateRatioEntity rebateRatioEntity = rebateRatioDao.queryByType(type);

        logger.info(purpose + "的比例：{}", rebateRatioEntity.getRatio());

        //返利的积分值
        int rebateNum = (int) (rebateRatioEntity.getRatio() * money);

        logger.info("user_id（" + superior_id + "）的返利积分为：{}", rebateNum);

        //积分返利记录  , 将记录新增到积分返利数据表中
        integralRebateDao.insertToRebate(superior_id, user_id, num, rebateNum, 0, money);

        //积分新增 记录入库
        userService.integralAddRecord(superior_id, rebateNum, 0, purpose);
    }




    /**
     * 给推广者返利积分
     *
     * @param money   充值金额 单位 分
     * @param user_id 充值user_id
     * @return
     */
    public void userSpreadIntegral(int money, int num, int user_id) {

        logger.info("推广者返利积分：");

        //推广者积分返利，查询该充值用户是否为 被推广用户
        UserSpreadEntity spreadInfo = userSpreadDao.findByUserId(user_id);

        if (spreadInfo != null) {   //不为空 说明该用户是 被推广下载的，需要给推广者返10%积分

            //推广者返积分比例；
            double integral_proportion = spreadInfo.getIntegral_proportion();

            //给推广者 返的积分值 10%
            int spreadIntegral = spreadIntegral = (int) (money * 0.1);  //返利10%

            logger.info("推广用户：下载者user_id（" + user_id + "）");
            logger.info("推广用户：推广者user_id（" + spreadInfo.getSpreader_user_id() + "）的返利积分为：{}", spreadIntegral);

            //积分返利 , 将记录新增到积分返利数据表中    user_id
            int rebate_number = spreadIntegral;
            integralRebateDao.insertToRebateTwo(spreadInfo.getSpreader_user_id(), user_id, num, rebate_number, 1, 1);

            String purpose = "下载者（" + user_id + "）给推广者user_id（" + spreadInfo.getSpreader_user_id() + "）的返利积分：冻结中";


            int old_val = 0, new_val = 0;
            //查询当前总可用积分值
            GameUserEntity queryIntegral = gameUserDao.findIntegralByUserId(user_id);
            if (queryIntegral != null) {
                old_val = queryIntegral.getIntegral();
                new_val = queryIntegral.getIntegral();
            }


            //记录积分操作
            integralsDao.insertToIntegrals(spreadInfo.getSpreader_user_id(), old_val, new_val,
                    rebate_number, 0, purpose);


            //将积分返利记录到（income）表中
            //incomeDao.updateIncome(spreadInfo.getSpreader_user_id(), "", "积分", user_id, rebate_number, "");
        } else {
            logger.info("user_id（" + user_id + "）无推广者");
        }
    }



}

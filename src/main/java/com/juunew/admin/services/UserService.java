package com.juunew.admin.services;


import com.juunew.admin.dao.GameUserDao;
import com.juunew.admin.dao.IntegralRebateDao;
import com.juunew.admin.dao.IntegralsDao;
import com.juunew.admin.entity.GameUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    Logger logger  = LoggerFactory.getLogger(UserService.class);

    @Autowired
    IntegralsDao integralsDao;
    @Autowired
    IntegralRebateDao integralRebateDao;
    @Autowired
    GameUserDao gameUserDao;



    /**
     *  积分新增 记录入库
     * @param user_id   增加积分的用户ID
     * @param integral   需要增加的积分值  （单位：分）
     * @param change_type
     * @param purpose   用途说明
     */
    public void integralAddRecord(int user_id,int integral, int change_type, String purpose){
        int old_val = 0, new_val = 0;

        //积分旧值
        GameUserEntity oldVal = gameUserDao.findIntegralByUserId(user_id);
        if (oldVal != null) old_val = oldVal.getIntegral();

        //积分增加
        gameUserDao.addIntegral(user_id, integral);

        //积分新值
        GameUserEntity newVal = gameUserDao.findIntegralByUserId(user_id);
        if (newVal != null) new_val = newVal.getIntegral();

        //记录积分操作
        integralsDao.insertToIntegrals(user_id, old_val, new_val, integral, change_type, purpose);
    }



}

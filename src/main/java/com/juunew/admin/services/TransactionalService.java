package com.juunew.admin.services;

import com.juunew.admin.dao.AllotDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by juunew on 2018/2/26.
 */

@Service
public class TransactionalService {

    Logger logger  = LoggerFactory.getLogger(TransactionalService.class);

    @Autowired
    AllotDao allotDao;

    @Autowired
    BankService bankService;



    @Transactional
    public void sd() throws Exception {

        allotDao.updateToAllot(0,1);

        //钻石充值
        int diamond_s = bankService.addDiamond(1, 55, 10, 1, "微信钻石充值", 302);

        allotDao.updateToAllot(0,2);

        System.out.println();
    }


    @Cacheable(value="myCache",key="#userName")
    public String get(String userName) {

        //remove();

        return userName;
    }

    @CacheEvict(value="myCache",key="#userName")
    public void remove(){

    }
}

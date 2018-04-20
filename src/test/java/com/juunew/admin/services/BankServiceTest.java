package com.juunew.admin.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by juunew on 2017/6/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BankServiceTest {

    @Autowired
    BankService bankService;

    @Test
    public void addDiamond() throws Exception {
      /*  int oldDiamond = bankService.addDiamond(23, 2);
        assertTrue(oldDiamond > 0);

        int newDiamond = bankService.addDiamond(23, 2);
        assertTrue(newDiamond == oldDiamond+2);*/
    }

}
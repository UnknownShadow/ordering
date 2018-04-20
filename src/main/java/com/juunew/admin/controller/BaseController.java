package com.juunew.admin.controller;

import com.juunew.admin.dao.GameUserDao;
import com.juunew.admin.entity.GameUserEntity;
import com.juunew.admin.entity.api.Ajax;
import com.juunew.admin.exceptions.AuthException;
import com.juunew.admin.exceptions.PersonalCenterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    GameUserDao gameUserDao;



    protected GameUserEntity auth(String token) throws Exception {
        GameUserEntity userEntity = gameUserDao.findUsersIDByToken(token);
        if (userEntity==null){
            throw new AuthException("token错误，认证失败", -9);
        }
        return userEntity;
    }

    //个人中心接口错误提示信息和错误码；
    protected void personalError(String msg,int code){
        throw new PersonalCenterException(msg, code);
    }

    protected int auth(HttpSession session){
        if (session.getAttribute("users_id")!=null) {
            return (int) session.getAttribute("users_id");
        }
        return 0;
    }

    @ExceptionHandler(Exception.class)
    public Ajax handleException(Exception ex) {
        logger.error("handleException", ex);
        if (ex instanceof AuthException){
            return new Ajax(-9, ex.getMessage());
        }
        return new Ajax(-1, ex.getMessage());
    }


    /**
     *  处理分页查询的 total 总条数
     * @param sum   总页数
     * @param limit     一页显示多少条数据
     * @return
     */
    protected int disposeTotal(int sum,int limit){
        if (sum == 0) { sum = 1; }
        int total = 0;
        if (sum % limit == 0) {
            total = sum / limit;
        } else {
            total = sum / limit + 1;
        }
        return total;
    }
}

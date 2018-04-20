package com.juunew.admin.exceptions;

/**
 * Created by guange on 03/05/2017.
 */
public class PersonalCenterException extends RuntimeException{

    public static final int PasswordError = 1;
    public static final int UnActive = 2;

    /**
     * 异常编码 1. 用户名或密码不正确  2. 用户未激活或被禁用
     */
    private int errorCode;


    public PersonalCenterException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}

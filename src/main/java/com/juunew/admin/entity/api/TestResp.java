package com.juunew.admin.entity.api;



/**
 * Created by juunew on 2018/1/26.
 */
public class TestResp {

    private Object Data;
    private int code;
    private String errMsg;


    @Override
    public String toString() {
        return "TestResp{" +
                "Data=" + Data +
                ", code=" + code +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

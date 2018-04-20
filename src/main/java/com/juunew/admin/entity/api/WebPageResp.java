package com.juunew.admin.entity.api;



/**
 * 前端网页请求返回类
 */
public class WebPageResp {

    private Object data;
    private int code;
    private String errMsg;


    @Override
    public String toString() {
        return "WebPageResp{" +
                "data=" + data +
                ", code=" + code +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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

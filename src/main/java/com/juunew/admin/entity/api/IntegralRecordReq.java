package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralRecordReq {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "一页显示总条数")
    private int limit;
    @ApiModelProperty(value = "当前第几页，从0页开始")
    private int page;


    @Override
    public String toString() {
        return "MyProxyReq{" +
                "token='" + token + '\'' +
                ", limit=" + limit +
                ", page=" + page +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

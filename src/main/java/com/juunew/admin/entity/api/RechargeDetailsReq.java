package com.juunew.admin.entity.api;


import com.juunew.admin.entity.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class RechargeDetailsReq extends ApiRequest{

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "一页显示总条数")
    private int limit;
    @ApiModelProperty(value = "当前第几页")
    private int page;


    @Override
    public String toString() {
        return "RechargeDetailsReq{" +
                "token='" + token + '\'' +
                ", limit=" + limit +
                ", page=" + page +
                '}';
    }


    @Override
    public String getToken() {
        return token;
    }

    @Override
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

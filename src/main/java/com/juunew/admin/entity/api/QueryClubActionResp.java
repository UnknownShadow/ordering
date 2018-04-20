package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class QueryClubActionResp {

    @ApiModelProperty(value = "是否符合条件内容显示")
    private String content;
    @ApiModelProperty(value = "是否符合条件；1：符合条件，0：不满足条件")
    private int status;


    @Override
    public String toString() {
        return "QueryClubActionResp{" +
                "content='" + content + '\'' +
                ", status=" + status +
                '}';
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class IntegralProductsResp {

    @ApiModelProperty(value = "商品ID")
    private int id;
    @ApiModelProperty(value = "钻石数量")
    private int diamond;
    @ApiModelProperty(value = "金额/积分")
    private double money;
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;


    @Override
    public String toString() {
        return "IntegralProductsResp{" +
                "id=" + id +
                ", diamond=" + diamond +
                ", money=" + money +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by juunew on 2018/1/26.
 */
@ApiModel
public class WebMyProxyReq {

    @ApiModelProperty(value = "carId")
    private String carId;


    @Override
    public String toString() {
        return "WebMyProxyReq{" +
                "carId='" + carId + '\'' +
                '}';
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}

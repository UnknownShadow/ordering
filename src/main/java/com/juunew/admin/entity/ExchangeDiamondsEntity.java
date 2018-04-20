package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class ExchangeDiamondsEntity {

    private int id;
    private int user_id;
    private int integral_product_id;        //积分换钻表的商品ID
    private int consume_integral;       //消耗的积分值；（单位：分）
    private int diamond;        //得到的钻石数
    private String created_time;
    private String updated_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIntegral_product_id() {
        return integral_product_id;
    }

    public void setIntegral_product_id(int integral_product_id) {
        this.integral_product_id = integral_product_id;
    }

    public int getConsume_integral() {
        return consume_integral;
    }

    public void setConsume_integral(int consume_integral) {
        this.consume_integral = consume_integral;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
}

package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class WechatRechargeEntity {

    private int id;
    private int user_id;
    private String order_number;
    private int recharge_number;
    private int recharge_money;         //充值的钱数
    private String created_date;
    private int payment_status;       //付款状态；0：未支付；1：已支付；
    private int payment_method;     //支付方式；1：公众号支付，2，app支付
    private int products_id;        //对应的商品ID

    //-----------------------------------------------------------------

    private String nickname;        //用户昵称
    private int user_status;        //用户身份 （2：代理；3：玩家）
    private int diamond;
    private double total_integral;        //总积分（单位：元）
    private double money;        //充值钱数（单位：元）
    private String couplet_date;        //联表查询所用的固定时间




    public String getCouplet_date() {
        return couplet_date;
    }

    public void setCouplet_date(String couplet_date) {
        this.couplet_date = couplet_date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public double getTotal_integral() {
        return total_integral;
    }

    public void setTotal_integral(double total_integral) {
        this.total_integral = total_integral;
    }

    public int getProducts_id() {
        return products_id;
    }

    public void setProducts_id(int products_id) {
        this.products_id = products_id;
    }

    public int getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(int payment_method) {
        this.payment_method = payment_method;
    }

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

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public int getRecharge_number() {
        return recharge_number;
    }

    public void setRecharge_number(int recharge_number) {
        this.recharge_number = recharge_number;
    }

    public int getRecharge_money() {
        return recharge_money;
    }

    public void setRecharge_money(int recharge_money) {
        this.recharge_money = recharge_money;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }
}

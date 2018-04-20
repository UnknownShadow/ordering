package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class ViewIntegralEntity {

    private int user_id;
    private int rebate_child_total;
    private int child_total_count;  //子代理返利笔数
    private int total_rebate;    //总返利积分
    private int withdrawal_rebate;   //提现积分
    private int present_rebate;   //当前积分


    @Override
    public String toString() {
        return "ViewIntegralEntity{" +
                "user_id=" + user_id +
                ", rebate_child_total=" + rebate_child_total +
                ", child_total_count=" + child_total_count +
                ", total_rebate=" + total_rebate +
                ", withdrawal_rebate=" + withdrawal_rebate +
                ", present_rebate=" + present_rebate +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRebate_child_total() {
        return rebate_child_total;
    }

    public void setRebate_child_total(int rebate_child_total) {
        this.rebate_child_total = rebate_child_total;
    }

    public int getChild_total_count() {
        return child_total_count;
    }

    public void setChild_total_count(int child_total_count) {
        this.child_total_count = child_total_count;
    }

    public int getTotal_rebate() {
        return total_rebate;
    }

    public void setTotal_rebate(int total_rebate) {
        this.total_rebate = total_rebate;
    }

    public int getWithdrawal_rebate() {
        return withdrawal_rebate;
    }

    public void setWithdrawal_rebate(int withdrawal_rebate) {
        this.withdrawal_rebate = withdrawal_rebate;
    }

    public int getPresent_rebate() {
        return present_rebate;
    }

    public void setPresent_rebate(int present_rebate) {
        this.present_rebate = present_rebate;
    }
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class Result<T> implements Serializable {

	private static final long serialVersionUID = 55;
	/**
	 * 错误码
	 */
	/*@ApiModelProperty(value = "错误码", required = true)
	private Integer code = 200;
	@ApiModelProperty(value = "数据", required = true)
	private T data;*/
	@ApiModelProperty(value = "当日充值笔数")
	private int recharge;
	@ApiModelProperty(value = "当日充值数量")
	private int recharge_num;
	@ApiModelProperty(value = "钻石消耗")
	private int diamondexpend;
	@ApiModelProperty(value = "当日私房次数")
	private int confidential;
	@ApiModelProperty(value = "上游开房次数")
	private int thirteen;
	@ApiModelProperty(value = "激k开房次数")
	private int fivecard;
	@ApiModelProperty(value = "鱼虾蟹开房次数")
	private int fish;


	/**
	 * 业务码
	 */
/*	@ApiModelProperty(value = "业务码")
	private Integer bizCode;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}*/

	public Integer getRecharge() {
		return recharge;
	}

	public void setRecharge(Integer recharge) {
		this.recharge = recharge;
	}

	public Integer getRecharge_num() {
		return recharge_num;
	}

	public void setRecharge_num(Integer recharge_num) {
		this.recharge_num = recharge_num;
	}

	public Integer getDiamondexpend() {
		return diamondexpend;
	}

	public void setDiamondexpend(Integer diamondexpend) {
		this.diamondexpend = diamondexpend;
	}

	public Integer getConfidential() {
		return confidential;
	}

	public void setConfidential(Integer confidential) {
		this.confidential = confidential;
	}

	public Integer getThirteen() {
		return thirteen;
	}

	public void setThirteen(Integer thirteen) {
		this.thirteen = thirteen;
	}

	public Integer getFivecard() {
		return fivecard;
	}

	public void setFivecard(Integer fivecard) {
		this.fivecard = fivecard;
	}

	public Integer getFish() {
		return fish;
	}

	public void setFish(Integer fish) {
		this.fish = fish;
	}

	@Override
	public String toString() {
		return "Result{" +
				"recharge=" + recharge +
				", recharge_num=" + recharge_num +
				", diamondexpend=" + diamondexpend +
				", confidential=" + confidential +
				", thirteen=" + thirteen +
				", fivecard=" + fivecard +
				", fish=" + fish +
				'}';
	}
}

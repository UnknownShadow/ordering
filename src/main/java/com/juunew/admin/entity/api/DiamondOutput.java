package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class DiamondOutput<T> implements Serializable {

	private static final long serialVersionUID = 55;

	@ApiModelProperty(value = "日常赛产出")
	private int daily_output;
	@ApiModelProperty(value = "签到产出")
	private int signin_output;
	@ApiModelProperty(value = "宝箱产出")
	private int treasure_output;
	@ApiModelProperty(value = "消息奖励")
	private int msg_reward;
	@ApiModelProperty(value = "签到次数")
	private int signin_count;
	@ApiModelProperty(value = "开宝箱次数")
	private int opentreasure_count;
	@ApiModelProperty(value = "消息领取次数")
	private int msgreceive_count;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getDaily_output() {
		return daily_output;
	}

	public void setDaily_output(int daily_output) {
		this.daily_output = daily_output;
	}

	public int getSignin_output() {
		return signin_output;
	}

	public void setSignin_output(int signin_output) {
		this.signin_output = signin_output;
	}

	public int getTreasure_output() {
		return treasure_output;
	}

	public void setTreasure_output(int treasure_output) {
		this.treasure_output = treasure_output;
	}

	public int getMsg_reward() {
		return msg_reward;
	}

	public void setMsg_reward(int msg_reward) {
		this.msg_reward = msg_reward;
	}

	public int getSignin_count() {
		return signin_count;
	}

	public void setSignin_count(int signin_count) {
		this.signin_count = signin_count;
	}

	public int getOpentreasure_count() {
		return opentreasure_count;
	}

	public void setOpentreasure_count(int opentreasure_count) {
		this.opentreasure_count = opentreasure_count;
	}

	public int getMsgreceive_count() {
		return msgreceive_count;
	}

	public void setMsgreceive_count(int msgreceive_count) {
		this.msgreceive_count = msgreceive_count;
	}

	@Override
	public String toString() {
		return "DiamondOutput{" +
				"daily_output=" + daily_output +
				", signin_output=" + signin_output +
				", treasure_output=" + treasure_output +
				", msg_reward=" + msg_reward +
				", signin_count=" + signin_count +
				", opentreasure_count=" + opentreasure_count +
				", msgreceive_count=" + msgreceive_count +
				'}';
	}
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class Consume<T> implements Serializable {

	private static final long serialVersionUID = 55;

	@ApiModelProperty(value = "开房消耗总量")
	private int room_consume;
	@ApiModelProperty(value = "开宝箱钻石消耗总量")
	private int opencase;
	@ApiModelProperty(value = "参赛钻石消耗总量")
	private int competition;
	@ApiModelProperty(value = "神秘宝箱开启次数")
	private int secretcase_open;
	@ApiModelProperty(value = "神秘宝箱钻石消耗总量")
	private int secretcase_diamondconsume;
	@ApiModelProperty(value = "银宝箱开启次数")
	private int silverycase_open;
	@ApiModelProperty(value = "银宝箱钻石消耗总量")
	private int silverycase_consume;
	@ApiModelProperty(value = "金宝箱开启次数")
	private int goldencase_open;
	@ApiModelProperty(value = "金宝箱消耗总量")
	private int goldencase_consume;
	@ApiModelProperty(value = "上游 报名次数")
	private int actionthirteen_onroll;
	@ApiModelProperty(value = "激K 报名次数")
	private int actionfivecard_onroll;
	@ApiModelProperty(value = "鱼虾蟹 报名次数")
	private int actionfish_onroll;
	@ApiModelProperty(value = "比赛 报名次数")
	private int actionthirteencompetition_onroll;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public int getRoom_consume() {
		return room_consume;
	}

	public void setRoom_consume(int room_consume) {
		this.room_consume = room_consume;
	}

	public int getOpencase() {
		return opencase;
	}

	public void setOpencase(int opencase) {
		this.opencase = opencase;
	}

	public int getCompetition() {
		return competition;
	}

	public void setCompetition(int competition) {
		this.competition = competition;
	}

	public int getSecretcase_open() {
		return secretcase_open;
	}

	public void setSecretcase_open(int secretcase_open) {
		this.secretcase_open = secretcase_open;
	}

	public int getSecretcase_diamondconsume() {
		return secretcase_diamondconsume;
	}

	public void setSecretcase_diamondconsume(int secretcase_diamondconsume) {
		this.secretcase_diamondconsume = secretcase_diamondconsume;
	}

	public int getSilverycase_open() {
		return silverycase_open;
	}

	public void setSilverycase_open(int silverycase_open) {
		this.silverycase_open = silverycase_open;
	}

	public int getSilverycase_consume() {
		return silverycase_consume;
	}

	public void setSilverycase_consume(int silverycase_consume) {
		this.silverycase_consume = silverycase_consume;
	}

	public int getGoldencase_open() {
		return goldencase_open;
	}

	public void setGoldencase_open(int goldencase_open) {
		this.goldencase_open = goldencase_open;
	}

	public int getGoldencase_consume() {
		return goldencase_consume;
	}

	public void setGoldencase_consume(int goldencase_consume) {
		this.goldencase_consume = goldencase_consume;
	}

	public int getActionthirteen_onroll() {
		return actionthirteen_onroll;
	}

	public void setActionthirteen_onroll(int actionthirteen_onroll) {
		this.actionthirteen_onroll = actionthirteen_onroll;
	}

	public int getActionfivecard_onroll() {
		return actionfivecard_onroll;
	}

	public void setActionfivecard_onroll(int actionfivecard_onroll) {
		this.actionfivecard_onroll = actionfivecard_onroll;
	}

	public int getActionfish_onroll() {
		return actionfish_onroll;
	}

	public void setActionfish_onroll(int actionfish_onroll) {
		this.actionfish_onroll = actionfish_onroll;
	}

	public int getActionthirteencompetition_onroll() {
		return actionthirteencompetition_onroll;
	}

	public void setActionthirteencompetition_onroll(int actionthirteencompetition_onroll) {
		this.actionthirteencompetition_onroll = actionthirteencompetition_onroll;
	}
}

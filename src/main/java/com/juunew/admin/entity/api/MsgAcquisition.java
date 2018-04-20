package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class MsgAcquisition<T> implements Serializable {


	@ApiModelProperty(value = "消息内容")
	private String content;
	@ApiModelProperty(value = "消息标题")
	private String title;
	@ApiModelProperty(value = "显示位置")
	private int display_position;
	@ApiModelProperty(value = "奖励类型")
	private int reward_type;
	@ApiModelProperty(value = "奖励数量")
	private int reward_number;
	@ApiModelProperty(value = "是否强制退出")
	private int sign_out;
	@ApiModelProperty(value = "开始时间")
	private String start_date;
	@ApiModelProperty(value = "开始时间")
	private int start_time;
	@ApiModelProperty(value = "read_status")
	private int read_status;
	@ApiModelProperty(value = "token")
	private String token;
	@ApiModelProperty(value = "ID")
	private int id;
	@ApiModelProperty(value = "reward_id")
	private int reward_id;
	@ApiModelProperty(value = "当前页")
	private int page;
	@ApiModelProperty(value = "一次获取多少条")
	private int limit;
	@ApiModelProperty(value = "消息是否已经过期；0（默认）没过期；1：已过期")
	private int expired;
	@ApiModelProperty(value = "cmd")
	private int cmd;

	@Override
	public String toString() {
		return "MsgRequest{" +
				"content='" + content + '\'' +
				", title='" + title + '\'' +
				", display_position=" + display_position +
				", reward_type=" + reward_type +
				", reward_number=" + reward_number +
				", sign_out=" + sign_out +
				", start_date='" + start_date + '\'' +
				", start_time=" + start_time +
				", read_status=" + read_status +
				", token='" + token + '\'' +
				", id=" + id +
				", reward_id=" + reward_id +
				", page=" + page +
				", limit=" + limit +
				", expired=" + expired +
				", cmd=" + cmd +
				'}';
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDisplay_position() {
		return display_position;
	}

	public void setDisplay_position(int display_position) {
		this.display_position = display_position;
	}

	public int getReward_type() {
		return reward_type;
	}

	public void setReward_type(int reward_type) {
		this.reward_type = reward_type;
	}

	public int getReward_number() {
		return reward_number;
	}

	public void setReward_number(int reward_number) {
		this.reward_number = reward_number;
	}

	public int getSign_out() {
		return sign_out;
	}

	public void setSign_out(int sign_out) {
		this.sign_out = sign_out;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRead_status() {
		return read_status;
	}

	public void setRead_status(int read_status) {
		this.read_status = read_status;
	}

	public int getReward_id() {
		return reward_id;
	}

	public void setReward_id(int reward_id) {
		this.reward_id = reward_id;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart_time() {
		return start_time;
	}
}

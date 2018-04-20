package com.juunew.admin.entity;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class MsgResponse<T> implements Serializable {

	@ApiModelProperty(value = "cmd")
	private int cmd;
	@ApiModelProperty(value = "json体，根据cmd值定")
	private String json;
	@ApiModelProperty(value = "消息内容")
	private String content;
	@ApiModelProperty(value = "消息标题")
	private String title;
	@ApiModelProperty(value = "显示位置")
	private int display_position;
	@ApiModelProperty(value = "开始时间")
	private String start_date;
	@ApiModelProperty(value = "开始时间")
	private int start_time;
	@ApiModelProperty(value = "读取状态")
	private int read_status;
	@ApiModelProperty(value = "msg_id")
	private int msg_id;
	@ApiModelProperty(value = "消息是否已经过期；0（默认）没过期；1：已过期")
	private int expired;

	@Override
	public String toString() {
		return "MsgResponse{" +
				"cmd=" + cmd +
				", json='" + json + '\'' +
				", content='" + content + '\'' +
				", title='" + title + '\'' +
				", display_position=" + display_position +
				", start_date='" + start_date + '\'' +
				", start_time=" + start_time +
				", read_status=" + read_status +
				", msg_id=" + msg_id +
				", expired=" + expired +
				'}';
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
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

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public int getStart_time() {
		return start_time;
	}

	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}

	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public int getRead_status() {
		return read_status;
	}

	public void setRead_status(int read_status) {
		this.read_status = read_status;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}

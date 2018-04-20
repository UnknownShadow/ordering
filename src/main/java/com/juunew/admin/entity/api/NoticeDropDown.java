package com.juunew.admin.entity.api;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class NoticeDropDown<T> implements Serializable {

	private static final long serialVersionUID = 55;

	@ApiModelProperty(value = "公告内容")
	private String content;
	@ApiModelProperty(value = "公告标题")
	private String title;
	@ApiModelProperty(value = "显示位置")
	private int display_position;
	@ApiModelProperty(value = "跳转地址")
	private String jump_url;
	@ApiModelProperty(value = "显示次数")
	private int times;
	@ApiModelProperty(value = "是否强制退出")
	private int exit;
	@ApiModelProperty(value = "ID")
	private int id;
	@ApiModelProperty(value = "公告开始时间")
	private int start_time;
	@ApiModelProperty(value = "公告类型")
	private int msg_type;
	@ApiModelProperty(value = "图片公告背景图")
	private String imgurl;
	@ApiModelProperty(value = "公告显示的页面")
	private int page_type;
	@ApiModelProperty(value = "token")
	private String token;
	/*@ApiModelProperty(value = "版本号")
	private String version;*/


	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getJump_url() {
		return jump_url;
	}

	public void setJump_url(String jump_url) {
		this.jump_url = jump_url;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getExit() {
		return exit;
	}

	public void setExit(int exit) {
		this.exit = exit;
	}

	public int getId() {
		return id;
	}

	public int getStart_time() {
		return start_time;
	}

	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public int getPage_type() {
		return page_type;
	}

	public void setPage_type(int page_type) {
		this.page_type = page_type;
	}


	@Override
	public String toString() {
		return "NoticeDropDown{" +
				"content='" + content + '\'' +
				", title='" + title + '\'' +
				", display_position=" + display_position +
				", jump_url='" + jump_url + '\'' +
				", times=" + times +
				", exit=" + exit +
				", id=" + id +
				", start_time=" + start_time +
				", msg_type=" + msg_type +
				", imgurl='" + imgurl + '\'' +
				", page_type=" + page_type +
				", token='" + token + '\'' +
				'}';
	}
}

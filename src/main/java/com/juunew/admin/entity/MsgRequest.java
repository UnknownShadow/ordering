package com.juunew.admin.entity;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class MsgRequest<T> implements Serializable {

	@ApiModelProperty(value = "token")
	private String token;
	@ApiModelProperty(value = "当前页")
	private int page;
	@ApiModelProperty(value = "一次获取多少条")
	private int limit;

	@Override
	public String toString() {
		return "MsgRequest{" +
				"token='" + token + '\'' +
				", page=" + page +
				", limit=" + limit +
				'}';
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
}

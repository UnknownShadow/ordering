package com.juunew.admin.entity;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class MsgsRequest<T> implements Serializable {

	@ApiModelProperty(value = "token")
	private String token;
	@ApiModelProperty(value = "版本号")
	private int build;
	@ApiModelProperty(value = "当前第几页")
	private int page;
	@ApiModelProperty(value = "请求条数")
	private int limit;


	@Override
	public String toString() {
		return "MsgsRequest{" +
				"token='" + token + '\'' +
				", build=" + build +
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

	public int getBuild() {
		return build;
	}

	public void setBuild(int build) {
		this.build = build;
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

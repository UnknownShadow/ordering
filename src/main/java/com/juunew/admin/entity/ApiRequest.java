package com.juunew.admin.entity;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class ApiRequest<T> implements Serializable {

	@ApiModelProperty(value = "token")
	private String token;


	@Override
	public String toString() {
		return "ApiRequest{" +
				"token='" + token + '\'' +
				'}';
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


public class MsgReward<T> implements Serializable {


	@ApiModelProperty(value = "code：0成功；-1失败")
	private int code;
	@ApiModelProperty(value = "消息ID")
	private int Msg_id;
	@ApiModelProperty(value = "错误信息")
	private String errMsg;
	@ApiModelProperty(value = "token")
	private String token;

	@Override
	public String toString() {
		return "MsgReward{" +
				"code=" + code +
				", Msg_id=" + Msg_id +
				", errMsg='" + errMsg + '\'' +
				", token='" + token + '\'' +
				'}';
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getMsg_id() {
		return Msg_id;
	}

	public void setMsg_id(int msg_id) {
		Msg_id = msg_id;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

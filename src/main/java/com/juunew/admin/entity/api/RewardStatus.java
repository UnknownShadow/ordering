package com.juunew.admin.entity.api;

import com.juunew.admin.controller.BaseController;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class RewardStatus<T> implements Serializable {


	@ApiModelProperty(value = "code：0成功；-1失败")
	private int code;
	@ApiModelProperty(value = "消息ID")
	private int rewardStatus;
	@ApiModelProperty(value = "错误信息")
	private String errMsg;
	@ApiModelProperty(value = "token")
	private String token;
	@ApiModelProperty(value = "消息ID")
	private int msg_id;

	@Override
	public String toString() {
		return "RewardStatus{" +
				"code=" + code +
				", rewardStatus=" + rewardStatus +
				", errMsg='" + errMsg + '\'' +
				", token='" + token + '\'' +
				", msg_id=" + msg_id +
				'}';
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(int rewardStatus) {
		this.rewardStatus = rewardStatus;
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

	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}
}

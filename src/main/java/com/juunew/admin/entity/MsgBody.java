package com.juunew.admin.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


public class MsgBody<T> implements Serializable {

	@ApiModelProperty(value = "code：0成功；-1失败")
	private int code;
	@ApiModelProperty(value = "获取消息内容")
	private List<MsgResponse> data;
	@ApiModelProperty(value = "错误信息")
	private String errMsg;


	@Override
	public String toString() {
		return "MsgBody{" +
				"code=" + code +
				", data=" + data +
				", errMsg='" + errMsg + '\'' +
				'}';
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<MsgResponse> getData() {
		return data;
	}

	public void setData(List<MsgResponse> data) {
		this.data = data;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}

package com.juunew.admin.entity.api;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


public class Msg<T> implements Serializable {

	@ApiModelProperty(value = "code：0成功；-1失败")
	private int code;
	@ApiModelProperty(value = "获取消息内容")
	private List<MsgAcquisition> data;
	@ApiModelProperty(value = "错误信息")
	private String errMsg;


	@Override
	public String toString() {
		return "Msg{" +
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

	public List<MsgAcquisition> getData() {
		return data;
	}

	public void setData(List<MsgAcquisition> data) {
		this.data = data;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}

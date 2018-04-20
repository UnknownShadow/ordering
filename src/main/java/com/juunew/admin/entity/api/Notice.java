package com.juunew.admin.entity.api;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


public class Notice<T> implements Serializable {

	@ApiModelProperty(value = "code：0成功；-1失败")
	private int code;
	@ApiModelProperty(value = "获取公告内容")
	private List<NoticeDropDown> data;
	@ApiModelProperty(value = "错误信息")
	private String errMsg;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<NoticeDropDown> getData() {
		return data;
	}

	public void setData(List<NoticeDropDown> data) {
		this.data = data;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String toString() {
		return "Notice{" +
				"code=" + code +
				", data=" + data +
				", errMsg='" + errMsg + '\'' +
				'}';
	}
}

package com.juunew.admin.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;

import javax.json.JsonObject;
import java.io.Serializable;
import java.util.List;


public class MsgsResponse<T> implements Serializable {


	@ApiModelProperty(value = "消息体")
	private JSONObject content;



	public JSONObject getContent() {
		return content;
	}

	public void setContent(JSONObject content) {
		this.content = content;
	}
}

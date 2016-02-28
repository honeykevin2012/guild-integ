package com.game.common.basics;

import java.util.Collections;

import net.sf.json.JSONObject;

public class JsonResult {
	private static final String BLANK_STR = "";
	private static final String OK_STR = "ok";

	private String state = OK_STR;
	private String msg = BLANK_STR;
	private Object data = Collections.EMPTY_LIST;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public String toString(){
		return JSONObject.fromObject(this).toString();
	}
}

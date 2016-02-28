package com.game.common.basics;

public class PageResult implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6799267100848455212L;
	
	private static final String BLANK_STR = "";
	private static final String OK_STR 		= "ok";
	
	private String state = OK_STR;
	private String msg  = BLANK_STR;
	
	private Object data = null;

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
}

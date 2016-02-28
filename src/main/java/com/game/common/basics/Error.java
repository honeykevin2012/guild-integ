package com.game.common.basics;


public class Error {
	
	private static final String BLANK_STR = "";
	
	private String state	= BLANK_STR;
	private String msg 	= BLANK_STR;
	
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

}

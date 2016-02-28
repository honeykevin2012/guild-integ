package com.game.common.basics.cache;

import java.io.Serializable;
import java.util.Set;

public class CacheMessage implements Serializable {
	
	private static final long serialVersionUID = -8324599961517490806L;

	/**
	 * 消息类型 text(2001), image(2002), doc(2003), video(2004),menu(2005), message(2006);
	 */
	private int msgType; 
	
	/**
	 * 引用的信息表Id
	 */
	private int messageId;
	
	/**
	 * 消息体 JSON格式的数据
	 */
	private String msg;
	
	/**
	 * 推送的mac列表
	 */
	private Set<String> macs;

	public CacheMessage() {	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Set<String> getMacs() {
		return macs;
	}

	public void setMacs(Set<String> macs) {
		this.macs = macs;
	}

}

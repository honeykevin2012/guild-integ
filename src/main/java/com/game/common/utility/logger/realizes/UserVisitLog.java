package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 用户访问
 * @author kevin
 *
 */
public class UserVisitLog extends BaseLogger{
	
	private String userId;
	private String url;
	public UserVisitLog() {
		super("UserVisitLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_UserVisit").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(url).append(this.append);//当前地址
	    //公共参数
	    buffer.append(this.baseParameter());
	    return buffer.toString();
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}

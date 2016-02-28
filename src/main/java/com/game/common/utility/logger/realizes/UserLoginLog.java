package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 用户登录
 * @author kevin
 *
 */
public class UserLoginLog extends BaseLogger{
	
	private String userId;
	public UserLoginLog() {
		super("UserLoginLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_UserLogin").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
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

	
}

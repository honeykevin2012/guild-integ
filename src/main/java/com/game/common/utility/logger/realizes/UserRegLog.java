package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 用户注册
 * @author kevin
 *
 */
public class UserRegLog extends BaseLogger{
	
	private String userId;
	private String userName;
	private String email;
	public UserRegLog() {
		super("UserRegLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_UserReg").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userName).append(this.append);//用户名
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(email).append(this.append);//邮箱地址
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

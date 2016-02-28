package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.UserLoginLogDao;



/**
 * 用户登录
 * @author kevin
 *
 */
@Document(collection = "user_login_log")
public class UserLoginLog extends BaseLogger{
	
	private String userId;
	@Override
	public void create() {
		UserLoginLogDao logger = new UserLoginLogDao();
		logger.save(this);
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}

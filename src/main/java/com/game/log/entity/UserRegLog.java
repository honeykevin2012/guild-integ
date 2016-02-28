package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.UserRegLogDao;


/**
 * 用户注册
 * @author kevin
 *
 */
@Document(collection = "user_reg_log")
public class UserRegLog extends BaseLogger{
	
	private String userId;
	private String userName;
	private String email;
	@Override
	public void create() {
		UserRegLogDao logger = new UserRegLogDao();
		logger.save(this);
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

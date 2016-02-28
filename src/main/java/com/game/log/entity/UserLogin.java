package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.UserLoggerDao;

@Document(collection = "user_login")
public class UserLogin extends BaseLogger{
	private String data;// 请求参数RSA加密密文

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public void create(){
		UserLoggerDao logger = new UserLoggerDao();
		logger.save(this);
	}
}

package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.UserRegisterDao;

@Document(collection = "user_register")
public class UserRegister extends BaseLogger {
	
	@Override
	public void create() {
		UserRegisterDao dao = new UserRegisterDao();
		dao.save(this);
	}
}

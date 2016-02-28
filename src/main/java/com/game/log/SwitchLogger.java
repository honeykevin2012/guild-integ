package com.game.log;

import com.game.common.BaseForm;
import com.game.common.utility.CommonUtils;
import com.game.log.entity.UserLogin;
import com.game.log.entity.UserRegister;

public class SwitchLogger {
	private static final String LOGGER_LOGIN = "login";
	private static final String LOGGER_REGISTER = "login";
	
	public static void create(String methodName, BaseForm form){
		if(LOGGER_LOGIN.equals(methodName)){
			UserLogin logger = new UserLogin();
			CommonUtils.copyProperties(form, logger);
			logger.create();
		}else if(LOGGER_REGISTER.equals(methodName)){
			UserRegister logger = new UserRegister();
			CommonUtils.copyProperties(form, logger);
			logger.create();
			
		}
	}
}

package com.game.user.domain.form;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class UserCenterForm extends BaseForm{
	@NotEmpty(message = "error#用户token不能为空.")
	private String token;//校验token

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
;
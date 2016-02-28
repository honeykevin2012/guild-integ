package com.game.user.domain;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class CreatorOrderVO extends BaseForm {
	@NotEmpty(message = "error#非法请求.")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

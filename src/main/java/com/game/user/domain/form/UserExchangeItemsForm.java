package com.game.user.domain.form;

import javax.validation.constraints.NotNull;

import com.game.common.BaseForm;

public class UserExchangeItemsForm extends BaseForm {
	@NotNull(message = "error#用户ID不能为空.")
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

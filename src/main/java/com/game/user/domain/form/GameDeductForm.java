package com.game.user.domain.form;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class GameDeductForm extends BaseForm{
	@NotEmpty(message = "error#用户id不能为空.")
	private String userId;
	@NotEmpty(message = "error#订单号不能为空.")
	private String orderId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	
}

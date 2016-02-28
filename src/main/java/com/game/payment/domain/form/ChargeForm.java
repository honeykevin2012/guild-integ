package com.game.payment.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class ChargeForm extends BaseForm {
	@NotEmpty(message = "error#用户名为空.")
	private String userName;// ：通行证ID

	@NotNull(message = "error#状态为空.")
	private Integer status;// :支付状态 0失败 1成功 2处理中

	private String startDate;
	private String endDate;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}

package com.game.guild.domain.form;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class GuildStockMgrMoveForm extends BaseForm {

	@NotEmpty(message = "error#生效时间不能为空.")
	private String startTime;

	@NotEmpty(message = "error#失效时间不能为空.")
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}

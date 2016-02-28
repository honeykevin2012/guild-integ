package com.game.guild.domain.form;

import com.game.common.BaseForm;

public class GuildRecommendForm extends BaseForm {
	
	private Integer userId;//
	private Integer limit;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}

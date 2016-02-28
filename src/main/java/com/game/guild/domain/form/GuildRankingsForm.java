package com.game.guild.domain.form;

import javax.validation.constraints.NotNull;

import com.game.common.BaseForm;

public class GuildRankingsForm extends BaseForm {
	@NotNull(message = "error#用户id不能为空.")
	private Integer userId=0;//
	private Integer limit;
	private String searchText;
	
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
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
}

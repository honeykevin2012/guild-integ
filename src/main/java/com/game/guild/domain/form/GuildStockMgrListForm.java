package com.game.guild.domain.form;

import javax.validation.constraints.NotNull;

import com.game.common.BaseForm;

public class GuildStockMgrListForm extends BaseForm{

	@NotNull(message = "error#token不能为空.")
	private String token;//ID
	
	@NotNull(message = "error#分页编号不能为空.")
	private Integer pn;//是否虚拟物品
	
	@NotNull(message = "error#分页大小不能为空.")
	private Integer ps;//是否虚拟物品



	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public Integer getPs() {
		return ps;
	}

	public void setPs(Integer ps) {
		this.ps = ps;
	}

	
	
}

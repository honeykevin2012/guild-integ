package com.game.guild.domain.form;

import javax.validation.constraints.NotNull;

public class GuildStockForm{

	@NotNull(message = "error#公会id不能为空.")
	private Integer guildId;//ID

	
	@NotNull(message = "error#分页编号不能为空.")
	private Integer pn;//是否虚拟物品
	
	@NotNull(message = "error#分页大小不能为空.")
	private Integer ps;//是否虚拟物品

	public Integer getGuildId() {
		return guildId;
	}

	public void setGuildId(Integer guildId) {
		this.guildId = guildId;
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

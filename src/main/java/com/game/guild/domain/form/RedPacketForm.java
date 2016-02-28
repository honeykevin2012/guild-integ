package com.game.guild.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class RedPacketForm extends BaseForm {
	
	@NotEmpty(message = "error#名称为空.")
	private String name;//
	@NotNull(message = "error#总金额不能为空.")
	private Long totalMoney;//
	@NotNull(message = "error#总人数不能为空.")
	private Integer totalPeople;//
	@NotNull(message = "error#用户id不能为空.")
	private Integer userId;//
	@NotNull(message = "error#公会id不能为空.")
	private Integer guildId;//
	public Long getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Integer getTotalPeople() {
		return totalPeople;
	}
	public void setTotalPeople(Integer totalPeople) {
		this.totalPeople = totalPeople;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGuildId() {
		return guildId;
	}
	public void setGuildId(Integer guildId) {
		this.guildId = guildId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

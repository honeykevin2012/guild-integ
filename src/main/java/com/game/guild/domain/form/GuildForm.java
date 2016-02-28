package com.game.guild.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class GuildForm extends BaseForm{
	
	@NotEmpty(message="error#公会名称不能为空.")
	@Length(min = 2, max = 20, message = "公会名字数在2-20之间")
	private String guildName;
	@Length(min = 5, max = 200, message = "公会宣言字数在5-200之间")
	private String slogan;
	@Length(min = 0, max = 300, message = "公会描述最多300字")
	private String remark;
	private String icon;
	@NotNull(message="error#用户编号不能为空.")
	private Integer userId;

	public String getGuildName() {
		return guildName;
	}

	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

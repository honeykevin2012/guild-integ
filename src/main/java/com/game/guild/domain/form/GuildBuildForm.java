package com.game.guild.domain.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class GuildBuildForm extends BaseForm {
	@NotEmpty(message = "error#用户token不能为空.")
	private String token;//校验token
	private String guildName;
	@NotEmpty(message = "error#公会宣言不能为空.")
	@Length(min = 5, max = 200, message = "公会宣言字数在5-200之间")
	private String slogan;
	@Length(min = 0, max = 300, message = "公会描述最多300字")
	private String remark;
	private String icon;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

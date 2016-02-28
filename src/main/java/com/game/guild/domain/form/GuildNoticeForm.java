package com.game.guild.domain.form;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class GuildNoticeForm extends BaseForm{

	@NotEmpty(message = "error#公告标题不能为空.")
	private String title;//标题
	@NotEmpty(message = "error#公告内容不能为空.")
	private String content;//内容

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	
}

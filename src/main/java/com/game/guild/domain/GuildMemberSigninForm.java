/*
 * @athor zhaolianjun
 * created on 2015-03-13
 */

package com.game.guild.domain;

import javax.validation.constraints.NotNull;

import com.game.common.BaseForm;

/**
 * @desc:公会成员签到 <br>
 * @author zhaolianjun
 */

public class GuildMemberSigninForm extends BaseForm{

	@NotNull(message="error#TOKEN不能为空")
	private String token;// 用户ID

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

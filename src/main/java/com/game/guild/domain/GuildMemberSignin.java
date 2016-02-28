/*
 * @athor zhaolianjun
 * created on 2015-03-13
 */

package com.game.guild.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:公会成员签到 <br>
 * @author zhaolianjun
 */

public class GuildMemberSignin extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// id
	private Integer userId;// 用户ID
	private Integer guildId;// 公会ID

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date signinTime;// 签到时间
	private String queryDate;//查询日期条件（临时）

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGuildId() {
		return this.guildId;
	}

	public void setGuildId(Integer guildId) {
		this.guildId = guildId;
	}

	public Date getSigninTime() {
		return this.signinTime;
	}

	public void setSigninTime(Date signinTime) {
		this.signinTime = signinTime;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
}

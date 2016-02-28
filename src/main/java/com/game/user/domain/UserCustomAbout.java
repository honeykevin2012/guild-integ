/*
 * @athor zhaolianjun
 * created on 2015-01-27
 */

package com.game.user.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:客服联系方式 <br>
 * @author zhaolianjun
 */

public class UserCustomAbout extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;// id
	private String gameId;// 游戏编号
	private String qq;// QQ群
	private String tel;// 电话
	private String email;// 邮箱
	private String remark;// 描述

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGameId() {
		return this.gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

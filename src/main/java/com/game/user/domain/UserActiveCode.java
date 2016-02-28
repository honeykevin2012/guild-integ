/*
 * @athor zhaolianjun
 * created on 2015-01-07
 */

package com.game.user.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:激活码管理 <br>
 * @author zhaolianjun
 */

public class UserActiveCode extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// id

	private String code;// 激活码

	private String isUsed;// 是否被使用

	private String gameId;// 游戏编号

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getGameId() {
		return this.gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}

package com.game.user.domain;

import java.util.ArrayList;
import java.util.List;

public class UserGameResult {
	private Integer userId;// 用户编号
	private Long gameAmount;// 用户游戏中总金币数
	private Integer gameId;// code
	private String gameName;// name
	private String gameIcon;// icon
	private String sitePageUrl;//
	private Double exchangeDivide;//兑换比例0.000
	private Long platAmount;//转换成最后的平台币金额(amount*exchangeDivide)
	private String roleServAppend;//数据分组拼接字符串roleId,roleName,serverId,serverName, amount
	private List<RoleVo> roles = new ArrayList<RoleVo>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameIcon() {
		return gameIcon;
	}

	public void setGameIcon(String gameIcon) {
		this.gameIcon = gameIcon;
	}

	public Double getExchangeDivide() {
		return exchangeDivide;
	}

	public void setExchangeDivide(Double exchangeDivide) {
		this.exchangeDivide = exchangeDivide;
	}

	public Long getGameAmount() {
		return gameAmount;
	}

	public void setGameAmount(Long gameAmount) {
		this.gameAmount = gameAmount;
	}

	public Long getPlatAmount() {
		return platAmount;
	}

	public void setPlatAmount(Long platAmount) {
		this.platAmount = platAmount;
	}

	public String getRoleServAppend() {
		return roleServAppend;
	}

	public void setRoleServAppend(String roleServAppend) {
		this.roleServAppend = roleServAppend;
	}

	public List<RoleVo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVo> roles) {
		this.roles = roles;
	}

	public String getSitePageUrl() {
		return sitePageUrl;
	}

	public void setSitePageUrl(String sitePageUrl) {
		this.sitePageUrl = sitePageUrl;
	}
}

package com.game.sync;

public class GameResult {
	private String gameId;
	private String userId;//
	private String serverId;
	private String roleId;
	private String roleName;
	private String amount;
	private String platformAmount;//nb gold
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getPlatformAmount() {
		return platformAmount;
	}

	public void setPlatformAmount(String platformAmount) {
		this.platformAmount = platformAmount;
	}
}

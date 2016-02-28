package com.game.user.domain;

public class RoleVo {
	private String roleId;
	private String roleName;
	private String serverId;
	private String serverName;
	private String gameAmount;//游戏金币
	private String platAmount;//平台金币

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

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getGameAmount() {
		return gameAmount;
	}

	public void setGameAmount(String gameAmount) {
		this.gameAmount = gameAmount;
	}

	public String getPlatAmount() {
		return platAmount;
	}

	public void setPlatAmount(String platAmount) {
		this.platAmount = platAmount;
	}

	public static void main(String[] args) {
		String[] s = "1001#$#哈哈#$#101#$#星际#$#360#$$$#1002#$#嘿嘿#$#101#$#222#$#1067".split("\\#\\$\\#");
		System.out.println(s.length);
	}
}

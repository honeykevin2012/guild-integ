package com.game.sync;

import java.util.List;

public class ViewResult {
	private long totalPlatformAmount;
	private String gameId;
	private String gameName;
	private String gameIcon;
	private List<ViewRoleResult> roles;

	public long getTotalPlatformAmount() {
		return totalPlatformAmount;
	}

	public void setTotalPlatformAmount(long totalPlatformAmount) {
		this.totalPlatformAmount = totalPlatformAmount;
	}

	public List<ViewRoleResult> getRoles() {
		return roles;
	}

	public void setRoles(List<ViewRoleResult> roles) {
		this.roles = roles;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
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
}

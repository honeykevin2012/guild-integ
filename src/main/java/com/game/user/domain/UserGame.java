/*
 * @athor zhaolianjun
 * created on 2015-04-22
 */

package com.game.user.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:用户在玩的游戏 <br>
 * @author zhaolianjun
 */

public class UserGame extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// id
	private Integer gameId;// 游戏编码
	private Integer userId;// 用户编号
	private Long amount;// 用户游戏中金币数
	private String roleId;// 角色ID
	private String roleName;// 角色名
	private String serverId;// 服务器ID
	private String serverName;// 服务器名称
	private Long platAmount;//nb
	private String gameName;
	private Double exchangeDivide;//游戏汇率

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;// 最后登录时间

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLogoutTime;// 最后退出时间

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getPlatAmount() {
		return platAmount;
	}

	public void setPlatAmount(Long platAmount) {
		this.platAmount = platAmount;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getServerId() {
		return this.serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLogoutTime() {
		return this.lastLogoutTime;
	}

	public void setLastLogoutTime(Date lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Double getExchangeDivide() {
		return exchangeDivide;
	}

	public void setExchangeDivide(Double exchangeDivide) {
		this.exchangeDivide = exchangeDivide;
	}

	@Override
	public String toString() {
		return "UserGame [id=" + id + ", gameId=" + gameId + ", userId=" + userId + ", amount=" + amount + ", roleId=" + roleId + ", roleName=" + roleName + ", serverId=" + serverId + ", serverName="
				+ serverName + ", platAmount=" + platAmount + ", gameName=" + gameName + ", exchangeDivide=" + exchangeDivide + ", lastLoginTime=" + lastLoginTime + ", lastLogoutTime="
				+ lastLogoutTime + "]";
	}
}

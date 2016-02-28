package com.game.user.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class UserGameForm extends BaseForm {
	@NotNull(message = "用户编号不能为空.")
	private Integer userId;// 用户编号
	@NotNull(message = "角色游戏币数量不能为空.")
	private Long amount;// 用户游戏中金币数
	@NotEmpty(message = "角色编号不能为空.")
	private String roleId;// 角色ID
	@NotEmpty(message = "角色名称不能为空.")
	private String roleName;// 角色名
	@NotEmpty(message = "服务器编号不能为空.")
	private String serverId;// 服务器ID
	@NotEmpty(message = "服务器名称不能为空.")
	private String serverName;// 服务器ID

	public Integer getUserId() {
		return userId;
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
}

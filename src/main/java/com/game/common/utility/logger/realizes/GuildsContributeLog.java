package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 公会捐献
 * @author kevin
 *
 */
public class GuildsContributeLog extends BaseLogger{
	
	private String userId;
	private String guildName;
	private String guildId;
	private String gameName;
	private String roleInfo;
	private String amount;
	public GuildsContributeLog() {
		super("GuildsContributeLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_GuildsContribute").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(guildId).append(this.append);//公会id
	    buffer.append(gameName).append(this.append);//游戏名称
	    buffer.append(roleInfo).append(this.append);//角色服务器信息
	    buffer.append(amount).append(this.append);//金额
	    //公共参数
	    buffer.append(this.baseParameter());
	    return buffer.toString();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGuildName() {
		return guildName;
	}

	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(String roleInfo) {
		this.roleInfo = roleInfo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}

package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsContributeLogDao;

/**
 * 公会捐献
 * @author kevin
 *
 */
@Document(collection = "guilds_contribute_log")
public class GuildsContributeLog extends BaseLogger{
	
	private String userId;
	private String guildName;
	private String guildId;
	private String gameName;
	private String roleInfo;
	private String amount;

	@Override
	public void create() {
		GuildsContributeLogDao logger = new GuildsContributeLogDao();
		logger.save(this);
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

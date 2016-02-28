package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsCreateLogDao;

/**
 * 公会创建
 * @author kevin
 *
 */
@Document(collection = "guilds_create_log")
public class GuildsCreateLog extends BaseLogger{
	
	private String userId;
	private String guildName;
	private String guildId;

	@Override
	public void create() {
		GuildsCreateLogDao logger = new GuildsCreateLogDao();
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

	
}

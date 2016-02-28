package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsDissolveLogDao;


/**
 * 公会解散
 * @author kevin
 *
 */
@Document(collection = "guilds_dissolve_log")
public class GuildsDissolveLog extends BaseLogger{
	
	private String userId;
	private String guildName;
	private String guildId;
	@Override
	public void create() {
		GuildsDissolveLogDao logger = new GuildsDissolveLogDao();
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

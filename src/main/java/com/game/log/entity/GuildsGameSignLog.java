package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsGameSignLogDao;


/**
 * 游戏签到
 * @author kevin
 *
 */
@Document(collection = "guilds_gameSign_log")
public class GuildsGameSignLog extends BaseLogger{
	
	private String userId;
	private String guildName;
	private String guildId;
	private String exp;
	@Override
	public void create() {
		GuildsGameSignLogDao logger = new GuildsGameSignLogDao();
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

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	
}

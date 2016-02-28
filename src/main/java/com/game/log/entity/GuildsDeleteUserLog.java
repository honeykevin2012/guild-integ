package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsDeleteUserLogDao;


/**
 * 公会踢人
 * @author kevin
 *
 */
@Document(collection = "guilds_deleteUser_log")
public class GuildsDeleteUserLog extends BaseLogger{
	
	private String optUserId;
	private String delUserId;
	private String guildName;
	private String guildId;
	@Override
	public void create() {
		GuildsDeleteUserLogDao logger = new GuildsDeleteUserLogDao();
		logger.save(this);
	}



	public String getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(String optUserId) {
		this.optUserId = optUserId;
	}



	public String getDelUserId() {
		return delUserId;
	}

	public void setDelUserId(String delUserId) {
		this.delUserId = delUserId;
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

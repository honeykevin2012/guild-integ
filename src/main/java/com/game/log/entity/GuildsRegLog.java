package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsRegLogDao;


/**
 * 公会申请加入
 * @author kevin
 *
 */
@Document(collection = "guilds_reg_log")
public class GuildsRegLog extends BaseLogger{
	
	private String userId;
	private String guildId;
	@Override
	public void create() {
		GuildsRegLogDao logger = new GuildsRegLogDao();
		logger.save(this);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	
}

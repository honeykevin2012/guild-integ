package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsRegConfirmLogDao;


/**
 * 公会同意加入
 * @author kevin
 *
 */
@Document(collection = "guilds_regConfirm_log")
public class GuildsRegConfirmLog extends BaseLogger{
	
	private String optUserId;
	private String reqUserId;
	private String guildName;
	private String guildId;
	@Override
	public void create() {
		GuildsRegConfirmLogDao logger = new GuildsRegConfirmLogDao();
		logger.save(this);
	}


	public String getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(String optUserId) {
		this.optUserId = optUserId;
	}

	public String getReqUserId() {
		return reqUserId;
	}

	public void setReqUserId(String reqUserId) {
		this.reqUserId = reqUserId;
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

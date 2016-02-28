package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsRegRejectLogDao;


/**
 * 公会拒绝加入
 * @author kevin
 *
 */
@Document(collection = "guilds_regReject_log")
public class GuildsRegRejectLog extends BaseLogger{
	
	private String optUserId;
	private String reqUserId;
	private String guildName;
	private String guildId;
	@Override
	public void create() {
		GuildsRegRejectLogDao logger = new GuildsRegRejectLogDao();
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

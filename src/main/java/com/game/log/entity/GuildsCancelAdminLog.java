package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsCancelAdminLogDao;


/**
 * 公会取消管理员
 * @author kevin
 *
 */
@Document(collection = "guilds_cancelAdmin_log")
public class GuildsCancelAdminLog extends BaseLogger{
	
	
	private String optUserId;
	private String toUserId;
	private String guildId;





	@Override
	public void create() {
		GuildsCancelAdminLogDao logger = new GuildsCancelAdminLogDao();
		logger.save(this);
	}

	public String getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(String optUserId) {
		this.optUserId = optUserId;
	}




	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}


	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	
}

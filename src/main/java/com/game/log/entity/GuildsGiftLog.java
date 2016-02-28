package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.GuildsGiftLogDao;


/**
 * 公会赠送
 * @author kevin
 *
 */
@Document(collection = "guilds_gift_log")
public class GuildsGiftLog extends BaseLogger{
	
	private String optUserId;
	private String toUserId;
	private String guildName;
	private String guildId;
	private String itemName;
	private String itemCount;
	@Override
	public void create() {
		GuildsGiftLogDao logger = new GuildsGiftLogDao();
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
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

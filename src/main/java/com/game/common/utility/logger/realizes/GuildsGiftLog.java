package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 公会赠送
 * @author kevin
 *
 */
public class GuildsGiftLog extends BaseLogger{
	
	private String optUserId;
	private String toUserId;
	private String guildName;
	private String guildId;
	private String itemName;
	private String itemCount;
	public GuildsGiftLog() {
		super("GuildsGiftLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_GuildsGift").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(optUserId).append(this.append);//操作用户id
	    buffer.append(toUserId).append(this.append);//接收用户id
	    buffer.append(guildId).append(this.append);//公会id
	    buffer.append(itemName).append(this.append);//物品名称
	    buffer.append(itemCount).append(this.append);//物品数量
	    //公共参数
	    buffer.append(this.baseParameter());;
	    return buffer.toString();
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

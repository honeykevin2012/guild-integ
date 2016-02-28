package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 公会踢人
 * @author kevin
 *
 */
public class GuildsDeleteUserLog extends BaseLogger{
	
	private String optUserId;
	private String delUserId;
	private String guildName;
	private String guildId;
	public GuildsDeleteUserLog() {
		super("GuildsDeleteUserLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_GuildsDeleteUser").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(optUserId).append(this.append);//操作用户id
	    buffer.append(delUserId).append(this.append);//踢出用户id
	    buffer.append(guildId).append(this.append);//公会id
	    //公共参数
	    buffer.append(this.baseParameter());
	    return buffer.toString();
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

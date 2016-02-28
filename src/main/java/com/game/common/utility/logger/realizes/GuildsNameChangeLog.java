package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 公会创建
 * @author kevin
 *
 */
public class GuildsNameChangeLog extends BaseLogger{
	
	private String userId;
	private String guildName;
	private String guildId;
	public GuildsNameChangeLog() {
		super("GuildsNameChangeLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_GuildsNameChange").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(guildName).append(this.append);//公会名称
	    buffer.append(guildId).append(this.append);//公会id
	    //公共参数
	    buffer.append(this.baseParameter());
	    return buffer.toString();
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

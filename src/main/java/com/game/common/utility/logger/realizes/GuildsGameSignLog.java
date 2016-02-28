package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 游戏签到
 * @author kevin
 *
 */
public class GuildsGameSignLog extends BaseLogger{
	
	private String userId;
	private String guildName;
	private String guildId;
	private String exp;
	public GuildsGameSignLog() {
		super("GuildsGameSignLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_GuildsGameSign").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(guildId).append(this.append);//公会id
	    buffer.append(exp).append(this.append);//签到经验值
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

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	
}

package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 公会申请加入
 * @author kevin
 *
 */
public class GuildsRegLog extends BaseLogger{
	
	private String userId;
	private String guildId;
	public GuildsRegLog() {
		super("GuildsRegLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_GuildsReg").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//申请用户id
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

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	
}

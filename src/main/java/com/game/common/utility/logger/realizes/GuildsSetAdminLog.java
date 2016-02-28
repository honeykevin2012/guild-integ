package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 公会设置管理员
 * @author kevin
 *
 */
public class GuildsSetAdminLog extends BaseLogger{
	
	private String optUserId;
	private String toUserId;
	private String guildId;
	public GuildsSetAdminLog() {
		super("GuildsSetAdminLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_GuildsSetAdmin").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(optUserId).append(this.append);//操作用户id
	    buffer.append(toUserId).append(this.append);//用户id
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

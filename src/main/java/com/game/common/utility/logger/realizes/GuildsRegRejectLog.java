package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 公会拒绝加入
 * @author kevin
 *
 */
public class GuildsRegRejectLog extends BaseLogger{
	
	private String optUserId;
	private String reqUserId;
	private String guildName;
	private String guildId;
	public GuildsRegRejectLog() {
		super("GuildsRegRejectLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_GuildsRegReject").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(optUserId).append(this.append);//操作用户id
	    buffer.append(reqUserId).append(this.append);//申请用户id
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

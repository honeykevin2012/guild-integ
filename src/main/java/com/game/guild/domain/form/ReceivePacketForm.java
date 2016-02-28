package com.game.guild.domain.form;

import javax.validation.constraints.NotNull;

import com.game.common.BaseForm;

public class ReceivePacketForm extends BaseForm {
	
	@NotNull(message = "error#用戶不能为空.")
	private Integer userId;//
	@NotNull(message = "error#紅包id不能为空.")
	private Integer packetId;//

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPacketId() {
		return packetId;
	}
	public void setPacketId(Integer packetId) {
		this.packetId = packetId;
	}


}

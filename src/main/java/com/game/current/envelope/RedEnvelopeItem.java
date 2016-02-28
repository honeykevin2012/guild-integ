package com.game.current.envelope;

import java.util.Date;

public class RedEnvelopeItem {
	private Integer envelopeId;//红包ID
	private Integer amount;//金额
	private Integer userId;//用户ID
	private Date obtainedTime;//领取时间

	public Integer getEnvelopeId() {
		return envelopeId;
	}

	public void setEnvelopeId(Integer envelopeId) {
		this.envelopeId = envelopeId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getObtainedTime() {
		return obtainedTime;
	}

	public void setObtainedTime(Date obtainedTime) {
		this.obtainedTime = obtainedTime;
	}
}

package com.game.payment.utils;

public class PaymentConfig {
	private String channelId;
	private String paymentId;
	private String channelName;

	public PaymentConfig() {

	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public String toString() {
		return "PaymentConfig [channelId=" + channelId + ", paymentId="
				+ paymentId + ", channelName=" + channelName + "]";
	}

}
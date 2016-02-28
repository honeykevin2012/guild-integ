package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.PayMsgLogDao;


/**
 * 用户访问
 * @author kevin
 *
 */
@Document(collection = "pay_msg_log")
public class PayMsgLog extends BaseLogger{
	
	private String userId;
	private String orderId;
	private String orderAmount;
	private String payType;
	@Override
	public void create() {
		PayMsgLogDao logger = new PayMsgLogDao();
		logger.save(this);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}

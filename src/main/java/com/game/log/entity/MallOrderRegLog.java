package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.MallOrderRegLogDao;


/**
 * 用户访问
 * @author kevin
 *
 */
@Document(collection = "mall_orderReg_log")
public class MallOrderRegLog extends BaseLogger{
	
	private String userId;
	private String orderId;
	private String orderType;//1平台2公会
	private String count;//订单商品数量
	@Override
	public void create() {
		MallOrderRegLogDao logger = new MallOrderRegLogDao();
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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}


	
}

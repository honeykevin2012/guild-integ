package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.MallOrderGoodsLogDao;


/**
 * 用户访问
 * @author kevin
 *
 */
@Document(collection = "mall_orderGoods_log")
public class MallOrderGoodsLog extends BaseLogger{
	
	private String userId;
	private String orderId;
	private String productName;
	private String productCount;
	private String orderType;
	@Override
	public void create() {
		MallOrderGoodsLogDao logger = new MallOrderGoodsLogDao();
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	
}

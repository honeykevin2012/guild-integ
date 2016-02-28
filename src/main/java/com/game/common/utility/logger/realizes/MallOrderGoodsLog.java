package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 用户访问
 * @author kevin
 *
 */
public class MallOrderGoodsLog extends BaseLogger{
	
	private String userId;
	private String orderId;
	private String productName;
	private String productCount;
	private String orderType;
	public MallOrderGoodsLog() {
		super("MallOrderGoodsLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_MallOrderGoods").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(orderId).append(this.append);//订单号
	    buffer.append(productName).append(this.append);//商品名称
	    buffer.append(productCount).append(this.append);//商品数量
	    buffer.append(orderType).append(this.append);//订单类型1平台2公会
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

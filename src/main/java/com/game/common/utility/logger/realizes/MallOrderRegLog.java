package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 用户访问
 * @author kevin
 *
 */
public class MallOrderRegLog extends BaseLogger{
	
	private String userId;
	private String orderId;
	private String orderType;//1平台2公会
	private String count;//订单商品数量
	public MallOrderRegLog() {
		super("MallOrderRegLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_MallOrderReg").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(orderId).append(this.append);//订单号
	    buffer.append(orderType).append(this.append);//订单类型1平台2公会
	    buffer.append(count).append(this.append);//订单商品数量
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

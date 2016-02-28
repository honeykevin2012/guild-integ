package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 用户访问
 * @author kevin
 *
 */
public class MallGuildsOrderSubmitLog extends BaseLogger{
	
	private String userId;
	private String orderId;
	private String orderAmount;
	private String guildId;
	private String products;
	private String discountAmount;
	private String deliveryAmount;
	private String acceptName;
	private String mobile;
	private String province;
	private String city;
	private String area;
	private String address;
	public MallGuildsOrderSubmitLog() {
		super("MallGuildsOrderSubmitLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_MallGuildsOrderSubmit").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(orderId).append(this.append);//订单号
	    buffer.append(orderAmount).append(this.append);//订单金额
	    buffer.append(guildId).append(this.append);//付款公会id
	    buffer.append(products).append(this.append);//商品信息
	    buffer.append(discountAmount).append(this.append);//折扣
	    buffer.append(deliveryAmount).append(this.append);//邮费
	    buffer.append(acceptName).append(this.append);//收货人
	    buffer.append(mobile).append(this.append);//手机号
	    buffer.append(province).append(this.append);//省
	    buffer.append(city).append(this.append);//市
	    buffer.append(area).append(this.append);//县区
	    buffer.append(address).append(this.append);//详细地址
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

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(String deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



	
}

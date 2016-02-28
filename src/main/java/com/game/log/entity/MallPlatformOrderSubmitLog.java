package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.MallPlatformOrderSubmitLogDao;


/**
 * 用户访问
 * @author kevin
 *
 */
@Document(collection = "mall_platformOrderSubmit_log")
public class MallPlatformOrderSubmitLog extends BaseLogger{
	
	private String userId;
	private String orderId;
	private String orderAmount;
	private String products;
	private String discountAmount;
	private String deliveryAmount;
	private String acceptName;
	private String mobile;
	private String province;
	private String city;
	private String area;
	private String address;
	@Override
	public void create() {
		MallPlatformOrderSubmitLogDao logger = new MallPlatformOrderSubmitLogDao();
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

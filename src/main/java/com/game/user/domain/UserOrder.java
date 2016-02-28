/*
 * @athor zhaolianjun
 * created on 2015-01-29
 */

package com.game.user.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:订单 <br>
 * @author zhaolianjun
 */

public class UserOrder extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// 订单ID
	private String orderId;// 订单流水号
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payTime;// 付款时间
	private String status;// 订单状态
	private String type;// 订单类型
	private Integer deliveryId;// 配送类型
	private Integer payChannelId;// 支付方式
	private Integer userId;// 买家ID
	private Double totalAmount;// 总金额
	private Double payAmount;// 实际付款
	private Double discountAmount;// 减免费用
	private Long walletPayAmount;// 钱包支付金额
	private String isCostDelivery;// 是否免邮
	private Double deliveryAmount;// 邮费
	private Integer addressId;// 收货人地址ID
	private String ip;// 买家IP
	private String resource;// 交易来源(1网站2安卓3IOS)
	private String productOrderType;// 商品类型 1平台商品订单2公会商品订单
	private Integer guildId;// 付款公会ID

	private List<UserOrderItem> orderItems = new ArrayList<UserOrderItem>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDeliveryId() {
		return this.deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Integer getPayChannelId() {
		return this.payChannelId;
	}

	public void setPayChannelId(Integer payChannelId) {
		this.payChannelId = payChannelId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getIsCostDelivery() {
		return this.isCostDelivery;
	}

	public void setIsCostDelivery(String isCostDelivery) {
		this.isCostDelivery = isCostDelivery;
	}

	public Double getDeliveryAmount() {
		return this.deliveryAmount;
	}

	public void setDeliveryAmount(Double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getResource() {
		return this.resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getProductOrderType() {
		return productOrderType;
	}

	public void setProductOrderType(String productOrderType) {
		this.productOrderType = productOrderType;
	}

	public Integer getGuildId() {
		return guildId;
	}

	public void setGuildId(Integer guildId) {
		this.guildId = guildId;
	}

	public List<UserOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<UserOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Long getWalletPayAmount() {
		return walletPayAmount;
	}

	public void setWalletPayAmount(Long walletPayAmount) {
		this.walletPayAmount = walletPayAmount;
	}

	public String appendNames() {
		StringBuilder builder = new StringBuilder();
		for (UserOrderItem item : this.getOrderItems()) {
			builder.append("[").append(item.getProductName()).append("]\r\n");
		}
		return builder.toString();
	}

	public static class OrderConsValue {
		public static final String PERSON_ORDER = "1";
		public static final  String GUILD_ORDER = "2";
		public static final  String GIVE_ORDER = "3";
		public static final  String SYSTEM_GIVE_ORDER = "4";
	}
}

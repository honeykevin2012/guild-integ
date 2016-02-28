package com.game.payment.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class OrderInfoForm extends BaseForm{

	private Integer merchantId;
	private String merchantVersion;
	
	@NotEmpty(message = "error#产品ID为空.")
	private String appId;//
	
	@NotEmpty(message = "error#服务器ID为空.")
	private String serverId;//
	
	@NotEmpty(message = "error#角色ID为空.")
	private String roleId;//
	
	@NotEmpty(message = "error#角色名称为空.")
	private String roleName;//
	
	@NotEmpty(message = "error#用户ID为空.")
	private String userId;
	
	@NotEmpty(message = "error#用户名为空.")
	private String userName;//
	
	@NotNull(message = "error#卡类为空.")
	private Integer cardType;
	
	@NotEmpty(message = "error#用户注册时间为空.")
	private String userRegTime;
	
	private String passportType;
	private String mid;//手机型号
	private String ua;//手机型号
	
	private Integer paymentId;
	private String clientOsType;//
	private String channelOrderId;//
	private String merchantOrderId;//
	private String merchantOrderDate;//
	private String merchantProductName;//
	private double orderAmount;
	private double amount;
	private Integer status;
	private String statusCode;
	private String createDate;
	private String dealDate;
	private String payOrderId;//
	private String payStatus;
	private String payStatusCode;//
	private String notifyStatus;
	private String notifyUrl;
	private String returnUrl;
	private String cardNo;
	private String cardPwd;
	private String parameter1;
	private String appChannelId;//9961channel id
	private String notifyTimes;
	private String notifyDate;
	private String sourceType;
	private String memo;
	private String payId;
	private String clientType;//
	private String clientOsVersion;//
	private String ap;
	private String phoneType;//
	private String statusMessage;
	private String accountId;//
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getChannelOrderId() {
		return channelOrderId;
	}
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantVersion() {
		return merchantVersion;
	}
	public void setMerchantVersion(String merchantVersion) {
		this.merchantVersion = merchantVersion;
	}
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getMerchantOrderDate() {
		return merchantOrderDate;
	}
	public void setMerchantOrderDate(String merchantOrderDate) {
		this.merchantOrderDate = merchantOrderDate;
	}
	public String getMerchantProductName() {
		return merchantProductName;
	}
	public void setMerchantProductName(String merchantProductName) {
		this.merchantProductName = merchantProductName;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayStatusCode() {
		return payStatusCode;
	}
	public void setPayStatusCode(String payStatusCode) {
		this.payStatusCode = payStatusCode;
	}
	public String getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardPwd() {
		return cardPwd;
	}
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}
	public String getParameter1() {
		return parameter1;
	}
	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}
	public String getAppChannelId() {
		return appChannelId;
	}
	public void setAppChannelId(String appChannelId) {
		this.appChannelId = appChannelId;
	}
	public String getNotifyTimes() {
		return notifyTimes;
	}
	public void setNotifyTimes(String notifyTimes) {
		this.notifyTimes = notifyTimes;
	}
	public String getNotifyDate() {
		return notifyDate;
	}
	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRegTime() {
		return userRegTime;
	}
	public void setUserRegTime(String userRegTime) {
		this.userRegTime = userRegTime;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getClientOsType() {
		return clientOsType;
	}
	public void setClientOsType(String clientOsType) {
		this.clientOsType = clientOsType;
	}
	public String getClientOsVersion() {
		return clientOsVersion;
	}
	public void setClientOsVersion(String clientOsVersion) {
		this.clientOsVersion = clientOsVersion;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getPassportType() {
		return passportType;
	}
	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}
}

package com.game.payment.domain;

import java.util.Date;

public class OrderInfo {
	private String orderId;
	private String channelOrderid;
	private Integer merchantId;
	private String merchantVersion;
	private String merchantOrderid;
	private String merchantOrderdate;
	private String merchantProductname;
	private double orderAmount;
	private double amount;
	private Integer status;
	private String statusCode;
	private Date createDate;
	private Date dealDate;
	private String payOrderid;
	private Integer payStatus;
	private String payStatuscode;
	private Integer notifyStatus;
	private String notifyUrl;
	private String returnUrl;
	private String cardNo;
	private String cardPwd;
	private String parameter1;
	private String appChannelId;//9961 channel
	private Integer notifyTimes;
	private Date notifyDate;
	private Integer paymentId;
	private Integer channelId;//支付channel
	private Integer sourceType;
	private String memo;
	private Integer payId;
	private String nnuid;
	private String username;
	private Date userregtime;
	private Integer roleid;
	private String rolename;
	private String clienttype;
	private String clientostype;
	private String clientosversion;
	private String ap;
	private String imei;
	private String mac;
	private String phonetype;
	private String appid;
	private String serverid;
	private String thirdUid;
	private String statusMessage;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getChannelOrderid() {
		return channelOrderid;
	}
	public void setChannelOrderid(String channelOrderid) {
		this.channelOrderid = channelOrderid;
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
	public String getMerchantOrderid() {
		return merchantOrderid;
	}
	public void setMerchantOrderid(String merchantOrderid) {
		this.merchantOrderid = merchantOrderid;
	}
	public String getMerchantOrderdate() {
		return merchantOrderdate;
	}
	public void setMerchantOrderdate(String merchantOrderdate) {
		this.merchantOrderdate = merchantOrderdate;
	}
	public String getMerchantProductname() {
		return merchantProductname;
	}
	public void setMerchantProductname(String merchantProductname) {
		this.merchantProductname = merchantProductname;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getPayOrderid() {
		return payOrderid;
	}
	public void setPayOrderid(String payOrderid) {
		this.payOrderid = payOrderid;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayStatuscode() {
		return payStatuscode;
	}
	public void setPayStatuscode(String payStatuscode) {
		this.payStatuscode = payStatuscode;
	}
	public Integer getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(Integer notifyStatus) {
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
	public Integer getNotifyTimes() {
		return notifyTimes;
	}
	public void setNotifyTimes(Integer notifyTimes) {
		this.notifyTimes = notifyTimes;
	}
	public Date getNotifyDate() {
		return notifyDate;
	}
	public void setNotifyDate(Date notifyDate) {
		this.notifyDate = notifyDate;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getPayId() {
		return payId;
	}
	public void setPayId(Integer payId) {
		this.payId = payId;
	}
	public String getNnuid() {
		return nnuid;
	}
	public void setNnuid(String nnuid) {
		this.nnuid = nnuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getUserregtime() {
		return userregtime;
	}
	public void setUserregtime(Date userregtime) {
		this.userregtime = userregtime;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getClienttype() {
		return clienttype;
	}
	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}
	public String getClientostype() {
		return clientostype;
	}
	public void setClientostype(String clientostype) {
		this.clientostype = clientostype;
	}
	public String getClientosversion() {
		return clientosversion;
	}
	public void setClientosversion(String clientosversion) {
		this.clientosversion = clientosversion;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getPhonetype() {
		return phonetype;
	}
	public void setPhonetype(String phonetype) {
		this.phonetype = phonetype;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getThirdUid() {
		return thirdUid;
	}
	public void setThirdUid(String thirdUid) {
		this.thirdUid = thirdUid;
	}
}

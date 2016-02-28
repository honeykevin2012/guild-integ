/*
 * @athor zhaolianjun
 * created on 2015-01-27
 */
 
package com.game.platform.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:游戏信息 <br>
 * @author zhaolianjun
 */
 
public class GameInfo extends BaseEntity{
	private static final long serialVersionUID = 1L;

	//table name : app_info
	private Integer id;//ID
	private Integer appId;//游戏id
	private String appName;//游戏名称
	private Integer appType;//游戏类型
	private String appDesc;//游戏描述
	private String noticeUrl;//通知充值结果Url
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	private String serverUrl;//获取服务器列表地址
	private String serverstateUrl;//serverstate_url
	private Integer scale;//充值比例
	private String gmSecret;//gm外链密钥
	private String gmAddress;//gm工具地址
	private String orderAddress;//gm工具订单查询地址
	private String appStatus;//是否有效
	private Integer appAccount;//app账号是否开启

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getAppId(){
		return this.appId;
	}
	public void setAppId(Integer appId){
		this.appId = appId;
	}
	public String getAppName(){
		return this.appName;
	}
	public void setAppName(String appName){
		this.appName = appName;
	}
	public Integer getAppType(){
		return this.appType;
	}
	public void setAppType(Integer appType){
		this.appType = appType;
	}
	public String getAppDesc(){
		return this.appDesc;
	}
	public void setAppDesc(String appDesc){
		this.appDesc = appDesc;
	}
	public String getNoticeUrl(){
		return this.noticeUrl;
	}
	public void setNoticeUrl(String noticeUrl){
		this.noticeUrl = noticeUrl;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public String getServerUrl(){
		return this.serverUrl;
	}
	public void setServerUrl(String serverUrl){
		this.serverUrl = serverUrl;
	}
	public String getServerstateUrl(){
		return this.serverstateUrl;
	}
	public void setServerstateUrl(String serverstateUrl){
		this.serverstateUrl = serverstateUrl;
	}
	public Integer getScale(){
		return this.scale;
	}
	public void setScale(Integer scale){
		this.scale = scale;
	}
	public String getGmSecret(){
		return this.gmSecret;
	}
	public void setGmSecret(String gmSecret){
		this.gmSecret = gmSecret;
	}
	public String getGmAddress(){
		return this.gmAddress;
	}
	public void setGmAddress(String gmAddress){
		this.gmAddress = gmAddress;
	}
	public String getOrderAddress(){
		return this.orderAddress;
	}
	public void setOrderAddress(String orderAddress){
		this.orderAddress = orderAddress;
	}
	public String getAppStatus(){
		return this.appStatus;
	}
	public void setAppStatus(String appStatus){
		this.appStatus = appStatus;
	}
	public Integer getAppAccount(){
		return this.appAccount;
	}
	public void setAppAccount(Integer appAccount){
		this.appAccount = appAccount;
	}
}

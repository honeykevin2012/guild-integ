/*
 * @athor yangchengwei
 * created on 2015-04-22
 */
 
package com.game.user.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:用户个人购买商品扣除游戏金币进程表 <br>
 * @author yangchengwei
 */
 
public class UserGameDeductingProcess extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	private Integer userId;//用户ID
	private String gameId;//游戏编号
	private String gameName;//游戏
	private String gameIcon;//游戏
	private Long costAmount;//扣除的游戏币数量
	private Long exchangeNbGold;//与游戏币等价的平台币
	private Object exchangeDivide;//游戏币与平台币的兑换比例
	private String orderId;//订单编号
	private String status;//游戏金币扣除是否成功1是0否
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//兑换时间
	private Integer requestTimes;//请求次数
	private String serverId;//服务器ID
	private String serverName;
	private String roleId;//角色ID
	private String roleName;
	private String faildMessage;//失败原因

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getUserId(){
		return this.userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getGameId(){
		return this.gameId;
	}
	public void setGameId(String gameId){
		this.gameId = gameId;
	}
	public Long getCostAmount(){
		return this.costAmount;
	}
	public void setCostAmount(Long costAmount){
		this.costAmount = costAmount;
	}
	public Long getExchangeNbGold(){
		return this.exchangeNbGold;
	}
	public void setExchangeNbGold(Long exchangeNbGold){
		this.exchangeNbGold = exchangeNbGold;
	}
	public Object getExchangeDivide(){
		return this.exchangeDivide;
	}
	public void setExchangeDivide(Object exchangeDivide){
		this.exchangeDivide = exchangeDivide;
	}
	public String getOrderId(){
		return this.orderId;
	}
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Integer getRequestTimes(){
		return this.requestTimes;
	}
	public void setRequestTimes(Integer requestTimes){
		this.requestTimes = requestTimes;
	}
	public String getServerId(){
		return this.serverId;
	}
	public void setServerId(String serverId){
		this.serverId = serverId;
	}
	public String getRoleId(){
		return this.roleId;
	}
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	public String getFaildMessage(){
		return this.faildMessage;
	}
	public void setFaildMessage(String faildMessage){
		this.faildMessage = faildMessage;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getGameIcon() {
		return gameIcon;
	}
	public void setGameIcon(String gameIcon) {
		this.gameIcon = gameIcon;
	}
	
}

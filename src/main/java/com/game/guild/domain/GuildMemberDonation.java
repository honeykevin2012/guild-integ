/*
 * @athor zhaolianjun
 * created on 2015-03-17
 */
 
package com.game.guild.domain;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:捐款记录 <br>
 * @author zhaolianjun
 */
 
public class GuildMemberDonation extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//ID
	private Integer userId;//用户ID
	private Integer guildId;//公会ID
	private Long amount;//捐款金额
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private String createTime;//捐款时间
	
	private Integer gameId;
	private String gameName;
	private String serverId;
	private String serverName;
	private String roleId;
	private String roleName;
	
	//query field
	private String guildName;
	private String userName;
	

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
	public Integer getGuildId(){
		return this.guildId;
	}
	public void setGuildId(Integer guildId){
		this.guildId = guildId;
	}
	public Long getAmount(){
		return this.amount;
	}
	public void setAmount(Long amount){
		this.amount = amount;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getGuildName() {
		return guildName;
	}
	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
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
}

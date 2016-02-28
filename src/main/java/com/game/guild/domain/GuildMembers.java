/*
 * @athor zhaolianjun
 * created on 2015-02-15
 */
 
package com.game.guild.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:公会等级信息 <br>
 * @author zhaolianjun
 */
 
public class GuildMembers extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//ID
	private Integer userId;//用户ID
	private Integer guildId;//所属公会ID
	private Integer level;//成员等级
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date joinTime;//加入公会时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date applyTime;//申请时间
	private Integer exp;//经验
	private Integer point;//积分
	private String isAdmin;//是否管理员1:会长2管理员0:普通成员
	private String status;//0未通过 1正常
	
	//query field
	private String userName;
	private String nickName;
	private String guildName;
	private String levelName;
	private String headIcon;
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
	public Integer getLevel(){
		return this.level;
	}
	public void setLevel(Integer level){
		this.level = level;
	}
	public Date getJoinTime(){
		return this.joinTime;
	}
	public void setJoinTime(Date joinTime){
		this.joinTime = joinTime;
	}
	public Date getApplyTime(){
		return this.applyTime;
	}
	public void setApplyTime(Date applyTime){
		this.applyTime = applyTime;
	}
	public Integer getExp(){
		return this.exp;
	}
	public void setExp(Integer exp){
		this.exp = exp;
	}
	public Integer getPoint(){
		return this.point;
	}
	public void setPoint(Integer point){
		this.point = point;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGuildName() {
		return guildName;
	}
	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

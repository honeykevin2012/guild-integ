/*
 * @athor yangchengwei
 * created on 2015-02-03
 */
 
package com.game.guild.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:公会信息 <br>
 * @author yangchengwei
 */
 
public class GuildInfo extends BaseEntity{
	private static final long serialVersionUID = 1L;
	public enum GuildDefault {
		Status("1"), //默认状态
		Icon("/default/guild_icon.jpg"), //默认图标
		Level("1"); //默认等级
		private String value;

		GuildDefault(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
	private Integer id;//ID
	private String name;//公会名称
	private String icon;//公会头像
	private String remark;//信息描述
	private Integer level;//公会等级
	private Integer memberCount;//公会成员数
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//公会建立时间
	private Long currency;//公会财富
	private Integer discountId;//折扣ID
	private String slogan;//公会宣言
	private String status;//公会状态1正常2关闭
	private Integer createUserId;//创建人
	private Integer commend;//推荐公会
	private Integer exp;
	private Integer nextExp;
	private Integer version;
	private String userName;//会长
	private String nickName;
	private String games;//在玩游戏
	private String joined;//
	private String iconPath;//公会头像
	private Integer giftCount;
	private String levelName;
	private Integer joinCount;//申请人数
	
	private GuildLevel guildLevel;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public Integer getLevel(){
		return this.level;
	}
	public void setLevel(Integer level){
		this.level = level;
	}
	public Integer getMemberCount(){
		return this.memberCount;
	}
	public void setMemberCount(Integer memberCount){
		this.memberCount = memberCount;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Long getCurrency(){
		return this.currency;
	}
	public void setCurrency(Long currency){
		this.currency = currency;
	}
	public Integer getDiscountId(){
		return this.discountId;
	}
	public void setDiscountId(Integer discountId){
		this.discountId = discountId;
	}
	public String getSlogan(){
		return this.slogan;
	}
	public void setSlogan(String slogan){
		this.slogan = slogan;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getCommend() {
		return commend;
	}
	public void setCommend(Integer commend) {
		this.commend = commend;
	}
	public Integer getExp() {
		return exp;
	}
	public void setExp(Integer exp) {
		this.exp = exp;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGames() {
		return games;
	}
	public void setGames(String games) {
		this.games = games;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getJoined() {
		return joined;
	}
	public void setJoined(String joined) {
		this.joined = joined;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public Integer getGiftCount() {
		return giftCount;
	}
	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}
	public Integer getNextExp() {
		return nextExp;
	}
	public void setNextExp(Integer nextExp) {
		this.nextExp = nextExp;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Integer getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}
	public GuildLevel getGuildLevel() {
		return guildLevel;
	}
	public void setGuildLevel(GuildLevel guildLevel) {
		this.guildLevel = guildLevel;
	}
}

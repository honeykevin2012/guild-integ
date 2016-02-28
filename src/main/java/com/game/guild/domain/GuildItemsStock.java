/*
 * @athor yangchengwei
 * created on 2015-03-04
 */
 
package com.game.guild.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:公会仓库 <br>
 * @author yangchengwei
 */
 
public class GuildItemsStock extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//ID
	
	private Integer guildId;//公会ID
	
	private Integer itemId;//礼品包ID

	private String name;//物品名称
	
	private String isVirtual;//是否虚拟
	
	private String startTime;
	
	private String endTime;
	
	private Integer count;
	private Integer typeId;
	private Integer gived;
	private String remark;
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getGuildId(){
		return this.guildId;
	}
	public void setGuildId(Integer guildId){
		this.guildId = guildId;
	}
	public Integer getItemId(){
		return this.itemId;
	}
	public void setItemId(Integer itemId){
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsVirtual() {
		return isVirtual;
	}
	public void setIsVirtual(String isVirtual) {
		this.isVirtual = isVirtual;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getGived() {
		return gived;
	}
	public void setGived(Integer gived) {
		this.gived = gived;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
}

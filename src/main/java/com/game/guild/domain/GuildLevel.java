/*
 * @athor zhaolianjun
 * created on 2015-03-17
 */
 
package com.game.guild.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:公会等级 <br>
 * @author zhaolianjun
 */
 
public class GuildLevel extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//ID
	private String name;//等级名称
	private String remark;//等级说明
	private Integer level;//级别
	private Integer exp;//经验阀值
	private Long amount;
	private Integer limitMemberQuantity;
	private String isDefault;

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
	public Integer getExp(){
		return this.exp;
	}
	public void setExp(Integer exp){
		this.exp = exp;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getLimitMemberQuantity() {
		return limitMemberQuantity;
	}
	public void setLimitMemberQuantity(Integer limitMemberQuantity) {
		this.limitMemberQuantity = limitMemberQuantity;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}

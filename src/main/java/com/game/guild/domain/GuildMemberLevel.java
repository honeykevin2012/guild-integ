/*
 * @athor zhaolianjun
 * created on 2015-06-26
 */
 
package com.game.guild.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:公会成员等级 <br>
 * @author zhaolianjun
 */
 
public class GuildMemberLevel extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//ID
	private String name;//name
	private Integer level;//等级
	private Integer guildId;//公会id
	private Integer exp;//exp
	private String isDefault;//成员默认等级
	private String remark;//描述

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
	public Integer getLevel(){
		return this.level;
	}
	public void setLevel(Integer level){
		this.level = level;
	}
	public Integer getGuildId(){
		return this.guildId;
	}
	public void setGuildId(Integer guildId){
		this.guildId = guildId;
	}
	public Integer getExp(){
		return this.exp;
	}
	public void setExp(Integer exp){
		this.exp = exp;
	}
	public String getIsDefault(){
		return this.isDefault;
	}
	public void setIsDefault(String isDefault){
		this.isDefault = isDefault;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
}

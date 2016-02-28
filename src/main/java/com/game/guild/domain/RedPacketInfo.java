/*
 * @athor yangchengwei
 * created on 2015-08-05
 */
 
package com.game.guild.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:红包信息表 <br>
 * @author yangchengwei
 */
 
public class RedPacketInfo extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	
	private String name;//红包名称
	
	private Long totalMoney;//总金额
	
	private Integer totalPeople;//总人数
	
	private Integer userId;//创建人id
	
	private Integer guildId;//公会id
	
	private Long leftMoney;//剩余金额
	
	private Integer leftPeople;//剩余人数
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	private Integer version;//id
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
	public Long getTotalMoney(){
		return this.totalMoney;
	}
	public void setTotalMoney(Long totalMoney){
		this.totalMoney = totalMoney;
	}
	public Integer getTotalPeople(){
		return this.totalPeople;
	}
	public void setTotalPeople(Integer totalPeople){
		this.totalPeople = totalPeople;
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
	public Long getLeftMoney(){
		return this.leftMoney;
	}
	public void setLeftMoney(Long leftMoney){
		this.leftMoney = leftMoney;
	}
	public Integer getLeftPeople(){
		return this.leftPeople;
	}
	public void setLeftPeople(Integer leftPeople){
		this.leftPeople = leftPeople;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
}

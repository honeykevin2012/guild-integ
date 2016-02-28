/*
 * @athor yangchengwei
 * created on 2015-08-05
 */
 
package com.game.guild.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:红包表 <br>
 * @author yangchengwei
 */
 
public class RedPacketItem extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	
	private Integer packetId;//红包id
	
	private Long money;//金额
	
	private Integer userId;//用户id
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date receiveTime;//领取时间

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getPacketId(){
		return this.packetId;
	}
	public void setPacketId(Integer packetId){
		this.packetId = packetId;
	}
	public Long getMoney(){
		return this.money;
	}
	public void setMoney(Long money){
		this.money = money;
	}
	public Integer getUserId(){
		return this.userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getReceiveTime(){
		return this.receiveTime;
	}
	public void setReceiveTime(Date receiveTime){
		this.receiveTime = receiveTime;
	}
}

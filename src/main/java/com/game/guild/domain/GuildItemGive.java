/*
 * @athor yangchengwei
 * created on 2015-03-17
 */
 
package com.game.guild.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:物品赠送 <br>
 * @author yangchengwei
 */
 
public class GuildItemGive extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//ID
	
	private Integer guildId;//公会ID
	
	private Integer itemId;//礼包ID
	
	private Integer productId;//商品ID
	
	private Integer receiveUserId;//接收人ID
	
	private Integer count;//数量
	
	private String sourceType;//转赠类型(1公会、2个人)
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date giveTime;//赠送时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date receiveTime;//接收时间
	
	private String status;//接收状态

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
	public Integer getProductId(){
		return this.productId;
	}
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	public Integer getReceiveUserId(){
		return this.receiveUserId;
	}
	public void setReceiveUserId(Integer receiveUserId){
		this.receiveUserId = receiveUserId;
	}
	public String getSourceType(){
		return this.sourceType;
	}
	public void setSourceType(String sourceType){
		this.sourceType = sourceType;
	}
	public Date getGiveTime(){
		return this.giveTime;
	}
	public void setGiveTime(Date giveTime){
		this.giveTime = giveTime;
	}
	public Date getReceiveTime(){
		return this.receiveTime;
	}
	public void setReceiveTime(Date receiveTime){
		this.receiveTime = receiveTime;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}

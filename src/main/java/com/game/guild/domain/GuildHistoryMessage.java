/*
 * @athor yangchengwei
 * created on 2015-08-11
 */
 
package com.game.guild.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:历史消息表 <br>
 * @author yangchengwei
 */
 
public class GuildHistoryMessage extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	
	private Integer guildId;//公会id
	
	private Integer type;//消息类型
	
	private Integer from;//from
	
	private Integer to;//to
	
	private String content;//content
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//create_time

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
	public Integer getType(){
		return this.type;
	}
	public void setType(Integer type){
		this.type = type;
	}
	public Integer getFrom(){
		return this.from;
	}
	public void setFrom(Integer from){
		this.from = from;
	}
	public Integer getTo(){
		return this.to;
	}
	public void setTo(Integer to){
		this.to = to;
	}
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public enum MsgTypeEnum {
		
		REDPACKET(1),GUILD(2);
		
		
		private final int value;

		MsgTypeEnum(int value) {
	        this.value = value;
	    }

		public int getValue() {
			return value;
		}
		
	}
}

/*
 * @athor yangchengwei
 * created on 2015-06-29
 */
 
package com.game.platform.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.basics.domain.BaseEntity;
import com.game.init.setting.SettingAttribute;

/**
 * @desc:用户消息表 <br>
 * @author yangchengwei
 */
 
public class MessageReader extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private Integer id;//id
	private Integer userId;//用户id
	private Integer msgId;//消息id
	private String status;//状态
	private String title;//标题
	private String content;//内容
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sendTime;//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date readTime;//创建时间
	private String type;
	private String isReceived;
	private Integer fromGuildId = 0;
	private String dataParams;
	
	private Set<MessageReaderAttach> attachs = new HashSet<MessageReaderAttach>();
	
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
	public Integer getMsgId(){
		return this.msgId;
	}
	public void setMsgId(Integer msgId){
		this.msgId = msgId;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsReceived() {
		return isReceived;
	}
	public void setIsReceived(String isReceived) {
		this.isReceived = isReceived;
	}
	public String getDataParams() {
		StringBuilder builder = new StringBuilder();
		if(!this.attachs.isEmpty()){
			Iterator<MessageReaderAttach> it = this.attachs.iterator();
			while(it.hasNext()){
				if(builder.length() != 0) builder.append(",");
				MessageReaderAttach attach = it.next();
				builder.append(attach.getDataId()).append("#$#").append(attach.getQuantity()).append("#$#").append(attach.getName());
			}
		}
		this.setDataParams(builder.toString());
		return dataParams;
	}
	public void setDataParams(String dataParams) {
		this.dataParams = dataParams;
	}
	
	public int getSumQuantity(){
		int sumQuantity = 0;
		if(!this.attachs.isEmpty()){
			Iterator<MessageReaderAttach> it = this.attachs.iterator();
			while(it.hasNext()){
				MessageReaderAttach attach = it.next();
				if(attach.getQuantity() == null) continue;
				sumQuantity += attach.getQuantity();
			}
		}
		return sumQuantity;
	}
	
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Set<MessageReaderAttach> getAttachs() {
		return attachs;
	}
	public void setAttachs(Set<MessageReaderAttach> attachs) {
		this.attachs = attachs;
	}

	public Integer getFromGuildId() {
		return fromGuildId;
	}
	public void setFromGuildId(Integer fromGuildId) {
		this.fromGuildId = fromGuildId;
	}

	//消息类型定义
	public enum type {
		//LOGIN(SettingAttribute.settingLogin, "5"), REGISTER(SettingAttribute.settingRegister, "6"), 
		SIGN(SettingAttribute.SETTING_SIGN, "5");
		private String name;
		private String value;

		type(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	public UserNoticeEnum getTypeEnum() {
		return UserNoticeEnum.getProvider(this.type);
	}
	@Override
	public String toString() {
		return "MessageReader [id=" + id + ", userId=" + userId + ", msgId=" + msgId + ", status=" + status + ", title=" + title + ", content=" + content + ", sendTime=" + sendTime + ", readTime="
				+ readTime + ", type=" + type + ", isReceived=" + isReceived + ", dataParams=" + dataParams + ", attachs=" + attachs + "]";
	}

	public enum UserNoticeEnum {
		LEVELUP("级别晋升", "1", "14ab05"),ORDER("订单消息", "2", "f45151"),SYSTEM("系统消息", "3", "23a0e8"),GUILD("公会公告", "4", "cf29b9"),EXCHANGE("汇率变化", "5", "000");
		
		private String name; 
		private String value; 
		private String color; 
		private UserNoticeEnum(String name,String value,String color) {
			this.value = value;
			this.color = color;
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public String getColor() {
			return color;
		}
		public String getName() {
			return name;
		}		
		public static UserNoticeEnum getProvider(String cpValue){
			UserNoticeEnum cps[] = UserNoticeEnum.values();
			for(UserNoticeEnum cp : cps){
				if(cp.value.equals(cpValue)) return cp;
			}
			return null;
		}
	}
}

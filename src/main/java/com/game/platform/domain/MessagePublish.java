/*
 * @athor yangchengwei
 * created on 2015-06-29
 */
 
package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:消息表 <br>
 * @author yangchengwei
 */
 
public class MessagePublish extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	
	private String title;//标题
	
	private String content;//内容
	
	private String groupId;//用户组
	
	private String status;//状态

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getGroupId(){
		return this.groupId;
	}
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}

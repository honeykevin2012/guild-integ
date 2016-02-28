/*
 * @athor yangchengwei
 * created on 2015-04-20
 */
 
package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:平台意见反馈 <br>
 * @author yangchengwei
 */
 
public class PlatformFeedback extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private Integer id;//id
	private String type;
	private String title;//标题
	private String content;//内容
	private String attachmentPath;//附件路径
	private String qq;
	private String phone;

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
	public String getAttachmentPath(){
		return this.attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath){
		this.attachmentPath = attachmentPath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}

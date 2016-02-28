/*
 * @athor zhaolianjun
 * created on 2015-04-09
 */
 
package com.game.user.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:用户登录加密信息表 <br>
 * @author zhaolianjun
 */
 
public class UserSessionToken extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	private Integer userId;//用户id
	private String ck;//客户端3DES密钥
	private String tk;//服务器给客户端的令牌
	private String os;//访问来源android, ios, web...

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
	public String getCk(){
		return this.ck;
	}
	public void setCk(String ck){
		this.ck = ck;
	}
	public String getTk(){
		return this.tk;
	}
	public void setTk(String tk){
		this.tk = tk;
	}
	public String getOs(){
		return this.os;
	}
	public void setOs(String os){
		this.os = os;
	}
}

/*
 * @athor zhaolianjun
 * created on 2015-08-06
 */
 
package com.game.user.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:用户备份资料表 <br>
 * @author zhaolianjun
 */
 
public class UserDataItems extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer userId;//user_id
	private String qq;//qq
	private String mobile;//mobile
	private String email;//email
	private String avatar;//avatar
	private String nickName;//nick_name
	private String address;//address
	private String sex;//sex
	private String brithday;//brithday

	public Integer getUserId(){
		return this.userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getQq(){
		return this.qq;
	}
	public void setQq(String qq){
		this.qq = qq;
	}
	public String getMobile(){
		return this.mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getAvatar(){
		return this.avatar;
	}
	public void setAvatar(String avatar){
		this.avatar = avatar;
	}
	public String getNickName(){
		return this.nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public String getSex(){
		return this.sex;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public String getBrithday(){
		return this.brithday;
	}
	public void setBrithday(String brithday){
		this.brithday = brithday;
	}
}

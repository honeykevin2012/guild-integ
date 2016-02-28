package com.game.user.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.BaseForm;

public class UserInfoForm  extends BaseForm{
	@NotNull(message = "error#用户未登录.")
	private Integer userId;
	//@Length(min = 5, max = 16, message = "用户名长度在5-16之间")
	private String userName;//用户名
	private String password;//
	private String originalPassword;//原始密码
	private String newPassword;//新密码
	private String registerType;//注册类型0:手动注册 1：自动注册
	private String email;
	private String phone;
	private String qq;
	@Length(min = 0, max = 10, message = "昵称长度最多10字")
	private String nickName;
	@Length(min = 0, max = 50, message = "签名长度最多50字")
	private String mood;
	private String sex;
	private String birthday;
	private String location;
	private String code;//激活码
	private String headIcon;//
	@NotEmpty(message = "error#用户token不能为空.")
	private String token;//校验token
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOriginalPassword() {
		return originalPassword;
	}
	public void setOriginalPassword(String originalPassword) {
		this.originalPassword = originalPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}

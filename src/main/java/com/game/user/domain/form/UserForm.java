package com.game.user.domain.form;

import com.game.common.BaseForm;

public class UserForm extends BaseForm{
	
	//@Length(min = 5, max = 16, message = "用户名长度在5-16之间")
	private String userName;//用户名
	private String password;//
	private String originalPassword;//原始密码
	private String newPassword;//新密码
	private String registerType;//注册类型0:手动注册 1：自动注册
	private String email;
	private String phone;
	private String icon;

	private String code;//激活码
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}

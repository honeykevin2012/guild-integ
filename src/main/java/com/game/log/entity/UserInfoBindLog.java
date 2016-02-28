package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.UserInfoBindLogDao;


/**
 * 用户绑定
 * @author kevin
 *
 */
@Document(collection = "user_infoBind_log")
public class UserInfoBindLog extends BaseLogger{
	
	private String userId;
	private String niceName;
	private String sex;
	private String phone;
	private String qq;
	private String email;
	private String birthday;
	
	@Override
	public void create() {
		UserInfoBindLogDao logger = new UserInfoBindLogDao();
		logger.save(this);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

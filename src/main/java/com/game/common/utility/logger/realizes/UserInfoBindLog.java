package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 用户绑定
 * @author kevin
 *
 */
public class UserInfoBindLog extends BaseLogger{
	
	private String userId;
	private String niceName;
	private String sex;
	private String phone;
	private String qq;
	private String email;
	private String birthday;
	
	public UserInfoBindLog() {
		super("UserInfoBind");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_UserInfoBind").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(niceName).append(this.append);//昵称
	    buffer.append(sex).append(this.append);//性别
	    buffer.append(phone).append(this.append);//手机号
	    buffer.append(qq).append(this.append);//qq
	    buffer.append(email).append(this.append);//邮箱地址
	    buffer.append(birthday).append(this.append);//生日
	    //公共参数
	    buffer.append(this.baseParameter());
	    return buffer.toString();
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

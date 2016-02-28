/*
 * @athor zhaolianjun
 * created on 2015-01-06
 */

package com.game.user.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:会员管理 <br>
 * @author zhaolianjun
 */

public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	public enum UserDefault {
		SEX("2"), //默认性别
		GROUP("1001"), //默认组
		BALANCE("500000"), //默认余额
		AVATAR("/default/user_avatar.jpg"), //默认头像
		MT_REGISTER("0"), //手动注册
		AT_REGISTER("1"); //自动注册
		private String value;

		UserDefault(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	private Integer id;// ID
	private String userName;// 用户名
	private String userPwd;// 密码
	private String sex;// 性别
	private String phone;// 手机号
	private String email;// 电子邮箱
	private Integer locationId;// 地址ID
	private String locationName;// 地址
	private String headIcon;// 头像
	private String headPath;// 头像
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crateTime;// 创建时间
	private String sourceFrom;// 用户来源
	private String mac;// MAC地址
	private String imei;// IMEI
	private String osVersion;// 操作系统版本号
	private String os;// 操作系统
	private String status;// 状态
	private Integer groupId;// 用户组ID
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;// 最后登录时间
	private Long balance;// 账户余额
	private String qq;// QQ号码
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;// 生日
	private Integer point;// 积分
	private Integer exp;// 经验
	private String clientIp;
	private String nickName;
	private String mood;
	private Integer version;
	private String registerType;//注册类型 0：手动, 1：自动
	private String educational;//学历
	private Long amount;
	
	private String groupName;
	private Integer groupLevel;
	private Double groupAmount;
	private String groupIcon;
	
	private UserVipGroup group;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadIcon() {
		return this.headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	public Date getCrateTime() {
		return this.crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	public String getSourceFrom() {
		return this.sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getOsVersion() {
		return this.osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getBalance() {
		return this.balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getExp() {
		return this.exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Integer getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(Integer groupLevel) {
		this.groupLevel = groupLevel;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Double getGroupAmount() {
		return groupAmount;
	}

	public void setGroupAmount(Double groupAmount) {
		this.groupAmount = groupAmount;
	}

	public String getGroupIcon() {
		return groupIcon;
	}

	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getEducational() {
		return educational;
	}

	public void setEducational(String educational) {
		this.educational = educational;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public UserVipGroup getGroup() {
		return group;
	}

	public void setGroup(UserVipGroup group) {
		this.group = group;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}

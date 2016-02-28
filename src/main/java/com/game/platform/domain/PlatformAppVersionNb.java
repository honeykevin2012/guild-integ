/*
 * @athor zhaolianjun
 * created on 2014-12-30
 */

package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:版本信息管理 <br>
 * @author zhaolianjun
 */

public class PlatformAppVersionNb extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// ID

	private String versionCode;// 版本号

	private String versionName;// 版本名称

	private String remark;// 描述

	private String url;// 地址

	private String isForce;// 是否强制更新

	private String clientType;// 客户端类型(0安卓1苹果)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersionCode() {
		return this.versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return this.versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsForce() {
		return this.isForce;
	}

	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}

	public String getClientType() {
		return this.clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
}

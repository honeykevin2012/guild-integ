/*
 * @athor yangchengwei
 * created on 2014-10-21
 */

package com.game.app.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:version <br>
 * @author zhaolianjun
 */

public class Version extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// 编号

	private Integer version;// 版本号

	private String name;// 版本名称

	private Integer osType;// 系统类型

	private String updateUrl;// 更新地址

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间
	private Integer isForce;// 强制更新

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOsType() {
		return this.osType;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public String getUpdateUrl() {
		return this.updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsForce() {
		return isForce;
	}

	public void setIsForce(Integer isForce) {
		this.isForce = isForce;
	}

}

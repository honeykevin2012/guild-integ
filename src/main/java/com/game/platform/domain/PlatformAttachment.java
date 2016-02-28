/*
 * @athor zhaolianjun
 * created on 2014-12-31
 */

package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:附件信息管理 <br>
 * @author zhaolianjun
 */

public class PlatformAttachment extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// 图片ID

	private String fileName;// filename

	private String size;// 规格

	private String path;// 相对路径

	private Integer relationId;// 业务编号

	private String channel;// 频道标识
	
	private String isDefault;//是否默认图片
	
	private Integer sort;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getRelationId() {
		return this.relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}

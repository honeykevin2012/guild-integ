/*
 * @athor zhaolianjun
 * created on 2015-01-29
 */

package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:区块管理 <br>
 * @author zhaolianjun
 */

public class PlatformBlockComment extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// id
	private String name;// 名字描述
	private String blockId;// 区块标记
	private String businessIds;// 绑定的业务编号
	private String handEdit;// 手工编辑区域

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBlockId() {
		return this.blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public String getBusinessIds() {
		return this.businessIds;
	}

	public void setBusinessIds(String businessIds) {
		this.businessIds = businessIds;
	}

	public String getHandEdit() {
		return this.handEdit;
	}

	public void setHandEdit(String handEdit) {
		this.handEdit = handEdit;
	}
}

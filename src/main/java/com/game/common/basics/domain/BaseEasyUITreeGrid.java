package com.game.common.basics.domain;

import java.io.Serializable;

import com.game.common.basics.domain.BaseEntity;

public class BaseEasyUITreeGrid extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -3827452387833949809L;
	private String parentId;
	private String state = "open";

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

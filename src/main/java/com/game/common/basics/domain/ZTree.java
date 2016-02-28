package com.game.common.basics.domain;

public class ZTree {
	private String id;
	private String pId;
	private String name;
	private boolean open = true;
	private boolean nocheck = true;
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
}

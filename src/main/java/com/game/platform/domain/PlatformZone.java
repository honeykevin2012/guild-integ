package com.game.platform.domain;

public class PlatformZone {
	private Integer id;
	private Integer code;
	private String name;
	private String zoneNames;
	private Integer parentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getZoneNames() {
		return zoneNames;
	}

	public void setZoneNames(String zoneNames) {
		this.zoneNames = zoneNames;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PlatformZone [id=" + id + ", code=" + code + ", name=" + name
				+ ", zoneNames=" + zoneNames + ", parentId=" + parentId + "]";
	}
}

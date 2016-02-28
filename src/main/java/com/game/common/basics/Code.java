package com.game.common.basics;

import java.io.Serializable;

/**
 * 
 * @author zhaolianjun
 *
 */
public class Code implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private CodeType type;
	private String name;
	private String value;

	public Code() {
		
	}

	/**
	 * @param type
	 */
	public Code(CodeType type) {
		this.type = type;
		this.name = null;
		this.value = null;
	}

	public Code(CodeType type, String value, String name) {
		if (type == null || value == null || name == null)
			throw new IllegalArgumentException();
		this.type = type;
		this.name = name;
		this.value = value;
		type.add(this);
	}

	public String getName() {
		if (name != null)
			return name;
		if (type == null || value == null || value.length() == 0)
			return null;
		Code code = type.get(value);
		return code == null ? null : code.getName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public CodeType getType() {
		return type;
	}

	public void setType(CodeType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 */
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Code))
			return false;
		Code tmp = (Code) obj;
		if (type == tmp.type) {
			if (value == null)
				return (tmp.value == null);
			else
				return value.equals(tmp.value);
		}
		return false;
	}

	/**
	 * 
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("type=").append(type).append(";");
		buffer.append("name=").append(name).append(";");
		buffer.append("value=").append(value);
		return buffer.toString();
	}

}

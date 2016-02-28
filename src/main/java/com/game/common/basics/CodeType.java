package com.game.common.basics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author zhaolianjun
 *
 */
public abstract class CodeType implements Serializable{
	private static final long serialVersionUID = 2081494029581582199L;
	private String codeTypeName = null;
	private ArrayList<Code> list = new ArrayList<Code>();
	private HashMap<String, Code> map = new HashMap<String, Code>();

	/**
	 * 
	 */
	public CodeType() {
		this.codeTypeName = getClass().getName().replaceAll("^([^\\.]+\\.)*", "");
	}

	public String getName() {
		return codeTypeName;
	}

	public Code getDefaultValue() {
		return new Code(this);
	}

	protected void add(Code code) {
		if (!map.containsKey(code.getValue())) {
			list.add(code);
			map.put(code.getValue(), code);
		}
	}

	protected void add(String value, String name) {
		new Code(this, value, name);
	}

	protected void remove(Code code) {
		if (map.containsKey(code.getValue())) {
			list.remove(code);
			map.remove(code);
		}
	}

	protected void removeAll() {
		list.clear();
		map.clear();
	}

	public Code get(String value) {
		return (Code) map.get(value);
	}

	public List<Code> getCodeList() {
		return list;
	}

}

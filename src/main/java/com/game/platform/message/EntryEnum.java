package com.game.platform.message;

/**
 * 邮件发放物品分类
 * @author kevin
 *
 */
public enum EntryEnum {
	NORMAL("0", "", ""),//普通邮件, 不需要领取
	GOLD("1", "N币", ""), 
	PRACTIC("2", "实物", ""), 
	VIRTUAL("3", "虚拟物品", "");

	private String identify;
	private String name;
	private String icon;

	private EntryEnum(String identify, String name, String icon) {
		this.identify = identify;
		this.name = name;
		this.icon = icon;
	}

	public String getIdentify() {
		return identify;
	}

	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}
}

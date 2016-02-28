package com.game.common.utility;

public enum Channel {
	
	Product("1000"), Guild("2000"), User("3000"), Platform("4000"), Game("5000");

	private String value;

	private Channel(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static void main(String[] args) {
		System.out.println(Channel.Guild.value);
	}
}

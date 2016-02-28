package com.game.user.utils;

import java.util.Random;

public class RandomHelper {
	public static String createUserName() {
		Random random = new Random();
		int number = -1;
		number = (int) (random.nextDouble() * (10000000 - 1000000) + 1000000);
		return "u" + number;
	}

	public static String createPassword() {
		Random random = new Random();
		int number = -1;
		number = (int) (random.nextDouble() * (1000000 - 100000) + 100000);
		return "" + number;
	}
	
	public static void main(String[] args) {
		System.out.println(createUserName());
		System.out.println(createPassword());
		Random random = new Random();
		String serverId = "00" + (random.nextInt(20) + 1);
		System.out.println(serverId);
	}
}

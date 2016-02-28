package com.game.common.utility;

import java.util.Random;
import java.util.UUID;

import com.game.common.basics.security.DESUtils;

public class RandomCreator {

	public static String numberCreator() {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < 9; i++) result = result * 10 + array[i];
		return result + "" + System.currentTimeMillis();
	}
	
	public static void main(String[] args) {
		System.out.println(numberCreator());
		String token = createToken();
		System.out.println(token);
		System.out.println(DESUtils.decode(token));
	}
	
	
	public static Integer genRandomNo(int param) {
		Random random = new Random(); //创建随机对象 	
		int randNum = 0;
		int nextInt=(int)Math.pow(10, param);
		int startInt=(int)Math.pow(10, (param-1));
		randNum = getRandNum(random, nextInt);
		if(randNum<startInt){		
			randNum=nextInt-randNum;
		}
		return randNum;
	}
	
	private static Integer getRandNum(Random random, int nextInt) {
		int randNum = random.nextInt(nextInt);
		if(randNum == 0){
			randNum = getRandNum(random, nextInt);
		}
		return randNum;
	}

	public static String createToken(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0, 8);
		long timestamp = System.currentTimeMillis();
		String token = uuid + "" + timestamp;
		try {
			return DESUtils.encode(token);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

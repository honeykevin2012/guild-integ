package com.game.common.utility;
/**
 * 微信红包分配算法
 * 
 * @author Michael282694
 * */
public class RedpacketUtil {

	public static long[] generate(long total_money,int total_people,int min_money) {
		// TODO Auto-generated method stub

		long[] result = new long[total_people];
		if(total_money<total_people){
			return null;
		}
		for (int i = 0; i < total_people - 1; i++) {
			int j = i + 1;
			double safe_money = (total_money - (total_people - j) * min_money)
					/ (total_people - j);
			double tmp_money = (Math.random()
					* (safe_money * 100 - min_money * 100) + min_money * 100) / 100;
			long tmp_final=Math.round(tmp_money);
			tmp_final=(tmp_final==0?min_money:tmp_final);
			result[i]=tmp_final;
			total_money = total_money - tmp_final;
//			System.out.format("第 %d 个红包： %d 元，剩下： %.2f 元\n", j, tmp_final,
//					total_money);
		
		}
		result[total_people - 1]=Math.round(total_money);
//		System.out.format("第 %d 个红包： %.2f 元，剩下： 0 元\n", total_people,
//				total_money);
		return result;
	}
}
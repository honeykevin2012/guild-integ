package com.game.user.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.game.common.basics.ApplicationContextLoader;
import com.game.common.utility.DateUtils;
import com.game.init.setting.SettingAttribute;
import com.game.user.domain.UserSign;
import com.game.user.persistence.dao.UserSignDao;

/**
 * 移位和积分规则类
 */
public class UserSignRule {
	private static final int LoginPiont = 1;//登录赠送积分
	private static final int SignPoint = 2;//签到赠送积分
	private static UserSignDao signDao = (UserSignDao) ApplicationContextLoader.getBean("userSignDao");

	public static Long moveByte(long oldHistory, long moveAmonut) {
		long moveResult = oldHistory << moveAmonut;
		long result = Long.parseLong(toFullBinaryString(moveResult), 2) + 1;
		return result;
	}

	/**
	 * 读取
	 * @param num
	 * @return
	 */
	private static String toFullBinaryString(long num) {
		final int size = 42;
		char[] chs = new char[size];
		for (int i = 0; i < size; i++) {
			chs[size - 1 - i] = (char) (((num >> i) & 1) + '0');
		}
		return new String(chs);
	}

	/**
	 * 按照积分规则，得到积分 , 积分规则如下： 签到功能说明 1.每天只能签到一次（按服务器系统时间为准） 2.连续签到
	 * 额外奖励积分，同种礼包只能使用一次 3.连续签到10天，一次性奖励2积分 4.连续签到30天，一次性奖励10积分
	 * 5.连续签到60天，一次性奖励30积分 6.连续签到90天，一次性奖励100积分
	 * 
	 * @param signCount
	 *            连续签到次数
	 * @return 增加的积分
	 */
	public static int getScoreByRule(int signCount) {
		int addScore = 1;

		if (signCount == 10) {
			addScore += 10;
		} else if (signCount == 30) {
			addScore += 20;
		} else if (signCount == 60) {
			addScore += 50;
		} else if (signCount == 90) {
			addScore += 100;
		}
		return addScore;
	}
	/**
	 * 
	 * @param userId
	 * @param isLogin true:login   false:sign
	 */
	public static UserSign signPoint(Integer userId){
		UserSign sign = signDao.selectByUserId(userId);
		Date date = new Date();
		if(sign == null) {//该用户从未登录过系统
			sign = new UserSign();
			sign.setUserId(userId);
			sign.setLastLoginTime(date);
			sign.setLastSignTime(date);
			sign.setPoint(SignPoint);
			sign.setSignCount(1);
			signDao.insert(sign);
			//用户签到奖励
			AwardUserHelper.awardUser(SettingAttribute.SETTING_SIGN, userId);
			return sign;
		}
		Timestamp todayStartTimeStamp = new Timestamp(getStartTime());
		if(sign.getLastSignTime() == null) {//第一次签到
			sign.setLastSignTime(date);
			sign.setSignCount(1);
			sign.setPoint(sign.getPoint() + SignPoint);
		}else{
			long lastSignTimeMillis = sign.getLastSignTime().getTime();
			Timestamp lastSignTimeStamp = new Timestamp(lastSignTimeMillis);
			if(todayStartTimeStamp.after(lastSignTimeStamp)){//今天未签到
				sign.setLastSignTime(date);//更新签到时间
				long missDays = (System.currentTimeMillis() - lastSignTimeMillis) / (24*60*60*1000);
				if(missDays == 1){//计算连续签到次数
					sign.setSignCount(sign.getSignCount() + 1);
				}else{
					sign.setSignCount(1);
				}
				sign.setPoint(sign.getPoint() + getScoreByRule(sign.getSignCount()));//计算积分
				if(sign.getSignCount() > 90) sign.setSignCount(1); //连续签到次数大于90天，签到次数重置为1，重新开始计算
			}
		}
		signDao.update(sign);
		//用户签到增加积分记录
		AwardUserHelper.awardUser(SettingAttribute.SETTING_SIGN, userId);
		return sign;
	}
	
	public static UserSign loginPoint(Integer userId){
		UserSign sign = signDao.selectByUserId(userId);
		Date date = new Date();
		if(sign == null) {//该用户从未登录过系统
			sign = new UserSign();
			sign.setUserId(userId);
			sign.setLastLoginTime(date);
			sign.setLastSignTime(null);
			sign.setPoint(LoginPiont);
			sign.setSignCount(0);
			signDao.insert(sign);
			return sign;
		}
		Timestamp lastLoginTimeStamp = new Timestamp(sign.getLastLoginTime().getTime());
		Timestamp todayStartTimeStamp = new Timestamp(getStartTime());
		if(todayStartTimeStamp.after(lastLoginTimeStamp)){//今天未登录过
			//更新最后登录时间， 同时增加积分
			sign.setLastLoginTime(date);
			sign.setPoint(sign.getPoint() + LoginPiont);
		}
		signDao.update(sign);
		return sign;
	}
	
	public static boolean isSigned(Integer userId){
		UserSign sign = signDao.selectByUserId(userId);
		if(sign == null) return false;
		long lastSignTimeMillis = sign.getLastSignTime().getTime();
		Timestamp todayStartTimeStamp = new Timestamp(getStartTime());
		Timestamp lastSignTimeStamp = new Timestamp(lastSignTimeMillis);
		if(todayStartTimeStamp.after(lastSignTimeStamp)) return false;
		return true;
	}
	
	private static Long getStartTime(){
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}
	
	public static void main(String[] args) {
		long result = moveByte(1, 4);

		System.out.println("移位结果：" + result);

		System.out.println("连续签到次数9：所增加的积分：" + getScoreByRule(9));
		System.out.println("连续签到次数10：所增加的积分：" + getScoreByRule(10));
		System.out.println("连续签到次数29：所增加的积分：" + getScoreByRule(29));
		System.out.println("连续签到次数30：所增加的积分：" + getScoreByRule(30));
		System.out.println("连续签到次数59：所增加的积分：" + getScoreByRule(59));
		System.out.println("连续签到次数60：所增加的积分：" + getScoreByRule(60));
		System.out.println("连续签到次数89：所增加的积分：" + getScoreByRule(89));
		System.out.println("连续签到次数90：所增加的积分：" + getScoreByRule(90));
		System.out.println("连续签到次数91：所增加的积分：" + getScoreByRule(91));
		
		System.out.println(DateUtils.format("2015-05-31 11:30:22", "yyyy-MM-dd HH:mm:ss").getTime());
		System.out.println(DateUtils.format("2015-06-01 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime());
		System.out.println(getStartTime());
		
		Timestamp lastModifyTimeStamp = new Timestamp(1433043022000L);
		Timestamp todayStartTimeStamp = new Timestamp(1433088000000L);
		System.out.println(todayStartTimeStamp.after(lastModifyTimeStamp));
	}

}
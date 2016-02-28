package com.game.user.utils;

import java.util.HashMap;
import java.util.Map;

import com.game.common.basics.ApplicationContextLoader;
import com.game.init.setting.SettingAttribute;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserLevelUpTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.user.domain.User;
import com.game.user.domain.UserCredits;
import com.game.user.domain.UserVipGroup;
import com.game.user.persistence.dao.UserCreditsDao;
import com.game.user.persistence.dao.UserDao;
import com.game.user.persistence.dao.UserVipGroupDao;

/**
 * 用户属性成长计算
 * @author kevin
 *
 */
public class AwardUserHelper {
	private static UserDao userDao = (UserDao) ApplicationContextLoader.getBean("userDao");
	private static UserCreditsDao userCreditsDao = (UserCreditsDao) ApplicationContextLoader.getBean("userCreditsDao");
	private static UserVipGroupDao groupDao = (UserVipGroupDao) ApplicationContextLoader.getBean("userVipGroupDao");

	/**
	 * 固定成长值
	 * @param attr 属性
	 * @param userId 用户ID
	 * @return
	 */
	public static String awardUser(String attr, Integer userId){
		if(attr == null || "".equals(attr)) return null;
		if(SettingAttribute.SETTING_LOGIN.equals(attr)){
			if(countActive(SettingAttribute.SETTING_LOGIN, userId) == 0){
				persistAward(SettingAttribute.SETTING_LOGIN, userId);
			}
		}else{
			persistAward(attr, userId);
		}
		return "success";
	}
	
	//设置用户积分
	private static void persistAward(String key, Integer userId){
		SettingAttribute.BasicSettingEntity entity = SettingAttribute.getValue(key);
		if(entity == null) return;
		String value = entity.getExp();
		if(value == null || "".equals(value)) return;
		UserCredits credits = new UserCredits();
		credits.setActive(key);
		credits.setCredits(Integer.valueOf(value));
		credits.setUserId(userId);
		credits.setCreateTime(new java.util.Date());
		int result = userCreditsDao.insert(credits);//插入积分记录
		if(result == 1){
			int countCredits = userCreditsDao.selectCreditsByUser(userId);//查询用户总积分
			UserVipGroup group = groupDao.selectCurrentLevel(countCredits);//根据总积分检查当前用户组是否可以达到升级条件，查询结果为当前积分可匹配的用户组
			if(group != null){
				User user = userDao.select(userId);//查询当前用户
				if(!user.getGroupId().equals(group.getId()) && (countCredits > user.getGroup().getPoint())){//对比是否相等，若不相等则表示用户达到升级条件，则进行升级
					user.setGroupId(group.getId());
					user.setGroupIcon(group.getIcon());
					user.setGroupLevel(group.getLevel());
					user.setGroupName(group.getName());
					user.setBalance(user.getBalance());
					userDao.update(user);
					//发送晋级邮件
					if(group.getAmount() > 0){
						MessageCore core = new MessageCore();
						PracticVirtual practic = new PracticVirtual();
						practic.setId(0);
						practic.setQuantity(group.getAmount());
						core.setAdapter(new MessageUserLevelUpTemplate()).transfer(practic, user.getId(), group.getLevel()).send();
					}
				}
			}
		}
	}
	
	/**
	 * 检查当天动作的参与次数，例：若动作为登录，则有效次数为1
	 * @param type
	 * @param userId
	 * @return
	 */
	private static int countActive(String type, Integer userId){
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId + "");
		map.put("type", type);
		int count = userCreditsDao.selectActiveCount(map);
		return count;
	}
}

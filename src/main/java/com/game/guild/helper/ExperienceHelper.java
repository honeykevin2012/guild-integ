package com.game.guild.helper;

import java.util.List;

import com.game.common.basics.ApplicationContextLoader;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildMemberSignin;
import com.game.guild.persistence.dao.GuildInfoDao;
import com.game.guild.persistence.dao.GuildMemberSigninDao;
import com.game.init.CacheListener;

/**
 * 公会经验值计算
 * @author kevin
 */
public class ExperienceHelper {
	/**公会财富级别比例*/
//	private static final String GuildLevelAmountRatioKey = "guild_level_amount_ratio";
	/**公会成员数*/
//	private static final String GuildLevelActiveMemberCountKey = "guild_level_active_member_count";
	/**公会商城购买数*/
//	private static final String GuildLevelActiveBuyTimesKey = "guild_level_active_buy_times";
	/**公会成员签到数*/
	private static final String GuildLevelActiveMemberSigninKey = "guild_level_active_member_signin";
	/**公会成员捐款*/
	private static final String GuildLevelGiveAmountKey = "guild_level_give_amount";
	
//	private static double guildLevelAmountRatio = 0.00;
//	private static double guildLevelActiveMemberCountRatio = 0.00;
//	private static double guildLevelActiveBuyTimesRatio = 0.00;
	private static double guildLevelActiveMemberSigninRatio = 0.00;
	private static double GuildLevelGiveAmountRatio = 0.00;
	
//	private static int BASE_RATIO = 100;
	
	static{
//		guildLevelAmountRatio = Double.parseDouble(DictListener.DICT_MAP.get(GuildLevelAmountRatioKey));
//		guildLevelActiveMemberCountRatio = Double.parseDouble(DictListener.DICT_MAP.get(GuildLevelActiveMemberCountKey));
//		guildLevelActiveBuyTimesRatio = Double.parseDouble(DictListener.DICT_MAP.get(GuildLevelActiveBuyTimesKey));
		guildLevelActiveMemberSigninRatio = Double.parseDouble(CacheListener.DICT_MAP.get(GuildLevelActiveMemberSigninKey));
		GuildLevelGiveAmountRatio = Double.parseDouble(CacheListener.DICT_MAP.get(GuildLevelGiveAmountKey));
	}
	
	private static GuildInfoDao guildDao = (GuildInfoDao) ApplicationContextLoader.getBean("guildInfoDao");
	private static GuildMemberSigninDao guildMemberSigninDao = (GuildMemberSigninDao) ApplicationContextLoader.getBean("guildMemberSigninDao");
	
	/**
	 * 经验计算工具
	 * @param id 公会ID
	 */
	public ExperienceHelper(Integer id){
		GuildInfo guild = guildDao.select(id);
		if(guild.getCurrency() == null){
			this.amount = 0l;
		}else{
			this.amount = guild.getCurrency();
		}
		this.experience = guild.getExp();
		this.memberCount = guild.getMemberCount();
		GuildMemberSignin signin = new GuildMemberSignin();
		signin.setGuildId(id);
		List<GuildMemberSignin> signinList = guildMemberSigninDao.selectByEntity(signin);
		if(signinList == null) {
			this.signinCount = 0;
		}else {
			this.signinCount = signinList.size();
		}
		this.buyTimes = 10;
	}
	
	public static int getSigninExp(){
		return (int) guildLevelActiveMemberSigninRatio;
	}
	
	public static int getGiveAmountExp(Long giveAmount){
		double result = giveAmount * GuildLevelGiveAmountRatio;
		return (int) (result == 0 ? 1 : result);
	}
	
	private Long amount;//公会财富
	private double giveAmount;
	private int memberCount;//成员数
	private int signinCount;//签到数
	private int buyTimes;//购买次数
	private int experience;
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public int getSigninCount() {
		return signinCount;
	}
	public void setSigninCount(int signinCount) {
		this.signinCount = signinCount;
	}
	public int getBuyTimes() {
		return buyTimes;
	}
	public void setBuyTimes(int buyTimes) {
		this.buyTimes = buyTimes;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public double getGiveAmount() {
		return giveAmount;
	}
	public void setGiveAmount(double giveAmount) {
		this.giveAmount = giveAmount;
	}
}

package com.game.init.setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.game.init.CommonXMLValues;

public class SettingAttribute {

	public static final String SETTING_LOGIN = "login"; // 1.用户登录获得3积分。
	public static final String SETTING_REGISTER = "register"; // 1.注册账号获得2积分。
	public static final String SETTING_SIGN = "sign"; // 2.每日签到，领取2积分
	public static final String SETTING_BIND_MOBILE = "bindmMobile"; // 4.绑定手机获得10积分。
	public static final String SETTING_DOWNLOAD_GAME = "downloadGame"; // 5.下载游戏得5积分。
	public static final String SETTING_FEEDBACK = "feedback"; // 7.反馈客户端问题2积分。
	public static final String SETTING_EDIT_MOOD = "editMood"; // 9.更新今日心情获得2积分（心情字数不得超过150字）
	public static final String SETTING_START_CLIENT = "startClient"; // 11.启动NB客户端获得3经验（每日校验，成功启动后当日只获得一次经验）
	public static final String SETTING_SHARE_CLIENT = "shareClient"; // 1.分享客户端获得5积分。
	public static final String SETTING_SHARE_ACTIVE = "shareActive"; // 2.分享活动获得2积分。
	public static final String SETTING_SHARE_PRODUCT = "shareProduct"; // 3.分享商城商品获得1积分。
	public static final String SETTING_SHARE_MEDIA = "shareMedia"; // 4.分享视频及新闻获得1积分。
	public static final String SETTING_CREATE_GUILD = "createGuild"; // 5.创建公会获得10积分
	public static final String SETTING_BASIC = "/setting/basicSetPoints/point";
	private static Map<String, BasicSettingEntity> basicSetting = new HashMap<String, BasicSettingEntity>();

	private static void loadSetting() {
		List<Element> nodes = CommonXMLValues.getNodeList(SETTING_BASIC);
		if (nodes != null && !nodes.isEmpty()) {
			for (Element node : nodes) {
				String key = node.attributeValue("key");
				String exp = node.attributeValue("exp");
				BasicSettingEntity entity = new BasicSettingEntity();
				entity.setKey(key);
				entity.setExp(exp);
				basicSetting.put(key, entity);
			}
		}
	}
	
	public static BasicSettingEntity getValue(String key){
		if (basicSetting.isEmpty()) loadSetting();
		BasicSettingEntity entity = basicSetting.get(key);
		return entity;
	}

	/**
	 * 映射基础设置的元素对象
	 * @author kevin
	 */
	public static class BasicSettingEntity {
		private String key;
		private String exp;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getExp() {
			return exp;
		}

		public void setExp(String exp) {
			this.exp = exp;
		}
	}
	public static void main(String[] args) {
		System.out.println(getValue("sign").getExp());
	}

	// public static final String settingLogin = "setting_login"; //
	// 1.用户登录获得3积分。
	// public static final String settingRegister = "setting_register"; //
	// 1.注册账号获得2积分。
	// public static final String settingSignPoints = "setting_sign_points"; //
	// 2.每日签到，领取2积分
	// public static final String settingSignNb = "setting_sign_nb"; //
	// 2.每日签到，领取5N币。
	// public static final String settingOpenGame = "setting_open_game"; //
	// 3.从客户端打开游戏获得2积分。
	// public static final String settingBindMobile = "setting_bind_mobile"; //
	// 4.绑定手机获得10积分。
	// public static final String settingDownloadGame = "setting_download_game";
	// // 5.下载游戏得5积分。
	// public static final String settingSearchSite = "setting_search_site"; //
	// 6.当日站内搜索获得2积分。
	// public static final String settingFeedback = "setting_feed_back"; //
	// 7.反馈客户端问题2积分。
	// public static final String settingReward = "setting_reward"; //
	// 8.日常抽奖活动（1-6积分，1-10N币，X道具或礼包），每日两次免费抽奖，间隔2小时。未满条件抽奖单次消耗1N币。
	// public static final String settingEditMood = "setting_edit_mood"; //
	// 9.更新今日心情获得2积分（心情字数不得超过150字）
	// public static final String settingEditMoodShareImage =
	// "setting_editmood_share_image"; // 9.分享图片额外获得1积分
	// public static final String settingAddFriend = "setting_add_friend"; //
	// 10.加好友获得1积分。（网页和客户端增加发现页或心情墙页，按时间和点赞数排序。）
	// public static final String settingStartClient = "setting_start_client";
	// // 11.启动NB客户端获得3经验（每日校验，成功启动后当日只获得一次经验）
	//
	// public static final String settingShareClient = "setting_share_client";
	// // 1.分享客户端获得5积分。
	// public static final String settingShareActive = "setting_share_active";
	// // 2.分享活动获得2积分。
	// public static final String settingShareProduct = "setting_share_product";
	// // 3.分享商城商品获得1积分。
	// public static final String settingShareMedia = "setting_share_media"; //
	// 4.分享视频及新闻获得1积分。
	// public static final String settingCreateGuildPoints =
	// "setting_create_guild_points"; // 5.创建公会获得10积分
	// public static final String settingCreateGuildNb =
	// "setting_create_guild_nb"; // 5.创建公会获得10N币。
	// public static final String settingShareTopic = "setting_share_topic"; //
	// 6.分享论坛置顶活动帖获得3积分。
	//
	// public static final String settingExchangeNbRatio =
	// "setting_exchange_nb_ratio"; // 100N币=1元人民币
	//
	// public static final String guildLevelGiveAmount =
	// "guild_level_give_amount"; // 用户活跃度：成员捐款站比例10%
	// public static final String guildLevelActiveBuyTimes =
	// "guild_level_active_buy_times"; // 用户活跃度：公会商城购买次数 20%
	// public static final String guildLevelActiveMemberSignin =
	// "guild_level_active_member_signin"; //用户活跃度：成员签到数固定增加10
	// public static final String guildLevelActiveMemberCount =
	// "guild_level_active_member_count"; // 用户活跃度：公会成员数比例 5%
	// public static final String guildLevelAmountRatio =
	// "guild_level_amount_ratio"; // 公会财富所占公会级别升级的比例 公会活跃度的比例则为：70%
}

package com.game.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.game.common.basics.ApplicationContextLoader;
import com.game.guild.domain.GuildLevel;
import com.game.guild.persistence.dao.GuildLevelDao;
import com.game.platform.domain.PlatformDict;
import com.game.platform.domain.PlatformGameApp;
import com.game.platform.persistence.dao.PlatformDictDao;
import com.game.platform.persistence.dao.PlatformGameAppDao;
import com.game.user.domain.UserVipGroup;
import com.game.user.persistence.dao.UserVipGroupDao;

@Service
public class CacheListener implements ApplicationListener<ContextRefreshedEvent> {

	public static Map<String, String> DICT_MAP = new HashMap<String, String>();//字典总表数据
	public static Map<String, PlatformGameApp> CAHCE_GAME = new HashMap<String, PlatformGameApp>();//获取游戏的查询、扣款、退款接口地址
	public static Map<String, GuildLevel> GUILD_LEVEL = new HashMap<String, GuildLevel>();//获取游戏的查询、扣款、退款接口地址
	public static Map<Integer, UserVipGroup> USER_VIP_GROUP = new HashMap<Integer, UserVipGroup>();
	private static PlatformDictDao dictDao;
	private static PlatformGameAppDao gameDao;
	private static GuildLevelDao guildLevelDao;
	private static UserVipGroupDao groupDao;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		dictDao = (PlatformDictDao) ApplicationContextLoader.getBean("platformDictDao");
		gameDao = (PlatformGameAppDao) ApplicationContextLoader.getBean("platformGameAppDao");
		guildLevelDao = (GuildLevelDao) ApplicationContextLoader.getBean("guildLevelDao");
		groupDao = (UserVipGroupDao) ApplicationContextLoader.getBean("userVipGroupDao");
		dictLoad();
		loadGame();
	}

	public static void dictLoad() {
		List<PlatformDict> list = dictDao.selectAll();
		for (PlatformDict dict : list) {
			DICT_MAP.put(dict.getCode(), dict.getValue());
		}
	}
	
	public static String getDictValue(String key){
		if(DICT_MAP.isEmpty()) dictLoad();
		return DICT_MAP.get(key);
	}
	
	public static void clear(){
		DICT_MAP.clear();
		CAHCE_GAME.clear();
		GUILD_LEVEL.clear();
		USER_VIP_GROUP.clear();
	}

	/**
	 * 获取游戏的查询、扣款、退款接口地址
	 * 
	 * @return
	 */
	public static void loadGame() {
		List<PlatformGameApp> list = gameDao.selectAll();
		for (PlatformGameApp game : list) {
			CAHCE_GAME.put(game.getCode().toString(), game);
		}
	}
	
	public static PlatformGameApp getGame(String key){
		if(CAHCE_GAME.isEmpty()) loadGame();
		return CAHCE_GAME.get(key);
	}
	
	public static void loadGuildLevelCache(){
		List<GuildLevel> list = guildLevelDao.selectAll();
		for(GuildLevel level : list){
			GUILD_LEVEL.put(level.getId().toString(), level);
		}
	}
	
	public static GuildLevel getGuildLevel(String key){
		if(GUILD_LEVEL.isEmpty()) loadGuildLevelCache();
		return GUILD_LEVEL.get(key);
	}
	
	
	
	public static void loadUserVipGroup(){
		List<UserVipGroup> list = groupDao.selectAll();
		for (UserVipGroup group : list) {
			USER_VIP_GROUP.put(group.getId(), group);
		}
	}
	
	public static UserVipGroup getUserVipGroup(Integer key){
		if(USER_VIP_GROUP.isEmpty()) loadUserVipGroup();
		return USER_VIP_GROUP.get(key);
	}
}
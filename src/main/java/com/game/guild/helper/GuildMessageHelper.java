package com.game.guild.helper;

import com.game.common.basics.ApplicationContextLoader;
import com.game.guild.domain.GuildHistoryMessage;
import com.game.guild.persistence.dao.GuildHistoryMessageDao;

/**
 * 公会历史消息
 * 
 * @author kevin
 */
public class GuildMessageHelper {
	private static GuildHistoryMessageDao dao = (GuildHistoryMessageDao) ApplicationContextLoader.getBean("guildHistoryMessageDao");

	/**
	 * 公会消息记录
	 * 
	 * @param type 消息类型
	 * @param guildId 公会id
	 * @param from
	 * @param to
	 * @param content 内容
	 */
	public static void saveMsg(Integer type, Integer guildId, Integer from, Integer to, String content) {

		GuildHistoryMessage msg = new GuildHistoryMessage();
		msg.setGuildId(guildId);
		msg.setType(type);
		msg.setFrom(from);
		msg.setTo(to);
		msg.setContent(content);
		dao.insert(msg);
	}
}

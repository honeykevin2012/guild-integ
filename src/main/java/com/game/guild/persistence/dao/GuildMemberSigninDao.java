package com.game.guild.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildMemberSignin;

public interface GuildMemberSigninDao extends BaseAccessDao<GuildMemberSignin> {
	public List<GuildMemberSignin> selectIsSignin(GuildMemberSignin signin);
	public int deleteByUserAndGuild(Map<String, Integer> map) throws DataAccessException;
	public int selectSigninCount(Integer guildId) throws DataAccessException;
	public int deleteByGuildId(Integer guildId) throws DataAccessException;
}

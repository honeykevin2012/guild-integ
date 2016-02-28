package com.game.guild.persistence.dao;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildMemberLevel;

public interface GuildMemberLevelDao extends BaseAccessDao<GuildMemberLevel> {
	public GuildMemberLevel selectNextLevel(Integer exp) throws DataAccessException;
	public GuildMemberLevel selectDefaultLevel();
}

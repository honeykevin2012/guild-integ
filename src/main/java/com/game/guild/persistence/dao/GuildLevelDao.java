package com.game.guild.persistence.dao;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildLevel;

public interface GuildLevelDao extends BaseAccessDao<GuildLevel> {
	public GuildLevel selectNextLevel(Integer exp) throws DataAccessException;
	public GuildLevel selectCurrentLevel(Integer amount) throws DataAccessException;
	public GuildLevel selectDefaultLevel();
}

package com.game.guild.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildItemGive;

public interface GuildItemGiveDao extends BaseAccessDao<GuildItemGive> {
	public List<Map<String,String>> selectRealMyitem(PageQuery querys) throws DataAccessException;
	public int updateReceiveItem(GuildItemGive entity) throws DataAccessException;
}

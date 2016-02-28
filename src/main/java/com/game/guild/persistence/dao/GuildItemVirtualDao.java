package com.game.guild.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildItemVirtual;

public interface GuildItemVirtualDao extends BaseAccessDao<GuildItemVirtual> {
	public int insertBatch(List<GuildItemVirtual> list) throws DataAccessException;
	public int updateGiveUser(GuildItemVirtual guildItemVirtual) throws DataAccessException;
	public int updateItemMove(GuildItemVirtual guildItemVirtual) throws DataAccessException;
	public int updateStockMove(GuildItemVirtual guildItemVirtual) throws DataAccessException;
	public int updateGetGift(GuildItemVirtual guildItemVirtual) throws DataAccessException;
	public List<Map<String,String>> selectMyitem(PageQuery querys) throws DataAccessException;
	public void insertVirtualItems(List<GuildItemVirtual> list) throws DataAccessException;
}

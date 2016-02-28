package com.game.guild.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildItemsStock;

public interface GuildItemsStockDao extends BaseAccessDao<GuildItemsStock> {
	public List<GuildItemsStock> selectGetGiftList(Map<String,Object> map) throws DataAccessException;
	public int updateGetGift(int  stockId) throws DataAccessException;
}

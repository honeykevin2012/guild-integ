/*
 * @author yangchengwei
 * created on 2015-03-04
 */
 
package com.game.guild.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildItemsStock;
import com.game.guild.persistence.dao.GuildItemsStockDao;

@Service
public class GuildItemsStockService {
	
	@Autowired
	private GuildItemsStockDao guildItemsStockDao;
	
	public List<GuildItemsStock> query(PageQuery querys) throws DataAccessException {
		try {
			return guildItemsStockDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildItemsStock> selectAll() throws DataAccessException {
		try {
			return guildItemsStockDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildItemsStock> selectByEntity(GuildItemsStock guildItemsStock) throws DataAccessException {
		try {
			return guildItemsStockDao.selectByEntity(guildItemsStock);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildItemsStock> selectGetGiftList(Integer guildId,Integer userId) throws DataAccessException {
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("guildId", guildId);
			map.put("userId", userId);
			return guildItemsStockDao.selectGetGiftList(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}	

	public GuildItemsStock select(Integer id) throws DataAccessException {
		try {
			return guildItemsStockDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(GuildItemsStock guildItemsStock) throws DataAccessException {
		try {
			guildItemsStockDao.insert(guildItemsStock);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GuildItemsStock guildItemsStock) throws DataAccessException {
		try {
			guildItemsStockDao.update(guildItemsStock);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			guildItemsStockDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildItemsStockDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

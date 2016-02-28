/*
 * @author yangchengwei
 * created on 2015-03-03
 */

package com.game.guild.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildItem;
import com.game.guild.persistence.dao.GuildItemDao;

@Service
public class GuildItemService {

	@Autowired
	private GuildItemDao guildItemDao;


	public List<GuildItem> query(PageQuery querys) throws DataAccessException {
		try {
			return guildItemDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildItem> selectAll() throws DataAccessException {
		try {
			return guildItemDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildItem> selectByEntity(GuildItem guildItem) throws DataAccessException {
		try {
			return guildItemDao.selectByEntity(guildItem);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public GuildItem select(Integer id) throws DataAccessException {
		try {
			return guildItemDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildItem> selectByTypeId(Integer productId, Integer guildId) throws DataAccessException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productId);
			map.put("guildId", guildId);
			return guildItemDao.selectByTypeId(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void update(GuildItem guildItem) throws DataAccessException {
		try {
			guildItemDao.update(guildItem);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void delete(Integer id) throws DataAccessException {
		try {
			guildItemDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void deleteByTypeId(Integer productId, Integer guildId) throws DataAccessException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productId);
			map.put("guildId", guildId);
			guildItemDao.deleteByTypeId(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildItemDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

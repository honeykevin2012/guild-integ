/*
 * @author zhaolianjun
 * created on 2015-03-17
 */
 
package com.game.guild.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildLevel;
import com.game.guild.persistence.dao.GuildLevelDao;

@Service
public class GuildLevelService {
	
	@Autowired
	private GuildLevelDao guildLevelDao;
	
	public List<GuildLevel> query(PageQuery querys) throws DataAccessException {
		try {
			return guildLevelDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildLevel> selectAll() throws DataAccessException {
		try {
			return guildLevelDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildLevel> selectByEntity(GuildLevel guildLevel) throws DataAccessException {
		try {
			return guildLevelDao.selectByEntity(guildLevel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GuildLevel select(Integer id) throws DataAccessException {
		try {
			return guildLevelDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public GuildLevel selectDefaultLevel() throws DataAccessException {
		try {
			return guildLevelDao.selectDefaultLevel();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public GuildLevel selectNextLevel(Integer exp) throws DataAccessException{
		try {
			return guildLevelDao.selectNextLevel(exp);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void insert(GuildLevel guildLevel) throws DataAccessException {
		try {
			guildLevelDao.insert(guildLevel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GuildLevel guildLevel) throws DataAccessException {
		try {
			guildLevelDao.update(guildLevel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			guildLevelDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildLevelDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

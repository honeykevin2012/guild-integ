/*
 * @author zhaolianjun
 * created on 2015-05-05
 */
 
package com.game.guild.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildMemberLevel;
import com.game.guild.persistence.dao.GuildMemberLevelDao;

@Service
public class GuildMemberLevelService {
	
	@Autowired
	private GuildMemberLevelDao guildMemberLevelDao;
	
	public List<GuildMemberLevel> query(PageQuery querys) throws DataAccessException {
		try {
			return guildMemberLevelDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildMemberLevel> selectAll() throws DataAccessException {
		try {
			return guildMemberLevelDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildMemberLevel> selectByEntity(GuildMemberLevel guildMemberLevel) throws DataAccessException {
		try {
			return guildMemberLevelDao.selectByEntity(guildMemberLevel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GuildMemberLevel select(Integer id) throws DataAccessException {
		try {
			return guildMemberLevelDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public GuildMemberLevel selectDefaultLevel() throws DataAccessException {
		try {
			return guildMemberLevelDao.selectDefaultLevel();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public GuildMemberLevel selectNextLevel(Integer exp) throws DataAccessException{
		try {
			return guildMemberLevelDao.selectNextLevel(exp);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void insert(GuildMemberLevel guildMemberLevel) throws DataAccessException {
		try {
			guildMemberLevelDao.insert(guildMemberLevel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GuildMemberLevel guildMemberLevel) throws DataAccessException {
		try {
			guildMemberLevelDao.update(guildMemberLevel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			guildMemberLevelDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildMemberLevelDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

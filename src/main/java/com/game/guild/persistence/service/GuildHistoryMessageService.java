/*
 * @author yangchengwei
 * created on 2015-08-11
 */
 
package com.game.guild.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildHistoryMessage;
import com.game.guild.persistence.dao.GuildHistoryMessageDao;

@Service
public class GuildHistoryMessageService {
	
	@Autowired
	private GuildHistoryMessageDao guildHistoryMessageDao;
	
	public List<GuildHistoryMessage> query(PageQuery querys) throws DataAccessException {
		try {
			return guildHistoryMessageDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildHistoryMessage> selectAll() throws DataAccessException {
		try {
			return guildHistoryMessageDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildHistoryMessage> selectByEntity(GuildHistoryMessage guildHistoryMessage) throws DataAccessException {
		try {
			return guildHistoryMessageDao.selectByEntity(guildHistoryMessage);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GuildHistoryMessage select(Integer id) throws DataAccessException {
		try {
			return guildHistoryMessageDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(GuildHistoryMessage guildHistoryMessage) throws DataAccessException {
		try {
			guildHistoryMessageDao.insert(guildHistoryMessage);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GuildHistoryMessage guildHistoryMessage) throws DataAccessException {
		try {
			guildHistoryMessageDao.update(guildHistoryMessage);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			guildHistoryMessageDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildHistoryMessageDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
}

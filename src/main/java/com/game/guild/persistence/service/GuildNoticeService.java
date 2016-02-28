/*
 * @author yangchengwei
 * created on 2015-03-05
 */
 
package com.game.guild.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildNotice;
import com.game.guild.persistence.dao.GuildNoticeDao;

@Service
public class GuildNoticeService {
	
	@Autowired
	private GuildNoticeDao guildNoticeDao;
	
	public List<GuildNotice> query(PageQuery querys) throws DataAccessException {
		try {
			return guildNoticeDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildNotice> selectAll() throws DataAccessException {
		try {
			return guildNoticeDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildNotice> selectByEntity(GuildNotice guildNotice) throws DataAccessException {
		try {
			return guildNoticeDao.selectByEntity(guildNotice);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<GuildNotice> selectByGuild(Integer id) throws DataAccessException {
		try {
			return guildNoticeDao.selectByGuild(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildNotice> selectEditList(PageQuery querys) throws DataAccessException {
		try {
			return guildNoticeDao.selectEditList(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GuildNotice select(Integer id) throws DataAccessException {
		try {
			return guildNoticeDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(GuildNotice guildNotice) throws DataAccessException {
		try {
			guildNoticeDao.insert(guildNotice);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GuildNotice guildNotice) throws DataAccessException {
		try {
			guildNoticeDao.update(guildNotice);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			guildNoticeDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildNoticeDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

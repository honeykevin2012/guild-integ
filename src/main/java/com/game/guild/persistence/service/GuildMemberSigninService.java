/*
 * @author zhaolianjun
 * created on 2015-03-13
 */
 
package com.game.guild.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildMemberSignin;
import com.game.guild.persistence.dao.GuildMemberSigninDao;

@Service
public class GuildMemberSigninService {
	
	@Autowired
	private GuildMemberSigninDao guildMemberSigninDao;
	
	public List<GuildMemberSignin> query(PageQuery querys) throws DataAccessException {
		try {
			return guildMemberSigninDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildMemberSignin> selectAll() throws DataAccessException {
		try {
			return guildMemberSigninDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildMemberSignin> selectByEntity(GuildMemberSignin guildMemberSignin) throws DataAccessException {
		try {
			return guildMemberSigninDao.selectByEntity(guildMemberSignin);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GuildMemberSignin select(Integer id) throws DataAccessException {
		try {
			return guildMemberSigninDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(GuildMemberSignin guildMemberSignin) throws DataAccessException {
		try {
			guildMemberSigninDao.insert(guildMemberSignin);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GuildMemberSignin guildMemberSignin) throws DataAccessException {
		try {
			guildMemberSigninDao.update(guildMemberSignin);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			guildMemberSigninDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildMemberSigninDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildMemberSignin> selectIsSignin(GuildMemberSignin signin){
		try {
			return guildMemberSigninDao.selectIsSignin(signin);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public int deleteByUserAndGuild(Integer userId, Integer guildId) throws DataAccessException{
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", userId);
			map.put("guildId", guildId);
			return guildMemberSigninDao.deleteByUserAndGuild(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public int deleteByGuildId(Integer guildId) throws DataAccessException {
		try {
			return guildMemberSigninDao.deleteByGuildId(guildId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public int selectSigninCount(Integer guildId) throws DataAccessException {
		try {
			return guildMemberSigninDao.selectSigninCount(guildId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

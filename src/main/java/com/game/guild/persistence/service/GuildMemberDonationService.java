/*
 * @author zhaolianjun
 * created on 2015-03-17
 */
 
package com.game.guild.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildMemberDonation;
import com.game.guild.persistence.dao.GuildMemberDonationDao;

@Service
public class GuildMemberDonationService {
	
	@Autowired
	private GuildMemberDonationDao guildMemberDonationDao;
	
	public List<GuildMemberDonation> query(PageQuery querys) throws DataAccessException {
		try {
			return guildMemberDonationDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildMemberDonation> selectAll() throws DataAccessException {
		try {
			return guildMemberDonationDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildMemberDonation> selectByEntity(GuildMemberDonation guildMemberDonation) throws DataAccessException {
		try {
			return guildMemberDonationDao.selectByEntity(guildMemberDonation);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GuildMemberDonation select(Integer id) throws DataAccessException {
		try {
			return guildMemberDonationDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(GuildMemberDonation guildMemberDonation) throws DataAccessException {
		try {
			guildMemberDonationDao.insert(guildMemberDonation);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GuildMemberDonation guildMemberDonation) throws DataAccessException {
		try {
			guildMemberDonationDao.update(guildMemberDonation);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			guildMemberDonationDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildMemberDonationDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public int deleteByUserAndGuild(Integer userId, Integer guildId) throws DataAccessException{
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", userId);
			map.put("guildId", guildId);
			return guildMemberDonationDao.deleteByUserAndGuild(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int deleteByGuildId(Integer guildId) throws DataAccessException{
		try {
			return guildMemberDonationDao.deleteByGuildId(guildId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<GuildMemberDonation> selectUserDonation(PageQuery querys) throws DataAccessException{
		try {
			return guildMemberDonationDao.selectUserDonation(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

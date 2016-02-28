/*
 * @author yangchengwei
 * created on 2015-02-03
 */

package com.game.guild.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.game.common.basics.aop.LoadFromMemcached;
import com.game.common.basics.aop.UpdateForMemcached;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildInfo;
import com.game.guild.persistence.dao.GuildInfoDao;

@Service
public class GuildInfoService {

	@Autowired
	private GuildInfoDao guildInfoDao;

	public List<GuildInfo> query(PageQuery querys) throws DataAccessException {
		try {
			return guildInfoDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildInfo> selectAll() throws DataAccessException {
		try {
			return guildInfoDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildInfo> selectByEntity(GuildInfo guildInfo)
			throws DataAccessException {
		try {
			return guildInfoDao.selectByEntity(guildInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<GuildInfo> selectByIds(Map<String,Object> map)
			throws DataAccessException {
		try {
			return guildInfoDao.selectByIds(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	/**
	 * 消费最高的公会
	 * @param limit
	 * @return
	 * @throws DataAccessException
	 */
	public List<JSONObject> selectCostTopQuantity(int limit) throws DataAccessException{
		try {
			return guildInfoDao.selectCostTopQuantity(limit);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public GuildInfo select(Integer id) throws DataAccessException {
		try {
			return guildInfoDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	@UpdateForMemcached(value = { "guild:recommend:userId:0" ,"guild:rankings:userId:0"})
	public void insert(GuildInfo guildInfo) throws DataAccessException {
		try {
			guildInfoDao.insert(guildInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	@UpdateForMemcached(value = { "guild:recommend:userId:0","guild:rankings:userId:0" })
	public void update(GuildInfo guildInfo) throws DataAccessException {
		try {
			guildInfoDao.update(guildInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	@UpdateForMemcached(value = { "guild:recommend:userId:0","guild:rankings:userId:0" })
	public int updateGuildBuilding(GuildInfo guildInfo) throws DataAccessException {
		try {
			return guildInfoDao.updateGuildBuilding(guildInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void delete(Integer id) throws DataAccessException {
		try {
			guildInfoDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildInfoDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildInfo> selectByUserId(Integer userId)
			throws DataAccessException {
		try {
			return guildInfoDao.selectByUserId(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	//@LoadFromMemcached(value = "guild:recommend:", condition = "userId", casevar = { "userId=0" })
	public List<GuildInfo> selectRecommend()
			throws DataAccessException {
		try {
			return guildInfoDao.selectRecommend();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildInfo> selectCommend(Integer[] guildIds)
			throws DataAccessException {
		try {
			return guildInfoDao.selectCommend(guildIds);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<GuildInfo> selectRankings(Integer limit)
			throws DataAccessException {
		try {
			return guildInfoDao.selectRankings(limit);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	@LoadFromMemcached(value = "guild:rankings:", condition = "userId:", casevar = {"userId=0"})
	public List<GuildInfo> selectRankingsByUser(Integer userId, Integer limit)
			throws DataAccessException {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", userId);
			map.put("limit", limit);
			return guildInfoDao.selectRankingsByUser(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public GuildInfo selectByUser(Integer userId, Integer guildId)
			throws DataAccessException {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", userId);
			map.put("guildId", guildId);
			return guildInfoDao.selectByUser(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void insertJoinGames(String guildId, String code)
			throws DataAccessException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("guildId", guildId);
			map.put("gameCode", code);
			guildInfoDao.insertJoinGames(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void deleteJoinGames(String guildId, String code)
			throws DataAccessException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("guildId", guildId);
			map.put("gameCode", code);
			guildInfoDao.deleteJoinGames(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public int updateGuildDestroy(Integer guild) throws DataAccessException {
		try {
			return guildInfoDao.updateGuildDestroy(guild);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

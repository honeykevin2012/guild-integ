package com.game.guild.persistence.dao;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildInfo;

public interface GuildInfoDao extends BaseAccessDao<GuildInfo> {
	public List<GuildInfo> selectByUserId(Integer userId) throws DataAccessException;
	public List<GuildInfo> selectRecommend() throws DataAccessException;
	public List<GuildInfo> selectCommend(Integer[] guildIds) throws DataAccessException;
	public List<GuildInfo> selectRankings(Integer limit) throws DataAccessException;
	public List<GuildInfo> selectRankingsByUser(Map<String, Integer> map) throws DataAccessException;
	public int updateBalance(GuildInfo guild) throws DataAccessException;
	public int updateGuildBuilding(GuildInfo guild) throws DataAccessException;;
	public void insertJoinGames(Map<String, String> map)throws DataAccessException;
	public void deleteJoinGames(Map<String, String> map)throws DataAccessException;
	public GuildInfo selectByUser(Map<String, Integer> map) throws DataAccessException;
	public int updateGuildDestroy(Integer guild) throws DataAccessException;
	public List<GuildInfo> selectByIds(Map<String,Object> map) throws DataAccessException;
	public List<JSONObject> selectCostTopQuantity(int limit) throws DataAccessException;
}

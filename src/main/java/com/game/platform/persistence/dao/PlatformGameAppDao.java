package com.game.platform.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.platform.domain.PlatformGameApp;

public interface PlatformGameAppDao extends BaseAccessDao<PlatformGameApp> {
	public List<PlatformGameApp> selectByProductId(Integer productId) throws DataAccessException;
	public List<PlatformGameApp> selectByUserId(Integer userId) throws DataAccessException;
	public List<PlatformGameApp> selectGuildGamesByUserId(Map<String, Integer> map) throws DataAccessException;
	public List<PlatformGameApp> selectCommend(Integer limit) throws DataAccessException;
	public List<PlatformGameApp> selectGamesByGuildId(Integer guildId) throws DataAccessException;
	public List<PlatformGameApp> selectJoinGames(Integer guildId) throws DataAccessException;
	public PlatformGameApp selectByCode(String code) throws DataAccessException;
	public List<Map<String,String>> selectExchangeList() throws DataAccessException;
	public List<PlatformGameApp> selectAllByUserId(Integer userId);
	public List<Map<String, String>> selectConsumeTopList(Integer code);
	public PlatformGameApp selectItem(Map<String, String> map);
}

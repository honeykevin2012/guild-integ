/*
 * @author zhaolianjun
 * created on 2015-01-30
 */
 
package com.game.platform.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.aop.LoadFromMemcached;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformGameApp;
import com.game.platform.persistence.dao.PlatformGameAppDao;

@Service
public class PlatformGameAppService {
	
	@Autowired
	private PlatformGameAppDao platformGameAppDao;
	
	@LoadFromMemcached(value = "guild:games:")
	public List<PlatformGameApp> query(PageQuery querys) throws DataAccessException {
		try {
			return platformGameAppDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameApp> selectAll() throws DataAccessException {
		try {
			return platformGameAppDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<PlatformGameApp> selectAll(Integer userId) throws DataAccessException {
		try {
			return platformGameAppDao.selectAllByUserId(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	
	public List<PlatformGameApp> selectByEntity(PlatformGameApp platformGameApp) throws DataAccessException {
		try {
			return platformGameAppDao.selectByEntity(platformGameApp);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformGameApp select(Integer id) throws DataAccessException {
		try {
			return platformGameAppDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformGameApp platformGameApp) throws DataAccessException {
		try {
			platformGameAppDao.insert(platformGameApp);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformGameApp platformGameApp) throws DataAccessException {
		try {
			platformGameAppDao.update(platformGameApp);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformGameAppDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformGameAppDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameApp> selectByProductId(Integer productId) throws DataAccessException{
		try {
			return platformGameAppDao.selectByProductId(productId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameApp> selectByUserId(Integer userId) throws DataAccessException{
		try {
			return platformGameAppDao.selectByUserId(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameApp> selectGuildGamesByUserId(String userId,String limit) throws DataAccessException{
		try {
			Map<String,Integer> map=new HashMap<String, Integer>();
			map.put("userId", Integer.valueOf(userId));
			map.put("limit", Integer.valueOf(limit));
			return platformGameAppDao.selectGuildGamesByUserId(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<PlatformGameApp> selectGamesByGuildId(Integer guildId) throws DataAccessException{
		try {
			return platformGameAppDao.selectGamesByGuildId(guildId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameApp> selectCommend(Integer limit) throws DataAccessException{
		try {
			return platformGameAppDao.selectCommend(limit);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<PlatformGameApp> selectJoinGames(Integer guildId) throws DataAccessException{
		try {
			return platformGameAppDao.selectJoinGames(guildId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformGameApp selectByCode(String code) throws DataAccessException{
		try {
			return platformGameAppDao.selectByCode(code);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public  List<Map<String,String>> selectExchangeList() throws DataAccessException {
		try {
			return platformGameAppDao.selectExchangeList();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<Map<String, String>> selectConsumeTopList(Integer code) {
		try {
			return platformGameAppDao.selectConsumeTopList(code);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public PlatformGameApp select(Map<String, String> map) {
		try {
			return platformGameAppDao.selectItem(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

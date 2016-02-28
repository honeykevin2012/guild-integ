/*
 * @author zhaolianjun
 * created on 2015-07-03
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformGameAppTrend;
import com.game.platform.persistence.dao.PlatformGameAppTrendDao;

@Service
public class PlatformGameAppTrendService {
	
	@Autowired
	private PlatformGameAppTrendDao platformGameAppTrendDao;
	
	public List<PlatformGameAppTrend> query(PageQuery querys) throws DataAccessException {
		try {
			return platformGameAppTrendDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameAppTrend> selectAll() throws DataAccessException {
		try {
			return platformGameAppTrendDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameAppTrend> selectByEntity(PlatformGameAppTrend platformGameAppTrend) throws DataAccessException {
		try {
			return platformGameAppTrendDao.selectByEntity(platformGameAppTrend);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformGameAppTrend select(Integer id) throws DataAccessException {
		try {
			return platformGameAppTrendDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformGameAppTrend selectByGameId(String gameId) throws DataAccessException {
		try {
			return platformGameAppTrendDao.selectByGameId(gameId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void insert(PlatformGameAppTrend platformGameAppTrend) throws DataAccessException {
		try {
			platformGameAppTrendDao.insert(platformGameAppTrend);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformGameAppTrend platformGameAppTrend) throws DataAccessException {
		try {
			platformGameAppTrendDao.update(platformGameAppTrend);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformGameAppTrendDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformGameAppTrendDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

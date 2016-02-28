/*
 * @author zhaolianjun
 * created on 2015-01-27
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.GameInfo;
import com.game.platform.persistence.dao.GameInfoDao;

@Service
public class GameInfoService {
	
	@Autowired
	private GameInfoDao gameInfoDao;
	
	public List<GameInfo> query(PageQuery querys) throws DataAccessException {
		try {
			return gameInfoDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GameInfo> selectAll() throws DataAccessException {
		try {
			return gameInfoDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GameInfo> selectByEntity(GameInfo gameInfo) throws DataAccessException {
		try {
			return gameInfoDao.selectByEntity(gameInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GameInfo select(Integer id) throws DataAccessException {
		try {
			return gameInfoDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(GameInfo gameInfo) throws DataAccessException {
		try {
			gameInfoDao.insert(gameInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GameInfo gameInfo) throws DataAccessException {
		try {
			gameInfoDao.update(gameInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			gameInfoDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			gameInfoDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

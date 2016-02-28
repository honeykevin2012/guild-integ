/*
 * @author zhaolianjun
 * created on 2015-04-22
 */
 
package com.game.user.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserGame;
import com.game.user.domain.UserGameResult;
import com.game.user.persistence.dao.UserGameDao;

@Service
public class UserGameService {
	
	@Autowired
	private UserGameDao userGameDao;
	
	public List<UserGame> query(PageQuery querys) throws DataAccessException {
		try {
			return userGameDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGame> selectAll() throws DataAccessException {
		try {
			return userGameDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGame> selectByEntity(UserGame userGame) throws DataAccessException {
		try {
			return userGameDao.selectByEntity(userGame);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserGame select(Integer id) throws DataAccessException {
		try {
			return userGameDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserGame userGame) throws DataAccessException {
		try {
			userGameDao.insert(userGame);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserGame userGame) throws DataAccessException {
		try {
			userGameDao.update(userGame);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userGameDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userGameDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserGame selectUnique(UserGame userGame) throws DataAccessException {
		try {
			return userGameDao.selectUnique(userGame);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGameResult> selectGameByUserId(Integer userId) throws DataAccessException{
		try {
			return userGameDao.selectGameByUserId(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserGameResult selectUserAmount(Integer userId) throws DataAccessException{
		try {
			return userGameDao.selectUserAmount(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<UserGame> selectGameRoles(Integer userId, Integer gameId) throws DataAccessException{
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", userId);
			map.put("gameId", gameId);
			return userGameDao.selectGameRoles(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserGame selectByUserServRole(Map<String, String> map) throws DataAccessException{
		try {
			return userGameDao.selectByUserServRole(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

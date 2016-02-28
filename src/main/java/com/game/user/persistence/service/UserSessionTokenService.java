/*
 * @author zhaolianjun
 * created on 2015-04-09
 */
 
package com.game.user.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserSessionToken;
import com.game.user.persistence.dao.UserSessionTokenDao;

@Service
public class UserSessionTokenService {
	
	@Autowired
	private UserSessionTokenDao userSessionTokenDao;
	
	public List<UserSessionToken> query(PageQuery querys) throws DataAccessException {
		try {
			return userSessionTokenDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserSessionToken> selectAll() throws DataAccessException {
		try {
			return userSessionTokenDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserSessionToken> selectByEntity(UserSessionToken userSessionToken) throws DataAccessException {
		try {
			return userSessionTokenDao.selectByEntity(userSessionToken);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserSessionToken select(Integer id) throws DataAccessException {
		try {
			return userSessionTokenDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserSessionToken selectByUserIdAndOs(Integer userId, String os) throws DataAccessException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", userId + "");
			map.put("os", os);
			return userSessionTokenDao.selectByUserIdAndOs(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void insert(UserSessionToken userSessionToken) throws DataAccessException {
		try {
			userSessionTokenDao.insert(userSessionToken);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserSessionToken userSessionToken) throws DataAccessException {
		try {
			userSessionTokenDao.update(userSessionToken);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userSessionTokenDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userSessionTokenDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

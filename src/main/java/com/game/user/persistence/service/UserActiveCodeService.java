/*
 * @author zhaolianjun
 * created on 2015-01-07
 */
 
package com.game.user.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserActiveCode;
import com.game.user.persistence.dao.UserActiveCodeDao;

@Service
public class UserActiveCodeService {
	
	@Autowired
	private UserActiveCodeDao userActiveCodeDao;
	
	public List<UserActiveCode> query(PageQuery querys) throws DataAccessException {
		try {
			return userActiveCodeDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserActiveCode> selectAll() throws DataAccessException {
		try {
			return userActiveCodeDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserActiveCode> selectByEntity(UserActiveCode userActiveCode) throws DataAccessException {
		try {
			return userActiveCodeDao.selectByEntity(userActiveCode);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserActiveCode select(Integer id) throws DataAccessException {
		try {
			return userActiveCodeDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserActiveCode userActiveCode) throws DataAccessException {
		try {
			userActiveCodeDao.insert(userActiveCode);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserActiveCode userActiveCode) throws DataAccessException {
		try {
			userActiveCodeDao.update(userActiveCode);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userActiveCodeDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userActiveCodeDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserActiveCode selectByCodeAndGame(String code, String gameId) throws DataAccessException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", code);
			map.put("gameId", gameId);
			return userActiveCodeDao.selectByCodeAndGame(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

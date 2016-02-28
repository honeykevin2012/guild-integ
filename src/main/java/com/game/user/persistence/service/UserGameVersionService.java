/*
 * @author zhaolianjun
 * created on 2015-01-27
 */
 
package com.game.user.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserGameVersion;
import com.game.user.persistence.dao.UserGameVersionDao;

@Service
public class UserGameVersionService {
	
	@Autowired
	private UserGameVersionDao userGameVersionDao;
	
	public List<UserGameVersion> query(PageQuery querys) throws DataAccessException {
		try {
			return userGameVersionDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGameVersion> selectAll() throws DataAccessException {
		try {
			return userGameVersionDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGameVersion> selectByEntity(UserGameVersion userGameVersion) throws DataAccessException {
		try {
			return userGameVersionDao.selectByEntity(userGameVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserGameVersion select(Integer id) throws DataAccessException {
		try {
			return userGameVersionDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserGameVersion userGameVersion) throws DataAccessException {
		try {
			userGameVersionDao.insert(userGameVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserGameVersion userGameVersion) throws DataAccessException {
		try {
			userGameVersionDao.update(userGameVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userGameVersionDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userGameVersionDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<UserGameVersion> selectByVersionAndType(String versionCode, String gameId, String clientType) throws DataAccessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("versionCode", versionCode);
			map.put("gameId", gameId);
			map.put("clientType", clientType);
			return userGameVersionDao.selectByVersionAndType(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

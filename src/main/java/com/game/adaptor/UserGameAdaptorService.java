/*
 * @author zhaolianjun
 * created on 2015-04-25
 */
 
package com.game.adaptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;

@Service
public class UserGameAdaptorService {
	
	@Autowired
	private UserGameAdaptorDao userGameAdaptorDao;
	
	public List<UserGameAdaptor> query(PageQuery querys) throws DataAccessException {
		try {
			return userGameAdaptorDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGameAdaptor> selectAll() throws DataAccessException {
		try {
			return userGameAdaptorDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGameAdaptor> selectByEntity(UserGameAdaptor userGameAdaptor) throws DataAccessException {
		try {
			return userGameAdaptorDao.selectByEntity(userGameAdaptor);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserGameAdaptor select(Integer id) throws DataAccessException {
		try {
			return userGameAdaptorDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserGameAdaptor userGameAdaptor) throws DataAccessException {
		try {
			userGameAdaptorDao.insert(userGameAdaptor);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserGameAdaptor userGameAdaptor) throws DataAccessException {
		try {
			userGameAdaptorDao.update(userGameAdaptor);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userGameAdaptorDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userGameAdaptorDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserGameAdaptor selectBy(Map<String, String> map){
		try {
			return userGameAdaptorDao.selectBy(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

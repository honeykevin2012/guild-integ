/*
 * @author zhaolianjun
 * created on 2015-08-06
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserDataItems;
import com.game.user.persistence.dao.UserDataItemsDao;

@Service
public class UserDataItemsService {
	
	@Autowired
	private UserDataItemsDao userDataItemsDao;
	
	public List<UserDataItems> query(PageQuery querys) throws DataAccessException {
		try {
			return userDataItemsDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserDataItems> selectAll() throws DataAccessException {
		try {
			return userDataItemsDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserDataItems> selectByEntity(UserDataItems userDataItems) throws DataAccessException {
		try {
			return userDataItemsDao.selectByEntity(userDataItems);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserDataItems select(Integer id) throws DataAccessException {
		try {
			return userDataItemsDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserDataItems userDataItems) throws DataAccessException {
		try {
			userDataItemsDao.insert(userDataItems);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserDataItems userDataItems) throws DataAccessException {
		try {
			userDataItemsDao.update(userDataItems);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userDataItemsDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userDataItemsDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

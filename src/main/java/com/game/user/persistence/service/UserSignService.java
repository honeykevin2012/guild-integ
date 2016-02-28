/*
 * @author zhaolianjun
 * created on 2015-06-01
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserSign;
import com.game.user.persistence.dao.UserSignDao;

@Service
public class UserSignService {
	
	@Autowired
	private UserSignDao userSignDao;
	
	public List<UserSign> query(PageQuery querys) throws DataAccessException {
		try {
			return userSignDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserSign> selectAll() throws DataAccessException {
		try {
			return userSignDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserSign> selectByEntity(UserSign userSign) throws DataAccessException {
		try {
			return userSignDao.selectByEntity(userSign);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserSign select(Integer id) throws DataAccessException {
		try {
			return userSignDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserSign userSign) throws DataAccessException {
		try {
			userSignDao.insert(userSign);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserSign userSign) throws DataAccessException {
		try {
			userSignDao.update(userSign);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userSignDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userSignDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserSign selectByUserId(Integer userId) throws DataAccessException {
		try {
			return userSignDao.selectByUserId(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

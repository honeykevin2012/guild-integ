/*
 * @author zhaolianjun
 * created on 2015-01-07
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserCustomService;
import com.game.user.persistence.dao.UserCustomServiceDao;

@Service
public class UserCustomServiceService {
	
	@Autowired
	private UserCustomServiceDao userCustomServiceDao;
	
	public List<UserCustomService> query(PageQuery querys) throws DataAccessException {
		try {
			return userCustomServiceDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserCustomService> selectCustomList(PageQuery querys) throws DataAccessException {
		try {
			return userCustomServiceDao.selectCustomList(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<UserCustomService> selectAll() throws DataAccessException {
		try {
			return userCustomServiceDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserCustomService> selectByEntity(UserCustomService userCustomService) throws DataAccessException {
		try {
			return userCustomServiceDao.selectByEntity(userCustomService);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserCustomService select(Integer id) throws DataAccessException {
		try {
			return userCustomServiceDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserCustomService userCustomService) throws DataAccessException {
		try {
			userCustomServiceDao.insert(userCustomService);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserCustomService userCustomService) throws DataAccessException {
		try {
			userCustomServiceDao.update(userCustomService);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userCustomServiceDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userCustomServiceDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

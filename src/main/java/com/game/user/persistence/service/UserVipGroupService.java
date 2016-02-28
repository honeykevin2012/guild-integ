/*
 * @author zhaolianjun
 * created on 2015-01-05
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserVipGroup;
import com.game.user.persistence.dao.UserVipGroupDao;

@Service
public class UserVipGroupService {
	
	@Autowired
	private UserVipGroupDao userVipGroupDao;
	
	public List<UserVipGroup> query(PageQuery querys) throws DataAccessException {
		try {
			return userVipGroupDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserVipGroup> selectAll() throws DataAccessException {
		try {
			return userVipGroupDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserVipGroup> selectByEntity(UserVipGroup userVipGroup) throws DataAccessException {
		try {
			return userVipGroupDao.selectByEntity(userVipGroup);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserVipGroup select(Integer id) throws DataAccessException {
		try {
			return userVipGroupDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public UserVipGroup selectDefaultLevel() throws DataAccessException {
		try {
			return userVipGroupDao.selectDefaultLevel();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserVipGroup userVipGroup) throws DataAccessException {
		try {
			userVipGroupDao.insert(userVipGroup);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserVipGroup userVipGroup) throws DataAccessException {
		try {
			userVipGroupDao.update(userVipGroup);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userVipGroupDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userVipGroupDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

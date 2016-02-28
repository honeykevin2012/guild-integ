/*
 * @author zhaolianjun
 * created on 2015-08-17
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserOrderItemVirtual;
import com.game.user.persistence.dao.UserOrderItemVirtualDao;

@Service
public class UserOrderItemVirtualService {
	
	@Autowired
	private UserOrderItemVirtualDao userOrderItemVirtualDao;
	
	public List<UserOrderItemVirtual> query(PageQuery querys) throws DataAccessException {
		try {
			return userOrderItemVirtualDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserOrderItemVirtual> selectAll() throws DataAccessException {
		try {
			return userOrderItemVirtualDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserOrderItemVirtual> selectByEntity(UserOrderItemVirtual userOrderItemVirtual) throws DataAccessException {
		try {
			return userOrderItemVirtualDao.selectByEntity(userOrderItemVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserOrderItemVirtual select(Integer id) throws DataAccessException {
		try {
			return userOrderItemVirtualDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserOrderItemVirtual userOrderItemVirtual) throws DataAccessException {
		try {
			userOrderItemVirtualDao.insert(userOrderItemVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void insertVirtuals(List<UserOrderItemVirtual> list) throws DataAccessException {
		try {
			userOrderItemVirtualDao.insertVirtuals(list);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserOrderItemVirtual userOrderItemVirtual) throws DataAccessException {
		try {
			userOrderItemVirtualDao.update(userOrderItemVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userOrderItemVirtualDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userOrderItemVirtualDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

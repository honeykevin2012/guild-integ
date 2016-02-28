/*
 * @author zhaolianjun
 * created on 2015-01-29
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserOrderItem;
import com.game.user.persistence.dao.UserOrderItemDao;

@Service
public class UserOrderItemService {
	
	@Autowired
	private UserOrderItemDao userOrderItemDao;
	
	public List<UserOrderItem> query(PageQuery querys) throws DataAccessException {
		try {
			return userOrderItemDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserOrderItem> selectAll() throws DataAccessException {
		try {
			return userOrderItemDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserOrderItem> selectByEntity(UserOrderItem userOrderItem) throws DataAccessException {
		try {
			return userOrderItemDao.selectByEntity(userOrderItem);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserOrderItem select(Integer id) throws DataAccessException {
		try {
			return userOrderItemDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserOrderItem userOrderItem) throws DataAccessException {
		try {
			userOrderItemDao.insert(userOrderItem);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserOrderItem userOrderItem) throws DataAccessException {
		try {
			userOrderItemDao.update(userOrderItem);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userOrderItemDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userOrderItemDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserOrderItem> selectByOrderId(String orderId) throws DataAccessException {
		try {
			return userOrderItemDao.selectByOrderId(orderId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

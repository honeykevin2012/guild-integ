/*
 * @author zhaolianjun
 * created on 2015-01-21
 */
 
package com.game.user.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserShoppingCart;
import com.game.user.persistence.dao.UserShoppingCartDao;

@Service
public class UserShoppingCartService {
	
	@Autowired
	private UserShoppingCartDao userShoppingCartDao;
	
	public List<UserShoppingCart> query(PageQuery querys) throws DataAccessException {
		try {
			return userShoppingCartDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserShoppingCart> selectAll() throws DataAccessException {
		try {
			return userShoppingCartDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserShoppingCart> selectByEntity(UserShoppingCart userShoppingCart) throws DataAccessException {
		try {
			return userShoppingCartDao.selectByEntity(userShoppingCart);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserShoppingCart select(Integer id) throws DataAccessException {
		try {
			return userShoppingCartDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserShoppingCart userShoppingCart) throws DataAccessException {
		try {
			userShoppingCartDao.insert(userShoppingCart);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserShoppingCart userShoppingCart) throws DataAccessException {
		try {
			userShoppingCartDao.update(userShoppingCart);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userShoppingCartDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userShoppingCartDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void deleteByProductIdAndUserId(Integer productId, Integer userId) throws DataAccessException{
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("productId", productId);
			map.put("userId", userId);
			userShoppingCartDao.deleteByProductIdAndUserId(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void deleteByUserId(Integer userId,List<Integer> productIds) throws DataAccessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productIds", productIds);
			map.put("userId", userId);
			userShoppingCartDao.deleteByUserId(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

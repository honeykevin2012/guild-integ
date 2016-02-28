/*
 * @author zhaolianjun
 * created on 2015-01-27
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserAddressInfo;
import com.game.user.persistence.dao.UserAddressInfoDao;

@Service
public class UserAddressInfoService {
	
	@Autowired
	private UserAddressInfoDao userAddressInfoDao;
	
	public List<UserAddressInfo> query(PageQuery querys) throws DataAccessException {
		try {
			return userAddressInfoDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserAddressInfo> selectAll() throws DataAccessException {
		try {
			return userAddressInfoDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserAddressInfo> selectByEntity(UserAddressInfo userAddressInfo) throws DataAccessException {
		try {
			return userAddressInfoDao.selectByEntity(userAddressInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserAddressInfo select(Integer id) throws DataAccessException {
		try {
			return userAddressInfoDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserAddressInfo userAddressInfo) throws DataAccessException {
		try {
			userAddressInfoDao.insert(userAddressInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserAddressInfo userAddressInfo) throws DataAccessException {
		try {
			userAddressInfoDao.update(userAddressInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userAddressInfoDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userAddressInfoDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void updateSetDefault(Integer addressId)throws DataAccessException {
		try {
			userAddressInfoDao.updateSetDefault(addressId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
}

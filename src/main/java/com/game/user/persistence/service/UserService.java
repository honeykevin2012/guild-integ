/*
 * @author jhnie
 * created on 2014-12-23
 */
 
package com.game.user.persistence.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.User;
import com.game.user.persistence.dao.UserDao;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public List<User> query(PageQuery querys) throws DataAccessException {
		try {
			return userDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<User> selectAll() throws DataAccessException {
		try {
			return userDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<User> selectByEntity(User user) throws DataAccessException {
		try {
			return userDao.selectByEntity(user);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<User> selectByIds(String[] ids) throws DataAccessException {
		try {
			return userDao.selectByIds(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}	
	public User select(Integer id) throws DataAccessException {
		try {
			return userDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	/**
	 * 消费最高的用户
	 * @param limit
	 * @return
	 * @throws DataAccessException
	 */
	public List<JSONObject> selectCostTopQuantity(int limit) throws DataAccessException{
		try {
			return userDao.selectCostTopQuantity(limit);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public int insert(User user) throws DataAccessException {
		try {
			return userDao.insert(user);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(User user) throws DataAccessException {
		try {
			userDao.update(user);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int updateBalance(User user) throws DataAccessException {
		try {
			return userDao.updateBalance(user);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void delete(Integer id) throws DataAccessException {
		try {
			userDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public User selectCheckUserExists(Map<String, String> map)throws DataAccessException {
		try {
			return userDao.selectCheckUserExists(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public User selectCheckUserExistsByName(String userName)throws DataAccessException {
		try {
			return userDao.selectCheckUserExistsByName(userName);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public User selectUserGroup(Integer userId) throws DataAccessException {
		try {
			return userDao.selectUserGroup(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void updateUserVipGroup(Integer userId) throws DataAccessException {
		try {
			userDao.updateUserVipGroup(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void updateUserInfo(User user) throws DataAccessException {
		try {
			userDao.updateUserInfo(user);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

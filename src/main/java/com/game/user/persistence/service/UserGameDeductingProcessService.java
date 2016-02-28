/*
 * @author yangchengwei
 * created on 2015-04-22
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserGameDeductingProcess;
import com.game.user.persistence.dao.UserGameDeductingProcessDao;

@Service
public class UserGameDeductingProcessService {
	
	@Autowired
	private UserGameDeductingProcessDao userGameDeductingProcessDao;
	
	public List<UserGameDeductingProcess> query(PageQuery querys) throws DataAccessException {
		try {
			return userGameDeductingProcessDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGameDeductingProcess> selectAll() throws DataAccessException {
		try {
			return userGameDeductingProcessDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGameDeductingProcess> selectPayFaildList(UserGameDeductingProcess userGameDeductingProcess) throws DataAccessException {
		try {
			return userGameDeductingProcessDao.selectPayFaildList(userGameDeductingProcess);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserGameDeductingProcess> selectPaySuccessList(UserGameDeductingProcess userGameDeductingProcess) throws DataAccessException {
		try {
			return userGameDeductingProcessDao.selectPaySuccessList(userGameDeductingProcess);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}	
	public List<UserGameDeductingProcess> selectByEntity(UserGameDeductingProcess userGameDeductingProcess) throws DataAccessException {
		try {
			return userGameDeductingProcessDao.selectByEntity(userGameDeductingProcess);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserGameDeductingProcess select(Integer id) throws DataAccessException {
		try {
			return userGameDeductingProcessDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserGameDeductingProcess userGameDeductingProcess) throws DataAccessException {
		try {
			userGameDeductingProcessDao.insert(userGameDeductingProcess);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserGameDeductingProcess userGameDeductingProcess) throws DataAccessException {
		try {
			userGameDeductingProcessDao.update(userGameDeductingProcess);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	
	public void delete(Integer id) throws DataAccessException {
		try {
			userGameDeductingProcessDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userGameDeductingProcessDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

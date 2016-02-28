/*
 * @author yangchengwei
 * created on 2015-07-31
 */
 
package com.game.user.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserCenterNotice;
import com.game.user.persistence.dao.UserCenterNoticeDao;

@Service
public class UserCenterNoticeService {
	
	@Autowired
	private UserCenterNoticeDao userCenterNoticeDao;
	
	public List<UserCenterNotice> query(PageQuery querys) throws DataAccessException {
		try {
			return userCenterNoticeDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserCenterNotice> selectAll() throws DataAccessException {
		try {
			return userCenterNoticeDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserCenterNotice> selectByEntity(UserCenterNotice userCenterNotice) throws DataAccessException {
		try {
			return userCenterNoticeDao.selectByEntity(userCenterNotice);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserCenterNotice select(Integer id) throws DataAccessException {
		try {
			return userCenterNoticeDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserCenterNotice> selectByUserId(Integer id) throws DataAccessException {
		try {
			return userCenterNoticeDao.selectByUserId(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserCenterNotice userCenterNotice) throws DataAccessException {
		try {
			userCenterNoticeDao.insert(userCenterNotice);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserCenterNotice userCenterNotice) throws DataAccessException {
		try {
			userCenterNoticeDao.update(userCenterNotice);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userCenterNoticeDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userCenterNoticeDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

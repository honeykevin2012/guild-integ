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
import com.game.user.domain.UserCustomAbout;
import com.game.user.persistence.dao.UserCustomAboutDao;

@Service
public class UserCustomAboutService {
	
	@Autowired
	private UserCustomAboutDao userCustomAboutDao;
	
	public List<UserCustomAbout> query(PageQuery querys) throws DataAccessException {
		try {
			return userCustomAboutDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserCustomAbout> selectAll() throws DataAccessException {
		try {
			return userCustomAboutDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserCustomAbout> selectByEntity(UserCustomAbout userCustomAbout) throws DataAccessException {
		try {
			return userCustomAboutDao.selectByEntity(userCustomAbout);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserCustomAbout select(Integer id) throws DataAccessException {
		try {
			return userCustomAboutDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(UserCustomAbout userCustomAbout) throws DataAccessException {
		try {
			userCustomAboutDao.insert(userCustomAbout);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(UserCustomAbout userCustomAbout) throws DataAccessException {
		try {
			userCustomAboutDao.update(userCustomAbout);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			userCustomAboutDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userCustomAboutDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserCustomAbout selectByGameId(String gameId) throws DataAccessException{
		try {
			return userCustomAboutDao.selectByGameId(gameId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

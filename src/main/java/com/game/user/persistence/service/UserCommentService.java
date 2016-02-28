/*
 * @author zhaolianjun
 * created on 2015-07-03
 */
 
package com.game.user.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.user.domain.UserComment;
import com.game.user.persistence.dao.UserCommentDao;

@Service
public class UserCommentService {
	
	@Autowired
	private UserCommentDao userCommentDao;
	
	//@Cacheable(value = "default")
	public List<UserComment> query(PageQuery querys) throws DataAccessException {
		try {
			return userCommentDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserComment> selectAll() throws DataAccessException {
		try {
			return userCommentDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserComment> selectCommentByGameId(Integer gameId, String channel, Integer limit) throws DataAccessException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gameId", gameId);
			map.put("channel", channel);
			map.put("limit", limit);
			return userCommentDao.selectCommentByGameId(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<UserComment> selectByEntity(UserComment userComment) throws DataAccessException {
		try {
			return userCommentDao.selectByEntity(userComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public UserComment select(Integer id) throws DataAccessException {
		try {
			return userCommentDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	@CacheEvict(value = "default", allEntries = true)
	public void insert(UserComment userComment) throws DataAccessException {
		try {
			userCommentDao.insert(userComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void update(UserComment userComment) throws DataAccessException {
		try {
			userCommentDao.update(userComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	/**
	 * 支持或反对
	 * @param id:评论ID
	 * @param isUp  ture:支持  false:反对
	 * @throws DataAccessException
	 */
	public void updateUpOrUnder(Integer id, boolean isUp) throws DataAccessException {
		try {
			if(isUp)
				userCommentDao.updateUp(id);
			else 
				userCommentDao.updateUnder(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void delete(Integer id) throws DataAccessException {
		try {
			userCommentDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userCommentDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

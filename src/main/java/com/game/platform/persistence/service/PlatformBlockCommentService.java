/*
 * @author zhaolianjun
 * created on 2015-01-29
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.aop.LoadFromMemcached;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformBlockComment;
import com.game.platform.persistence.dao.PlatformBlockCommentDao;

@Service
public class PlatformBlockCommentService {
	
	@Autowired
	private PlatformBlockCommentDao platformBlockCommentDao;
	
	public List<PlatformBlockComment> query(PageQuery querys) throws DataAccessException {
		try {
			return platformBlockCommentDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformBlockComment> selectAll() throws DataAccessException {
		try {
			return platformBlockCommentDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformBlockComment> selectByEntity(PlatformBlockComment platformBlockComment) throws DataAccessException {
		try {
			return platformBlockCommentDao.selectByEntity(platformBlockComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformBlockComment select(Integer id) throws DataAccessException {
		try {
			return platformBlockCommentDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformBlockComment platformBlockComment) throws DataAccessException {
		try {
			platformBlockCommentDao.insert(platformBlockComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformBlockComment platformBlockComment) throws DataAccessException {
		try {
			platformBlockCommentDao.update(platformBlockComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformBlockCommentDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformBlockCommentDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	@LoadFromMemcached(value="platform:block:", condition="blockId")
	public PlatformBlockComment selectByBlock(String blockId) throws DataAccessException {
		try {
			return platformBlockCommentDao.selectByBlock(blockId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

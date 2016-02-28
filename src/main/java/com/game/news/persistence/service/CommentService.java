/*
 * @author yangchengwei
 * created on 2014-10-15
 */
 
package com.game.news.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.news.domain.Comment;
import com.game.news.persistence.dao.CommentDao;

@Service
public class CommentService {
	
	@Autowired
	private CommentDao zstCommentDao;
	
	public List<Comment> query(PageQuery querys) throws DataAccessException {
		try {
			return zstCommentDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Comment> selectAll() throws DataAccessException {
		try {
			return zstCommentDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Comment> selectByEntity(Comment zstComment) throws DataAccessException {
		try {
			return zstCommentDao.selectByEntity(zstComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public Comment select(Integer id) throws DataAccessException {
		try {
			return zstCommentDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(Comment zstComment) throws DataAccessException {
		try {
			zstCommentDao.insert(zstComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(Comment zstComment) throws DataAccessException {
		try {
			zstCommentDao.update(zstComment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			zstCommentDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			zstCommentDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

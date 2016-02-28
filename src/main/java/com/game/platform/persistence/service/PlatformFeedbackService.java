/*
 * @author yangchengwei
 * created on 2015-04-20
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformFeedback;
import com.game.platform.persistence.dao.PlatformFeedbackDao;

@Service
public class PlatformFeedbackService {
	
	@Autowired
	private PlatformFeedbackDao platformFeedbackDao;
	
	public List<PlatformFeedback> query(PageQuery querys) throws DataAccessException {
		try {
			return platformFeedbackDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformFeedback> selectAll() throws DataAccessException {
		try {
			return platformFeedbackDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformFeedback> selectByEntity(PlatformFeedback platformFeedback) throws DataAccessException {
		try {
			return platformFeedbackDao.selectByEntity(platformFeedback);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformFeedback select(Integer id) throws DataAccessException {
		try {
			return platformFeedbackDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformFeedback platformFeedback) throws DataAccessException {
		try {
			platformFeedbackDao.insert(platformFeedback);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformFeedback platformFeedback) throws DataAccessException {
		try {
			platformFeedbackDao.update(platformFeedback);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformFeedbackDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformFeedbackDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

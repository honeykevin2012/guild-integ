/*
 * @author zhaolianjun
 * created on 2015-02-02
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformAdvert;
import com.game.platform.persistence.dao.PlatformAdvertDao;

@Service
public class PlatformAdvertService {
	
	@Autowired
	private PlatformAdvertDao platformAdvertDao;
	
	public List<PlatformAdvert> query(PageQuery querys) throws DataAccessException {
		try {
			return platformAdvertDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAdvert> selectAll() throws DataAccessException {
		try {
			return platformAdvertDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAdvert> selectByEntity(PlatformAdvert platformAdvert) throws DataAccessException {
		try {
			return platformAdvertDao.selectByEntity(platformAdvert);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformAdvert select(Integer id) throws DataAccessException {
		try {
			return platformAdvertDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformAdvert platformAdvert) throws DataAccessException {
		try {
			platformAdvertDao.insert(platformAdvert);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformAdvert platformAdvert) throws DataAccessException {
		try {
			platformAdvertDao.update(platformAdvert);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformAdvertDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformAdvertDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

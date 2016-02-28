/*
 * @author zhaolianjun
 * created on 2015-03-10
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformDict;
import com.game.platform.persistence.dao.PlatformDictDao;

@Service
public class PlatformDictService {
	
	@Autowired
	private PlatformDictDao platformDictDao;
	
	public List<PlatformDict> query(PageQuery querys) throws DataAccessException {
		try {
			return platformDictDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformDict> selectAll() throws DataAccessException {
		try {
			return platformDictDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformDict> selectByEntity(PlatformDict platformDict) throws DataAccessException {
		try {
			return platformDictDao.selectByEntity(platformDict);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformDict select(Integer id) throws DataAccessException {
		try {
			return platformDictDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformDict platformDict) throws DataAccessException {
		try {
			platformDictDao.insert(platformDict);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformDict platformDict) throws DataAccessException {
		try {
			platformDictDao.update(platformDict);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformDictDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformDictDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

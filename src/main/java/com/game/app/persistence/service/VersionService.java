/*
 * @author yangchengwei
 * created on 2014-10-21
 */
 
package com.game.app.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.app.domain.Version;
import com.game.app.persistence.dao.VersionDao;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;

@Service
public class VersionService {
	
	@Autowired
	private VersionDao versionDao;
	
	public List<Version> query(PageQuery querys) throws DataAccessException {
		try {
			return versionDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Version> selectAll() throws DataAccessException {
		try {
			return versionDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Version> selectByEntity(Version zstVersion) throws DataAccessException {
		try {
			return versionDao.selectByEntity(zstVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public Version select(Version zstVersion) throws DataAccessException {
		try {
			return versionDao.select(zstVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(Version zstVersion) throws DataAccessException {
		try {
			versionDao.insert(zstVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(Version zstVersion) throws DataAccessException {
		try {
			versionDao.update(zstVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			versionDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			versionDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

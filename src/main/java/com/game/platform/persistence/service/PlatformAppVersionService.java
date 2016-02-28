/*
 * @author zhaolianjun
 * created on 2014-12-30
 */
 
package com.game.platform.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformAppVersion;
import com.game.platform.persistence.dao.PlatformAppVersionDao;

@Service
public class PlatformAppVersionService {
	
	@Autowired
	private PlatformAppVersionDao platformAppVersionDao;
	
	public List<PlatformAppVersion> query(PageQuery querys) throws DataAccessException {
		try {
			return platformAppVersionDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAppVersion> selectAll() throws DataAccessException {
		try {
			return platformAppVersionDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAppVersion> selectByEntity(PlatformAppVersion platformAppVersion) throws DataAccessException {
		try {
			return platformAppVersionDao.selectByEntity(platformAppVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformAppVersion select(Integer id) throws DataAccessException {
		try {
			return platformAppVersionDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformAppVersion platformAppVersion) throws DataAccessException {
		try {
			platformAppVersionDao.insert(platformAppVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformAppVersion platformAppVersion) throws DataAccessException {
		try {
			platformAppVersionDao.update(platformAppVersion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformAppVersionDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformAppVersionDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<PlatformAppVersion> selectByVersionAndType(String versionCode, String clientType) throws DataAccessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("versionCode", versionCode);
			map.put("clientType", clientType);
			return platformAppVersionDao.selectByVersionAndType(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

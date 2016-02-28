/*
 * @author yangchengwei
 * created on 2015-08-04
 */
 
package com.game.app.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.app.domain.AppLoadimg;
import com.game.app.persistence.dao.AppLoadimgDao;

@Service
public class AppLoadimgService {
	
	@Autowired
	private AppLoadimgDao appLoadimgDao;
	
	public List<AppLoadimg> query(PageQuery querys) throws DataAccessException {
		try {
			return appLoadimgDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<AppLoadimg> selectAll() throws DataAccessException {
		try {
			return appLoadimgDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<AppLoadimg> selectByEntity(AppLoadimg appLoadimg) throws DataAccessException {
		try {
			return appLoadimgDao.selectByEntity(appLoadimg);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public AppLoadimg select(Integer id) throws DataAccessException {
		try {
			return appLoadimgDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(AppLoadimg appLoadimg) throws DataAccessException {
		try {
			appLoadimgDao.insert(appLoadimg);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(AppLoadimg appLoadimg) throws DataAccessException {
		try {
			appLoadimgDao.update(appLoadimg);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			appLoadimgDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			appLoadimgDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public AppLoadimg selectByOsType(Integer osType) {
		try {
			return appLoadimgDao.selectByOsType(osType);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

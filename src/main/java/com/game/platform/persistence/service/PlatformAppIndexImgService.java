/*
 * @author yangchengwei
 * created on 2015-05-26
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformAppIndexImg;
import com.game.platform.persistence.dao.PlatformAppIndexImgDao;

@Service
public class PlatformAppIndexImgService {
	
	@Autowired
	private PlatformAppIndexImgDao platformAppIndexImgDao;
	
	public List<PlatformAppIndexImg> query(PageQuery querys) throws DataAccessException {
		try {
			return platformAppIndexImgDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAppIndexImg> selectAll() throws DataAccessException {
		try {
			return platformAppIndexImgDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAppIndexImg> selectByEntity(PlatformAppIndexImg platformAppIndexImg) throws DataAccessException {
		try {
			return platformAppIndexImgDao.selectByEntity(platformAppIndexImg);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformAppIndexImg select(Integer id) throws DataAccessException {
		try {
			return platformAppIndexImgDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformAppIndexImg platformAppIndexImg) throws DataAccessException {
		try {
			platformAppIndexImgDao.insert(platformAppIndexImg);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformAppIndexImg platformAppIndexImg) throws DataAccessException {
		try {
			platformAppIndexImgDao.update(platformAppIndexImg);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformAppIndexImgDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformAppIndexImgDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

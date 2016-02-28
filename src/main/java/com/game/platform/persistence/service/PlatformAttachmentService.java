/*
 * @author zhaolianjun
 * created on 2014-12-31
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformAttachment;
import com.game.platform.persistence.dao.PlatformAttachmentDao;

@Service
public class PlatformAttachmentService {
	
	@Autowired
	private PlatformAttachmentDao platformAttachmentDao;
	
	public List<PlatformAttachment> query(PageQuery querys) throws DataAccessException {
		try {
			return platformAttachmentDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAttachment> selectAll() throws DataAccessException {
		try {
			return platformAttachmentDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAttachment> selectByEntity(PlatformAttachment platformAttachment) throws DataAccessException {
		try {
			return platformAttachmentDao.selectByEntity(platformAttachment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformAttachment select(Integer id) throws DataAccessException {
		try {
			return platformAttachmentDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformAttachment platformAttachment) throws DataAccessException {
		try {
			platformAttachmentDao.insert(platformAttachment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformAttachment platformAttachment) throws DataAccessException {
		try {
			platformAttachmentDao.update(platformAttachment);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformAttachmentDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformAttachmentDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void deleteByName(List<String> fileNames) throws DataAccessException{
		try {
			platformAttachmentDao.deleteByName(fileNames);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformAttachment> selectProductItem(PlatformAttachment attach)throws DataAccessException{
		try {
			return platformAttachmentDao.selectProductItem(attach);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

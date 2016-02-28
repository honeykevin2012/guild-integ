/*
 * @author yangchengwei
 * created on 2015-06-29
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.MessagePublish;
import com.game.platform.persistence.dao.MessagePublishDao;

@Service
public class MessagePublishService {
	
	@Autowired
	private MessagePublishDao messagePublishDao;
	
	public List<MessagePublish> query(PageQuery querys) throws DataAccessException {
		try {
			return messagePublishDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<MessagePublish> selectAll() throws DataAccessException {
		try {
			return messagePublishDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<MessagePublish> selectByEntity(MessagePublish messagePublish) throws DataAccessException {
		try {
			return messagePublishDao.selectByEntity(messagePublish);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public MessagePublish select(Integer id) throws DataAccessException {
		try {
			return messagePublishDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(MessagePublish messagePublish) throws DataAccessException {
		try {
			messagePublishDao.insert(messagePublish);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(MessagePublish messagePublish) throws DataAccessException {
		try {
			messagePublishDao.update(messagePublish);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			messagePublishDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			messagePublishDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

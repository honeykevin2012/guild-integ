/*
 * @author zhaolianjun
 * created on 2015-08-06
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.MessageReaderAttach;
import com.game.platform.persistence.dao.MessageReaderAttachDao;

@Service
public class MessageReaderAttachService {
	
	@Autowired
	private MessageReaderAttachDao messageReaderAttachDao;
	
	public List<MessageReaderAttach> query(PageQuery querys) throws DataAccessException {
		try {
			return messageReaderAttachDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<MessageReaderAttach> selectAll() throws DataAccessException {
		try {
			return messageReaderAttachDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<MessageReaderAttach> selectByEntity(MessageReaderAttach messageReaderAttach) throws DataAccessException {
		try {
			return messageReaderAttachDao.selectByEntity(messageReaderAttach);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public MessageReaderAttach select(Integer id) throws DataAccessException {
		try {
			return messageReaderAttachDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(MessageReaderAttach messageReaderAttach) throws DataAccessException {
		try {
			messageReaderAttachDao.insert(messageReaderAttach);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(MessageReaderAttach messageReaderAttach) throws DataAccessException {
		try {
			messageReaderAttachDao.update(messageReaderAttach);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			messageReaderAttachDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			messageReaderAttachDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

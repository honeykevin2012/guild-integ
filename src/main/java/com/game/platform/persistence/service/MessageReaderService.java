/*
 * @author yangchengwei
 * created on 2015-06-29
 */
 
package com.game.platform.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.MessageReader;
import com.game.platform.persistence.dao.MessageReaderDao;

@Service
public class MessageReaderService {
	
	@Autowired
	private MessageReaderDao messageReaderDao;
	
	public List<MessageReader> query(PageQuery querys) throws DataAccessException {
		try {
			return messageReaderDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<MessageReader> selectAll() throws DataAccessException {
		try {
			return messageReaderDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<MessageReader> selectByEntity(MessageReader messageReader) throws DataAccessException {
		try {
			return messageReaderDao.selectByEntity(messageReader);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public MessageReader select(Integer id) throws DataAccessException {
		try {
			return messageReaderDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void updateReader(Integer id) throws DataAccessException{
		try {
			messageReaderDao.updateReader(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void updateReceived(Integer id) throws DataAccessException{
		try {
			messageReaderDao.updateReceived(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public MessageReader selectNewMsg(Integer userId,Integer msgId) throws DataAccessException {
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("msgId", msgId);
			return messageReaderDao.selectNewMsg(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int selectNewMsgCount(Integer userId) throws DataAccessException {
		try {
			return messageReaderDao.selectNewMsgCount(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void insert(MessageReader messageReader) throws DataAccessException {
		try {
			messageReaderDao.insert(messageReader);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void insertByUserId(MessageReader messageReader) throws DataAccessException {
		try {
			messageReaderDao.insertByUserId(messageReader);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	
	public void update(MessageReader messageReader) throws DataAccessException {
		try {
			messageReaderDao.update(messageReader);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			messageReaderDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			messageReaderDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<MessageReader> selectByUserId(Integer u) {
		try {
			return messageReaderDao.selectByUserId(u);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

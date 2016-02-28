package com.game.platform.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.platform.domain.MessageReader;

public interface MessageReaderDao extends BaseAccessDao<MessageReader> {
	public int insertByUserId(MessageReader messageReader) throws DataAccessException;
	public MessageReader selectNewMsg(Map<String, Object> map) throws DataAccessException;
	public void updateReader(Integer id) throws DataAccessException;
	public void updateReceived(Integer id) throws DataAccessException;
	public List<MessageReader> selectByUserId(Integer u);
	public int selectNewMsgCount(Integer u);
}

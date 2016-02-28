package com.game.platform.persistence.dao;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.platform.domain.PlatformGameTopic;

public interface PlatformGameTopicDao extends BaseAccessDao<PlatformGameTopic> {
	public void updateByGameCode(PlatformGameTopic platformGameTopic) throws DataAccessException;
	public void updateById(PlatformGameTopic platformGameTopic) throws DataAccessException;
	public List<PlatformGameTopic> selectAppIndexList(Integer gameCode)throws DataAccessException;
	public List<PlatformGameTopic> selectAppIndexAll()throws DataAccessException;
}

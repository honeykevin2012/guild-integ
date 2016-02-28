/*
 * @author yangchengwei
 * created on 2015-05-25
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.platform.domain.PlatformGameTopic;
import com.game.platform.persistence.dao.PlatformGameTopicDao;

@Service
public class PlatformGameTopicService {
	
	@Autowired
	private PlatformGameTopicDao platformGameTopicDao;
	
	public List<PlatformGameTopic> query(PageQuery querys) throws DataAccessException {
		try {
			return platformGameTopicDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameTopic> selectAll() throws DataAccessException {
		try {
			return platformGameTopicDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameTopic> selectByEntity(PlatformGameTopic platformGameTopic) throws DataAccessException {
		try {
			return platformGameTopicDao.selectByEntity(platformGameTopic);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformGameTopic> selectAppIndexList(Integer gameCode) throws DataAccessException {
		try {
			return platformGameTopicDao.selectAppIndexList(gameCode);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public PlatformGameTopic select(Integer id) throws DataAccessException {
		try {
			return platformGameTopicDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(PlatformGameTopic platformGameTopic) throws DataAccessException {
		try {
			platformGameTopicDao.insert(platformGameTopic);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(PlatformGameTopic platformGameTopic) throws DataAccessException {
		try {
			platformGameTopicDao.update(platformGameTopic);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			platformGameTopicDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			platformGameTopicDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void updateIsTop(Integer id, Integer isTop) {
		// TODO Auto-generated method stub
		Integer gameCode=platformGameTopicDao.select(id).getGameCode();
		PlatformGameTopic p1=new PlatformGameTopic();
		p1.setGameCode(gameCode);
		p1.setIsTop(0);
		platformGameTopicDao.updateByGameCode(p1);
		p1.setId(id);
		p1.setIsTop(isTop);
		platformGameTopicDao.updateById(p1);
		
	}

	public List<PlatformGameTopic> selectAppIndexAll() {
		try {
			return platformGameTopicDao.selectAppIndexAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

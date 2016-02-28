/*
 * @author yangchengwei
 * created on 2014-10-01
 */
 
package com.game.news.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.news.domain.Channel;
import com.game.news.persistence.dao.ChannelDao;

@Service
public class ChannelService {
	
	@Autowired
	private ChannelDao zstChannelDao;
	
	public List<Channel> query(PageQuery querys) throws DataAccessException {
		try {
			return zstChannelDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Channel> selectAll() throws DataAccessException {
		try {
			return zstChannelDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Channel> selectByEntity(Channel zstChannel) throws DataAccessException {
		try {
			return zstChannelDao.selectByEntity(zstChannel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public Channel select(Integer id) throws DataAccessException {
		try {
			return zstChannelDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(Channel zstChannel) throws DataAccessException {
		try {
			zstChannelDao.insert(zstChannel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(Channel zstChannel) throws DataAccessException {
		try {
			zstChannelDao.update(zstChannel);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			zstChannelDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			zstChannelDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

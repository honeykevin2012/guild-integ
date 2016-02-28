/*
 * @author yangchengwei
 * created on 2015-08-05
 */
 
package com.game.guild.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.RedPacketItem;
import com.game.guild.persistence.dao.RedPacketItemDao;

@Service
public class RedPacketItemService {
	
	@Autowired
	private RedPacketItemDao redPacketItemDao;
	
	public List<RedPacketItem> query(PageQuery querys) throws DataAccessException {
		try {
			return redPacketItemDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<RedPacketItem> selectAll() throws DataAccessException {
		try {
			return redPacketItemDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<RedPacketItem> selectByEntity(RedPacketItem redPacketItem) throws DataAccessException {
		try {
			return redPacketItemDao.selectByEntity(redPacketItem);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public RedPacketItem select(Integer id) throws DataAccessException {
		try {
			return redPacketItemDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(RedPacketItem redPacketItem) throws DataAccessException {
		try {
			redPacketItemDao.insert(redPacketItem);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(RedPacketItem redPacketItem) throws DataAccessException {
		try {
			redPacketItemDao.update(redPacketItem);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			redPacketItemDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			redPacketItemDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

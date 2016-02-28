/*
 * @author yangchengwei
 * created on 2015-03-03
 */
 
package com.game.guild.persistence.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.guild.domain.GuildItemVirtual;
import com.game.guild.persistence.dao.GuildItemVirtualDao;

@Service
public class GuildItemVirtualService {
	
	@Autowired
	private GuildItemVirtualDao guildItemVirtualDao;
	
	public List<GuildItemVirtual> query(PageQuery querys) throws DataAccessException {
		try {
			return guildItemVirtualDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildItemVirtual> selectAll() throws DataAccessException {
		try {
			return guildItemVirtualDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<GuildItemVirtual> selectByEntity(GuildItemVirtual guildItemVirtual) throws DataAccessException {
		try {
			return guildItemVirtualDao.selectByEntity(guildItemVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<Map<String,String>> selectMyitem(PageQuery querys) throws DataAccessException {
		try {
			return guildItemVirtualDao.selectMyitem(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public GuildItemVirtual select(Integer id) throws DataAccessException {
		try {
			return guildItemVirtualDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(GuildItemVirtual guildItemVirtual) throws DataAccessException {
		try {
			guildItemVirtualDao.insert(guildItemVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(GuildItemVirtual guildItemVirtual) throws DataAccessException {
		try {
			guildItemVirtualDao.update(guildItemVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			guildItemVirtualDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			guildItemVirtualDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public static void main(String[] args) {
		List<String> s=new ArrayList<String>();
		s.add("1");
		s.add("2");
		s.add("3");
		s.add("4");
		s.add("5");
		
		System.out.println(s.subList(4, 5));
	}
}

/*
 * @author zhaolianjun
 * created on 2014-12-30
 */
 
package com.game.platform.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.platform.domain.PlatformZone;
import com.game.platform.persistence.dao.PlatformZoneDao;

@Service
public class PlatformZoneService {
	
	@Autowired
	private PlatformZoneDao platformZoneDao;
	
	public List<PlatformZone> selectAll() throws DataAccessException {
		try {
			return platformZoneDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<PlatformZone> selectByEntity(PlatformZone platformZone) throws DataAccessException {
		try {
			return platformZoneDao.selectByEntity(platformZone);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public PlatformZone select(Integer id) throws DataAccessException {
		try {
			return platformZoneDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<PlatformZone> selectZoneProvince() throws DataAccessException{
		try {
			return platformZoneDao.selectZoneProvince();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<PlatformZone> selectZoneCity(Integer code) throws DataAccessException{
		try {
			return platformZoneDao.selectZoneCity(code);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<PlatformZone> selectZoneArea(Integer code) throws DataAccessException{
		try {
			return platformZoneDao.selectZoneArea(code);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

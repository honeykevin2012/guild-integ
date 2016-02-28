package com.game.platform.persistence.dao;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.platform.domain.PlatformZone;

public interface PlatformZoneDao extends BaseAccessDao<PlatformZone> {
	public List<PlatformZone> selectZoneProvince() throws DataAccessException;
	public List<PlatformZone> selectZoneCity(Integer code) throws DataAccessException;
	public List<PlatformZone> selectZoneArea(Integer code) throws DataAccessException;
}

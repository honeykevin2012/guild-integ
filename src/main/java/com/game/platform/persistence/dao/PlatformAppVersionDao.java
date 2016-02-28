package com.game.platform.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.platform.domain.PlatformAppVersion;

public interface PlatformAppVersionDao extends BaseAccessDao<PlatformAppVersion> {
	public List<PlatformAppVersion> selectByVersionAndType(Map<String, Object> map) throws DataAccessException;
}

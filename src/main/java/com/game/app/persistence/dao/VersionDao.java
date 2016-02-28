package com.game.app.persistence.dao;

import com.game.app.domain.Version;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;

public interface VersionDao extends BaseAccessDao<Version> {
	public Version select(Version zstVersion)throws DataAccessException;
}

package com.game.user.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserGameVersion;

public interface UserGameVersionDao extends BaseAccessDao<UserGameVersion> {
	public List<UserGameVersion> selectByVersionAndType(Map<String, Object> map) throws DataAccessException;
}

package com.game.user.persistence.dao;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserCustomAbout;

public interface UserCustomAboutDao extends BaseAccessDao<UserCustomAbout> {
	public UserCustomAbout selectByGameId(String gameId) throws DataAccessException;
}

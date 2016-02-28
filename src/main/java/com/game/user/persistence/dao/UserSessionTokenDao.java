package com.game.user.persistence.dao;

import java.util.Map;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserSessionToken;

public interface UserSessionTokenDao extends BaseAccessDao<UserSessionToken> {
	public UserSessionToken selectByUserIdAndOs(Map<String, String> map);
}

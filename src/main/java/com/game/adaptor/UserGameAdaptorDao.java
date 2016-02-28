package com.game.adaptor;

import java.util.Map;

import com.game.common.basics.persistence.access.BaseAccessDao;

public interface UserGameAdaptorDao extends BaseAccessDao<UserGameAdaptor> {
	public UserGameAdaptor selectBy(Map<String, String> map);
}

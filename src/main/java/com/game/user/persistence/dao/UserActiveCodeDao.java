package com.game.user.persistence.dao;

import java.util.Map;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserActiveCode;

public interface UserActiveCodeDao extends BaseAccessDao<UserActiveCode> {
	public UserActiveCode selectByCodeAndGame(Map<String, String> map);
}

package com.game.user.persistence.dao;

import java.util.Map;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserCredits;

public interface UserCreditsDao extends BaseAccessDao<UserCredits> {
	public Integer selectCreditsByUser(Integer userId);
	public Integer selectActiveCount(Map<String, String> map);
}

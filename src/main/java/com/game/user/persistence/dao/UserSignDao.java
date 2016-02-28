package com.game.user.persistence.dao;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserSign;

public interface UserSignDao extends BaseAccessDao<UserSign> {
	public UserSign selectByUserId(Integer userId);
}

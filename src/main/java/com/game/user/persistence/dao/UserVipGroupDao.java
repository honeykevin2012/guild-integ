package com.game.user.persistence.dao;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserVipGroup;

public interface UserVipGroupDao extends BaseAccessDao<UserVipGroup> {
	public UserVipGroup selectDefaultLevel();
	public UserVipGroup selectCurrentLevel(Integer point);
}

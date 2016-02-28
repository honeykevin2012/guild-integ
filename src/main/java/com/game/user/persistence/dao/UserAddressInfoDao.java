package com.game.user.persistence.dao;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserAddressInfo;

public interface UserAddressInfoDao extends BaseAccessDao<UserAddressInfo> {
	public void updateSetDefault(Integer addressId);
}

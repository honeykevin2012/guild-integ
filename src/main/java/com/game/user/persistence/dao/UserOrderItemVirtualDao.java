package com.game.user.persistence.dao;

import java.util.List;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserOrderItemVirtual;

public interface UserOrderItemVirtualDao extends BaseAccessDao<UserOrderItemVirtual> {
	public void insertVirtuals(List<UserOrderItemVirtual> list);
}

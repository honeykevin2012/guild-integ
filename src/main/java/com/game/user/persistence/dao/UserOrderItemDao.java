package com.game.user.persistence.dao;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserOrderItem;

public interface UserOrderItemDao extends BaseAccessDao<UserOrderItem> {
	public List<UserOrderItem> selectByOrderId(String orderId) throws DataAccessException;
}

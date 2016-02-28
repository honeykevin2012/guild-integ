package com.game.user.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserShoppingCart;

public interface UserShoppingCartDao extends BaseAccessDao<UserShoppingCart> {
	public void deleteByProductIdAndUserId(Map<String, Integer> map) throws DataAccessException;
	public void deleteByUserId(Map<String, Object> map) throws DataAccessException;
	public List<UserShoppingCart> selectUserCart(Integer userId) throws DataAccessException;
}

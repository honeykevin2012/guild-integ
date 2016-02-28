package com.game.user.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserOrder;

public interface UserOrderDao extends BaseAccessDao<UserOrder> {
	public Long selectSumAmount(Integer userId) throws DataAccessException;
	public List<Map<String,Object>> selectUserOrdersComplete(PageQuery querys) throws DataAccessException;
	public List<Map<String,Object>> selectUserOrdersFaild(PageQuery querys) throws DataAccessException;
	public Map<String,String> selectByOrderId(UserOrder userOrder) throws DataAccessException;
	public Map<String,Object> selectVirtualItem(UserOrder userOrder) throws DataAccessException;
	public Long selectOrderRemain(String orderId) throws DataAccessException;
	public int updateRepayStatus(String orderId) throws DataAccessException;
	public int updateRefundStatus(UserOrder userOrder) throws DataAccessException;
	public int updateDeliverStatus(UserOrder order);
}

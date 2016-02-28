package com.game.payment.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.BaseException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.payment.domain.OrderInfo;
import com.game.payment.domain.UserCharge;

public interface ChargeDao extends BaseAccessDao<UserCharge> {
	public List<UserCharge> query(PageQuery query) throws BaseException;
	public int queryUserChargeListCount(Map<String, Object> param) throws BaseException;
	public void insertAndReturnOrderId(OrderInfo info) throws BaseException;
	public int updateAcountDebit(Map<String, Object> param)throws BaseException;
	public int updateOrderInfo(Map<String, Object> param)throws BaseException;
}

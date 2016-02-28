package com.game.user.persistence.dao;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserGameDeductingProcess;

public interface UserGameDeductingProcessDao extends BaseAccessDao<UserGameDeductingProcess> {
	public List<UserGameDeductingProcess> selectPayFaildList(UserGameDeductingProcess model) throws DataAccessException;
	public List<UserGameDeductingProcess> selectPaySuccessList(UserGameDeductingProcess model) throws DataAccessException;
	public int updatePayRefund(String orderId)throws DataAccessException;
}

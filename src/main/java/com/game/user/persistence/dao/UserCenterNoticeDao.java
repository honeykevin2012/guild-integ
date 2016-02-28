package com.game.user.persistence.dao;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserCenterNotice;

public interface UserCenterNoticeDao extends BaseAccessDao<UserCenterNotice> {
	
	public List<UserCenterNotice> selectByUserId(Integer id) throws DataAccessException;
}

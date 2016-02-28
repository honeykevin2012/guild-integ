package com.game.user.persistence.dao;

import java.util.List;

import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserCustomService;

public interface UserCustomServiceDao extends BaseAccessDao<UserCustomService> {
	public List<UserCustomService> selectCustomList(PageQuery query);
}

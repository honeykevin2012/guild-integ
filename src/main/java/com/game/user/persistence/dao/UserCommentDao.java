package com.game.user.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.user.domain.UserComment;

public interface UserCommentDao extends BaseAccessDao<UserComment> {
	public void updateUp(Integer id);
	public void updateUnder(Integer id);
	public List<UserComment> selectCommentByGameId(Map<String, Object> map);
}

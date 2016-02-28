package com.game.platform.persistence.dao;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.platform.domain.PlatformBlockComment;

public interface PlatformBlockCommentDao extends BaseAccessDao<PlatformBlockComment> {
	public PlatformBlockComment selectByBlock(String blockId)throws DataAccessException;
}

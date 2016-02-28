package com.game.platform.persistence.dao;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.platform.domain.PlatformAttachment;

public interface PlatformAttachmentDao extends BaseAccessDao<PlatformAttachment> {
	public void deleteByName(List<String> fileNames) throws DataAccessException;
	public List<PlatformAttachment> selectProductItem(PlatformAttachment attach);
}

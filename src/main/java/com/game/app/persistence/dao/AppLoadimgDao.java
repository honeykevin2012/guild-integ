package com.game.app.persistence.dao;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.app.domain.AppLoadimg;

public interface AppLoadimgDao extends BaseAccessDao<AppLoadimg> {

	AppLoadimg selectByOsType(Integer osType);

}

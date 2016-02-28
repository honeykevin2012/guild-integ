package com.game.platform.persistence.dao;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.platform.domain.PlatformGameAppTrend;

public interface PlatformGameAppTrendDao extends BaseAccessDao<PlatformGameAppTrend> {
	public PlatformGameAppTrend selectByGameId(String gameId);
}

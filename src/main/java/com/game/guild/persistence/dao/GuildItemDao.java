package com.game.guild.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildItem;

public interface GuildItemDao extends BaseAccessDao<GuildItem> {
	public List<GuildItem> selectByTypeId(Map<String, Object> map);
	public void deleteByTypeId(Map<String, Object> map);
}

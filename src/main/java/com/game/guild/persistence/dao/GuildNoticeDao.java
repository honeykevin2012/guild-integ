package com.game.guild.persistence.dao;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildNotice;

public interface GuildNoticeDao extends BaseAccessDao<GuildNotice> {
	public List<GuildNotice> selectByGuild(Integer id) throws DataAccessException;
	public List<GuildNotice> selectEditList(PageQuery querys) throws DataAccessException;
}

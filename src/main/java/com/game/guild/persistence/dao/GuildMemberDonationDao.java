package com.game.guild.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildMemberDonation;

public interface GuildMemberDonationDao extends BaseAccessDao<GuildMemberDonation> {
	public int deleteByUserAndGuild(Map<String, Integer> map) throws DataAccessException;
	public List<GuildMemberDonation> selectUserDonation(PageQuery querys) throws DataAccessException;
	public int deleteByGuildId(Integer guildId) throws DataAccessException;
}

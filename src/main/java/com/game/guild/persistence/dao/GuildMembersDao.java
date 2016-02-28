package com.game.guild.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.GuildMembers;
import com.game.guild.domain.MemberVO;

public interface GuildMembersDao extends BaseAccessDao<GuildMembers> {
	public List<GuildMembers> selectMemberList(PageQuery querys) throws DataAccessException;
	public List<GuildMembers> selectByUserIds(Integer[] ids) throws DataAccessException;
	public List<MemberVO> selectAdminMember(Integer userId) throws DataAccessException;
	public int updateMemberApply(Integer id) throws DataAccessException;
	public int updateMemberIsAdmin(Integer id) throws DataAccessException;
	public int updateMemberRemoveAdmin(Integer id) throws DataAccessException;
	public GuildMembers selectByUserAndGuildId(Map<String, Integer> map) throws DataAccessException;
	public int deleteByUserAndGuild(Map<String, Integer> map) throws DataAccessException;
	public int deleteByGuildId(Integer guildId) throws DataAccessException;
	public int deleteByUserId(Integer userId) throws DataAccessException;
	public int updateMemberApplyByIds(Integer[] ids) throws DataAccessException;
	public List<GuildMembers> selectAdminByLevel(Integer guildId);
	public int deleteApplyUserExcludeCurrGuild(Map<String, Object> map) throws DataAccessException;
	public List<Integer> selectUsersByIds(Integer[] ids) throws DataAccessException;
}

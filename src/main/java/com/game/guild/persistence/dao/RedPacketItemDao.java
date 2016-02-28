package com.game.guild.persistence.dao;

import java.util.List;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.RedPacketItem;

public interface RedPacketItemDao extends BaseAccessDao<RedPacketItem> {

	public int insertRedPacketItem(List<RedPacketItem> insertList)throws DataAccessException;

	public RedPacketItem selectOne(Integer packetId)throws DataAccessException;

}

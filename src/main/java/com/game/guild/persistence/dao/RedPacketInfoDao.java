package com.game.guild.persistence.dao;

import java.util.Map;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.guild.domain.RedPacketInfo;

public interface RedPacketInfoDao extends BaseAccessDao<RedPacketInfo> {

	RedPacketInfo selectGuildLimit(Map<String, Object> map);

}

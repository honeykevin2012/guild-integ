package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.GuildsCancelAdminLog;

public class GuildsCancelAdminLogDao extends MongoBaseDao<GuildsCancelAdminLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public GuildsCancelAdminLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public GuildsCancelAdminLog save(GuildsCancelAdminLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<GuildsCancelAdminLog> getEntityClass() {
		return GuildsCancelAdminLog.class;
	}
}

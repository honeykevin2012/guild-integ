package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.GuildsDissolveLog;

public class GuildsDissolveLogDao extends MongoBaseDao<GuildsDissolveLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public GuildsDissolveLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public GuildsDissolveLog save(GuildsDissolveLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<GuildsDissolveLog> getEntityClass() {
		return GuildsDissolveLog.class;
	}
}

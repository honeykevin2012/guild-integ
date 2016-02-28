package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.GuildsNameChangeLog;

public class GuildsNameChangeLogDao extends MongoBaseDao<GuildsNameChangeLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public GuildsNameChangeLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public GuildsNameChangeLog save(GuildsNameChangeLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<GuildsNameChangeLog> getEntityClass() {
		return GuildsNameChangeLog.class;
	}
}

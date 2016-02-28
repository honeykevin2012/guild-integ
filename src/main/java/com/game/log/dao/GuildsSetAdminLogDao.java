package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.GuildsSetAdminLog;

public class GuildsSetAdminLogDao extends MongoBaseDao<GuildsSetAdminLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public GuildsSetAdminLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public GuildsSetAdminLog save(GuildsSetAdminLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<GuildsSetAdminLog> getEntityClass() {
		return GuildsSetAdminLog.class;
	}
}

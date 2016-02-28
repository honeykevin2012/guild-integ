package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.GuildsGiftLog;

public class GuildsGiftLogDao extends MongoBaseDao<GuildsGiftLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public GuildsGiftLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public GuildsGiftLog save(GuildsGiftLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<GuildsGiftLog> getEntityClass() {
		return GuildsGiftLog.class;
	}
}

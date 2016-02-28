package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.MallGuildsOrderSubmitLog;

public class MallGuildsOrderSubmitLogDao extends MongoBaseDao<MallGuildsOrderSubmitLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public MallGuildsOrderSubmitLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public MallGuildsOrderSubmitLog save(MallGuildsOrderSubmitLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<MallGuildsOrderSubmitLog> getEntityClass() {
		return MallGuildsOrderSubmitLog.class;
	}
}

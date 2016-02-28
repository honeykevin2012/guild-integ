package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.PayMsgLog;

public class PayMsgLogDao extends MongoBaseDao<PayMsgLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public PayMsgLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public PayMsgLog save(PayMsgLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<PayMsgLog> getEntityClass() {
		return PayMsgLog.class;
	}
}

package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.UserLoginLog;

public class UserLoginLogDao extends MongoBaseDao<UserLoginLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public UserLoginLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public UserLoginLog save(UserLoginLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<UserLoginLog> getEntityClass() {
		return UserLoginLog.class;
	}
}

package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.UserInfoBindLog;

public class UserInfoBindLogDao extends MongoBaseDao<UserInfoBindLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public UserInfoBindLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public UserInfoBindLog save(UserInfoBindLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<UserInfoBindLog> getEntityClass() {
		return UserInfoBindLog.class;
	}
}

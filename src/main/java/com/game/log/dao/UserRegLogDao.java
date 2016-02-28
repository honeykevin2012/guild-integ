package com.game.log.dao;

import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.game.common.basics.mongo.MongoBaseDao;
import com.game.log.entity.UserRegLog;

public class UserRegLogDao extends MongoBaseDao<UserRegLog> {
	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public UserRegLog findOne(Map<String, Object> params) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(params.get("id")));
		return super.findOne(query);
	}

	/**
	 * @param id
	 * @param params
	 */
	public UserRegLog save(UserRegLog entity) {
		return super.save(entity);
	}

	@Override
	protected Class<UserRegLog> getEntityClass() {
		return UserRegLog.class;
	}
}

package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.UserVisitLogDao;


/**
 * 用户访问
 * @author kevin
 *
 */
@Document(collection = "user_visit_log")
public class UserVisitLog extends BaseLogger{
	
	private String userId;
	private String url;
	@Override
	public void create() {
		UserVisitLogDao logger = new UserVisitLogDao();
		logger.save(this);
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}

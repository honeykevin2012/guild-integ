package com.game.news.persistence.dao;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.news.domain.Article;

public interface ArticleDao extends BaseAccessDao<Article> {
	public void updateStatus(Article zstArticle) throws DataAccessException;
}

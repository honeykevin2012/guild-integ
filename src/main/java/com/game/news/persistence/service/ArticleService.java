/*
 * @author yangchengwei
 * created on 2014-09-30
 */
 
package com.game.news.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.news.domain.Article;
import com.game.news.persistence.dao.ArticleDao;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao zstArticleDao;
	
	public List<Article> query(PageQuery querys) throws DataAccessException {
		try {
			return zstArticleDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Article> selectAll() throws DataAccessException {
		try {
			return zstArticleDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Article> selectByEntity(Article zstArticle) throws DataAccessException {
		try {
			return zstArticleDao.selectByEntity(zstArticle);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public Article select(Integer id) throws DataAccessException {
		try {
			return zstArticleDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(Article zstArticle) throws DataAccessException {
		try {
			zstArticleDao.insert(zstArticle);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(Article zstArticle) throws DataAccessException {
		try {
			zstArticleDao.update(zstArticle);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void updateStatus(Article zstArticle) throws DataAccessException {
		try {
			zstArticleDao.updateStatus(zstArticle);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			zstArticleDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			zstArticleDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

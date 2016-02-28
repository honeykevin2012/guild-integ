/*
 * @author zhaolianjun
 * created on 2015-01-05
 */
 
package com.game.shop.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.shop.domain.RefProductPromotion;
import com.game.shop.persistence.dao.RefProductPromotionDao;

@Service
public class RefProductPromotionService {
	
	@Autowired
	private RefProductPromotionDao refProductPromotionDao;
	
	public List<RefProductPromotion> query(PageQuery querys) throws DataAccessException {
		try {
			return refProductPromotionDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<RefProductPromotion> selectAll() throws DataAccessException {
		try {
			return refProductPromotionDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<RefProductPromotion> selectByEntity(RefProductPromotion refProductPromotion) throws DataAccessException {
		try {
			return refProductPromotionDao.selectByEntity(refProductPromotion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public RefProductPromotion select(Integer id) throws DataAccessException {
		try {
			return refProductPromotionDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(RefProductPromotion refProductPromotion) throws DataAccessException {
		try {
			refProductPromotionDao.insert(refProductPromotion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(RefProductPromotion refProductPromotion) throws DataAccessException {
		try {
			refProductPromotionDao.update(refProductPromotion);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			refProductPromotionDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			refProductPromotionDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void deleteByEntity(RefProductPromotion ref)throws DataAccessException {
		try {
			refProductPromotionDao.deleteByEntity(ref);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<RefProductPromotion> selectPromotionByProductId(Integer product) throws DataAccessException {
		try {
			return refProductPromotionDao.selectPromotionByProductId(product);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<RefProductPromotion> selectPromotionByUserAndProduct(Integer productId, Integer groupLevel)throws DataAccessException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productId);
			map.put("groupLevel", groupLevel);
			return refProductPromotionDao.selectPromotionByUserAndProduct(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}
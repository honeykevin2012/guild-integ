/*
 * @author zhaolianjun
 * created on 2014-12-16
 */
 
package com.game.shop.persistence.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.shop.domain.ProductInfo;
import com.game.shop.persistence.dao.ProductInfoDao;

@Service
@CacheConfig(cacheNames={"testCache"})
public class ProductInfoService {
	
	@Autowired
	private ProductInfoDao productInfoDao;
	
	//@LoadFromMemcached(value="Query:product:", timeout=3600)
	//@Cacheable(value = "default", key="#querys.pageNo")
	//@ReadThroughSingleCache(namespace = "test", expiration = 30000)
	public List<ProductInfo> query(PageQuery querys) throws DataAccessException {
		try {
			return productInfoDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<ProductInfo> selectAll() throws DataAccessException {
		try {
			return productInfoDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<ProductInfo> selectByEntity(ProductInfo productInfo) throws DataAccessException {
		try {
			return productInfoDao.selectByEntity(productInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public ProductInfo select(Integer id) throws DataAccessException {
		try {
			return productInfoDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void insert(ProductInfo productInfo) throws DataAccessException {
		try {
			productInfoDao.insert(productInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void update(ProductInfo productInfo) throws DataAccessException {
		try {
			productInfoDao.update(productInfo);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			productInfoDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			productInfoDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<ProductInfo> selectEntityByIds(List<Integer> ids, String channel, String size, String isDefault)throws DataAccessException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("channel", channel);
			map.put("picsize", size);
			map.put("isDefault", isDefault);
			return productInfoDao.selectEntityByIds(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public List<ProductInfo> selectEntityByCart(Integer userId, List<Integer> productIds, String channel, String size, String isDefault)throws DataAccessException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("channel", channel);
			map.put("picsize", size);
			map.put("productIds", productIds);
			map.put("isDefault", isDefault);
			return productInfoDao.selectEntityByCart(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<ProductInfo> selectUserExchangeItems(Long platAmount, String channel, String size, String isDefault)throws DataAccessException {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("channel", channel);
				map.put("picsize", size);
				map.put("isDefault", isDefault);
				map.put("platAmount", platAmount);
				return productInfoDao.selectUserExchangeItems(map);
			} catch (DataAccessException e) {
				throw new DataAccessException(e);
			}
	}
	
	public List<ProductInfo> selectEntityByOrderPaid(PageQuery querys) throws DataAccessException {
		try {

			return productInfoDao.selectEntityByOrderPaid(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public int updateStock(ProductInfo product) throws DataAccessException {
		try {
			return productInfoDao.updateStock(product);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<ProductInfo> selectHotList ( String channel, String size, String isDefault)throws DataAccessException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channel", channel);
			map.put("picsize", size);
			map.put("isDefault", isDefault);
			return productInfoDao.selectHotList(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
}

	public List<ProductInfo> selectProductByIds(String[] ids, String channel, String size, String isDefault) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channel", channel);
			map.put("picsize", size);
			map.put("isDefault", isDefault);
			map.put("ids", ids);
			return productInfoDao.selectProductByIds(map);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

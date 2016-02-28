/*
 * @author zhaolianjun
 * created on 2015-08-10
 */
 
package com.game.shop.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.common.basics.exception.DataAccessException;

import com.game.common.basics.pagination.PageQuery;
import com.game.shop.domain.ProductInfoVirtual;
import com.game.shop.persistence.dao.ProductInfoVirtualDao;

@Service
public class ProductInfoVirtualService {
	
	@Autowired
	private ProductInfoVirtualDao productInfoVirtualDao;
	
	public List<ProductInfoVirtual> query(PageQuery querys) throws DataAccessException {
		try {
			return productInfoVirtualDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<ProductInfoVirtual> selectAll() throws DataAccessException {
		try {
			return productInfoVirtualDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<ProductInfoVirtual> selectByEntity(ProductInfoVirtual productInfoVirtual) throws DataAccessException {
		try {
			return productInfoVirtualDao.selectByEntity(productInfoVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public ProductInfoVirtual select(Integer id) throws DataAccessException {
		try {
			return productInfoVirtualDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void insert(ProductInfoVirtual productInfoVirtual) throws DataAccessException {
		try {
			productInfoVirtualDao.insert(productInfoVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void update(ProductInfoVirtual productInfoVirtual) throws DataAccessException {
		try {
			productInfoVirtualDao.update(productInfoVirtual);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public int updateStatus(List<Integer> ids) throws DataAccessException {
		try {
			return productInfoVirtualDao.updateStatus(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void delete(Integer id) throws DataAccessException {
		try {
			productInfoVirtualDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			productInfoVirtualDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
}

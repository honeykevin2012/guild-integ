package com.game.shop.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.shop.domain.ProductInfo;

public interface ProductInfoDao extends BaseAccessDao<ProductInfo> {
	public List<ProductInfo> selectEntityByIds(Map<String, Object> map);
	public List<ProductInfo> selectEntityByCart(Map<String, Object> map);
	public List<ProductInfo> selectEntityByOrderPaid(PageQuery querys);
	public int updateStock(ProductInfo product);
	public List<ProductInfo> selectUserExchangeItems(Map<String, Object> map);
	public List<ProductInfo> selectHotList(Map<String, Object> map);
	public List<ProductInfo> selectProductByIds(Map<String, Object> map);
	public ProductInfo selectProductById(Map<String, Object> map);
}

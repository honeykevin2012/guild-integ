package com.game.shop.persistence.dao;

import java.util.List;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.shop.domain.ProductInfoVirtual;

public interface ProductInfoVirtualDao extends BaseAccessDao<ProductInfoVirtual> {
	public int updateStatus(List<Integer> ids);
}

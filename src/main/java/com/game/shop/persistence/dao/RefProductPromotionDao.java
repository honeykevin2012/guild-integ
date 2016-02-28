package com.game.shop.persistence.dao;

import java.util.List;
import java.util.Map;

import com.game.common.basics.persistence.access.BaseAccessDao;
import com.game.shop.domain.RefProductPromotion;

public interface RefProductPromotionDao extends BaseAccessDao<RefProductPromotion> {
	public void deleteByEntity(RefProductPromotion ref);
	public List<RefProductPromotion> selectPromotionByProductId(Integer product);
	public List<RefProductPromotion> selectPromotionByUserAndProduct(Map<String, Object> map);
}

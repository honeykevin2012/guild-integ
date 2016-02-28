package com.game.shop.helper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.common.basics.ApplicationContextLoader;
import com.game.shop.domain.RefProductPromotion;
import com.game.shop.persistence.dao.RefProductPromotionDao;

public class DiscountHelper {
	private static RefProductPromotionDao refPromotionDao = (RefProductPromotionDao) ApplicationContextLoader.getBean("refProductPromotionDao");
	
	/**
	 * 根据商品ID 和 当前用户所在用户组级别， 计算该商品的【折扣后单价】
	 * @param productId
	 * @param groupLevel
	 * @return
	 */
	public static double getDiscountAmount(Integer productId, Integer groupLevel){
		RefProductPromotion ref = getEntity(productId, groupLevel);
		if(ref == null) return 0;
		double price = ref.getPrice();//商品原始单价
		double discount = ref.getConditions();//活动折扣比例
		double discountAmount = amount(price, discount/10);
		if(discountAmount > 0) return discountAmount;//防止折扣出错，再次进行校验
		return -1;
	}
	
	/**
	 * 根据商品ID 和 当前用户所在用户组级别， 查询该商品的活动基本信息
	 * @param productId
	 * @param groupLevel
	 * @return 返回基本信息对象
	 */
	private static RefProductPromotion getEntity(Integer productId, Integer groupLevel){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("groupLevel", groupLevel);
		List<RefProductPromotion> ref = refPromotionDao.selectPromotionByUserAndProduct(map);
		if(ref != null && ref.size() > 0) 
			return ref.get(0);
		return null;
	}
	
	private static double amount(double price, double discount){
		BigDecimal decp = new BigDecimal(Double.toString(price));
		BigDecimal decc = new BigDecimal(Double.toString(discount));
		BigDecimal total = decp.multiply(decc);
		return total.doubleValue();
	}
}

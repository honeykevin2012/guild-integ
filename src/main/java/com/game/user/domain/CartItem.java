package com.game.user.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.common.basics.ApplicationContextLoader;
import com.game.common.basics.pagination.PageQuery;
import com.game.shop.domain.ProductInfo;
import com.game.shop.domain.ProductInfoVirtual;
import com.game.shop.helper.DiscountHelper;
import com.game.shop.persistence.dao.ProductInfoVirtualDao;
import com.game.user.persistence.dao.UserDao;

public class CartItem {
	private static UserDao userDao = (UserDao) ApplicationContextLoader.getBean("userDao");
	private static ProductInfoVirtualDao virtualDao = (ProductInfoVirtualDao) ApplicationContextLoader.getBean("productInfoVirtualDao");
	
	public static final String STATUS = "1";//在售状态
	public static final String IS_VIRTUAL = "1";//虚拟商品
	
	private int quantity;
	private int userId;
	private ProductInfo product;
	private List<ProductInfoVirtual> virtuals = new ArrayList<ProductInfoVirtual>();

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductInfo getProduct() {
		return product;
	}

	public void setProduct(ProductInfo product) {
		this.product = product;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * 获取虚拟商品
	 */
	public List<ProductInfoVirtual> getVirtuals() {
		if(!virtuals.isEmpty()) return this.virtuals;
		if(IS_VIRTUAL.equals(product.getIsVirtual()) && quantity > 0){
			PageQuery query = new PageQuery();
			query.setPageSize(quantity);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("isUsable", STATUS);
			query.setParams(params);
			List<ProductInfoVirtual> list = virtualDao.pageQuery(query);
			if(list != null && !list.isEmpty()) virtuals = list;
		}
		return virtuals;
	}
	
	/**
	 * 创建订单虚拟商品明细
	 * @param virtuals
	 * @param orderItemId
	 * @return
	 */
	public List<UserOrderItemVirtual> getOrderItemVirtual(List<ProductInfoVirtual> virtuals, Integer orderItemId){
		List<UserOrderItemVirtual> list = new ArrayList<UserOrderItemVirtual>();
		if(!virtuals.isEmpty()){
			for(ProductInfoVirtual virtual : virtuals){
				UserOrderItemVirtual item = new UserOrderItemVirtual();
				item.setCardNumber(virtual.getCardNumber());
				item.setCodeId(virtual.getId());
				item.setCodeValue(virtual.getPasswordCode());
				item.setOrderItemId(orderItemId);
				list.add(item);
			}
		}
		return list;
	}
	
	public List<Integer> identityList(List<ProductInfoVirtual> list){
		List<Integer> ids = new ArrayList<Integer>();
		for(ProductInfoVirtual virtual : list) ids.add(virtual.getId());
		return ids;
	}

	public void setVirtuals(List<ProductInfoVirtual> virtuals) {
		this.virtuals = virtuals;
	}

	/**
	 * 商品总价
	 * @return
	 */
	public Long getTotalAmount(){
		if(quantity == 0) return 0L;
		return (product.getPrice() * quantity);
	}
	
	/**
	 * 商品折后价格
	 * @return
	 */
	public Long getDiscountAmount(){
		if(Cart.PRODUCT_PERSON.equals(product.getType())){
			User user = userDao.select(userId);
			Double discountAmount = DiscountHelper.getDiscountAmount(product.getId(), user.getGroupLevel());
			Long total = discountAmount.longValue() * quantity;
			if(total == 0) total = this.getTotalAmount();
			return total;
		}else if(Cart.PRODUCT_GUILD.equals(product.getType())){
			return product.getPrice() * quantity;
		}else{
			return product.getPrice();
		}
	}
	/**
	 * 商品类型
	 * @return
	 */
	public String isLegal(){
		return product.getType();
	}
	/**
	 * 判断库存量是否充足
	 * @return
	 */
	public boolean hasStock(){
		return this.quantity <= product.getCount();
	}
	/**
	 * 是否在售, 没有下架
	 * @return
	 */
	public boolean isOnSale(){
		return STATUS.equals(product.getStatus());
	}
	
	public String isVirtual() {
		if (IS_VIRTUAL.equals(product.getIsVirtual())) {
			return IS_VIRTUAL;
		} else {
			return "-1";
		}
	}
}

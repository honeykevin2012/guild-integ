package com.game.user.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.game.common.Constants;
import com.game.common.basics.ApplicationContextLoader;
import com.game.shop.domain.ProductInfo;
import com.game.shop.persistence.dao.ProductInfoDao;
import com.game.user.utils.OrderHelper;

public class Cart {
	
	private static ProductInfoDao productDao = (ProductInfoDao) ApplicationContextLoader.getBean("productInfoDao");
	public static final String PRODUCT_PERSON = "1";//商城分类, 个人商城
	public static final String PRODUCT_GUILD = "2";//商城分类, 公会商城
	
	public Cart(){
		
	}
	/**
	 * 个人商城
	 * @param params
	 * @param userId
	 */
	public Cart(List<CartParam> params, Integer userId){
		this.params = params;
		this.userId = userId;
		this.createItems();
		this.createOrderId();
		this.setType(PRODUCT_PERSON);
	}
	/**
	 * 公会商城
	 * @param params
	 * @param userId
	 * @param guildId
	 */
	public Cart(List<CartParam> params, Integer userId, Integer guildId){
		this.params = params;
		this.userId = userId;
		this.guildId = guildId;
		this.createItems();
		this.createOrderId();
		this.setType(PRODUCT_GUILD);
	}
	
	private int userId;
	private int guildId = 0;
	private String status = Constants.STATUS;
	private String orderId;
	private String type;
	
	/**
	 * 保存购买商品参数
	 */
	private List<CartParam> params = new ArrayList<CartParam>();
	private List<CartItem> items = new ArrayList<CartItem>();
	
	public List<Integer> productIds(){
		List<Integer> ids = new ArrayList<Integer>();
		if(this.getParams() != null){
			for(CartParam param : this.getParams()){
				ids.add(param.getId());
			}
		}
		return ids;
	}

	public List<ProductInfo> getProducts(){
		Map<String, Object> findMap = new HashMap<String, Object>();
		findMap.put("channel", Constants.PRODUCT_CHANNEL);
		findMap.put("picsize", Constants.PRODUCT_DEFAULT_IMG_SIZE);
		findMap.put("isDefault", Constants.PRODUCT_DEFAULT_FLAG);
		findMap.put("ids", this.productIds());
		List<ProductInfo> products = productDao.selectEntityByIds(findMap);
		return products;
	}
	
	public Map<Integer, ProductInfo> productMap(){
		Map<Integer, ProductInfo> map = new HashMap<Integer, ProductInfo>();
		for(ProductInfo product : this.getProducts()){
			map.put(product.getId(), product);
		}
		return map;
	}
	
	public void createItems(){
		if(this.getParams() == null) return;
		for(CartParam param : this.getParams()){
			CartItem item = new CartItem();
			item.setQuantity(param.getQuantity());
			item.setProduct(this.productMap().get(param.getId()));
			item.setUserId(userId);
			if(param.getList() != null && !param.getList().isEmpty()){//外部传入的虚拟商品,此时不查虚拟商品数据库，目前用在物品赠送
				item.setVirtuals(param.getList());
			}
			this.items.add(item);
		}
	}
	public void createOrderId(){
		this.orderId = OrderHelper.createOrderNumber();
	}
	/**
	 * 全部商品总价
	 * @return
	 */
	public Long getTotalAmount(){
		long total = 0L;
		for(CartItem item : this.items){
			total += item.getTotalAmount();
		}
		return total;
	}
	
	/**
	 * 全部商品折后总价
	 * @return
	 */
	public Long getDiscountAmount(){
		long total = 0L;
		for(CartItem item : this.items){
			total += item.getDiscountAmount();
		}
		return total;
	}
	/**
	 * 验证购买商品类型是否合法
	 * @return
	 */
	public boolean isLegal(){
		Set<String> set = new HashSet<String>();
		for(CartItem item : this.items){
			set.add(item.isLegal());
		}
		if(set.size() > 1) return false;
		return true;
	}
	/**
	 * 判断库存量是否充足
	 * @return
	 */
	public boolean hasStock(){
		for(CartItem item : this.items){
			if(!item.hasStock()) return false;
		}
		return true;
	}
	/**
	 * 是否下架
	 * @return
	 */
	public boolean isOnSale(){
		for(CartItem item : this.items){
			if(!item.isOnSale()) return false;
		}
		return true;
	}
	
	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<CartParam> getParams() {
		return params;
	}

	public void setParams(List<CartParam> params) {
		this.params = params;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getGuildId() {
		return guildId;
	}
	public void setGuildId(int guildId) {
		this.guildId = guildId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}

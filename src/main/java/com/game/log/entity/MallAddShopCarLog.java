package com.game.log.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.game.log.dao.MallAddShopCarLogDao;


/**
 * 添加购物车
 * @author kevin
 *
 */
@Document(collection = "mall_addShopCar_log")
public class MallAddShopCarLog extends BaseLogger{
	
	private String userId;
	private String productType;//
	private String addType;//
	private String productName;//商品名称
	private String productId;//
	private String count;//
	@Override
	public void create() {
		MallAddShopCarLogDao logger = new MallAddShopCarLogDao();
		logger.save(this);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}


	
}

package com.game.common.utility.logger.realizes;

import com.game.common.utility.logger.BaseLogger;

/**
 * 添加购物车
 * @author kevin
 *
 */
public class MallAddShopCarLog extends BaseLogger{
	
	private String userId;
	private String productType;//
	private String addType;//
	private String productName;//商品名称
	private String productId;//
	private String count;//
	public MallAddShopCarLog() {
		super("MallAddShopCarLog");
	}

	@Override
	public String create() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("NB_MallAddShopCar").append(this.append);
		buffer.append(getTimestamp()).append(this.append);//时间戳
	    buffer.append(getLogId()).append(this.append);//日志id
	    buffer.append(userId).append(this.append);//用户id
	    buffer.append(productType).append(this.append);//商品类型1平台2公会
	    buffer.append(addType).append(this.append);//添加类型1列表2详情
	    buffer.append(productName).append(this.append);//商品名称
	    buffer.append(productId).append(this.append);//商品id
	    buffer.append(count).append(this.append);//数量
	    //公共参数
	    buffer.append(this.baseParameter());
	    return buffer.toString();
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

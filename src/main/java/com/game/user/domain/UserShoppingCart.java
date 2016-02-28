/*
 * @athor zhaolianjun
 * created on 2015-01-21
 */

package com.game.user.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.basics.domain.BaseEntity;
import com.game.shop.domain.ProductInfo;

/**
 * @desc:购物车 <br>
 * @author zhaolianjun
 */

public class UserShoppingCart extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;// ID
	@NotNull(message = "error#用户ID不能为空.")
	private Integer userId;// 用户ID
	@NotNull(message = "error#商品ID不能为空.")
	private Integer productId;// 商品ID
	@NotNull(message = "error#商品数量不能为空.")
	private Integer count;// 数量
	
	private String os;
	
	private Integer quantity;
	public ProductInfo product;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public ProductInfo getProduct() {
		return product;
	}

	public void setProduct(ProductInfo product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}

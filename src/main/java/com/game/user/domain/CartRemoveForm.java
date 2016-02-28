package com.game.user.domain;

import java.util.List;

import com.game.common.BaseForm;

public class CartRemoveForm extends BaseForm {
	private List<Integer> productId;
	private Integer userId;

	public List<Integer> getProductId() {
		return productId;
	}

	public void setProductId(List<Integer> productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

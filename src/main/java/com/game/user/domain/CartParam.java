package com.game.user.domain;

import java.util.List;

import com.game.shop.domain.ProductInfoVirtual;

public class CartParam {
	private int id;// 商品ID
	private int quantity;// 购买数量
	private List<ProductInfoVirtual> list;//虚拟商品

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<ProductInfoVirtual> getList() {
		return list;
	}

	public void setList(List<ProductInfoVirtual> list) {
		this.list = list;
	}
}
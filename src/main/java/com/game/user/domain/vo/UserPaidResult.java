package com.game.user.domain.vo;

public class UserPaidResult {
	private Integer productId;// :商品ID
	private String productName;// :商品名称
	private Double price;// :单价
	private String image;// :商品图片
	private Integer count;// :购买数量
	private String acceptName;
	private String address;
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}

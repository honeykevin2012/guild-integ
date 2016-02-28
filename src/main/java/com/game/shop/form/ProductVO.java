package com.game.shop.form;

public class ProductVO {
	private Integer productId;
	private String productName;
	private Long price;
	private String image;
	private String exclusive;
	private String status;
	private Integer saleTimes;
	private String itemUrl;
	private String count;
	private String saleStatus;
	private String type;

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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getExclusive() {
		return exclusive;
	}

	public void setExclusive(String exclusive) {
		this.exclusive = exclusive;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSaleTimes() {
		return saleTimes;
	}

	public void setSaleTimes(Integer saleTimes) {
		this.saleTimes = saleTimes;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

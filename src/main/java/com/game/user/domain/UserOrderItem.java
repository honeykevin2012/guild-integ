/*
 * @athor zhaolianjun
 * created on 2015-01-29
 */
 
package com.game.user.domain;

import java.util.HashSet;
import java.util.Set;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:订单明细 <br>
 * @author zhaolianjun
 */
 
public class UserOrderItem extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private Integer id;//ID
	private Integer productId;//商品ID
	private String productName;//商品名称
	private String orderId;//订单ID
	private Long productPrice;//商品定价
	private Integer count;//数量
	private Double totalAmount;//总金额
	private Double payAmount;//实际付款金额
	private Double discountAmount;//优惠金额
	private String status;//状态
	private String isVirtual;
	private String productPhoto;
	private Set<UserOrderItemVirtual> virtuals = new HashSet<UserOrderItemVirtual>();

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getProductId(){
		return this.productId;
	}
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	public String getProductName(){
		return this.productName;
	}
	public void setProductName(String productName){
		this.productName = productName;
	}
	public String getOrderId(){
		return this.orderId;
	}
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	public Long getProductPrice(){
		return this.productPrice;
	}
	public void setProductPrice(Long productPrice){
		this.productPrice = productPrice;
	}
	public Integer getCount(){
		return this.count;
	}
	public void setCount(Integer count){
		this.count = count;
	}
	public Double getTotalAmount(){
		return this.totalAmount;
	}
	public void setTotalAmount(Double totalAmount){
		this.totalAmount = totalAmount;
	}
	public Double getPayAmount(){
		return this.payAmount;
	}
	public void setPayAmount(Double payAmount){
		this.payAmount = payAmount;
	}
	public Double getDiscountAmount(){
		return this.discountAmount;
	}
	public void setDiscountAmount(Double discountAmount){
		this.discountAmount = discountAmount;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getIsVirtual() {
		return isVirtual;
	}
	public void setIsVirtual(String isVirtual) {
		this.isVirtual = isVirtual;
	}
	public Set<UserOrderItemVirtual> getVirtuals() {
		return virtuals;
	}
	public void setVirtuals(Set<UserOrderItemVirtual> virtuals) {
		this.virtuals = virtuals;
	}
	public String getProductPhoto() {
		return productPhoto;
	}
	public void setProductPhoto(String productPhoto) {
		this.productPhoto = productPhoto;
	}
	
}

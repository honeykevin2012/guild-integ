/*
 * @athor zhaolianjun
 * created on 2015-08-17
 */
 
package com.game.user.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:虚拟商品明细表 <br>
 * @author zhaolianjun
 */
 
public class UserOrderItemVirtual extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private Integer orderItemId;//订单明细ID
	private String codeValue;//虚拟物品账号或密码
	private String cardNumber;//卡号
	private Integer codeId;//主键

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getOrderItemId(){
		return this.orderItemId;
	}
	public void setOrderItemId(Integer orderItemId){
		this.orderItemId = orderItemId;
	}
	public String getCodeValue(){
		return this.codeValue;
	}
	public void setCodeValue(String codeValue){
		this.codeValue = codeValue;
	}
	public String getCardNumber(){
		return this.cardNumber;
	}
	public void setCardNumber(String cardNumber){
		this.cardNumber = cardNumber;
	}
	public Integer getCodeId(){
		return this.codeId;
	}
	public void setCodeId(Integer codeId){
		this.codeId = codeId;
	}
}

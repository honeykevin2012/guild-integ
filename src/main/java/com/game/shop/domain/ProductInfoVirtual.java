/*
 * @athor zhaolianjun
 * created on 2015-08-10
 */
 
package com.game.shop.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:虚拟商品明细表 <br>
 * @author zhaolianjun
 */
 
public class ProductInfoVirtual extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private Integer productId;//商品ID
	private String passwordCode;//商品虚拟密码
	private String cardNumber;//卡号
	private String isUsable;//是否被使用1可用0已使用
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//create_time

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
	public String getPasswordCode(){
		return this.passwordCode;
	}
	public void setPasswordCode(String passwordCode){
		this.passwordCode = passwordCode;
	}
	public String getCardNumber(){
		return this.cardNumber;
	}
	public void setCardNumber(String cardNumber){
		this.cardNumber = cardNumber;
	}
	public String getIsUsable(){
		return this.isUsable;
	}
	public void setIsUsable(String isUsable){
		this.isUsable = isUsable;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
}

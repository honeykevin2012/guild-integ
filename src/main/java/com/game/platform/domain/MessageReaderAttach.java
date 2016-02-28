/*
 * @athor zhaolianjun
 * created on 2015-08-06
 */
 
package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:邮件附件表 <br>
 * @author zhaolianjun
 */
 
public class MessageReaderAttach extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private String name;
	private String type;//附件类型
	private Integer dataId;//数据ID
	private Integer quantity;//数量
	private Integer messageReaderId;//message_reader_id
	private String orderId;//来源订单号, 虚拟物品需要
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	public Integer getDataId(){
		return this.dataId;
	}
	public void setDataId(Integer dataId){
		this.dataId = dataId;
	}
	public Integer getQuantity(){
		return this.quantity;
	}
	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}
	public Integer getMessageReaderId(){
		return this.messageReaderId;
	}
	public void setMessageReaderId(Integer messageReaderId){
		this.messageReaderId = messageReaderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "MessageReaderAttach [id=" + id + ", name=" + name + ", type=" + type + ", dataId=" + dataId + ", quantity=" + quantity + ", messageReaderId=" + messageReaderId + ", orderId=" + orderId + "]";
	}
}

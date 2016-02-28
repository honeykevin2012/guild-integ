/*
 * @athor yangchengwei
 * created on 2015-03-03
 */
 
package com.game.guild.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:公会物品 <br>
 * @author yangchengwei
 */
 
public class GuildItem extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private String name;//物品名称
	private String status;//状态
	private String isVirtual;//是否虚拟物品0实物1虚拟
	private Integer guildId;//公会ID
	private Integer count;//物品数量
	private Integer givedQuantity;//已赠送数量
	private Integer typeId;//物品ID（is_virtual=1时 激活码类型 is_virtual=0实物商品ID）
	private String remark;
	private String productPhoto;//商品图片
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getIsVirtual(){
		return this.isVirtual;
	}
	public void setIsVirtual(String isVirtual){
		this.isVirtual = isVirtual;
	}
	public Integer getGuildId(){
		return this.guildId;
	}
	public void setGuildId(Integer guildId){
		this.guildId = guildId;
	}
	public Integer getCount(){
		return this.count;
	}
	public void setCount(Integer count){
		this.count = count;
	}
	public Integer getTypeId(){
		return this.typeId;
	}
	public void setTypeId(Integer typeId){
		this.typeId = typeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getGivedQuantity() {
		return givedQuantity;
	}
	public void setGivedQuantity(Integer givedQuantity) {
		this.givedQuantity = givedQuantity;
	}
	public String getProductPhoto() {
		return productPhoto;
	}
	public void setProductPhoto(String productPhoto) {
		this.productPhoto = productPhoto;
	}
	/**
	 * 判断是否虚拟物品
	 * @return
	 */
	public boolean isVritual() {
		if ("1".equals(this.isVirtual)) return true;
		return false;
	}
}

/*
 * @athor zhaolianjun
 * created on 2015-01-06
 */

package com.game.shop.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:活动关系 <br>
 * @author zhaolianjun
 */

public class RefProductPromotion extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// id
	private Integer promotionId;// 促销ID
	private Integer businessId;// type=0类别ID，type=1商品ID
	private Integer groupId;// 用户等级ID
	private Integer groupLevel;// 组级别
	private String typeFlag;// 类型(0分类，1商品)

	private String groupName;
	private String promotionName;
	private Double conditions;//折扣或减免金额
	private Double price;//商品单价
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPromotionId() {
		return this.promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public Integer getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getGroupLevel() {
		return this.groupLevel;
	}

	public void setGroupLevel(Integer groupLevel) {
		this.groupLevel = groupLevel;
	}

	public String getTypeFlag() {
		return this.typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public Double getConditions() {
		return conditions;
	}

	public void setConditons(Double conditions) {
		this.conditions = conditions;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}

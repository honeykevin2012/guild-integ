/*
 * @athor zhaolianjun
 * created on 2015-01-05
 */
 
package com.game.user.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:用户组 <br>
 * @author zhaolianjun
 */
 
public class UserVipGroup extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//ID
	private String name;//名称
	private Integer level;//级别
	private Integer point;//经验积分
	private Integer amount;//福利
	private String icon;//图标
	private String isDefault;//默认级别
	private Double discount;//折扣
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date crateTime;//创建时间

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
	public Integer getLevel(){
		return this.level;
	}
	public void setLevel(Integer level){
		this.level = level;
	}
	public Integer getAmount(){
		return this.amount;
	}
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	public String getIcon(){
		return this.icon;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
	public Date getCrateTime(){
		return this.crateTime;
	}
	public void setCrateTime(Date crateTime){
		this.crateTime = crateTime;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}

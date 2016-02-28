/*
 * @athor zhaolianjun
 * created on 2014-12-16
 */

package com.game.shop.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.BaseForm;

/**
 * @desc:商品基本信息 <br>
 * @author zhaolianjun
 */

public class ProductInfoForm extends BaseForm {
	
	private Integer id;//记录主键
	
	private String name;//商品名称
	
	private String goodsNo;//商品货号
	
	private Double price;//商品价格
	
	private Integer categoryId;//商品分类ID
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date upTime;//上架时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date downTime;//下架时间
	
	private Integer brand;//商品品牌
	
	private String weight;//重量
	
	private String unit;//计量单位
	
	private Integer visitTimes;//浏览次数
	
	private Integer sort;//排序
	
	private Integer commentTimes;//评论次数
	
	private Integer saleTimes;//销量
	
	private Integer grade;//评分数
	
	private String status;//商品状态
	
	private String type;//商品分类（1商城2公会商城）

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
	public String getGoodsNo(){
		return this.goodsNo;
	}
	public void setGoodsNo(String goodsNo){
		this.goodsNo = goodsNo;
	}
	public Double getPrice(){
		return this.price;
	}
	public void setPrice(Double price){
		this.price = price;
	}
	public Integer getCategoryId(){
		return this.categoryId;
	}
	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getUpTime(){
		return this.upTime;
	}
	public void setUpTime(Date upTime){
		this.upTime = upTime;
	}
	public Date getDownTime(){
		return this.downTime;
	}
	public void setDownTime(Date downTime){
		this.downTime = downTime;
	}
	public Integer getBrand(){
		return this.brand;
	}
	public void setBrand(Integer brand){
		this.brand = brand;
	}
	public String getWeight(){
		return this.weight;
	}
	public void setWeight(String weight){
		this.weight = weight;
	}
	public String getUnit(){
		return this.unit;
	}
	public void setUnit(String unit){
		this.unit = unit;
	}
	public Integer getVisitTimes(){
		return this.visitTimes;
	}
	public void setVisitTimes(Integer visitTimes){
		this.visitTimes = visitTimes;
	}
	public Integer getSort(){
		return this.sort;
	}
	public void setSort(Integer sort){
		this.sort = sort;
	}
	public Integer getCommentTimes(){
		return this.commentTimes;
	}
	public void setCommentTimes(Integer commentTimes){
		this.commentTimes = commentTimes;
	}
	public Integer getSaleTimes(){
		return this.saleTimes;
	}
	public void setSaleTimes(Integer saleTimes){
		this.saleTimes = saleTimes;
	}
	public Integer getGrade(){
		return this.grade;
	}
	public void setGrade(Integer grade){
		this.grade = grade;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
}

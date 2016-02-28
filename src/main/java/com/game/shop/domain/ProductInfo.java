/*
 * @athor zhaolianjun
 * created on 2014-12-16
 */
 
package com.game.shop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.basics.domain.BaseEntity;
import com.game.platform.domain.PlatformAttachment;
import com.game.platform.domain.PlatformGameApp;

/**
 * @desc:商品基本信息 <br>
 * @author zhaolianjun
 */
 
public class ProductInfo extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private Integer id;//记录主键
	private String name;//商品名称
	private String goodsNo;//商品货号
	private Long price;//商品价格
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
	private String productPhoto;//商品图片
	private String remark;
	private Integer count;//库存量
	private String saleStatus;
	private Integer version;//数据版本号
	private String isVirtual;//是否虚拟商品
	
	private List<String> imageList = new ArrayList<String>();
	private List<PlatformAttachment> images = new ArrayList<PlatformAttachment>();
	private List<PlatformGameApp> exclusive = new ArrayList<PlatformGameApp>();
	//query field
	private String channel;//图片所属频道  产品为1000
	private String size;//图片大小 根据具体情况选择尺寸
	private String isDefault;//是否默认图片 1是0否
	private String promotion;
	private Integer cartCount;
	private Date orderCreateTime;//订单创建时间
	private String exchangeUserName;//兑换用户
	private String acceptName;
	private String address;
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
	public Long getPrice(){
		return this.price;
	}
	public void setPrice(Long price){
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
	public String getProductPhoto() {
		return productPhoto;
	}
	public void setProductPhoto(String productPhoto) {
		this.productPhoto = productPhoto;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public Integer getCartCount() {
		return cartCount;
	}
	public void setCartCount(Integer cartCount) {
		this.cartCount = cartCount;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public String getExchangeUserName() {
		return exchangeUserName;
	}
	public void setExchangeUserName(String exchangeUserName) {
		this.exchangeUserName = exchangeUserName;
	}
	public String getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}
	public List<PlatformGameApp> getExclusive() {
		return exclusive;
	}
	public void setExclusive(List<PlatformGameApp> exclusive) {
		this.exclusive = exclusive;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getIsVirtual() {
		return isVirtual;
	}
	public void setIsVirtual(String isVirtual) {
		this.isVirtual = isVirtual;
	}
	public List<PlatformAttachment> getImages() {
		return images;
	}
	public void setImages(List<PlatformAttachment> images) {
		this.images = images;
	}
}

/*
 * @athor zhaolianjun
 * created on 2015-01-27
 */

package com.game.user.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:用户收货地址 <br>
 * @author zhaolianjun
 */

public class UserAddressInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// ID
	private Integer userId;// 用户ID
	private String acceptName;// 收货人姓名
	private String zip;// 邮编
	private Integer country;// 国
	private Integer province;// 省
	private Integer city;// 市
	private Integer area;// 区县
	private String address;// 地址
	private String tel;// 联系电话
	private String mobile;// 手机
	private String isDefault;// 是否默认
	
	private String provName;
	private String cityName;
	private String areaName;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAcceptName() {
		return this.acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getCountry() {
		return this.country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getProvince() {
		return this.province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return this.city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getArea() {
		return this.area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}

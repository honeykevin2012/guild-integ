/*
 * @athor zhaolianjun
 * created on 2015-03-10
 */
 
package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:字典 <br>
 * @author zhaolianjun
 */
 
public class PlatformDict extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private String code;//编码
	private String value;//值
	private String remark;//备注

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code = code;
	}
	public String getValue(){
		return this.value;
	}
	public void setValue(String value){
		this.value = value;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
}

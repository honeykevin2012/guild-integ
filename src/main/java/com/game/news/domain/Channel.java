/*
 * @athor yangchengwei
 * created on 2014-10-01
 */
 
package com.game.news.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:zstChannel <br>
 * @author yangchengwei
 */
 
public class Channel extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	@NotEmpty(message = "error#栏目名称为空.")	
	private String name;//name
	@NotEmpty(message = "error#栏目编码为空.")	
	private String code;//code
	@NotNull(message = "error#排序编号为空.")	
	private Integer sortId;//sort_id

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
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code = code;
	}
	public Integer getSortId(){
		return this.sortId;
	}
	public void setSortId(Integer sortId){
		this.sortId = sortId;
	}
}

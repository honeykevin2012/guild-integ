/*
 * @athor yangchengwei
 * created on 2015-08-04
 */
 
package com.game.app.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:用户消息表 <br>
 * @author yangchengwei
 */
 
public class AppLoadimg extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	
	private String title;//标题
	
	private String picPath;//图片路径
	
	private String picResolution;//图片分辨率
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建日期
	
	private Integer osType;//操作系统版本 (1 ios  2 android)
	
	private String isValid;//是否有效 0否 1是
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date effectTime;//effect_time
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date failureTime;//failure_time

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getPicPath(){
		return this.picPath;
	}
	public void setPicPath(String picPath){
		this.picPath = picPath;
	}
	public String getPicResolution(){
		return this.picResolution;
	}
	public void setPicResolution(String picResolution){
		this.picResolution = picResolution;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Integer getOsType() {
		return osType;
	}
	public void setOsType(Integer osType) {
		this.osType = osType;
	}
	public String getIsValid(){
		return this.isValid;
	}
	public void setIsValid(String isValid){
		this.isValid = isValid;
	}
	public Date getEffectTime(){
		return this.effectTime;
	}
	public void setEffectTime(Date effectTime){
		this.effectTime = effectTime;
	}
	public Date getFailureTime(){
		return this.failureTime;
	}
	public void setFailureTime(Date failureTime){
		this.failureTime = failureTime;
	}
}

/*
 * @athor zhaolianjun
 * created on 2015-02-02
 */
 
package com.game.platform.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:广告管理 <br>
 * @author zhaolianjun
 */
 
public class PlatformAdvert extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//ID
	private String title;//标题
	private String remark;//描述
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	private Integer sort;//排序
	private String pcPhoto;//广告图片地址
	private Integer channel;//所属频道
	private String actionType;//動作类型
	private String actionParam;//动作参数
	private String pcUrl;//网站url
	private String appPhoto;//app图片地址
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
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Integer getSort(){
		return this.sort;
	}
	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getChannel(){
		return this.channel;
	}
	public void setChannel(Integer channel){
		this.channel = channel;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionParam() {
		return actionParam;
	}
	public void setActionParam(String actionParam) {
		this.actionParam = actionParam;
	}

	public String getPcPhoto() {
		return pcPhoto;
	}
	public void setPcPhoto(String pcPhoto) {
		this.pcPhoto = pcPhoto;
	}
	public String getPcUrl() {
		return pcUrl;
	}
	public void setPcUrl(String pcUrl) {
		this.pcUrl = pcUrl;
	}
	public String getAppPhoto() {
		return appPhoto;
	}
	public void setAppPhoto(String appPhoto) {
		this.appPhoto = appPhoto;
	}
	
}

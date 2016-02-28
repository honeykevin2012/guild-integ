/*
 * @athor zhaolianjun
 * created on 2015-07-03
 */
 
package com.game.user.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:用户评论 <br>
 * @author zhaolianjun
 */
 
public class UserComment extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private Integer userId;//用户ID
	private String content;//评论内容
	private Integer upPoint;//支持数
	private Integer underPoint;//不支持
	private String channel;//评论类型 1:游戏评论
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//评论时间
	private String userName;
	private String headIcon;
	private String ip;//IP地址
	private String dataId;//数据ID

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getUserId(){
		return this.userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public Integer getUpPoint(){
		return this.upPoint;
	}
	public void setUpPoint(Integer upPoint){
		this.upPoint = upPoint;
	}
	public Integer getUnderPoint(){
		return this.underPoint;
	}
	public void setUnderPoint(Integer underPoint){
		this.underPoint = underPoint;
	}
	public String getChannel(){
		return this.channel;
	}
	public void setChannel(String channel){
		this.channel = channel;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public String getIp(){
		return this.ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDataId() {
		return dataId; 
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	
}

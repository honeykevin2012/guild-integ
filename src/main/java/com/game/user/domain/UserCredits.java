/*
 * @athor zhaolianjun
 * created on 2015-07-02
 */
 
package com.game.user.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:用户积分记录表 <br>
 * @author zhaolianjun
 */
 
public class UserCredits extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private Integer userId;//用户ID
	private String active;//积分动作类型
	private Integer credits;//积分
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//create_time
	private String shareUrl;//分享地址

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
	public String getActive(){
		return this.active;
	}
	public void setActive(String active){
		this.active = active;
	}
	public Integer getCredits(){
		return this.credits;
	}
	public void setCredits(Integer credits){
		this.credits = credits;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public String getShareUrl(){
		return this.shareUrl;
	}
	public void setShareUrl(String shareUrl){
		this.shareUrl = shareUrl;
	}
}

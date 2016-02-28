/*
 * @athor zhaolianjun
 * created on 2015-06-01
 */
 
package com.game.user.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:用户签到 <br>
 * @author zhaolianjun
 */
 
public class UserSign extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private Integer userId;//user_id
	private Integer signCount;//连续签到次数
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastSignTime;//最后签到时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;//最后登录时间
	private Integer point;//总积分
	
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
	public Integer getSignCount(){
		return this.signCount;
	}
	public void setSignCount(Integer signCount){
		this.signCount = signCount;
	}
	public Date getLastSignTime(){
		return this.lastSignTime;
	}
	public void setLastSignTime(Date lastSignTime){
		this.lastSignTime = lastSignTime;
	}
	public Date getLastLoginTime(){
		return this.lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getPoint(){
		return this.point;
	}
	public void setPoint(Integer point){
		this.point = point;
	}
}

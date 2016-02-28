/*
 * @athor yangchengwei
 * created on 2015-07-31
 */

package com.game.user.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:用户消息表 <br>
 * @author yangchengwei
 */

public class UserCenterNotice extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// id
	private Integer type;// type
	private Integer userId;// user_id
	private String content;// content

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public UserNoticeEnum getTypeEnum() {
		return UserNoticeEnum.getProvider(this.type);
	}
	public enum UserNoticeEnum {
		LEVELUP("级别晋升",1,"14ab05"),ORDER("订单消息",2,"f45151"),SYSTEM("系统消息",3,"23a0e8"),GUILD("公会公告",4,"cf29b9"),EXCHANGE("汇率变化",5,"000");
		
		private String name; 
		private int value; 
		private String color; 
		private UserNoticeEnum(String name,int value,String color) {
			this.value = value;
			this.color = color;
			this.name = name;
		}
		public int getValue() {
			return value;
		}
		public String getColor() {
			return color;
		}
		public String getName() {
			return name;
		}		
		public static UserNoticeEnum getProvider(int cpValue){
			UserNoticeEnum cps[] = UserNoticeEnum.values();
			for(UserNoticeEnum cp : cps){
				if(cp.value == cpValue) return cp;
			}
			return null;
		}
	}
}

/*
 * @athor yangchengwei
 * created on 2014-10-15
 */
 
package com.game.news.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:zstComment <br>
 * @author yangchengwei
 */
 
public class Comment extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Object id;//id
	
	private Object articleId;//article_id
	
	private Object userId;//user_id
	
	private String content;//content

	private String mobileNo;//mobile

	public Object getId(){
		return this.id;
	}
	public void setId(Object id){
		this.id = id;
	}
	public Object getArticleId(){
		return this.articleId;
	}
	public void setArticleId(Object articleId){
		this.articleId = articleId;
	}
	public Object getUserId(){
		return this.userId;
	}
	public void setUserId(Object userId){
		this.userId = userId;
	}
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
}

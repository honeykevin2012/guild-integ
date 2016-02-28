/*
 * @athor yangchengwei
 * created on 2015-05-25
 */
 
package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:游戏活动表 <br>
 * @author yangchengwei
 */
 
public class PlatformGameTopic extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	
	private Integer gameCode;//所属游戏编码
	
	private String title;//标题
	
	private String content;//内容
	
	private Integer isTop;//是否推荐
	
	private String gameName;//

	private String icon;//
	private String url;//
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getGameCode(){
		return this.gameCode;
	}
	public void setGameCode(Integer gameCode){
		this.gameCode = gameCode;
	}
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public Integer getIsTop(){
		return this.isTop;
	}
	public void setIsTop(Integer isTop){
		this.isTop = isTop;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

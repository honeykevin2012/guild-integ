/*
 * @athor yangchengwei
 * created on 2015-05-26
 */
 
package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:游戏首页背景图表 <br>
 * @author yangchengwei
 */
 
public class PlatformAppIndexImg extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	
	private Integer gameCode;//游戏编码
	
	private String imgUrl;//图片地址

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
	public String getImgUrl(){
		return this.imgUrl;
	}
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
}

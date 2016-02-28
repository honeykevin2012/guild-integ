/*
 * @athor zhaolianjun
 * created on 2015-07-03
 */
 
package com.game.platform.domain;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:汇率走势 <br>
 * @author zhaolianjun
 */
 
public class PlatformGameAppTrend extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private String gameId;//游戏编码
	private String serverId;//服务器ID
	private String one;//one
	private String two;//two
	private String three;//three
	private String four;//four
	private String five;//five
	private String six;//six
	private String seven;//seven

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getGameId(){
		return this.gameId;
	}
	public void setGameId(String gameId){
		this.gameId = gameId;
	}
	public String getServerId(){
		return this.serverId;
	}
	public void setServerId(String serverId){
		this.serverId = serverId;
	}
	public String getOne(){
		return this.one;
	}
	public void setOne(String one){
		this.one = one;
	}
	public String getTwo(){
		return this.two;
	}
	public void setTwo(String two){
		this.two = two;
	}
	public String getThree(){
		return this.three;
	}
	public void setThree(String three){
		this.three = three;
	}
	public String getFour(){
		return this.four;
	}
	public void setFour(String four){
		this.four = four;
	}
	public String getFive(){
		return this.five;
	}
	public void setFive(String five){
		this.five = five;
	}
	public String getSix(){
		return this.six;
	}
	public void setSix(String six){
		this.six = six;
	}
	public String getSeven(){
		return this.seven;
	}
	public void setSeven(String seven){
		this.seven = seven;
	}
	@Override
	public String toString() {
		return "PlatformGameAppTrend [id=" + id + ", gameId=" + gameId + ", serverId=" + serverId + ", one=" + one + ", two=" + two + ", three=" + three + ", four=" + four + ", five=" + five
				+ ", six=" + six + ", seven=" + seven + "]";
	}
}

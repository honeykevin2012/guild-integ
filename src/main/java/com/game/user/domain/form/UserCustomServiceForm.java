/*
 * @athor zhaolianjun
 * created on 2015-01-07
 */
 
package com.game.user.domain.form;

import com.game.common.BaseForm;

/**
 * @desc:玩家客服管理 <br>
 * @author zhaolianjun
 */
 
public class UserCustomServiceForm extends BaseForm{

	private Integer id;//id
	private String content;//问题内容
	private Integer userId;//用户ID
	private String userName;//用户名
	private String type;//问题类型(1账号安全、2游戏内、3消费问题、4意见与建议)
	private String email;//邮箱
	private String mobile;//手机
	private String userType;//用户类型(1玩家0管理员)
	
	private String title;//问题标题
	private String isReply;//是否已回复1是0否
	private Integer parentId;//父级编号
	private String gameName;//游戏名称
	private String gameServerId;//游戏服务器标识
	private String gameServerName;//游戏服务器名称
	private String gameRoleName;//游戏角色名
	
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
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public Integer getUserId(){
		return this.userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getMobile(){
		return this.mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	public String getIsReply(){
		return this.isReply;
	}
	public void setIsReply(String isReply){
		this.isReply = isReply;
	}
	public String getUserType(){
		return this.userType;
	}
	public void setUserType(String userType){
		this.userType = userType;
	}
	public Integer getParentId(){
		return this.parentId;
	}
	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}
	public String getGameName(){
		return this.gameName;
	}
	public void setGameName(String gameName){
		this.gameName = gameName;
	}
	public String getGameServerId(){
		return this.gameServerId;
	}
	public void setGameServerId(String gameServerId){
		this.gameServerId = gameServerId;
	}
	public String getGameServerName(){
		return this.gameServerName;
	}
	public void setGameServerName(String gameServerName){
		this.gameServerName = gameServerName;
	}
	public String getGameRoleName(){
		return this.gameRoleName;
	}
	public void setGameRoleName(String gameRoleName){
		this.gameRoleName = gameRoleName;
	}
}

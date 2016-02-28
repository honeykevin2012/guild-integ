/*
 * @athor zhaolianjun
 * created on 2015-01-30
 */
 
package com.game.platform.domain;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.game.common.Constants;
import com.game.common.basics.domain.BaseEntity;
import com.game.user.domain.UserComment;

/**
 * @desc:游戏管理 <br>
 * @author zhaolianjun
 */
 
public class PlatformGameApp extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;//id
	private Integer code;//code
	private String name;//name
	private String icon;//icon
	private String remark;//remark
	private String webUrl;
	private String sitePageUrl;
	private Integer commend;
	private Integer downloadTimes;
	private Integer type;
	private String packageSize;
	private Double exchangeDivide;
	private String screenshot;
	private String gameAmountQueryUrl;
	private String gameAmountDeductUrl;
	private String trend;
	private String expend;
	private String downloadUrl;
	private String downloadIosUrl;
	//
	private Integer joined;//公会入驻为1 否则 0
	private List<String> shotList = new ArrayList<String>();
	private List<UserComment> commentsList;
	private JSONArray comments;
	private JSONArray consumeTopList;
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getCode(){
		return this.code;
	}
	public void setCode(Integer code){
		this.code = code;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getIcon(){
		return this.icon;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
	public String getRemark(){
		return this.remark == null ? "" : remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getWebUrl() {
		return webUrl == null ? "" : webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getSitePageUrl() {
		return sitePageUrl;
	}
	public void setSitePageUrl(String sitePageUrl) {
		this.sitePageUrl = sitePageUrl;
	}
	public Integer getDownloadTimes() {
		return downloadTimes;
	}
	public void setDownloadTimes(Integer downloadTimes) {
		this.downloadTimes = downloadTimes;
	}
	public Integer getCommend() {
		return commend;
	}
	public void setCommend(Integer commend) {
		this.commend = commend;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	public Integer getJoined() {
		return joined;
	}
	public void setJoined(Integer joined) {
		this.joined = joined;
	}
	public Double getExchangeDivide() {
		return exchangeDivide;
	}
	public void setExchangeDivide(Double exchangeDivide) {
		this.exchangeDivide = exchangeDivide;
		
	}
	public String getGameAmountQueryUrl() {
		return gameAmountQueryUrl;
	}
	public void setGameAmountQueryUrl(String gameAmountQueryUrl) {
		this.gameAmountQueryUrl = gameAmountQueryUrl;
	}
	public String getGameAmountDeductUrl() {
		return gameAmountDeductUrl;
	}
	public void setGameAmountDeductUrl(String gameAmountDeductUrl) {
		this.gameAmountDeductUrl = gameAmountDeductUrl;
	}
	public String getScreenshot() {
		return screenshot;
	}
	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}
	public List<String> getShotList() {
		return shotList;
	}
	public void setShotList(List<String> shotList) {
		this.shotList = shotList;
	}


	public List<UserComment> getCommentsList() {
		return commentsList;
	}
	public void setCommentsList(List<UserComment> commentsList) {
		this.commentsList = commentsList;
	}
	public JSONArray getComments() {
		return comments;
	}
	public void setComments(JSONArray comments) {
		this.comments = comments;
	}
	public String getExchange() {
		if(this.getExchangeDivide() == null) return "";
		return "1:"+Math.round(1 / this.getExchangeDivide());
	}
	public String getTrend() {
		return trend;
	}
	public void setTrend(String trend) {
		this.trend = trend;
	}
	public String getExpend() {
		return expend;
	}
	public void setExpend(String expend) {
		this.expend = expend;
	}
	public JSONArray getConsumeTopList() {
		return consumeTopList;
	}
	public void setConsumeTopList(JSONArray consumeTopList) {
		this.consumeTopList = consumeTopList;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getDownloadIosUrl() {
		return downloadIosUrl;
	}
	public void setDownloadIosUrl(String downloadIosUrl) {
		this.downloadIosUrl = downloadIosUrl;
	}
	public String getDescUrl() {
		return Constants.WEB_URL +"/games/item/"+this.code;
	}

	
}

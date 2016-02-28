/*
 * @athor yangchengwei
 * created on 2014-09-30
 */
 
package com.game.news.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.game.common.basics.domain.BaseEntity;

/**
 * @desc:zstArticle <br>
 * @author yangchengwei
 */
 
public class Article extends BaseEntity{
	private static final long serialVersionUID = 1L;

	
	private Integer id;//id
	@NotEmpty(message = "error#标题为空.")	
	private String title;//标题
	@NotEmpty(message = "error#内容为空.")	
	private String content;//内容
	private String	intro;
	@NotNull(message = "error#是否原创为空.")	
	private Integer isOriginal;//是否原创
	
	private String source;//来源
	private String author;//作者
	@NotNull(message = "error#是否置顶为空.")
	private Integer isTop;//是否置顶
	@NotNull(message = "error#推荐为空.")
	private Integer priority;//推荐
	
	private String tags;//标签
	private Integer status;//文章状态
	@NotEmpty(message = "error#栏目为空.")	
	private String channelId;//栏目id
	
	private String channelName;//栏目名称
	
	private String pageUrl;//栏目名称
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;//修改时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date publishTime;//发布时间
	@NotNull(message = "error#是否允许评论为空.")
	private Integer forbidComments;//是否允许评论
	//@NotEmpty(message = "error#封面图片为空.")
	private String coverImg;//封面图片

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
	public Integer getIsOriginal(){
		return this.isOriginal;
	}
	public void setIsOriginal(Integer isOriginal){
		this.isOriginal = isOriginal;
	}
	public String getSource(){
		return this.source;
	}
	public void setSource(String source){
		this.source = source;
	}
	public String getAuthor(){
		return this.author;
	}
	public void setAuthor(String author){
		this.author = author;
	}
	public Integer getIsTop(){
		return this.isTop;
	}
	public void setIsTop(Integer isTop){
		this.isTop = isTop;
	}
	public Integer getPriority(){
		return this.priority;
	}
	public void setPriority(Integer priority){
		this.priority = priority;
	}
	public String getTags(){
		return this.tags;
	}
	public void setTags(String tags){
		this.tags = tags;
	}
	public Integer getStatus(){
		return this.status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public String getChannelId(){
		return this.channelId;
	}
	public void setChannelId(String channelId){
		this.channelId = channelId;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getModifyTime(){
		return this.modifyTime;
	}
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
	public Date getPublishTime(){
		return this.publishTime;
	}
	public void setPublishTime(Date publishTime){
		this.publishTime = publishTime;
	}
	public Integer getForbidComments(){
		return this.forbidComments;
	}
	public void setForbidComments(Integer forbidComments){
		this.forbidComments = forbidComments;
	}
	public String getCoverImg(){
		return this.coverImg;
	}
	public void setCoverImg(String coverImg){
		this.coverImg = coverImg;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
}

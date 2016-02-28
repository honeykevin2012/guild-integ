package com.game.current.envelope;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedEnvelope {
	private Integer id;
	private String desc;//红包描述
	private Integer totalQuantity;//总数
	private Integer remainQuantity;//剩余
	private Integer totalAmount;//总金额
	private Integer remainAmount;//剩余金额
	private Integer userId;
	private Integer guildId;
	private Date createTime;
	
	private Set<String> canNotObtainedUnique = new HashSet<String>();//已经抢了的用户， 不能再次抢
	private List<RedEnvelopeItem> obtained = new ArrayList<RedEnvelopeItem>();//可抢的
	private List<RedEnvelopeItem> notObtained = new ArrayList<RedEnvelopeItem>();//不可抢的
	private RedEnvelopeItem obtainedEnvelopeItem;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<RedEnvelopeItem> getObtained() {
		return obtained;
	}

	public void setObtained(List<RedEnvelopeItem> obtained) {
		this.obtained = obtained;
	}

	public List<RedEnvelopeItem> getNotObtained() {
		return notObtained;
	}

	public void setNotObtained(List<RedEnvelopeItem> notObtained) {
		this.notObtained = notObtained;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(Integer remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(Integer remainAmount) {
		this.remainAmount = remainAmount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGuildId() {
		return guildId;
	}

	public void setGuildId(Integer guildId) {
		this.guildId = guildId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<String> getCanNotObtainedUnique() {
		return canNotObtainedUnique;
	}

	public void setCanNotObtainedUnique(Set<String> canNotObtainedUnique) {
		this.canNotObtainedUnique = canNotObtainedUnique;
	}

	public RedEnvelopeItem getObtainedEnvelopeItem() {
		return obtainedEnvelopeItem;
	}

	public void setObtainedEnvelopeItem(RedEnvelopeItem obtainedEnvelopeItem) {
		this.obtainedEnvelopeItem = obtainedEnvelopeItem;
	}

	/**
	 * 判断当前用户是否可以抢红包
	 * @param userId
	 * @return
	 */
	public boolean canObtained(String userId){
		if(obtained.isEmpty()) return false;
		return !canNotObtainedUnique.contains(userId);
	}

	/**
	 * 抢红包
	 * @return
	 */
	public RedEnvelope startObtained(String userId){
		if(this.getObtained().isEmpty()) return null;
		RedEnvelopeItem obtainedItem = this.getObtained().get(0);
		this.getObtained().remove(obtainedItem);
		this.getNotObtained().add(obtainedItem);
		this.canNotObtainedUnique.add(userId);
		this.setObtainedEnvelopeItem(obtainedItem);
		return this;
	}
}

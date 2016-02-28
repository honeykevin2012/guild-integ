/*
 * @athor yangchengwei
 * created on 2015-03-03
 */

package com.game.guild.domain;

import java.util.Date;
import com.game.common.basics.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @desc:礼包子表 <br>
 * @author yangchengwei
 */

public class GuildItemVirtual extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer id;// id
	private Integer itemId;// 物品ID
	private Integer codeId;
	private String activeCode;
	private String cardNumber;
	private String status;// 状态(0表示已分配给公会，1表示已放入仓库，2表示已领取)
	private String userId;// 领取用户id
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date getItemTime;// 领取时间
	private Integer version;// 版本号
	private Integer limit;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public void entity(Integer itemId) {
		this.itemId = itemId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getGetItemTime() {
		return this.getItemTime;
	}

	public void setGetItemTime(Date getItemTime) {
		this.getItemTime = getItemTime;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
}

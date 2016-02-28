package com.game.user.domain.vo;

import java.util.ArrayList;
import java.util.List;

public class MainBodyCustomService {
	private Integer id;
	private String content;
	private String date;
	private List<ReplyBodyCustomService> replyList = new ArrayList<ReplyBodyCustomService>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<ReplyBodyCustomService> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<ReplyBodyCustomService> replyList) {
		this.replyList = replyList;
	}
}

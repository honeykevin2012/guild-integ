package com.game.user.domain.vo;

import java.util.ArrayList;
import java.util.List;

public class FinalCustomServiceResult {
	private Integer pn;
	private Integer totalPage;
	private List<MainBodyCustomService> askList = new ArrayList<MainBodyCustomService>();

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public List<MainBodyCustomService> getAskList() {
		return askList;
	}

	public void setAskList(List<MainBodyCustomService> askList) {
		this.askList = askList;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
}

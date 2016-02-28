// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2013/8/3 星期六 14:10:55
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Page.java

package com.game.common.basics.pagination;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.game.common.basics.web.WebRequest;
import com.game.common.utility.CommonUtils;

public class PageQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final List<String> ORDERS = Arrays.asList(new String[]{"asc", "desc" });
	private int pageNo = 1;
	private int pageSize = 15;
	private String orderBy;
	private String order;
	private boolean autoCount = true;
	private long totalCount = 0;
	private List<Object> result = Collections.emptyList();
	private Map<String, Object> params = new HashMap<String, Object>();
	private String searchKeys;
	private Map<String, Object> values = new HashMap<String, Object>();
	private int size;
	private boolean flag = true;
	private List<?> dataList = Collections.emptyList();
	
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PageQuery() {
		
	}
	
	public PageQuery(Object scope) {
		if (scope == null) return;
		if (scope instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) scope;
			
			String pageSize = request.getParameter(PageKeys.PAGINATION_CURR_PAGE_SIZE);
			if(pageSize != null && !"".equals(pageSize)){
				this.setPageSize(Integer.valueOf(pageSize));
			}
			
			String currPageNo = request.getParameter(PageKeys.PAGINATION_CURR_PAGE);
			currPageNo = currPageNo == null ? "1" : currPageNo;
			
			this.setPageNo(Integer.valueOf(currPageNo));
			String parameter = (String) request.getAttribute(PageKeys.PARAMETER_KEY);
			if(parameter != null){
				Map<String, Object> map = WebRequest.parseParameterMap(parameter);
				this.setParams(map);
			}
			this.setSearchKeys((String) request.getAttribute(PageKeys.PARAMETER_KEY));
			request.setAttribute(PageKeys.PAGINATION_KEY, this);
		}
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public PageQuery setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (this.pageNo < 1)
			this.pageNo = 1;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public PageQuery setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public PageQuery setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public String getOrder() {
		return order;
	}

	public PageQuery setOrder(String order) {
		String orders[] = StringUtils.split(StringUtils.lowerCase(order), ',');
		String as[];
		int j = (as = orders).length;
		for (int i = 0; i < j; i++) {
			String orderStr = as[i];
			if (!ORDERS.contains(orderStr.toLowerCase()))
				throw new IllegalArgumentException("page's order must be asc or desc");
		}

		this.order = StringUtils.lowerCase(order);
		return this;
	}

	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order);
	}

	public boolean isAutoCount() {
		return autoCount;
	}

	public PageQuery setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
		return this;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public PageQuery setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		return this;
	}

	public List<Object> getResult() {
		return result;
	}

	public PageQuery setResult(List<Object> result) {
		this.result = result;
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public long getTotalPages() {
		if (totalCount < 0L)
			return -1L;
		long count = totalCount / (long) pageSize;
		if (totalCount % (long) pageSize > 0L)
			count++;
		return count;
	}

	public int getFirst() {
		return (pageNo - 1) * pageSize + 1;
	}

	public boolean getIsHasNext() {
		return (long) (pageNo + 1) <= getTotalPages();
	}

	public int getNextPage() {
		if (getIsHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	public boolean getIsHasPre() {
		return pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (getIsHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
	public String getSearchKeys() {
		return searchKeys;
	}

	public void setSearchKeys(String searchKeys) {
		this.searchKeys = searchKeys;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("pn:").append(this.pageNo).append(":ps:").append(this.pageSize).append(":");
		if(!CommonUtils.isNullEmpty(this.orderBy)) builder.append("order:").append(this.orderBy).append(":");
		for(String key : this.params.keySet()){
			if(set.contains(key)) continue;
			builder.append(key).append(":").append(this.params.get(key)).append(":");
		}
		return builder.toString().replace(" ", "");
	}
	private static Set<String> set = new HashSet<String>();
	static{
		set.add("deviceId");
		set.add("gameId");
		set.add("osVersion");
		set.add("gameVersion");
		set.add("os");
		set.add("sdkVersion");
		set.add("screenResolution");
		set.add("timestamp");
		set.add("signature");
		set.add("data");
		set.add("token");
		set.add("nuid");
		set.add("ip");
		set.add("searchText");
	}
}
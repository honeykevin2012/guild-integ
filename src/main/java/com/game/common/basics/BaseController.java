package com.game.common.basics;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.game.common.basics.pagination.PageQuery;

public class BaseController {
    private static Logger logger = Logger.getLogger(BaseController.class.getName());

	@SuppressWarnings("unchecked")
	public PageQuery setPagination(HttpServletRequest request, PageQuery page){
		Map<String, Object> map = new HashMap<String, Object>();
		if(page == null) page = new PageQuery();
		Enumeration<String> enums = request.getParameterNames();
		while(enums.hasMoreElements()){
		    String name=(String)enums.nextElement();
		    String value = request.getParameter(name);
		    if(value != null && !"".equals(value.trim())){
		    	if("page".equals(name)) page.setPageNo(Integer.valueOf(value));
		    	else if("rows".equals(name)) page.setPageSize(Integer.valueOf(value));
		    	else map.put(name, value);
		    }
		}
		page.setParams(map);
		return page;
	}
	
	
	
	protected Error buildFormError(String errorMsg) {
		logger.info("errorMsg = "+errorMsg);
		Error error = new Error();
		String[] formMsg = errorMsg.split("#");
		if(formMsg.length == 2){
			error.setState(formMsg[0]);
			error.setMsg(formMsg[1]);
		}else{
			error.setState("error");
			error.setMsg(errorMsg);
		}
		return error;
	}
	
	protected String getStackTrace(Exception ex){
		StackTraceElement[] ele = ex.getStackTrace();
		StringBuilder build = new StringBuilder();
		build.append("####").append("\r\n");
		build.append(ex.getMessage());
		for(StackTraceElement el : ele){
			build.append(el.toString()).append("\r\n");
		}
		build.append("####\r\n");
		return build.toString();
	}
	
	private String message;
	private List<?> dataList;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	
	public void logger(String log){
	    logger.info(log);
	}
}

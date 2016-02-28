package com.game.common.utility;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public class DebugUtils {
	public static String debug(HttpServletRequest request){
		Enumeration<String> names = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			String value = request.getParameter(name);
			sb.append(name).append("=").append(value).append("|");
		}
		return sb.toString();
	}
	public static String append(HttpServletRequest request){
		Enumeration<String> names = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			String value = request.getParameter(name);
			sb.append(name).append("=").append(value).append("&");
		}
		return sb.toString();
	}
}

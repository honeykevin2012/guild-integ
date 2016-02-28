package com.game.common.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.game.common.OrderTypeEnum;

public class OrderNoUtils {
	
	private static final SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static String getNo(OrderTypeEnum type){
		StringBuilder sb=new StringBuilder();
		sb.append(type.getPrefix());
		sb.append(formatter.format(new Date()));
		return sb.toString();
	}

}

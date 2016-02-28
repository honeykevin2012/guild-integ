package com.game.common.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String format(Date date,String format){
		if(null == date) throw new java.lang.NullPointerException();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	public static Date format(String date,String format){
		if(null == date) throw new java.lang.NullPointerException();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String format(long time, String format){
		if(time == 0) throw new java.lang.NullPointerException();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

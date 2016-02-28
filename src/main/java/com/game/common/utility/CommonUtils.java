package com.game.common.utility;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;

import com.game.common.Constants;

import sun.misc.BASE64Encoder;

public class CommonUtils {
	/**
	 * 中文URL DECODE处理
	 * 
	 * @param strURLENCODE字符串
	 * @return DECODE后字符串
	 */
	public static String decodeForAndroid(String value) {
		String returnValue = "";
		try {
			returnValue = URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * MD5加密类
	 * 
	 * @param str
	 *            加密的字符串
	 * @return 加密后的字符串
	 */
	public static String toMD5Base64Encoder(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md.digest(str.getBytes("UTF-8")));

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * MD5加密类
	 * 
	 * @param str
	 *            加密的字符串
	 * @return 加密后的字符串
	 */
	public static String toMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isNumber(String content) {
		if (null == content || content.trim().length() == 0)
			return false;
		content = content.trim();
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (c > '9' || c < '0') {
				return false;
			}
		}
		return true;
	}

	public static String getStrBylength(long value, int len) {
		StringBuffer buf = new StringBuffer();
		if (String.valueOf(get36(value)).length() < len) {
			for (int i = 0; i < len - String.valueOf(get36(value)).length(); i++) {
				buf.append("0");
			}
		}
		buf.append(get36(value));
		return buf.toString();
	}

	private static String get36(long value) {
		if (0 > value)
			return null;
		if (value >= 36) {
			long result = value / 36;
			int remainer = (int) (value % 36);
			return get36(result) + THIRTY_SIX_SYSTEM_CHARS.charAt(remainer);
		} else {
			return THIRTY_SIX_SYSTEM_CHARS.charAt((int) value) + "";
		}
	}

	public static String generateStr(int n) {
		if (0 >= n)
			throw new java.lang.IllegalArgumentException();
		char[] ascii = new char[n];
		Random random = new Random();
		String result = null;
		for (int i = 0; i < n; i++) {
			int asc = 47;
			asc = random.nextInt(10) + 48;
			ascii[i] = (char) asc;
		}
		result = new String(ascii);
		return result;
	}

	public static String passwordSecurity(String pwd) {
		if (pwd == null || pwd.length() < 6)
			return "0";
		String low = "[0-9]*"; // 不超过16位的数字组合
		String mid = "[a-zA-Z]*"; // 由字母不超过16位
		String high = "[0-9|a-z|A-Z]*"; // 由字母、数字组成，不超过16位
		if (pwd.matches(low)) {
			return "0";
		}
		if (pwd.matches(mid)) {
			return "1";
		}
		if (pwd.matches(high)) {
			return "2";
		}
		return "";
	}

	public static void copyProperties(Object src, Object dest) {
		try {
			PropertyUtils.copyProperties(dest, src);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static String THIRTY_SIX_SYSTEM_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode)))
				return encode;
		} catch (Exception exception) {
			return "UTF-8";
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode)))
				return encode;
		} catch (Exception exception1) {
			return "UTF-8";
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode)))
				return encode;
		} catch (Exception exception2) {
			return "UTF-8";
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode)))
				return encode;
		} catch (Exception exception3) {
			return "UTF-8";
		}
		return "UTF-8";
	}

	public static String encodeContent(String str) {
		if (str == null)
			return "";
		String encode = CommonUtils.getEncoding(str);
		String content = null;
		try {
			content = new String(new String(str.getBytes(encode),
					"ISO-8859-1".equals(encode) ? "UTF-8" : encode));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}

	public static boolean match(String filter, String param) {
		Pattern p = Pattern.compile(filter);
		Matcher m = p.matcher(param);
		return m.matches();
	}

	public static boolean isNullEmpty(String s) {
		if (s == null || "".equals(s.trim()))
			return true;
		return false;
	}
	
	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static String getExtendName(String fileName) {
		if (fileName == null || "".equals(fileName.trim()))
			return null;
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return prefix;
	}

	/**
	 * 获取客户端ip
	 * 
	 * @param request
	 * @return
	 */
	public static String realIPAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (null != ip && ip.indexOf(',') != -1) {
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				if (null != ips[i] && !"unknown".equalsIgnoreCase(ips[i])) {
					ip = ips[i];
					break;
				}
			}
		}
		return ip.trim();
	}

	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobile(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static String isNull(String str) {
		return str == null ? "" : str;
	}
	/**
	 * double类型计算
	 * @param a
	 * @param b
	 * @return
	 */
	public static double multiply(double a, double b) {
		BigDecimal decp = new BigDecimal(Double.toString(a));
		BigDecimal decc = new BigDecimal(Double.toString(b));
		BigDecimal total = decp.multiply(decc);
		return total.doubleValue();
	}
	/**
	 * double类型计算
	 * @param a
	 * @param b
	 * @return
	 */
	public static double multiply(double a, int b) {
		BigDecimal decp = new BigDecimal(Double.toString(a));
		BigDecimal decc = new BigDecimal(Double.toString(b));
		BigDecimal total = decp.multiply(decc);
		return total.doubleValue();
	}
	
	public static Integer[] convertArray(String s){
		if(s == null) return null;
		String[] ss = s.split(",");
		Integer[] intArray = new Integer[ss.length];
		for(int i = 0; i < ss.length; i++){
			Integer ele = Integer.parseInt(ss[i]);
			intArray[i] = ele;
		}
		return intArray;
	}
	
	/**
	 * 生成随机中文
	 * @return
	 * @throws Exception
	 */
	public static String create() {
		String str = null;
		int hightPos, lowPos; // 定义高低位
		Random random = new Random();
		hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
		lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
		byte[] b = new byte[2];
		b[0] = (new Integer(hightPos).byteValue());
		b[1] = (new Integer(lowPos).byteValue());
		try {
			str = new String(b, "GBK");// 转成中文
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String getSystem(String from) {
		if (Constants.ANDROID.equals(from.toLowerCase()))
			return Constants.ANDROID_DB_FLAG;
		else if (Constants.IOS.equals(from.toLowerCase()))
			return Constants.IOS_DB_FLAG;
		else
			return Constants.PC_DB_FLAG;
	}
}

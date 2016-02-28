package com.game.common.basics.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.game.common.basics.pagination.PageKeys;
import com.game.common.basics.security.ByteUtils;
import com.game.common.basics.security.Enumerator;
import com.game.common.basics.security.SecurityUtils;

public class WebRequest extends HttpServletRequestWrapper {

	private String encoding = null;
	private HashMap<String, String[]> parameters = null;
	private HashMap<String, String[]> searchKeys = new HashMap<String, String[]>();
	private boolean parseFlag = false;

	public WebRequest(HttpServletRequest request) {
		super(request);
		decodeParameters();
		setRequest(request);
	}

	public String getParameter(String name) {
		parseParameter();
		Object value = parameters.get(name);
		if (value == null)
			return null;
		if (value instanceof String[])
			return ((String[]) value)[0];
		if (value instanceof String)
			return (String) value;
		else
			return value.toString();
	}

	public Map<String, String[]> getParameterMap() {
		parseParameter();
		return parameters;
	}

	public Enumeration<?> getParameterNames() {
		parseParameter();
		return new Enumerator(parameters.keySet());
	}

	public String[] getParameterValues(String name) {
		parseParameter();
		Object value = parameters.get(name);
        if (value == null)
			return null;
		if (value instanceof String[])
			return (String[]) value;
		if (value instanceof String) {
			return new String[]{(String) value};
		} else {
			return new String[]{value.toString()};
		}
	}

	public Object getAttribute(String name) {
		if (PageKeys.PARAMETER_KEY.equals(name))
			return encodeParameters();
		else
			return getRequest().getAttribute(name);
	}

	@SuppressWarnings("unchecked")
	private void parseParameter() {
		if (parseFlag) return;
		HashMap<String, String[]> temp = copyMap(searchKeys);
		temp.putAll(getRequest().getParameterMap());
		temp.remove(PageKeys.PARAMETER_KEY);
		this.parameters = temp;
		parseFlag = true;
	}

	public void setRequest(ServletRequest request) {
		super.setRequest(request);
		parseFlag = false;
	}

    private static void putMapEntry(HashMap<String, String[]> map, String name, String value) {
		String newValues[] = null;
		String oldValues[] = map.get(name);
		if (oldValues == null) {
			newValues = new String[1];
			newValues[0] = value;
		} else {
			newValues = new String[oldValues.length + 1];
			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			newValues[oldValues.length] = value;
		}
		map.put(name, newValues);
	}

    private HashMap<String, String[]> copyMap(Map<String, String[]> orig) {
		if (orig == null)
			return new HashMap<String, String[]>();
		HashMap<String, String[]> dest = new HashMap<String, String[]>();
		String key;
		for (Iterator<String> keys = orig.keySet().iterator(); keys.hasNext(); dest.put(key, orig.get(key)))
			key = keys.next();
		return dest;
	}

	private void decodeParameters() {
		String parameter = getRequest().getParameter(PageKeys.PARAMETER_KEY);
		if (parameter != null && !"".equals(parameter)) {
			parameter = parameter.replace(' ', '+');//edit by kevin at 2009-12-12
			parameter = parameter.replaceAll("!", "+");
			parameter = this.argumentFilter(parameter);//过滤加密后重复的参数 add at 2010-12-27
			String[] paramerers = parameter.split("-");
			for (int i = 0; i < paramerers.length; i++) {
				if (paramerers[i].length() == 0) continue;
				String[] temp = ByteUtils.toString(SecurityUtils.base64Decode(paramerers[i]), getCharacterEncoding()).split("=", 2);
				putMapEntry(searchKeys, temp[0], temp[1]);
			}
		}
	}

	private String encodeParameters() {
		if (encoding != null) return encoding;
		StringBuffer buffer = new StringBuffer();
		Enumeration<?> names = getParameterNames();
		Set<String> set = new HashSet<String>();
		while(names.hasMoreElements()) {
			String name = (String) names.nextElement();
			name = name.replace("?", "").replace("/", "");
			if(set.contains(name)) continue;
			set.add(name);
			String[] values = getParameterValues(name);
			if (PageKeys.PAGINATION_CURR_PAGE.equals(name)||values == null) continue;
			for(int i=0; i<values.length; i++) {
				byte[] temp = ByteUtils.toBytes(name + "=" + values[i], getCharacterEncoding());
				buffer.append("-").append(SecurityUtils.base64Encode(temp));
			}
		}
		encoding = buffer.toString().replaceAll("\\+", "!");
		//过滤加密后重复的参数 add at 2010-12-27
		if(encoding != null && !"".equals(encoding)) encoding = this.argumentFilter(encoding);
		set = null;
		return encoding;
	}
	/**
	 * 过滤加密后重复的参数
	 * @param para
	 * @date 2010-12-27
	 * @return
	 */
	public String argumentFilter(String para){
		StringBuffer buff = null;
		String args = null;
		if(para != null && !"".equals(para)){
			String[] arrs = splitStringToArray(para, "-");
			Object[] objectArrays = filterArrayRepeatElement(arrs);
			String[] stringArrays = new String[objectArrays.length];
			for (int i = 0; i < objectArrays.length; i++) {
				if(objectArrays[i] == null) continue;
				stringArrays[i] = objectArrays[i].toString();
			}
			buff = arrayToString(stringArrays, "-");
			args = "-" + buff.toString();
		}
		return args;
	}
	
	public static String[] splitStringToArray(String str, String delimitor) {
		if (str == null || str.length() == 0 || delimitor == null || delimitor.length() == 0)
			return null;
		return str.split(delimitor);
	}
	
	public static Object[] filterArrayRepeatElement(String[] arrs) {
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		for (int i = 0; i < arrs.length; ++i) {
			hashTable.put(arrs[i], arrs[i]);
		}
		Object[] results = null;
		results = hashTable.values().toArray();
		return results;
	}
	
	public static StringBuffer arrayToString(String[] aryStr, String delimitor) {
		if (aryStr == null)
			return (null);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < aryStr.length; ++i) {
			if (delimitor != null && i > 0)
				sb.append(delimitor);
			sb.append(aryStr[i]);
		}
		return (sb);
	}
	
	public static Map<String, Object> parseParameterMap(String parameter){
		if(parameter == null) return new HashMap<String, Object>();
		 Map<String, Object> map = new HashMap<String, Object>(); 
		String[] parameters = parameter.split("-");
		for (int i = 0; i < parameters.length; i++) {
			String[] splitParameters = ByteUtils.toString(SecurityUtils.base64Decode(parameters[i]), "UTF-8").split("=", 2);
			for (int k = 0; k < splitParameters.length; k++) {
				if (splitParameters.length == 0 || "".equals(splitParameters[k])) continue;
				map.put(splitParameters[0], splitParameters[1]);
			}
		}
		return map;
	}
	
	public static void main(String[] args){
		StringBuffer buffer = new StringBuffer();
		byte[] temp = ByteUtils.toBytes("brand" + "=" + "奥迪 ", "UTF-8");
		byte[] temp1 = ByteUtils.toBytes("serial" + "=" + "宝马", "UTF-8");
		buffer.append("-").append(SecurityUtils.base64Encode(temp));
		buffer.append("-").append(SecurityUtils.base64Encode(temp1));
		System.out.println("编码后 = "+buffer.toString());
		
		String parameter = "-bmFtZT3kuZDop4bnvZE=-c3VibWl0U2VhcmNoPeafpSDor6I=-".replaceAll("!", "+");
		String[] paramerers = parameter.split("-");
		for (int i = 0; i < paramerers.length; i++) {
			String[] tempDe = ByteUtils.toString(SecurityUtils.base64Decode(paramerers[i]), "UTF-8").split("=", 2);
			for (int k = 0; k < tempDe.length; k++) {
				if (tempDe.length == 0 || "".equals(tempDe[k])){
					System.out.println(tempDe.length);
					continue;
				}
				System.out.println("解码后 = " + tempDe[k]);
			}
		}
//		StringBuffer buffer = new StringBuffer();
//		String p = "argkeys=&pagekeys=1";
//		byte[] temp = ByteUtils.toBytes(p, "UTF-8");
//		buffer.append("-").append(SecurityUtils.base64Encode(temp));
//		System.out.println("编码后 = "+buffer.toString());
//		
//		String parameter = buffer.toString().replaceAll("!", "+");
//		String[] paramerers = parameter.split("-");
//		for (int i = 0; i < paramerers.length; i++) {
//			byte[] codeBytes = SecurityUtils.base64Decode(paramerers[i]);
//			String code = ByteUtils.toString(codeBytes, "UTF-8");
//			String[] temps = code.split("=", 2);
//			for(int k=0; k<temps.length; k++){
//				if(temps.length == 0 || temps[i].equals(""))continue;
//				System.out.println("解码后 = "+temps[k]);
//			}
//		}
//		String s = "-cHJpY2V0eXBlPTE=-b3JpZ2luPTA=-b3JpZ2luPTA=-cHJpY2V0eXBlPTE=-Y2FydHlwZT0w-Y2FydHlwZT0w-RGVsaXZlcnlBcGFjaXR5PTA=-RGVsaXZlcnlBcGFjaXR5PTA=-U3BlZWRCb3g9MA==-U3BlZWRCb3g9MA==";
//		String[] arrs = StringUtil.splitStringToArray(s, "-");
//		Object[] t = StringUtil.filterArrayRepeatElement(arrs);
//		String[] x = new String[t.length];
//		for(int i=0;i<t.length;i++){
//			x[i]= t[i].toString();
//		}
//		StringBuffer temp = StringUtil.arrayToString(x, "-");
//		System.out.println(temp.toString());
//		Set set = new HashSet();
//		set.add("kevin");
//		System.out.println(set.contains("kevin1"));
	}
}

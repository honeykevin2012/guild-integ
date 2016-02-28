package com.game.user.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.game.common.basics.ApplicationContextLoader;
import com.game.common.basics.security.Base64Utils;
import com.game.common.basics.security.DESUtils;
import com.game.common.basics.security.RSAUtils;
import com.game.common.utility.CommonUtils;
import com.game.guild.GuildMemberController;
import com.game.user.domain.UserSessionToken;
import com.game.user.persistence.dao.UserSessionTokenDao;

public class SessionTokenUtils {
	private static final Logger logger = Logger.getLogger(GuildMemberController.class);
	private static UserSessionTokenDao dao = (UserSessionTokenDao) ApplicationContextLoader.getBean("userSessionTokenDao");
	/**
	 * 用户登录时，建立session数据
	 * @param uid 用户id
	 * @param os 客户端os
	 * @param ck 客户端传递的3des密钥
	 * @return
	 */
	public static UserSessionToken createSession(String nuid, String os, String ck, String tk){
		if(CommonUtils.isNullEmpty(ck)) return null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", nuid);
		map.put("os", os);
		UserSessionToken sessionToken = dao.selectByUserIdAndOs(map);
		if(sessionToken == null) {
			sessionToken = new UserSessionToken();
			sessionToken.setUserId(Integer.valueOf(nuid));
			sessionToken.setCk(ck);//需要解析token
			sessionToken.setOs(os);
			sessionToken.setTk(tk);
			dao.insert(sessionToken);
		}else{
			//if(!ck.equals(sessionToken.getCk())){
			sessionToken.setCk(ck);
			sessionToken.setTk(tk);
			dao.update(sessionToken);
			//}
		}
		return sessionToken;
	}
	
	public static boolean validateLogin(String nuid, String os, String tk){
		if(tk == null || "".equals(tk)){
			logger.info("用户请求参数缺少tk...");
			return false;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", nuid);
		map.put("os", os);
		UserSessionToken sessionToken = dao.selectByUserIdAndOs(map);
		if(sessionToken == null || !sessionToken.getTk().equals(tk)) return false;
		return true;
	}
	
	public static boolean validateLogin(Map<String, Object> data, Integer nuid, String os){
		if(nuid == null) return false;
		String tk = data.containsKey("tk") ? data.get("tk").toString() : null;//获取tk值
		boolean tkLegal = SessionTokenUtils.validateLogin(nuid.toString(), os, tk);
		if(!tkLegal) return false;
		return true;
	}
	
	/**
	 * 获取tk中的时间戳信息（用来计算是否tk是否过期）
	 * @param tk
	 * @return
	 */
	public long tkAnalsy(String tk){
		try{
			String detk = DESUtils.decode(tk);
			String time = detk.substring(8, detk.length());
			return Long.valueOf(time);
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * 解析请求数据RSA（token），转换为map
	 * @param token
	 * @return
	 */
	public static Map<String, Object> dataAnalsy(String data){
		if(data == null || "".equals(data)) return null;
		Map<String, Object> map = dataBuilder(data);
		return map;
	}
	
	public static String getSessionCK(String data){
		if(data == null || "".equals(data)) return null;
		Map<String, Object> map = dataAnalsy(data);
		String ck = map.containsKey("ck") ? (String)map.get("ck") : "";
		return ck;
	}
	
	public static String getSessionCK(Map<String, Object> map){
		if(map == null) return null;
		String ck = map.containsKey("ck") ? (String)map.get("ck") : "";
		return ck;
	}
	
	public static UserSessionToken getSession(String token){
		if(token == null || "".equals(token)) return null;
		UserSessionToken session = new UserSessionToken();
		Map<String, Object> map = dataAnalsy(token);
		String ck = map.containsKey("ck") ? (String)map.get("ck") : "";
		String tk = map.containsKey("tk") ? (String)map.get("tk") : "";
		session.setCk(ck);
		session.setTk(tk);
		return session;
	}
	
	/**
	 * 创建token字符串
	 * @param uid
	 * @param registerTime
	 * @return
	 */
	public static String createToken(String uid, long registerTime){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0, 8);
		StringBuilder builder = new StringBuilder();
		long timestamp = System.currentTimeMillis();
		builder.append(uuid).append("-");
		builder.append(timestamp).append("-");
		builder.append(uid).append("-");
		builder.append(registerTime);
		try {
			return DESUtils.encode(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析请求数据RSA（token），转换为map
	 * @param token
	 * @return
	 */
	public static Map<String, Object> dataBuilder(String token){
		try {
			if(token == null) return null;
			token = token.replaceAll(" ", "+");
			byte[] tkBytes = Base64Utils.decode(token);//原始token是base64转换成byte
			byte[] tkRsaBytes = RSAUtils.decryptByPrivateKey(tkBytes, RSAUtils.privateKey);//RSA解密成byte
	        String data = new String(tkRsaBytes,"UTF-8");//解密RSA 还原报文为明文
			Map<String, Object> map = new HashMap<String, Object>();
			String[] parameters = data.split("&");
			for(String parameter : parameters){
				String[] kv = parameter.split("=");
				if(kv != null && kv.length == 2){
					map.put(kv[0], kv[1]);
				}else{
					map.put(kv[0], "");
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

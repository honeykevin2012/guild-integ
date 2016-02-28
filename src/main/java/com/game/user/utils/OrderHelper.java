package com.game.user.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.game.common.MessageConsValue;
import com.game.common.basics.security.DES3Utils;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;

public class OrderHelper {
	private static final Logger logger = Logger.getLogger(OrderHelper.class);
	public static final String ERROR = "error";
	public static final String BUY_ERROR_MSG = "[公会商品]与[平台商品]不能同时购买, 请返回购物车重新选择";
	public static String createOrderNumber() {
		String date = DateUtils.format(new Date(), "yyyyMMddHHmmSS");
		String uuid = UUID.randomUUID().toString();
		String uuidPrex = uuid.substring(0, uuid.indexOf('-'));
		String result = date + uuidPrex.toUpperCase();
		return result;
	}
	/**
	 * 解析token
	 * @param token
	 * @param MAX_TIME
	 * @return
	 */
	public static Map<String, Object> dataAnalsy(String token, Integer nuid, String os){
		List<Integer> ids = new ArrayList<Integer>();
		Map<String, Object> result = new HashMap<String, Object>(); 
		try{
			Map<String, Object> data = SessionTokenUtils.dataAnalsy(token);//解析token中的请求参数，转化成key value形式
		
			boolean legal = SessionTokenUtils.validateLogin(data, nuid, os);
			if(!legal || !data.containsKey("userId") || !data.containsKey("addressId") || !data.containsKey("productIds")|| !data.containsKey("gameDeducts") || !data.containsKey("walletAmount")){
				logger.info(MessageConsValue.legalMessage);
				result.put(ERROR, MessageConsValue.legalMessage);
				return result;
			}
			
			Integer userId = Integer.valueOf(data.get("userId").toString());
			Integer addressId = Integer.valueOf(data.get("addressId").toString());
			String productIds = data.get("productIds").toString();
			String gameDeducts = data.get("gameDeducts").toString();
			Long walletAmount = Double.valueOf(data.get("walletAmount").toString()).longValue();
			if(!CommonUtils.isNumber(userId + "") || !CommonUtils.isNumber(addressId + "") || CommonUtils.isNullEmpty(productIds)){
				logger.info(MessageConsValue.legalMessage);
				result.put(ERROR, MessageConsValue.legalMessage);
				return result;
			}
			//解析加密token 时间戳-用户id-地址id-商品编号1,商品编号2,...商品编号n
			result.put("userId", userId);
			result.put("addressId", addressId);
			result.put("gameDeducts", gameDeducts);
			result.put("walletAmount", walletAmount);
			String[] pids = productIds.split(",");
			if(pids == null || pids.length == 0) {
				ids.add(-1);
			}else{
				for (String pid : pids) {
					ids.add(Integer.valueOf(pid));
				}
			}
			result.put("ids", ids);
		}catch(Exception e){
			ids.add(-1);
			result.clear();
			result.put(ERROR, MessageConsValue.serverErrorMessage);
			return result;
		}
		return result;
	}
	
	/**
	 * 解析token
	 * @param token
	 * @param MAX_TIME
	 * @return
	 */
	public static Map<String, Object> dataAnalsySingleton(String token, Integer nuid, String os){
		List<Integer> ids = new ArrayList<Integer>();
		Map<String, Object> result = new HashMap<String, Object>(); 
		try{
			Map<String, Object> data = SessionTokenUtils.dataAnalsy(token);//解析token中的请求参数，转化成key value形式
		
			boolean legal = SessionTokenUtils.validateLogin(data, nuid, os);
			if(!legal || !data.containsKey("userId") || !data.containsKey("count") || (!data.containsKey("gameDeducts") && !data.containsKey("walletAmount"))){
				logger.info(MessageConsValue.legalMessage);
				result.put(ERROR, MessageConsValue.legalMessage);
				return result;
			}
			
			Integer userId = Integer.valueOf(data.get("userId").toString());
			Integer addressId = 0;
			if(data.get("addressId") != null && !"".equals(data.get("addressId").toString())){
				addressId = Integer.valueOf(data.get("addressId").toString());
			}
			String productId = data.get("productId").toString();
			String gameDeducts = data.get("gameDeducts") == null ? null : data.get("gameDeducts").toString();
			Integer count = Integer.valueOf(data.get("count").toString());
			Long walletAmount = Double.valueOf(data.get("walletAmount") == null ? "0" : data.get("walletAmount").toString()).longValue();
			
			if(!CommonUtils.isNumber(userId + "") || CommonUtils.isNullEmpty(productId)){
				logger.info(MessageConsValue.legalMessage);
				result.put(ERROR, MessageConsValue.legalMessage);
				return result;
			}
			//解析加密token 时间戳-用户id-地址id-商品编号1,商品编号2,...商品编号n
			result.put("userId", userId);
			result.put("addressId", addressId);
			result.put("gameDeducts", gameDeducts);
			result.put("walletAmount", walletAmount);
			result.put("productId", productId);
			result.put("count", count);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			ids.add(-1);
			result.clear();
			result.put(ERROR, MessageConsValue.serverErrorMessage);
			return result;
		}
		return result;
	}
	/**
	 * 解析token
	 * @param token
	 * @param MAX_TIME
	 * @return
	 */
	public static Map<String, Object> parseToken(String token, long MAX_TIME){
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<Integer> ids = new ArrayList<Integer>();
		try{
			String decryptToken = DES3Utils.decryptThreeDESECB(token);//解析加密token 时间戳-用户id-地址id-商品编号1,商品编号2,...商品编号n
			String[] decrypts = decryptToken.split("-");
			if(decrypts != null && decrypts.length == 4){//正常请求
				//若非数字则是非法请求
				if(!CommonUtils.isNumber(decrypts[0]) || !CommonUtils.isNumber(decrypts[1]) || !CommonUtils.isNumber(decrypts[2])){
					result.put(ERROR, "非法请求.");
					return result;
				}
				long timestamp = Long.valueOf(decrypts[0]);
				if(Math.abs(System.currentTimeMillis() - timestamp) > MAX_TIME) {
					result.put(ERROR, "请求超时.");
					return result;
				}
				result.put("userId", decrypts[1]);
				result.put("addressId", decrypts[2]);
				if (decrypts[3] != null && StringUtils.contains(decrypts[3], ",")) {
					String[] pids = StringUtils.split(decrypts[3], ",");
					for (String pid : pids) {
						ids.add(Integer.valueOf(pid));
					}
				} else {
					if(CommonUtils.isNumber(decrypts[3])){
						ids.add(Integer.valueOf(decrypts[3]));
					}else{
						ids.add(-1);
					}
				}
				result.put("ids", ids);
			}else{
				result.put(ERROR, "非法请求.");
				return result;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			ids.add(-1);
			result.clear();
			result.put(ERROR, MessageConsValue.serverErrorMessage);
			return result;
		}
		return result;
	}
	
	/**
	 * 解析token
	 * @param token
	 * @param MAX_TIME
	 * @return
	 */
	public static Map<String, Object> guildDataAnalsy(String token, Integer nuid, String os){
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<Integer> ids = new ArrayList<Integer>();
		
		Map<String, Object> data = SessionTokenUtils.dataAnalsy(token);//解析token中的请求参数，转化成key value形式
		boolean legal = SessionTokenUtils.validateLogin(data, nuid, os);
		if(!legal || !data.containsKey("userId") || !data.containsKey("addressId") || !data.containsKey("productIds") || !data.containsKey("guildId")){
			logger.info(MessageConsValue.legalMessage);
			result.put(ERROR, MessageConsValue.legalMessage);
			return result;
		}
		
		Integer userId = Integer.valueOf(data.get("userId").toString());
		if(CommonUtils.isNullEmpty(data.get("addressId").toString())){
			logger.info(MessageConsValue.legalMessage);
			result.put(ERROR, "请选择收货人信息.");
			return result;
		}
		Integer addressId = Integer.valueOf(data.get("addressId").toString());
		String productIds = data.get("productIds").toString();
		Integer guildId = Integer.valueOf(data.get("guildId").toString());
		try{
			String[] pids = productIds.split(",");//
			if(pids == null || pids.length == 0) ids.add(-1);
			for (String pid : pids) {
				ids.add(Integer.valueOf(pid));
			}
			result.put("userId", userId);
			result.put("addressId", addressId);
			result.put("guildId", guildId);
			result.put("ids", ids);
		}catch(Exception e){
			ids.add(-1);
			result.clear();
			result.put(ERROR, MessageConsValue.serverErrorMessage);
			return result;
		}
		return result;
	}
	
	/**
	 * 解析token
	 * @param token
	 * @param MAX_TIME
	 * @return
	 */
	public static Map<String, Object> guildDataAnalsySingleton(String token, Integer nuid, String os){
		Map<String, Object> result = new HashMap<String, Object>(); 
		List<Integer> ids = new ArrayList<Integer>();
		
		Map<String, Object> data = SessionTokenUtils.dataAnalsy(token);//解析token中的请求参数，转化成key value形式
		boolean legal = SessionTokenUtils.validateLogin(data, nuid, os);
		if(!legal || !data.containsKey("userId") || !data.containsKey("count") || !data.containsKey("productId") || !data.containsKey("guildId")){
			logger.info(MessageConsValue.legalMessage);
			result.put(ERROR, MessageConsValue.legalMessage);
			return result;
		}
		
		Integer userId = Integer.valueOf(data.get("userId").toString());
//		if(CommonUtils.isNullEmpty(data.get("addressId").toString())){
//			logger.info(MessageConsValue.legalMessage);
//			result.put(ERROR, "请选择收货人信息.");
//			return result;
//		}
		String productId = data.get("productId").toString();
		Integer count = Integer.valueOf(data.get("count").toString());
		Integer guildId = Integer.valueOf(data.get("guildId").toString());
		try{
			result.put("userId", userId);
			result.put("guildId", guildId);
			result.put("productId", productId);
			result.put("count", count);
		}catch(Exception e){
			logger.error(e.getMessage());
			ids.add(-1);
			result.clear();
			result.put(ERROR, MessageConsValue.serverErrorMessage);
			return result;
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
	}
}

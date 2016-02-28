package com.game.common.basics.validate;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.game.common.Constants;
import com.game.common.basics.security.Base64Utils;
import com.game.common.basics.security.RSAUtils;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.MD5;

public class SignatureValidator implements ConstraintValidator<Signature, Object> {
	private static final Logger logger = Logger.getLogger(SignatureValidator.class);
	private String signature;//签名
	private String gameId;//游戏编号
	private String deviceId;//设备唯一标识
	private String deviceName;//手机型号
	private String os;// (android、ios) 操作系统
	private String osVersion;// 操作系统版本
	private String gameVersion;// (游戏升级用)
	private String sdkVersion;// （当前SDK的版本号）
	private String screenResolution;// 屏幕分辨率
	private String timestamp;// （后续安全验证）
	private String data;//请求参数RSA加密密文
	//private String nuid;//用户标识ID
	private static final String CK = "ck";//客户端随机生成的3des加密字符串key
	//private static final String TK = "tk";//登录后的令牌参数

	public void initialize(Signature sign) {
		this.signature = sign.signature();
		this.deviceId = sign.deviceId();
		this.gameId = sign.gameId();
		this.deviceName = sign.deviceName();
		this.os = sign.os();
		this.osVersion = sign.osVersion();
		this.gameVersion = sign.gameVersion();
		this.sdkVersion = sign.sdkVersion();
		this.screenResolution = sign.screenResolution();
		this.timestamp = sign.timestamp();
		this.data = sign.data();
		//this.nuid = sign.nuid();
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext arg1) {
		try {
			String psignature = BeanUtils.getProperty(obj, this.signature);
			String deviceId = BeanUtils.getProperty(obj, this.deviceId);
			String gameId = BeanUtils.getProperty(obj, this.gameId);
			if (psignature == null) return false;
			String queryData = BeanUtils.getProperty(obj, this.data);
			if(queryData == null || "".equals(queryData)){
				String securityKey = CommonUtils.toMD5(Constants.AUTH_KEY + deviceId + gameId).toLowerCase();
				boolean result = psignature.toLowerCase().equals(securityKey);
				return result;
			}
			String deviceName = BeanUtils.getProperty(obj, this.deviceName);
			String os = BeanUtils.getProperty(obj, this.os);
			String osVersion = BeanUtils.getProperty(obj, this.osVersion);
			String gameVersion = BeanUtils.getProperty(obj, this.gameVersion);
			String sdkVersion = BeanUtils.getProperty(obj, this.sdkVersion);
			String screenResolution = BeanUtils.getProperty(obj, this.screenResolution);
			String timestamp = BeanUtils.getProperty(obj, this.timestamp);
			if(CommonUtils.isNullEmpty(deviceId) || 
					CommonUtils.isNullEmpty(os) ||
					CommonUtils.isNullEmpty(osVersion) ||
					CommonUtils.isNullEmpty(gameVersion) ||
					CommonUtils.isNullEmpty(sdkVersion) ||
					CommonUtils.isNullEmpty(screenResolution) ||
					CommonUtils.isNullEmpty(timestamp) ||
					CommonUtils.isNullEmpty(gameId)){
				logger.info("请求参数错误......");
				return false;
			}
			//String nuid = BeanUtils.getProperty(obj, this.nuid);
			String data = BeanUtils.getProperty(obj, this.data);
			logger.info("客户端请求加密报文 : " + data);
			SignatureSortMap map = new SignatureSortMap();
			try {
				data = data.replaceAll(" ", "+");
				byte[] bytes = Base64Utils.decode(data);//原始token是base64转换成byte
				byte[] decodedData = RSAUtils.decryptByPrivateKey(bytes, RSAUtils.privateKey);//RSA解密成byte
		        String target = new String(decodedData, "UTF-8");//解密RSA 还原报文为明文
		        SignatureSortMap decodeRASTokenMap = buildParameter(target, map);//解析明文参数变成key value形式
		        CommonUtils.copyProperties(decodeRASTokenMap, map);
			} catch (Exception e) {
				logger.error("加密报文不合法 : " + e.getMessage());
				return false;
			}
			map.put(this.deviceId, deviceId);
			map.put(this.gameId, gameId);
			map.put(this.deviceId, deviceId);
			map.put(this.deviceName, deviceName);
			map.put(this.os, os);
			map.put(this.osVersion, osVersion);
			map.put(this.gameVersion, gameVersion);
			map.put(this.sdkVersion, sdkVersion);
			map.put(this.screenResolution, screenResolution);
			map.put(this.timestamp, timestamp);
			map.sort();//升序排序
			
			String ckValue = (String) map.get(CK);
			StringBuilder builder = new StringBuilder();
			for (String key : map.keySet()) {
				if(CK.equals(key)) continue;
	            Object value = map.get(key);
	            if(builder.length() != 0) builder.append("&");
	            builder.append(key).append("=").append(value);
	        }
			builder.append(ckValue);
			
			String securityKey = MD5.md5(builder.toString()).toLowerCase();
			boolean result = psignature.toLowerCase().equals(securityKey);
			logger.info("Signature result : " + result);
			return result;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return false;
	}
	public SignatureSortMap buildParameter(String token, SignatureSortMap map){
		String[] parameters = token.split("&");
		for(String parameter : parameters){
			String[] kv = parameter.split("=");
			if(kv != null && kv.length == 2){
				map.put(kv[0], kv[1]);
			}else{
				map.put(kv[0], "");
			}
		}
		return map;
	}
}

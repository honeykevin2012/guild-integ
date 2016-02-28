package com.game.common.basics.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

	/**
	 * md5 encode
	 * @param bytes
	 * @return
	 */
	public static byte[] md5Digest(byte[] bytes) {
		try {
			return MessageDigest.getInstance("MD5").digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("md5 is inavalible.");
		}
	}

	/**
	 * base64 encode
	 * @param bytes
	 * @return
	 */
	public static String base64Encode(byte[] bytes) {
		return Base64.byteArrayToBase64(bytes);
	}

	/**
	 * base64 decode
	 * @param base64
	 * @return
	 */
	public static byte[] base64Decode(String base64) {
		return Base64.base64ToByteArray(base64);
	}

	public static String decode(String args){
		StringBuffer buff = new StringBuffer();
		if(args == null) return null;
		String parameter = args.replaceAll("!", "+");
		String[] paramerers = parameter.split("-");
		for (int i = 0; i < paramerers.length; i++) {
			String[] tempDe = ByteUtils.toString(SecurityUtils.base64Decode(paramerers[i]), "UTF-8").split("=", 2);
			for (int k = 0; k < tempDe.length; k++){
				if(tempDe.length == 0)continue;
				if(k != tempDe.length - 1 ){
					buff.append(tempDe[k]).append("=").append(tempDe[k+1]).append("\n");
				}
			}
		}
		return buff.toString();
	}
	public static String encode(String p){
		StringBuffer buffer = new StringBuffer();
		if(p == null) return "";
		byte[] temp = ByteUtils.toBytes(p, "UTF-8");
		buffer.append("-").append(SecurityUtils.base64Encode(temp));
		return buffer.toString();
	}
	public static void main(String[] args) {
//		String temp = "soKeyWords=宝马X1";
//		String res = encode(temp);//编码
//		System.out.println(res);
		System.out.println(SecurityUtils.decode("-bmFtZT0=-eWVhcj0yMDEw-c2VyaWVzSWQ9MjEwMg==-c2VsZWN0VHlwZT0x-dHlwZT0x-Y29tcGFueUlkPTgx-YnJhbmRJZD0y-dHVyblBhZ2U9-"));//解码
	}
	//     /系列英文/price/1/?argkeys=sdfsfsdfsdfsdfd
}

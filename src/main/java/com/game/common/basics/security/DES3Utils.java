package com.game.common.basics.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DES3Utils {
	private static final String key = "fd3e43f0fd9440719e5908c1a591";
	private static final String tokenKey = "fd3e43d0fd94f0711e5908c1a521";
	
	public static String encrypt3DESECB(String src, String key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(src.getBytes());

		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

	}

	// 3DESECB解密,key必须是长度大于等于 3*8 = 24 位
	public static String decrypt3DESECB(String src, String key) throws Exception {
		// --通过base64,将字符串转成byte数组
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytesrc = decoder.decodeBuffer(src);
		// --解密的key
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		// --Chipher对象解密
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		byte[] retByte = cipher.doFinal(bytesrc);

		return new String(retByte);
	}
	
	public static String decryptThreeDESECB(String text) throws Exception{
		return decrypt3DESECB(text, key);
	}
	
	public static String encryptThreeDESECB(String text) throws Exception{
		return encrypt3DESECB(text, key);
	}
	
	public static String decryptToken(String text) throws Exception{
		return decrypt3DESECB(text, tokenKey);
	}
	
	public static String encryptToken(String text) throws Exception{
		return encrypt3DESECB(text, tokenKey);
	}
	
	public static void main(String[] args) throws Exception {
		 String key = "fd3e43f0fd9111119e5908c1a591";
		 System.out.println(key.length());
	     String text = "201411123233-1004-44455-1,2,4"; 
	     String en = encryptToken(text);
	     System.out.println(en);
	     System.out.println(encryptToken(en));
	}
}

package com.game.common.basics.security;

import java.security.Key;

import javax.crypto.Cipher;

public class DESUtils {
	private static String DEFAULT_KEY = "fd3e43d0fd94f0711e5908c1a521";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public DESUtils() throws Exception {
		this(DEFAULT_KEY);
	}

	public DESUtils(String strKey) throws Exception {
		if (strKey == null)
			return;
		java.security.Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	private Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	public static String encode(String key, String str) {
		DESUtils des = null;
		try {
			des = new DESUtils(key);
			return des.encrypt(str);
		} catch (Exception ex) {
		}
		return "";
	}
	
	public static String encode(String str) {
		DESUtils des = null;
		try {
			des = new DESUtils(DEFAULT_KEY);
			return des.encrypt(str);
		} catch (Exception ex) {
		}
		return "";
	}
	
	public static String decode(String str) {
		DESUtils des = null;
		try {
			des = new DESUtils(DEFAULT_KEY);
			return des.decrypt(str);
		} catch (Exception ex) {
		}
		return "";
	}
	
	public static String decode(String key, String str) {
		DESUtils des = null;
		try {
			des = new DESUtils(key);
			return des.decrypt(str);
		} catch (Exception ex) {
		}
		return "";
	}

	public static String getKey() {
		return DEFAULT_KEY;
	}

	public static void main(String[] args) {
		try {
			String test = "6b23f646d4d736ba5d99d3f9d5ea3b5f6e4ca38d7ff3bc0c440b102a9ef77ea7cd543638ed926e32";
			DESUtils des = new DESUtils();// 自定义密钥
			System.out.println("加密前的字符：" + test);
			System.out.println("加密后的字符：" + des.encrypt(test));
			System.out.println("wI+sNn8AdrJfO/pWfT3WMbFw/IxLRq9eqOLanXQceWIxF6D/FjOsebxwZlkGJU0e5pm71l42s/BNisFB1t82UA==".toLowerCase());
			System.out.println("解密后的字符：" + des.decrypt(test));
			System.out.println("22-33".split("-").length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

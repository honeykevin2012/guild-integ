package com.game.common.utility;

public class HexByteUtils {
	public static byte ConvertChar(byte aChar) {
		if (aChar >= 'A' && aChar <= 'F')
			return (byte) (aChar - 'A' + 10);
		else if (aChar >= 'a' && aChar <= 'f')
			return (byte) (aChar - 'a' + 10);
		else if (aChar >= '0' && aChar <= '9')
			return (byte) (aChar - '0');
		else {
			return 0;
		}
	}

	public static byte[] ConvertVCardUTF2Bytes(String srcVCardStr) {
		srcVCardStr = srcVCardStr.replace(" ", "");
		String[] pars = srcVCardStr.split("=");
		StringBuffer strBuf = new StringBuffer();
		if (pars != null && pars.length > 0) {
			for (String s : pars) {
				strBuf.append(s);
			}
		}
		String strMiddle = strBuf.toString();
		byte[] byteMiddle = strMiddle.getBytes();
		byte[] byteResult = new byte[byteMiddle.length / 2];
		for (int i = 0, j = 0; i < byteMiddle.length && byteMiddle.length % 2 == 0; i += 2) {
			byte th = ConvertChar(byteMiddle[i]);
			byte tl = ConvertChar(byteMiddle[i + 1]);
			byteResult[j++] = (byte) (th * 16 + tl);
		}
		return byteResult;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] bytes = ConvertVCardUTF2Bytes("=28=8F=EB=51=AF=E0=BE=00=60=85=48=9F=D8=A9=44=71");
		System.out.println(bytes);
	}

}

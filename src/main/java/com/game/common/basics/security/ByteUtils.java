package com.game.common.basics.security;

import java.io.UnsupportedEncodingException;

public class ByteUtils {

	private static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; 

	/**
	 * ���ַ����ַ�ָ���ĳ���
	 * @param value
	 * @param width
	 * @param c
	 * @return
	 */
	public static String padLeft(String value, int width, char c) {
		if (value == null)
			throw new IllegalArgumentException("value is null.");
		StringBuffer buffer = new StringBuffer();
		for(int i=width-value.length(); i>0; i--)
			buffer.insert(0, c);
		return buffer.append(value).toString();
	}

	/**
	 * ���ַ����Ҳ��ַ�ָ���ĳ���
	 * @param value
	 * @param width
	 * @param c
	 * @return
	 */
	public static String padRight(String value, int width, char c) {
		if (value == null)
			throw new IllegalArgumentException("value is null.");
		StringBuffer buffer = new StringBuffer(value);
		for(int i=width-value.length(); i>0; i--)
			buffer.append(c);
		return buffer.toString();
	}

	/**
	 * ��Stringת����ָ�����ȵ�byte����
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(String value) {
		return value == null ? new byte[0] : value.getBytes();
	}

	/**
	 * ��Stringת����ָ�����ȵ�byte����
	 * @param value
	 * @param charset
	 * @return
	 */
	public static byte[] toBytes(String value, String charset) {
		try {
			if (value == null)
				return new byte[0];
			if (charset == null)
				return toBytes(value);
			return value.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("charset unsupported.");
		}
	}

	/**
	 * ��Stringת����ָ�����ȵ�byte���飬�ַ��Ȳ��������油\0
	 * @param value
	 * @param size ָ�����ȵ��ֽ���
	 * @return
	 */
	public static byte[] toBytes(String value, int size) {
		byte[] bytes = toBytes(value);
		if (bytes.length > size)
			throw new IllegalArgumentException("value is too long.");
		byte[] b = new byte[size];
		copyBytes(bytes, 0, b, 0, bytes.length);
		return b;
	}

	/**
	 * ��Stringת����ָ�����ȵ�byte����
	 * @param value
	 * @param size
	 * @param charset
	 * @return
	 */
	public static byte[] toBytes(String value, int size, String charset) {
		byte[] bytes = toBytes(value, charset);
		if (bytes.length > size)
			throw new IllegalArgumentException("value is too long.");
		byte[] b = new byte[size];
		copyBytes(bytes, 0, b, 0, bytes.length);
		return b;
	}

	/**
	 * ��longֵת����byte����
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(long v) {
		byte[] bytes = new byte[8];
		for(int i=0; i<8; i++) {
			bytes[7-i] = (byte) (v & 0xFF);
			v >>= 8;
		}
		return bytes;
	}

	/**
	 * ��intֵת����byte����
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(int v) {
		byte[] bytes = new byte[4];
		for(int i=0; i<4; i++) {
			bytes[3-i] = (byte) (v & 0xFF);
			v >>= 8;
		}
		return bytes;
	}

	/**
	 * ��shortֵת����byte����
	 * @param v
	 * @return
	 */
	public static byte[] toBytes(short v) {
		byte[] bytes = new byte[2];
		for(int i=0; i<2; i++) {
			bytes[1-i] = (byte) (v & 0xFF);
			v >>= 8;
		}
		return bytes;
	}

	/**
	 * ��byte����ָ����buf
	 * @param value
	 * @param buf
	 * @param offset
	 */
	public static void putByte(byte value, byte[] buf, int offset) {
		buf[offset] = value;
	}

	/**
	 * ��shortֵת����byte���飬Ȼ�����ָ����buf
	 * @param value
	 * @param buf
	 * @param offset
	 */
	public static void putShort(short value, byte[] buf, int offset) {
		byte[] bytes = toBytes(value);
		copyBytes(bytes, buf, offset);
	}

	/**
	 * ��intֵת����byte���飬Ȼ�����ָ����buf
	 * @param value
	 * @param buf
	 * @param offset
	 */
	public static void putInt(int value, byte[] buf, int offset) {
		byte[] bytes = toBytes(value);
		copyBytes(bytes, buf, offset);
	}

	/**
	 * ��longֵת����byte���飬Ȼ�����ָ����buf
	 * @param value
	 * @param buf
	 * @param offset
	 */
	public static void putLong(long value, byte[] buf, int offset) {
		byte[] bytes = toBytes(value);
		copyBytes(bytes, buf, offset);
	}

	/**
	 * ��byte[]����ָ����buf
	 * @param value
	 * @param buf
	 * @param offset
	 */
	public static void putBytes(byte[] value, byte[] buf, int offset) {
		copyBytes(value, buf, offset);
	}

	/**
	 * ���ַ�ת����byte���飬Ȼ�����ָ����buf
	 * @param value
	 * @param buf
	 * @param offset
	 */
	public static void putString(String value, byte[] buf, int offset) {
		byte[] bytes = toBytes(value);
		copyBytes(bytes, buf, offset);
	}

	/**
	 * ���ַ�ת����byte���飬Ȼ�����ָ����buf
	 * @param value �ַ�
	 * @param size ָ�����ȵ��ֽ���
	 * @param buf
	 * @param offset
	 */
	public static void putString(String value, int size, byte[] buf, int offset) {
		byte[] bytes = toBytes(value, size);
		copyBytes(bytes, buf, offset);
	}

	/**
	 * ��byte����ת����long
	 * @param bytes
	 * @return
	 */
	public static long toLong(byte[] bytes) {
		return toLong(bytes, 0, bytes.length);
	}

	/**
	 * ��byte����ת����long
	 * @param bytes
	 * @param offset
	 * @param length
	 * @return
	 */
	public static long toLong(byte[] bytes, int offset, int length) {
		if (length > 8)
			throw new IllegalArgumentException("out of range.");
		if (offset + length > bytes.length)
			throw new IllegalArgumentException("offset+length out of range.");
		long value = 0;
		for(int i=offset; i<offset+length; i++)
			value = (value<<8) + (0xFF & bytes[i]);
		return value;
	}

	/**
	 * ��byte����ת����int
	 * @param bytes
	 * @return
	 */
	public static int toInt(byte[] bytes) {
		return toInt(bytes, 0, bytes.length);
	}

	/**
	 * ��byte����ת����int
	 * @param bytes
	 * @param offset
	 * @param length
	 * @return
	 */
	public static int toInt(byte[] bytes, int offset, int length) {
		if (length > 4)
			throw new IllegalArgumentException("out of range.");
		return (int) toLong(bytes, offset, length);
	}

	/**
	 * ��byte����ת����short
	 * @param bytes
	 * @return
	 */
	public static short toShort(byte[] bytes) {
		return toShort(bytes, 0, bytes.length);
	}

	/**
	 * ��byte����ת����short
	 * @param bytes
	 * @param offset
	 * @param length
	 * @return
	 */
	public static short toShort(byte[] bytes, int offset, int length) {
		if (length > 2)
			throw new IllegalArgumentException("out of range.");
		return (short) toLong(bytes, offset, length);
	}

	/**
	 * ��byte����ת����String
	 * @param bytes
	 * @return
	 */
	public static String toString(byte[] bytes) {
		return new String(bytes);
	}

	/**
	 * ��byte����ת����String
	 * @param bytes
	 * @param charset �ַ����
	 * @return
	 */
	public static String toString(byte[] bytes, String charset) {
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("charset unsupported.");
		}
	}

	/**
	 * ��byte����ת����String
	 * @param bytes
	 * @param offset
	 * @param length
	 * @return
	 */
	public static String toString(byte[] bytes, int offset, int length) {
		int len = Math.min(bytes.length-offset, length);
		return new String(bytes, offset, len);
	}

	/**
	 * ��byte����ת����String
	 * @param bytes
	 * @param offset
	 * @param length
	 * @param charset �ַ����
	 * @return
	 */
	public static String toString(byte[] bytes, int offset, int length, String charset) {
		try {
			return new String(bytes, offset, length, charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("charset unsupported.");
		}
	}

	/**
	 * ��bytesת����16��ʾ��String
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes) {
		return toHexString(bytes, 0, bytes.length);
	}

	/**
	 * ��bytesת����16��ʾ��String
	 * @param bytes
	 * @param offset
	 * @param length
	 * @return
	 */
	public static String toHexString(byte[] bytes, int offset, int length) {
		int count = Math.min(length, bytes.length-offset);
		char[] chars = new char[count * 2];
		for (int i = 0; i < count; i++) {
			int b = 0xff & bytes[offset+i];
			chars[i * 2] = hexDigits[b >> 4];
			chars[i * 2 + 1] = hexDigits[b & 0xF];
		}
		return new String(chars);
	}

	/**
	 * ����byte����
	 * @param src
	 * @param srcOff
	 * @param dest
	 * @param destOff
	 * @param len
	 */
	public static void copyBytes(byte[] src, int srcOff, byte[] dest, int destOff, int len) {
		if (src == null || dest == null) return;
		System.arraycopy(src, srcOff, dest, destOff, len);
	}

	/**
	 * ��������
	 * @param src
	 * @param dest
	 * @param destOff
	 */
	public static void copyBytes(byte[] src, byte[] dest, int destOff) {
		copyBytes(src, 0, dest, destOff, src.length);
	}

	/**
	 * ��Դbyte�����н�ȡ���֣�������µ�byte����
	 * @param bytes
	 * @param offset
	 * @param len
	 * @return
	 */
	public static byte[] cutBytes(byte[] bytes, int offset, int len) {
		byte[] newBytes = new byte[len];
		copyBytes(bytes, offset, newBytes, 0, len);
		return newBytes;
	}

	/**
	 * ��ӡbytes����
	 * @param bytes
	 */
	public static void printBytes(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<bytes.length; i+=16) {
			buffer.append(padLeft(Integer.toHexString(i), 8, '0')).append("h: ");
			buffer.append(padRight(toHexString(bytes, i, 16), 32, ' ')).append(" ");
			buffer.append(toString(bytes, i, 16));
			buffer.append("\n");
		}
		System.err.println(buffer.toString());
	}

	/**
	 * ��}���������
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] addBytes(byte[] a, byte[] b) {
		if (a == null && b == null) return null;
		if (a == null) return b;
		if (b == null) return a;
		byte[] bytes = new byte[a.length + b.length];
		copyBytes(a, 0, bytes, 0, a.length);
		copyBytes(b, 0, bytes, a.length, b.length);
		return bytes;
	}

	public static void main(String[] args) {
		
	}

}

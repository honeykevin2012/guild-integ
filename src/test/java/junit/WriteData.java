package junit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.commons.io.FileUtils;
import org.apache.mina.core.buffer.IoBuffer;

public class WriteData {

	public void writer(IoBuffer buffer) {
		byte[] bytes = null;
		try {
			buffer.flip();
			bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			String filePath = "Test.pr";
			File file = new File("D:/" + filePath);
			FileUtils.writeByteArrayToFile(file, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 此方法是用来求的字符串的长度 (包括中文和英文)
	 * 
	 * @param str
	 * @return
	 */
	public static int getByte(String str) {
		byte[] by = str.getBytes();
		int length = by.length;
		return length;
	}

	/**
	 * @param args
	 * @throws CharacterCodingException
	 */
	public static void main(String[] args) throws CharacterCodingException {
		Charset cn = Charset.forName("UTF-8");
		CharsetEncoder cnEncoder = cn.newEncoder();
		IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
		buffer.putInt(1);

		String reportTeam = "贵州送变电工程公司_10标段";
		buffer.put((byte) WriteData.getByte(reportTeam));
		buffer.putString(reportTeam, cnEncoder);
		// 用户名
		String reportUser = "asdf1";
		buffer.put((byte) WriteData.getByte(reportUser));
		buffer.putString(reportUser, cnEncoder);
		// 日期
		buffer.putInt(20101111);

		// 冻土和人员数目
		buffer.putInt(11);
		buffer.putInt(22);
		buffer.putInt(33);
		buffer.putInt(44);
		buffer.putInt(55);
		buffer.putInt(66);
		buffer.putInt(77);

		// 天气、材料、存在问题
		String weather = "很好";
		buffer.putInt(WriteData.getByte(weather));
		buffer.putString(weather, cnEncoder);
		String material = "一般";
		buffer.putInt(WriteData.getByte(material));
		buffer.putString(material, cnEncoder);
		String problems = "还行";
		buffer.putInt(WriteData.getByte(problems));
		buffer.putString(problems, cnEncoder);

		// towers长度
		buffer.putShort((short) 2);

		// 塔号、桩号、塔型
		String towerNo = "22222222";
		buffer.put((byte) WriteData.getByte(towerNo));
		buffer.putString(towerNo, cnEncoder);
		String stakeNo = "6106";
		buffer.put((byte) WriteData.getByte(stakeNo));
		buffer.putString(stakeNo, cnEncoder);
		String towerMode = "333333333";
		buffer.put((byte) WriteData.getByte(towerMode));
		buffer.putString(towerMode, cnEncoder);

		// 冻土类型和装配类型
		buffer.put((byte) 1);
		buffer.put((byte) 0);
		// 状态和时间
		buffer.put((byte) 1);
		buffer.putInt(20101111);
		buffer.put((byte) 2);
		buffer.putInt(20101113);
		buffer.put((byte) 3);
		buffer.putInt(20101115);
		buffer.put((byte) 4);
		buffer.putInt(20101118);
		buffer.put((byte) 5);
		buffer.putInt(20101119);

		// 下面重复
		towerNo = "22222222";
		buffer.put((byte) WriteData.getByte(towerNo));
		buffer.putString(towerNo, cnEncoder);
		stakeNo = "6106";
		buffer.put((byte) WriteData.getByte(stakeNo));
		buffer.putString(stakeNo, cnEncoder);
		towerMode = "333333333";
		buffer.put((byte) WriteData.getByte(towerMode));
		buffer.putString(towerMode, cnEncoder);
		buffer.put((byte) 1);
		buffer.put((byte) 0);
		buffer.put((byte) 1);
		buffer.putInt(20101111);
		buffer.put((byte) 2);
		buffer.putInt(20101113);
		buffer.put((byte) 3);
		buffer.putInt(20101115);
		buffer.put((byte) 4);
		buffer.putInt(20101118);
		buffer.put((byte) 5);
		buffer.putInt(20101119);

		buffer.putString("end", 32, cnEncoder);
		WriteData writeData = new WriteData();
		writeData.writer(buffer);
	}

}

package junit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.mina.core.buffer.IoBuffer;

public class DBUtils {
	private static Connection conn = null;
	public static Connection getConnection() {
		try {
			String url = "jdbc:mysql://120.132.70.180:3306/a1?characterEncoding=utf-8";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, "root", "LHz^M42f(Y6mBg8");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void insert(String uid, String roleId, String roleName, String amount) {
		String sql = "insert into user_game_trigger1 (uid, role_id, role_name, amount) values('" + uid + "', '" + roleId + "', '" + roleName + "', '" + amount + "');";
		System.out.println(sql);
		if(conn == null) conn = getConnection();
		try {
			conn.createStatement().execute(sql);
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update() {
		String sql = "update user_game_trigger1 set amount=6667 where uid=888 and role_id=888000";
		Connection conn = getConnection();
		try {
			boolean result = conn.createStatement().execute(sql);
			System.out.println(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void readblob() throws IOException {
		String sql = "select charInfo as charInfo from t_big_character_info where id='288551433547024357'";
		Connection conn = getConnection();
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while(rs.next()){
				Blob charInfo = rs.getBlob(1);
				InputStream is = charInfo.getBinaryStream();
				ByteArrayInputStream bais = (ByteArrayInputStream) is;
				byte[] byte_data = new byte[bais.available()]; //bais.available()返回此输入流的字节数

				bais.read(byte_data, 0,byte_data.length);//将输入流中的内容读到指定的数组
				//String note = new String(byte_data,"iso-8859-1"); //再转为String，并使用指定的编码方式
				is.close();
				CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
				IoBuffer buf = IoBuffer.allocate(byte_data.length);
				buf.put(byte_data);
				buf.flip();
				int length = buf.get();  
				String team = buf.getString(length, decoder);  
	            System.out.println(team); 
	            
	             length= buf.get();
	             if(length > 0) System.out.println(buf.getString(length, decoder));
	             length=buf.get();  
	             if(length > 0) System.out.println(buf.getString(length, decoder)); 
	             length=buf.get();  
	             if(length > 0) System.out.println(buf.getString(length, decoder)); 
	             length=buf.get();  
	             if(length > 0) System.out.println(buf.getString(length, decoder)); 
				//System.out.println(convertStreamToString(is));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertblob() throws UnsupportedEncodingException {
		String sql = "insert into t_big_character_info (charInfo) values(?)";
		Connection conn = getConnection();
		try {
			InputStream is = new ByteArrayInputStream("中华人民共和国".getBytes());
			PreparedStatement pst  = conn.prepareStatement(sql);
			pst.setBinaryStream(1, is);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
//		File file = new File("D://Test.pr");
//		InputStream is = new FileInputStream(file);
//		System.out.println(convertStreamToString(is));
//		byte[] bs = FileUtils.readFileToByteArray(file);
//		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
//		IoBuffer buf = IoBuffer.allocate(bs.length);
//		buf.put(bs);
//		buf.flip();
//		System.out.println("文件大小：" + buf.limit());
//		int m = buf.getInt();
//		System.out.println("文件数:" + m);
//		 for(int t=0;t<m;t++){
//			 int length=buf.get();  
//			 String team = buf.getString(length, decoder);  
//             System.out.println(team); 
//             
//             length=buf.get();  
//			 String user = buf.getString(length, decoder);  
//             System.out.println(user);  
//             
//             length=buf.get();  
//             System.out.println(buf.getString(length, decoder));
//             
//             length=buf.getInt();
//             if(length > 0) System.out.println(buf.getString(length, decoder));
//             length=buf.get();  
//             if(length > 0) System.out.println(buf.getString(length, decoder)); 
//             length=buf.get();  
//             if(length > 0) System.out.println(buf.getString(length, decoder)); 
//             length=buf.get();  
//             if(length > 0) System.out.println(buf.getString(length, decoder)); 
//		 }
//		for (int i = 0; i < 260; i++) {
//			String uid = (10 + i) + "";
//			String roleId = (100 + i) + "";
//			String roleName = "游戏角色" + i;
//			String amount = (20000 + i) + "";
//			insert(uid, roleId, roleName, amount);
//		}
//		 update();
//		insertblob();
		readblob();
	}
}

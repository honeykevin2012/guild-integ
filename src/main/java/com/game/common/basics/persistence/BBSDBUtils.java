package com.game.common.basics.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import com.game.common.utility.MD5;

public class BBSDBUtils {
	private static Connection conn = getConnection();
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://123.59.56.43:3306/discuz?characterEncoding=utf-8";
			String user = "root";
			String passd = "123456";
//			Connection conn = DriverManager.getConnection(Constants.BBS_DB_URL, Constants.BBS_DB_USERNAME, Constants.BBS_DB_PASSWORD);
			Connection conn = DriverManager.getConnection(url, user, passd);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void insert(String username, String password, String email) {
		if(conn == null) conn = getConnection();
		long time = System.currentTimeMillis();
		String millis = (time + "").substring(0, 10);
		Random random = new Random();
		int salt = (int) (random.nextDouble() * (1000000 - 100000) + 100000);
		String passd = MD5.md5(MD5.md5(password).toLowerCase() + salt).toLowerCase();
		//String ucmemberSql = "INSERT INTO pre_ucenter_members SET username='"+username+"', password='"+passd+"', email='"+email+"', regip='', regdate='"+millis+"', salt='"+salt+"'";
		String ucmemberSql = "INSERT INTO pre_ucenter_members(username, password, email, regip, regdate, salt) values (?, ?, ?, ?, ?, ?)";
		int uid = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(ucmemberSql, Statement.RETURN_GENERATED_KEYS); ; 
			pstmt.setString(1, username);
			pstmt.setString(2, passd);
			pstmt.setString(3, email);
			pstmt.setString(4, "");
			pstmt.setInt(5, Integer.valueOf(millis));
			pstmt.setString(6, salt + "");
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys(); 
			if(rs.next()){ 
				uid = rs.getInt(1); 
			} 
			System.out.println(uid);
			if(uid > 0){
				//insert ucenter_ucmemberfields
				String ucmemberfieldsSql = "INSERT INTO pre_ucenter_memberfields SET uid = "+uid+"";
				pstmt = conn.prepareStatement(ucmemberfieldsSql);
				pstmt.execute();
				
				//insert pre_common_member
				String commonMemberSql = "INSERT INTO pre_common_member (uid, username, password, email, adminid, groupid, regdate, credits, timeoffset) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(commonMemberSql);
				pstmt.setInt(1, uid);
				pstmt.setString(2, username);
				pstmt.setString(3, passd);
				pstmt.setString(4, email);
				pstmt.setString(5, "0");
				pstmt.setString(6, "10");
				pstmt.setInt(7, Integer.valueOf(millis));
				pstmt.setString(8, "0");
				pstmt.setString(9, "9999");
				pstmt.execute();
				
				String commonMemberStatusSql = "INSERT INTO pre_common_member_status (uid, regip, lastip, lastvisit, lastactivity, lastpost, lastsendmail) values (?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(commonMemberStatusSql);
				pstmt.setInt(1, uid);
				pstmt.setString(2, "");
				pstmt.setString(3, "");
				pstmt.setInt(4, Integer.valueOf(millis));
				pstmt.setInt(5, Integer.valueOf(millis));
				pstmt.setString(6, "0");
				pstmt.setString(7, "0");
				pstmt.execute();
				
			}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
//		$this->db->query("INSERT INTO ".UC_DBTABLEPRE."memberfields SET uid='$uid'");
//		// BEGIN
//		$this->db->query("INSERT INTO ".UC_DBNAME."pre_common_member SET uid='$uid', username='$username', password='$password', email='$email', adminid='0', groupid='10', regdate='".$this->base->time."', credits='0', timeoffset='9999'");
//		$this->db->query("INSERT INTO ".UC_DBNAME."pre_common_member_status SET uid='$uid', regip='$regip', lastip='$regip', lastvisit='".$this->base->time."', lastactivity='".$this->base->time."', lastpost='0', lastsendmail='0'");
//		$this->db->query("INSERT INTO ".UC_DBNAME."pre_common_member_profile SET uid='$uid'");
//		$this->db->query("INSERT INTO ".UC_DBNAME."pre_common_member_field_forum SET uid='$uid'");
//		$this->db->query("INSERT INTO ".UC_DBNAME."pre_common_member_field_home SET uid='$uid'");
//		$this->db->query("INSERT INTO ".UC_DBNAME."pre_common_member_count SET uid='$uid', extcredits1='0', extcredits2='0', extcredits3='0', extcredits4='0', extcredits5='0', extcredits6='0', extcredits7='0', extcredits8='0'");
//		
//		String sql = "insert into user_game_trigger1 (uid, role_id, role_name, amount) values('" + uid + "', '" + roleId + "', '" + roleName + "', '" + amount + "');";
		try {
			//conn.createStatement().execute(sql);
		} catch (Exception e) {
			try {
				if(!conn.isClosed()) conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(!conn.isClosed()) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		insert("test", "test", "111@163.com");
//		System.out.println(MD5.md5(MD5.md5("test12").toLowerCase() + "ed834e").toLowerCase());
	}
}

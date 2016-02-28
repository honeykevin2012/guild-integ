package com.game.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

	public static final String COMMON_ZERO = "0";
	public static final String COMMON_FLAG = "1";
	public static final String STATUS = COMMON_FLAG;
	public static final String REQUEST_FLAG = COMMON_FLAG;
	public static final String PC_DB_FLAG = "1";
	public static final String ANDROID_DB_FLAG = "2";
	public static final String IOS_DB_FLAG = "3";
	public static final String ANDROID = "android";
	public static final String IOS = "ios";
	public static final String WEB = "00000";
	public static final String PRODUCT_CHANNEL = "1000";
	public static final String PRODUCT_MAX_IMAGE_SIZE = "800800";
	public static final String PRODUCT_MIN_IMAGE_SIZE = "200200";
	public static final String PRODUCT_MIDDLE_IMAGE_SIZE = "350350";
	public static final String PRODUCT_DEFAULT_FLAG = COMMON_FLAG;
	
	public static String QUERY_USER_GAME_FLAG;
	public static String AUTH_KEY;
	public static String ASSERT_DEBUG;
	public static String PRODUCT_DEFAULT_IMG_SIZE;//
	public static String IMAGE_SITE_URL;
	public static String WEB_URL;
	
	public static String TEMP_FILE_PATH;//临时文件存储路径

	
	public static String FTP_USERNAME;
	public static String FTP_PASSWORD;
	public static String FTP_SERVER;
	public static String FTP_PORT;	
	//文件FTP存储根目录
	public static String FTP_PRODUCT;
	public static String FTP_GUILD;
	public static String FTP_USER;
	public static String FTP_PLATFORM;
	public static String FTP_GAME;
	public static String FTP_FEEDBACK;
	
	public static String BBS_DB_URL;
	public static String BBS_DB_USERNAME;
	public static String BBS_DB_PASSWORD;
	public static String BBS_DB_PRE;
	
	public static String EXCHANGE_RULE_KEY="text_exchange_rule";
	public static String TEXT_GAME_RULE="text_game_rule";
	
	public static String INNER_AUTH_KEY;
	@Value("#{config.image_site_url}")
	public void setIMAGE_SITE_URL(String iMAGE_SITE_URL) {
		IMAGE_SITE_URL = iMAGE_SITE_URL;
	}
	
	@Value("#{config.auth_key}")
	public void setAUTH_KEY(String aUTH_KEY) {
		AUTH_KEY = aUTH_KEY;
	}
	
	@Value("#{config.assert_debug}")
	public void setASSERT_DEBUG(String aSSERT_DEBUG) {
		ASSERT_DEBUG = aSSERT_DEBUG;
	}
	
	@Value("#{config.product_default_img_size}")
	public void setPRODUCT_DEFAULT_IMG_SIZE(String pRODUCT_DEFAULT_IMG_SIZE) {
		PRODUCT_DEFAULT_IMG_SIZE = pRODUCT_DEFAULT_IMG_SIZE;
	}
	
	@Value("#{config.web_url}")
	public void setWEB_URL(String wEB_URL) {
		WEB_URL = wEB_URL;
	}
	
	@Value("#{config.query_user_game_flag}")
	public void setQUERY_USER_GAME_FLAG(String qUERY_USER_GAME_FLAG) {
		QUERY_USER_GAME_FLAG = qUERY_USER_GAME_FLAG;
	}

	@Value("#{ftp.ftp_name}")
	public void setFTP_USERNAME(String fTP_USERNAME) {
		FTP_USERNAME = fTP_USERNAME;
	}
	@Value("#{ftp.ftp_pwd}")
	public void setFTP_PASSWORD(String fTP_PASSWORD) {
		FTP_PASSWORD = fTP_PASSWORD;
	}
	@Value("#{ftp.ftp_server}")
	public void setFTP_SERVER(String fTP_SERVER) {
		FTP_SERVER = fTP_SERVER;
	}
	@Value("#{ftp.ftp_port}")
	public void setFTP_PORT(String fTP_PORT) {
		FTP_PORT = fTP_PORT;
	}
	
	@Value("#{ftp.ftp_product}")
	public void setFTP_PRODUCT(String fTP_PRODUCT) {
		FTP_PRODUCT = fTP_PRODUCT;
	}
	
	@Value("#{ftp.ftp_guild}")
	public void setFTP_GUILD(String fTP_GUILD) {
		FTP_GUILD = fTP_GUILD;
	}
	
	@Value("#{ftp.ftp_user}")
	public void setFTP_USER(String fTP_USER) {
		FTP_USER = fTP_USER;
	}
	
	@Value("#{ftp.ftp_platform}")
	public void setFTP_PLATFORM(String fTP_PLATFORM) {
		FTP_PLATFORM = fTP_PLATFORM;
	}
	
	@Value("#{ftp.ftp_feedback}")
	public void setFTP_FEEDBACK(String fTP_FEEDBACK) {
		FTP_FEEDBACK = fTP_FEEDBACK;
	}
	
	@Value("#{config.temp_file_path}")
	public void setTEMP_FILE_PATH(String tEMP_FILE_PATH) {
		TEMP_FILE_PATH = tEMP_FILE_PATH;
	}

	@Value("#{db.bbs_db_url}")
	public void setBBS_DB_URL(String bBS_DB_URL) {
		BBS_DB_URL = bBS_DB_URL;
	}

	@Value("#{db.bbs_db_username}")
	public void setBBS_DB_USERNAME(String bBS_DB_USERNAME) {
		BBS_DB_USERNAME = bBS_DB_USERNAME;
	}

	@Value("#{db.bbs_db_password}")
	public void setBBS_DB_PASSWORD(String bBS_DB_PASSWORD) {
		BBS_DB_PASSWORD = bBS_DB_PASSWORD;
	}
	@Value("#{db.bbs_db_pre}")
	public void setBBS_DB_PRE(String bBS_DB_PRE) {
		BBS_DB_PRE = bBS_DB_PRE;
	}
	@Value("#{config.inner_auth_key}")
	public void setINNER_AUTH_KEY(String iNNER_AUTH_KEY) {
		INNER_AUTH_KEY = iNNER_AUTH_KEY;
	}
	
	
}

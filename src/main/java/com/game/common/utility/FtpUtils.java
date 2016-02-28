package com.game.common.utility;

import java.io.InputStream;
import org.apache.commons.net.ftp.FTPClient;



public class FtpUtils {
	
	public static boolean ftpUploadFile(InputStream is,
			String server,
			int port,
			String userName, 
			String userPassword,
			String path, 
			String fileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server,port);
            ftpClient.login(userName, userPassword);
            ftpClient.changeWorkingDirectory(path);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1") , is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }


	
    public static boolean deleteFtpFile(
			String server,
			int port,
			String userName, 
			String userPassword,
			String path) { 
	    FTPClient ftpClient = new FTPClient();
	    try {
	        ftpClient.connect(server,port);
	        ftpClient.login(userName, userPassword);
	        ftpClient.changeWorkingDirectory(path);
	        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
	        ftpClient.deleteFile(new String(path.getBytes("GBK"), "iso-8859-1"));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if(ftpClient.isConnected()) {
	            try {
	                ftpClient.disconnect();
	            } catch (Exception e) {
	                e.printStackTrace();
	                return false;
	            }
	        }
	    }
	    return true;  
    } 
}

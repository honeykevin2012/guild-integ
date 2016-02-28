package com.game.common.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.game.common.Constants;

/**
 * FTP客户端
 * @author zhaolianjun
 */
public class FTPClientTemplate {
	// ---------------------------------------------------------------------
	// Instance data
	// ---------------------------------------------------------------------
	/** logger */
	protected final Logger log = Logger.getLogger(getClass());

	private String host;
	private int port;
	private String username;
	private String password;

	private boolean binaryTransfer = true;
	private boolean passiveMode = true;
	private String encoding = "UTF-8";
	private int clientTimeout = 5000;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isBinaryTransfer() {
		return binaryTransfer;
	}

	public void setBinaryTransfer(boolean binaryTransfer) {
		this.binaryTransfer = binaryTransfer;
	}

	public boolean isPassiveMode() {
		return passiveMode;
	}

	public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getClientTimeout() {
		return clientTimeout;
	}

	public void setClientTimeout(int clientTimeout) {
		this.clientTimeout = clientTimeout;
	}

	public static FTPClientTemplate getClient(){
		FTPClientTemplate ftp = new FTPClientTemplate();
		ftp.setHost(Constants.FTP_SERVER);
		ftp.setPort(Integer.valueOf(Constants.FTP_PORT));
		ftp.setUsername(Constants.FTP_USERNAME);
		ftp.setPassword(Constants.FTP_PASSWORD);
		return ftp;
	}
	// ---------------------------------------------------------------------
	// private method
	// ---------------------------------------------------------------------
	/**
	 * 返回一个FTPClient实例
	 * @throws Exception
	 */
	private FTPClient getFTPClient() throws Exception {
		FTPClient ftpClient = new FTPClient(); // 构造一个FtpClient实例
		ftpClient.setControlEncoding(encoding); // 设置字符集
		connect(ftpClient); // 连接到ftp服务器
		// 设置为passive模式
		if (passiveMode) {
			ftpClient.enterLocalPassiveMode();
		}
		setFileType(ftpClient); // 设置文件传输类型

		try {
			ftpClient.setSoTimeout(clientTimeout);
		} catch (SocketException e) {
			throw new Exception("Set timeout error.", e);
		}
		return ftpClient;
	}

	/**
	 * 设置文件传输类型
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	private void setFileType(FTPClient ftpClient) throws Exception {
		try {
			if (binaryTransfer) {
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			} else {
				ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
			}
		} catch (IOException e) {
			throw new Exception("Could not to set file type.", e);
		}
	}

	/**
	 * 连接到ftp服务器
	 * 
	 * @param ftpClient
	 * @return 连接成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean connect(FTPClient ftpClient) throws Exception {
		try {
			ftpClient.connect(host, port);

			// 连接后检测返回码来校验连接是否成功
			int reply = ftpClient.getReplyCode();

			if (FTPReply.isPositiveCompletion(reply)) {
				// 登陆到ftp服务器
				if (ftpClient.login(username, password)) {
					setFileType(ftpClient);
					return true;
				}else{
					ftpClient.disconnect();
					throw new Exception("Login username or password error.");
				}
			} else {
				ftpClient.disconnect();
				throw new Exception("FTP server refused connection.");
			}
		} catch (IOException e) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect(); // 断开连接
				} catch (IOException e1) {
					throw new Exception("Could not disconnect from server.", e);
				}
			}
			throw new Exception("Could not connect to server.", e);
		}
	}

	/**
	 * 断开ftp连接
	 * @throws Exception
	 */
	private void disconnect(FTPClient ftpClient) throws Exception {
		try {
			ftpClient.logout();
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			throw new Exception("Could not disconnect from server.", e);
		}
	}

	// ---------------------------------------------------------------------
	// public method
	// ---------------------------------------------------------------------
	/**
	 * 上传一个本地文件到远程指定文件
	 * 
	 * @param serverFile 服务器端文件名(包括完整路径)
	 * @param localFile 本地文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 * @throws Exception
	 */
	public boolean put(String serverFile, String localFile) throws Exception {
		return put(serverFile, localFile, false);
	}

	/**
	 * 上传一个本地文件到远程指定文件
	 * 
	 * @param serverFile服务器端文件名(包括完整路径)
	 * @param localFile本地文件名(包括完整路径)
	 * @param delFile成功后是否删除文件
	 * @return 成功时，返回true，失败返回false
	 * @throws Exception
	 */
	public boolean put(String serverFile, String localFile, boolean delFile) throws Exception {
		FTPClient ftpClient = null;
		InputStream input = null;
		try {
			ftpClient = getFTPClient();
			// 处理传输
			input = new FileInputStream(localFile);
			boolean result = ftpClient.storeFile(serverFile, input);
			log.debug("put " + localFile);
			input.close();
			if (delFile) {
				(new File(localFile)).delete();
				log.debug("delete " + localFile);
			}
			return result;
		} catch (FileNotFoundException e) {
			throw new Exception("local file not found.", e);
		} catch (IOException e) {
			throw new Exception("Could not put file to server.", e);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
				throw new Exception("Couldn't close FileInputStream.", e);
			}
			if (ftpClient != null) {
				disconnect(ftpClient); // 断开连接
			}
		}
	}
	
	/**
	 * 上传一个本地文件到远程指定文件
	 * 
	 * @param serverFile服务器端文件名(包括完整路径)
	 * @param inStream 输入流
	 * @return 成功时，返回true，失败返回false
	 * @throws Exception
	 */
	public boolean put(String destPath, InputStream inStream) throws Exception {
		FTPClient ftpClient = null;
		InputStream input = null;
		try {
			ftpClient = getFTPClient();
			// 处理传输
			this.createDirecroty(destPath, ftpClient);
			boolean result = ftpClient.storeFile(destPath, inStream);
			return result;
		} catch (FileNotFoundException e) {
			throw new Exception("local file not found.", e);
		} catch (IOException e) {
			throw new Exception("Could not put file to server.", e);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
				throw new Exception("Couldn't close FileInputStream.", e);
			}
			if (ftpClient != null) {
				disconnect(ftpClient); // 断开连接
			}
		}
	}

	/**
	 * 下载一个远程文件到本地的指定文件
	 * 
	 * @param serverFile服务器端文件名(包括完整路径)
	 * @param localFile本地文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 * @throws Exception
	 */
	public boolean get(String serverFile, String localFile) throws Exception {
		return get(serverFile, localFile, false);
	}

	/**
	 * 下载一个远程文件到本地的指定文件
	 * 
	 * @param serverFile服务器端文件名(包括完整路径)
	 * @param localFile本地文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 * @throws Exception
	 */
	public boolean get(String serverFile, String localFile, boolean delFile)
			throws Exception {
		OutputStream output = null;
		try {
			output = new FileOutputStream(localFile);
			return get(serverFile, output, delFile);
		} catch (FileNotFoundException e) {
			throw new Exception("local file not found.", e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				throw new Exception("Couldn't close FileOutputStream.", e);
			}
		}
	}

	/**
	 * 下载一个远程文件到指定的流 处理完后记得关闭流
	 * 
	 * @param serverFile
	 * @param output
	 * @return
	 * @throws Exception
	 */
	public boolean get(String serverFile, OutputStream output) throws Exception {
		return get(serverFile, output, false);
	}

	/**
	 * 下载一个远程文件到指定的流 处理完后记得关闭流
	 * 
	 * @param serverFile
	 * @param output
	 * @param delFile
	 * @return
	 * @throws Exception
	 */
	public boolean get(String serverFile, OutputStream output, boolean delFile) throws Exception {
		FTPClient ftpClient = null;
		try {
			ftpClient = getFTPClient();
			// 处理传输
			ftpClient.retrieveFile(serverFile, output);
			if (delFile) { // 删除远程文件
				ftpClient.deleteFile(serverFile);
			}
			return true;
		} catch (IOException e) {
			throw new Exception("Couldn't get file from server.", e);
		} finally {
			if (ftpClient != null) {
				disconnect(ftpClient); // 断开连接
			}
		}
	}

	/**
	 * 从ftp服务器上删除一个文件
	 * 
	 * @param delFile
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String delFile) throws Exception {
		FTPClient ftpClient = null;
		try {
			ftpClient = getFTPClient();
			ftpClient.deleteFile(delFile);
			return true;
		} catch (IOException e) {
			throw new Exception("Couldn't delete file from server.", e);
		} finally {
			if (ftpClient != null) {
				disconnect(ftpClient); // 断开连接
			}
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param delFiles
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String[] delFiles) throws Exception {
		FTPClient ftpClient = null;
		try {
			ftpClient = getFTPClient();
			for (String s : delFiles) {
				ftpClient.deleteFile(s);
			}
			return true;
		} catch (IOException e) {
			throw new Exception("Couldn't delete file from server.", e);
		} finally {
			if (ftpClient != null) {
				disconnect(ftpClient); // 断开连接
			}
		}
	}

	/**
	 * 列出远程默认目录下所有的文件
	 * 
	 * @return 远程默认目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
	 * @throws Exception
	 */
	public String[] listNames() throws Exception {
		return listNames(null);
	}

	/**
	 * 列出远程目录下所有的文件
	 * 
	 * @param remotePath
	 *            远程目录名
	 * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
	 * @throws Exception
	 */
	public String[] listNames(String remotePath) throws Exception {
		FTPClient ftpClient = null;
		try {
			ftpClient = getFTPClient();
			String[] listNames = ftpClient.listNames(remotePath);
			return listNames;
		} catch (IOException e) {
			throw new Exception("列出远程目录下所有的文件时出现异常", e);
		} finally {
			if (ftpClient != null) {
				disconnect(ftpClient); // 断开连接
			}
		}
	}

	public void directoryCreator(String folder){
		
	}
	
	  /** */
    /**
    * 创建远程服务器目录
    * @param remote 远程服务器文件绝对路径
    * @return 目录创建是否成功
    * @throws IOException
    */
    public boolean createDirecroty(String remote, FTPClient ftp) throws IOException {
        boolean success = true;
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !ftp.changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = new String(remote.substring(start, end));
                if (!ftp.changeWorkingDirectory(subDirectory)) {
                    if (ftp.makeDirectory(subDirectory)) {
                        ftp.changeWorkingDirectory(subDirectory);
                    } else {
                        log.info("Create folder failed.");
                        success = false;
                        return success;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

	
	public static void main(String[] args) throws Exception {
		FTPClientTemplate ftp = new FTPClientTemplate();
		ftp.setHost("192.168.152.1");
		ftp.setPort(21);
		ftp.setUsername("root");
		ftp.setPassword("123456");

		FileInputStream fis = new FileInputStream("E:/photo/s.jpg");
		boolean result = ftp.put("/ss/aa/ss12345.jpg", fis);
		//boolean result = ftp.put("/ss/aa/ss123.jpg", "E:/photo/s.jpg");
		System.out.println(result);
		// ftp.get("/test_0920.zip", "d:/test_0920.zip");
		//String[] aa = { "qqq/111.txt", "qqq/222.zip" };
		//ftp.delete(aa);
	}
}

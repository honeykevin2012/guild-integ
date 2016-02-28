package com.game.common.utility;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

public class SendMail {

	private Logger log = Logger.getLogger(this.getClass());
	private MimeMessage mimeMsg; // MIME邮件对象

	private Session session; // 邮件会话对象

	private Properties props; // 系统属性
	private String username = ""; // smtp认证用户名和密码

	private String password = "";
	

	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

	public SendMail(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * @param hostName
	 *  String
	 */
	public void setSmtpHost(String hostName) {
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
	}

	/**
	 * @return boolean
	 */
	public boolean createMimeMessage() {
		try {
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
			
		} catch (Exception e) {
			System.err.println("getDefaultInstance error！" + e);
			return false;
		}

		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();
			return true;
		} catch (Exception e) {
			System.err.println("MimeMessage！" + e);
			return false;
		}
	}

	/**
	 * @param need
	 *            boolean
	 */
	public void setNeedAuth(boolean need) {
		if (props == null)
			props = System.getProperties();

		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/**
	 * @param mailSubject
	 *            String
	 * @return boolean
	 */
	public boolean setSubject(String mailSubject) {
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			System.err.println("setSubject error！");
			return false;
		}
	}

	/**
	 * @param mailBody
	 *            String
	 */
	public boolean setBody(String mailBody) {
		try {
			//mimeMsg.setDataHandler(new javax.activation.DataHandler(new StringDataSource (mailBody,"text/html")));
			
			BodyPart bp = new MimeBodyPart();
			bp.setContent(mailBody,"text/html;charset=gbk");
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			System.err.println("setBody！" + e);
			return false;
		}
	}

	/**
	 * 增加附件
	 * @param srcFilename
	 * @param realFilaName
	 * @return
	 */
	public boolean addFileAffix(String srcFilename,byte[] fileData) {
		try {
			System.err.println("@@@@@addFileAffix：" + srcFilename + " datalen:" + fileData.length);
			BodyPart bp = new MimeBodyPart();
			String strExt=srcFilename.substring(srcFilename.length()-3,srcFilename.length());
			String fileType=null;
			if((strExt.equalsIgnoreCase("log"))||(strExt.equalsIgnoreCase("log")))
				fileType="text/plain";
			else if((strExt.equalsIgnoreCase("doc"))||(strExt.equalsIgnoreCase("rtf")))
				fileType="application/msword";
			else if(strExt.equalsIgnoreCase("xls"))
				fileType="application/vnd.ms-excel";
			else if(strExt.equalsIgnoreCase("pdf"))
				fileType="application/pdf";
			else if((strExt.equalsIgnoreCase("gif"))||(strExt.equalsIgnoreCase("JPG"))||(strExt.equalsIgnoreCase("jepg"))||(strExt.equalsIgnoreCase("bmp")))
				fileType="image/gif";
			else
				fileType="application/"+strExt;
			
			DataHandler dh = new DataHandler(new ByteArrayDataSource(fileData,fileType));
			bp.setDataHandler(dh);
			bp.setFileName(srcFilename);
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			System.err.println("addFileAffix：" + srcFilename + "error！" + e);
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setFrom(String from) {
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setTo(String to) {
		if (to == null || to.length() == 0)
			return false;
		try {
			String lastChar=to.substring(to.length()-1, to.length());
			if((";").equals(lastChar))
			{
				to=to.substring(0,to.length()-1);
			}
			String[]   arrReceiver   =   to.split(";");
			InternetAddress[]   address=new   InternetAddress[arrReceiver.length];   
			for   (int   i=0;i<arrReceiver.length;i++){   
				address[i]=new   InternetAddress(arrReceiver[i]);   
			}   
			mimeMsg.setRecipients(Message.RecipientType.TO, address);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean sendout() throws Exception
	{
		Transport transport=null;
		mimeMsg.setContent(mp);
		mimeMsg.saveChanges();
		log.debug("正在发送邮件....");
		Session mailSession = Session.getInstance(props, null);
		transport = mailSession.getTransport("smtp");
		transport.connect((String) props.get("mail.smtp.host"), username, password);//System.out.println("qqq！");
		transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
		if (mimeMsg.getRecipients(Message.RecipientType.CC) != null)
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
		log.debug("发送邮件成功！");
		transport.close();
		transport=null;
		return true;
	}
	
	public static void main(String[] ares)throws Exception{
		SendMail send = new SendMail("smtp.163.com");
		send.setBody("ss");
		send.setFrom("hyjuezhanhz@163.com");
		send.setNamePass("hyjuezhanhz","kevinmadoka");
//		send.setSmtpHost("mail.qq.com");
		send.setSubject("火影决战海贼王密码找回!");
		send.setTo("zhaolianjun@9961.cn");
		send.setNeedAuth(true);
		boolean ss = send.sendout();
		System.out.print(ss);
		
		//Runtime.getRuntime().exec("CMD /C start mailto:luo_918@126.com?cc=luo_918@sohu.com&subject=xxxx&body=aaaa"); 
	}
}

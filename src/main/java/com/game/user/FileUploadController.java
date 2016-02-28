package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.exception.ActionException;
import com.game.common.utility.Channel;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;
import com.game.common.utility.FTPClientTemplate;
import com.game.common.utility.image.Im4ImageUtils;

@Controller
public class FileUploadController extends BaseController {
	private static final Logger logger = Logger.getLogger(FileUploadController.class);

	/**
	 * 普通FTP文件上传
	 * @param request
	 * @param response
	 * @throws ActionException
	 */
	@RequestMapping(value = "/user/upload", method = { POST })
	public @ResponseBody Object fileNormal(Integer type, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(type == null) return buildFormError("error#参数不正确.");
			String fileChannel = null;
			if (type == 1) {
				fileChannel = Channel.User.getValue();
			}else if(type == 2){
				fileChannel = Channel.Guild.getValue();
			}else{
				return buildFormError("error#参数值不正确.");
			}
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("headIcon");
			if (file == null) {
				file = (CommonsMultipartFile) multipartRequest.getFile("upload");
			}
			if(file == null) return buildFormError("error#请选择上传文件.");
			String date = DateUtils.format(new Date(), "yyyy-MM");
			String datePath = date.replace("-", File.separator);
			// 根据channel获取不同业务存储根路径

			String ext = CommonUtils.getExtendName(file.getOriginalFilename()).toLowerCase();
			if("exe".equals(ext) || "js".equals(ext)) return buildFormError("error#文件不合法.");
			String randomName = DateUtils.format(new Date(), "yyyyMMddHHmmSS");
			String fileName = randomName + "." + ext;
			String filePath = this.getChannelDir(fileChannel) + File.separator + datePath + File.separator + fileName;
			filePath = filePath.replace('\\', '/');
			if(file.getSize() > 100000){//文件大于50K，需要切图200*200
				logger.info("上传文件大小为: " + file.getSize());
				InputStream is = file.getInputStream();
				String tempFilePath = Constants.TEMP_FILE_PATH + File.separator + fileName;//(1)文件上传临时存储路径
				tempFilePath = tempFilePath.replace('\\', '/');
				FileOutputStream fos = new FileOutputStream(tempFilePath);
				Im4ImageUtils.cutImageCenter(is, fos, 200, 200);
				is.close();
				fos.close();
				
				InputStream tempInputStream = new FileInputStream(tempFilePath);
				FTPClientTemplate client = FTPClientTemplate.getClient();//ftp server
				client.put(filePath, tempInputStream);//上传到FTP服务器
				tempInputStream.close();
				new File(tempFilePath).delete();//(2)删除(1)处的临时文件
			}else{
				FTPClientTemplate ftpClient = FTPClientTemplate.getClient();// 存储原始图片,不做任何处理
				ftpClient.put(filePath, file.getInputStream());
			}
			
			String webURL = filePath;
			JSONObject res = new JSONObject();
			res.put("path", webURL.replace("\\", "/"));
			res.put("url", Constants.IMAGE_SITE_URL + webURL.replace("\\", "/"));
			JsonResult jsonResult = new JsonResult();
			jsonResult.setData(res);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#上传失败.");
		}
	}
	
	public String getChannelDir(String channel){
		if (Channel.Product.getValue().equals(channel))
			return Constants.FTP_PRODUCT;
		else if (Channel.Guild.getValue().equals(channel))
			return Constants.FTP_GUILD;
		else if (Channel.User.getValue().equals(channel))
			return Constants.FTP_USER;
		else if (Channel.Platform.getValue().equals(channel))
			return Constants.FTP_PLATFORM;
		else if (Channel.Game.getValue().equals(channel))
			return Constants.FTP_GAME;
		else
			return Constants.FTP_PRODUCT;
	}
}

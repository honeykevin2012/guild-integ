package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.BaseForm;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.platform.domain.PlatformAppVersion;
import com.game.platform.persistence.service.PlatformAppVersionService;

@Controller
public class AppVersionController extends BaseController {
	private static final Logger logger = Logger.getLogger(AppVersionController.class);
	@Autowired
	private PlatformAppVersionService service;
	
	@RequestMapping(value="/platform/client/version", method = { POST })
	public @ResponseBody Object list(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String clientType = "android".equals(form.getOs().toLowerCase()) ? "0" : "1";
				List<PlatformAppVersion> versions = service.selectByVersionAndType(form.getSdkVersion(), clientType);
				JsonResult jsonResult = new JsonResult();
				if(versions != null && versions.size() > 0){
					PlatformAppVersion version = versions.get(0);
					JSONObject json = new JSONObject();
					json.put("version", version.getVersionCode());
					json.put("versionName", version.getVersionName());
					json.put("updateUrl", version.getUrl());
					json.put("isForce", version.getIsForce());
					json.put("description", version.getRemark());
					jsonResult.setData(json);
					return jsonResult;
				}else{
					JSONObject json = new JSONObject();
					json.put("version", "");
					json.put("versionName", "");
					json.put("updateUrl", "");
					json.put("isForce", "");
					json.put("description", "");
					jsonResult.setData(json);
					return jsonResult;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
}

package com.game.app;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.app.domain.AppLoadimg;
import com.game.app.domain.Version;
import com.game.app.form.VersionForm;
import com.game.app.persistence.service.AppLoadimgService;
import com.game.app.persistence.service.VersionService;
import com.game.common.BaseForm;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.utility.MD5;

@Controller
public class ServiceAppController extends BaseController {

	@Autowired
	private VersionService service;
	@Autowired
	private AppLoadimgService appLoadimgService;
	// 版本升级接口
	@RequestMapping(value = "/services/app/version", method = { POST })
	public @ResponseBody Object channel(@Valid @ModelAttribute("versionForm") VersionForm versionForm,
			BindingResult bindResult) {
		try {
			if (bindResult.hasErrors()) {
				return this.buildFormError(bindResult.getAllErrors().get(0).getDefaultMessage());
			} else {
				try {
					Integer osType;
					if ("android".equalsIgnoreCase(versionForm.getOs())) {
						osType = 1;
					} else {
						osType = 2;
					}
					Version version = new Version();
					version.setOsType(osType);
					version.setVersion(versionForm.getVersion());
					version = service.select(version);
						if(version!=null){
							JSONObject obj = new JSONObject();
							obj.put("isForce", version.getIsForce());
							obj.put("version", version.getVersion());
							obj.put("name", version.getName());
							obj.put("updateUrl", version.getUpdateUrl());
							JsonResult result = new JsonResult();
							result.setState("update");
							result.setData(obj);
							return result;
						}else{
							return ResultHandler.bindResult("error#已是最新版本.");
						}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return ResultHandler.bindResult("error#版本升级接口错误.");
				}

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return ResultHandler.bindResult("error#版本升级接口错误.");
		}
	}
	
	
	// 启动页接口
		@RequestMapping(value = "/services/app/loadimg", method = { POST })
		public @ResponseBody Object loadimg(@Valid @ModelAttribute("form") BaseForm form,
				BindingResult bindResult) {
			try {
				if (bindResult.hasErrors()) {
					return this.buildFormError(bindResult.getAllErrors().get(0).getDefaultMessage());
				} else {
					try {
						Integer osType;
						if ("android".equalsIgnoreCase(form.getOs())) {
							osType = 1;
						} else {
							osType = 2;
						}
						AppLoadimg loadimg = appLoadimgService.selectByOsType(osType);
							if(loadimg!=null){
								JSONObject obj = new JSONObject();
								obj.put("imgUrl", loadimg.getPicPath());
								obj.put("md5", MD5.md5(loadimg.getPicPath()));
								JsonResult result = new JsonResult();
								result.setData(obj);
								return result;
							}else{
								return ResultHandler.bindResult("error#没有启动页.");
							}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return ResultHandler.bindResult("error#启动页接口错误.");
					}

				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				return ResultHandler.bindResult("error#启动页接口错误.");
			}
		}

}

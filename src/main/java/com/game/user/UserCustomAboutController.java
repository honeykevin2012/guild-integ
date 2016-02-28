package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JsonConfig;

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
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.user.domain.UserCustomAbout;
import com.game.user.persistence.service.UserCustomAboutService;

@Controller
public class UserCustomAboutController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserCustomAboutController.class);
	@Autowired
	private UserCustomAboutService service;
	
	
	@RequestMapping(value="/user/custom/about", method = { POST })
	public @ResponseBody Object list(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				JsonConfig config = new JsonConfig();
			    config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			    UserCustomAbout about = service.selectByGameId(form.getGameId());
			    if(about != null){
				    JsonResult jsonResult = new JsonResult();
				    jsonResult.setData(about); 
					return jsonResult;
			    }else{
			    	return buildFormError("数据不存在.");
			    }
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
}

package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.basics.BaseController;
import com.game.common.basics.ResultHandler;
import com.game.common.utility.CommonUtils;
import com.game.user.domain.UserActiveCode;
import com.game.user.domain.form.UserForm;
import com.game.user.persistence.service.UserActiveCodeService;

@Controller
public class UserActiveCodeController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserActiveCodeController.class);
	@Autowired
	private UserActiveCodeService service;
	
	@RequestMapping(value="/user/active/code", method = { POST })
    public @ResponseBody Object active(@Valid @ModelAttribute("userForm") UserForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getCode())) return buildFormError("error#激活码不能为空.");
				
				UserActiveCode active = service.selectByCodeAndGame(form.getCode(), form.getGameId());
				if(active == null || "1".equals(active.getIsUsed())) return buildFormError("error#激活码无效.");
				
				active.setIsUsed("1");//设置激活码为"已使用"状态
				service.update(active);
				return ResultHandler.bindResult("ok#激活码使用成功."); 
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
}

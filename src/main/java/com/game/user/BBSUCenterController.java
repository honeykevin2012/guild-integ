package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import api.ucenter.Client;

import com.game.common.BaseForm;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.utility.CommonUtils;
import com.game.user.domain.User;
import com.game.user.persistence.service.UserService;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class BBSUCenterController extends BaseController {
	private static final Logger logger = Logger.getLogger(BBSUCenterController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/bbs/user/center/login", method = { POST })
	public @ResponseBody Object login(@Valid @ModelAttribute("form") BaseForm form){
		try{
			if(CommonUtils.isNullEmpty(form.getData())) return buildFormError("error#请求数据不合法.");
			//token parser start
			String data = form.getData(); 
			logger.info("User login request token : " + data);
			Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
			String username = dataMap.containsKey("userName") ? (String)dataMap.get("userName") : "";
			String password = dataMap.containsKey("password") ? (String)dataMap.get("password") : "";
			
			if(CommonUtils.isNullEmpty(username)) return buildFormError("error#用户名不能为空.");
			if(CommonUtils.isNullEmpty(password)) return buildFormError("error#密码不能为空.");
			
			Client uc = new Client();
			String $ucsynlogin = uc.ssologin(username, password);
			logger.info(">>>>>>>>>>>>>>>bbs login : " + $ucsynlogin);
			if($ucsynlogin.indexOf("<script") != -1){
				JsonResult res = new JsonResult();
				res.setData($ucsynlogin);
				return res;
			}else{//用户不存在时候去注册
				User user = userService.selectCheckUserExistsByName(username);
				if(user != null){
					String mail = (user.getEmail() == null || "".equals(user.getEmail())) ? username + "@simple.com" : user.getEmail();
					String $register = uc.uc_user_register(username, password, mail);
					if(CommonUtils.isNumber($register) && Integer.valueOf($register) > 1){//注册成功
						String $relogin = uc.ssologin(username, password);
						if($relogin.indexOf("<script") != -1){
							JsonResult res = new JsonResult();
							res.setData($relogin);
							return res;
						}else{
							return buildFormError($ucsynlogin); 
						}
					}
				}
			}
			return buildFormError($ucsynlogin);
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/bbs/user/center/register", method = { POST })
	public @ResponseBody Object register(@Valid @ModelAttribute("form") BaseForm form){
		try{
			if(CommonUtils.isNullEmpty(form.getData())) return buildFormError("error#请求数据不合法.");
			//token parser start
			String data = form.getData(); 
			logger.info("User login request token : " + data);
			Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
			String username = dataMap.containsKey("userName") ? (String)dataMap.get("userName") : "";
			String password = dataMap.containsKey("password") ? (String)dataMap.get("password") : "";
			String email = dataMap.containsKey("email") ? (String)dataMap.get("email") : username + "@simple.com";
			email = (email == null || "".equals(email)) ? username + "@simple.com" : email;
			
			if(CommonUtils.isNullEmpty(username)) return buildFormError("error#用户名不能为空.");
			if(CommonUtils.isNullEmpty(password)) return buildFormError("error#密码不能为空.");
			
			Client uc = new Client();
			String $register = uc.uc_user_register(username, password, email);
			if(CommonUtils.isNumber($register) && Integer.valueOf($register) > 1){
				JsonResult res = new JsonResult();
				res.setData($register);
				return res;
			}else{
				//-1 : 用户名不合法
				//-2 : 包含不允许注册的词语
				//-3 : 用户名已经存在
				//-4 : email 格式有误
				//-5 : email 不允许注册
				//-6 : 该 email 已经被注册
				if("-1".equals($register)){
					return buildFormError("用户名不合法"); 
				}else if("-2".equals($register)){
					return buildFormError("包含不允许注册的词语"); 
				}else if("-3".equals($register)){
					return buildFormError("用户名已经存在"); 
				}else if("-4".equals($register)){
					return buildFormError("email 格式有误"); 				
				}else if("-5".equals($register)){
					return buildFormError("email 不允许注册"); 
				}else if("-6".equals($register)){
					return buildFormError("该 email 已经被注册"); 
				}else{
					return buildFormError("未知错误"); 
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/bbs/user/center/logout", method = { POST })
	public @ResponseBody Object logout(){
		try{
			Client uc = new Client();
			String $res = uc.uc_user_synlogout();
			JsonResult reult = new JsonResult();
			reult.setData($res);
			return reult;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

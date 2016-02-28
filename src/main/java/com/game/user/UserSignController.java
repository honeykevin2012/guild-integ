package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Map;

import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.BaseForm;
import com.game.common.MessageConsValue;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.init.SettingListener;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserSignTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.user.utils.SessionTokenUtils;
import com.game.user.utils.UserSignRule;

@Controller
public class UserSignController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserSignController.class);
	private static final String content = null;
	
	@RequestMapping(value="/user/sign", method = { POST })
    public @ResponseBody Object sign(@Valid @ModelAttribute("form") BaseForm form, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
				if(!legal || !dataMap.containsKey("userId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				Integer userId = Integer.valueOf(dataMap.get("userId").toString());
				boolean isSigned = UserSignRule.isSigned(userId);
				if(isSigned) return this.buildFormError("已经签到过了, 不能重复签到.");
				
				UserSignRule.signPoint(userId);//签到奖励积分
				//发送系统奖励邮件
				MessageCore core = new MessageCore();
				core.setAdapter(new MessageUserSignTemplate()).transfer(new PracticVirtual(), userId).send();
				
				//DataEyeAgent.userSign(userId+"", userId+"",form.getOs());
				return ResultHandler.bindResult("ok#签到成功.");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	@RequestMapping(value="/user/sign/check", method = { POST })
    public @ResponseBody Object check(@Valid @ModelAttribute("form") BaseForm form, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
				if(!legal || !dataMap.containsKey("userId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				Integer userId = Integer.valueOf(dataMap.get("userId").toString());
				boolean isSigned = UserSignRule.isSigned(userId);
				JsonResult json = new JsonResult();
				JSONObject d = new JSONObject();
				d.put("isSigned", isSigned ? "1" : "0");
				d.put("content", SettingListener.getValue("userSignDesc"));
				json.setData(d);
				return json;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	public static void main(String[] args) {
		System.out.println(content);
	}
}

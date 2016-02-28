package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import api.ucenter.Client;

import com.game.common.Constants;
import com.game.common.MessageConsValue;
import com.game.common.basics.BaseController;
import com.game.common.basics.BaseException;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.security.DES3Utils;
import com.game.common.basics.security.DESUtils;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;
import com.game.common.utility.MD5;
import com.game.common.utility.SendMail;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.init.setting.SettingAttribute;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserATRegisterTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.platform.persistence.service.PlatformGameAppService;
import com.game.platform.utils.Avatar;
import com.game.user.domain.User;
import com.game.user.domain.User.UserDefault;
import com.game.user.domain.UserDataItems;
import com.game.user.domain.UserGameResult;
import com.game.user.domain.UserLoginEntity;
import com.game.user.domain.UserVipGroup;
import com.game.user.domain.form.UserForm;
import com.game.user.persistence.service.UserDataItemsService;
import com.game.user.persistence.service.UserGameService;
import com.game.user.persistence.service.UserService;
import com.game.user.persistence.service.UserVipGroupService;
import com.game.user.utils.AwardUserHelper;
import com.game.user.utils.RandomHelper;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class UserSecurityController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserSecurityController.class);
	@Autowired
	private UserService service;
	@Autowired
	private UserDataItemsService userDataService;
	@Autowired
	UserVipGroupService userVipGroupService;
	@Autowired
	PlatformGameAppService platformGameAppService;
	@Autowired
	UserGameService userGameService;
	@Autowired
	GuildInfoService guildInfoService;	
	@RequestMapping(value="/user/login", method = { POST })
    public @ResponseBody Object login(@Valid @ModelAttribute("userForm") UserForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try{
			//String ip = "";
			//if(!"pc".equalsIgnoreCase(form.getOs())){
			//	ip = CommonUtils.realIPAddress(request);
			//}else{
			//	ip=form.getIp();
			//}
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getData())) return buildFormError("error#请求数据不合法.");
				//token parser start
				String data = form.getData(); 
				logger.info("User login request token : " + data);
				Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
				String username = dataMap.containsKey("userName") ? (String)dataMap.get("userName") : "";
				String password = dataMap.containsKey("password") ? (String)dataMap.get("password") : "";
				
				if(CommonUtils.isNullEmpty(username)) return buildFormError("error#用户名不能为空.");
				if(CommonUtils.isNullEmpty(password)) return buildFormError("error#密码不能为空.");
				
				//token parser end
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", username.trim());
				map.put("userPassword", MD5.md5(password));
				User user = service.selectCheckUserExists(map);
				if(user == null) return buildFormError("error#用户名或密码错误, 请重新输入.");
				if(!"1".equals(user.getStatus())) return buildFormError("error#用户已经被禁用, 请联系客服人员.");
				String ck = SessionTokenUtils.getSessionCK(data);//获取客户端的ck
				
				String tk = SessionTokenUtils.createToken(user.getId().toString(), user.getCrateTime().getTime());//创建tk
				SessionTokenUtils.createSession(user.getId().toString(), form.getOs(), ck, tk);
				
				UserLoginEntity entity = new UserLoginEntity();
				entity.setRegisterTime(DateUtils.format(user.getCrateTime(), "yyyy-MM-dd HH:mm:ss"));
				entity.setSecurityLevel(user.getEmail() == null ? "0" : "2");
				entity.setUserId(user.getId());
				entity.setAvatar(user.getHeadIcon());
				entity.setEmail(user.getEmail());
				entity.setExp(user.getExp());
				entity.setGroupName(user.getGroupName());
				entity.setNickName(user.getNickName());
				entity.setGroupId(user.getGroupId());
				entity.setGroupLevel(user.getGroupLevel());
				entity.setTk(DES3Utils.encrypt3DESECB(tk, ck));
				//entity.setGameToken(tk);
				entity.setNuid(user.getId() + "");
				
				//积分计算
				AwardUserHelper.awardUser(SettingAttribute.SETTING_LOGIN, user.getId());
				
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(entity);
				//DataEyeAgent.login(user.getId().toString(), form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), ip,"", form.getOsVersion(), form.getScreenResolution());
				return jsonResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/register", method = { POST })
    public @ResponseBody Object register(@Valid @ModelAttribute("userForm") UserForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try{
			String ip = CommonUtils.realIPAddress(request);
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getRegisterType())) return buildFormError("error#注册类型不能为空.");
				if(UserDefault.MT_REGISTER.getValue().equals(form.getRegisterType())){//手动注册
					String userName = form.getUserName();
					if(userName == null || "".equals(userName.trim())) return buildFormError("error#用户名不能为空.");
					if(userName.length() < 5 || userName.length() > 16) return buildFormError("error#用户名长度5-16位.");
					if(CommonUtils.isNullEmpty(form.getPassword())) return buildFormError("error#密码不能为空.");
					User user = new User();
					user.setUserName(userName.trim());
					List<User> userList = service.selectByEntity(user);
					if(userList != null && userList.size() > 0) return buildFormError("error#用户已存在."); 
					user.setUserPwd(MD5.md5(form.getPassword()));
					user.setSex(UserDefault.SEX.getValue());
					user.setClientIp(ip);
					user.setEmail(form.getEmail());
					user.setPhone(form.getPhone());
					UserVipGroup group = userVipGroupService.selectDefaultLevel();//查询用户默认等级用户组
					if(group == null) user.setGroupId(Integer.parseInt(UserDefault.GROUP.getValue()));//默认级别最低会员
					else user.setGroupId(group.getId());
					user.setBirthday(new Date());
					user.setExp(0);
					user.setBalance(Long.valueOf(UserDefault.BALANCE.getValue()));
					user.setPoint(0);
					user.setSourceFrom(parseSource(form.getOs()));
					user.setHeadIcon(form.getIcon() == null || "".equals(form.getIcon()) ? Avatar.getFemaleAvatar() : form.getIcon());
					user.setRegisterType(UserDefault.MT_REGISTER.getValue());
					user.setOs(form.getOs());
					user.setOsVersion(form.getOsVersion());
					user.setImei(form.getDeviceId());
					service.insert(user);
					
					//记录用户信息
					UserDataItems userDataItems = new UserDataItems();
					userDataItems.setUserId(user.getId());
					userDataService.insert(userDataItems);
					//积分计算
					AwardUserHelper.awardUser(SettingAttribute.SETTING_REGISTER, user.getId());
					
					JsonResult jsonResult = new JsonResult();
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userId", user.getId());
					jsonResult.setData(jsonObject);
					
					try{
						Client uc = new Client();
						uc.uc_user_register(userName.trim(), form.getPassword(), form.getEmail());
					}catch(Exception e){
						return jsonResult;  
					}
					
					//DataEyeAgent.register(user.getId().toString(), "", form.getDeviceId(), form.getOs(), form.getDeviceName(), ip, "", form.getOsVersion(), form.getScreenResolution());
					return jsonResult;
				}else if(UserDefault.AT_REGISTER.getValue().equals(form.getRegisterType())){//自动注册
					return autoRegister(ip, form);
				}else{
					return buildFormError("error#非法注册来源."); 
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/account/setting", method = { POST })
    public @ResponseBody Object accountSetting(@Valid @ModelAttribute("userForm") UserForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try{
			//String ip = CommonUtils.realIPAddress(request);
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	private String parseSource(String os){
		//设置请求来源
		String resource = null;
		if (Constants.ANDROID.equals(os))
			resource = "2";
		else if (Constants.IOS.equals(os))
			resource = "3";
		else
			resource = "1";
		return resource;
	}
	
	@RequestMapping(value="/user/change/password", method = { POST })
    public @ResponseBody Object change(@Valid @ModelAttribute("userForm") UserForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getUserName())) return buildFormError("error#用户名不能为空.");
				if(CommonUtils.isNullEmpty(form.getOriginalPassword())) return buildFormError("error#原始密码不能为空.");
				if(CommonUtils.isNullEmpty(form.getNewPassword())) return buildFormError("error#新密码不能为空.");
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", form.getUserName());
				map.put("userPassword", MD5.md5(form.getOriginalPassword()));
				User user = service.selectCheckUserExists(map);
				if(user == null) return buildFormError("error#用户名或密码错误.");
				user.setUserPwd(MD5.md5(form.getNewPassword()));
				service.update(user);
				return ResultHandler.bindResult("ok#恭喜您, 密码修改成功."); 
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/security/bind", method = { POST })
    public @ResponseBody Object bind(@Valid @ModelAttribute("userForm") UserForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getUserName())) return buildFormError("error#用户名不能为空.");
				if(CommonUtils.isNullEmpty(form.getPassword())) return buildFormError("error#密码不能为空.");
				if(CommonUtils.isNullEmpty(form.getEmail())) return buildFormError("error#安全邮箱不能为空.");
				if(!CommonUtils.checkEmail(form.getEmail())) return buildFormError("error#邮箱格式不正确.");
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("userName", form.getUserName());
				map.put("userPassword", MD5.md5(form.getPassword()));
				User user = service.selectCheckUserExists(map);
				if(user == null) return buildFormError("error#用户名或密码错误.");
				user.setEmail(form.getEmail());
				service.update(user);
				return ResultHandler.bindResult("ok#恭喜您, 邮箱绑定成功."); 
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/find/password", method = { POST })
    public @ResponseBody Object find(@Valid @ModelAttribute("userForm") UserForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getUserName())) return buildFormError("error#用户名不能为空.");
				if(CommonUtils.isNullEmpty(form.getEmail())) return buildFormError("error#邮箱不能为空.");
				User user = new User();
				user.setUserName(form.getUserName());
				user.setEmail(form.getEmail());
				List<User> list = service.selectByEntity(user);
				if(list != null && list.size() > 0){
					User findUser = list.get(0);
					String email = findUser.getEmail();
					if(email == null || "".equals(email)) return buildFormError("error#用户未绑定安全邮箱.");
					if(!form.getEmail().equals(email)) return buildFormError("error#输入的邮箱与安全邮箱不匹配.");
					
					String newPassword = RandomHelper.createPassword();
					findUser.setUserPwd(MD5.md5(newPassword));
					service.update(findUser);
					return sendMail(findUser.getUserName(), newPassword, email);
				}else
					return buildFormError("error#用户邮箱不匹配或未绑定安全邮箱.");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	/**
	 * 自动注册调用方法
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	private Object autoRegister(String ip, UserForm form) throws BaseException{
		try{
			while(true){
				String username = RandomHelper.createUserName();//自动生成用户名
				String password = RandomHelper.createPassword();
				User user = new User();
				user.setUserName(username);
				List<User> listUser = service.selectByEntity(user);
				if(listUser != null && listUser.size() > 0){
					continue;
				}else{
					user.setUserName(username);
					user.setUserPwd(MD5.md5(password));
					user.setSex(UserDefault.SEX.getValue());
					user.setClientIp(ip);
					user.setHeadIcon(CommonUtils.isNullEmpty(form.getIcon()) ? UserDefault.AVATAR.getValue() : form.getIcon());
					user.setGroupId(Integer.parseInt(UserDefault.GROUP.getValue()));//默认级别最低会员
					user.setBirthday(new Date());
					user.setExp(0);
					user.setBalance(Long.valueOf(UserDefault.BALANCE.getValue()));
					user.setPoint(0);
					user.setSourceFrom(CommonUtils.getSystem(form.getOs()));
					user.setOs(form.getOs());
					user.setOsVersion(form.getOsVersion());
					user.setImei(form.getDeviceId());
					user.setRegisterType(UserDefault.AT_REGISTER.getValue());
					service.insert(user);
					
					//记录用户信息
					UserDataItems userDataItems = new UserDataItems();
					userDataItems.setUserId(user.getId());
					userDataService.insert(userDataItems);
					
					JsonResult jsonResult = new JsonResult();
					JSONObject json = new JSONObject();
					json.put("userName", username);
					json.put("password", password);
					jsonResult.setData(json);
					//快速注册邮件提醒
					MessageCore core = new MessageCore();
					core.setAdapter(new MessageUserATRegisterTemplate()).transfer(new PracticVirtual(), user.getId(), username, password).send();
					return jsonResult;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	/**
	 * 发送邮件
	 * @param userName
	 * @param newPassword
	 * @param email
	 * @return
	 */
	private Object sendMail(String userName, String newPassword, String email){
		SendMail send = null;
		send = new SendMail("mail.9961.cn");
		send.setBody("游戏账号：" + userName + " 新密码为：" +  newPassword);
		send.setFrom("service@9961.cn");
		send.setNamePass("service@9961.cn","jiujiuleyou");
		send.setSmtpHost("mail.9961.cn");
		send.setSubject("游戏密码找回!");
		send.setTo(email);
		send.setNeedAuth(true);
		boolean isSend;
		try {
			isSend = send.sendout();
			if(isSend){
				return ResultHandler.bindResult("ok#密码已发送邮箱, 请注意查收.");
			}else 
				return ResultHandler.bindResult("error#邮件发送失败.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#邮件发送失败.");
		}
	}
	
	@RequestMapping(value = "/user/select", method = { POST })
	public @ResponseBody Object select(String token) throws Exception {
		try {
			String userId;
			try {
				userId = DES3Utils.decryptThreeDESECB(token);
			} catch (Exception e) {
				e.printStackTrace();
				return ResultHandler.bindResult("error#非法请求.");
			}
			User user = service.select(Integer.valueOf(userId));
			if (user == null) return buildFormError("error#用户不存在.");
			user.setHeadPath(user.getHeadIcon());
			user.setHeadIcon(CommonUtils.isNullEmpty(user.getHeadIcon()) ? "" : Constants.IMAGE_SITE_URL + user.getHeadIcon());
			//UserGameResult userGameResult = userGameService.selectUserAmount(user.getId());//查询用户余额
			//user.setBalance("0");
			//if(userGameResult != null) user.setBalance(userGameResult.getPlatAmount() + "");
			List<UserGameResult> list = userGameService.selectGameByUserId(user.getId());
			JSONArray array = null;
			if(list != null){
				array = new JSONArray();
				for(UserGameResult ug : list){
					JSONObject json = new JSONObject();
					json.put("gameId", ug.getGameId());
					json.put("gameName", ug.getGameName());
					json.put("gameIcon", Constants.IMAGE_SITE_URL + ug.getGameIcon());
					array.add(json);
				}
			}
			JsonResult jsonResult = new JsonResult();
			JsonConfig jsonConfig = new JsonConfig();  
			jsonConfig.setExcludes(new String[]{"userPwd"});
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			JSONObject data = JSONObject.fromObject(user, jsonConfig);
			if(array != null) data.put("gameList", array);
			jsonResult.setData(data);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/user/exists/valid", method = { POST })
    public @ResponseBody Object exists(String userName) throws Exception {
		try{
			if(userName == null || "".equals(userName.trim())) return buildFormError("error#请输入用户名.");
			User user = new User();
			user.setUserName(userName.trim());
			List<User> userList = service.selectByEntity(user);
			if(userList != null && userList.size() > 0) return buildFormError("error#用户已存在.");
			return ResultHandler.bindResult("ok#该用户名可用.");  
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }

	/**
	 * 游戏token验证
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/game/token/verify", method = { POST })
	public @ResponseBody Object verify(String token) throws Exception {
		try {
			if(CommonUtils.isNullEmpty(token)) return buildFormError("error#" + MessageConsValue.legalMessage);
			String tk = DESUtils.decode(token);
			String[] arrs = tk.split("-");
			if(arrs == null || arrs.length != 4) return buildFormError("error#" + MessageConsValue.legalMessage);
			long time = Long.valueOf(arrs[1]);
			if((System.currentTimeMillis() - time) > 5 * 60 * 1000) return buildFormError("error#token无效或已过期.");//计算超时时间
			JSONObject json = new JSONObject();
			json.put("uid", arrs[2]);
			json.put("registerTime", arrs[3]);
			JsonResult result = new JsonResult();
			result.setData(json);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		File file = new File("E://201505061349893.jpg");
		System.out.println(file.length());
	}
}

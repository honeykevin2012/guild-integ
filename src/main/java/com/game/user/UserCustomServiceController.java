package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;
import com.game.user.domain.User;
import com.game.user.domain.UserCustomService;
import com.game.user.domain.form.CustomOrderForm;
import com.game.user.domain.form.UserCustomServiceForm;
import com.game.user.domain.vo.FinalCustomServiceResult;
import com.game.user.domain.vo.MainBodyCustomService;
import com.game.user.domain.vo.ReplyBodyCustomService;
import com.game.user.persistence.service.UserCustomServiceService;
import com.game.user.persistence.service.UserService;

@Controller
public class UserCustomServiceController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserCustomServiceController.class);
	@Autowired
	private UserCustomServiceService service;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user/custom/service/creator", method = { POST })
    public @ResponseBody Object creator(@Valid @ModelAttribute("userCustomServiceForm") UserCustomServiceForm userCustomServiceForm, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String content =  CommonUtils.encodeContent(userCustomServiceForm.getContent());
				if(CommonUtils.isNullEmpty(userCustomServiceForm.getUserName())) return this.buildFormError("error#用户名不能为空.");
				if(CommonUtils.isNullEmpty(content)) return this.buildFormError("error#问题内容不能为空.");
				if(content.length() > 200) return this.buildFormError("error#内容输入请限制在150字以内.");
				
				if(userCustomServiceForm.getId() == null || userCustomServiceForm.getId() == 0){
					if(CommonUtils.isNullEmpty(userCustomServiceForm.getType())) return this.buildFormError("error#问题类型不能为空.");
					if(CommonUtils.isNullEmpty(userCustomServiceForm.getMobile())) return this.buildFormError("error#手机不能为空.");
					if(CommonUtils.isNullEmpty(userCustomServiceForm.getGameId())) return this.buildFormError("error#游戏编号不能为空.");
					if(CommonUtils.isNullEmpty(userCustomServiceForm.getGameServerId())) return this.buildFormError("error#服务器编号不能为空.");
					if(CommonUtils.isNullEmpty(userCustomServiceForm.getGameRoleName())) return this.buildFormError("error#游戏角色名不能为空.");
					
					User user = userService.selectCheckUserExistsByName(userCustomServiceForm.getUserName());
					if(user == null) return this.buildFormError("error#非法请求用户.");
					
					UserCustomService custom = new UserCustomService();
					
					CommonUtils.copyProperties(userCustomServiceForm, custom);
					custom.setUserId(user.getId());
					custom.setUserType("1");//玩家
					custom.setParentId(-1);
					custom.setContent(content);
					
					List<UserCustomService> list = service.selectByEntity(custom);
					if(list != null && list.size() > 0) return this.buildFormError("error#请不要重复提交问题, 谢谢合作.");
					
					service.insert(custom);
					return ResultHandler.bindResult("ok#问题提交成功.");
					
				}else if(userCustomServiceForm.getId() != null && userCustomServiceForm.getId() != 0){
					User user = userService.selectCheckUserExistsByName(userCustomServiceForm.getUserName());
					if(user == null) return this.buildFormError("error#非法请求用户.");
					
					UserCustomService custom = service.select(userCustomServiceForm.getId());
					if(custom == null) return this.buildFormError("error#非法提交问题.");//问题不存在
					UserCustomService addtion = new UserCustomService();
					CommonUtils.copyProperties(custom, addtion);
					
					addtion.setContent(userCustomServiceForm.getContent());
					addtion.setParentId(custom.getId());
					addtion.setUserId(user.getId());
					addtion.setContent(content);
					List<UserCustomService> list = service.selectByEntity(addtion);
					if(list != null && list.size() > 0) return this.buildFormError("error#请不要重复提交问题, 谢谢合作.");
					
					service.insert(addtion);
					return ResultHandler.bindResult("ok#问题提交成功.");
				}else{
					return this.buildFormError("error#非法提交问题.");//问题不存在
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/custom/service/list", method = { POST })
    public @ResponseBody Object list(@Valid @ModelAttribute("userCustomServiceForm") UserCustomServiceForm userCustomServiceForm,  BindingResult result, HttpServletRequest request) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
					if(CommonUtils.isNullEmpty(userCustomServiceForm.getUserName())) 
						return this.buildFormError("error#用户名不能为空.");
					if(userCustomServiceForm.getPn() == null || userCustomServiceForm.getPn() == 0) 
						return this.buildFormError("error#页码不能为空.");
					
					int pageSize = 50;
					PageQuery page = new PageQuery(request);
					page.setPageSize(pageSize);
					page.setPageNo(userCustomServiceForm.getPn());
					Map<String, Object> params = page.getParams();
					if(params == null) params = new HashMap<String, Object>();
					params.put("userName", userCustomServiceForm.getUserName());
					List<UserCustomService> list = service.selectCustomList(page);
					if(list == null || list.size() == 0) return this.buildFormError("error#没有提交问题记录.");
					FinalCustomServiceResult finalServiceResult = new FinalCustomServiceResult();
					finalServiceResult.setPn(userCustomServiceForm.getPn());
					finalServiceResult.setTotalPage((int)page.getTotalPages());
					for(UserCustomService custom : list){
						MainBodyCustomService mainBody = new MainBodyCustomService();
						mainBody.setContent(custom.getContent());
						mainBody.setDate(DateUtils.format(custom.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						mainBody.setId(custom.getId());
						String addtion = custom.getAddtion();
						String[] bodyArray = splitBody(addtion);
						if(bodyArray != null){
							for(String body : bodyArray){
								String[] items = splitBodyItems(body);
								if(items != null && items.length == 3){
									ReplyBodyCustomService bodyService = new ReplyBodyCustomService();
									String content = items[0];
									if(content.indexOf(',') != -1) 
										content = content.substring(1, content.length());
									bodyService.setContent(content);
									bodyService.setDate(items[1]);
									bodyService.setUserType(items[2]);
									mainBody.getReplyList().add(bodyService);
								}
							}
						}
						finalServiceResult.getAskList().add(mainBody);
					}
					JsonResult jsonResult = new JsonResult();
					jsonResult.setData(finalServiceResult);
					return jsonResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	private String[] splitBody(String addtion){
		if(addtion == null) return null;
		String[] replyBody = addtion.split(BODY_SPLIT);
		return replyBody;
	}
	private String[] splitBodyItems(String body){
		if(body == null) return null;
		String[] bodyItems = body.split(ITEMS_SPLIT);
		return bodyItems;
	}
	
	private static final String BODY_SPLIT = "\\|\\|\\|\\|";
	private static final String ITEMS_SPLIT = "####";
	
	
	@RequestMapping(value="/user/custom/order/creator", method = { POST })
    public @ResponseBody Object orderAsk(@Valid @ModelAttribute("customForm") CustomOrderForm form, BindingResult result) {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getContent())) return this.buildFormError("error#问题内容不能为空.");
				if(CommonUtils.isNullEmpty(form.getMobile())) return this.buildFormError("error#手机不能为空.");
				if(CommonUtils.isNullEmpty(form.getGameId())) return this.buildFormError("error#游戏编号不能为空.");
				if(CommonUtils.isNullEmpty(form.getGameServerId())) return this.buildFormError("error#服务器编号不能为空.");
				if(CommonUtils.isNullEmpty(form.getGameRoleName())) return this.buildFormError("error#游戏角色名不能为空.");
				if(CommonUtils.isNullEmpty(form.getUserName())) return this.buildFormError("error#用户名为空.");
				if(CommonUtils.isNullEmpty(form.getChargeTime())) return this.buildFormError("error#充值时间不能为空.");
				if(CommonUtils.isNullEmpty(form.getAmount())) return this.buildFormError("error#充值金额不能为空.");
				if(CommonUtils.isNullEmpty(form.getChargeType())) return this.buildFormError("error#充值类型不能为空.");
				if(CommonUtils.isNullEmpty(form.getOrderId())) return this.buildFormError("error#订单号不能为空.");
				
				User user = userService.selectCheckUserExistsByName(form.getUserName());
				if(user == null) return this.buildFormError("error#非法请求用户.");
				
				String content = CommonUtils.encodeContent(form.getContent());
				if(content.length() > 200) return this.buildFormError("error#问题长度在200字以内.");
				String chargeType = CommonUtils.encodeContent(form.getChargeType());
				StringBuilder sBuilder = new StringBuilder();
				sBuilder.append("订单号：").append(form.getOrderId()).append("; ");
				sBuilder.append("充值时间：").append(form.getChargeTime()).append("; ");
				sBuilder.append("充值方式：").append(chargeType).append("; ");
				sBuilder.append("充值金额：").append(form.getAmount()).append("; ");
				sBuilder.append("详细描述：").append(content).append(";");
				
				UserCustomService customService = new UserCustomService();
				CommonUtils.copyProperties(form, customService);
				customService.setContent(sBuilder.toString());
				customService.setUserId(user.getId());
				customService.setUserType("1");//玩家
				customService.setParentId(-1);
				customService.setType(form.getType());
				service.insert(customService);
				JsonResult jsonResult = new JsonResult();
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("signature", form.getSignature());
				jsonResult.setData(param);
				return jsonResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	public static void main(String[] args) {
		String s = "9999999999####2015-01-07 14:30:14####1||||,999999####2015-01-07 14:30:14####1||||,99999999####2015-01-07 14:30:14####1||||,99####2015-01-07 14:58:06####1||||";
		String[] replyBody = s.split(BODY_SPLIT);
		System.out.println(replyBody);
	}
}

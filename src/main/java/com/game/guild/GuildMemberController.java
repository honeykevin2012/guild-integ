package com.game.guild;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;
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

import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.MessageConsValue;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.security.DES3Utils;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.guild.domain.GuildHistoryMessage;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildMemberLevel;
import com.game.guild.domain.GuildMembers;
import com.game.guild.domain.MemberVO;
import com.game.guild.domain.form.GuildMemberListForm;
import com.game.guild.helper.ConstantKeys;
import com.game.guild.helper.GuildMessageHelper;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.guild.persistence.service.GuildMemberDonationService;
import com.game.guild.persistence.service.GuildMemberLevelService;
import com.game.guild.persistence.service.GuildMemberSigninService;
import com.game.guild.persistence.service.GuildMembersService;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserJoinGuildApplyTemplate;
import com.game.platform.message.MessageUserJoinGuildTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.user.domain.User;
import com.game.user.persistence.service.UserService;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class GuildMemberController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuildMemberController.class);

	@Autowired
	GuildMembersService guildMembersService;
	@Autowired
	GuildMemberLevelService guildMembersLevelService;
	@Autowired
	UserService userService;
	@Autowired
	GuildInfoService guildService;
	@Autowired
	GuildMemberDonationService donationService;
	@Autowired
	GuildMemberSigninService signinService;

	/**
	 * 验证当前用户 是否管理员或会长
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/admin/member", method = { POST })
	public @ResponseBody Object recommend(Integer userId, HttpServletRequest request) throws Exception {
		try {
			List<MemberVO> data = guildMembersService.selectAdminMember(userId);
			for (MemberVO vo : data) {
				vo.setIcon( CommonUtils.isNullEmpty(vo.getIcon()) ? "" : Constants.IMAGE_SITE_URL + vo.getIcon());
				vo.setGroupIcon( CommonUtils.isNullEmpty(vo.getGroupIcon()) ? "" : Constants.IMAGE_SITE_URL + vo.getGroupIcon());
				vo.setHeadIcon( CommonUtils.isNullEmpty(vo.getHeadIcon()) ? "" : Constants.IMAGE_SITE_URL + vo.getHeadIcon());
			}
			JsonResult result = new JsonResult();
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			result.setData(JSONArray.fromObject(data, config));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	/**
	 * 验证当前用户是否加入选中公会
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/member/valid", method = { POST })
	public @ResponseBody Object valid(String token, HttpServletRequest request) throws Exception {
		try {
			String t = null;
			try{
				t = DES3Utils.decryptThreeDESECB(token);//guildId-userId
			}catch(Exception e){
				return buildFormError("非法捐款请求");
			}
			String[] decrypts = t.split("-");
			if(decrypts == null || decrypts.length != 2) return buildFormError("非法请求");
			String guildId = decrypts[0];
			String userId = decrypts[1];
			GuildMembers member = new GuildMembers();
			member.setUserId(Integer.valueOf(userId));
			member.setGuildId(Integer.valueOf(guildId));
			GuildInfo guild = guildService.selectByUser(Integer.valueOf(userId), Integer.valueOf(guildId));
			if(guild == null) return buildFormError("暂未加入该公会");
			JsonResult json = new JsonResult();
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			json.setData(JSONObject.fromObject(guild, config));
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	/**
	 * 加入公会申请	需要登录
	 * @param token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/member/join", method = { POST })
	public @ResponseBody Object join(BaseForm form, HttpServletRequest request) throws Exception {
		try {
			String t = null;
			try{
				t = DES3Utils.decryptThreeDESECB(form.getToken());//guildId-userId
			}catch(Exception e){
				return buildFormError("error#非法请求.");
			}
			String[] decrypts = t.split("-");
			if(decrypts == null || decrypts.length != 2) return buildFormError("error#非法请求.");
			String guildId = decrypts[0];
			String userId = decrypts[1];
			if(!CommonUtils.isNumber(guildId)) return buildFormError("error#公会参数异常.");
			if(!CommonUtils.isNumber(userId) || "0".equals(userId.trim())) return buildFormError("error#用户参数异常.");
			GuildInfo validGuild = guildService.select(Integer.valueOf(guildId));
			if(validGuild == null) return buildFormError("error#该公会已解散或被关闭.");
			if(ConstantKeys.StatusClose.equals(validGuild.getStatus())) return buildFormError("error#该公会已解散或被关闭.");
			
			GuildMembers member = guildMembersService.selectByUserAndGuildId(Integer.valueOf(userId), Integer.valueOf(guildId));
			if(member != null){
				if(ConstantKeys.MEMBER_STATUS_YES.equals(member.getStatus())) return buildFormError("error#您已是该公会成员.");
				else if(ConstantKeys.MEMBER_STATUS_NO.equals(member.getStatus())) return buildFormError("error#公会管理员审批中, 请耐心等待.");
			}else{
				//判断是否创建过公会
				GuildInfo checkUser = new GuildInfo();
				checkUser.setCreateUserId(Integer.valueOf(userId));
				List<GuildInfo> userHasGuild = guildService.selectByEntity(checkUser);
				if(userHasGuild != null && !userHasGuild.isEmpty()) return buildFormError("error#已经创建了公会, 无法再次加入其他公会.");
				//判断是否已经加入 或 已经申请加入公会
				GuildMembers hasMember = new GuildMembers();
				hasMember.setUserId(Integer.valueOf(userId));
				List<GuildMembers> hasMemberList = guildMembersService.selectByEntity(hasMember);
				if(hasMemberList != null && !hasMemberList.isEmpty()){
					for(GuildMembers m : hasMemberList){//判断是否已经是公会正式成员
						if(ConstantKeys.MEMBER_STATUS_YES.equals(m.getStatus())) return buildFormError("error#已加入了公会, 不能再次加入其他公会.");
					}
					//用户申请数量过多
					if(hasMemberList.size() >= ConstantKeys.limitApplyQuantity) return buildFormError("error#您提交的申请已达上限, 最高可申请10个公会.");
				}
				GuildMembers entity = new GuildMembers();
				entity.setUserId(Integer.valueOf(userId));
				entity.setGuildId(Integer.valueOf(guildId));
				entity.setApplyTime(new Date());
				entity.setJoinTime(new Date());
				entity.setExp(0);
				entity.setStatus(ConstantKeys.MEMBER_STATUS_NO);
				entity.setPoint(0);
				entity.setIsAdmin(ConstantKeys.Normal);
				GuildMemberLevel mLevel = guildMembersLevelService.selectDefaultLevel();
				if(mLevel == null) entity.setLevel(1);
				else entity.setLevel(mLevel.getId());
				guildMembersService.insert(entity);
			}
			GuildInfo guild = guildService.select(Integer.valueOf(guildId));
			//DataEyeAgent.guildsReg(userId, userId,form.getOs(), guildId, guild.getName(), guild.getMemberCount()+"");
			
			//邮件通知
			List<GuildMembers> toUsers=guildMembersService.selectAdminByLevel(Integer.valueOf(guildId));
			for (GuildMembers gm : toUsers) {
				MessageCore core = new MessageCore();
				core.setAdapter(new MessageUserJoinGuildTemplate()).transfer(new PracticVirtual(), gm.getUserId(), userService.select(Integer.valueOf(userId)).getUserName()).send();	
			}
	
			
			JsonResult result = new JsonResult();
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			result.setData(JSONObject.fromObject(guild, config));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	/**
	 * 退出公会	需要登录
	 * @param token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/member/out", method = { POST })
	public @ResponseBody Object out(BaseForm form, HttpServletRequest request) throws Exception {
		try {
			if(CommonUtils.isNullEmpty(form.getData())) return buildFormError("error#" + MessageConsValue.legalMessage);
			Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
			if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
			boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
			if(!legal || !data.containsKey("userId") || !data.containsKey("guildId"))
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			String guildId = data.get("guildId").toString();
			String userId = data.get("userId").toString();
			
			if(!CommonUtils.isNumber(guildId)) return buildFormError("error#公会参数异常.");
			if(!CommonUtils.isNumber(userId)) return buildFormError("error#用户参数异常.");
			Integer uid = Integer.valueOf(userId);
			Integer gid = Integer.valueOf(guildId);
			GuildInfo guild = guildService.select(gid);
			if(guild == null) return buildFormError("error#非法请求.");
			GuildMembers member = guildMembersService.selectByUserAndGuildId(uid, gid);
			if(member == null) return buildFormError("error#非法请求.");
			if(ConstantKeys.President.equals(member.getIsAdmin())) return buildFormError("error#会长不能进行该操作.");
			if(ConstantKeys.MEMBER_STATUS_YES.equals(member.getStatus())){//经过审批的正式成员
				guild.setMemberCount(guild.getMemberCount() == 1 ? 1 : guild.getMemberCount() - 1);
				guildService.update(guild);
			}
			guildMembersService.deleteByUserId(uid);
			donationService.deleteByUserAndGuild(uid, gid);
			signinService.deleteByUserAndGuild(uid, gid);
			return ResultHandler.bindResult("ok#恭喜, 退出公会操作成功.");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/**
	 * 用户申请审批接口	需要登录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/member/apply", method = { POST })
	public @ResponseBody Object apply(@Valid @ModelAttribute("baseForm") BaseForm form, HttpServletRequest request) throws Exception {
		try {
			if(CommonUtils.isNullEmpty(form.getData())) return buildFormError("error#" + MessageConsValue.legalMessage);
			Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
			if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
			boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
			if(!legal || !data.containsKey("userId") || !data.containsKey("guildId") || !data.containsKey("ids"))
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			
			String guildId = data.get("guildId").toString();
			String userId = data.get("userId").toString();
			String id = data.get("ids").toString();
			if(CommonUtils.isNullEmpty(id) || !CommonUtils.isNumber(userId) || !CommonUtils.isNumber(guildId)) return buildFormError("error#" + MessageConsValue.legalMessage);
			GuildMembers member = guildMembersService.selectByUserAndGuildId(Integer.valueOf(userId), Integer.valueOf(guildId));
			if(member == null) BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			if(ConstantKeys.Normal.equals(member.getIsAdmin())) return buildFormError("error#当前角色不能进行审批.");//普通成员不能进行审批
			Integer[] ids = CommonUtils.convertArray(id);
			if(ids == null || ids.length == 0) BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			
			//List<GuildMembers> validMembers = guildMembersService.selectByUserIds(ids);
			//if(validMembers != null && validMembers.size() > 0) return buildFormError("error#当前所选用户已是其他公会成员.");
			//删除 除当前公会以外的所有申请中(status=0)的记录
			guildMembersService.deleteApplyUserExcludeCurrGuild(ids, Integer.valueOf(guildId));
			int result = guildMembersService.updateMemberApplyByIds(ids);
			//int result = guildMembersService.updateMemberApply(Integer.valueOf(id));
			if(result == 0) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			GuildInfo guild = guildService.select(Integer.valueOf(guildId));
			if(guild != null){
				guild.setMemberCount(guild.getMemberCount() + result);
				guildService.update(guild);
			}
			//审批通过邮件通知用户
			List<Integer> userIds = guildMembersService.selectUsersByIds(ids);
			for(Integer uid : userIds){
				if(uid == null || uid == 0) continue;
				MessageCore core = new MessageCore();
				core.setAdapter(new MessageUserJoinGuildApplyTemplate()).transfer(new PracticVirtual(), uid, guild.getName()).send();
				
				User user = userService.select(uid);
				if(user == null) continue;
				String userName = CommonUtils.isNullEmpty(user.getNickName()) ? user.getUserName() : user.getNickName();
				String content = String.format("欢迎  %s 加入公会, 希望对公会多多支持, 谢谢!", userName);
				GuildMessageHelper.saveMsg(GuildHistoryMessage.MsgTypeEnum.GUILD.getValue(), Integer.valueOf(guildId), user.getId(), user.getId(), content);
			}
			
			//for (Integer pid : ids) {
				//DataEyeAgent.guildsRegConfirm(member.getUserName(), userId,form.getOs(), guildId, guild.getName(), "guildMemberId="+pid, "", guild.getMemberCount()+"");
			//}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	/*
	 * 
	 * 未审批成员列表	需要登录
	 */
	@RequestMapping(value = "/guild/member/examine/list", method = { POST })
	public @ResponseBody Object memberList(@Valid @ModelAttribute("guildMemberListForm") GuildMemberListForm guildMemberListForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				PageQuery page = new PageQuery(request);
				page.setPageSize(Integer.valueOf(guildMemberListForm.getPs()));
				page.setPageNo(Integer.valueOf(guildMemberListForm.getPn()));
				Map<String, Object> params = page.getParams();
				params.put("guildId", guildMemberListForm.getGuildId());
				params.put("status", "0");
				page.setParams(params);
				List<GuildMembers> members = guildMembersService.selectMemberList(page);
				JSONArray membersJson = new JSONArray();
				for (GuildMembers n : members) {
					JSONObject member = new JSONObject();
					member.put("id", n.getId());
					member.put("userId", n.getUserId());
					member.put("name", n.getNickName());
					member.put("headIcon", CommonUtils.isNullEmpty(n.getHeadIcon()) ? "" : Constants.IMAGE_SITE_URL + n.getHeadIcon());
					member.put("levelName", n.getLevelName());
					member.put("point", n.getPoint());
					membersJson.add(member);
				}
				JsonResult jsonResult = new JsonResult();
				JSONObject json = new JSONObject();
				json.put("pn", page.getPageNo());
				json.put("totalPage", page.getTotalPages());
				json.put("members", membersJson);
				jsonResult.setData(json);
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

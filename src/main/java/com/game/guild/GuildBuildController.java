package com.game.guild;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.game.common.basics.security.DES3Utils;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildInfo.GuildDefault;
import com.game.guild.domain.GuildLevel;
import com.game.guild.domain.GuildMembers;
import com.game.guild.domain.GuildNotice;
import com.game.guild.domain.form.GuildBuildForm;
import com.game.guild.domain.form.GuildForm;
import com.game.guild.helper.ConstantKeys;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.guild.persistence.service.GuildLevelService;
import com.game.guild.persistence.service.GuildMemberDonationService;
import com.game.guild.persistence.service.GuildMemberSigninService;
import com.game.guild.persistence.service.GuildMembersService;
import com.game.guild.persistence.service.GuildNoticeService;
import com.game.init.SettingListener;
import com.game.init.setting.SettingAttribute;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserCreateGuildTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.platform.utils.Avatar;
import com.game.user.utils.AwardUserHelper;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class GuildBuildController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuildBuildController.class);

	@Autowired
	GuildInfoService guildService;
	@Autowired
	GuildLevelService levelService;
	@Autowired
	private GuildMembersService guildMembersService;
	@Autowired
	GuildNoticeService guildNoticeService;
	@Autowired
	GuildMemberDonationService donationService;
	@Autowired
	GuildMemberSigninService signinService;
	/**
	 * 创建公会	需要登录
	 * 
	 * @param guildForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/creator", method = { POST })
	public @ResponseBody
	Object guild(@Valid @ModelAttribute("guildForm") GuildForm guildForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(guildForm.getUserId() == null || guildForm.getUserId() == 0) 
					return buildFormError("error#" + MessageConsValue.legalMessage);
				GuildInfo guild = new GuildInfo();
				guild.setName(guildForm.getGuildName().trim());
				List<GuildInfo> exists = guildService.selectByEntity(guild);
				if (exists != null && exists.size() > 0)
					return buildFormError("error#该公会名称已经存在, 请更换其他名称.");
				
				GuildInfo checkUser = new GuildInfo();
				checkUser.setCreateUserId(guildForm.getUserId());
				List<GuildInfo> userHasGuild = guildService.selectByEntity(checkUser);
				if(userHasGuild != null && !userHasGuild.isEmpty()) return buildFormError("error#只能建立一个公会, 请解散原公会后再建立新的公会.");
				//判断用户是否加入了公会且为正式成员，若为正式成员， 不允许创建公会
				GuildMembers bean = new GuildMembers();
				bean.setUserId(guildForm.getUserId());
				List<GuildMembers> memberList = guildMembersService.selectByEntity(bean);
				if(memberList != null && !memberList.isEmpty()){
					for(GuildMembers m : memberList){
						if(ConstantKeys.StatusOk.equals(m.getStatus())) return buildFormError("error#您已加入了公会, 请退出或解散后再创建公会."); 
					}
				}
				
				guild.setCommend(0);
				guild.setCreateTime(new Date());
				guild.setCreateUserId(guildForm.getUserId());
				guild.setCurrency(0l);
				guild.setDiscountId(1);
				guild.setExp(0);
				guild.setIcon((guildForm.getIcon() == null || "".equals(guildForm.getIcon())) ? Avatar.getGuildAvatar() : guildForm.getIcon());
				GuildLevel level = levelService.selectDefaultLevel();
				if(level == null) guild.setLevel(Integer.parseInt(GuildDefault.Level.getValue()));//获取公会默认级别
				else guild.setLevel(level.getId());//获取公会默认级别
				guild.setMemberCount(1);
				guild.setRemark(guildForm.getRemark());
				guild.setSlogan(guildForm.getSlogan());
				guild.setStatus(GuildDefault.Status.getValue());
				guild.setVersion(0);
				guildService.insert(guild);
				//创建公会后需要删除用户所有加入公会申请记录
				guildMembersService.deleteByUserId(guildForm.getUserId());
				//创建会长成员记录
				GuildMembers member = new GuildMembers();
				member.setGuildId(guild.getId());
				member.setUserId(guildForm.getUserId());
				member.setLevel(1);
				member.setJoinTime(new Date());
				member.setApplyTime(new Date());
				member.setExp(0);
				member.setPoint(0);
				member.setIsAdmin(ConstantKeys.President);// 会长
				member.setStatus(ConstantKeys.MEMBER_STATUS_YES);
				guildMembersService.insert(member);
				GuildInfo select = guildService.select(guild.getId());
				select.setNickName((select.getNickName() == null || "".equals(select.getNickName()) ? select.getUserName() : select.getNickName()));
				select.setIcon(Constants.IMAGE_SITE_URL + select.getIcon());
				JsonResult json = new JsonResult();
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "iconPath", "giftCount","nextExp","games"});
				config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
				json.setData(JSONObject.fromObject(select, config));
				
				//公会默认公告
				String title = SettingListener.getValue("guild_notice_title_default");
				String content = SettingListener.getValue("guild_notice_content_default");
				GuildNotice notice = new GuildNotice();
				notice.setTitle(title);
				notice.setContent(content);
				notice.setGuildId(guild.getId());
				guildNoticeService.insert(notice);
				//创建公会奖励积分
				AwardUserHelper.awardUser(SettingAttribute.SETTING_CREATE_GUILD, guildForm.getUserId());
				//发送系统奖励邮件
				MessageCore core = new MessageCore();
				core.setAdapter(new MessageUserCreateGuildTemplate()).transfer(new PracticVirtual(), guildForm.getUserId()).send();
				//日志
				//DataEyeAgent.guildCreate(guild.getCreateUserId()+"",guildForm.getOs(), guild.getCreateUserId()+"", guild.getId()+"", guild.getName());
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#公会创建失败.");
		}
	}

	/**
	 * 解散公会	需要登录
	 * 
	 * @param token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/destroy", method = { POST })
	public @ResponseBody
	Object destroy(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if(!legal || !data.containsKey("userId") || !data.containsKey("guildId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				String guildId = data.get("guildId").toString();
				String userId = data.get("userId").toString();
				if (!CommonUtils.isNumber(userId) || !CommonUtils.isNumber(guildId))
					return buildFormError("error#" + MessageConsValue.legalMessage);
				GuildInfo guild = guildService.selectByUser(Integer.valueOf(userId), Integer.valueOf(guildId));
				if (guild == null || !ConstantKeys.President.equals(guild.getJoined()))
					return buildFormError("error#" + MessageConsValue.legalMessage);// 会长标识为2,只有会长可以解散公会
				int effectRows = guildService.updateGuildDestroy(Integer.valueOf(guildId));
				if (effectRows == 1){
					guildMembersService.deleteByGuildId(Integer.valueOf(guildId));
					donationService.deleteByGuildId(Integer.valueOf(guildId));
					signinService.deleteByGuildId(Integer.valueOf(guildId));
					//DataEyeAgent.guildsDissolve(userId, userId, form.getOs(),guildId, guild.getName());
					return ResultHandler.bindResult("ok#解散公会操作成功.");
				}else{
					return buildFormError("error#解散公会操作失败.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + MessageConsValue.legalMessage);
		}
	}

	/*
	 * 
	 * 公会建设	需要登录
	 */
	@RequestMapping(value = "/guild/guildBuilding", method = { POST })
	public @ResponseBody Object guildBuilding(@Valid @ModelAttribute("guildBuildForm") GuildBuildForm guildBuildForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				// memberid
				String params = null;
				try {
					params = DES3Utils.decryptThreeDESECB(guildBuildForm.getToken());
				} catch (Exception e) {
					e.printStackTrace();
					return ResultHandler.bindResult("error#非法请求.");
				}
				if (params != null) {
					String[] ids = params.split("-");
					GuildMembers entity = new GuildMembers();
					entity.setGuildId(Integer.valueOf(ids[0]));
					entity.setUserId(Integer.valueOf(ids[1]));
					List<GuildMembers> list = guildMembersService.selectByEntity(entity);
					if(list == null || list.isEmpty()) return ResultHandler.bindResult("error#该成员不存在."); 
					GuildMembers fromUser = list.get(0);
					if (ConstantKeys.Admin.equals(fromUser.getIsAdmin()) || ConstantKeys.President.equals(fromUser.getIsAdmin())) {
						GuildInfo guild = guildService.select(Integer.valueOf(ids[0]));
						if(guild == null) return ResultHandler.bindResult("error#公会信息异常."); 
						if(!CommonUtils.isNullEmpty(guildBuildForm.getIcon())){
							guild.setIcon(guildBuildForm.getIcon());
						}else{
							if(CommonUtils.isNullEmpty(guild.getIcon())){
								guild.setIcon(GuildDefault.Icon.getValue());
							}
						}
						guild.setSlogan(guildBuildForm.getSlogan());
						guild.setRemark(guildBuildForm.getRemark());
						guildService.updateGuildBuilding(guild);
						//日志
						//DataEyeAgent.guildsNameChange(fromUser.getUserName(), fromUser.getUserId()+"",guildBuildForm.getOs(), fromUser.getGuildId()+"", fromUser.getGuildName(), fromUser.getGuildName());
						return ResultHandler.bindResult("ok#修改成功.");
					} else {
						return ResultHandler.bindResult("error#沒有操作权限.");
					}
				} else {
					return ResultHandler.bindResult("error#非法请求.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

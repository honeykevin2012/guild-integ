package com.game.guild;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;
import java.util.List;

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
import com.game.common.basics.security.DES3Utils;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildLevel;
import com.game.guild.domain.GuildMemberLevel;
import com.game.guild.domain.GuildMemberSignin;
import com.game.guild.domain.GuildMemberSigninForm;
import com.game.guild.domain.GuildMembers;
import com.game.guild.helper.ExperienceHelper;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.guild.persistence.service.GuildLevelService;
import com.game.guild.persistence.service.GuildMemberLevelService;
import com.game.guild.persistence.service.GuildMemberSigninService;
import com.game.guild.persistence.service.GuildMembersService;

@Controller
public class GuildMemberSigninController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuildMemberSigninController.class);
	@Autowired
	private GuildMemberSigninService service;
	@Autowired
	private GuildInfoService guildService;
	@Autowired
	private GuildMembersService membersService;
	@Autowired
	private GuildLevelService levelService;
	@Autowired
	private GuildMemberLevelService memberLevelService;
	/**
	 * 公会成员签到接口	需要登录
	 * @param guildMemberSignin
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/guild/member/signin", method = { POST })
    public @ResponseBody Object signin(@Valid @ModelAttribute("guildMemberSignin") GuildMemberSigninForm guildMemberSignin, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String token = null;
				try{
					token = DES3Utils.decryptThreeDESECB(guildMemberSignin.getToken());//解析加密token 时间戳-用户id-地址id-商品编号1,商品编号2,...商品编号n-公会ID
				}catch(Exception e){
					return buildFormError("非法签到请求");
				}
				String[] decrypts = token.split("-");
				if(decrypts == null || decrypts.length != 2) return buildFormError("非法签到请求");
				String guildId = decrypts[0];
				String userId = decrypts[1];
				if(!CommonUtils.isNumber(guildId)) return buildFormError("公会参数异常.");
				if(!CommonUtils.isNumber(userId)) return buildFormError("用户参数异常.");
				GuildInfo guild = guildService.select(Integer.valueOf(guildId));
				if(guild == null) return buildFormError("公会参数错误.");
				GuildMembers member = new GuildMembers();
				member.setUserId(Integer.valueOf(userId));
				member.setGuildId(Integer.valueOf(guildId));
				List<GuildMembers> memberList = membersService.selectByEntity(member);
				if(memberList == null || memberList.isEmpty()) {
					return buildFormError("您不是该公会成员.");
				}else{
					GuildMembers m = memberList.get(0);
					if("0".equals(m.getStatus())) return buildFormError("error#还不是该公会正式成员, 请等待管理员审批后操作.");
				}
				GuildMemberSignin signin = new GuildMemberSignin();
				signin.setGuildId(Integer.valueOf(guildId));
				signin.setUserId(Integer.valueOf(userId));
				String date = DateUtils.format(new Date(), "yyyy-MM-dd");
				signin.setQueryDate(date);
				List<GuildMemberSignin> list = service.selectIsSignin(signin);
				if(list == null || list.isEmpty()) {//用户没有签到
					service.insert(signin);//存储签到记录
					int experi = ExperienceHelper.getSigninExp();
					guild.setExp(guild.getExp() + experi);//更新公会经验值
					//需要判断公会等级  当经验达到临界值时，需要更新公会等级
					GuildLevel guildLevel = levelService.selectNextLevel(guild.getExp());
					if(guildLevel != null){
						if(guild.getExp() >= guildLevel.getExp()){//已经达到下一等级经验阀值，需要更新公会等级为下一等级
							guild.setLevel(guildLevel.getId());
						}
					}
					guildService.update(guild);
					GuildMembers guildMember = membersService.selectByUserAndGuildId(Integer.valueOf(userId), Integer.valueOf(guildId));
					if(guildMember != null){
						guildMember.setExp(guildMember.getExp() + experi);//更新用户贡献值
						guildMember.setPoint(guildMember.getPoint() + experi);//更新用户贡献值
						GuildMemberLevel memberLevel = memberLevelService.selectNextLevel(guildMember.getExp());//公会成员下一等级
						if(memberLevel != null){//获取下一级别
							if(guildMember.getExp() >= memberLevel.getExp()){//当前用户经验大于等于下一级别经验，说明可以升级到下一级别
								guildMember.setLevel(memberLevel.getId());
							}
						}
						membersService.update(guildMember);//更新用户贡献值和等级信息
						//DataEyeAgent.guildsGameSign(guildMember.getUserName(), userId,guildMemberSignin.getOs(), guildId, guild.getName(), experi+"");
					}
				}else{
					return buildFormError("已经签到过了, 不能重复签到.");
				}
				//日志
				//DataEyeAgent.pageEvent(userId, guildMemberSignin.getDeviceId(), guildMemberSignin.getDeviceId(), guildMemberSignin.getOs(), guildMemberSignin.getDeviceName(), guildMemberSignin.getIp(), "", guildMemberSignin.getOsVersion(), guildMemberSignin.getScreenResolution(), PageEventEnum.SIGN);
				return ResultHandler.bindResult("ok#签到成功.");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value = "/guild/member/signin/count", method = { POST })
	public @ResponseBody Object signinCount(Integer guildId, HttpServletRequest request) throws Exception {
		try {
			int count = service.selectSigninCount(guildId);
			JsonResult json = new JsonResult();
			json.setData(count);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

package com.game.guild;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

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
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.security.DES3Utils;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.games.transfer.SyncRequestGameHelper;
import com.game.guild.domain.GuildHistoryMessage;
import com.game.guild.domain.GuildMemberDonation;
import com.game.guild.domain.GuildMembers;
import com.game.guild.helper.GuildMessageHelper;
import com.game.guild.persistence.service.GuildMemberDonationService;
import com.game.guild.persistence.service.GuildMembersService;
import com.game.user.domain.User;
import com.game.user.domain.UserGame;
import com.game.user.persistence.service.UserGameService;
import com.game.user.persistence.service.UserService;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class GuildMemberDonationController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuildMemberDonationController.class);
	@Autowired
	private GuildMembersService membersService;
	@Autowired
	private UserService userService;
	@Autowired
	private GuildMemberDonationService donationService;
	@Autowired
	private UserGameService userGameService;
	
	private static final String itemSepartor = "\\#\\*\\@\\*\\#";
	/**
	 * 公会成员捐款接口	需要登录
	 * @param guildMemberSignin
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/guild/member/dogift", method = { POST })
    public @ResponseBody Object donation(@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if(!legal || !data.containsKey("userId") || !data.containsKey("guildId") || (!data.containsKey("gameDeducts") && !data.containsKey("walletAmount")))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				String guildId = data.get("guildId").toString();
				String userId = data.get("userId").toString();
				boolean hasWallet = data.containsKey("walletAmount");
				Object temp = data.get("walletAmount");
				String wallet = Constants.COMMON_ZERO;
				if(hasWallet && temp != null) wallet = temp.toString();
				boolean isNumber = CommonUtils.isDouble(wallet) || CommonUtils.isNumber(wallet);
				User user = userService.select(Integer.parseInt(userId));
				if(user == null) return buildFormError("非法用户.");
				if(!"".equals(wallet) && !Constants.COMMON_ZERO.equals(wallet) && isNumber){//从钱包捐款
					long walletAmount = Double.valueOf(wallet).longValue();
					if(user.getBalance() < walletAmount) return buildFormError("余额不足.");
					if(walletAmount == 0) return buildFormError("请输入有效金额.");
					GuildMemberDonation donation = new GuildMemberDonation();
					donation.setUserId(Integer.valueOf(userId));
					donation.setGuildId(Integer.valueOf(guildId));
					donation.setGameId(null);
					donation.setServerId(null);
					donation.setRoleId(null);
					donation.setAmount(walletAmount);
					donation.setGameName(null);
					donation.setServerName(null);
					donation.setRoleName(null);
					Object resp = membersService.updateDonatinMember(donation, true);
					
					//捐款历史消息
					String userName = CommonUtils.isNullEmpty(user.getNickName()) ? user.getUserName() : user.getNickName();
					String content = String.format("用户 %s 向公会捐款 %s N币, 感谢对公会的支持.", userName , walletAmount);
					GuildMessageHelper.saveMsg(GuildHistoryMessage.MsgTypeEnum.GUILD.getValue(), Integer.valueOf(guildId), user.getId(), user.getId(), content);
					return resp;
				}else {//从游戏捐款
					Object append = data.get("gameDeducts");
					if(append == null) return buildFormError("无效游戏捐款额度.");
					String deducts = data.get("gameDeducts").toString();
					String[] items = deducts.split(itemSepartor);
					if(items == null || items.length != 4) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
					
					Integer gameId = Integer.parseInt(items[0]);
					String serverId = items[1];
					String roleId = items[2];
					Long platAmount = Double.valueOf(items[3]).longValue();
					
					if(!CommonUtils.isNumber(guildId)) return buildFormError("公会参数异常.");
					if(!CommonUtils.isNumber(userId)) return buildFormError("用户参数异常.");
					if(platAmount == null || platAmount <= 0) return buildFormError("无效捐款额度.");
					
					GuildMembers member = new GuildMembers();
					member.setUserId(Integer.valueOf(userId));
					member.setGuildId(Integer.valueOf(guildId));
					List<GuildMembers> memberList = membersService.selectByEntity(member);
					if(memberList != null && !memberList.isEmpty()) {
						GuildMembers m = memberList.get(0);
						if("0".equals(m.getStatus())) return buildFormError("error#还不是该公会正式成员, 请等待管理员审批后操作.");
					}
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("userId", userId);
					map.put("gameId", gameId + "");
					map.put("serverId", serverId);
					map.put("roleId", roleId);
					UserGame userGame = userGameService.selectByUserServRole(map);//查询用户所选捐款游戏是否正确
					if(userGame == null) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
					
					//
					//HTTP请求游戏扣除金币
					long gameAmount = Double.valueOf(platAmount/userGame.getExchangeDivide()).longValue();
					String response = SyncRequestGameHelper.reduce(Integer.valueOf(userId), gameId, serverId, roleId, gameAmount);
					JSONObject json = JSONObject.fromObject(response);
					logger.info("待扣款游戏信息：" + userGame.toString());
					logger.info("游戏扣款结果：" + json.toString());
					if(!"1".equals(json.get("state"))) return BuildFormErrorUtils.buildFormError("游戏扣款失败.");
					//
					userGame.setAmount(userGame.getAmount() - gameAmount);
					userGameService.update(userGame);
					
					GuildMemberDonation donation = new GuildMemberDonation();
					donation.setUserId(Integer.valueOf(userId));
					donation.setGuildId(Integer.valueOf(guildId));
					donation.setGameId(gameId);
					donation.setServerId(serverId);
					donation.setRoleId(roleId);
					donation.setAmount(platAmount);
					donation.setGameName(userGame.getGameName());
					donation.setServerName(userGame.getServerName());
					donation.setRoleName(userGame.getRoleName());
					Object resp = membersService.updateDonatinMember(donation, false);
					
					String userName = CommonUtils.isNullEmpty(user.getNickName()) ? user.getUserName() : user.getNickName();
					String content = String.format("用户 %s 向公会捐款 %s N币, 感谢对公会的支持.", userName , platAmount);
					GuildMessageHelper.saveMsg(GuildHistoryMessage.MsgTypeEnum.GUILD.getValue(), Integer.valueOf(guildId), user.getId(), user.getId(), content);
					return resp;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#捐款操作失败, 请稍候重试.");
		}
    }
	
	/**
	 * 个人捐款记录	需要登录
	 * @param token
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/guild/member/donation", method = { POST })
    public @ResponseBody Object donationList(String token, Integer pn, Integer ps, HttpServletRequest request) throws Exception {
		try{
			String t = null;
			try{
				t = DES3Utils.decryptThreeDESECB(token);//解析加密token 公会id-用户id
			}catch(Exception e){
				return buildFormError("非法请求");
			}
			PageQuery page = new PageQuery(request);
			page.setPageSize(ps);
			page.setPageNo(pn);
			Map<String, Object> params = page.getParams();
			String[] decrypts = t.split("-");
			if(decrypts == null || decrypts.length != 2) return buildFormError("非法请求");
			String guildId = decrypts[0];
			String userId = decrypts[1];
			params.put("guildId", guildId);
			params.put("userId", userId);
			page.setParams(params);
			if(!CommonUtils.isNumber(guildId)) return buildFormError("公会参数异常.");
			if(!CommonUtils.isNumber(userId)) return buildFormError("用户参数异常.");
			List<GuildMemberDonation> list = donationService.selectUserDonation(page);
			JsonResult result = new JsonResult();
			JSONObject json = new JSONObject();
			json.put("pn", page.getPageNo());
			json.put("totalPage", page.getTotalPages());
			json.put("donations", list);
			result.setData(json);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value="/guild/allmember/donation", method = { POST })
    public @ResponseBody Object allDonation(String token, Integer pn, Integer ps, HttpServletRequest request) throws Exception {
		try{
			String t = null;
			try{
				t = DES3Utils.decryptThreeDESECB(token);//解析加密token 公会id-用户id
			}catch(Exception e){
				return buildFormError("非法请求");
			}
			PageQuery page = new PageQuery(request);
			page.setPageSize(ps);
			page.setPageNo(pn);
			Map<String, Object> params = page.getParams();
			//String[] decrypts = t.split("-");
			//if(decrypts == null || decrypts.length != 2) return buildFormError("非法请求");
			String guildId = t;
			params.put("guildId", guildId);
			page.setParams(params);
			if(!CommonUtils.isNumber(guildId)) return buildFormError("公会参数异常.");
			List<GuildMemberDonation> list = donationService.selectUserDonation(page);
			JsonResult result = new JsonResult();
			JSONObject json = new JSONObject();
			json.put("pn", page.getPageNo());
			json.put("totalPage", page.getTotalPages());
			json.put("donations", list);
			result.setData(json);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
}

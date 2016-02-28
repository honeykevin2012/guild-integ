package com.game.guild;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

import com.game.common.BaseForm;
import com.game.common.MessageConsValue;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.guild.domain.RedPacketInfo;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.guild.persistence.service.RedPacketInfoService;
import com.game.guild.persistence.service.RedPacketItemService;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class RedPacketController extends BaseController {
	private static final Logger logger = Logger.getLogger(RedPacketController.class);

	@Autowired
	GuildInfoService guildService;
	@Autowired
	RedPacketInfoService redPacketInfoService;
	@Autowired
	RedPacketItemService redPacketItemService;
	private static final String itemSepartor = "\\#\\*\\@\\*\\#";

	/**
	 * 创建红包
	 * @param form
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/redPacket/add", method = { POST })
	public @ResponseBody
	Object add(@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());// 解析data中的请求参数，转化成key
																						// value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if (!legal || !data.containsKey("userId") || !data.containsKey("guildId") || (!data.containsKey("name") && !data.containsKey("totalMoney") && !data.containsKey("totalPeople") && !data.containsKey("moneyFrom"))) {
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				}
				String userId = data.get("userId").toString();
				String guildId = data.get("guildId").toString();
				String name = data.get("name").toString();
				String totalMoney = data.get("totalMoney").toString();
				String totalPeople = data.get("totalPeople").toString();
				String moneyFrom = data.get("moneyFrom").toString();
				if (Long.valueOf(totalMoney) < Long.valueOf(totalPeople)) {
					return buildFormError("error#红包总金额需要大于总人数.");
				}
				Map<String, String> extMap = new HashMap<String, String>();
				if ("3".equals(moneyFrom)) {
					if (!data.containsKey("userId")) {
						return buildFormError("error#请输入游戏兑换信息.");
					}
					// 个人游戏
					String deducts = data.get("gameDeducts").toString();
					String[] items = deducts.split(itemSepartor);
					if (items == null || items.length != 4)
						return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);

					Integer gameId = Integer.parseInt(items[0]);
					String serverId = items[1];
					String roleId = items[2];
					Long platAmount = Double.valueOf(items[3]).longValue();

					if (!CommonUtils.isNumber(guildId))
						return buildFormError("公会参数异常.");
					if (!CommonUtils.isNumber(userId))
						return buildFormError("用户参数异常.");
					if (platAmount == null || platAmount <= 0)
						return buildFormError("无效捐款额度.");

					extMap.put("userId", userId);
					extMap.put("gameId", gameId + "");
					extMap.put("serverId", serverId);
					extMap.put("roleId", roleId);
				}
				//记录历史消息
				
				RedPacketInfo info = new RedPacketInfo();
				info.setName(name);
				info.setUserId(Integer.valueOf(userId));
				info.setGuildId(Integer.valueOf(guildId));
				info.setTotalMoney(Long.valueOf(totalMoney));
				info.setTotalPeople(Integer.valueOf(totalPeople));
				Object resultData = redPacketInfoService.insertRedPacket(info, moneyFrom, extMap);
				return resultData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#红包创建失败.");
		}
	}

	/**
	 * 领取红包
	 * @param form
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/redPacket/receive", method = { POST })
	public @ResponseBody
	Object receive(@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());// 解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if (!legal || !data.containsKey("userId") || !data.containsKey("guildId")) {
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				}

				String userId = data.get("userId").toString();
				String guildId = data.get("guildId").toString();
				
				Object resultData;
				try {
					resultData = redPacketInfoService.updateRecieveRedPacket(Integer.valueOf(userId),Integer.valueOf(guildId));
					return resultData;
				} catch (Exception e) {
					JsonResult jsonResult = new JsonResult();
					JSONObject obj = new JSONObject();
					obj.put("redPacket", 1);
					jsonResult.setMsg("再试一次");
					jsonResult.setData(obj);
					return jsonResult;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#红包领取失败.");
		}
	}
	
	/**
	 * 红包列表
	 * @param form
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/redPacket/list", method = { POST })
	public @ResponseBody
	Object list(Integer guildId,String pn ,String ps,HttpServletRequest request) throws Exception {
		try {
				PageQuery page = new PageQuery(request);
				page.setPageSize(Integer.valueOf(ps));
				page.setPageNo(Integer.valueOf(pn));
				Map<String, Object> params = page.getParams();
				params.put("guildId",guildId);
				page.setParams(params);
				List<RedPacketInfo> list = redPacketInfoService.query(page);
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "id"});
				config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				JsonResult jsonResult = new JsonResult();
				JSONObject json = new JSONObject();
				json.put("pn", page.getPageNo());
				json.put("totalPage", page.getTotalPages());
				json.put("packets", JSONArray.fromObject(list, config));
				jsonResult.setData(json);
				return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#红包列表失败.");
		}
	}
}

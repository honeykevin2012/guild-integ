package com.game.guild;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
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
import com.game.common.utility.DateUtils;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildItem;
import com.game.guild.domain.GuildItemsStock;
import com.game.guild.domain.GuildMembers;
import com.game.guild.domain.GuildNotice;
import com.game.guild.domain.form.GiftListForm;
import com.game.guild.domain.form.GuildMemberListForm;
import com.game.guild.domain.form.GuildNoticeForm;
import com.game.guild.domain.form.GuildStockForm;
import com.game.guild.domain.form.GuildStockMgrListForm;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.guild.persistence.service.GuildItemService;
import com.game.guild.persistence.service.GuildItemsStockService;
import com.game.guild.persistence.service.GuildMembersService;
import com.game.guild.persistence.service.GuildNoticeService;
import com.game.platform.domain.PlatformGameApp;
import com.game.platform.persistence.service.PlatformGameAppService;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class GuildManagerController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuildManagerController.class);
	@Autowired
	GuildInfoService guildInfoService;
	@Autowired
	PlatformGameAppService platformGameAppService;
	@Autowired
	GuildItemsStockService guildStockService;
	@Autowired
	GuildItemService guildItemService;
	@Autowired
	GuildNoticeService guildNoticeService;
	@Autowired
	GuildMembersService guildMembersService;

	/**
	 * 公会游戏入驻或取消入驻
	 * 
	 * @param token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guild/join/game", method = { POST })
	public @ResponseBody
	Object joinGames(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String token = form.getToken();
				if (token == null) {
					return buildFormError("error#token不能为空.");
				}
				String t = null;
				try {
					t = DES3Utils.decryptThreeDESECB(token);// guildId-userId
				} catch (Exception e) {
					return buildFormError("非法请求");
				}
				String[] decrypts = t.split("-");
				if (decrypts == null || decrypts.length != 3)
					return buildFormError("非法请求");
				String guildId = decrypts[0];
				String code = decrypts[1];
				String status = decrypts[2];// 0取消入驻 1入驻
				if (!CommonUtils.isNumber(guildId))
					return buildFormError("公会参数异常.");
				if (!CommonUtils.isNumber(code))
					return buildFormError("游戏参数异常.");
				if ("0".equals(status))
					guildInfoService.deleteJoinGames(guildId, code);// 取消入驻
				if ("1".equals(status))
					guildInfoService.insertJoinGames(guildId, code);// 入驻
				JsonResult json = new JsonResult();
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/**
	 * 公会入驻游戏列表（包含未入驻的游戏全集）
	 * 
	 * @param form
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/platform/game/join/list", method = { POST })
	public @ResponseBody
	Object list(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, Integer guildId, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				List<PlatformGameApp> list = platformGameAppService.selectJoinGames(guildId);
				if(list==null||list.isEmpty()){
					return buildFormError("error#没有数据");
				}
				for (PlatformGameApp app : list) {
					app.setIcon(Constants.IMAGE_SITE_URL + app.getIcon());
				}
				JsonResult data = new JsonResult();
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "comments", "consumeTopList","shotList","downloadUrl","downloadIosUrl","trend","expend","gameAmountQueryUrl","screenshot","gameAmountQueryUrl","packageSize","exchangeDivide","exchange"});
				config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				data.setData(JSONArray.fromObject(list,config));
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/guild/itemStock", method = { POST })
	public @ResponseBody Object itemStock(@Valid @ModelAttribute("guildStockForm") GuildStockForm guildStockForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				PageQuery page = new PageQuery(request);
				page.setPageSize(Integer.valueOf(guildStockForm.getPs()));
				page.setPageNo(Integer.valueOf(guildStockForm.getPn()));
				Map<String, Object> params = page.getParams();
				params.put("guildId", guildStockForm.getGuildId());
				page.setParams(params);
				List<GuildItem> list = guildItemService.query(page);
				if (list == null || list.isEmpty()) return buildFormError("error#没有数据");
				
				JSONArray items = new JSONArray();
				for (GuildItem it : list) {
					JSONObject item = new JSONObject();
					item.put("id", it.getId());
					item.put("name", it.getName());
					item.put("count", it.getCount());
					item.put("gived", it.getGivedQuantity());
					item.put("left", it.getCount());
					item.put("isVirtual", it.getIsVirtual());
					item.put("url", "/product/item/" + it.getTypeId());
					item.put("productPhoto", Constants.IMAGE_SITE_URL + it.getProductPhoto());
					items.add(item);
				}
				JsonResult jsonResult = new JsonResult();
				JSONObject json = new JSONObject();
				json.put("pn", page.getPageNo());
				json.put("totalPage", page.getTotalPages());
				json.put("items", items);
				jsonResult.setData(json);
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * 
	 * 公会公告列表
	 */
	@RequestMapping(value = "/guild/notice/list", method = { POST })
	public @ResponseBody
	Object noticeList(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, Integer guildId, HttpServletRequest request) throws Exception {
		try {
			// 公会
			if (guildId == null) {
				return ResultHandler.bindResult("error#公会id不能为空.");
			}

			List<GuildNotice> notices = guildNoticeService.selectByGuild(guildId);
			if(notices==null||notices.isEmpty()){
				return buildFormError("error#没有公告");
			}
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "guildId" });
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JsonResult json = new JsonResult();
			json.setData(JSONArray.fromObject(notices,config));
			return json;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * 
	 * 公会公告编辑列表
	 */
	@RequestMapping(value = "/guild/notice/editList", method = { POST })
	public @ResponseBody
	Object editList(@Valid @ModelAttribute("guildMemberListForm") GuildMemberListForm guildMemberListForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				PageQuery page = new PageQuery(request);
				page.setPageSize(Integer.valueOf(guildMemberListForm.getPs()));
				page.setPageNo(Integer.valueOf(guildMemberListForm.getPn()));
				Map<String, Object> params = page.getParams();
				params.put("guildId", guildMemberListForm.getGuildId());
				page.setParams(params);
				List<GuildNotice> notices = guildNoticeService.selectEditList(page);
				if(notices==null||notices.isEmpty()){
					return buildFormError("error#没有公告");
				}
				JSONArray noticesJson = new JSONArray();
				for (GuildNotice n : notices) {
					JSONObject notice = new JSONObject();
					notice.put("id", n.getId());
					notice.put("title", n.getTitle());
					notice.put("content", n.getContent());
					notice.put("createTime", DateUtils.format(n.getCreateTime(), "yyyy-MM-dd"));
					noticesJson.add(notice);
				}
				JsonResult jsonResult = new JsonResult();
				JSONObject json = new JSONObject();
				json.put("pn", page.getPageNo());
				json.put("totalPage", page.getTotalPages());
				json.put("notices", noticesJson);
				jsonResult.setData(json);
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * 
	 * 公会公告查看
	 */
	@RequestMapping(value = "/guild/notice/select", method = { POST })
	public @ResponseBody
	Object noticeSelect(Integer id, HttpServletRequest request) throws Exception {
		try {
			// 公会
			if (id == null) {
				return ResultHandler.bindResult("error#公告id不能为空.");
			}

			GuildNotice n = guildNoticeService.select(id);
			if (n == null) {
				return ResultHandler.bindResult("error#公告不存在.");
			}
			JSONObject obj = new JSONObject();
			obj.put("id", n.getId());
			obj.put("title", n.getTitle());
			obj.put("content", n.getContent());
			JsonResult result = new JsonResult();
			result.setData(obj);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * 
	 * 公会公告发布 需要登录
	 */
	@RequestMapping(value = "/guild/notice/add", method = { POST })
	public @ResponseBody
	Object noticeAdd(@Valid @ModelAttribute("guildNoticeForm") GuildNoticeForm guildNoticeForm, BindingResult result, String token, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(guildNoticeForm.getData())) return buildFormError("error#" + MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(guildNoticeForm.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, guildNoticeForm.getNuid(), guildNoticeForm.getOs());
				if(!legal || !data.containsKey("userId") || !data.containsKey("guildId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				Integer guildId = Integer.valueOf(data.get("guildId").toString());
				Integer userId = Integer.valueOf(data.get("userId").toString());
				
				GuildMembers fromUser = guildMembersService.selectByUserAndGuildId(userId, guildId);
				if (fromUser == null || Integer.valueOf(fromUser.getIsAdmin()) < 1) {
					return ResultHandler.bindResult("error#没有操作权限.");
				}
				GuildNotice notice = new GuildNotice();
				BeanUtils.copyProperties(notice, guildNoticeForm);
				String title = CommonUtils.encodeContent(guildNoticeForm.getTitle());
				String content = CommonUtils.encodeContent(guildNoticeForm.getContent());
				notice.setTitle(title);
				notice.setContent(content);
				notice.setGuildId(guildId);
				guildNoticeService.insert(notice);
				return ResultHandler.bindResult("ok#发布成功.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * 
	 * 公会公告修改 需要登录
	 */
	@RequestMapping(value = "/guild/notice/edit", method = { POST })
	public @ResponseBody
	Object noticeEdit(@Valid @ModelAttribute("guildNoticeForm") GuildNoticeForm guildNoticeForm, BindingResult result, String token, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(guildNoticeForm.getData())) return buildFormError("error#" + MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(guildNoticeForm.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, guildNoticeForm.getNuid(), guildNoticeForm.getOs());
				if(!legal || !data.containsKey("userId") || !data.containsKey("guildId") || !data.containsKey("id"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				Integer guildId = Integer.valueOf(data.get("guildId").toString());
				Integer userId = Integer.valueOf(data.get("userId").toString());
				Integer id = Integer.valueOf(data.get("id").toString());
			
				GuildMembers fromUser = guildMembersService.selectByUserAndGuildId(userId, guildId);
				if (fromUser == null || Integer.valueOf(fromUser.getIsAdmin()) < 1) {
					return ResultHandler.bindResult("error#没有操作权限.");
				}
				GuildNotice notice = new GuildNotice();
				BeanUtils.copyProperties(notice, guildNoticeForm);
				String title = CommonUtils.encodeContent(guildNoticeForm.getTitle());
				String content = CommonUtils.encodeContent(guildNoticeForm.getContent());
				notice.setTitle(title);
				notice.setContent(content);
				notice.setId(id);
				guildNoticeService.update(notice);
				return ResultHandler.bindResult("ok#修改成功.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * 
	 * 公会公告删除 需要登录
	 */
	@RequestMapping(value = "/guild/notice/delete", method = { POST })
	public @ResponseBody
	Object noticeDelete(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getData())) return buildFormError("error#" + MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if(!legal || !data.containsKey("userId") || !data.containsKey("guildId") || !data.containsKey("id"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				Integer guildId = Integer.valueOf(data.get("guildId").toString());
				Integer userId = Integer.valueOf(data.get("userId").toString());
				Integer id = Integer.valueOf(data.get("id").toString());
				
				GuildMembers entity = new GuildMembers();
				entity.setGuildId(guildId);
				entity.setUserId(userId);
				GuildMembers fromUser = guildMembersService.selectByEntity(entity).get(0);
				if (Integer.valueOf(fromUser.getIsAdmin()) < 1) {
					return ResultHandler.bindResult("error#没有操作权限.");
				}
				guildNoticeService.delete(id);
				return ResultHandler.bindResult("ok#删除成功.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * 
	 * 公会成员列表 需要登录
	 */
	@RequestMapping(value = "/guild/member/list", method = { POST })
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
				params.put("status", "1");
				page.setParams(params);
				List<GuildMembers> members = guildMembersService.selectMemberList(page);
				if (members == null || members.isEmpty()) {
					return buildFormError("error#暂无公会成员.");
				}
				JSONArray membersJson = new JSONArray();
				for (GuildMembers n : members) {
					JSONObject member = new JSONObject();
					member.put("id", n.getId());
					member.put("userId", n.getUserId());
					member.put("name", n.getNickName());
					member.put("headIcon", CommonUtils.isNullEmpty(n.getHeadIcon()) ? "" : Constants.IMAGE_SITE_URL + n.getHeadIcon());
					member.put("levelName", n.getLevelName());
					member.put("point", n.getPoint());
					member.put("isAdmin", n.getIsAdmin());
					member.put("guildName", n.getGuildName());
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

	/*
	 * 
	 * 公会踢人 需要登录
	 */
	@RequestMapping(value = "/guild/member/delete", method = { POST })
	public @ResponseBody
	Object memberDelete(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getData())) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if(!legal || !data.containsKey("fromUserId") || !data.containsKey("toUserId") || !data.containsKey("guildId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				String fromGuildId = data.get("guildId").toString();
				String fromUserId = data.get("fromUserId").toString();					
				String toUserId = data.get("toUserId").toString();	
				
				GuildMembers fromUser = guildMembersService.selectByUserAndGuildId(Integer.valueOf(fromUserId), Integer.valueOf(fromGuildId));
				GuildMembers toUser = guildMembersService.selectByUserAndGuildId(Integer.valueOf(toUserId), Integer.valueOf(fromGuildId));
				if (fromUser == null || toUser == null) {
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				}
				if (Integer.valueOf(fromUser.getIsAdmin()) > Integer.valueOf(toUser.getIsAdmin())) {
					guildMembersService.delete(toUser.getId());
					// 成员被踢需要更新公会成员数
					Integer guildId = toUser.getGuildId();
					GuildInfo guild = guildInfoService.select(guildId);
					if (guild != null) {
						if (guild.getMemberCount() != null && guild.getMemberCount() > 0) {
							guild.setMemberCount(guild.getMemberCount() - 1);
							guildInfoService.update(guild);
						}
					}
					//
					//DataEyeAgent.guildsDeleteUser(fromUser.getUserName(), fromUserId,form.getOs(), guildId+"", guild.getName(), toUserId, toUser.getUserName(), guild.getMemberCount()+"");
					return ResultHandler.bindResult("ok#删除成功.");
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

	/*
	 * 
	 * 公会设置管理员 需要登录
	 */
	@RequestMapping(value = "/guild/member/setAdmin", method = { POST })
	public @ResponseBody
	Object setAdmin(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getData())) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if(!legal || !data.containsKey("fromUserId") || !data.containsKey("toUserId") || !data.containsKey("guildId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				String guildId = data.get("guildId").toString();
				String fromUserId = data.get("fromUserId").toString();
				String toUserId = data.get("toUserId").toString();
				
				GuildMembers entity = new GuildMembers();
				entity.setGuildId(Integer.valueOf(guildId));
				entity.setUserId(Integer.valueOf(fromUserId));
				GuildMembers fromUser = guildMembersService.selectByEntity(entity).get(0);
				entity.setUserId(Integer.valueOf(toUserId));
				GuildMembers toUser = guildMembersService.selectByEntity(entity).get(0);
				if (Integer.valueOf(fromUser.getIsAdmin()) == 2 && Integer.valueOf(toUser.getIsAdmin()) == 0) {
					guildMembersService.updateMemberIsAdmin(toUser.getId());
					//DataEyeAgent.guildSetAdmin(fromUser.getUserName(), fromUser.getUserId()+"",form.getOs(), guildId, fromUser.getGuildName(), toUser.getUserId()+"", toUser.getUserName());
					return ResultHandler.bindResult("ok#设置成功.");
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

	/*
	 * 
	 * 公会取消管理员 需要登录
	 */
	@RequestMapping(value = "/guild/member/setRemove", method = { POST })
	public @ResponseBody
	Object setRemove(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getData())) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if(!legal || !data.containsKey("fromUserId") || !data.containsKey("toUserId") || !data.containsKey("guildId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				String guildId = data.get("guildId").toString();
				String fromUserId = data.get("fromUserId").toString();
				String toUserId = data.get("toUserId").toString();
				
				GuildMembers entity = new GuildMembers();
				entity.setGuildId(Integer.valueOf(guildId));
				entity.setUserId(Integer.valueOf(fromUserId));
				GuildMembers fromUser = guildMembersService.selectByEntity(entity).get(0);
				entity.setUserId(Integer.valueOf(toUserId));
				GuildMembers toUser = guildMembersService.selectByEntity(entity).get(0);
				if (Integer.valueOf(fromUser.getIsAdmin()) == 2 && Integer.valueOf(toUser.getIsAdmin()) == 1) {
					guildMembersService.updateMemberRemoveAdmin(toUser.getId());
					//DataEyeAgent.guildCancelAdmin(fromUser.getUserName(), fromUser.getUserId()+"",form.getOs(), guildId, fromUser.getGuildName(), toUser.getUserId()+"", toUser.getUserName());
					return ResultHandler.bindResult("ok#设置成功.");
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

	/*
	 * 
	 * 公会赠送 需要登录
	 */
	@RequestMapping(value = "/guild/member/giveUser", method = { POST })
	public @ResponseBody
	Object giveUser(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(form.getData())) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if(!legal || !data.containsKey("fromUserId") || !data.containsKey("toUserId") || !data.containsKey("guildId") || !data.containsKey("das"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				String guildId = data.get("guildId").toString();
				String fromUserId = data.get("fromUserId").toString();
				String toUserId = data.get("toUserId").toString();
				String das = data.get("das").toString();
				if (fromUserId.equals(toUserId)) return ResultHandler.bindResult("error#不能赠送给自己.");
				
				GuildMembers entity = new GuildMembers();
				entity.setGuildId(Integer.valueOf(guildId));
				entity.setUserId(Integer.valueOf(fromUserId));
				List<GuildMembers> adminList = guildMembersService.selectByEntity(entity);
				if (adminList != null && !adminList.isEmpty()) {
					GuildMembers admin = adminList.get(0);
					//DataEyeAgent.guildsGift(admin.getUserName(), admin.getUserId()+"",form.getOs(), guildId, admin.getGuildName(), das.split("\\|"), toUserId, toUserId);
					if (Integer.valueOf(admin.getIsAdmin()) < 1) {
						return ResultHandler.bindResult("error#非法请求.");
					}
				} else {
					return ResultHandler.bindResult("error#非法请求.");
				}
				return guildMembersService.updateGuildGiveUser(Integer.valueOf(fromUserId),Integer.valueOf(guildId), Integer.valueOf(toUserId), das,request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ResultHandler.bindResult("error#赠送失败.");
		}
	}

	/*
	 * 公会物品列表 需要登录
	 */
	@RequestMapping(value = "/guild/itemList", method = { POST })
	public @ResponseBody
	Object itemList(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String token = form.getToken();
				if (token == null) {
					return ResultHandler.bindResult("error#token不能为空.");
				}
				String guildId = null;
				try {
					guildId = DES3Utils.decryptThreeDESECB(token);
				} catch (Exception e) {
					e.printStackTrace();
					return ResultHandler.bindResult("error#非法请求.");
				}
				if (guildId != null) {
					GuildItem entity = new GuildItem();
					entity.setGuildId(Integer.valueOf(guildId));
					List<GuildItem> list = guildItemService.selectByEntity(entity);
					JSONArray items = new JSONArray();
					for (GuildItem it : list) {
						JSONObject item = new JSONObject();
						item.put("id", it.getId());
						item.put("name", it.getName());
						item.put("count", it.getCount());
						item.put("gived", it.getGivedQuantity());
						item.put("left", it.getCount());
						item.put("isVirtual", it.getIsVirtual());
						item.put("url", "/product/item/" + it.getTypeId());
						item.put("productPhoto", Constants.IMAGE_SITE_URL + it.getProductPhoto());
						items.add(item);
					}
					JsonResult jsonResult = new JsonResult();
					jsonResult.setData(items);
					return jsonResult;
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

	/*
	 * 公会物品管理列表 需要登录
	 */
	@RequestMapping(value = "/guild/itemManagerList", method = { POST })
	public @ResponseBody
	Object itemManagerList(@Valid @ModelAttribute("guildStockMgrListForm") GuildStockMgrListForm guildStockMgrListForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				// 公会id
				String guildId = null;
				try {
					guildId = DES3Utils.decryptThreeDESECB(guildStockMgrListForm.getToken());
				} catch (Exception e) {
					e.printStackTrace();
					return ResultHandler.bindResult("error#非法请求.");
				}
				PageQuery page = new PageQuery(request);
				page.setPageSize(Integer.valueOf(guildStockMgrListForm.getPs()));
				page.setPageNo(Integer.valueOf(guildStockMgrListForm.getPn()));
				Map<String, Object> params = page.getParams();
				params.put("guildId", guildId);
				page.setParams(params);
				List<GuildItem> list = guildItemService.query(page);
			if(list==null||list.isEmpty()){
				return ResultHandler.bindResult("error#物品数据为空.");
			}
			JSONArray items = new JSONArray();
			for (GuildItem it : list) {
				JSONObject item = new JSONObject();
				item.put("id", it.getId());
				item.put("name", it.getName());
				item.put("count", it.getCount());
				item.put("gived", it.getGivedQuantity());
				item.put("left", it.getCount());
				item.put("isVirtual", it.getIsVirtual());
				item.put("url", "/product/item/" + it.getTypeId());
				item.put("productPhoto", Constants.IMAGE_SITE_URL + it.getProductPhoto());
				items.add(item);
			}
			JsonResult jsonResult = new JsonResult();
			JSONObject json = new JSONObject();
			json.put("pn", page.getPageNo());
			json.put("totalPage", page.getTotalPages());
			json.put("items", items);
			jsonResult.setData(json);
			return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

//	/*
//	 * 公会仓库列表
//	 */
//	@RequestMapping(value = "/guild/stockManagerList", method = { POST })
//	public @ResponseBody Object stockManagerList(@Valid @ModelAttribute("guildStockMgrListForm") GuildStockMgrListForm guildStockMgrListForm, BindingResult result, HttpServletRequest request) throws Exception {
//		try {
//			if (result.hasErrors()) {
//				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
//			} else {
//				// 公会id
//				String guildId = null;
//				try {
//					guildId = DES3Utils.decryptThreeDESECB(guildStockMgrListForm.getToken());
//				} catch (Exception e) {
//					e.printStackTrace();
//					return ResultHandler.bindResult("error#非法请求.");
//				}
//				PageQuery page = new PageQuery(request);
//				page.setPageSize(Integer.valueOf(guildStockMgrListForm.getPs()));
//				page.setPageNo(Integer.valueOf(guildStockMgrListForm.getPn()));
//				Map<String, Object> params = page.getParams();
//				params.put("guildId", guildId);
//				page.setParams(params);
//				List<GuildItem> list = guildItemService.query(page);
//				if (list == null || list.isEmpty()) return ResultHandler.bindResult("error#仓库数据为空.");
//				JSONArray items = new JSONArray();
//				for (GuildItem it : list) {
//					JSONObject item = new JSONObject();
//					item.put("id", it.getId());
//					item.put("name", it.getName());
//					item.put("count", it.getCount());
//					item.put("gived", it.getGivedQuantity());
//					item.put("left", it.getCount());
//					item.put("isVirtual", it.getIsVirtual());
//					item.put("url", "/product/item/" + it.getTypeId());
//					item.put("productPhoto", Constants.IMAGE_SITE_URL + it.getProductPhoto());
//					items.add(item);
//				}
//				JsonResult jsonResult = new JsonResult();
//				JSONObject json = new JSONObject();
//				json.put("pn", page.getPageNo());
//				json.put("totalPage", page.getTotalPages());
//				json.put("items", items);
//				jsonResult.setData(json);
//				return jsonResult;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//			return buildFormError("error#" + e.getMessage());
//		}
//	}

	/**
	 * 
	 * 公会放入仓库 需要登录
	
	@RequestMapping(value = "/guild/itemManagerMove", method = { POST })
	public @ResponseBody
	Object itemManagerMove(@Valid @ModelAttribute("guildStockMgrMoveForm") GuildStockMgrMoveForm guildStockMgrMoveForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(CommonUtils.isNullEmpty(guildStockMgrMoveForm.getData())) return buildFormError("error#" + MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(guildStockMgrMoveForm.getData());//解析data中的请求参数，转化成key value形式
				if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
				boolean legal = SessionTokenUtils.validateLogin(data, guildStockMgrMoveForm.getNuid(), guildStockMgrMoveForm.getOs());
				if(!legal || !data.containsKey("userId") || !data.containsKey("guildId") || !data.containsKey("id") || !data.containsKey("count"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				String guildId = data.get("guildId").toString();
				String userId = data.get("userId").toString();
				Integer itemId = Integer.valueOf(data.get("id").toString());
				Integer count = Integer.valueOf(data.get("count").toString());
				
				GuildMembers entity = new GuildMembers();
				entity.setGuildId(Integer.valueOf(guildId));
				entity.setUserId(Integer.valueOf(userId));
				List<GuildMembers> adminList = guildMembersService.selectByEntity(entity);
				if (adminList != null && !adminList.isEmpty()) {
					GuildMembers admin = adminList.get(0);
					if (Integer.valueOf(admin.getIsAdmin()) > 0) {
						return guildMembersService.updateGuildItemsMove(Integer.valueOf(guildId), itemId, count, guildStockMgrMoveForm.getStartTime(), guildStockMgrMoveForm.getEndTime());
					} else {
						return ResultHandler.bindResult("error#没有操作权限.");
					}
				} else {
					return ResultHandler.bindResult("error#非法请求.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ResultHandler.bindResult("error#操作失败.");
		}
	}
	 */
	
	/**
	 * 
	 * 公会放回物品管理 需要登录
	 
	@RequestMapping(value = "/guild/stockManagerMove", method = { POST })
	public @ResponseBody
	Object stockManagerMove(@Valid @ModelAttribute("baseForm") BaseForm form, HttpServletRequest request) throws Exception {
		try {
			if(CommonUtils.isNullEmpty(form.getData())) return buildFormError("error#" + MessageConsValue.legalMessage);
			Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());//解析data中的请求参数，转化成key value形式
			if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
			boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
			if(!legal || !data.containsKey("userId") || !data.containsKey("guildId") || !data.containsKey("id"))
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			
			String guildId = data.get("guildId").toString();
			String userId = data.get("userId").toString();
			Integer stockItemId = Integer.valueOf(data.get("id").toString());
			
			GuildMembers entity = new GuildMembers();
			entity.setGuildId(Integer.valueOf(guildId));
			entity.setUserId(Integer.valueOf(userId));
			List<GuildMembers> adminList = guildMembersService.selectByEntity(entity);
			if (adminList != null && !adminList.isEmpty()) {
				GuildMembers admin = adminList.get(0);
				if (Integer.valueOf(admin.getIsAdmin()) > 0) {
					return guildMembersService.updateGuildItemsStockMove(Integer.valueOf(guildId), stockItemId);
				} else {
					return ResultHandler.bindResult("error#没有操作权限.");
				}
			} else {
				return ResultHandler.bindResult("error#非法请求.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ResultHandler.bindResult("error#放回物品管理失败.");
		}
	}
	*/

	/**
	 * 
	 * 礼包列表 需要登录
	 */
	@RequestMapping(value = "/guild/getGiftList", method = { POST })
	public @ResponseBody
	Object getGiftList(@Valid @ModelAttribute("giftListForm") GiftListForm giftListForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				PageQuery page = new PageQuery(request);
				page.setPageSize(Integer.valueOf(giftListForm.getPs()));
				page.setPageNo(Integer.valueOf(giftListForm.getPn()));
				List<GuildItemsStock> list = guildStockService.selectGetGiftList(giftListForm.getGuildId(), giftListForm.getUserId());
				if(list==null||list.isEmpty()){
					return ResultHandler.bindResult("error#礼包数据为空.");
				}
				JSONArray items = new JSONArray();
				for (GuildItemsStock gi : list) {
					JSONObject item = new JSONObject();
					item.put("id", gi.getId());
					item.put("name", gi.getName());
					item.put("remark", gi.getRemark());
					items.add(item);

				}
				JsonResult jsonResult = new JsonResult();
				JSONObject json = new JSONObject();
				json.put("pn", page.getPageNo());
				json.put("totalPage", page.getTotalPages());
				json.put("items", items);
				jsonResult.setData(json);
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/**
	 * 领取礼包 需要登录
	 
	@RequestMapping(value = "/guild/getGift", method = { POST })
	public @ResponseBody
	Object getGift(String token, HttpServletRequest request) throws Exception {
		try {
			if (token == null) {
				return ResultHandler.bindResult("error#token不能为空.");
			}
			// memberid
			String params = null;
			try {
				params = DES3Utils.decryptThreeDESECB(token);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResultHandler.bindResult("error#非法请求.");
			}
			if (params != null) {
				String[] ids = params.split("\\|");
				if(ids.length<2){
					return ResultHandler.bindResult("error#非法请求.");
				}
				
				String userId = ids[0];
				String[] stockIds = ids[1].split("-");
				return guildMembersService.updateGetGift(stockIds, Integer.valueOf(userId));

			} else {
				return ResultHandler.bindResult("error#非法请求.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ResultHandler.bindResult("error#领取礼包失败.");
		}
	}
	*/
}

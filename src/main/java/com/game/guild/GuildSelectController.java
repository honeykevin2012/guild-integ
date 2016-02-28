package com.game.guild;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.CommonUtils;
import com.game.guild.domain.GuildHistoryMessage;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildLevel;
import com.game.guild.domain.RedPacketInfo;
import com.game.guild.domain.form.GuildMemberListForm;
import com.game.guild.persistence.service.GuildHistoryMessageService;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.guild.persistence.service.GuildLevelService;
import com.game.guild.persistence.service.RedPacketInfoService;
import com.game.platform.domain.PlatformGameApp;
import com.game.platform.persistence.service.PlatformGameAppService;
import com.game.user.domain.UserComment;

@Controller
public class GuildSelectController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuildSelectController.class);

	@Autowired
	GuildInfoService guildInfoService;
	@Autowired
	PlatformGameAppService platformGameAppService;
	@Autowired
	GuildLevelService guildLevelService;
	@Autowired
	private GuildHistoryMessageService guildHistoryMessageService;
	@Autowired
	private RedPacketInfoService redPacketInfoService;

	@RequestMapping(value = "/guild/select", method = { POST })
	public @ResponseBody Object select(BaseForm form, Integer gid, Integer userId, HttpServletRequest request) throws Exception {
		try {
			// 公会
			if (gid == null) {
				return ResultHandler.bindResult("error#请填公会id.");
			}
			GuildInfo guild = guildInfoService.selectByUser(userId, gid);
			if (guild == null) {
				return ResultHandler.bindResult("error#该公会不存在或已经解散.");
			}

			GuildLevel guildLevel = guildLevelService.selectNextLevel(guild.getExp() == 0 ? 1 : guild.getExp());// 公会下一等级经验
			if (guildLevel != null)
				guild.setNextExp(guildLevel.getExp());

			//GuildLevel cacheLevel = CacheListener.getGuildLevel(guild.getLevel().toString());
			//guild.setLevelName(cacheLevel.getName());

			guild.setIconPath(guild.getIcon());
			guild.setIcon(CommonUtils.isNullEmpty(guild.getIcon()) ? "" : Constants.IMAGE_SITE_URL + guild.getIcon());
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			JSONObject guildJson = JSONObject.fromObject(JSON.toJSONString(guild), config);
			// 公会历史消息
			PageQuery pq = new PageQuery();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("guildId", gid);
			pq.setParams(params);
			pq.setPageNo(1);
			if ("pc".equalsIgnoreCase(form.getOs())) {
				pq.setPageSize(10);
			} else {
				pq.setPageSize(1);
			}

			List<GuildHistoryMessage> msgList = guildHistoryMessageService.query(pq);
			JsonConfig cfg1 = new JsonConfig();
			cfg1.setExcludes(new String[] { "guildId", "type", "from", "to" });
			cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			guildJson.put("historyMessages", JSONArray.fromObject(msgList, cfg1));

			// 红包标识
			RedPacketInfo redPacket = redPacketInfoService.selectGuildLimit(gid, userId);
			if (redPacket != null) {
				guildJson.put("redPacket", 1);
			} else {
				guildJson.put("redPacket", 0);
			}
			JsonResult result = new JsonResult();
			result.setData(guildJson);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getStackTrace(e));
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/guild/games", method = { POST })
	public @ResponseBody
	Object games(BaseForm form, String userId, String limit, Integer guildId, String needComment, HttpServletRequest request) throws Exception {
		try {
			if (guildId != null) {
				List<PlatformGameApp> games = platformGameAppService.selectGamesByGuildId(guildId);
				if(games==null||games.isEmpty()){
					return ResultHandler.bindResult("error#没有可推荐游戏.");
				}
				JsonConfig cfg1 = new JsonConfig();
				cfg1.setExcludes(new String[] { "channel", "userId", "ip" });
				cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				for (PlatformGameApp game : games) {
					game.setIcon(Constants.IMAGE_SITE_URL + game.getIcon());
					game.setDownloadUrl("ios".equalsIgnoreCase(form.getOs()) ? game.getDownloadIosUrl() : game.getDownloadUrl());
					// game.setExchange(Math.round(1 / game.getExchangeDivide())
					// + ":1");
					String pictures = game.getScreenshot();
					if (pictures != null && !"".equals(pictures)) {
						String[] arrays = pictures.split("\\|");
						if (arrays != null && arrays.length > 0) {
							List<String> shotList = new ArrayList<String>();
							for (String p : arrays) {
								shotList.add(Constants.IMAGE_SITE_URL + p);
							}
							game.setShotList(shotList);
						}
					}
					List<UserComment> commentList = game.getCommentsList();
					for (UserComment userComment : commentList) {
						userComment.setHeadIcon(Constants.IMAGE_SITE_URL + userComment.getHeadIcon());
					}
					game.setComments(JSONArray.fromObject(commentList, cfg1));
				}
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "commentsList", "downloadIosUrl", "webUrl", "type", "screenshot", "exchangeDivide", "joined", "gameAmountQueryUrl", "gameAmountDeductUrl" });
				JsonResult data = new JsonResult();
				data.setData(JSONArray.fromObject(games, config));
				return data;
			} else if (limit != null && userId != null) {
				List<PlatformGameApp> games = platformGameAppService.selectGuildGamesByUserId(userId, limit);
				if(games==null||games.isEmpty()){
					return ResultHandler.bindResult("error#没有可推荐游戏.");
				}
				JsonConfig cfg1 = new JsonConfig();
				cfg1.setExcludes(new String[] { "channel", "userId", "ip" });
				cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				for (PlatformGameApp game : games) {
					game.setIcon(Constants.IMAGE_SITE_URL + game.getIcon());
					game.setDownloadUrl("ios".equalsIgnoreCase(form.getOs()) ? game.getDownloadIosUrl() : game.getDownloadUrl());
					// game.setExchange(Math.round(1 / game.getExchangeDivide())
					// + ":1");
					String pictures = game.getScreenshot();
					if (pictures != null && !"".equals(pictures)) {
						String[] arrays = pictures.split("\\|");
						if (arrays != null && arrays.length > 0) {
							List<String> shotList = new ArrayList<String>();
							for (String p : arrays) {
								shotList.add(Constants.IMAGE_SITE_URL + p);
							}
							game.setShotList(shotList);
						}
					}
					List<UserComment> commentList = game.getCommentsList();
					for (UserComment userComment : commentList) {
						userComment.setHeadIcon(Constants.IMAGE_SITE_URL + userComment.getHeadIcon());
					}
					game.setComments(JSONArray.fromObject(commentList, cfg1));
				}
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "commentsList", "downloadIosUrl", "webUrl", "type", "screenshot", "exchangeDivide", "joined", "gameAmountQueryUrl", "gameAmountDeductUrl" });
				JsonResult data = new JsonResult();
				data.setData(JSONArray.fromObject(games, config));
				return data;
			} else {
				return ResultHandler.bindResult("error#请输入用户id,limit或者公会id.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/guild/gamesRecommend", method = { POST })
	public @ResponseBody
	Object gamesRecommend(BaseForm form, Integer limit, Integer pn, Integer ps, String needComment, HttpServletRequest request) throws Exception {
		try {
			// 游戏
			List<PlatformGameApp> games = new ArrayList<PlatformGameApp>();
			if (limit != null) {
				games = platformGameAppService.selectCommend(limit);

				JsonConfig cfg1 = new JsonConfig();
				cfg1.setExcludes(new String[] { "channel", "userId", "ip" });
				cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				for (PlatformGameApp game : games) {
					game.setIcon(Constants.IMAGE_SITE_URL + game.getIcon());
					game.setDownloadUrl("ios".equalsIgnoreCase(form.getOs()) ? game.getDownloadIosUrl() : game.getDownloadUrl());
					// game.setExchange(Math.round(1 / game.getExchangeDivide())
					// + ":1");
					String pictures = game.getScreenshot();
					if (pictures != null && !"".equals(pictures)) {
						String[] arrays = pictures.split("\\|");
						if (arrays != null && arrays.length > 0) {
							List<String> shotList = new ArrayList<String>();
							for (String p : arrays) {
								shotList.add(Constants.IMAGE_SITE_URL + p);
							}
							game.setShotList(shotList);
						}
					}
					List<UserComment> commentList = game.getCommentsList();
					for (UserComment userComment : commentList) {
						userComment.setHeadIcon(Constants.IMAGE_SITE_URL + userComment.getHeadIcon());
					}
					game.setComments(JSONArray.fromObject(commentList, cfg1));
				}

				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "commentsList", "downloadIosUrl", "webUrl", "type", "screenshot", "exchangeDivide", "joined", "gameAmountQueryUrl", "gameAmountDeductUrl" });
				JsonResult result = new JsonResult();
				JSONObject data = new JSONObject();
				data.put("games", JSONArray.fromObject(games, config));
				result.setData(data);
				return result;
			} else {
				if (pn == null) {
					return ResultHandler.bindResult("error#请填分页编号.");
				} else if (ps == null) {
					return ResultHandler.bindResult("error#请填分页大小.");
				} else {
					PageQuery page = new PageQuery(request);
					page.setPageSize(ps);
					page.setPageNo(pn);
					Map<String, Object> params = page.getParams();
					page.setParams(params);
					games = platformGameAppService.query(page);

					JsonConfig cfg1 = new JsonConfig();
					cfg1.setExcludes(new String[] { "channel", "userId", "ip" });
					cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
					for (PlatformGameApp game : games) {
						game.setIcon(Constants.IMAGE_SITE_URL + game.getIcon());
						game.setDownloadUrl("ios".equalsIgnoreCase(form.getOs()) ? game.getDownloadIosUrl() : game.getDownloadUrl());
						// game.setExchange(Math.round(1 /
						// game.getExchangeDivide()) + ":1");
						String pictures = game.getScreenshot();
						if (pictures != null && !"".equals(pictures)) {
							String[] arrays = pictures.split("\\|");
							if (arrays != null && arrays.length > 0) {
								List<String> shotList = new ArrayList<String>();
								for (String p : arrays) {
									shotList.add(Constants.IMAGE_SITE_URL + p);
								}
								game.setShotList(shotList);
							}
						}
						List<UserComment> commentList = game.getCommentsList();
						for (UserComment userComment : commentList) {
							userComment.setHeadIcon(Constants.IMAGE_SITE_URL + userComment.getHeadIcon());
						}
						game.setComments(JSONArray.fromObject(commentList, cfg1));
					}
					JsonConfig config = new JsonConfig();
					config.setExcludes(new String[] { "commentsList", "downloadIosUrl", "webUrl", "type", "screenshot", "exchangeDivide", "joined", "gameAmountQueryUrl", "gameAmountDeductUrl" });

					JsonResult jsonResult = new JsonResult();
					JSONObject json = new JSONObject();
					json.put("pn", page.getPageNo());
					json.put("totalPage", page.getTotalPages());
					json.put("games", JSONArray.fromObject(games, config));
					jsonResult.setData(json);
					return jsonResult;
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
	 * 公会历史消息列表
	 */
	@RequestMapping(value = "/guild/historyMessage/list", method = { POST })
	public @ResponseBody
	Object historyMessage(@Valid @ModelAttribute("guildMemberListForm") GuildMemberListForm guildMemberListForm, BindingResult result, HttpServletRequest request) throws Exception {
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
				List<GuildHistoryMessage> msgList = guildHistoryMessageService.query(page);
				if(msgList==null||msgList.isEmpty()){
					return ResultHandler.bindResult("error#没有历史消息.");
				}
				JsonConfig cfg1 = new JsonConfig();
				cfg1.setExcludes(new String[] { "guildId", "type", "from", "to" });
				cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				JsonResult resultData = new JsonResult();
				resultData.setData(JSONArray.fromObject(msgList, cfg1));
				return resultData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

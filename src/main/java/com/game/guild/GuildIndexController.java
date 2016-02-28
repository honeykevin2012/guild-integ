package com.game.guild;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.cache.CbaseHelper;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.form.GuildRankingsForm;
import com.game.guild.domain.form.GuildRecommendForm;
import com.game.guild.persistence.service.GuildInfoService;

@Controller
public class GuildIndexController extends BaseController {
	private static final Logger logger = Logger.getLogger(GuildIndexController.class);

	@Autowired
	GuildInfoService guildInfoService;
	private static CbaseHelper cache = CbaseHelper.getInstance();
	@RequestMapping(value = "/guild/recommend", method = { POST })
	public @ResponseBody
	Object recommend(@Valid @ModelAttribute("guildRecommendForm") GuildRecommendForm guildRecommendForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(guildRecommendForm.getUserId() == null) guildRecommendForm.setUserId(0);
				Object guildRecommendList = cache.get("guildRecommendList");
				if(guildRecommendList==null){
					List<GuildInfo> dbList = guildInfoService.selectRecommend();
					JSONArray jsonList=new JSONArray();
					for (GuildInfo gu : dbList) {
						JSONObject guild = new JSONObject();
						guild.put("id", gu.getId());
						guild.put("name", gu.getName());
						guild.put("userName", gu.getUserName());
						guild.put("icon", CommonUtils.isNullEmpty(gu.getIcon()) ? "" : Constants.IMAGE_SITE_URL + gu.getIcon());
						guild.put("level", gu.getLevel());
						guild.put("createTime", DateUtils.format(gu.getCreateTime(), "yyyy-MM-dd"));
						guild.put("currency", gu.getCurrency());
						guild.put("memberCount", gu.getMemberCount());
						guild.put("slogan", gu.getSlogan());
						//guild.put("welfare", "fuli");
						guild.put("joined", gu.getJoined());
						jsonList.add(guild);
					}
					guildRecommendList=jsonList;
					cache.set("guildRecommendList", 60 * 10, guildRecommendList);
				}
			
				JsonResult json = new JsonResult();
				json.setData(guildRecommendList);
				
				//日志
				//DataEyeAgent.pageEvent(null, guildRecommendForm.getDeviceId(), guildRecommendForm.getDeviceId(), guildRecommendForm.getOs(), guildRecommendForm.getDeviceName(), guildRecommendForm.getIp(), "", guildRecommendForm.getOsVersion(), guildRecommendForm.getScreenResolution(), PageEventEnum.GUILD);
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/guild/rankings", method = { POST })
	public @ResponseBody
	Object rankings(@Valid @ModelAttribute("guildRankingsForm") GuildRankingsForm guildRankingsForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			List<GuildInfo> guilds = new ArrayList<GuildInfo>();
			Integer limit=guildRankingsForm.getLimit();
			Integer userId=guildRankingsForm.getUserId();
			Integer pn=guildRankingsForm.getPn();
			Integer ps=guildRankingsForm.getPs();
			String searchText=guildRankingsForm.getSearchText();
			if (limit != null) {
				guilds = guildInfoService.selectRankingsByUser(userId, limit);
				JSONArray guildsJson = new JSONArray();
				for (GuildInfo gu : guilds) {
					JSONObject guild = new JSONObject();
					guild.put("id", gu.getId());
					guild.put("name", gu.getName());
					guild.put("icon", CommonUtils.isNullEmpty(gu.getIcon()) ? "" : Constants.IMAGE_SITE_URL + gu.getIcon());
					guild.put("level", gu.getLevel());
					guild.put("levelName", gu.getLevelName());
					guild.put("createTime", DateUtils.format(gu.getCreateTime(), "yyyy-MM-dd"));
					guild.put("currency", gu.getCurrency());
					guild.put("memberCount", gu.getMemberCount());
					guild.put("slogan", gu.getSlogan());
					guild.put("welfare", "fuli");
					guild.put("userId", gu.getCreateUserId());
					guild.put("userName", gu.getUserName());
					guild.put("nickName", gu.getNickName());
					guild.put("joined", Integer.valueOf(gu.getJoined()));
					guildsJson.add(guild);
				}
				JsonResult json = new JsonResult();
				json.setData(guildsJson);
				return json;
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
					if (userId == null) {
						userId = 0;
					}
					params.put("name", searchText);
					params.put("userId", Integer.valueOf(userId));
					page.setParams(params);
					guilds = guildInfoService.query(page);
					JSONArray guildsJson = new JSONArray();
					for (GuildInfo gu : guilds) {
						JSONObject guild = new JSONObject();
						guild.put("id", gu.getId());
						guild.put("name", gu.getName());
						guild.put("icon", CommonUtils.isNullEmpty(gu.getIcon()) ? "" : Constants.IMAGE_SITE_URL + gu.getIcon());
						guild.put("level", gu.getLevel());
						guild.put("levelName", gu.getLevelName());
						guild.put("createTime", DateUtils.format(gu.getCreateTime(), "yyyy-MM-dd"));
						guild.put("currency", gu.getCurrency());
						guild.put("memberCount", gu.getMemberCount());
						guild.put("slogan", gu.getSlogan());
						guild.put("welfare", "fuli");
						guild.put("userId", gu.getCreateUserId());
						guild.put("userName", gu.getUserName());
						guild.put("nickName", gu.getNickName() == null ? "" : gu.getNickName());
						guild.put("joined", Integer.valueOf(gu.getJoined()));
						guildsJson.add(guild);
					}
					JsonResult jsonResult = new JsonResult();
					JSONObject json = new JSONObject();
					json.put("pn", page.getPageNo());
					json.put("totalPage", page.getTotalPages());
					json.put("guilds", guildsJson);
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

}

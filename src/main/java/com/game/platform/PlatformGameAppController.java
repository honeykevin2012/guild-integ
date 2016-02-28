package com.game.platform;

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

import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.cache.CbaseHelper;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.platform.domain.PlatformGameApp;
import com.game.platform.persistence.service.PlatformGameAppService;
import com.game.user.domain.UserComment;

@Controller
public class PlatformGameAppController extends BaseController {
	private static final Logger logger = Logger.getLogger(PlatformGameAppController.class);
	@Autowired
	private PlatformGameAppService service;
	@RequestMapping(value = "/platform/game/app/list", method = { POST })
	public @ResponseBody
	Object list(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				List<PlatformGameApp> list = service.selectAll();
				if(list==null||list.isEmpty()){
					return buildFormError("error#数据错误");
				}
				JsonResult data = new JsonResult();
				JsonConfig config = new JsonConfig();
				config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				data.setData(JSONArray.fromObject(list, config));
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/platform/game/app/select", method = { POST })
	public @ResponseBody Object select(BaseForm form, Integer userId, HttpServletRequest request) {
		try {
			if (userId == null) {
				userId = 0;
			}
			List<PlatformGameApp> list = service.selectAll(userId);
			if (list == null || list.isEmpty()) {
				return buildFormError("error#数据错误");
			}
			JsonConfig cfg1 = new JsonConfig();
			cfg1.setExcludes(new String[] { "channel", "userId", "ip" });
			cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			for (PlatformGameApp game : list) {
				game.setIcon(Constants.IMAGE_SITE_URL + game.getIcon());
				game.setDownloadUrl("ios".equalsIgnoreCase(form.getOs()) ? game.getDownloadIosUrl() : game.getDownloadUrl());
				// game.setExchange(Math.round(1 / game.getExchangeDivide()) +
				// ":1");
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
					if (userComment.getHeadIcon() != null) {
						userComment.setHeadIcon(Constants.IMAGE_SITE_URL + userComment.getHeadIcon());
					}
				}
				game.setComments(JSONArray.fromObject(commentList, cfg1));
				CbaseHelper cache = CbaseHelper.getInstance();
				Object consumeList = cache.get("consumeList-" + game.getCode());
				if (consumeList == null) {
					List<Map<String, String>> dbList = service.selectConsumeTopList(game.getCode());
					consumeList = JSONArray.fromObject(dbList);
					cache.add("consumeList-" + game.getCode(), consumeList);
				}
				game.setConsumeTopList((JSONArray) consumeList);
			}
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "commentsList", "downloadIosUrl", "webUrl", "type", "screenshot", "exchangeDivide", "joined", "gameAmountQueryUrl", "gameAmountDeductUrl" });
			JsonResult data = new JsonResult();
			data.setData(JSONArray.fromObject(list, config));
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	@RequestMapping(value = "/platform/game/app/item", method = { POST })
	public @ResponseBody
	Object gameItem(Integer userId,String gameId,HttpServletRequest request) {
		try {
			if(userId==null){
				userId=0;
			}
			Map<String,String> map=new HashMap<String, String>();
			map.put("userId", userId.toString());
			map.put("code", gameId);
			PlatformGameApp game = service.select(map);
			if(game==null){
				return buildFormError("error#游戏id不存在");
			}

			JsonConfig cfg1 = new JsonConfig();
			cfg1.setExcludes(new String[] { "channel","userId","ip"});
			cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				game.setIcon(Constants.IMAGE_SITE_URL +game.getIcon());
				//game.setExchange(Math.round(1 / game.getExchangeDivide()) + ":1");
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

				List<UserComment> commentList=game.getCommentsList();
				for (UserComment userComment : commentList) {
					if(userComment.getHeadIcon()!=null){
					userComment.setHeadIcon(Constants.IMAGE_SITE_URL+userComment.getHeadIcon());
					}
				}
				game.setComments(JSONArray.fromObject(commentList, cfg1));
				CbaseHelper cache = CbaseHelper.getInstance();
				Object consumeList = cache.get("consumeList-"+game.getCode());
				if(consumeList==null){
					List<Map<String,String>> dbList=service.selectConsumeTopList(game.getCode());
					consumeList=JSONArray.fromObject(dbList);
					cache.add("consumeList-"+game.getCode(), consumeList);
				}
				game.setConsumeTopList((JSONArray)consumeList);
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "commentsList","downloadIosUrl","webUrl","type","screenshot","exchangeDivide","joined","gameAmountQueryUrl","gameAmountDeductUrl"});
			JsonResult data = new JsonResult();
			data.setData(JSONObject.fromObject(game,config));
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/platform/game/exchange/list", method = { POST })
	public @ResponseBody
	Object list(HttpServletRequest request) {
		try {

			List<Map<String, String>> list = service.selectExchangeList();
			if(list==null||list.isEmpty()){
				return buildFormError("error#数据错误");
			}
			JsonResult data = new JsonResult();
			data.setData(JSONArray.fromObject(list));
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

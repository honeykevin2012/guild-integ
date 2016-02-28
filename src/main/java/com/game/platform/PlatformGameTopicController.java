package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

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
import com.game.platform.domain.PlatformGameTopic;
import com.game.platform.persistence.service.PlatformGameTopicService;

@Controller
public class PlatformGameTopicController extends BaseController {
	private static final Logger logger = Logger.getLogger(PlatformGameTopicController.class);
	@Autowired
	private PlatformGameTopicService service;

	@RequestMapping(value = "/platform/game/topicList", method = { POST })
	public @ResponseBody
	Object list(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				PlatformGameTopic topic = new PlatformGameTopic();
				topic.setIsTop(1);
				List<PlatformGameTopic> list = service.selectByEntity(topic);
				if(list==null||list.isEmpty()){
					return buildFormError("error#数据错误");
				}
				for (PlatformGameTopic p : list) {
					p.setIcon(Constants.IMAGE_SITE_URL + p.getIcon());
					p.setUrl(Constants.WEB_URL + "/wap/topic/" + p.getId());
				}
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "gameCode", "isTop" });
				JSONArray jsonArray = JSONArray.fromObject(list, config);
				JsonResult data = new JsonResult();
				data.setData(jsonArray);
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/platform/game/topicItem", method = { POST })
	public @ResponseBody
	Object item(Integer id, HttpServletRequest request) {
		try {
			PlatformGameTopic p = service.select(id);
			if(p==null){
				return buildFormError("error#数据错误");
			}
			p.setIcon(Constants.IMAGE_SITE_URL + p.getIcon());
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "gameCode", "isTop" });
			JSONObject obj = JSONObject.fromObject(p, config);
			JsonResult data = new JsonResult();
			data.setData(obj);
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/platform/topic", method = { POST })
	public @ResponseBody
	Object topic(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				PlatformGameTopic topic = new PlatformGameTopic();
				topic.setIsTop(1);
				List<PlatformGameTopic> list = service.selectByEntity(topic);
				if(list==null||list.isEmpty()){
					return buildFormError("error#数据错误");
				}
				JSONObject obj = new JSONObject();
				if (!list.isEmpty()) {
					PlatformGameTopic p = list.get(0);
					obj.put("url", p.getUrl());
				} else {
					obj.put("url", "");
				}
				JsonResult data = new JsonResult();
				data.setData(obj);
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

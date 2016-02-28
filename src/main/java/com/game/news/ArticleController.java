package com.game.news;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.news.domain.Article;
import com.game.news.persistence.service.ArticleService;

@Controller
public class ArticleController extends BaseController {
	@Autowired
	private ArticleService service; 


	@RequestMapping(value = "/news/article/list", method = { POST })
	public @ResponseBody
	Object ArticleList(BaseForm form,Integer pn,Integer ps,HttpServletRequest request) {
		if(pn==null){
			return this.buildFormError("error#页码不能为空.");
		}
		if(ps==null){
			return this.buildFormError("error#分页大小不能为空.");
		}
		PageQuery page = new PageQuery(request);
		page.setPageSize(ps);
		page.setPageNo(pn);
		List<Article> list = service.query(page);
		for (Article article : list) {
			article.setCoverImg(Constants.IMAGE_SITE_URL+article.getCoverImg());
		}
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "author","channelId","channelName","content","forbidComments","isOriginal","isTop","modifyTime","pageUrl","priority","publishTime","source","status","tags"});
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		JsonResult jsonResult = new JsonResult();
		JSONObject json = new JSONObject();
		json.put("pn", page.getPageNo());
		json.put("totalPage", page.getTotalPages());
		json.put("news", jsonArray);
		jsonResult.setData(json);
		//日志
		//DataEyeAgent.pageEvent(null, form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), form.getIp(), "", form.getOsVersion(), form.getScreenResolution(), PageEventEnum.NEWS);
		return jsonResult;
	}


	// 获取新闻详情
	@RequestMapping(value = { "/news/article/item" }, method = { POST })
	public @ResponseBody
	Object shareSearchPSK(Integer id) {
		if(id==null){
			return this.buildFormError("error#新闻id不能为空.");
		}
		Article article = service.select(id);
		if(article==null){
			return this.buildFormError("error#新闻不存在.");
		}
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "author","channelId","channelName","forbidComments","isOriginal","isTop","modifyTime","pageUrl","priority","publishTime","source","status","tags"});
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject obj=JSONObject.fromObject(article, config);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(obj);
		return jsonResult;
	}
}

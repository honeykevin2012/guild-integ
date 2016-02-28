package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;
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

import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.sensitive.words.FinderUtil;
import com.game.common.utility.CommonUtils;
import com.game.user.domain.UserComment;
import com.game.user.domain.form.UserCommentForm;
import com.game.user.persistence.service.UserCommentService;

@Controller
public class UserCommentController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserCommentController.class);
	@Autowired
	private UserCommentService service;
	
	
	@RequestMapping(value="/user/comment/add", method = { POST })
	public @ResponseBody Object add(@Valid @ModelAttribute("form") UserCommentForm form, BindingResult result, HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				UserComment bean = new UserComment();
				bean.setChannel(form.getChannel());
				bean.setContent(FinderUtil.replace(CommonUtils.encodeContent(form.getContent())));
				bean.setCreateTime(new Date());
				bean.setIp(form.getIp());
				bean.setUnderPoint(0);
				bean.setUnderPoint(0);
				bean.setUserId(form.getUserId());
				bean.setDataId(form.getDataId());
				//User user = userService.select(form.getUserId());
				//if(user != null) bean.setUserName(user.getNickName() == null ? user.getUserName() : user.getNickName());
				service.insert(bean);
				JsonConfig cfg1 = new JsonConfig();
				cfg1.setExcludes(new String[] { "channel","userId","ip"});
				cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				JsonResult dataResult=new JsonResult();
				dataResult.setData(JSONObject.fromObject(bean,cfg1));
				return dataResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/user/comment/list", method = { POST })
	public @ResponseBody Object list(Integer gameId, HttpServletRequest request){
		try{
			// channel = 1, limit = 5
			List<UserComment> list = service.selectCommentByGameId(gameId, "1", 5);
			if(list==null||list.isEmpty()){
				return buildFormError("error#评论为空");
			}
			JsonResult json = new JsonResult();
			json.setData(list);
			return json;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/user/comment/listAll", method = { POST })
	public @ResponseBody Object listAll(Integer dataId,String channel, HttpServletRequest request){
		try{
			// channel = 1, limit = 5
			List<UserComment> list = service.selectCommentByGameId(dataId, channel, 100);
			if(list==null||list.isEmpty()){
				return buildFormError("error#数据为空");
			}
			JsonConfig cfg1 = new JsonConfig();
			cfg1.setExcludes(new String[] { "channel","userId","ip"});
			cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			for (UserComment userComment : list) {
				userComment.setHeadIcon(userComment.getHeadIcon()==null?null:Constants.IMAGE_SITE_URL + userComment.getHeadIcon());
			}
			JsonResult json = new JsonResult();
			json.setData(JSONArray.fromObject(list,cfg1));
			return json;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/user/comment/listPage", method = { POST })
	public @ResponseBody Object listPage(Integer dataId,String channel,Integer pn,Integer ps, HttpServletRequest request){
		try{
			PageQuery page = new PageQuery();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dataId", dataId);
			params.put("channel", channel);
			page.setParams(params);
			page.setPageNo(pn);
			page.setPageSize(ps);
			List<UserComment> list = service.query(page);
			if(list==null||list.isEmpty()){
				return buildFormError("error#数据为空");
			}
			JsonConfig cfg1 = new JsonConfig();
			cfg1.setExcludes(new String[] { "channel","userId","ip"});
			cfg1.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			for (UserComment userComment : list) {
				userComment.setHeadIcon(userComment.getHeadIcon()==null?null:Constants.IMAGE_SITE_URL + userComment.getHeadIcon());
			}
			JsonResult result = new JsonResult();
			JSONObject json = new JSONObject();
			json.put("pn", page.getPageNo());
			json.put("totalPage", page.getTotalPages());
			json.put("comments", JSONArray.fromObject(list,cfg1));
			result.setData(json);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/user/comment/up", method = { POST })
	public @ResponseBody Object up(Integer id){
		try{
			service.updateUpOrUnder(id, true);
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
		return new JsonResult();
	}
	
	@RequestMapping(value="/user/comment/under", method = { POST })
	public @ResponseBody Object under(Integer id){
		try{
			service.updateUpOrUnder(id, false);
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
		return new JsonResult();
	}
}

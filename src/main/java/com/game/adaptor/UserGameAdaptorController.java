package com.game.adaptor;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import org.springframework.web.servlet.ModelAndView;

import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.domain.EasyUIResult;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.CommonUtils;
import com.game.sync.GameResult;
import com.game.sync.ViewResult;
import com.game.sync.ViewResultAnalsy;
import com.game.user.domain.UserGame;
import com.game.user.persistence.service.UserGameService;

@Controller
public class UserGameAdaptorController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserGameAdaptorController.class);
	@Autowired
	private UserGameAdaptorService service;
	@Autowired
	private UserGameService userGameService;
	
	@RequestMapping(value="/user/game/adaptor/list", method = { GET})
    public ModelAndView list(){
    	try{
			ModelAndView mav = new ModelAndView("/user/game/adaptor");
			return mav;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
    }
	
	@RequestMapping(value="/user/game/adaptor/list", method = { POST })
	public @ResponseBody Object list(HttpServletRequest request){
		try{
			PageQuery page = new PageQuery(request);
			List<UserGameAdaptor> list = service.query(page);
			JsonConfig config = new JsonConfig();
		    config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONArray jsonArray = JSONArray.fromObject(list, config);
			EasyUIResult result = new EasyUIResult();
			result.setTotal(page.getTotalCount());
			result.setRows(jsonArray);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/user/game/adaptor/creator", method = { POST })
    public @ResponseBody Object creator(@Valid @ModelAttribute("userGameAdaptor") UserGameAdaptor userGameAdaptor, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				Long[] amount = new Long[]{100000L, 200000L, 300000L, 400000L, 500000L, 1000000L};
				Map<String, String> map = new HashMap<String, String>();
				map.put("001", "北京一服");
				map.put("002", "北京二服");
				map.put("003", "北京三服");
				map.put("004", "北京四服");
				map.put("005", "北京五服");
				map.put("006", "北京六服");
				map.put("007", "北京七服");
				map.put("008", "北京八服");
				map.put("009", "北京九服");
				for(int i = 0;i < userGameAdaptor.getCount(); i++){
					String roleName = CommonUtils.create() + CommonUtils.create() + CommonUtils.create() + CommonUtils.create() + CommonUtils.create();
					Random random = new Random();
					String roleId = (int)(random.nextDouble() * (1000000 - 100000) + 100000) + "";
					String serverId = "00" + (random.nextInt(8) + 1);
					userGameAdaptor.setRoleId(roleId);
					userGameAdaptor.setRoleName(roleName);
					userGameAdaptor.setServerId(serverId);
					userGameAdaptor.setServerName(map.get(serverId));
					userGameAdaptor.setAmount(amount[random.nextInt(5)]);
					Date date = new Date();
					userGameAdaptor.setLastLoginTime(date);
					userGameAdaptor.setLastLogoutTime(date);
					service.insert(userGameAdaptor);
					System.out.println("生成角色信息 ： " + userGameAdaptor.toString());
					
					UserGame game = new UserGame();
					game.setAmount(userGameAdaptor.getAmount());
					game.setUserId(userGameAdaptor.getUserId());
					game.setGameId(userGameAdaptor.getGameId());
					game.setServerId(userGameAdaptor.getServerId());
					game.setServerName(userGameAdaptor.getServerName());
					game.setRoleId(userGameAdaptor.getRoleId());
					game.setRoleName(userGameAdaptor.getRoleName());
					game.setLastLoginTime(date);
					game.setLastLogoutTime(date);
					userGameService.insert(game);
				}
				return ResultHandler.bindResult("ok#数据添加成功.");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/game/adaptor/deducts", method = { GET })
    public @ResponseBody Object editor(Integer id) {
	    try{
	    	UserGameAdaptor userGameAdaptor = service.select(id);
			if(userGameAdaptor == null) return null;
			JsonResult result = new JsonResult();
			JsonConfig config = new JsonConfig();
		    config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		    JSONObject json = JSONObject.fromObject(userGameAdaptor, config);
		    result.setData(json);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value="/game/request/test", method = { POST })
    public @ResponseBody Object test1(Integer id) {
		List<ViewResult> list = ViewResultAnalsy.list("41");
		System.out.println(JSONArray.fromObject(list).toString());
		return JSONArray.fromObject(list).toString();
	}
	
	@RequestMapping(value="/game/request/adaptor", method = { POST })
    public @ResponseBody Object test(Integer id) {
	    try{
	    	String gameId = null;
	    	String amount = null;
	    	if(id != null && id == 100){
	    		gameId = "100";
	    		amount = "1000";
	    	}else{
	    		gameId = "200";
	    		amount = "2000";
	    	}
	    	Map<String, List<GameResult>> map = new HashMap<String, List<GameResult>>();
//	    	ViewResult view = new ViewResult();
//	    	view.setGameIcon("http://xxxx.com/aaa.jpg");
//	    	view.setGameId("100");
//	    	view.setGameName("猎魂珠");
	    	System.out.println(gameId);
	    	GameResult role = new GameResult();
	    	role.setAmount(amount);
	    	role.setRoleId("NB10001");
	    	role.setRoleName("无上荣耀");
	    	role.setServerId("S008");
	    	
	    	GameResult role1 = new GameResult();
	    	role1.setAmount("5000");
	    	role1.setRoleId("NB10002");
	    	role1.setRoleName("见光死");
	    	role1.setServerId("S009");
	    	
	    	List<GameResult> roleList = new ArrayList<GameResult>();
	    	roleList.add(role);
	    	roleList.add(role1);

	    	map.put("roles", roleList);
	    	JsonResult json = new JsonResult();
	    	json.setState("1");
	    	json.setData(map);
	    	return json;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	@RequestMapping(value = "/user/game/adaptor/deducts", method = { POST })
    public @ResponseBody Object editor(@Valid @ModelAttribute("userGameAdaptor") UserGameAdaptor adaptor, BindingResult result) {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(adaptor.getUserId() == null || adaptor.getGameId() == null || 
						adaptor.getServerId() == null || adaptor.getRoleId() == null || 
						adaptor.getAmount() == null || adaptor.getSign() == null){
					return buildFormError("5#参数不正确.");
				}
				StringBuilder builder = new StringBuilder();
				builder.append("amount").append("=").append(adaptor.getAmount()).append("&");
				builder.append("identify").append("=").append(adaptor.getIdentify()).append("&");
				builder.append("roleId").append("=").append(adaptor.getRoleId()).append("&");
				builder.append("serverId").append("=").append(adaptor.getServerId()).append("&");
				builder.append("transparent").append("=").append(adaptor.getTransparent()).append("&");
				builder.append("userId").append("=").append(adaptor.getUserId()).append("123");
				//String sign = MD5.md5(builder.toString()).toLowerCase();
				
				JSONObject json = new JSONObject();
				json.put("userId", adaptor.getUserId());
				json.put("serverId", adaptor.getServerId());
				json.put("roleId", adaptor.getRoleId());
				json.put("amount", adaptor.getAmount());
				json.put("identify", adaptor.getIdentify());
				json.put("transparent", adaptor.getTransparent());
				JsonResult jsonResult = new JsonResult();
				
//				if(!sign.equals(adaptor.getSign().toLowerCase())) {
//					jsonResult.setState("5");
//					jsonResult.setMsg("参数不正确, 签名验证失败.");
//					jsonResult.setData(json);
//					return jsonResult;
//				}
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("userId", adaptor.getUserId().toString());
				map.put("gameId", adaptor.getGameId().toString());
				map.put("serverId", adaptor.getServerId());
				map.put("roleId", adaptor.getRoleId());
				UserGameAdaptor uga = service.selectBy(map);
				if(uga == null) {
					jsonResult.setState("3");
					jsonResult.setMsg("角色不存在");
					jsonResult.setData(json);
					return jsonResult;
				}
				if(adaptor.getAmount() > 0){
					System.err.println("扣款请求：金额："+adaptor.getAmount()+", 用户：" + adaptor.getUserId() + ", 游戏："+adaptor.getGameId() + ", 服务器：[" + adaptor.getServerId() +"], 角色：[" + adaptor.getRoleId() + "]"); 
					if(uga.getAmount() < adaptor.getAmount()){
						jsonResult.setState("9");
						jsonResult.setMsg("扣款失败, 余额不足");
						jsonResult.setData(json);
						return jsonResult;
					}
					uga.setAmount(uga.getAmount() - adaptor.getAmount());
					service.update(uga);
					System.err.println("扣款操作成功：扣款额度为：" + adaptor.getAmount() + ", 游戏内余额为：" + uga.getAmount());
				}else if(adaptor.getAmount() < 0){
					System.err.println("退款请求：金额："+adaptor.getAmount()+", 用户：" + adaptor.getUserId() + ", 游戏："+adaptor.getGameId() + ", 服务器：[" + adaptor.getServerId() +", "+adaptor.getServerName()+"], 角色：[" + adaptor.getRoleId() + ", "+adaptor.getRoleName() + "]"); 
					uga.setAmount(uga.getAmount() - adaptor.getAmount());
					service.update(uga);
					System.err.println("退款操作成功：退款额度为：" + Math.abs(adaptor.getAmount()) + ", 游戏内余额为：" + uga.getAmount());
				}else{
					jsonResult.setState("6");
					jsonResult.setMsg("无效金额");
					jsonResult.setData(json);
					return jsonResult;
				}
				jsonResult.setState("1");
				jsonResult.setMsg(adaptor.getAmount() > 0 ? "扣款成功." : "退款成功.");
				jsonResult.setData(json);
				return jsonResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
    
    @RequestMapping(value="/user/game/adaptor/query", method = { POST })
    public @ResponseBody Object select(@Valid @ModelAttribute("userGameAdaptor") UserGameAdaptor adaptor, BindingResult result) {
	    try{
	    	Map<String, String> map = new HashMap<String, String>();
			map.put("userId", adaptor.getUserId().toString());
			map.put("gameId", adaptor.getGameId().toString());
			map.put("serverId", adaptor.getServerId());
			map.put("roleId", adaptor.getRoleId());
			UserGameAdaptor uga = service.selectBy(map);
			if(uga == null) return buildFormError("error#查询出错.");
			JsonResult json = new JsonResult();
			json.setData(uga.getAmount());
			return json;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
    }
	
	@RequestMapping(value="/user/game/adaptor/delete", method = { POST })
    public @ResponseBody Object delete(Integer[] ids) {
	    try{
			service.deleteSelect(ids);
			return ResultHandler.bindResult("ok#数据删除成功.");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
}

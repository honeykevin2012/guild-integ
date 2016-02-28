package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.MessageConsValue;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.games.SettleUserGameHelper;
import com.game.games.transfer.SyncQuery;
import com.game.user.domain.UserGame;
import com.game.user.domain.UserGameResult;
import com.game.user.persistence.service.UserGameService;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class UserGameController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserGameController.class);
	@Autowired
	private UserGameService service;
	/**
	 * 用户获取游戏相关信息
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/game/list", method = { POST })
	public @ResponseBody Object gameList(@Valid @ModelAttribute("baseForm") BaseForm form) throws Exception {
		try {
			String data = form.getData();
			if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
			if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
			boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
			if(!legal || !dataMap.containsKey("userId"))
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			
			Integer userId = Integer.valueOf(dataMap.get("userId").toString());
			//db中查询角色信息
			List<UserGameResult> list = null;
			if(Constants.REQUEST_FLAG.equals(Constants.QUERY_USER_GAME_FLAG)){
				list = SettleUserGameHelper.getGameByUserId(userId);
			}else{
				//实时HTTP请求查询游戏信息
				//SyncQuery sync = new SyncQuery(userId);
				//list = sync.syncRequest();
				//List<ViewResult> view = ViewResultAnalsy.list(userId + "");
			}
			
			JsonResult result = new JsonResult();
			result.setData(list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/user/game/list/sync", method = { POST })
	public @ResponseBody Object gameListSync(@Valid @ModelAttribute("baseForm") BaseForm form) throws Exception {
		try {
			String data = form.getData();
			if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
			if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
			boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
			if(!legal || !dataMap.containsKey("userId"))
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			
			Integer userId = Integer.valueOf(dataMap.get("userId").toString());
			//实时HTTP请求查询游戏信息
			SyncQuery sync = new SyncQuery(userId);
			List<UserGameResult> list = sync.syncRequest();
			
			JsonResult result = new JsonResult();
			result.setData(list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	/**
	 * 根据用户查询游戏
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/game/listByUser", method = { POST })
	public @ResponseBody Object gameListByUser(@Valid @ModelAttribute("baseForm") BaseForm form) throws Exception {
		try {
			String data = form.getData();
			if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
			if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
			boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
			if(!legal || !dataMap.containsKey("userId") || !dataMap.containsKey("gameId"))
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			
			Integer userId = Integer.valueOf(dataMap.get("userId").toString());
			List<UserGameResult> list = service.selectGameByUserId(userId);
			JsonResult result = new JsonResult();
			result.setData(list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	/**
	 * 根据用户与游戏查询角色
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/game/role/list", method = { POST })
	public @ResponseBody Object roleList(@Valid @ModelAttribute("baseForm") BaseForm form) throws Exception {
		try {
			String data = form.getData();
			if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
			if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
			boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
			if(!legal || !dataMap.containsKey("userId") || !dataMap.containsKey("gameId"))
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			
			Integer userId = Integer.valueOf(dataMap.get("userId").toString());
			Integer gameId = Integer.valueOf(dataMap.get("gameId").toString());
			
			List<UserGame> list = service.selectGameRoles(userId, gameId);
			JsonResult result = new JsonResult();
			result.setData(list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

package com.game.log;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.BaseForm;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.utility.logger.NBLogTypeEnum;
import com.game.log.entity.GuildsCancelAdminLog;
import com.game.log.entity.GuildsContributeLog;
import com.game.log.entity.GuildsCreateLog;
import com.game.log.entity.GuildsDeleteUserLog;
import com.game.log.entity.GuildsDissolveLog;
import com.game.log.entity.GuildsGameSignLog;
import com.game.log.entity.GuildsGiftLog;
import com.game.log.entity.GuildsNameChangeLog;
import com.game.log.entity.GuildsRegConfirmLog;
import com.game.log.entity.GuildsRegLog;
import com.game.log.entity.GuildsRegRejectLog;
import com.game.log.entity.GuildsSetAdminLog;
import com.game.log.entity.MallAddShopCarLog;
import com.game.log.entity.MallGuildsOrderSubmitLog;
import com.game.log.entity.MallOrderGoodsLog;
import com.game.log.entity.MallOrderRegLog;
import com.game.log.entity.MallPlatformOrderSubmitLog;
import com.game.log.entity.PayMsgLog;
import com.game.log.entity.UserInfoBindLog;
import com.game.log.entity.UserLoginLog;
import com.game.log.entity.UserRegLog;
import com.game.log.entity.UserVisitLog;
import com.game.log.entity.BaseLogger;

/**
 * 统计日志接口
 * 
 * @author zhaolianjun
 * 
 */
@Controller
public class LogController extends BaseController {

	@RequestMapping(value = "/log/nbgame/logger", method = RequestMethod.POST)
	public @ResponseBody
	Object logPost(@RequestBody JSONObject json,BaseForm form, HttpServletRequest request) {

		NBLogTypeEnum logType = NBLogTypeEnum.getProvider(json.getInt("logType"));
		if (logType == null) {
			return this.buildFormError("error#日志类型错误.");
		}
		JSONObject data=json.getJSONObject("data");
		BaseLogger log=null;
		switch (logType) {
		case UserReg:
			log=(UserRegLog) JSONObject.toBean(data, UserRegLog.class);
			break;
		case UserLogin:
			log=(UserLoginLog) JSONObject.toBean(data, UserLoginLog.class);
			break;
		case UserInfoBind:
			log=(UserInfoBindLog) JSONObject.toBean(data, UserInfoBindLog.class);
			break;
		case UserVisit:
			log=(UserVisitLog) JSONObject.toBean(data, UserVisitLog.class);
			break;
		case GuildsCreate:
			log=(GuildsCreateLog) JSONObject.toBean(data, GuildsCreateLog.class);
			break;
		case GuildsDissolve:
			log=(GuildsDissolveLog) JSONObject.toBean(data, GuildsDissolveLog.class);
			break;
		case GuildsContribute:
			log=(GuildsContributeLog) JSONObject.toBean(data, GuildsContributeLog.class);
			break;
		case GuildsGameSign:
			log=(GuildsGameSignLog) JSONObject.toBean(data, GuildsGameSignLog.class);
			break;
		case GuildsReg:
			log=(GuildsRegLog) JSONObject.toBean(data, GuildsRegLog.class);
			break;
		case GuildsRegConfirm:
			log=(GuildsRegConfirmLog) JSONObject.toBean(data, GuildsRegConfirmLog.class);
			break;
		case GuildsRegReject:
			log=(GuildsRegRejectLog) JSONObject.toBean(data, GuildsRegRejectLog.class);
			break;
		case GuildsDeleteUser:
			log=(GuildsDeleteUserLog) JSONObject.toBean(data, GuildsDeleteUserLog.class);
			break;
		case GuildsGift:
			log=(GuildsGiftLog) JSONObject.toBean(data,GuildsGiftLog.class);
			break;
		case GuildsSetAdmin:
			log=(GuildsSetAdminLog) JSONObject.toBean(data,GuildsSetAdminLog.class);
			break;
		case GuildsCancelAdmin:
			log=(GuildsCancelAdminLog) JSONObject.toBean(data,GuildsCancelAdminLog.class);
			break;
		case GuildsNameChange:
			log=(GuildsNameChangeLog) JSONObject.toBean(data,GuildsNameChangeLog.class);
			break;
		case MallAddShopCar:
			log=(MallAddShopCarLog) JSONObject.toBean(data,MallAddShopCarLog.class);
			break;
		case MallOrderReg:
			log=(MallOrderRegLog) JSONObject.toBean(data,MallOrderRegLog.class);
			break;
		case MallPlatformOrderSubmit:
			log=(MallPlatformOrderSubmitLog) JSONObject.toBean(data,MallPlatformOrderSubmitLog.class);
			break;
		case MallGuildsOrderSubmit:
			log=(MallGuildsOrderSubmitLog) JSONObject.toBean(data,MallGuildsOrderSubmitLog.class);
			break;
		case MallOrderGoods:
			log=(MallOrderGoodsLog) JSONObject.toBean(data,MallOrderGoodsLog.class);
			break;
		case PayMsg:
			log=(PayMsgLog) JSONObject.toBean(data,PayMsgLog.class);
			break;
		default:
			break;
		}
		if(log!=null){
			log.create();	
		}
		JsonResult result = new JsonResult();
		result.setData("");
		return result;
	}

}

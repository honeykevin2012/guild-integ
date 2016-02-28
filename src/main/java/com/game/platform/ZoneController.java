package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.init.LocationListener;

@Controller
public class ZoneController extends BaseController {
	private static final Logger logger = Logger.getLogger(ZoneController.class);
	
	@RequestMapping(value="/platform/zone/province", method = { POST })
	public @ResponseBody Object province(HttpServletRequest request){
		try{
			JsonResult result = new JsonResult();
			result.setData(LocationListener.ZONE_PROVINCE);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/platform/zone/city", method = { POST })
	public @ResponseBody Object city(Integer code, HttpServletRequest request){
		try{
			JsonResult result = new JsonResult();
			result.setData(LocationListener.getZoneCity(code));
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/platform/zone/area", method = { POST })
	public @ResponseBody Object area(Integer code, HttpServletRequest request){
		try{
			JsonResult result = new JsonResult();
			result.setData(LocationListener.getZoneArea(code));
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
}

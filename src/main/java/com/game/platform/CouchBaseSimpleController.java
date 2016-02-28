package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.basics.BaseController;
import com.game.common.basics.cache.CbaseHelper;

@Controller
public class CouchBaseSimpleController extends BaseController {
	private static final Logger logger = Logger.getLogger(CouchBaseSimpleController.class);
	
	@RequestMapping(value="/cbase/simple", method = { POST })
	public @ResponseBody Object list(String key, String value, HttpServletRequest request){
		try{
			CbaseHelper helper = CbaseHelper.getInstance();
			helper.set(key, value);
			String v = "Get value from cahce by "+key+", value is : " + helper.get(key);
			return v;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
	}
}

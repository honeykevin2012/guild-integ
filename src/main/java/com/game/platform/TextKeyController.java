package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.init.SettingListener;

@Controller
public class TextKeyController extends BaseController {
	private static final Logger logger = Logger.getLogger(TextKeyController.class);

	@RequestMapping(value = "/platform/text", method = { POST })
	public @ResponseBody
	Object gamesRule(String key, HttpServletRequest request) throws Exception {
		try {
			String value = SettingListener.getValue(key);
			if (value != null) {
				JsonResult result = new JsonResult();
				result.setData(value);
				return result;
			} else {
				return buildFormError("error#key错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

}

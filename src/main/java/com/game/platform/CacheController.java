package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.basics.BaseController;
import com.game.common.basics.ResultHandler;
import com.game.init.CacheListener;

@Controller
public class CacheController extends BaseController {
	private static final Logger logger = Logger.getLogger(CacheController.class);

	/**
	 * 清除字典表数据缓存
	 * @return
	 */
	@RequestMapping(value = "/dbcache/clear", method = { POST })
	public @ResponseBody Object clearCache() {
		CacheListener.clear();
		if(CacheListener.DICT_MAP.isEmpty()){
			logger.info("DICT缓存清除成功.");
			return ResultHandler.bindResult("ok#缓存清除成功.");
		}else{
			return ResultHandler.bindResult("error#缓存清除失败.");
		}
	}
}

package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.platform.domain.PlatformAdvert;
import com.game.platform.persistence.service.PlatformAdvertService;
/**
 * 轮换图等图片广告管理
 * @author kevin
 */
@Controller
public class PlatformAdvertController extends BaseController {
	private static final Logger logger = Logger.getLogger(PlatformAdvertController.class);
	@Autowired
	private PlatformAdvertService service;	
	
	@RequestMapping(value="/platform/slider/list", method = { POST })
	public @ResponseBody Object list(Integer channel, HttpServletRequest request){
		try{
			if(channel == null) return buildFormError("缺少频道参数.");
			PlatformAdvert platformAdvert = new PlatformAdvert();
			platformAdvert.setChannel(channel);
			List<PlatformAdvert> sliderList = service.selectByEntity(platformAdvert);
			if(sliderList==null||sliderList.isEmpty()){
				return buildFormError("error#轮换图数据为空");
			}
			for (PlatformAdvert pa : sliderList) {
				pa.setPcPhoto(Constants.IMAGE_SITE_URL + pa.getPcPhoto());
				pa.setAppPhoto(Constants.IMAGE_SITE_URL + pa.getAppPhoto());
			}
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "id","remark", "createTime", "sort", "url", "channel" });
			JSONArray sliderItems = JSONArray.fromObject(sliderList, config);
			JsonResult result = new JsonResult();
			result.setData(sliderItems);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

//package com.game.platform;
//
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//import net.sf.json.JsonConfig;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.game.common.BaseForm;
//import com.game.common.basics.BaseController;
//import com.game.common.basics.json.DateJsonValueProcessor;
//import com.game.platform.domain.GameInfo;
//import com.game.platform.persistence.service.GameInfoService;
//
//@Controller
//public class GameInfoController extends BaseController {
//	private static final Logger logger = Logger.getLogger(GameInfoController.class);
//	@Autowired
//	private GameInfoService service;
//	
//	@RequestMapping(value="/platform/game/info/list", method = { POST })
//	public @ResponseBody Object list(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request){
//		try{
//			if(result.hasErrors()) {
//				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
//			} else {
//				GameInfo game = service.select(Integer.valueOf(form.getGameId()));
//				JsonConfig config = new JsonConfig();
//			    config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//				return result;
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.info(e.getMessage());
//			return buildFormError("error#" + e.getMessage());
//		}
//	}
//}

package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

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

import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.user.domain.UserAddressInfo;
import com.game.user.persistence.service.UserAddressInfoService;

@Controller
public class UserAddressInfoController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserAddressInfoController.class);
	@Autowired
	private UserAddressInfoService service;
	
	@RequestMapping(value="/user/address/list", method = { POST })
	public @ResponseBody Object list(Integer userId, HttpServletRequest request){
		try{
			JsonConfig config = new JsonConfig();
		    config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		    UserAddressInfo address = new UserAddressInfo();
		    address.setUserId(userId);
		    List<UserAddressInfo> list = service.selectByEntity(address);
		    JsonResult result = new JsonResult();
		    if(list != null){
		    	JSONArray jsonArray = JSONArray.fromObject(list, config);
		    	result.setData(jsonArray);
		    }
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/user/address/creator", method = { POST })
    public @ResponseBody Object creator(@Valid @ModelAttribute("userAddressInfo") UserAddressInfo userAddressInfo, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(userAddressInfo.getId() == null){
					service.insert(userAddressInfo);
				}else{
					service.update(userAddressInfo);
				}
				JSONObject json = new JSONObject();
				json.put("id", userAddressInfo.getId());
				JsonResult res = new JsonResult();
				res.setData(json);
				return res;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
    }
	
	
	@RequestMapping(value = "/user/address/default", method = { POST })
    public @ResponseBody Object setDefault(@Valid @ModelAttribute("userAddressInfo") UserAddressInfo userAddressInfo, BindingResult result) {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				UserAddressInfo address = new UserAddressInfo();
				address.setUserId(userAddressInfo.getUserId());
				List<UserAddressInfo> list = service.selectByEntity(address);
				if(list != null && list.size() > 0){
					for(UserAddressInfo info : list){
						if("1".equals(info.getIsDefault())){
							info.setIsDefault("0");
							service.update(info);
							break;
						}
					}
				}
				service.updateSetDefault(userAddressInfo.getId());
				return ResultHandler.bindResult("ok#数据修改成功.");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
    }
    
    @RequestMapping(value="/user/address/select", method = { POST })
    public @ResponseBody Object select(Integer id) {
	    try{
			UserAddressInfo userAddressInfo = service.select(id);
			if(userAddressInfo == null) return null;
			JsonResult result = new JsonResult();
			JsonConfig config = new JsonConfig();
		    config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		    JSONObject json = JSONObject.fromObject(userAddressInfo, config);
		    result.setData(json);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return null;
		}
    }
	
	@RequestMapping(value="/user/address/delete", method = { POST })
    public @ResponseBody Object delete(@Valid @ModelAttribute("userAddressInfo") UserAddressInfo userAddressInfo, BindingResult result) {
	    try{
	    	if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
	    	} else {
		    	Integer[] ids = new Integer[]{userAddressInfo.getId()};
				service.deleteSelect(ids);
				return ResultHandler.bindResult("ok#数据删除成功.");
	    	}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError(e.getMessage());
		}
    }
}

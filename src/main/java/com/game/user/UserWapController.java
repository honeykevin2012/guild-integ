package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.utility.Channel;
import com.game.games.SettleUserGameHelper;
import com.game.platform.domain.PlatformAppVersion;
import com.game.platform.persistence.service.PlatformAppVersionService;
import com.game.shop.domain.ProductInfo;
import com.game.shop.persistence.service.ProductInfoService;
import com.game.user.domain.UserGameResult;
import com.game.user.domain.form.UserExchangeItemsForm;
import com.game.user.persistence.service.UserGameService;

@Controller
public class UserWapController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserWapController.class);
	@Autowired
	UserGameService userGameService;
	@Autowired
	private PlatformAppVersionService versionService;
	@Autowired
	private ProductInfoService productService;
	
	@RequestMapping(value="/user/exchange/items", method = { POST })
	public @ResponseBody Object exchange(@Valid @ModelAttribute("form") UserExchangeItemsForm form, BindingResult result){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				List<UserGameResult> list = SettleUserGameHelper.getGameByUserId(form.getUserId());
				long totalPlatAmount = 0L;
				JSONObject json = new JSONObject();
				JsonResult jsonResult = new JsonResult();
				String clientType = "android".equals(form.getOs().toLowerCase()) ? "0" : "1";
				String downloadUrl = null;
				List<PlatformAppVersion> versions = versionService.selectByVersionAndType(form.getSdkVersion(), clientType);
				if(versions != null && versions.size() > 0){
					PlatformAppVersion version = versions.get(0);
					downloadUrl = version.getUrl();
				}
				if(list == null || list.isEmpty()) {
					json.put("totalPlatAmount", "0");
					json.put("downloadUrl", downloadUrl == null ? "" : downloadUrl);
					json.put("products", "[]");
				}
				for(UserGameResult game : list){
					totalPlatAmount += game.getPlatAmount();//获取用户NB总金额
				}
				//查询可兑换的商品
				List<ProductInfo> products = productService.selectUserExchangeItems(totalPlatAmount, Channel.Product.getValue(), Constants.PRODUCT_DEFAULT_IMG_SIZE, "1");
				for(ProductInfo p : products){
					p.setProductPhoto(Constants.IMAGE_SITE_URL + p.getProductPhoto());
				}
				//NB客户端最新版本下载地址
				json.put("totalPlatAmount", totalPlatAmount);
				json.put("downloadUrl", downloadUrl == null ? "" : downloadUrl);
				json.put("products", products == null ? "[]" : products);
				jsonResult.setData(json);
				return jsonResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
}

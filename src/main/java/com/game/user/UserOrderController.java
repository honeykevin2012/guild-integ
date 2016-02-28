package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.MessageConsValue;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.shop.domain.ProductInfo;
import com.game.shop.helper.DiscountHelper;
import com.game.shop.persistence.service.ProductInfoService;
import com.game.user.domain.Cart;
import com.game.user.domain.CartParam;
import com.game.user.domain.User;
import com.game.user.domain.vo.UserCartResult;
import com.game.user.persistence.service.CartOrderService;
import com.game.user.persistence.service.UserOrderService;
import com.game.user.persistence.service.UserService;
import com.game.user.utils.OrderHelper;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class UserOrderController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserOrderController.class);
	public static final String DEFAULT = "1";
	@Autowired
	private ProductInfoService productService;
	@Autowired
	private UserOrderService userOrderService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartOrderService cartOrderService;
	
	@RequestMapping(value="/user/cart/settle", method = { POST})
    public @ResponseBody Object settle (@Valid @ModelAttribute("baseForm") BaseForm form, HttpServletRequest request){
		String data = form.getData();
		if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		Map<String, Object> result = dataAnalsy(data, form.getNuid(), form.getOs());
		if(result.containsKey(OrderHelper.ERROR)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		Integer userId = Integer.valueOf(result.get("userId").toString());
		Integer count = Integer.valueOf(result.get("count").toString());
		Integer productId = Integer.parseInt(result.get("productId").toString());

		User user = userService.select(userId);
		if (user == null)
			return BuildFormErrorUtils.buildFormError("用户数据异常.");
		if (user.getGroupId() == null)
			return BuildFormErrorUtils.buildFormError("用户组数据异常.");

		List<CartParam> params = new ArrayList<CartParam>();
		CartParam param = new CartParam();
		param.setId(productId);
		param.setQuantity(count);
		params.add(param);
		Cart cart = new Cart(params, userId);
		if (!cart.isOnSale())
			return BuildFormErrorUtils.buildFormError("对不起, 您所购买的商品已经下架.");
		if (!cart.hasStock())
			return BuildFormErrorUtils.buildFormError("对不起, 您所购买的商品库存不足.");
		if (!cart.isLegal())
			return BuildFormErrorUtils.buildFormError("个人无法购买公会商城物品.");
		JsonResult jsonResult = new JsonResult();
		JSONObject json = new JSONObject();
		json.put("realPayAmount", cart.getDiscountAmount());
		json.put("totalAmount", cart.getTotalAmount());
		json.put("totalCount", cart.getItems().get(0).getQuantity());
		 List<ProductInfo> listP = cart.getProducts();
		 for (ProductInfo productInfo : listP) {
			 productInfo.setProductPhoto(Constants.IMAGE_SITE_URL+productInfo.getProductPhoto());
		}
		json.put("products", JSONArray.toJSONString(listP));
		json.put("type", cart.getType());
		jsonResult.setData(json);
		return jsonResult;
	}
	
	private static Map<String, Object> dataAnalsy(String token, Integer nuid, String os){
		List<Integer> ids = new ArrayList<Integer>();
		Map<String, Object> result = new HashMap<String, Object>(); 
		try{
			Map<String, Object> data = SessionTokenUtils.dataAnalsy(token);//解析token中的请求参数，转化成key value形式
		
			boolean legal = SessionTokenUtils.validateLogin(data, nuid, os);
			if(!legal || !data.containsKey("userId") || !data.containsKey("count") || (!data.containsKey("productId"))){
				logger.info(MessageConsValue.legalMessage);
				result.put("error", MessageConsValue.legalMessage);
				return result;
			}
			
			Integer userId = Integer.valueOf(data.get("userId").toString());
			String productId = data.get("productId").toString();
			Integer count = Integer.valueOf(data.get("count").toString());
			
			if(!CommonUtils.isNumber(userId + "") || CommonUtils.isNullEmpty(productId)){
				logger.info(MessageConsValue.legalMessage);
				result.put("error", MessageConsValue.legalMessage);
				return result;
			}
			//解析加密token 时间戳-用户id-地址id-商品编号1,商品编号2,...商品编号n
			result.put("userId", userId);
			result.put("productId", productId);
			result.put("count", count);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			ids.add(-1);
			result.clear();
			result.put("error", MessageConsValue.serverErrorMessage);
			return result;
		}
		return result;
	}
	
	/**
	 * 购物车结算列表接口
	 * @param userId 用户ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/cart/amount", method = { POST})
    public @ResponseBody Object orderList (@Valid @ModelAttribute("baseForm") BaseForm form, HttpServletRequest request){
		String data = form.getData();
		if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
		if(data == null) buildFormError("error#" + MessageConsValue.legalMessage);
		boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
		if(!legal || !dataMap.containsKey("userId") || !dataMap.containsKey("productIds"))
			return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		
		Integer userId = Integer.valueOf(dataMap.get("userId").toString());
		String productIds = dataMap.get("productIds").toString();
		
		if(userId == null || userId <= 0 || CommonUtils.isNullEmpty(productIds))
			return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		
		User user = userService.selectUserGroup(userId);
		if(user == null) return BuildFormErrorUtils.buildFormError("非法用户请求.");
		if(user.getGroupId() == null) return BuildFormErrorUtils.buildFormError("所属用户组非法.");
		
		JsonResult result = new JsonResult();
    	try{
			List<UserCartResult> resultList = new ArrayList<UserCartResult>();
			List<Integer> ids = new ArrayList<Integer>();
			if (productIds != null && StringUtils.contains(productIds, ",")) {
				String[] pids = StringUtils.split(productIds, ",");
				for (String pid : pids) {
					ids.add(Integer.valueOf(pid));
				}
			} else {
				if(CommonUtils.isNumber(productIds)){
					ids.add(Integer.valueOf(productIds));
				}else{
					ids = null;
				}
			}
			List<ProductInfo> list = productService.selectEntityByCart(userId, ids, Constants.PRODUCT_CHANNEL, Constants.PRODUCT_MIN_IMAGE_SIZE, DEFAULT);
			int totalCount = 0;
			double totalAmount = 0.0;
			double discountTotalAmount = 0.00;//实付款金额(折扣后金额)
			if(list==null||list.isEmpty()){
				return buildFormError("error#订单商品信息错误");
			}
			for(ProductInfo p : list){
				int count = p.getCartCount();
				totalCount += count;
				double amount = 0.00;
				if(user.getGroupLevel() != null){//根据用户所在用户组计算 商品折扣后单价
					Integer level = user.getGroupLevel();
					double discountAmount = DiscountHelper.getDiscountAmount(p.getId(), level);
					if(discountAmount < 0){
						amount = CommonUtils.multiply(p.getPrice(), count);//当折扣金额小于0，说明折扣比例有问题，每种商品按原始价格总金额计算（不打折）
					}else{
						amount = CommonUtils.multiply(discountAmount, count);//每种商品折扣后总金额
					}
					discountTotalAmount += amount;
				}else{//所在组无折扣，按照原始价格计算（不打折）
					discountTotalAmount += CommonUtils.multiply(p.getPrice(), count);
				}
				totalAmount += CommonUtils.multiply(p.getPrice(), count);//每种商品原始价格总金额（不打折）
				
				UserCartResult c = new UserCartResult();
				c.setProductId(p.getId());
				c.setProductName(p.getName());
				c.setPrice(p.getPrice());
				c.setCount(count);
				c.setStock(p.getCount());
				c.setImage(CommonUtils.isNullEmpty(p.getProductPhoto()) ? "" : Constants.IMAGE_SITE_URL + p.getProductPhoto());
				c.setType(p.getType());
				resultList.add(c);
			}
			JSONObject json = new JSONObject();
			json.put("realPayAmount", Math.round(discountTotalAmount));
			json.put("totalAmount", Math.round(totalAmount));
			json.put("totalCount", totalCount);
			json.put("products", resultList);
			result.setData(json);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
    	return result;
    }
	
//	@RequestMapping(value="/user/order/creatorOnig", method = { POST})
//    public @ResponseBody Object creatorOrderOnig (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, HttpServletRequest request){
//		try{
//			if(result.hasErrors()) {
//				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
//			} else {
//				String data = form.getData();
//				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError("请求不合法.");
//				Object orderResult = userOrderService.creatorOrder(data, form.getNuid(), form.getOs(), request);
//				return orderResult;
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.error(e.getMessage());
//			return this.buildFormError(e.getMessage());
//		}
//    }

	/**
	 * 个人多商品批量购买
	 * @param form
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/order/creator", method = { POST})
    public @ResponseBody Object creatorOrder (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result,HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Object orderResult = userOrderService.creatorOrderNew(data, form.getNuid(), form.getOs(), request);
				//String ip = CommonUtils.realIPAddress(request);
				//Object orderResult = cartOrderService.creatorOrderPerson(data, form.getNuid(), form.getOs(), ip);
				//日志
				//DataEyeAgent.pageEvent(form.getNuid()+"", form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), form.getIp(), "", form.getOsVersion(), form.getScreenResolution(), PageEventEnum.CREATE_ORDER);
				return orderResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }
	/**
	 * 个人单个商品批量购买
	 * @param form
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/order/creatorSingleton", method = { POST})
    public @ResponseBody Object creatorOrderSingleton (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result,HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				String ip = CommonUtils.realIPAddress(request);
				Object orderResult = cartOrderService.creatorOrder(data, form.getNuid(), form.getOs(), ip);
				//日志
				//DataEyeAgent.pageEvent(form.getNuid()+"", form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), form.getIp(), "", form.getOsVersion(), form.getScreenResolution(), PageEventEnum.CREATE_ORDER);
				return orderResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/order/rePay", method = { POST})
    public @ResponseBody Object orderRePay (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result,HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError("请求不合法.");
				Object orderResult = userOrderService.orderRePay(data, form.getNuid(), form.getOs(), request);
				return orderResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }

	@RequestMapping(value="/user/order/refund", method = { POST})
    public @ResponseBody Object orderRefund (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result,HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError("请求不合法.");
				Object orderResult = userOrderService.orderRefund(data, form.getNuid(), form.getOs(), request);
				return orderResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }
	@RequestMapping(value="/user/order/deliver", method = { POST})
    public @ResponseBody Object orderDeliver (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result,HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError("请求不合法.");
				Object orderResult = userOrderService.orderDeliver(data, form.getNuid(), form.getOs(), request);
				return orderResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }	
	@RequestMapping(value="/user/order/guildcreator", method = { POST})
    public @ResponseBody Object guildcreator (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Object orderResult = userOrderService.creatorGuildOrder(data, form.getNuid(), form.getOs(), request);
				return orderResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/order/guildCreatorSingletion", method = { POST})
    public @ResponseBody Object guildCreatorSingletion (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				//Object orderResult = userOrderService.creatorGuildOrderSingleton(data, form.getNuid(), form.getOs(), request);
				String ip = CommonUtils.realIPAddress(request);
				Object orderResult = cartOrderService.creatorOrderGuild(data, form.getNuid(), form.getOs(), ip);
				return orderResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }
	
	
	@RequestMapping(value="/user/order/checkType", method = { POST})
    public @ResponseBody Object checkType (@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, HttpServletRequest request){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if(CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				
				List<Integer> ids = new ArrayList<Integer>();
				//解析token 返回map######################################################################
				Map<String, Object> map = SessionTokenUtils.dataAnalsy(data);//解析token中的请求参数，转化成key value形式
				boolean legal = SessionTokenUtils.validateLogin(map, form.getNuid(), form.getOs());
				if(!legal || !map.containsKey("userId") || !map.containsKey("productIds")){
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				}
				
				Integer userId = Integer.valueOf(map.get("userId").toString());
				String productIds = map.get("productIds").toString();
				String[] idsArray = productIds.split(",");
				for(String id : idsArray){
					ids.add(Integer.valueOf(id));
				}
				
				User user = userService.select(userId);
				if(user == null) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				if(user.getGroupId() == null) return BuildFormErrorUtils.buildFormError("所属用户组非法.");
				//查询用户购物车中所有数据（需结算数据）
				List<ProductInfo> list = productService.selectEntityByCart(userId, ids, Constants.PRODUCT_CHANNEL, Constants.PRODUCT_MIN_IMAGE_SIZE, "1");//1为默认图片
				Set<String> set = new HashSet<String>();
				for(ProductInfo p : list){
					set.add(p.getType());
				}
				if(set.size() > 1) return BuildFormErrorUtils.buildFormError(OrderHelper.BUY_ERROR_MSG);
				JsonResult jsonResult = new JsonResult();
				JSONObject json = new JSONObject();
				if(set.contains("1")) 
					json.put("type", "1") ;
				else if(set.contains("2"))
					json.put("type", "2");
				else 
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				jsonResult.setData(json);//1平台商品 2 公会商品
				return jsonResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return this.buildFormError(e.getMessage());
		}
    }
}

package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.utility.CommonUtils;
import com.game.shop.domain.ProductInfo;
import com.game.shop.helper.DiscountHelper;
import com.game.shop.persistence.service.ProductInfoService;
import com.game.user.domain.CartRemoveForm;
import com.game.user.domain.User;
import com.game.user.domain.UserShoppingCart;
import com.game.user.domain.UserVipGroup;
import com.game.user.domain.vo.UserCartResult;
import com.game.user.persistence.service.UserService;
import com.game.user.persistence.service.UserShoppingCartService;
import com.game.user.persistence.service.UserVipGroupService;

@Controller
public class UserShoppingCartController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserShoppingCartController.class);
	public static final String DEFAULT = "1";
	@Autowired
	private UserShoppingCartService service;
	@Autowired
	private ProductInfoService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserVipGroupService groupService;
	
	/**
	 * 购物车列表接口
	 * @param userId 用户ID
	 * @param values 加入到cookie中的商品, 以1,2,3,4,1,2,2,1,3形式进行存储
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/cart/list", method = { POST})
    public @ResponseBody Object cartList (BaseForm form,Integer userId, String values, HttpServletRequest request){
		JsonResult result = new JsonResult();
    	try{
    		if(userId == null){//用户未登录,只需要展示cookie中数据即可
    			//首先检查cookie中是否存在数据
    			if(values != null && !"".equals(values)){
    				values = values.replace("\"", "");
    				//开始解析
    				List<Integer> ids = new ArrayList<Integer>();
    				Map<String, Integer> map = cookieFilter(values);//map中key即是 商品ID，value为商品数量
    				Set<String> set = map.keySet();//遍历商品ID
    				Iterator<String> it = set.iterator();
    				while(it.hasNext()){
    					String itkey = it.next();
    					if("".equals(itkey)) continue;
    					int key = Integer.valueOf(itkey);
    					ids.add(key);
    				}
    				List<UserCartResult> resultList = new ArrayList<UserCartResult>();
    				//1000:商品图片频道编号   200200：商品最小图片十寸  1:代表默认图
    				if(ids.size() > 0){
	    				List<ProductInfo> list = productService.selectEntityByIds(ids, Constants.PRODUCT_CHANNEL, Constants.PRODUCT_MIN_IMAGE_SIZE, DEFAULT);
	    				int totalCount = 0;
	    				double totalPrice = 0.0;
	    				for(ProductInfo p : list){
	    					int count = map.get(p.getId() + "");
	    					totalCount += count;
	    					totalPrice += amount(p.getPrice(), count);
	    					UserCartResult cart = new UserCartResult();
	    					cart.setProductId(p.getId());
	    					cart.setProductName(p.getName());
	    					cart.setPrice(p.getPrice());
	    					cart.setCount(count);
	    					cart.setStock(p.getCount());
	    					cart.setImage(CommonUtils.isNullEmpty(p.getProductPhoto()) ? "" : Constants.IMAGE_SITE_URL + p.getProductPhoto());
	    					cart.setItemUrl(Constants.WEB_URL + "/product/item/" + p.getId());
	    					cart.setType(p.getType());
	    					resultList.add(cart);
	    				}
	    				JSONObject json = new JSONObject();
	    				json.put("totalAmount", totalPrice);
	    				json.put("totalCount", totalCount);
	    				json.put("products", resultList);
	    				result.setData(json);
    				}
    			}else{
    				JSONObject json = new JSONObject();
    				json.put("totalAmount", 0.00);
    				json.put("totalCount", 0);
    				json.put("products", new ArrayList<UserCartResult>());
    				result.setData(json);
    			}
    		}else{//用户已登录, 需要将cookie中的商品同步到购物车表, 然后显示购物车表中的数据
    			//首先遍历cookie中的数据
    			User user = userService.select(userId);
    			if(user == null) return this.buildFormError("非法用户.");
    			if(user.getGroupId() == null) return this.buildFormError("所属用户组非法.");
				UserVipGroup group = groupService.select(user.getGroupId());
				
    			if(values != null && !"".equals(values)){
    				values = values.replace("\"", "");
    				Map<String, Integer> map = cookieFilter(values);//map中key即是 商品ID，value为商品数量
    				Set<String> set = map.keySet();//遍历商品ID
    				Iterator<String> it = set.iterator();
    				while(it.hasNext()){
    					int productId = Integer.valueOf(it.next());//商品ID
    					UserShoppingCart cart = new UserShoppingCart();
    					cart.setUserId(userId);
    					cart.setProductId(productId);
    					List<UserShoppingCart> list = service.selectByEntity(cart);
    					if(list != null && list.size() > 0){//判断购物车是否存在同款商品
    						UserShoppingCart c = list.get(0);//购物车表已经存在同款商品
    						int count = c.getCount() + map.get(productId + "");
    						c.setCount(count < 1 ? 1 : count);//需要更新购物车商品数量
    						service.update(c);
    					}else{//不存在同款商品，直接插入购物车表
    						int c = map.get(productId + "");
    						if(c < 0) c = 1;
    						cart.setCount(c);
    						service.insert(cart);
    					}
    				}
    				//以上cookie已经同步完成，下面开始查询购物车表中的数据进行展示
    			}
    			
    			List<UserCartResult> resultList = new ArrayList<UserCartResult>();
    			List<ProductInfo> list = productService.selectEntityByCart(userId, null,Constants.PRODUCT_CHANNEL, Constants.PRODUCT_MIN_IMAGE_SIZE, DEFAULT);
    			int totalCount = 0;
				double totalPrice = 0.0;
    			for(ProductInfo p : list){
    				int count = p.getCartCount();
					totalCount += count;
					
					totalPrice += 0.00;//总价
					double disAmount = 0.00;//折扣后总金额
					double discountAmount = 0.00;//商品折后单价
					if(group != null){//根据用户所在用户组计算 商品折扣后单价
						Integer level = group.getLevel();
						discountAmount = DiscountHelper.getDiscountAmount(p.getId(), level);
						if(discountAmount < 0){
							discountAmount = p.getPrice();
							disAmount = amount(p.getPrice(), count);//当折扣金额小于0，说明折扣比例有问题，每种商品按原始价格总金额计算（不打折）
						}else{
							disAmount = amount(discountAmount, count);//每种商品折扣后总金额
						}
					}else{
						disAmount = amount(p.getPrice(), count);
					}
					totalPrice += disAmount;
					UserCartResult c = new UserCartResult();
					c.setProductId(p.getId());
					c.setProductName(p.getName());
					c.setPrice(p.getPrice());
					c.setCount(count);
					c.setStock(p.getCount());
					c.setImage(CommonUtils.isNullEmpty(p.getProductPhoto()) ? "" : Constants.IMAGE_SITE_URL + p.getProductPhoto());
					c.setDiscountPrice(discountAmount);
					c.setType(p.getType());
					resultList.add(c);
				}
    			JSONObject json = new JSONObject();
				json.put("totalAmount", totalPrice);
				json.put("totalCount", totalCount);
				json.put("products", resultList);
				result.setData(json);
    		}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
    	//日志
		//DataEyeAgent.pageEvent(userId+"", form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), form.getIp(), "", form.getOsVersion(), form.getScreenResolution(), PageEventEnum.CART);
    	return result;
    }
	
	/**
	 * 把cookie中数据转换成MAP  EX:1,1,2,2,2,3,4 -> map(键值：出现次数) 1:2 2:3 3:1 4:1 
	 * @param value
	 * @return
	 */
	private static Map<String, Integer> cookieFilter(String value){
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] values = value.split(",");
		for(String s : values){
			if("".equals(s)) continue;
			if(!map.containsKey(s)){
				map.put(s, 1);
			}else{
				Integer count = map.get(s);
				count++;
				map.put(s, count);
			}
		}
		return map;
	}
	
	@RequestMapping(value="/user/cart/add", method = { POST })
    public @ResponseBody Object creator(@Valid @ModelAttribute("userShoppingCart") UserShoppingCart userShoppingCart, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				UserShoppingCart cart = new UserShoppingCart();
				cart.setUserId(userShoppingCart.getUserId());
				cart.setProductId(userShoppingCart.getProductId());
				List<UserShoppingCart> list = service.selectByEntity(cart);
				int finalCount = 0;
				if(list != null && list.size() > 0){//判断购物车是否存在同款商品
					UserShoppingCart c = list.get(0);//购物车表已经存在同款商品
					int count = c.getCount() + userShoppingCart.getCount();
					c.setCount(count < 1 ? 1 : count);//需要更新购物车商品数量 
					service.update(c);
					finalCount = c.getCount();
				}else{//不存在同款商品，直接插入购物车表
					int c = userShoppingCart.getCount();
					if(c < 0) c = 1;
					userShoppingCart.setCount(c);
					service.insert(userShoppingCart);
					finalCount = userShoppingCart.getCount();
				}
				JSONObject json = new JSONObject();
				json.put("productId", userShoppingCart.getProductId());
				json.put("count", finalCount);
				json.put("userId", userShoppingCart.getUserId());
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(json);
				//DataEyeAgent.addCart(userShoppingCart.getUserId().toString(), userShoppingCart.getUserId().toString(),userShoppingCart.getOs(), userShoppingCart.getProductId().toString(), userShoppingCart.getCount().toString());
				return jsonResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError(e.getMessage());
		}
    }
	
	@RequestMapping(value="/user/cart/remove", method = { POST })
    public @ResponseBody Object remove(@Valid @ModelAttribute("form") CartRemoveForm form, BindingResult result) throws Exception {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(form.getProductId() == null || form.getUserId() == null) return this.buildFormError("参数不正确.");
				List<Integer> idList = form.getProductId();
				Integer userId = form.getUserId();
				if(idList.size() == 1){
					Integer id = idList.get(0);
					service.deleteByProductIdAndUserId(id, userId);
					JsonResult jsonResult = new JsonResult();
					JSONObject json = new JSONObject();
					json.put("productId", id);
					json.put("userId", userId);
					jsonResult.setData(json);
					return jsonResult;
				}else if(idList.size() > 1){
					service.deleteByUserId(userId, idList);
					return ResultHandler.bindResult("ok#商品删除成功.");
				}else{
					return this.buildFormError("参数不正确.");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError(e.getMessage());
		}
    }
	

	@RequestMapping(value="/user/cart/checkedCancel", method = { POST })
    public @ResponseBody Object checkedCancel(Integer userId, String productIds, HttpServletRequest request) throws Exception {
		try{
			JsonResult result = new JsonResult();
    			List<UserCartResult> resultList = new ArrayList<UserCartResult>();
    			List<Integer> ids=new ArrayList<Integer>();
    			if(productIds!=null&&StringUtils.contains(productIds, ",")){
    				
    				String[] pids=StringUtils.split(productIds, ",");
    				for (String pid : pids) {
    					ids.add(Integer.valueOf(pid));
					}
    			}else{
    				ids.add(-1);
    			}
    			
    			User user = userService.select(userId);
    			if(user == null) return this.buildFormError("非法用户.");
    			if(user.getGroupId() == null) return this.buildFormError("所属用户组非法.");
				UserVipGroup group = groupService.select(user.getGroupId());
				
    			List<ProductInfo> list = productService.selectEntityByCart(userId, ids,Constants.PRODUCT_CHANNEL, Constants.PRODUCT_MIN_IMAGE_SIZE, DEFAULT);
    			int totalCount = 0;
				double totalPrice = 0.0;
    			for(ProductInfo p : list){
    				
    				int count = p.getCartCount();
    				double disAmount = 0.00;//折扣后总金额
					double discountAmount = 0.00;//商品折后单价
					if(group != null){//根据用户所在用户组计算 商品折扣后单价
						Integer level = group.getLevel();
						discountAmount = DiscountHelper.getDiscountAmount(p.getId(), level);
						if(discountAmount < 0){
							discountAmount = p.getPrice();
							disAmount = amount(p.getPrice(), count);//当折扣金额小于0，说明折扣比例有问题，每种商品按原始价格总金额计算（不打折）
						}else{
							disAmount = amount(discountAmount, count);//每种商品折扣后总金额
						}
					}else{
						disAmount = amount(p.getPrice(), count);
					}
    				
					totalCount += count;
					totalPrice += disAmount;
					//totalPrice += amount(p.getPrice(), count);
					UserCartResult c = new UserCartResult();
					c.setProductId(p.getId());
					c.setProductName(p.getName());
					c.setPrice(p.getPrice());
					c.setCount(count);
					c.setStock(p.getCount());
					c.setImage(CommonUtils.isNullEmpty(p.getProductPhoto()) ? "" : Constants.IMAGE_SITE_URL + p.getProductPhoto());
					resultList.add(c);
				}
    			JSONObject json = new JSONObject();
				json.put("totalAmount", totalPrice);
				json.put("totalCount", totalCount);
				result.setData(json);
				return result;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
    }
	
	private double amount(double price, int count){
		BigDecimal decp = new BigDecimal(Double.toString(price));
		BigDecimal decc = new BigDecimal(Double.toString(count));
		BigDecimal total = decp.multiply(decc);
		return total.doubleValue();
	}
	
	public static void main(String[] args) {
		//System.out.println(amount(2.30, 6));
	}
}

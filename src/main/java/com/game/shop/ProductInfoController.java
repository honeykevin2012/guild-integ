package com.game.shop;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.cache.CbaseHelper;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.Channel;
import com.game.common.utility.CommonUtils;
import com.game.platform.domain.PlatformAttachment;
import com.game.platform.domain.PlatformGameApp;
import com.game.shop.domain.ProductInfo;
import com.game.shop.domain.RefProductPromotion;
import com.game.shop.form.ProductVO;
import com.game.shop.persistence.service.ProductInfoService;
import com.game.shop.persistence.service.RefProductPromotionService;
import com.game.user.domain.form.UserExchangeItemsForm;

@Controller
public class ProductInfoController extends BaseController {
	private static final Logger logger = Logger.getLogger(ProductInfoController.class);
	
	@Autowired
	private ProductInfoService service;
	@Autowired
	private RefProductPromotionService refPromService;
	private static CbaseHelper cache = CbaseHelper.getInstance();
	
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/product/info/list", method = { POST })
	public @ResponseBody Object list(BaseForm form,String orderBy, String searchText, String type, HttpServletRequest request){
    	try{
			PageQuery page = new PageQuery(request);
			Map<String, Object> params = page.getParams();
			if(params == null) params = new HashMap<String, Object>();
			params.put("name", searchText);
			params.put("status", "1");//正常销售的商品
			params.put("channel", "1000");
			params.put("size", Constants.PRODUCT_DEFAULT_IMG_SIZE);
			params.put("isDefault", "1");//选取默认图片
			if(CommonUtils.isNullEmpty(type)){
				params.put("type", "1");//商品类型 1为平台商城2为公会商城
			}else{
				params.put("type", type);
			}
			page.setParams(params);
			page.setOrderBy(createSort(orderBy));
			String pn = request.getParameter("pn");
			String ps = request.getParameter("ps");
			if(CommonUtils.isNullEmpty(pn)) return this.buildFormError("error#当前页数不能为空.");
			if(CommonUtils.isNullEmpty(ps)) return this.buildFormError("error#每页记录数不能为空.");
			page.setPageSize(Integer.valueOf(ps));
			
			PageQuery pageCache = cache.get(page.toString());
			List<ProductInfo> list = null;
			if(pageCache != null) list = (List<ProductInfo>) pageCache.getDataList();
			
			if(list == null || list.isEmpty()){
				list = service.query(page);
				page.setDataList(list);
				cache.add(page.toString(), 600, page);
			}
			//List<ProductVO> resultList = new ArrayList<ProductVO>();
			JSONArray jsonProductArray = new JSONArray();
			for(ProductInfo product : list){
				//ProductVO vo = new ProductVO();
				JSONObject jsonProduct = new JSONObject();
				//vo.setProductId(product.getId()); 
				jsonProduct.put("productId", product.getId());
				//vo.setProductName(product.getName());
				jsonProduct.put("productName", product.getName());
				//vo.setPrice(product.getPrice());
				jsonProduct.put("price", product.getPrice());
				//vo.setExclusive("");//专属
				jsonProduct.put("exclusive", "");
				//vo.setImage(Constants.IMAGE_SITE_URL + product.getProductPhoto());
				jsonProduct.put("image", Constants.IMAGE_SITE_URL + product.getProductPhoto());
				//vo.setSaleTimes(product.getSaleTimes());
				jsonProduct.put("saleTimes", product.getSaleTimes());
				//vo.setCount(product.getCount() + "");
				jsonProduct.put("count", product.getCount());
				//vo.setItemUrl(Constants.WEB_URL + "/product/item/" + product.getId());
				jsonProduct.put("itemUrl", Constants.WEB_URL + "/product/item/" + product.getId());
				//vo.setSaleStatus(product.getSaleStatus());
				jsonProduct.put("saleStatus", product.getSaleStatus());
				//vo.setType(product.getType());
				jsonProduct.put("type", product.getType());
				//resultList.add(vo);
				jsonProduct.put("isVirtual", product.getIsVirtual());
				jsonProductArray.add(jsonProduct);
			}
			JsonResult jsonResult = new JsonResult();
			JSONObject json = new JSONObject();
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			json.put("pn", page.getPageNo());
			json.put("totalPage", pageCache == null ? page.getTotalPages() : pageCache.getTotalPages());
			json.put("products", JSONArray.fromObject(jsonProductArray, config));
			jsonResult.setData(json);
			if("1".equals(type)){
				//日志
				//DataEyeAgent.pageEvent(null, form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), form.getIp(), "", form.getOsVersion(), form.getScreenResolution(), PageEventEnum.SHOP);
			}else{
				//日志
				//DataEyeAgent.pageEvent(null, form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), form.getIp(), "", form.getOsVersion(), form.getScreenResolution(), PageEventEnum.GUILD_SHOP);
			}
			return jsonResult;
    	}catch(Exception e){
    		e.printStackTrace();
			logger.error(e.getMessage());
			return null;
    	}
	}
	
    @RequestMapping(value="/product/info/select/{id}", method = { POST })
	public @ResponseBody Object select(@PathVariable Integer id,BaseForm form) {
		try {
			String key = "product_select_"+ id;
			ProductInfo product = cache.get(key);
			if(product == null) {
				product = service.select(id);
				if (product == null) return this.buildFormError("error#该商品不存在.");
				List<String> imageList = new ArrayList<String>();
				for (PlatformAttachment att : product.getImages()) {
					imageList.add(Constants.IMAGE_SITE_URL + att.getPath());
				}
				product.setImageList(imageList);
				for (PlatformGameApp platformGameApp : product.getExclusive()) {
					platformGameApp.setIcon(Constants.IMAGE_SITE_URL + platformGameApp.getIcon());
				}
				// 查询商品活动
				List<RefProductPromotion> proms = refPromService.selectPromotionByProductId(product.getId());
				if (proms != null && proms.size() > 0) {
					RefProductPromotion ref = proms.get(0);// 获取活动详情, 理论上商品有且只能有一个活动
					String promotion = null;
					if (ref.getGroupLevel() == 1)
						promotion = ref.getConditions() + "折";
					else
						promotion = ref.getGroupName() + "享" + ref.getConditions() + "折";
					product.setPromotion(promotion);
				}else{
					product.setPromotion("无");
				}
				cache.add(key, 1800, product);
			}
			JsonResult result = new JsonResult();
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			result.setData(JSONObject.fromObject(product, config));
			//日志
			//DataEyeAgent.productInfo(null, form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), form.getIp(), "", form.getOsVersion(), form.getScreenResolution(), PageEventEnum.PRDOUCTINFO,id+"");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
    
    @RequestMapping(value="/product/mobile/select/{id}", method = { POST })
 	public @ResponseBody Object mobileSelect(@PathVariable Integer id,BaseForm form) {
 		try {
 			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "1");//正常销售的商品
			params.put("channel", "1000");
			params.put("size", Constants.PRODUCT_DEFAULT_IMG_SIZE);
			params.put("isDefault", "1");//选取默认图片
			params.put("id", id);
			PageQuery pq=new PageQuery();
			pq.setParams(params);
			pq.setPageNo(1);
			pq.setPageSize(1);
			pq.setOrderBy(createSort("1"));
			List<ProductInfo> list = service.query(pq);
			if(list!=null&&!list.isEmpty()){
				ProductInfo product=list.get(0);
				ProductVO vo = new ProductVO();
				vo.setProductId(product.getId());
				vo.setProductName(product.getName());
				vo.setPrice(product.getPrice());
				vo.setExclusive("");//专属
				vo.setImage(Constants.IMAGE_SITE_URL + product.getProductPhoto());
				vo.setSaleTimes(product.getSaleTimes());
				vo.setCount(product.getCount() + "");
				vo.setItemUrl(Constants.WEB_URL + "/product/item/" + product.getId());
				vo.setSaleStatus(product.getSaleStatus());
				vo.setType(product.getType());
				//DataEyeAgent.productInfo(null, form.getDeviceId(), form.getDeviceId(), form.getOs(), form.getDeviceName(), form.getIp(), "", form.getOsVersion(), form.getScreenResolution(), PageEventEnum.PRDOUCTINFO,id+"");
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(JSONObject.fromObject(vo));
				return jsonResult;
			}else{
				return ResultHandler.bindResult("error#商品id不存在.");
			}
 		} catch (Exception e) {
 			e.printStackTrace();
 			logger.error(e.getMessage());
 			return null;
 		}
 	}
	
	@RequestMapping(value="/product/info/delete", method = { POST })
    public @ResponseBody Object delete(Integer[] ids) {
		service.deleteSelect(ids);
		return ResultHandler.bindResult("ok#数据删除成功.");
    }
	
	private String createSort(String sort){
		if("1".equals(sort)){//销量
			return "sale_times desc";
		}else if("2".equals(sort)){//价格
			return "sale_times asc";
		}else if("3".equals(sort)){
			return "price desc";
		}else if("4".equals(sort)){
			return "price asc";
		}else if("5".equals(sort)){//评论数
			return "up_time desc";
		}else if("6".equals(sort)){
			return "up_time asc";
		}else if("7".equals(sort)){//评论数
			return "visit_times desc";
		}else if("8".equals(sort)){
			return "visit_times asc";
		}else{
			return "sale_times desc";
		}
	}
	
	@RequestMapping(value="/shop/hotList", method = { POST })
	public @ResponseBody Object hotList(@Valid @ModelAttribute("form") UserExchangeItemsForm form, BindingResult result){
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				List<ProductInfo> list = service.selectHotList( Channel.Product.getValue(), Constants.PRODUCT_DEFAULT_IMG_SIZE, "1");
				if(list==null||list.isEmpty()){
					return buildFormError("error#数据为空");
				}
				JSONArray jsonArray = new JSONArray();
					for (ProductInfo p : list) {
						JSONObject obj=new JSONObject();
						obj.put("id", p.getId());
						obj.put("name", p.getName());
						obj.put("image", Constants.IMAGE_SITE_URL +p.getProductPhoto());
						obj.put("price", p.getPrice());
						obj.put("url", Constants.WEB_URL + "/product/item/" + p.getId());
						jsonArray.add(obj);
					}
				
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(jsonArray);
				return jsonResult;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

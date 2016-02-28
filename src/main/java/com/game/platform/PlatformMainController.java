package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.BaseForm;
import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.cache.CbaseHelper;
import com.game.common.basics.exception.ActionException;
import com.game.common.utility.Channel;
import com.game.common.utility.CommonUtils;
import com.game.guild.domain.GuildInfo;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.init.CacheListener;
import com.game.init.SettingListener;
import com.game.platform.domain.PlatformBlockComment;
import com.game.platform.domain.PlatformFeedback;
import com.game.platform.domain.PlatformGameApp;
import com.game.platform.persistence.service.PlatformBlockCommentService;
import com.game.platform.persistence.service.PlatformFeedbackService;
import com.game.platform.persistence.service.PlatformGameAppService;
import com.game.shop.domain.ProductInfo;
import com.game.shop.persistence.service.ProductInfoService;
import com.game.user.domain.User;
import com.game.user.domain.UserVipGroup;
import com.game.user.persistence.service.UserService;
import com.game.user.persistence.service.UserVipGroupService;

@Controller
public class PlatformMainController extends BaseController {
	private static final Logger logger = Logger.getLogger(PlatformMainController.class);
	// private static final String ext_allow =
	// "jpg,jpeg,png,pdf,txt,docx,xlsx,gif";
	@Autowired
	private PlatformFeedbackService service;
	@Autowired
	private ProductInfoService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private GuildInfoService guildService;
	@Autowired
	private PlatformGameAppService gameAppService;
	@Autowired
	private PlatformBlockCommentService blockCommentService;
	@Autowired
	UserVipGroupService userVipGroupService;

	/**
	 * 意见反馈
	 * 
	 * @param request
	 * @param response
	 * @throws ActionException
	 */
	@RequestMapping(value = "/platform/feedback", method = { POST })
	public @ResponseBody
	Object fileNormal(String type, String title, String content, String qq, String phone, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (title == null || "".equals(StringUtils.trim(title))) {
				return buildFormError("error#请写一个标题.");
			}
			if (type == null || "".equals(StringUtils.trim(type))) {
				return buildFormError("error#请选择类型.");
			}
			// MultipartHttpServletRequest multipartRequest =
			// (MultipartHttpServletRequest) request;
			// CommonsMultipartFile file = (CommonsMultipartFile)
			// multipartRequest.getFile("attachment");
			// String attachmentPath = null;
			// if (file != null) {
			//
			// String date = DateUtils.format(new Date(), "yyyy-MM");
			// String datePath = date.replace("-", File.separator);
			// // 根据channel获取不同业务存储根路径
			//
			// String ext =
			// CommonUtils.getExtendName(file.getOriginalFilename()).toLowerCase();
			// if(!StringUtils.contains(ext_allow, ext)){
			// return buildFormError("error#"+ext+"文件不允许上传.");
			// }
			// String randomName = DateUtils.format(new Date(),
			// "yyyyMMddHHmmSS");
			// String fileName = randomName + "." + ext;
			//
			// String filePath = Constants.FTP_FEEDBACK + File.separator +
			// datePath + File.separator + fileName;
			//
			// FTPClientTemplate ftpClient = FTPClientTemplate.getClient();//
			// 存储原始图片,不做任何处理
			// filePath = filePath.replace('\\', '/');
			// ftpClient.put(filePath, file.getInputStream());
			//
			// // String webURL = Constants.PAGE_SERVICE_URL + filePath;
			// attachmentPath = filePath.replace("\\", "/");
			// }

			PlatformFeedback feed = new PlatformFeedback();
			feed.setTitle(title);
			feed.setContent(content);
			feed.setType(type);
			feed.setQq(qq);
			feed.setPhone(phone);
			// feed.setAttachmentPath(attachmentPath);
			service.insert(feed);
			JsonResult jsonResult = new JsonResult();
			jsonResult.setMsg("感谢您的反馈");
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#反馈失败了.");
		}
	}

	/**
	 * app首页
	 * 
	 * @param request
	 * @param response
	 * @throws ActionException
	 * 
	 @RequestMapping(value = "/platform/app/index", method = { POST }) public @ResponseBody
	 *                       Object index(BaseForm form,String gameCode,
	 *                       HttpServletRequest request, HttpServletResponse
	 *                       response) { try { if (gameCode == null) { return
	 *                       buildFormError("error#请输入游戏编码."); } Integer
	 *                       gameCodeIn = -1; if
	 *                       (!gameCode.equalsIgnoreCase("platform")) {
	 *                       gameCodeIn = Integer.valueOf(gameCode); } //
	 *                       游戏首页背景图 PlatformAppIndexImg p1 = new
	 *                       PlatformAppIndexImg(); p1.setGameCode(gameCodeIn);
	 * 
	 *                       String imgUrl = ""; PlatformAppIndexImg img =
	 *                       appIndexImgService.selectByEntity(p1).get(0); if
	 *                       (img != null) { imgUrl = img.getImgUrl(); } //
	 *                       首页推荐商品 JSONArray productArray = new JSONArray();
	 *                       PlatformBlockComment block =
	 *                       blockCommentService.selectByBlock("3003"); if
	 *                       (block != null) { List<ProductInfo> productlist =
	 *                       productService
	 *                       .selectProductByIds(block.getBusinessIds
	 *                       ().split(","), Channel.Product.getValue(),
	 *                       Constants.PRODUCT_DEFAULT_IMG_SIZE, "1"); for
	 *                       (ProductInfo p : productlist) { JSONObject obj =
	 *                       new JSONObject(); obj.put("productId", p.getId());
	 *                       obj.put("productName", p.getName());
	 *                       obj.put("image", Constants.IMAGE_SITE_URL +
	 *                       p.getProductPhoto()); obj.put("price",
	 *                       p.getPrice()); obj.put("itemUrl", Constants.WEB_URL
	 *                       + "/product/item/" + p.getId());
	 *                       productArray.add(obj); } }
	 * 
	 *                       // 首页活动 List<PlatformGameTopic> topicList; if
	 *                       (gameCodeIn == -1) { topicList =
	 *                       topicService.selectAppIndexAll(); } else {
	 *                       topicList =
	 *                       topicService.selectAppIndexList(gameCodeIn); }
	 * 
	 *                       for (PlatformGameTopic p : topicList) {
	 *                       p.setIcon(Constants.IMAGE_SITE_URL + p.getIcon());
	 *                       p.setUrl(Constants.WEB_URL + "/wap/topic/" +
	 *                       p.getId()); } JsonConfig config = new JsonConfig();
	 *                       config.setExcludes(new String[] { "gameCode",
	 *                       "isTop" }); JSONArray topicArray =
	 *                       JSONArray.fromObject(topicList, config);
	 * 
	 *                       JSONObject data = new JSONObject();
	 *                       data.put("imgUrl", imgUrl); data.put("products",
	 *                       productArray); data.put("topics", topicArray);
	 *                       JsonResult jsonResult = new JsonResult();
	 *                       jsonResult.setData(data);
	 *                       DataEyeAgent.accountActive("",
	 *                       form.getDeviceId()+"", form.getOs(),
	 *                       form.getDeviceName(), form.getIp(), "",
	 *                       form.getOsVersion(), form.getScreenResolution());
	 *                       return jsonResult; } catch (Exception e) {
	 *                       e.printStackTrace(); logger.info(e.getMessage());
	 *                       return buildFormError("error#获取数据失败."); } }
	 */

	/**
	 * app首页
	 * 
	 * @param request
	 * @param response
	 * @throws ActionException
	 * 
	 */
	@RequestMapping(value = "/platform/app/index", method = { POST })
	public @ResponseBody
	Object index(BaseForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject data = new JSONObject();

			// 销量榜
			PlatformBlockComment param = new PlatformBlockComment();
			JSONArray productTopList = null;
			CbaseHelper cache = CbaseHelper.getInstance();
			Object cacheProductTopList = cache.get("productTopList");
			if (cacheProductTopList != null)
				productTopList = (JSONArray) cacheProductTopList;
			if (productTopList == null) {
				param.setBlockId("5001");
				productTopList = new JSONArray();
				List<PlatformBlockComment> list1 = blockCommentService.selectByEntity(param);
				if (!list1.isEmpty()) {
					PlatformBlockComment model = list1.get(0);
					String ids = model.getBusinessIds();
					if (ids != null && !"".equals(ids)) {
						String[] items = ids.split(",");
						items = (String[]) ArrayUtils.subarray(items, 0, 4);
						for (String item : items) {
							JSONObject obj = new JSONObject();
							String[] pas = item.split("-");
							obj.put("productName", productService.select(Integer.valueOf(pas[0])).getName());
							obj.put("productId", pas[0]);
							obj.put("daySum", pas[1]);
							obj.put("weekSum", pas[2]);
							productTopList.add(obj);
						}
					}
				}
				cache.add("productTopList", 600, productTopList);
			}

			data.put("productTopList", productTopList);

			// 土豪榜
			JSONArray userList = null;
			Object cacheUserList = cache.get("userList");
			if (cacheUserList != null)
				userList = (JSONArray) cacheUserList;
			if (userList == null || userList.isEmpty()) {
				userList = new JSONArray();
				param.setBlockId("5003");
				List<PlatformBlockComment> list2 = blockCommentService.selectByEntity(param);
				if (!list2.isEmpty()) {
					PlatformBlockComment model = list2.get(0);
					String ids = model.getBusinessIds();
					if (ids != null && !"".equals(ids)) {
						String[] items = ids.split(",");
						items = (String[]) ArrayUtils.subarray(items, 0, 4);
						ArrayList<String> userIds = new ArrayList<String>();
						ArrayList<String> amountItems = new ArrayList<String>();
						for (String it : items) {
							userIds.add(it.split("-")[0]);
							amountItems.add(it.split("-")[1]);
						}

						String[] idsParams = new String[items.length];
						List<User> users = userService.selectByIds(userIds.toArray(idsParams));
						if (users != null && !users.isEmpty()) {
							for (int i = 0; i < users.size(); i++) {
								User u = users.get(i);
								JSONObject obj = new JSONObject();
								obj.put("userName", u.getUserName());
								obj.put("userId", u.getId());
								obj.put("amount", amountItems.get(i));
								userList.add(obj);
							}
						}
					} else {
						List<com.alibaba.fastjson.JSONObject> tops = userService.selectCostTopQuantity(5);
						for (com.alibaba.fastjson.JSONObject json : tops) {
							userList.add(json.toJSONString());
						}
					}
				}
				cache.add("userList", 600, userList);
			}
			data.put("userList", userList);

			// 汇率列表
			JSONArray exchangeList = null;
			Object cacheExchangeList = cache.get("exchangeList");
			if (cacheExchangeList != null)
				exchangeList = (JSONArray) cacheExchangeList;
			if (exchangeList == null) {
				exchangeList = new JSONArray();
				List<PlatformGameApp> list3 = gameAppService.selectAll();
				for (PlatformGameApp g : list3) {
					JSONObject obj = new JSONObject();
					obj.put("gameName", g.getName());
					obj.put("gameIcon", Constants.IMAGE_SITE_URL + g.getIcon());
					obj.put("gameCode", g.getCode());
					obj.put("trend", g.getTrend());
					obj.put("exchange", g.getExchange());
					exchangeList.add(obj);
				}
				cache.add("exchangeList", 30 * 60, exchangeList);
			}
			data.put("exchangeList", exchangeList);

			// 兑换规则
			String rule = SettingListener.getValue("text_exchange_rule");
			data.put("exchangeRule", rule);

			JsonResult jsonResult = new JsonResult();
			jsonResult.setData(data);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#获取数据失败.");
		}
	}

	/**
	 * 个人买入榜列表
	 * 
	 * @param request
	 * @param response
	 * @throws ActionException
	 * 
	 */
	@RequestMapping(value = "/platform/app/buyList", method = { POST })
	public @ResponseBody
	Object personalList(Integer type, Integer userId, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 个人销量榜
			if (type == 1)
				return personBuy();
			// 公会
			else
				return guildBuy(userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#获取数据失败.");
		}
	}

	private JsonResult personBuy() {
		PlatformBlockComment param = new PlatformBlockComment();
		JSONArray dataList = new JSONArray();
		CbaseHelper cache = CbaseHelper.getInstance();
		Object cachePersonDataList = cache.get("personDataList");
		if (cachePersonDataList != null) {
			dataList = (JSONArray) cachePersonDataList;
			if (!dataList.isEmpty()) {
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(dataList);
				return jsonResult;
			}
		}
		param.setBlockId("5003");
		List<PlatformBlockComment> list = blockCommentService.selectByEntity(param);
		if (list != null && !list.isEmpty()) {
			PlatformBlockComment model = list.get(0);
			String ids = model.getBusinessIds();
			if (ids != null && !"".equals(ids)) {
				String[] items = ids.split(",");
				ArrayList<String> userIds = new ArrayList<String>();
				ArrayList<String> amountItems = new ArrayList<String>();
				for (String it : items) {
					userIds.add(it.split("-")[0]);
					amountItems.add(it.split("-")[1]);
				}

				String[] idsParams = new String[items.length];
				List<User> users = userService.selectByIds(userIds.toArray(idsParams));
				if (users != null && !users.isEmpty()) {
					for (int i = 0; i < users.size(); i++) {
						User u = users.get(i);
						JSONObject obj = new JSONObject();
						obj.put("userId", u.getId());
						obj.put("nickName", u.getNickName() == null ? u.getUserName() : u.getNickName());
						if (u.getHeadIcon() != null && !"".equals(u.getHeadIcon())) {
							obj.put("avatar", Constants.IMAGE_SITE_URL + u.getHeadIcon());
						}
						// 用户组
						UserVipGroup group = CacheListener.getUserVipGroup(u.getGroupId());
						JSONObject level = new JSONObject();
						level.put("groupName", group.getName());
						level.put("icon", CommonUtils.isNullEmpty(group.getIcon()) ? "" : Constants.IMAGE_SITE_URL + group.getIcon());
						obj.put("group", level);
						obj.put("amount", amountItems.get(i));
						dataList.add(obj);
					}
				}
			} else {// 若没有推荐 获取真实数据
				List<com.alibaba.fastjson.JSONObject> userList = userService.selectCostTopQuantity(50);
				for (com.alibaba.fastjson.JSONObject json : userList) {
					JSONObject obj = new JSONObject();
					obj.put("userId", json.getInteger("userId"));
					obj.put("nickName", json.get("userName"));
					String avatar = json.getString("avatar");
					if (!CommonUtils.isNullEmpty(avatar))
						obj.put("avatar", Constants.IMAGE_SITE_URL + avatar);
					UserVipGroup group = CacheListener.getUserVipGroup(json.getInteger("groupId"));
					JSONObject groupView = new JSONObject();
					groupView.put("groupName", group.getName());
					groupView.put("icon", CommonUtils.isNullEmpty(group.getIcon()) ? "" : Constants.IMAGE_SITE_URL + group.getIcon());
					obj.put("group", groupView);
					obj.put("amount", json.getLong("amount"));
					dataList.add(obj);
				}
			}
			cache.add("personDataList", 600, dataList);

		} else {
			return ResultHandler.jsonResult("error#暂无数据.");
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(dataList);
		return jsonResult;
	}

	private JsonResult guildBuy(Integer userId) {
		PlatformBlockComment param = new PlatformBlockComment();
		JSONArray dataList = new JSONArray();
		CbaseHelper cache = CbaseHelper.getInstance();
		Object cacheGuildDataList = cache.get("guildDataList");
		if (cacheGuildDataList != null) {
			dataList = (JSONArray) cacheGuildDataList;
			if (!dataList.isEmpty()) {
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(dataList);
				return jsonResult;
			}
		}
		param.setBlockId("5004");
		List<PlatformBlockComment> list = blockCommentService.selectByEntity(param);
		if (list != null && !list.isEmpty()) {
			PlatformBlockComment model = list.get(0);
			String ids = model.getBusinessIds();
			if (ids != null && !"".equals(ids)) {
				String[] items = ids.split(",");
				ArrayList<String> guildIds = new ArrayList<String>();
				ArrayList<String> amountItems = new ArrayList<String>();
				for (String it : items) {
					guildIds.add(it.split("-")[0]);
					amountItems.add(it.split("-")[1]);
				}
				String[] idsParams = new String[items.length];
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("array", guildIds.toArray(idsParams));
				params.put("userId", userId);
				List<GuildInfo> guildList = guildService.selectByIds(params);
				if (guildList != null && !guildList.isEmpty()) {
					for (int i = 0; i < guildList.size(); i++) {
						JSONObject obj = new JSONObject();
						GuildInfo g = guildList.get(i);
						obj.put("id", g.getId());
						obj.put("icon", g.getIcon());
						obj.put("name", g.getName());
						if (g.getIcon() != null && !"".equals(g.getIcon())) {
							obj.put("icon", Constants.IMAGE_SITE_URL + g.getIcon());
						}
						obj.put("levelName", g.getLevelName());
						obj.put("memberCount", g.getMemberCount());
						obj.put("slogan", g.getSlogan());
						obj.put("amount", amountItems.get(i));
						dataList.add(obj);
					}
				}

			} else {
				List<com.alibaba.fastjson.JSONObject> guilds = guildService.selectCostTopQuantity(50);
				for (com.alibaba.fastjson.JSONObject json : guilds) {
					String icon = json.getString("icon");
					if (!CommonUtils.isNullEmpty(icon)) {
						icon = Constants.IMAGE_SITE_URL + icon;
						json.put("icon", icon);
					}
					dataList.add(json.toJSONString());
				}
			}
			cache.add("guildDataList", 600, dataList);
		} else {
			return ResultHandler.jsonResult("error#暂无数据.");
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(dataList);
		return jsonResult;
	}

	/**
	 * app商城
	 * 
	 * @param request
	 * @param response
	 * @throws ActionException
	 */
	@RequestMapping(value = "/platform/shop/index", method = { POST })
	public @ResponseBody
	Object shopIndex(Integer type, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 商城推荐商品
			String blockId = "";
			String descValue = "";
			if (type == 1) {
				blockId = "3001";
				descValue = SettingListener.getValue("personShopDesc");
			} else {
				blockId = "3003";
				descValue = SettingListener.getValue("guildShopDesc");
			}
			JSONObject data = new JSONObject();
			JSONArray TJArray = new JSONArray();
			PlatformBlockComment TJblock = blockCommentService.selectByBlock(blockId);
			if (TJblock != null) {
				List<ProductInfo> TJlist = productService.selectProductByIds(TJblock.getBusinessIds().split(","), Channel.Product.getValue(), Constants.PRODUCT_DEFAULT_IMG_SIZE, "1");
				for (ProductInfo p : TJlist) {
					JSONObject obj = new JSONObject();
					obj.put("productId", p.getId());
					obj.put("productName", p.getName());
					obj.put("image", Constants.IMAGE_SITE_URL + p.getProductPhoto());
					obj.put("price", p.getPrice());
					obj.put("itemUrl", Constants.WEB_URL + "/product/item/" + p.getId());
					TJArray.add(obj);
				}
			}
			data.put("products", TJArray);
			// 规则
			// PlatformDict dictParam = new PlatformDict();
			// dictParam.setCode(ruleCode);
			// List<PlatformDict> list4 = dictService.selectByEntity(dictParam);
			// if (!list4.isEmpty()) {
			// dictParam = list4.get(0);
			// data.put("shopRule", dictParam.getValue());
			// }
			data.put("shopRule", descValue);

			JsonResult jsonResult = new JsonResult();
			jsonResult.setData(data);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#获取数据失败.");
		}
	}

	/**
	 * 推荐公会
	 * 
	 * @param request
	 * @param response
	 * @throws ActionException
	 */
	@RequestMapping(value = "/platform/app/guildRecommend", method = { POST })
	public @ResponseBody
	Object guildRecommend(Integer userId, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 公会推荐榜
			PlatformBlockComment param = new PlatformBlockComment();
			param.setBlockId("5005");
			List<PlatformBlockComment> list = blockCommentService.selectByEntity(param);
			JSONArray dataList = new JSONArray();
			if (list != null && !list.isEmpty()) {
				PlatformBlockComment model = list.get(0);
				String ids = model.getBusinessIds();
				Map<String, Object> params = new HashMap<String, Object>();
				List<GuildInfo> guildList;
				if (ids != null && !"".equals(ids)) {
					String[] items = ids.split(",");
					params.put("array", items);
					params.put("userId", userId);
					guildList = guildService.selectByIds(params);
				} else {
					List<com.alibaba.fastjson.JSONObject> guilds = guildService.selectCostTopQuantity(50);
					List<Integer> idsList = new ArrayList<Integer>();
					for (com.alibaba.fastjson.JSONObject json : guilds) {
						idsList.add(json.getInteger("id"));
					}
					params.put("array", idsList.toArray());
					guildList = guildService.selectByIds(params);
				}
				if (guildList != null && !guildList.isEmpty()) {

					for (GuildInfo g : guildList) {
						JSONObject obj = new JSONObject();
						obj.put("id", g.getId());
						obj.put("name", g.getName());
						if (g.getIcon() != null && !"".equals(g.getIcon())) {
							obj.put("icon", Constants.IMAGE_SITE_URL + g.getIcon());
						}
						obj.put("slogan", g.getSlogan());
						obj.put("joined", g.getJoined());
						dataList.add(obj);
					}
				}
			} else {
				return buildFormError("error#没有可推荐公会.");
			}
			JsonResult jsonResult = new JsonResult();
			jsonResult.setData(dataList);
			return jsonResult;

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return buildFormError("error#获取数据失败.");
		}
	}
}

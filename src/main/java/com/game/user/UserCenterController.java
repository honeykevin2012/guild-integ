package com.game.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
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
import com.game.common.MessageConsValue;
import com.game.common.basics.ApplicationContextLoader;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.basics.security.DES3Utils;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;
import com.game.games.SettleUserGameHelper;
import com.game.games.transfer.SyncQuery;
import com.game.guild.domain.GuildInfo;
import com.game.guild.persistence.service.GuildInfoService;
import com.game.guild.persistence.service.GuildItemVirtualService;
import com.game.init.CacheListener;
import com.game.platform.domain.MessageReader;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserEditTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.platform.persistence.service.MessageReaderService;
import com.game.platform.persistence.service.PlatformGameAppService;
import com.game.user.domain.User;
import com.game.user.domain.User.UserDefault;
import com.game.user.domain.UserAddressInfo;
import com.game.user.domain.UserDataItems;
import com.game.user.domain.UserGameDeductingProcess;
import com.game.user.domain.UserGameResult;
import com.game.user.domain.UserOrder;
import com.game.user.domain.UserOrderItem;
import com.game.user.domain.UserVipGroup;
import com.game.user.domain.form.UserCenterForm;
import com.game.user.domain.form.UserInfoForm;
import com.game.user.persistence.dao.UserCreditsDao;
import com.game.user.persistence.service.UserAddressInfoService;
import com.game.user.persistence.service.UserDataItemsService;
import com.game.user.persistence.service.UserGameDeductingProcessService;
import com.game.user.persistence.service.UserOrderItemService;
import com.game.user.persistence.service.UserOrderService;
import com.game.user.persistence.service.UserService;
import com.game.user.persistence.service.UserVipGroupService;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class UserCenterController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserCenterController.class);
	@Autowired
	private UserService userService;
	@Autowired
	UserOrderService userOrderService;
	@Autowired
	UserOrderItemService userOrderItemService;
	@Autowired
	UserVipGroupService userVipGroupService;
	@Autowired
	PlatformGameAppService platformGameAppService;
	@Autowired
	GuildInfoService guildInfoService;
	@Autowired
	GuildItemVirtualService guildItemVirtualService;
	// @Autowired
	// GuildItemGiveService guildItemGiveService;
	@Autowired
	UserGameDeductingProcessService userGameDeductingProcessService;
	@Autowired
	MessageReaderService messageReaderService;
	@Autowired
	private UserAddressInfoService addressService;
	@Autowired
	private UserDataItemsService userDataItemsService;

	@RequestMapping(value = "/user/center/{u:\\d+}", method = { POST })
	public @ResponseBody
	Object profile(@Valid @ModelAttribute("userCenterForm") UserCenterForm userCenterForm, BindingResult result, @PathVariable Integer u, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				try {
					String userId = DES3Utils.decryptThreeDESECB(userCenterForm.getToken());
					if (!Integer.valueOf(userId).equals(u)) {
						return ResultHandler.bindResult("error#非法请求.");
					}
				} catch (Exception e) {
					e.printStackTrace();
					return ResultHandler.bindResult("error#非法请求.");
				}
				JsonConfig config = new JsonConfig();
				config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
				JSONObject profile = new JSONObject();
				
				User user = userService.select(u);
				if(user == null || user.getUserName() == null) {
					JsonResult dataResult = new JsonResult();
					dataResult.setData(profile);
					return dataResult;
				}
				profile = JSONObject.fromObject(profile, config);
				profile.put("userId", user.getId());
				profile.put("avatar", CommonUtils.isNullEmpty(user.getHeadIcon()) ? "" : Constants.IMAGE_SITE_URL + user.getHeadIcon());
				profile.put("nickName", CommonUtils.isNullEmpty(user.getNickName()) ? user.getUserName() : user.getNickName());
				profile.put("mood", user.getMood());
				profile.put("balance", user.getBalance());// 钱包余额
				profile.put("exp", user.getExp());
				profile.put("point", user.getPoint());
				profile.put("phone", user.getPhone());
				profile.put("qq", user.getQq());
				profile.put("email", user.getEmail());
				profile.put("location", user.getLocationName() == null ? "" : user.getLocationName());
				profile.put("sex", user.getSex());
				profile.put("userName", user.getUserName());
				profile.put("clientIp", user.getClientIp());
				profile.put("registerType", user.getRegisterType());
				profile.put("birthday", user.getBirthday() == null ? "" : DateUtils.format(user.getBirthday(), "yyyy-MM-dd"));
				profile.put("msgCount", messageReaderService.selectNewMsgCount(u));
				profile.put("amount", user.getAmount());//消费

				// 用户组
				//UserVipGroup group = userVipGroupService.select(user.getGroupId());
				UserVipGroup group = user.getGroup();
				JSONObject level = new JSONObject();
				level.put("groupName", group.getName());
				level.put("icon", CommonUtils.isNullEmpty(group.getIcon()) ? "" : Constants.IMAGE_SITE_URL + group.getIcon());
				profile.put("group", level);
				Double levelRate = 0.00;
				//UserVipGroup param = new UserVipGroup();
				//param.setLevel(group.getLevel() + 1);
				//List<UserVipGroup> nextProup = userVipGroupService.selectByEntity(param);
				UserVipGroup nextProup = CacheListener.getUserVipGroup(group.getId() + 1);
				if (nextProup == null) {
					levelRate = 1.00;
				} else {
					UserCreditsDao userCreditsDao = (UserCreditsDao) ApplicationContextLoader.getBean("userCreditsDao");
					Integer sumAmount = userCreditsDao.selectCreditsByUser(user.getId());
					if (sumAmount != null) {
						Integer nextAmount = nextProup.getPoint();
						double rate = (sumAmount - group.getPoint()) / (nextAmount - (double) group.getPoint());
						levelRate = Double.valueOf(new DecimalFormat("######0.00").format(rate));
					}
				}
				if (levelRate > 1) {
					levelRate = 1d;
				}
				profile.put("levelRate", levelRate);
				Long balance = 0l;
				// 所属游戏
				List<UserGameResult> games = SettleUserGameHelper.getGameByUserId(u);
				JSONArray gamesJson = new JSONArray();
				for (UserGameResult g : games) {
					balance = balance + g.getPlatAmount();
					JSONObject game = new JSONObject();
					game.put("id", g.getGameId());
					game.put("name", g.getGameName());
					game.put("icon", g.getGameIcon());
					game.put("sitePageUrl", g.getSitePageUrl());
					gamesJson.add(game);
				}
				profile.put("games", gamesJson);

				// 公会
				List<GuildInfo> guilds = guildInfoService.selectByUserId(u);
				JSONArray guildsJson = new JSONArray();
				for (GuildInfo gu : guilds) {
					JSONObject guild = new JSONObject();
					guild.put("id", gu.getId());
					guild.put("name", gu.getName());
					guild.put("userName", gu.getUserName());
					guild.put("icon", CommonUtils.isNullEmpty(gu.getIcon()) ? "" : Constants.IMAGE_SITE_URL + gu.getIcon());
					guild.put("level", gu.getLevel());
					guild.put("levelName", gu.getLevelName());
					guild.put("createTime", DateUtils.format(gu.getCreateTime(), "yyyy-MM-dd"));
					guild.put("currency", gu.getCurrency());
					guild.put("slogan", gu.getSlogan());
					guild.put("welfare", "");
					guild.put("memberCount", gu.getMemberCount());
					guild.put("welfare", "");
					guild.put("joined", gu.getJoined());
					if (Integer.valueOf(gu.getJoined()) > 0) {
						guild.put("joinCount", gu.getJoinCount());
					}
					guildsJson.add(guild);
				}
				profile.put("guilds", guildsJson);
				JsonResult dataResult = new JsonResult();
				dataResult.setData(profile);

				// 日志
				//DataEyeAgent.pageEvent(user.getId() + "", userCenterForm.getDeviceId(), userCenterForm.getDeviceId(), userCenterForm.getOs(), userCenterForm.getDeviceName(), userCenterForm.getIp(), "", userCenterForm.getOsVersion(), userCenterForm.getScreenResolution(), PageEventEnum.UCENTER);
				return dataResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * @RequestMapping(value = "/user/center/order/notcomplete", method = { POST
	 * }) public @ResponseBody Object waitPay(@Valid
	 * @ModelAttribute("userCenterForm") UserCenterForm form, BindingResult
	 * result, HttpServletRequest request) throws Exception { try { if
	 * (result.hasErrors()) { return
	 * this.buildFormError(result.getAllErrors().get(0).getDefaultMessage()); }
	 * else { List<ProductInfo> list =
	 * productService.selectEntityByCart(form.getUserId(), null,
	 * Constants.PRODUCT_CHANNEL, Constants.PRODUCT_MIN_IMAGE_SIZE, "1");
	 * List<UserCartResult> resultList = new ArrayList<UserCartResult>(); for
	 * (ProductInfo p : list) { UserCartResult cart = new UserCartResult();
	 * cart.setProductId(p.getId()); cart.setProductName(p.getName());
	 * cart.setPrice(p.getPrice()); cart.setCount(p.getCartCount());
	 * cart.setStock(p.getCount());
	 * cart.setImage(CommonUtils.isNullEmpty(p.getProductPhoto()) ? "" :
	 * Constants.IMAGE_SITE_URL + p.getProductPhoto()); resultList.add(cart); }
	 * JsonResult data = new JsonResult(); data.setData(resultList); return
	 * data; } } catch (Exception e) { e.printStackTrace();
	 * logger.info(e.getMessage()); return buildFormError("error#" +
	 * e.getMessage()); } }
	 */

	@RequestMapping(value = "/user/center/order/complete", method = { POST })
	public @ResponseBody
	Object complete(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if (CommonUtils.isNullEmpty(form.getData()))
					return buildFormError("error#" + MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());// 解析data中的请求参数，转化成key
																						// value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if (!legal || !data.containsKey("userId") || !data.containsKey("pn") || !data.containsKey("ps") || !data.containsKey("status"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);

				Integer status = Integer.valueOf(data.get("status").toString());
				Integer userId = Integer.valueOf(data.get("userId").toString());

				PageQuery page = new PageQuery(request);
				Map<String, Object> params = page.getParams();
				if (params == null)
					params = new HashMap<String, Object>();
				String pn = data.get("pn").toString();
				String ps = data.get("ps").toString();
				if (CommonUtils.isNullEmpty(pn))
					return this.buildFormError("error#当前页数不能为空.");
				if (CommonUtils.isNullEmpty(ps))
					return this.buildFormError("error#每页记录数不能为空.");
				page.setPageSize(Integer.valueOf(ps));
				page.setPageNo(Integer.valueOf(pn));
				params.put("userId", userId);
				params.put("channel", Constants.PRODUCT_CHANNEL);
				params.put("picsize", Constants.PRODUCT_MIN_IMAGE_SIZE);
				params.put("isDefault", "1");
				page.setParams(params);
				List<Map<String, Object>> list;
				if (status == 1) {
					list = userOrderService.selectUserOrdersComplete(page);
				} else {
					list = userOrderService.selectUserOrdersFaild(page);
				}

				JSONArray resultList = new JSONArray();
				for (Map<String, Object> o : list) {
					JSONObject order = new JSONObject();
					String orderId = o.get("orderId").toString();
					order.put("orderId", orderId);
					order.put("orderType", o.get("order_type"));
					order.put("status", o.get("status"));
					order.put("deliverStatus", o.get("deliver"));
					order.put("amount", o.get("payAmount"));
					order.put("guildName", o.get("guildName"));
					if (o.get("guildName")!=null) {
						order.put("from", o.get("guildName"));
					}
					order.put("address", o.get("address"));
					order.put("acceptName", o.get("acceptName"));
					
					order.put("orderItemUrl", Constants.WEB_URL + "/user/order/item/" + orderId);
					
					JSONArray pList = new JSONArray();
						String[] products = StringUtils.split(o.get("items").toString(), "#$");
						for (String p : products) {
							String[] pinfo = p.split("!@");
							if (pinfo == null || pinfo.length != 6)
								continue;
							JSONObject product = new JSONObject();
							product.put("productId", pinfo[0]);
							product.put("productName", pinfo[1]);
							product.put("image", CommonUtils.isNullEmpty(pinfo[2]) ? "" : Constants.IMAGE_SITE_URL + pinfo[2]);
							product.put("price", pinfo[3]);
							product.put("count", pinfo[4]);
							product.put("isVirtual", pinfo[5]);
							pList.add(product);
						}
					order.put("products", pList);
					resultList.add(order);
				}
				JsonResult jsonResult = new JsonResult();
				JSONObject json = new JSONObject();
				json.put("pn", page.getPageNo());
				json.put("totalPage", page.getTotalPages());
				json.put("orders", resultList);
				jsonResult.setData(json);
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	// @RequestMapping(value = "/user/center/myItem", method = { POST })
	// public @ResponseBody
	// Object myItem(@Valid @ModelAttribute("userCenterForm") UserCenterForm
	// userCenterForm, BindingResult result, HttpServletRequest request) {
	// try {
	// if (result.hasErrors()) {
	// return
	// this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
	// } else {
	// String token;
	// try {
	// token = DES3Utils.decryptThreeDESECB(userCenterForm.getToken());
	// } catch (Exception e) {
	// e.printStackTrace();
	// return ResultHandler.bindResult("error#非法请求.");
	// }
	// String[] reqParams = token.split("-");
	// PageQuery page = new PageQuery(request);
	// Map<String, Object> params = page.getParams();
	// if (params == null)
	// params = new HashMap<String, Object>();
	// String pn = reqParams[2];
	// String ps = reqParams[1];
	// String isVirtual = reqParams[3];
	// if (CommonUtils.isNullEmpty(pn))
	// return this.buildFormError("error#当前页数不能为空.");
	// if (CommonUtils.isNullEmpty(ps))
	// return this.buildFormError("error#每页记录数不能为空.");
	// page.setPageSize(Integer.valueOf(ps));
	// page.setPageNo(Integer.valueOf(pn));
	// JSONArray resultList = new JSONArray();
	// if ("1".equals(isVirtual)) {
	// params.put("userId", reqParams[0]);
	// page.setParams(params);
	// List<Map<String, String>> virtualList =
	// guildItemVirtualService.selectMyitem(page);
	// for (Map<String, String> v : virtualList) {
	// JSONObject o = new JSONObject();
	// o.put("guildName", v.get("guildName"));
	// o.put("gameName", v.get("gameName"));
	// o.put("name", v.get("itemName"));
	// o.put("remark", v.get("remark"));
	// o.put("code", v.get("code"));
	// resultList.add(o);
	// }
	// } else {
	// params.put("userId", reqParams[0]);
	// page.setParams(params);
	// List<Map<String, String>> giveList =
	// guildItemGiveService.selectRealMyitem(page);
	// for (Map<String, String> g : giveList) {
	// JSONObject o = new JSONObject();
	// o.put("id", g.get("id"));
	// o.put("guildId", g.get("guildId"));
	// o.put("guildName", g.get("guildName"));
	// o.put("name", g.get("itemName"));
	// o.put("giveTime", g.get("giveTime"));
	// o.put("status", g.get("status"));
	// o.put("productId", g.get("productId"));
	// o.put("count", g.get("count"));
	// resultList.add(o);
	// }
	// }
	//
	// JsonResult jsonResult = new JsonResult();
	// JSONObject json = new JSONObject();
	// json.put("pn", page.getPageNo());
	// json.put("totalPage", page.getTotalPages());
	// json.put("items", resultList);
	// jsonResult.setData(json);
	// return jsonResult;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error(e.getMessage());
	// return buildFormError("error#" + e.getMessage());
	// }
	// }

	@RequestMapping(value = "/user/center/order/item", method = { POST })
	public @ResponseBody
	Object orderItem(@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String formData = form.getData();
				if (CommonUtils.isNullEmpty(formData))
					return ResultHandler.bindResult("error#" + MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());// 解析token中的请求参数，转化成key
																						// value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if (!legal || !data.containsKey("userId") || !data.containsKey("orderId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);

				Integer userId = Integer.valueOf(data.get("userId").toString());
				String paramOrderId = data.get("orderId").toString();
				UserOrder params = new UserOrder();
				params.setUserId(userId);
				params.setOrderId(paramOrderId);
				Map<String, String> orderInfo = userOrderService.selectByOrderId(params);
				if (orderInfo == null || orderInfo.isEmpty())
					return BuildFormErrorUtils.buildFormError("error#订单号" + paramOrderId + "不存在.");
				JSONObject order = new JSONObject();
				String orderId = orderInfo.get("orderId");
				order.put("orderId", orderId);
				order.put("status", orderInfo.get("status"));
				order.put("statusCode", orderInfo.get("statusCode"));
				order.put("amount", orderInfo.get("payAmount"));
				order.put("address", orderInfo.get("address"));
				order.put("acceptName", orderInfo.get("acceptName"));
				order.put("createTime", orderInfo.get("createTime"));
				order.put("mobile", orderInfo.get("mobile"));
				List<UserOrderItem> products = userOrderItemService.selectByOrderId(orderId);
				JSONArray pList = new JSONArray();
				for (UserOrderItem p : products) {
					JSONObject product = new JSONObject();
					product.put("productId", p.getProductId());
					product.put("productName", p.getProductName());
					product.put("image", CommonUtils.isNullEmpty(p.getProductPhoto()) ? "" : Constants.IMAGE_SITE_URL + p.getProductPhoto());
					product.put("price", p.getProductPrice());
					product.put("count", p.getCount());
					product.put("isVirtual", p.getIsVirtual());
					product.put("virtuals", p.getVirtuals());
					pList.add(product);
				}
				order.put("products", pList);
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(order);
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/user/center/order/virtual/item", method = { POST })
	public @ResponseBody
	Object virtualItem(@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String formData = form.getData();
				if (CommonUtils.isNullEmpty(formData))
					return ResultHandler.bindResult("error#" + MessageConsValue.legalMessage);
				Map<String, Object> data = SessionTokenUtils.dataAnalsy(form.getData());// 解析token中的请求参数，转化成key
																						// value形式
				boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(), form.getOs());
				if (!legal || !data.containsKey("userId") || !data.containsKey("orderId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);

				Integer userId = Integer.valueOf(data.get("userId").toString());
				String paramOrderId = data.get("orderId").toString();
				UserOrder params = new UserOrder();
				params.setUserId(userId);
				params.setOrderId(paramOrderId);
				Map<String, Object> orderInfo = userOrderService.selectVirtualItem(params);
				if (orderInfo == null || orderInfo.isEmpty())
					return BuildFormErrorUtils.buildFormError("error#虚拟物品id号" + paramOrderId + "不存在.");
				JsonResult jsonResult = new JsonResult();
				Object virtualName = orderInfo.get("items");
				jsonResult.setData(String.valueOf(virtualName == null ? "" : virtualName));
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/user/center/notice/{u:\\d+}", method = { POST })
	public @ResponseBody
	Object centerNotice(@Valid @ModelAttribute("baseForm") BaseForm form, BindingResult result, @PathVariable Integer u, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				List<MessageReader> noticeList = messageReaderService.selectByUserId(u);
				JSONArray notices = new JSONArray();
				for (MessageReader n : noticeList) {
					JSONObject obj = new JSONObject();
					obj.put("type", n.getTypeEnum().getName());
					obj.put("color", n.getTypeEnum().getColor());
					obj.put("content", n.getContent());
					notices.add(obj);
				}
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(notices);
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	@RequestMapping(value = "/user/center/modifyInfo", method = { POST })
	public @ResponseBody
	Object modifyInfo(@Valid @ModelAttribute("userInfoForm") UserInfoForm userInfoForm, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				try {
					String userId = DES3Utils.decryptThreeDESECB(userInfoForm.getToken());
					if (!Integer.valueOf(userId).equals(userInfoForm.getUserId())) {
						return ResultHandler.bindResult("error#非法请求.");
					}
				} catch (Exception e) {
					e.printStackTrace();
					return ResultHandler.bindResult("error#非法请求.");
				}
				User user = userService.select(userInfoForm.getUserId());
				if (user == null) return ResultHandler.bindResult("error#用户不存在.");
				String userName = StringUtils.trim(userInfoForm.getUserName());
				if (!CommonUtils.isNullEmpty(userName) && UserDefault.AT_REGISTER.getValue().equals(user.getRegisterType()) && !user.getUserName().equals(userName)) {
					User param = new User();
					param.setUserName(userName);
					List<User> ulist = userService.selectByEntity(param);
					if (ulist != null && !ulist.isEmpty()) return ResultHandler.bindResult("error#该用户名已存在.");
					user.setUserName(userName);
					user.setRegisterType(UserDefault.MT_REGISTER.getValue());
				}
				if (userInfoForm.getHeadIcon() != null && !"".equals(userInfoForm.getHeadIcon())) {
					user.setHeadIcon(userInfoForm.getHeadIcon());
				}
				if (user.getLocationId() != null) {
					UserAddressInfo address = addressService.select(user.getLocationId());
					if(address != null){
						address.setAddress(userInfoForm.getLocation());
						addressService.update(address);
					}else {
						UserAddressInfo add = new UserAddressInfo();
						add.setAddress(userInfoForm.getLocation());
						addressService.insert(add);
						user.setLocationId(add.getId());
					}
				}else{
					UserAddressInfo add = new UserAddressInfo();
					add.setAddress(userInfoForm.getLocation());
					addressService.insert(add);
					user.setLocationId(add.getId());
				}
				Set<String> set = new HashSet<String>();
				UserDataItems data = userDataItemsService.select(user.getId());
				if(data == null){
					data = new UserDataItems();
					data.setUserId(user.getId());
					userDataItemsService.insert(data);
				}
				if (data.getMobile() == null && !CommonUtils.isNullEmpty(userInfoForm.getPhone())) {
					set.add("phone");
					data.setMobile(userInfoForm.getPhone());
				}
				if (data.getSex() == null && !CommonUtils.isNullEmpty(userInfoForm.getSex())) {
					set.add("sex");
					data.setSex(userInfoForm.getSex());
				}
				if (data.getQq() == null && !CommonUtils.isNullEmpty(userInfoForm.getQq())) {
					set.add("qq");
					data.setQq(userInfoForm.getQq());
				}
				if (data.getAvatar() == null && !CommonUtils.isNullEmpty(userInfoForm.getHeadIcon())) {
					set.add("avatar");
					data.setAvatar(userInfoForm.getHeadIcon());
				}
				if (data.getNickName() == null && !CommonUtils.isNullEmpty(userInfoForm.getNickName())) {
					set.add("nickname");
					data.setNickName(userInfoForm.getNickName());
				}
				if (data.getEmail() == null && !CommonUtils.isNullEmpty(userInfoForm.getEmail())) {
					set.add("email");
					data.setEmail(userInfoForm.getEmail());
				}
				if (data.getBrithday() == null && !CommonUtils.isNullEmpty(userInfoForm.getBirthday())) {
					set.add("birthday");
					data.setBrithday(userInfoForm.getBirthday());
				}
				if (data.getAddress() == null && !CommonUtils.isNullEmpty(userInfoForm.getLocation())) {
					set.add("address");
					data.setAddress(userInfoForm.getLocation());
				}
				user.setNickName(userInfoForm.getNickName());
				user.setPhone(userInfoForm.getPhone());
				user.setEmail(userInfoForm.getEmail());
				user.setSex(userInfoForm.getSex());
				user.setQq(userInfoForm.getQq());
				user.setMood(userInfoForm.getMood());
				user.setBirthday(DateUtils.format(userInfoForm.getBirthday(), "yyyy-MM-dd"));
				userService.updateUserInfo(user);
				// 奖励消息
				if (!set.isEmpty()) {
					MessageCore core = new MessageCore();
					core.setAdapter(new MessageUserEditTemplate(set)).transfer(new PracticVirtual(), user.getId()).send();
					userDataItemsService.update(data);
				}
				//DataEyeAgent.bindAccountInfo(user.getUserName(), user.getId() + "", userInfoForm.getOs(), user.getEmail(), user.getPhone());
				return ResultHandler.bindResult("ok#用户信息修改成功.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	// /*
	// *
	// * 物品接收
	// */
	// @RequestMapping(value = "/user/center/receiveItem", method = { POST })
	// public @ResponseBody
	// Object receiveItem(@Valid @ModelAttribute("baseForm") BaseForm form,
	// HttpServletRequest request) throws Exception {
	// try {
	// if (CommonUtils.isNullEmpty(form.getData()))
	// return buildFormError("error#" + MessageConsValue.legalMessage);
	// Map<String, Object> data =
	// SessionTokenUtils.dataAnalsy(form.getData());// 解析data中的请求参数，转化成key
	// // value形式
	// if (data == null)
	// buildFormError("error#" + MessageConsValue.legalMessage);
	// boolean legal = SessionTokenUtils.validateLogin(data, form.getNuid(),
	// form.getOs());
	// if (!legal || !data.containsKey("userId") || !data.containsKey("guildId")
	// || !data.containsKey("giveId") || !data.containsKey("productId") ||
	// !data.containsKey("count") || !data.containsKey("addressId"))
	// return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
	//
	// Integer guildId = Integer.valueOf(data.get("guildId").toString());
	// Integer userId = Integer.valueOf(data.get("userId").toString());
	// Integer giveId = Integer.valueOf(data.get("giveId").toString());
	// Integer productId = Integer.valueOf(data.get("productId").toString());
	// Integer count = Integer.valueOf(data.get("count").toString());
	// Integer addressId = Integer.valueOf(data.get("addressId").toString());
	// return guildItemGiveService.updateReceiveItem(userId, giveId, guildId,
	// productId, count, addressId, request);
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error(e.getMessage());
	// return buildFormError("error#" + e.getMessage());
	// }
	// }
	//
	/*
	 * 
	 * 订单支付失败原因列表
	 */
	@RequestMapping(value = "/user/center/order/payFaildList", method = { POST })
	public @ResponseBody
	Object payFaildList(String userId, String orderId, HttpServletRequest request) throws Exception {
		try {
			if (orderId == null) {
				return buildFormError("error#订单号不能为空");
			}
			if (userId == null) {
				return buildFormError("error#用户id不能为空");
			}
			UserGameDeductingProcess param = new UserGameDeductingProcess();
			param.setOrderId(orderId);
			param.setUserId(Integer.valueOf(userId));
			List<UserGameDeductingProcess> list = userGameDeductingProcessService.selectPayFaildList(param);
			if(list==null||list.isEmpty()){
				return buildFormError("error#游戏支付数据为空");
			}
			JSONArray resultList = new JSONArray();
			for (UserGameDeductingProcess p : list) {
				JSONObject obj = new JSONObject();
				obj.put("gameIcon", Constants.IMAGE_SITE_URL + p.getGameIcon());
				obj.put("gameName", p.getGameName());
				obj.put("serverName", p.getServerName());
				obj.put("roleName", p.getRoleName());
				obj.put("amount", p.getExchangeNbGold());
				resultList.add(obj);
			}
			JsonResult jsonResult = new JsonResult();
			jsonResult.setData(resultList);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}

	/*
	 * 
	 * 订单重新支付界面数据列表
	 */
	@RequestMapping(value = "/user/center/order/repayView", method = { POST })
	public @ResponseBody
	Object repayView(String userId, String orderId, HttpServletRequest request) throws Exception {
		try {
			if (orderId == null) {
				return buildFormError("error#订单号不能为空");
			}
			if (userId == null) {
				return buildFormError("error#用户id不能为空");
			}

			Long payAmount = 0l;
			// 成功列表
			UserGameDeductingProcess param = new UserGameDeductingProcess();
			param.setOrderId(orderId);
			param.setUserId(Integer.valueOf(userId));
			List<UserGameDeductingProcess> success = userGameDeductingProcessService.selectPaySuccessList(param);

			JSONArray successList = new JSONArray();
			if (success != null && !success.isEmpty()) {
				for (UserGameDeductingProcess p : success) {
					payAmount = payAmount + p.getExchangeNbGold();
					JSONObject obj = new JSONObject();
					obj.put("gameIcon", Constants.IMAGE_SITE_URL + p.getGameIcon());
					obj.put("gameName", p.getGameName());
					obj.put("serverName", p.getServerName());
					obj.put("roleName", p.getRoleName());
					obj.put("amount", p.getExchangeNbGold());
					successList.add(obj);
				}
			}
			// 失败列表
			List<UserGameDeductingProcess> failed = userGameDeductingProcessService.selectPayFaildList(param);

			JSONArray faildList = new JSONArray();
			if (failed != null && !failed.isEmpty()) {
				for (UserGameDeductingProcess p : failed) {
					JSONObject obj = new JSONObject();
					obj.put("gameIcon", Constants.IMAGE_SITE_URL + p.getGameIcon());
					obj.put("gameName", p.getGameName());
					obj.put("serverName", p.getServerName());
					obj.put("roleName", p.getRoleName());
					obj.put("amount", p.getExchangeNbGold());
					faildList.add(obj);
				}
			}
			// 游戏信息
			List<UserGameResult> gameList = null;
			if (Constants.REQUEST_FLAG.equals(Constants.QUERY_USER_GAME_FLAG)) {// 实时从db中查询游戏金币信息
				gameList = SettleUserGameHelper.getGameByUserId(Integer.valueOf(userId));
			} else {// 实时http请求游戏服务器查询游戏金币信息
				SyncQuery sync = new SyncQuery(Integer.valueOf(userId));
				gameList = sync.syncRequest();
			}

			// 订单信息
			UserOrder params = new UserOrder();
			params.setUserId(Integer.valueOf(userId));
			params.setOrderId(orderId);
			Map<String, String> orderInfo = userOrderService.selectByOrderId(params);
			if (orderInfo == null) {
				return buildFormError("error#订单信息不存在");
			}
			JsonResult jsonResult = new JsonResult();
			JSONObject data = new JSONObject();
			data.put("paySuccessList", successList);
			data.put("payFaildList", faildList);
			data.put("gameList", gameList);
			data.put("totalAmount", orderInfo.get("payAmount"));
			data.put("payAmount", payAmount);
			jsonResult.setData(data);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

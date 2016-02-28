/*
 * @author zhaolianjun
 * created on 2015-01-29
 */

package com.game.user.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.Constants;
import com.game.common.MessageConsValue;
import com.game.common.basics.JsonResult;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildItem;
import com.game.guild.persistence.dao.GuildInfoDao;
import com.game.guild.persistence.dao.GuildItemDao;
import com.game.platform.domain.PlatformGameApp;
import com.game.platform.persistence.dao.PlatformGameAppDao;
import com.game.shop.domain.ProductInfo;
import com.game.shop.helper.DiscountHelper;
import com.game.shop.persistence.dao.ProductInfoDao;
import com.game.user.domain.User;
import com.game.user.domain.UserGameDeductingProcess;
import com.game.user.domain.UserOrder;
import com.game.user.domain.UserOrderItem;
import com.game.user.persistence.dao.UserDao;
import com.game.user.persistence.dao.UserGameDeductingProcessDao;
import com.game.user.persistence.dao.UserOrderDao;
import com.game.user.persistence.dao.UserOrderItemDao;
import com.game.user.persistence.dao.UserShoppingCartDao;
import com.game.user.utils.OrderHelper;
import com.game.user.utils.SessionTokenUtils;

@Service
public class UserOrderService {

	@Autowired
	private UserOrderDao userOrderDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserShoppingCartDao cartDao;
	@Autowired
	private ProductInfoDao productDao;
	@Autowired
	private UserOrderItemDao userOrderItemDao;
	@Autowired
	private GuildInfoDao guildInfoDao;
	@Autowired
	private GuildItemDao guildItemDao;
	@Autowired
	private UserGameDeductingProcessDao userGameDeductingProcessDao;
	@Autowired
	private PlatformGameAppDao platformGameAppDao;

	private static final String ERROR = "error";

	public List<UserOrder> query(PageQuery querys) throws DataAccessException {
		try {
			return userOrderDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<UserOrder> selectAll() throws DataAccessException {
		try {
			return userOrderDao.selectAll();
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<UserOrder> selectByEntity(UserOrder userOrder) throws DataAccessException {
		try {
			return userOrderDao.selectByEntity(userOrder);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public UserOrder select(Integer id) throws DataAccessException {
		try {
			return userOrderDao.select(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void insert(UserOrder userOrder) throws DataAccessException {
		try {
			userOrderDao.insert(userOrder);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void update(UserOrder userOrder) throws DataAccessException {
		try {
			userOrderDao.update(userOrder);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void delete(Integer id) throws DataAccessException {
		try {
			userOrderDao.delete(id);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public void deleteSelect(Integer[] ids) throws DataAccessException {
		try {
			userOrderDao.deleteSelect(ids);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public Long selectSumAmount(Integer userId) throws DataAccessException {
		try {
			return userOrderDao.selectSumAmount(userId);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public List<Map<String, Object>> selectUserOrdersComplete(PageQuery querys) throws DataAccessException {
		try {
			return userOrderDao.selectUserOrdersComplete(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public Map<String, Object> selectVirtualItem(UserOrder userOrder) throws DataAccessException {
		try {
			return userOrderDao.selectVirtualItem(userOrder);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	public List<Map<String, Object>> selectUserOrdersFaild(PageQuery querys) throws DataAccessException {
		try {
			return userOrderDao.selectUserOrdersFaild(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	public Map<String, String> selectByOrderId(UserOrder userOrder) throws DataAccessException {
		try {
			return userOrderDao.selectByOrderId(userOrder);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}

	/**
	 * 平台商品订单提交
	 * 
	 * @param token
	 * @param MAX_TIME
	 * @param os
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public Object creatorOrderNew(String data, Integer nuid, String os, HttpServletRequest request) throws DataAccessException {
		try {
			List<Integer> ids = new ArrayList<Integer>();
			// 解析token
			// 返回map######################################################################
			Map<String, Object> result = OrderHelper.dataAnalsy(data, nuid, os);
			if (result.get(ERROR) != null) {
				String error = (String) result.get(ERROR);
				return BuildFormErrorUtils.buildFormError(error);
			}
			Integer userId = Integer.valueOf(result.get("userId").toString());
			if(CommonUtils.isNullEmpty(result.get("addressId").toString())) 
				return BuildFormErrorUtils.buildFormError("请选择收货地址.");
			Integer addressId = Integer.valueOf(result.get("addressId").toString());
			ids = (List<Integer>) result.get("ids");
			Long walletAmount = Long.valueOf(result.get("walletAmount").toString());//来自用户钱包的NB
			
			String gameDeducts = result.get("gameDeducts").toString();
			
			String orderId = OrderHelper.createOrderNumber();
			User user = userDao.select(userId);
			if (user == null)
				return BuildFormErrorUtils.buildFormError("非法用户请求.");
			if (user.getGroupId() == null)
				return BuildFormErrorUtils.buildFormError("所属用户组非法.");
			JsonResult jsonResult = new JsonResult();
			// 查询用户购物车中所有数据（需结算数据）
			
			if(Long.valueOf(user.getBalance()) <  Math.abs(walletAmount)) return BuildFormErrorUtils.buildFormError("您的钱包余额不足, 当前来自充值的NB余额为:" + user.getBalance() + ".");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("channel", Constants.PRODUCT_CHANNEL);
			map.put("picsize", Constants.PRODUCT_MIN_IMAGE_SIZE);
			map.put("productIds", ids);
			map.put("isDefault", "1");

			List<ProductInfo> list = productDao.selectEntityByCart(map);// 1为默认图片
			Long totalAmount = 0l;// 订单总金额
			Double discountTotalAmount = 0d;// 实付款金额(折扣后金额)
			if (list != null && list.size() > 0) {
				Set<String> set = new HashSet<String>();
				for (ProductInfo p : list) {
					set.add(p.getType());
				}
				if (set.size() > 1)
					return BuildFormErrorUtils.buildFormError(OrderHelper.BUY_ERROR_MSG);
				if (set.contains("2"))
					return BuildFormErrorUtils.buildFormError("非法购买公会商品.");// 判断物品类型是否为公会物品
				// 验证库存量 与
				// 用户账户余额是否充足########################################################
				// 计算游戏扣除金币总额

				Map<String, String> checkResult = checkBalanceAndStock(list, user.getGroupLevel(), walletAmount,gameDeducts);
				if (checkResult.get(ERROR) != null) {
					String error = (String) checkResult.get(ERROR);
					return BuildFormErrorUtils.buildFormError(error);
				}
				// double ta = Double.valueOf(checkResult.get("totalAmount"));
				totalAmount = Double.valueOf(checkResult.get("totalAmount")).longValue();
				discountTotalAmount = Double.valueOf(checkResult.get("discountTotalAmount"));
				ParamsVO vo = new ParamsVO();
				if(addressId == null || addressId == -1) vo.setAddressId(null);
				else vo.setAddressId(addressId);
				vo.setDiscountTotalAmount(discountTotalAmount);
				vo.setGroupLevel(user.getGroupLevel());
				vo.setList(list);
				vo.setOrderId(orderId);
				vo.setOs(os);
				vo.setRequest(request);
				vo.setTotalAmount(totalAmount);
				vo.setUserId(userId);
				vo.setOrderType("1");// 平台商品订单
				// 扣除金币任务信息入库
				Long leftAmount=totalAmount- Math.abs(walletAmount);
				if(leftAmount>0){//钱包金币不能完全支付订单，需要游戏金币补充
					List<Map<String, String>> deductItems = parseDeducts(gameDeducts);
					Double deductAmount = 0d;
					for (Map<String, String> demap : deductItems) {
						deductAmount = deductAmount + Math.abs(Double.valueOf(demap.get("amount")));
					}
					insertDeducts(leftAmount, deductItems, nuid, orderId);
					vo.setStatus(0);
					user.setBalance(user.getBalance() - Math.abs(walletAmount));
				}else{//钱包金币足够支付订单，无需扣除游戏内金币
					vo.setStatus(1);
					user.setBalance(user.getBalance() - Math.abs(totalAmount));
				}
				// 保存订单信息#########################################################################
				Map<String, String> orderResult = persistenceOrder(vo);
				if (orderResult.get(ERROR) != null) {
					String error = (String) orderResult.get(ERROR);
					return BuildFormErrorUtils.buildFormError(error);
				}

				// 删除用户购物车所有商品
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productIds", ids);
				params.put("userId", userId);
				cartDao.deleteByUserId(params);
				//userDao.updateUserVipGroup(userId);
				userDao.updateBalance(user);//更新用户余额
				JSONObject json = new JSONObject();
				json.put("totalAmount", totalAmount);
				json.put("realPayAmount", discountTotalAmount);
				json.put("orderId", orderId);
				jsonResult.setData(json);
				return jsonResult;
			} else {
				return BuildFormErrorUtils.buildFormError("购买商品参数异常.商品ID" + ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BuildFormErrorUtils.buildFormError(e.getMessage());
		}
	}

	/**
	 * 重新支付订单
	 * @param data
	 * @param nuid
	 * @param os
	 * @param request
	 * @return
	 */
	public Object orderRePay(String data, Integer nuid, String os, HttpServletRequest request) {

		if (CommonUtils.isNullEmpty(data) || nuid == null || CommonUtils.isNullEmpty(os)) {
			return BuildFormErrorUtils.buildFormError("error#" + MessageConsValue.legalMessage);
		}
		Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);// 解析data中的请求参数，转化成key
																			// value形式
		boolean legal = SessionTokenUtils.validateLogin(dataMap, nuid, os);
		if (!legal || !dataMap.containsKey("userId") || !dataMap.containsKey("orderId") || !dataMap.containsKey("gameDeducts") || !dataMap.containsKey("walletAmount")) {
			return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		}

		Integer userId = Integer.valueOf(dataMap.get("userId").toString());
		String orderId = dataMap.get("orderId").toString();
		String gameDeducts = dataMap.get("gameDeducts").toString();

		Long walletAmount = Long.valueOf(dataMap.get("walletAmount").toString());// 来自用户钱包的NB
		walletAmount = Math.abs(walletAmount);
		User user = userDao.select(userId);
		if (Long.valueOf(user.getBalance()) < walletAmount)
			return BuildFormErrorUtils.buildFormError("您的钱包余额不足, 当前来自充值的NB余额为:" + user.getBalance() + ".");

		// 计算游戏扣除金币总额
		//计算剩余需支付总金额
		Long totalAmount = userOrderDao.selectOrderRemain(orderId);
		Long deductAmount = 0l;

		Long leftAmount = totalAmount - walletAmount;
		if (leftAmount > 0) {//钱包支付金额不够支付，需要游戏补充差价
			List<Map<String, String>> deductItems = parseDeducts(gameDeducts);
			for (Map<String, String> map : deductItems) {
				deductAmount = deductAmount + Math.abs(Double.valueOf(map.get("amount")).longValue());
			}
			if (deductAmount < leftAmount) {
				return BuildFormErrorUtils.buildFormError("error#追加金额不足以支付当前订单,请重新支付");
			}
			insertDeducts(leftAmount, deductItems, nuid, orderId);
			user.setBalance(user.getBalance() - walletAmount);
		} else {//若总金额小于钱包数量，即用钱包支付即可，无需游戏付款
			user.setBalance(user.getBalance() - walletAmount);
		}

		// 更新订单状态
		userOrderDao.updateRepayStatus(orderId);
		userDao.updateBalance(user);// 更新用户余额
		JsonResult jsonResult = new JsonResult();
		JSONObject json = new JSONObject();
		json.put("totalAmount", totalAmount);
		json.put("realPayAmount", totalAmount);
		json.put("orderId", orderId);
		jsonResult.setData(json);
		return jsonResult;
	}
	
	/**
	 * 订单退款
	 * @param data
	 * @param nuid
	 * @param os
	 * @param request
	 * @return
	 */
	public Object orderRefund(String data, Integer nuid, String os, HttpServletRequest request) {

		if (CommonUtils.isNullEmpty(data) || nuid == null || CommonUtils.isNullEmpty(os)) {
			return BuildFormErrorUtils.buildFormError("error#" + MessageConsValue.legalMessage);
		}
		Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);// 解析data中的请求参数，转化成key
																			// value形式
		boolean legal = SessionTokenUtils.validateLogin(dataMap, nuid, os);
		if (!legal || !dataMap.containsKey("userId") || !dataMap.containsKey("orderId")) {
			return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		}

		Integer userId = Integer.valueOf(dataMap.get("userId").toString());
		String orderId = dataMap.get("orderId").toString();
		// 更新订单状态
		UserOrder order = new UserOrder();
		order.setUserId(userId);
		order.setOrderId(orderId);
		int orderRows = userOrderDao.updateRefundStatus(order);
		int gameRows = userGameDeductingProcessDao.updatePayRefund(orderId);
		if (orderRows == 0 || gameRows == 0) {
			return BuildFormErrorUtils.buildFormError("error#退款失败");
		} else {
			JsonResult jsonResult = new JsonResult();
			JSONObject json = new JSONObject();
			json.put("orderId", orderId);
			jsonResult.setData(json);
			jsonResult.setMsg("退款申请已提交,请耐心等待处理结果.");
			return jsonResult;
		}
	}
	/**
	 * 公会商品订单提交
	 * 
	 * @param token
	 * @param MAX_TIME
	 * @param os
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public Object creatorGuildOrder(String data, Integer nuid, String os, HttpServletRequest request) throws DataAccessException {
		try {
			Map<String, Object> result = OrderHelper.guildDataAnalsy(data, nuid, os);
			if (result.get(ERROR) != null) {
				String error = (String) result.get(ERROR);
				return BuildFormErrorUtils.buildFormError(error);
			}
			Integer userId = Integer.valueOf(result.get("userId").toString());
			if(CommonUtils.isNullEmpty(result.get("addressId").toString())) 
				return BuildFormErrorUtils.buildFormError("请选择收货人信息.");
			Integer addressId = Integer.valueOf(result.get("addressId").toString());
			Integer guildId = Integer.valueOf(result.get("guildId").toString());
			List<Integer> ids = (List<Integer>) result.get("ids");

			String orderId = OrderHelper.createOrderNumber();
			User user = userDao.select(userId);
			if (user == null)
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			if (user.getGroupId() == null)
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			GuildInfo guild = guildInfoDao.select(guildId);
			if (guild == null)
				return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			JsonResult jsonResult = new JsonResult();
			// 查询用户购物车中所有数据（需结算数据）

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("channel", Constants.PRODUCT_CHANNEL);
			map.put("picsize", Constants.PRODUCT_MIN_IMAGE_SIZE);
			map.put("productIds", ids);
			map.put("isDefault", "1");

			List<ProductInfo> list = productDao.selectEntityByCart(map);// 1为默认图片
			Long totalAmount = 0L;// 订单总金额
			Double discountTotalAmount = 0d;// 实付款金额(折扣后金额)
			Long balance = 0L;// 用户余额
			if (list != null && list.size() > 0) {
				Set<String> set = new HashSet<String>();
				for (ProductInfo p : list) {
					set.add(p.getType());
				}
				if (set.size() > 1)
					return BuildFormErrorUtils.buildFormError(OrderHelper.BUY_ERROR_MSG);
				if (set.contains("1"))
					return BuildFormErrorUtils.buildFormError("非法购买平台商品.");// 判断物品类型是否为平台商品
				// 验证库存量 与
				// 用户账户余额是否充足########################################################
				Map<String, String> checkResult = checkGuildBalanceAndStock(list, guild.getDiscountId(), guild.getCurrency() + "");
				if (checkResult.get(ERROR) != null) {
					String error = (String) checkResult.get(ERROR);
					return BuildFormErrorUtils.buildFormError(error);
				}
				// double ta = Double.valueOf(checkResult.get("totalAmount"));
				totalAmount = Double.valueOf(checkResult.get("totalAmount")).longValue();
				balance = Long.valueOf(checkResult.get("balance"));

				// double dta =
				// Double.valueOf(checkResult.get("discountTotalAmount"));
				discountTotalAmount = Double.valueOf(checkResult.get("discountTotalAmount"));

				ParamsVO vo = new ParamsVO();
				vo.setAddressId(addressId);
				vo.setDiscountTotalAmount(discountTotalAmount);
				vo.setGroupLevel(user.getGroupLevel());
				vo.setList(list);
				vo.setOrderId(orderId);
				vo.setOs(os);
				vo.setRequest(request);
				vo.setTotalAmount(totalAmount);
				vo.setUserId(userId);
				vo.setOrderType("2");// 公会商品订单
				vo.setGuildId(guildId);
				vo.setStatus(1);
				// 保存订单信息#########################################################################
				Map<String, String> orderResult = persistenceOrder(vo);
				if (orderResult.get(ERROR) != null) {
					String error = (String) orderResult.get(ERROR);
					return BuildFormErrorUtils.buildFormError(error);
				}

				// 订单持久化全部完成，需要扣除用户余额
				guild.setCurrency((balance - Math.round(discountTotalAmount)));
				int r = guildInfoDao.updateBalance(guild);
				if (r == 0) {// 更新不成功，说明余额已经被更新过，需要重新计算
					throw new Exception("当前公会余额已经发生变更, 请重新提交订单.");
				}
				// 删除用户购物车所有商品
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productIds", ids);
				params.put("userId", userId);
				cartDao.deleteByUserId(params);
				// userDao.updateUserVipGroup(userId);
				// 重新计算公会经验
				JSONObject json = new JSONObject();
				json.put("totalAmount", totalAmount);
				json.put("realPayAmount", discountTotalAmount);
				json.put("orderId", orderId);
				jsonResult.setData(json);
				return jsonResult;
			} else {
				return BuildFormErrorUtils.buildFormError("购买商品参数异常.商品ID" + ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return BuildFormErrorUtils.buildFormError(e.getMessage());
		}
	}

	/**
	 * 判断余额与库存
	 * 
	 * @return
	 */
	private Map<String, String> checkGuildBalanceAndStock(List<ProductInfo> list, double discount, String guildBalance) {
		if (discount == 0)
			discount = 1L;
		Map<String, String> result = new HashMap<String, String>();
		Double totalAmount = 0d;// 订单总金额
		Double discountTotalAmount = 0d;// 实付款金额(折扣后金额)
		for (ProductInfo p : list) {// 需要提前计算商品总价与用户可用进行比较
			if (!"1".equals(p.getStatus()))
				continue;// 商品下架 不做计算
			int count = p.getCartCount();// 每件商品数量
			// 验证库存量是否充足
			if (count > p.getCount()) {
				result.put(ERROR, p.getName() + " 库存量不足, 当前库存量为" + p.getCount());
				return result;
			}
			Double amount = 0d;
			// 根据用户所在用户组计算 商品折扣后单价
			double discountAmount = CommonUtils.multiply(discount, p.getPrice());
			if (discountAmount < 0) {
				amount = CommonUtils.multiply(p.getPrice(), count);// 当折扣金额小于0，说明折扣比例有问题，每种商品按原始价格总金额计算（不打折）
			} else {
				amount = CommonUtils.multiply(discountAmount, count);// 每种商品折扣后总金额
			}
			discountTotalAmount += amount;

			totalAmount += p.getPrice() * count;// 每种商品原始价格总金额（不打折）

		}
		if (!CommonUtils.isNullEmpty(guildBalance)) {//
			double balance = Double.valueOf(guildBalance);
			if (balance < discountTotalAmount) {// 用户余额不足
				result.put(ERROR, "公会财力不足, 快号召小弟们捐款吧.");
				return result;
			}
		} else {
			result.put(ERROR, "公会财力不足, 快号召小弟们捐款吧.");
			return result;
		}
		result.put("totalAmount", totalAmount + "");
		result.put("discountTotalAmount", discountTotalAmount + "");
		result.put("balance", guildBalance);
		return result;
	}
	
	/**
	 * 判断余额与库存
	 * 
	 * @return
	 */
	private Map<String, String> checkBalanceAndStock(List<ProductInfo> list, Integer groupLevel,Long walletAmount,String gameDeducts) {
		Map<String, String> result = new HashMap<String, String>();
		Double totalAmount = 0d;// 订单总金额
		Double discountTotalAmount = 0d;// 实付款金额(折扣后金额)
		for (ProductInfo p : list) {// 需要提前计算商品总价与用户可用进行比较
			if (!"1".equals(p.getStatus()))
				continue;// 商品下架 不做计算
			int count = p.getCartCount();// 每件商品数量
			// 验证库存量是否充足
			if (count > p.getCount()) {
				result.put(ERROR, p.getName() + " 库存量不足, 当前库存量为" + p.getCount());
				return result;
			}
			Double amount = 0d;
			if (groupLevel != null) {// 根据用户所在用户组计算 商品折扣后单价
				Integer level = groupLevel;
				double discountAmount = DiscountHelper.getDiscountAmount(p.getId(), level);
				if (discountAmount < 0) {
					amount = CommonUtils.multiply(p.getPrice(), count);// 当折扣金额小于0，说明折扣比例有问题，每种商品按原始价格总金额计算（不打折）
				} else {
					amount = CommonUtils.multiply(discountAmount, count);// 每种商品折扣后总金额
				}
				discountTotalAmount += amount;
			} else {// 所在组无折扣，按照原始价格计算（不打折）
				discountTotalAmount += p.getPrice() * count;
			}
			totalAmount += p.getPrice() * count;// 每种商品原始价格总金额（不打折）

		}
		//钱包金额大于订单金额
		if(walletAmount<totalAmount){
			Double leftAmount=discountTotalAmount-walletAmount;//钱包不足，需要扣除游戏的金额
			// 计算游戏扣除金币总额
			List<Map<String, String>> deductItems = parseDeducts(gameDeducts);
			Double deductAmount = 0d;
			for (Map<String, String> demap : deductItems) {
				deductAmount = deductAmount + Math.abs(Double.valueOf(demap.get("amount")));
			}
			if(leftAmount-deductAmount>0){
				result.put(ERROR, "您提交的N币数量不足, 请追加差额.");
				return result;
			}
		}

//		if (!CommonUtils.isNullEmpty(userBalance)) {//
//			Long balance = Long.valueOf(userBalance);
//			if (balance < discountTotalAmount) {// 用户余额不足
//				result.put(ERROR, "您提交的N币数量不足, 请追加差额.");
//				return result;
//			}
//		} else {
//			result.put(ERROR, "您提交的N币数量不足, 请追加差额.");
//			return result;
//		}
		result.put("totalAmount", totalAmount + "");
		result.put("discountTotalAmount", discountTotalAmount + "");
		result.put("balance", discountTotalAmount+"");
		return result;
	}

	private Map<String, String> persistenceOrder(ParamsVO vo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			ArrayList<String> logProducts=new ArrayList<String>();
			ArrayList<String> logGuildProducts=new ArrayList<String>();
			for (ProductInfo p : vo.getList()) { // 遍历需结算的商品，创建订单明细
				if (!"1".equals(p.getStatus()))
					continue;// 商品下架 不做计算
				int count = p.getCartCount();// 每件商品数量
				double amount = 0.00;// 实际总金额
				double disAmount = 0.00;// 折扣后总金额
				if (vo.getGroupLevel() != null) {// 根据用户所在用户组计算 商品折扣后单价
					Integer level = vo.getGroupLevel();
					double discountAmount = DiscountHelper.getDiscountAmount(p.getId(), level);
					if (discountAmount < 0) {
						disAmount = CommonUtils.multiply(p.getPrice(), count);// 当折扣金额小于0，说明折扣比例有问题，每种商品按原始价格总金额计算（不打折）
					} else {
						disAmount = CommonUtils.multiply(discountAmount, count);// 每种商品折扣后总金额
					}
				} else {
					disAmount = CommonUtils.multiply(p.getPrice(), count);
				}
				amount += CommonUtils.multiply(p.getPrice(), count);// 每种商品原始价格总金额（不打折）
				UserOrderItem item = new UserOrderItem();
				item.setOrderId(vo.getOrderId());
				item.setCount(p.getCartCount());
				item.setTotalAmount(amount);// 总金额
				item.setPayAmount(disAmount);// 实际付款金额 =总金额-优惠活动金额
				item.setDiscountAmount(amount - disAmount);// 优惠活动金额
				item.setProductId(p.getId());
				item.setProductName(p.getName());
				item.setProductPrice(p.getPrice());
				item.setStatus(vo.getStatus().toString());// -1无效订单 0处理中订单 1已完成订单
				if (count > p.getCount()) {
					result.put(ERROR, p.getName() + " 库存量不足, 当前库存量为" + p.getCount());
					return result;
				}
				p.setCount(p.getCount() - count);// 更新库存
				Integer saleTimes = p.getSaleTimes();// 更新销量
				if (saleTimes == null)
					saleTimes = 0;
				p.setSaleTimes(saleTimes + count);
				int record = productDao.updateStock(p);
				if (record == 0) {// 更新库存不成功，说明该商品库存被其他订单更新过，版本号已发生变化
					int effect = 0;
					while (effect == 0) {
						ProductInfo bean = productDao.select(p.getId());// 重新查询该商品
						int stock = bean.getCount();
						if (count > stock) {
							result.put(ERROR, p.getName() + " 库存量不足, 当前库存量为" + p.getCount());
							return result;
						}
						bean.setCount(bean.getCount() - count);// 更新销量
						Integer sales = bean.getSaleTimes();
						if (sales == null)
							sales = 0;
						bean.setSaleTimes(sales + count);
						effect = productDao.updateStock(bean);
					}
				}
				userOrderItemDao.insert(item);// 持久化订单详情
				logProducts.add(item.getProductName()+"-"+item.getCount());
				// 物品持久化到公会物品中
				if ("2".equals(vo.getOrderType())) {// 公会商品
					GuildItem guildItem = new GuildItem();
					guildItem.setGuildId(vo.getGuildId());
					guildItem.setIsVirtual("0");
					guildItem.setName(p.getName());
					guildItem.setTypeId(p.getId());
					guildItem.setCount(p.getCartCount());
					guildItem.setStatus("1");
					guildItemDao.insert(guildItem);
					logGuildProducts.add(item.getProductName()+"-"+item.getCount());
				}
			}

			// 设置请求来源
			String resource = parseSource(vo.getOs());

			// 创建订单头信息
			UserOrder order = new UserOrder();
			order.setOrderId(vo.getOrderId());// 生成随机唯一订单号
			order.setAddressId(vo.getAddressId());
			order.setPayTime(new Date());//
			order.setDeliveryAmount(0.0);// 邮费（在商品总价里）
			order.setDeliveryId(-1);// 邮寄类型(暂时无用)
			order.setIp(CommonUtils.realIPAddress(vo.getRequest()));// 获取用户IP
			order.setIsCostDelivery("0");// 是否免邮（暂时无用）
			order.setDiscountAmount(vo.getTotalAmount() - vo.getDiscountTotalAmount());// *****计算活动减免的费用*****
			order.setTotalAmount(vo.getTotalAmount());// 商品总价
			order.setPayAmount(vo.getDiscountTotalAmount());// 实际付款 = 商品总价 -
															// 活动减免费用(购物车活动
															// 满?少减?)
			order.setPayChannelId(-1);
			order.setResource(resource);// 订单来源 1网站 2安卓 3 苹果
			order.setStatus(vo.getStatus().toString());// -1无效订单 0处理中订单 1已完成订单
			order.setType("0");// 暂时无用，预留字段
			order.setUserId(vo.getUserId());// 提交订单用户ID
			order.setProductOrderType(vo.getOrderType());
			if (vo.getGuildId() != null && vo.getGuildId() != 0) {// 公会商品需要存储购买的商会ID
				order.setGuildId(vo.getGuildId());
			} else {
				order.setGuildId(0);
			}
			userOrderDao.insert(order);// 持久化订单头信息
			//日志
			//if("1".equals(vo.getOrderType()))
			//	DataEyeAgent.createOrder(vo.getUserId()+"", vo.getUserId()+"",vo.getOs(), logProducts.toArray(new String[logProducts.size()]), vo.getOrderId(), vo.getTotalAmount()+"");
			//else if("2".equals(vo.getOrderType())){
			//	DataEyeAgent.guildsCreateOrder(vo.getUserId()+"", vo.getUserId()+"", vo.getOs(),vo.getGuildId()+"", vo.getGuildId()+"",  logGuildProducts.toArray(new String[logGuildProducts.size()]),vo.getOrderId(), vo.getTotalAmount()+"");
			//}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage());
		}
		return result;
	}

	private String parseSource(String os) {
		// 设置请求来源
		String resource = null;
		if (Constants.ANDROID.equals(os))
			resource = "2";
		else if (Constants.IOS.equals(os))
			resource = "3";
		else
			resource = "1";
		return resource;
	}

	private List<Map<String, String>> parseDeducts(String deducts) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(!"".endsWith(deducts)){
			String[] deductItems = deducts.split("\\$\\*\\@\\*\\$");
			for (String item : deductItems) {
				String[] info = item.split("\\#\\*\\@\\*\\#");
				Map<String, String> map = new HashMap<String, String>();
				map.put("gameId", info[0]);
				map.put("serverId", info[1]);
				map.put("roleId", info[2]);
				map.put("amount", Math.abs(Double.valueOf(info[3])) + "");
				list.add(map);
			}
			
		}
		return list;
	}

	private void insertDeducts(Long totalAmount, List<Map<String, String>> list, Integer userId, String orderId) {
		Long leftAmount = totalAmount;

		for (Map<String, String> map : list) {
			UserGameDeductingProcess process = new UserGameDeductingProcess();
			PlatformGameApp gameInfo = platformGameAppDao.selectByCode(map.get("gameId"));
			Double devide = gameInfo.getExchangeDivide();
			process.setUserId(userId);
			process.setOrderId(orderId);
			process.setStatus("0");
			process.setGameId(map.get("gameId"));
			process.setServerId(map.get("serverId"));
			process.setRoleId(map.get("roleId"));
			process.setExchangeDivide(devide);
			Long amount = Double.valueOf(map.get("amount")).longValue();
			if (leftAmount - Double.valueOf(map.get("amount")).longValue() > 0) {
				process.setCostAmount(Double.valueOf(amount / devide).longValue());
				process.setExchangeNbGold(Double.valueOf(map.get("amount")).longValue());
				userGameDeductingProcessDao.insert(process);
				leftAmount = leftAmount - Double.valueOf(map.get("amount")).longValue();
			} else {
				process.setCostAmount(Double.valueOf(leftAmount / devide).longValue());
				process.setExchangeNbGold(leftAmount);
				userGameDeductingProcessDao.insert(process);
				break;
			}
		}
	}

	/**
	 * 参数类
	 * 
	 * @author kevin
	 */
	private class ParamsVO {
		List<ProductInfo> list;
		Integer groupLevel;
		String orderId;
		Integer userId;
		Integer addressId;
		String os;
		HttpServletRequest request;
		double totalAmount;
		double discountTotalAmount;
		String orderType;
		Integer guildId;
		Integer status;
		public List<ProductInfo> getList() {
			return list;
		}

		public void setList(List<ProductInfo> list) {
			this.list = list;
		}

		public Integer getGroupLevel() {
			return groupLevel;
		}

		public void setGroupLevel(Integer groupLevel) {
			this.groupLevel = groupLevel;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public Integer getAddressId() {
			return addressId;
		}

		public void setAddressId(Integer addressId) {
			this.addressId = addressId;
		}

		public String getOs() {
			return os;
		}

		public void setOs(String os) {
			this.os = os;
		}

		public HttpServletRequest getRequest() {
			return request;
		}

		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}

		public double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public double getDiscountTotalAmount() {
			return discountTotalAmount;
		}

		public void setDiscountTotalAmount(double discountTotalAmount) {
			this.discountTotalAmount = discountTotalAmount;
		}

		public String getOrderType() {
			return orderType;
		}

		public void setOrderType(String orderType) {
			this.orderType = orderType;
		}

		public Integer getGuildId() {
			return guildId;
		}

		public void setGuildId(Integer guildId) {
			this.guildId = guildId;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}
		
	}

	public Object orderDeliver(String data, Integer nuid, String os, HttpServletRequest request) {

		if (CommonUtils.isNullEmpty(data) || nuid == null || CommonUtils.isNullEmpty(os)) {
			return BuildFormErrorUtils.buildFormError("error#" + MessageConsValue.legalMessage);
		}
		Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);// 解析data中的请求参数，转化成key
																			// value形式
		boolean legal = SessionTokenUtils.validateLogin(dataMap, nuid, os);
		if (!legal || !dataMap.containsKey("userId") || !dataMap.containsKey("orderId")|| !dataMap.containsKey("addressId")) {
			return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		}

		Integer userId = Integer.valueOf(dataMap.get("userId").toString());
		Integer addressId = Integer.valueOf(dataMap.get("addressId").toString());
		String orderId = dataMap.get("orderId").toString();
		// 更新订单状态
		UserOrder order = new UserOrder();
		order.setUserId(userId);
		order.setOrderId(orderId);
		order.setAddressId(addressId);
		int orderRows = userOrderDao.updateDeliverStatus(order);
		if (orderRows == 0) {
			return BuildFormErrorUtils.buildFormError("error#发货失败");
		} else {
			JsonResult jsonResult = new JsonResult();
			JSONObject json = new JSONObject();
			json.put("orderId", orderId);
			jsonResult.setData(json);
			jsonResult.setMsg("发货申请已提交,请耐心等待处理结果.");
			return jsonResult;
		}
	}
}

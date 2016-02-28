package com.game.user.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.game.common.Constants;
import com.game.common.MessageConsValue;
import com.game.common.basics.JsonResult;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.guild.domain.GuildHistoryMessage;
import com.game.guild.domain.GuildInfo;
import com.game.guild.domain.GuildItem;
import com.game.guild.domain.GuildItemVirtual;
import com.game.guild.domain.GuildMembers;
import com.game.guild.helper.ConstantKeys;
import com.game.guild.helper.GuildMessageHelper;
import com.game.guild.persistence.dao.GuildInfoDao;
import com.game.guild.persistence.dao.GuildItemDao;
import com.game.guild.persistence.dao.GuildItemVirtualDao;
import com.game.guild.persistence.dao.GuildMembersDao;
import com.game.platform.message.EntryEnum;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserOrderSuccessTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.shop.domain.ProductInfo;
import com.game.shop.domain.ProductInfoVirtual;
import com.game.user.domain.Cart;
import com.game.user.domain.CartItem;
import com.game.user.domain.CartParam;
import com.game.user.domain.User;
import com.game.user.domain.UserOrder;
import com.game.user.persistence.dao.UserDao;
import com.game.user.utils.OrderHelper;

@Service
public class CartOrderService extends BaseOrder {
	@Autowired
	private UserDao userDao;
	@Autowired
	private GuildInfoDao guildInfoDao;
	@Autowired
	private GuildItemDao guildItemDao;
	@Autowired
	private GuildItemVirtualDao guildItemVirtualDao;
	@Autowired
	private GuildMembersDao guildMembersDao;
	/**
	 * 个人订单
	 * 
	 * @param user
	 * @param cart
	 * @param addressId
	 * @param costWallet
	 * @param gameInfo
	 * @param values
	 * @return
	 */
	public Object creatorOrder(User user, Cart cart, Integer addressId, Long costWallet, String gameInfo, String... values) {
		String orderId = cart.getOrderId();
		JsonResult jsonResult = new JsonResult();
		// 查询用户购物车中所有数据（需结算数据）
		if (Long.valueOf(user.getBalance()) < Math.abs(costWallet))
			return BuildFormErrorUtils.buildFormError("您的钱包余额不足, 当前来自充值的NB余额为:" + user.getBalance() + ".");

		Long totalAmount = cart.getDiscountAmount();// 订单总金额
		Long discountTotalAmount = cart.getDiscountAmount();// 实付款金额(折扣后金额)

		Long leftAmount = discountTotalAmount - Math.abs(costWallet);
		// 钱包金币不能完全支付订单，需要游戏金币补充
		if (leftAmount > 0) {
			List<Map<String, String>> gameInfoItems = parseGameString(gameInfo);
			if(gameInfoItems.isEmpty()){
				return BuildFormErrorUtils.buildFormError("您输入的付款金额, 不足支付当前商品.");
			}else{
				int payTotal = 0;
				for(Map<String, String> item : gameInfoItems){
					payTotal += Double.valueOf(item.get("amount")).intValue();
				}
				if(leftAmount > payTotal) return BuildFormErrorUtils.buildFormError("您输入的付款金额, 不足支付当前商品.");
			}
			insertSubstactGameGoldInQueue(leftAmount, gameInfoItems, user.getId(), orderId);
			user.setBalance(user.getBalance() - Math.abs(costWallet));
			cart.setStatus("0");
		} else {// 钱包金币足够支付订单，无需扣除游戏内金币
			user.setBalance(user.getBalance() - Math.abs(discountTotalAmount));
		}
		// 保存订单信息#############################(os)###########(ip)#####################
		UserOrder order = this.createBasicOrder(user, addressId, cart, values[0], values[1], costWallet.toString());
		if (order == null) return BuildFormErrorUtils.buildFormError("下单失败, 请稍候重试.");
		userDao.updateBalance(user);// 更新用户余额
		
		if(Constants.STATUS.equals(order.getStatus())){//判断订单状态， 只有钱包支付时候订单会立即成功，状态为1
			//邮件提醒用户
			MessageCore core = new MessageCore();
			String productNames = order.appendNames();
			core.setAdapter(new MessageUserOrderSuccessTemplate()).transfer(new PracticVirtual(), user.getId(), order.getOrderId(), productNames, order.getTotalAmount()).send();
		}
		
		JSONObject json = new JSONObject();
		json.put("totalAmount", totalAmount);
		json.put("realPayAmount", discountTotalAmount);
		json.put("orderId", orderId);
		jsonResult.setData(json);
		return jsonResult;
	}

	/**
	 * 个人订单
	 * 
	 * @param data
	 * @param nuid
	 * @param os
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	public Object creatorOrder(String data, Integer nuid, String os, String ip) throws DataAccessException {
		// 解析token
		// 返回map######################################################################
		Map<String, Object> result = OrderHelper.dataAnalsySingleton(data, nuid, os);
		if(result.containsKey(OrderHelper.ERROR)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
		Integer userId = Integer.valueOf(result.get("userId").toString());
		Integer addressId = Integer.valueOf(result.get("addressId").toString());
		Integer count = Integer.valueOf(result.get("count").toString());
		Integer productId = Integer.parseInt(result.get("productId").toString());
		Long costWallet = Long.valueOf(result.get("walletAmount").toString());// 来自用户钱包的NB
		String gameInfo = result.get("gameDeducts").toString();

		User user = userDao.select(userId);
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
		return creatorOrder(user, cart, addressId, costWallet, gameInfo, CommonUtils.getSystem(os), ip);
	}

	/**
	 * 公会购买商品
	 * 
	 * @param data
	 * @param nuid
	 * @param os
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	public Object creatorOrderGuild(String data, Integer nuid, String os, String ip) throws DataAccessException {
		try {
			Map<String, Object> result = OrderHelper.guildDataAnalsySingleton(data, nuid, os);
			if(result.containsKey(OrderHelper.ERROR)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			Integer userId = Integer.valueOf(result.get("userId").toString());
			Integer addressId = null;
			Integer guildId = Integer.valueOf(result.get("guildId").toString());
			Integer productId = Integer.parseInt(result.get("productId").toString());
			Integer count = Integer.parseInt(result.get("count").toString());

			String orderId = OrderHelper.createOrderNumber();
			User user = userDao.select(userId);

			if (user == null) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			GuildInfo guild = guildInfoDao.select(guildId);
			if (guild == null) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
			
			JsonResult jsonResult = new JsonResult();
			List<CartParam> params = new ArrayList<CartParam>();
			CartParam param = new CartParam();
			param.setId(productId);
			param.setQuantity(count);
			params.add(param);
			Cart cart = new Cart(params, userId, guildId);

			if (cart.getTotalAmount() > guild.getCurrency())
				return BuildFormErrorUtils.buildFormError("公会财力不足, 快号召小弟们捐款吧.");
			if (!cart.isOnSale())
				return BuildFormErrorUtils.buildFormError("对不起, 您所购买的商品已经下架.");
			if (!cart.hasStock())
				return BuildFormErrorUtils.buildFormError("对不起, 您所购买的商品库存不足.");
			if (!cart.isLegal())
				return BuildFormErrorUtils.buildFormError("公会无法购买个人商城物品.");
			UserOrder order = this.createBasicOrder(user, addressId, cart, CommonUtils.getSystem(os), ip);
			
			createItems(cart);//公会商品录入
			
			createHistory(cart, user);//创建历史记录
			
			if (order == null) return BuildFormErrorUtils.buildFormError("下单失败, 请稍候重试.");
			// 订单持久化全部完成，需要扣除用户余额
			guild.setCurrency((guild.getCurrency() - cart.getTotalAmount()));
			int effective = guildInfoDao.updateBalance(guild);
			// 更新不成功，说明余额已经被更新过，需要重新计算
			if (effective == 0) throw new Exception("订单提交失败, 请重新提交订单.");

			// 重新计算公会经验
			JSONObject json = new JSONObject();
			json.put("totalAmount", cart.getTotalAmount());
			json.put("realPayAmount", cart.getDiscountAmount());
			json.put("orderId", orderId);
			jsonResult.setData(json);
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			return BuildFormErrorUtils.buildFormError("订单提交失败.");
		}
	}
	
	private void createHistory(Cart cart, User user){
		String userRole = "";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", cart.getUserId());
		map.put("guildId", cart.getGuildId());
		GuildMembers guildMembers = guildMembersDao.selectByUserAndGuildId(map);
		if (guildMembers != null) {
			if (ConstantKeys.Admin.equals(guildMembers.getIsAdmin())) {
				userRole = "管理员";
			} else if (ConstantKeys.President.equals(guildMembers.getIsAdmin())) {
				userRole = "会长";
			}
		}
		String msgUserName = user.getNickName() == null ? user.getUserName() : user.getNickName();
		for(CartItem item : cart.getItems()){
			// 公会历史消息记录
			String content = String.format("【%s】%s购买了[%s]个%s", userRole, msgUserName ,item.getQuantity(), item.getProduct().getName());
			GuildMessageHelper.saveMsg(GuildHistoryMessage.MsgTypeEnum.GUILD.getValue(), cart.getGuildId(), user.getId(), user.getId(), content);
		}
	}
	/**
	 * 创建公会物品
	 * @param cart
	 */
	private void createItems(Cart cart){
		for(CartItem item : cart.getItems()){
			ProductInfo product = item.getProduct();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", product.getId());
			map.put("guildId", cart.getGuildId());
			List<GuildItem> itemList = guildItemDao.selectByTypeId(map);
			GuildItem guildItem = null;
			if(itemList != null && itemList.size() == 1){//买过且数据正常，只需要合并数量
				guildItem = itemList.get(0);
				guildItem.setCount(guildItem.getCount() + item.getQuantity());
				guildItemDao.update(guildItem);
			}else if(itemList != null && itemList.size() > 1){//如果同样商品记录不是唯一数据，需要删除后进行数量累加，变成一条记录
				int orignQuantity = 0;//原始物品总数量
				int givedQuantity = 0;//已经赠送总数量
				for(GuildItem it : itemList){
					orignQuantity += it.getCount();
					givedQuantity += it.getGivedQuantity();
				}
				guildItemDao.deleteByTypeId(map);//删除重复记录，重新插入一条新数据
				guildItem = new GuildItem();
				guildItem.setGuildId(cart.getGuildId());
				guildItem.setIsVirtual(item.isVirtual());
				guildItem.setName(product.getName());
				guildItem.setTypeId(product.getId());
				guildItem.setCount(orignQuantity + item.getQuantity());
				guildItem.setGivedQuantity(givedQuantity);
				guildItem.setStatus(CartItem.STATUS);
				guildItemDao.insert(guildItem);
			}else if(itemList == null || itemList.size() == 0){//没有购买过该物品
				guildItem = new GuildItem();
				guildItem.setGuildId(cart.getGuildId());
				guildItem.setIsVirtual(item.isVirtual());
				guildItem.setName(product.getName());
				guildItem.setTypeId(product.getId());
				guildItem.setCount(item.getQuantity());
				guildItem.setGivedQuantity(0);
				guildItem.setStatus(CartItem.STATUS);
				guildItemDao.insert(guildItem);
			}
			//创建公会虚拟物品
			if(CartItem.IS_VIRTUAL.equals(item.getProduct().getIsVirtual())){
				createVirtualItems(item, guildItem.getId());
			}
		}
	}
	//公会虚拟物品创建
	private void createVirtualItems(CartItem item, Integer itemId){
		List<ProductInfoVirtual> virtualList = item.getVirtuals();
		List<GuildItemVirtual> list = new ArrayList<GuildItemVirtual>();
		for(ProductInfoVirtual virtual : virtualList){
			GuildItemVirtual virtualItem = new GuildItemVirtual();
			virtualItem.setItemId(itemId);
			virtualItem.setActiveCode(virtual.getPasswordCode());
			virtualItem.setCardNumber(virtual.getCardNumber());
			virtualItem.setStatus(Constants.COMMON_ZERO);
			virtualItem.setCodeId(virtual.getId());
			list.add(virtualItem);
		}
		guildItemVirtualDao.insertVirtualItems(list);
	}

	/**
	 * 领取实物奖励, 自动创建订单
	 */
	public int received(User user, List<PracticVirtual> entitys, Integer guildId, String... added) {
		Assert.notNull(entitys);
		try {
			List<CartParam> params = new ArrayList<CartParam>();
			for (PracticVirtual entity : entitys) {
				CartParam param = new CartParam();
				param.setId(entity.getId());//product id
				param.setQuantity(entity.getQuantity());
				if(EntryEnum.VIRTUAL.getIdentify().equals(entity.getType())){//虚拟物
					List<ProductInfoVirtual> virtuals = doVirtual(entity, user);
					if(virtuals == null) return 0;
					param.setList(virtuals);
				}
				params.add(param);
			}
			Cart cart = new Cart(params, user.getId(), guildId);
			UserOrder order = this.createBasicOrderReceived(user, 0, cart, CommonUtils.getSystem(added[0]), added[1]);
			if (order.getId() != null) {
				order.setStatus("1");
				order.setProductOrderType("3");
				userOrderDao.update(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 当领取虚拟商品时候，需要处理公会商品
	 * @param entity
	 * @param user
	 * @return
	 */
	private List<ProductInfoVirtual> doVirtual(PracticVirtual entity, User user){
		String itemId = entity.getOrderId();//虚拟物品对应的物品ID
		Integer userId = user.getId();
		//查询
		GuildItemVirtual bean = new GuildItemVirtual();
		bean.setItemId(Integer.valueOf(itemId));
		bean.setUserId(userId.toString());
		bean.setStatus("2");//已分配给用户，且未领取的
		
		List<GuildItemVirtual> list = guildItemVirtualDao.selectByEntity(bean);
		if(list == null || list.isEmpty()) return null;
		List<ProductInfoVirtual> temp = new ArrayList<ProductInfoVirtual>();
		for(GuildItemVirtual virtual : list){
			ProductInfoVirtual product = new ProductInfoVirtual();
			product.setId(virtual.getCodeId());
			product.setCardNumber(virtual.getCardNumber());
			product.setPasswordCode(virtual.getActiveCode());
			product.setProductId(entity.getId());
			temp.add(product);
		}
		bean.setLimit(entity.getQuantity());
		int effect = guildItemVirtualDao.updateGetGift(bean);//设置虚拟状态为已领取
		if(effect == 0) return null;
		return temp;
	}
}

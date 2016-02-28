package com.game.user.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.game.common.Constants;
import com.game.init.CacheListener;
import com.game.platform.domain.PlatformGameApp;
import com.game.shop.domain.ProductInfo;
import com.game.shop.domain.ProductInfoVirtual;
import com.game.shop.persistence.dao.ProductInfoDao;
import com.game.shop.persistence.dao.ProductInfoVirtualDao;
import com.game.user.domain.Cart;
import com.game.user.domain.CartItem;
import com.game.user.domain.User;
import com.game.user.domain.UserGameDeductingProcess;
import com.game.user.domain.UserOrder;
import com.game.user.domain.UserOrderItem;
import com.game.user.domain.UserOrderItemVirtual;
import com.game.user.persistence.dao.UserGameDeductingProcessDao;
import com.game.user.persistence.dao.UserOrderDao;
import com.game.user.persistence.dao.UserOrderItemDao;
import com.game.user.persistence.dao.UserOrderItemVirtualDao;
@Service
public class BaseOrder {
	@Autowired
	protected UserOrderDao userOrderDao;
	@Autowired
	protected UserOrderItemDao userOrderItemDao;
	@Autowired
	protected UserOrderItemVirtualDao userOrderItemVirtualDao;
	@Autowired
	protected UserGameDeductingProcessDao userGameDeductingProcessDao;
	@Autowired
	private ProductInfoVirtualDao virtualDao;
	@Autowired
	private ProductInfoDao productDao;
	
	/**
	 * 创建基本订单信息
	 * @param user 用户对象
	 * @param address 配送地址对象
	 * @param carts 购物车集合
	 * @param argValues 变参类型, 附加参数
	 */
	@Transactional
	public UserOrder createBasicOrder(User user, Integer addressId, Cart cart, String...argValues){
		Assert.notNull(user);
		Assert.notNull(cart);
		String orderId = cart.getOrderId();
		Date date = new Date();
		UserOrder order = new UserOrder();
		if(addressId == null) addressId = 0;
		order.setAddressId(addressId);
		order.setUserId(user.getId());
		order.setDeliveryAmount(0.0);
		order.setDiscountAmount(cart.getTotalAmount().doubleValue() - cart.getDiscountAmount().doubleValue());
		order.setTotalAmount(cart.getTotalAmount().doubleValue());
		order.setDeliveryId(-1);
		order.setGuildId(cart.getGuildId());
		order.setIsCostDelivery(Constants.STATUS);
		order.setOrderId(orderId);
		order.setPayAmount(cart.getDiscountAmount().doubleValue());
		order.setPayChannelId(-1);
		order.setPayTime(date);
		order.setProductOrderType(cart.getType());
		order.setResource(argValues[0]);
		order.setCreateTime(date);
		order.setStatus(cart.getStatus());
		order.setType(Constants.COMMON_ZERO);
		order.setIp(argValues[1]);
		order.setWalletPayAmount(argValues.length == 3 ? Long.valueOf(argValues[2]) : 0);
		
		ArrayList<String> logProducts = new ArrayList<String>();
		
		for(CartItem item : cart.getItems()){
			UserOrderItem orderItem = new UserOrderItem();
			ProductInfo product = item.getProduct();
			orderItem.setOrderId(orderId);
			orderItem.setProductId(product.getId());
			orderItem.setProductName(product.getName());
			orderItem.setCount(item.getQuantity());
			orderItem.setDiscountAmount(item.getDiscountAmount().doubleValue());
			orderItem.setPayAmount(item.getDiscountAmount().doubleValue());
			orderItem.setProductPrice(product.getPrice());
			orderItem.setStatus(CartItem.STATUS);
			orderItem.setTotalAmount(item.getTotalAmount().doubleValue());
			orderItem.setIsVirtual(item.isVirtual());
			order.getOrderItems().add(orderItem);
			userOrderItemDao.insert(orderItem);
			
			//更新库存信息
			if(product.getCount() >= item.getQuantity()){
				product.setCount(product.getCount() - item.getQuantity());
				product.setSaleTimes(product.getSaleTimes() + item.getQuantity());
				productDao.updateStock(product);
			}
			
			//创建虚拟商品信息
			if(CartItem.IS_VIRTUAL.equals(item.getProduct().getIsVirtual())){
				List<ProductInfoVirtual> virtualList = item.getVirtuals();
				List<Integer> ids = item.identityList(virtualList);//虚拟商品IDS
				if(ids == null || ids.isEmpty()) throw new RuntimeException("商品库存不足, 提交订单失败."); 
				List<UserOrderItemVirtual> list = item.getOrderItemVirtual(virtualList, orderItem.getId());
				if(list == null || list.isEmpty()) throw new RuntimeException("商品库存不足, 提交订单失败."); 
				userOrderItemVirtualDao.insertVirtuals(list);
				int effective = virtualDao.updateStatus(ids);
				if(effective != item.getQuantity()) throw new RuntimeException("商品库存不足, 提交订单失败."); 
			}
			//日志
			logProducts.add(item.getProduct().getName() + "-" + item.getQuantity());
		}
		userOrderDao.insert(order);
		//DataEyeAgent.createOrder(user.getUserName(), user.getId().toString(), argValues[0], logProducts.toArray(new String[logProducts.size()]), orderId, cart.getTotalAmount().toString());
		return order;
	}

	public List<Map<String, String>> parseGameString(String deducts) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (!"".endsWith(deducts)) {
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

	public void insertSubstactGameGoldInQueue(Long totalAmount, List<Map<String, String>> list, Integer userId, String orderId) {
		Long leftAmount = totalAmount;
		for (Map<String, String> map : list) {
			UserGameDeductingProcess process = new UserGameDeductingProcess();
			PlatformGameApp gameInfo = CacheListener.getGame(map.get("gameId"));
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
	 * 创建接收物品订单信息
	 * @param user 用户对象
	 * @param address 配送地址对象
	 * @param carts 购物车集合
	 * @param argValues 变参类型, 附加参数
	 */
	@Transactional
	public UserOrder createBasicOrderReceived(User user, Integer addressId, Cart cart, String...argValues){
		Assert.notNull(user);
		Assert.notNull(cart);
		String orderId = cart.getOrderId();
		Date date = new Date();
		UserOrder order = new UserOrder();
		if(addressId != null) addressId = 0;
		order.setAddressId(addressId);
		order.setUserId(user.getId());
		order.setDeliveryAmount(0.0);
		order.setDiscountAmount(cart.getTotalAmount().doubleValue() - cart.getDiscountAmount().doubleValue());
		order.setTotalAmount(cart.getTotalAmount().doubleValue());
		order.setDeliveryId(-1);
		order.setGuildId(cart.getGuildId());
		order.setIsCostDelivery(Constants.STATUS);
		order.setOrderId(orderId);
		order.setPayAmount(cart.getDiscountAmount().doubleValue());
		order.setPayChannelId(-1);
		order.setPayTime(date);
		order.setProductOrderType(cart.getType());
		order.setResource(argValues[0]);
		order.setCreateTime(date);
		order.setStatus(cart.getStatus());
		order.setType(Constants.COMMON_ZERO);
		order.setIp(argValues[1]);
		order.setWalletPayAmount(argValues.length == 3 ? Long.valueOf(argValues[2]) : 0);
		
		for(CartItem item : cart.getItems()){
			UserOrderItem orderItem = new UserOrderItem();
			ProductInfo product = item.getProduct();
			orderItem.setOrderId(orderId);
			orderItem.setProductId(product.getId());
			orderItem.setProductName(product.getName());
			orderItem.setCount(item.getQuantity());
			orderItem.setDiscountAmount(item.getDiscountAmount().doubleValue());
			orderItem.setPayAmount(item.getDiscountAmount().doubleValue());
			orderItem.setProductPrice(product.getPrice());
			orderItem.setStatus(CartItem.STATUS);
			orderItem.setTotalAmount(item.getTotalAmount().doubleValue());
			orderItem.setIsVirtual(item.isVirtual());
			order.getOrderItems().add(orderItem);
			userOrderItemDao.insert(orderItem);
			
			//创建虚拟商品信息
			if(CartItem.IS_VIRTUAL.equals(item.getProduct().getIsVirtual())){
				List<ProductInfoVirtual> virtualList = item.getVirtuals();
				List<UserOrderItemVirtual> list = item.getOrderItemVirtual(virtualList, orderItem.getId());
				userOrderItemVirtualDao.insertVirtuals(list);
			}
		}
		userOrderDao.insert(order);
		return order;
	}
}

///*
// * @author yangchengwei
// * created on 2015-03-17
// */
//
//package com.game.guild.persistence.service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.game.common.basics.ResultHandler;
//import com.game.common.basics.exception.DataAccessException;
//import com.game.common.basics.pagination.PageQuery;
//import com.game.common.utility.CommonUtils;
//import com.game.guild.domain.GuildItemGive;
//import com.game.guild.persistence.dao.GuildItemGiveDao;
//import com.game.shop.domain.ProductInfo;
//import com.game.shop.persistence.dao.ProductInfoDao;
//import com.game.user.domain.UserOrder;
//import com.game.user.domain.UserOrderItem;
//import com.game.user.persistence.dao.UserOrderDao;
//import com.game.user.persistence.dao.UserOrderItemDao;
//import com.game.user.utils.OrderHelper;
//
//@Service
//public class GuildItemGiveService {
//
//	@Autowired
//	private GuildItemGiveDao guildItemGiveDao;
//	@Autowired
//	private UserOrderDao userOrderDao;
//	@Autowired
//	private UserOrderItemDao userOrderItemDao;
//	@Autowired
//	private ProductInfoDao productInfoDao;
//
//	public List<GuildItemGive> query(PageQuery querys) throws DataAccessException {
//		try {
//			return guildItemGiveDao.pageQuery(querys);
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public List<GuildItemGive> selectAll() throws DataAccessException {
//		try {
//			return guildItemGiveDao.selectAll();
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public List<GuildItemGive> selectByEntity(GuildItemGive guildItemGive) throws DataAccessException {
//		try {
//			return guildItemGiveDao.selectByEntity(guildItemGive);
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public List<Map<String, String>> selectRealMyitem(PageQuery querys) throws DataAccessException {
//		try {
//			return guildItemGiveDao.selectRealMyitem(querys);
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public GuildItemGive select(Integer id) throws DataAccessException {
//		try {
//			return guildItemGiveDao.select(id);
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public void insert(GuildItemGive guildItemGive) throws DataAccessException {
//		try {
//			guildItemGiveDao.insert(guildItemGive);
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public void update(GuildItemGive guildItemGive) throws DataAccessException {
//		try {
//			guildItemGiveDao.update(guildItemGive);
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public void delete(Integer id) throws DataAccessException {
//		try {
//			guildItemGiveDao.delete(id);
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public void deleteSelect(Integer[] ids) throws DataAccessException {
//		try {
//			guildItemGiveDao.deleteSelect(ids);
//		} catch (DataAccessException e) {
//			throw new DataAccessException(e);
//		}
//	}
//
//	public Object updateReceiveItem(Integer userId, Integer giveId, Integer guildId, Integer productId, Integer count, Integer addressId, HttpServletRequest request) throws Exception {
//		GuildItemGive entity = new GuildItemGive();
//		entity.setId(giveId);
//		entity.setReceiveUserId(userId);
//		int updateRow = guildItemGiveDao.updateReceiveItem(entity);
//		ProductInfo productInfo = productInfoDao.select(productId);
//
//		// 创建订单头信息
//		String orderNo = OrderHelper.createOrderNumber();
//		UserOrder order = new UserOrder();
//		order.setOrderId(orderNo);// 生成随机唯一订单号
//		order.setAddressId(addressId);
//		order.setPayTime(new Date());//
//		order.setDeliveryAmount(0.0);// 邮费（在商品总价里）
//		order.setDeliveryId(-1);// 邮寄类型(暂时无用)
//		order.setIp(CommonUtils.realIPAddress(request));// 获取用户IP
//		order.setIsCostDelivery("0");// 是否免邮（暂时无用）
//		order.setDiscountAmount(0.00);// *****计算活动减免的费用*****
//		order.setTotalAmount(0.00);// 商品总价
//		order.setPayAmount(0.00);// 实际付款 = 商品总价 - 活动减免费用(购物车活动 满?少减?)
//		order.setPayChannelId(-1);
//		order.setResource("1");// 订单来源 1网站 2安卓 3 苹果
//		order.setStatus("1");// -1无效订单 0处理中订单 1已完成订单
//		order.setType("0");// 暂时无用，预留字段
//		order.setUserId(userId);// 提交订单用户ID
//		order.setProductOrderType("2");
//		order.setGuildId(guildId);
//		int orderRow = userOrderDao.insert(order);// 持久化订单头信息
//		UserOrderItem item = new UserOrderItem();
//		item.setOrderId(orderNo);
//		item.setCount(count);// 修改==
//		item.setTotalAmount(0.00);// 总金额
//		item.setPayAmount(0.00);// 实际付款金额 =总金额-优惠活动金额
//		item.setDiscountAmount(0.00);// 优惠活动金额
//		item.setProductId(productId);
//		item.setProductName(productInfo.getName());
//		item.setProductPrice(productInfo.getPrice());
//		item.setStatus("0");// -1无效订单 0处理中订单 1已完成订单
//
//		int orderItemRow = userOrderItemDao.insert(item);
//		if (orderRow == 1 && orderItemRow == 1 && updateRow == 1) {
//			return ResultHandler.bindResult("ok#接收物品成功.");
//		} else {
//			throw new Exception("接收物品失败, 请重新操作.");
//		}
//	}
//}

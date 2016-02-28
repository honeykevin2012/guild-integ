package com.game.payment.persistence.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.common.basics.BaseException;
import com.game.common.basics.exception.DataAccessException;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;
import com.game.payment.domain.OrderInfo;
import com.game.payment.domain.UserCharge;
import com.game.payment.domain.form.OrderInfoForm;
import com.game.payment.persistence.dao.ChargeDao;
import com.game.user.domain.User;

@Service
public class ChargeService {
	
	@Autowired
	private ChargeDao chargeDao;
	public List<UserCharge> query(PageQuery querys) throws BaseException{
		try {
			return chargeDao.pageQuery(querys);
		} catch (DataAccessException e) {
			throw new DataAccessException(e);
		}
	}
	
	
	public String insertAndReturnOrderId(OrderInfoForm form, User user) throws BaseException{
		//PaymentConfig config = PaymentStrategy.config(form.getCardType().toString());
		//if("".equals(config.getChannelId()) || "-1".equals(config.getChannelId())) return "-1";
		OrderInfo order = new OrderInfo();
		CommonUtils.copyProperties(form, order);
		String orderId = this.createOrderId(6);
		order.setOrderId(orderId);
		order.setChannelOrderid(orderId);
		order.setMerchantOrderid(form.getMerchantOrderId());
		order.setMerchantProductname(form.getMerchantProductName());
		order.setPayOrderid(form.getPayOrderId());
		order.setPayStatuscode(form.getPayStatusCode());
		if(user == null) return "-2";
		order.setUsername(user.getUserName());
		order.setThirdUid(form.getUserName());
		order.setRoleid(Integer.valueOf(form.getRoleId()));
		String roleName = CommonUtils.encodeContent(form.getRoleName());
		order.setRolename(roleName);
		order.setClienttype(form.getClientType());
		order.setClientostype(form.getClientOsType());
		order.setPhonetype(form.getPhoneType());
		order.setAppid(form.getAppId());
		order.setServerid(form.getServerId());
		order.setCreateDate(new Date());
		order.setClientostype(form.getOs());
		order.setClientosversion(form.getOsVersion());
		order.setAp("");
		if(form.getNotifyStatus() == null) order.setNotifyStatus(0);
		order.setMerchantId(form.getMerchantId() == null ? 9961 : form.getMerchantId());
		order.setMerchantVersion(form.getMerchantVersion() == null || "".equals(form.getMerchantVersion()) ? "9961" : form.getMerchantVersion());
		if (form.getAmount() <= 0) {
			order.setAmount(-1);
		} else {
			order.setAmount(form.getAmount());
		}
		order.setNotifyTimes(0);
		order.setNotifyStatus(0);
		if (form.getStatus() != null && form.getStatus() == 1) {
			order.setStatus(1);
			order.setPayStatus(1);
		} else {
			order.setStatus(0);
			order.setPayStatus(0);
		}
	
		order.setParameter1(form.getAppId());
		order.setAppChannelId("99");
		
		//Integer paymentId = (config.getPaymentId() == null ? -1 : Integer.valueOf(config.getPaymentId()));
		//order.setPaymentId(paymentId);
		
		order.setSourceType(6);
		order.setPayId(1);
		order.setMemo(form.getMemo());
		//Integer channelId = (config.getChannelId() == null ? -1 : Integer.valueOf(config.getChannelId()));
		//order.setChannelId(channelId);
		if(form.getUserRegTime() == null || "".equals(form.getUserRegTime()))
			order.setUserregtime(user.getCrateTime());
		else{
			order.setUserregtime(DateUtils.format(form.getUserRegTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		try{
			chargeDao.insertAndReturnOrderId(order);
		}catch(Exception e){
			e.printStackTrace();
			return "-1";
		}
		return order.getOrderId();
	}
	
	public int updateOrderInfo(String orderId) throws BaseException {
		int res = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		res = chargeDao.updateOrderInfo(map);
		return res;
	}

	public String createOrderId(int length) {
		String date = DateUtils.format(new Date(), "yyyyMMddHHmmss");
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++)
			sb.append(CHARS_NUM[random.nextInt(CHARS_NUM.length)]);
		return date + sb.toString();
	}
	
	private final static String RAND_RANGE_NUM = "1234567890";
	private final static char[] CHARS_NUM = RAND_RANGE_NUM.toCharArray();
}	

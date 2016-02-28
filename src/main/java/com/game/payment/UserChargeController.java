package com.game.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.BaseException;
import com.game.common.basics.JsonResult;
import com.game.common.basics.ResultHandler;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.DateUtils;
import com.game.common.utility.DebugUtils;
import com.game.payment.domain.UserCharge;
import com.game.payment.domain.form.ChargeForm;
import com.game.payment.domain.form.OrderInfoForm;
import com.game.payment.domain.vo.UserChargeVo;
import com.game.payment.persistence.service.ChargeService;
import com.game.payment.utils.PaymentStrategy;
import com.game.user.domain.User;
import com.game.user.persistence.service.UserService;

/**
 * 用户支付查询接口
 * @author zhaolianjun
 *
 */
@Controller
public class UserChargeController extends BaseController {
	private static Logger logger = Logger.getLogger(UserChargeController.class.getName());
	@Autowired
	private ChargeService service;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user/charge/list", method = RequestMethod.POST)
    public @ResponseBody Object charge(@Valid @ModelAttribute("chargeForm") ChargeForm form, BindingResult result) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if("true".equals(Constants.ASSERT_DEBUG)) logger.info(DebugUtils.debug(request));
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				if(form.getPn() == null || form.getPn() == 0) return this.buildFormError("error#页码不能为空.");
				JsonResult pageResult = new JsonResult();
				PageQuery page = new PageQuery(request);
				page.setPageSize(50);
				page.setPageNo(form.getPn());
				Map<String, Object> params = page.getParams();
				if(params == null) params = new HashMap<String, Object>();
				params.put("userName", form.getUserName());
				params.put("status", form.getStatus());
				page.setParams(params);
				List<UserCharge> data = service.query(page);
				List<UserChargeVo> resultVO = new ArrayList<UserChargeVo>();
				for(UserCharge ch : data){
					UserChargeVo userCharge = new UserChargeVo();
					userCharge.setOrderId(ch.getOrderId());
					userCharge.setAmount(ch.getAmount());
					userCharge.setCardNo(ch.getCardNo() == null ? "无" : ch.getCardNo());
					userCharge.setChannelId(0);
					userCharge.setGameName("");/////////////////
					userCharge.setMessage(ch.getMessage() == null ? "无" : ch.getMessage());
					if(ch.getPayStatus().equals(0) || ch.getPayStatus().equals(2)){
						userCharge.setPayStatus("未成功");
					}else if(ch.getPayStatus().equals(1)){
						userCharge.setPayStatus("成功");
					}
					String payChannelName = null;
					if(ch.getPaymentId() != null){
						payChannelName = PaymentStrategy.paymentValue(ch.getPaymentId().toString());
					}
					userCharge.setChargeType(payChannelName == null ? "" : payChannelName);
					if(ch.getChargeDate() != null) userCharge.setChargeDate(DateUtils.format(ch.getChargeDate(), "yyyy-MM-dd HH:mm:ss"));
					else userCharge.setChargeDate("处理中");
					userCharge.setCreateDate(DateUtils.format(ch.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
					resultVO.add(userCharge);
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("signature", form.getSignature());
				map.put("pn", form.getPn());
				map.put("ps", page.getPageSize());
				map.put("totalPage", page.getTotalPages());
				map.put("records", resultVO);
				pageResult.setData(map);
				return pageResult; 
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.severe(e.getMessage());
			return null;
		}
    }
	
	@RequestMapping(value="/user/order", method = RequestMethod.POST)
	public @ResponseBody Object order(@Valid @ModelAttribute("chargeForm") OrderInfoForm form, BindingResult result) {
		try{
			if(result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
				if("true".equals(Constants.ASSERT_DEBUG)) logger.info(DebugUtils.debug(request));
				
				User user = userService.selectCheckUserExistsByName(form.getUserName());
				if(user == null) return this.buildFormError("error#用户: "+form.getUserName()+" 不存在.");
				
				String orderId = service.insertAndReturnOrderId(form, user);
				
				if("-1".equals(orderId)) return ResultHandler.bindResult("error#Card type参数错误.");
				JsonResult data = null;
				if(orderId != null) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderId", orderId);
					map.put("signature", form.getSignature());
					data = new JsonResult();
					data.setData(map);
					return data;
				}else {
					return this.buildFormError("error#订单生成失败.");
				}
			}
		}catch(BaseException e){
			e.printStackTrace();
			logger.severe(e.getMessage());
			return null;
		}
	}
	
}

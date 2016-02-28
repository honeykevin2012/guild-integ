package com.game.platform;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.Constants;
import com.game.common.basics.BaseController;
import com.game.common.basics.exception.ActionException;
import com.game.platform.message.MessageCore;
import com.game.platform.message.MessageUserOrderFailedTemplate;
import com.game.platform.message.MessageUserOrderSuccessTemplate;
import com.game.platform.message.PracticVirtual;
import com.game.user.domain.UserOrder;
import com.game.user.domain.UserOrderItem;
import com.game.user.persistence.service.UserOrderItemService;
import com.game.user.persistence.service.UserOrderService;

@Controller
public class InnerServiceController extends BaseController {
	private static final Logger logger = Logger.getLogger(InnerServiceController.class);

	@Autowired
	private UserOrderService userOrderService;
	@Autowired
	UserOrderItemService userOrderItemService;

	/**
	 * 订单邮件
	 * 
	 * @param request
	 * @param response
	 * @throws ActionException
	 */
	@RequestMapping(value = "/inner/service/orderMail", method = { POST })
	public @ResponseBody
	Object orderMail(String orderId, String authKey, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (Constants.INNER_AUTH_KEY.equals(authKey) && orderId != null) {
				UserOrder params = new UserOrder();
				params.setOrderId(orderId);
				Map<String, String> orderInfo = userOrderService.selectByOrderId(params);

				if (orderInfo != null) {
					String status = String.valueOf(orderInfo.get("statusCode"));
					List<UserOrderItem> products = userOrderItemService.selectByOrderId(orderId);
					String userId =String.valueOf(orderInfo.get("userId"));
					String amount=String.valueOf(orderInfo.get("payAmount"));
					StringBuilder productNames = new StringBuilder();
					if(products==null||products.isEmpty()){
						return "OK";
					}
					for (UserOrderItem map : products) {
						productNames.append("[" + map.getProductName() + "]");
						productNames.append("\r\n");
					}
					MessageCore core = new MessageCore();
					if ("1".equalsIgnoreCase(status)) {
						core.setAdapter(new MessageUserOrderSuccessTemplate()).transfer(new PracticVirtual(), userId, orderId, productNames.toString(),amount).send();
					}
					if ("-1".equalsIgnoreCase(status)) {
						core.setAdapter(new MessageUserOrderFailedTemplate()).transfer(new PracticVirtual(), userId, orderId, productNames.toString(),amount).send();
					}
				}
			}
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return "OK";
		}
	}

}

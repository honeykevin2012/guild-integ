package com.game.platform;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.common.BaseForm;
import com.game.common.MessageConsValue;
import com.game.common.basics.BaseController;
import com.game.common.basics.JsonResult;
import com.game.common.basics.json.DateJsonValueProcessor;
import com.game.common.basics.pagination.PageQuery;
import com.game.common.utility.BuildFormErrorUtils;
import com.game.common.utility.CommonUtils;
import com.game.common.utility.DateUtils;
import com.game.platform.domain.MessageReader;
import com.game.platform.domain.MessageReaderAttach;
import com.game.platform.message.EntryEnum;
import com.game.platform.message.PracticVirtual;
import com.game.platform.persistence.service.MessageReaderService;
import com.game.user.domain.User;
import com.game.user.persistence.service.CartOrderService;
import com.game.user.persistence.service.UserService;
import com.game.user.utils.SessionTokenUtils;

@Controller
public class PlatformMsgController extends BaseController {
	private static final Logger logger = Logger.getLogger(PlatformMsgController.class);
	@Autowired
	private MessageReaderService readerService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartOrderService cartOrderService;

	@RequestMapping(value = "/platform/msg/list", method = { POST })
	public @ResponseBody Object list(@Valid @ModelAttribute("form") BaseForm form, BindingResult result, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if (CommonUtils.isNullEmpty(data)) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);// 解析token中的请求参数，转化成key
																					// value形式
				if (data == null) buildFormError(MessageConsValue.legalMessage);
				boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
				if (!legal || !dataMap.containsKey("userId")) return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Integer userId = Integer.valueOf(dataMap.get("userId").toString());
				MessageReader reader = new MessageReader();
				reader.setUserId(userId);
				readerService.insertByUserId(reader);
				MessageReader listParam = new MessageReader();
				listParam.setUserId(userId);
				List<MessageReader> list=readerService.selectByEntity(listParam);
				if(list==null||list.isEmpty()){
					return buildFormError("error#数据错误");
				}
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] { "msgId", "userId","typeEnum","isReceived","readTime","sendTime","type","amount"});
				config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				JsonResult jsonResult = new JsonResult();
				jsonResult.setData(JSONArray.fromObject(list, config));
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	/**
	 * 站内信列表分页
	 * @param userId
	 * @param pn
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/platform/message/pagelist", method = { POST })
	public @ResponseBody
	Object wap(Integer userId, Integer pn, HttpServletRequest request) {
		try {
			MessageReader reader = new MessageReader();
			reader.setUserId(userId);
			readerService.insertByUserId(reader);
			MessageReader listParam = new MessageReader();
			listParam.setUserId(userId);
			PageQuery query = new PageQuery();
			query.setPageSize(15);
			if (pn == null || pn == 0) query.setPageNo(1);
			else query.setPageNo(pn);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			query.setParams(map);
			List<MessageReader> list = readerService.query(query);
			if (list == null || list.isEmpty()) {
				return buildFormError("error#暂无更多数据.");
			}
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "msgId", "attachs" });
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
			JsonResult jsonResult = new JsonResult();
			jsonResult.setData(JSONArray.fromObject(list, config).toString());
			return jsonResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	/**
	 * 更新站内信状态， 未读变更为已读
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/platform/message/reader", method = { POST })
	public @ResponseBody Object reader(Integer id, HttpServletRequest request) {
		try {
			readerService.updateReader(id);
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/platform/message/select", method = { POST })
	public @ResponseBody Object select(Integer id, HttpServletRequest request) {
		try {
			MessageReader reader = readerService.select(id);
			if(reader == null) return buildFormError("error#请求数据异常.");
			JsonResult result = new JsonResult();
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			result.setData(JSONObject.fromObject(reader, config).toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	/**
	 * 更新站内信状态， 未读变更为已读
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/platform/message/received", method = { POST })
	public @ResponseBody Object received(Integer id, HttpServletRequest request) {
		try {
			if(id == null) return buildFormError(MessageConsValue.legalMessage);
			MessageReader reader = readerService.select(id);
			if("0".equals(reader.getIsReceived())){
				User user = userService.select(reader.getUserId());
				if(user != null){
					String ip = CommonUtils.realIPAddress(request);
					if(EntryEnum.GOLD.getIdentify().equals(reader.getType())){//领取N币
						long total = Long.valueOf(user.getBalance()) + reader.getSumQuantity();
						user.setBalance(total);
						int effect = userService.updateBalance(user);//更新用户余额
						if(effect == 1) readerService.updateReceived(id);//更新为已领取
					}else if(EntryEnum.PRACTIC.getIdentify().equals(reader.getType())){//领取实物
						if(reader.getAttachs().isEmpty()) return buildFormError("error#很遗憾, 物品领取失败.");
						List<PracticVirtual> entitys = new ArrayList<PracticVirtual>();
						for(MessageReaderAttach attach : reader.getAttachs()){
							PracticVirtual entity = new PracticVirtual();
							entity.setId(attach.getDataId());
							entity.setQuantity(attach.getQuantity());
							entity.setName(attach.getName());
							entity.setType(attach.getType());
							entity.setOrderId(attach.getOrderId());
							entitys.add(entity);
						}
						int effect = cartOrderService.received(user, entitys, reader.getFromGuildId(), request.getParameter("os"), ip);
						if(effect == 1) readerService.updateReceived(id);//更新为已领取
					}else if(EntryEnum.VIRTUAL.getIdentify().equals(reader.getType())){//领取虚拟物品
						if(reader.getAttachs().isEmpty()) return buildFormError("error#很遗憾, 物品领取失败.");
						List<PracticVirtual> entitys = new ArrayList<PracticVirtual>();
						for(MessageReaderAttach attach : reader.getAttachs()){
							PracticVirtual entity = new PracticVirtual();
							entity.setId(attach.getDataId());
							entity.setQuantity(attach.getQuantity());
							entity.setName(attach.getName());
							entity.setType(attach.getType());
							entity.setOrderId(attach.getOrderId());
							entitys.add(entity);
						}
						int effect = cartOrderService.received(user, entitys, reader.getFromGuildId(), request.getParameter("os"), ip);
						if(effect == 1) readerService.updateReceived(id);//更新为已领取
					}else{
						//do nothing...
					}
				}
			}else{
				if("1".equals(reader.getIsReceived())) return buildFormError("error#不能重复领取.");
				else return buildFormError("error#领取操作的数据异常.");
			}
			JsonResult result = new JsonResult();
			result.setData(reader.getType());
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
	
	// @RequestMapping(value="/platform/msg/list", method = { POST })
	// public @ResponseBody Object list(Integer userId,HttpServletRequest
	// request){
	// try{
	//
	// MessageReader reader=new MessageReader();
	// reader.setUserId(userId);
	// readerService.insertByUserId(reader);
	// MessageReader listParam=new MessageReader();
	// listParam.setUserId(userId);
	// List<MessageReader> list=readerService.selectByEntity(listParam);
	// JsonConfig config = new JsonConfig();
	// config.setExcludes(new String[] { "msgId","userId"});
	// config.registerJsonValueProcessor(java.util.Date.class, new
	// DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
	// JsonResult jsonResult = new JsonResult();
	// jsonResult.setData(JSONArray.fromObject(list,config));
	// return jsonResult;
	//
	// }catch(Exception e){
	// e.printStackTrace();
	// logger.error(e.getMessage());
	// return buildFormError("error#" + e.getMessage());
	// }
	// }

	@RequestMapping(value = "/platform/msg/new", method = { POST })
	public @ResponseBody
	Object newMsg(@Valid @ModelAttribute("form") BaseForm form,String id, BindingResult result, HttpServletRequest request) {
		try {
			if (result.hasErrors()) {
				return this.buildFormError(result.getAllErrors().get(0).getDefaultMessage());
			} else {
				String data = form.getData();
				if (CommonUtils.isNullEmpty(data))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Map<String, Object> dataMap = SessionTokenUtils.dataAnalsy(data);// 解析token中的请求参数，转化成key
																					// value形式
				if (data == null)
					buildFormError("error#" + MessageConsValue.legalMessage);
				boolean legal = SessionTokenUtils.validateLogin(dataMap, form.getNuid(), form.getOs());
				if (!legal || !dataMap.containsKey("userId"))
					return BuildFormErrorUtils.buildFormError(MessageConsValue.legalMessage);
				Integer userId = Integer.valueOf(dataMap.get("userId").toString());
				Integer msgId=0;
				if(id!=null&&!"".equals(id)){
					msgId=Integer.valueOf(id);
				}
				MessageReader msg = readerService.selectNewMsg(userId,msgId);
				JsonResult resultData = new JsonResult();
				JSONObject obj=new JSONObject();
				if(msg!=null){
					obj.put("id", msg.getId());
					obj.put("title", msg.getTitle());
					obj.put("content", msg.getContent());
					obj.put("createTime", DateUtils.format(msg.getSendTime(),"yyyy-MM-dd HH:mm:ss"));
				}
				resultData.setData(obj);
				return resultData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return buildFormError("error#" + e.getMessage());
		}
	}
}

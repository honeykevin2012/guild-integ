package com.game.platform.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
/**
 * 入会申请邮件
 * @author kevin
 */
public class MessageUserOrderFailedTemplate extends MessageTemplate{
	private static String NODE_NAME = "/setting/messageUserOrderFailed/messageUserOrderFailedTemplate";
	private static String NODE_TITLE_NAME = "/setting/messageUserOrderFailed";
	private static String SUB_NODE_NAME = "orderFailed";
	private static Map<String, Object> template = new HashMap<String, Object>();
	@Override
	public EntryEnum getEntry() {
		return EntryEnum.NORMAL;
	}
	
	private String getTitle(){
		Element node = getSingleNode(NODE_TITLE_NAME);
		return node.attributeValue(ConsValues.TITLE);
	}
	
	public String createTemplate() {
		if (template.isEmpty()) this.loadTemplate();
		StringBuilder builder = new StringBuilder();
		MessageTemplateEntity entity = (MessageTemplateEntity) template.get(SUB_NODE_NAME);
		builder.append(entity.getValue()).append("\r\n");
		return builder.toString();
	}

	/**
	 * values[0]=userId(用户ID), values[1]=orderId, values[2]=productNames
	 */
	@Override
	public Map<String, Object> getParameters(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		String content = this.createTemplate();
		params.put(ConsValues.ENTRY, this.getEntry().getIdentify());
		params.put(ConsValues.TITLE, this.getTitle());
		String orderId = null;
		String productNames = null;
		String amount = null;
		if(values != null && values.length > 0){
			Integer userId = Integer.parseInt(values[0].toString());
			orderId = values[1].toString();
			productNames = values[2].toString();
			amount = values[3].toString();
			params.put(ConsValues.USER_ID, userId);
			params.put("orderId", orderId);
			params.put("productNames", productNames);
			params.put(ConsValues.CONTENT, MessageFormat.format(content, orderId, productNames, amount));
		}
		return params;
	}

	@Override
	public void loadTemplate() {
		List<Element> nodes = getNodeList(NODE_NAME);
		if (nodes != null && !nodes.isEmpty()) {
			for (Element node : nodes) {
				String key = node.attributeValue("key");
				String value = node.attributeValue("value");
				MessageTemplateEntity entity = new MessageTemplateEntity();
				entity.setKey(key);
				entity.setValue(value);
				template.put(key, entity);
			}
		}
	}

	public static void main(String[] args) {
		MessageCore core = new MessageCore();
		String productNames = "[充电器]\r\n[手机]\r\n[电话]";
		core.setAdapter(new MessageUserOrderFailedTemplate()).transfer(new PracticVirtual(), "4", "201522335888", productNames, "1000").send();
	}
}

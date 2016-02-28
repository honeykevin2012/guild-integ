package com.game.platform.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
/**
 * 用户级别晋升系统奖励邮件
 * @author kevin
 */
public class MessageUserLevelUpTemplate extends MessageTemplate{
	private static String NODE_NAME = "/setting/messageUserLevelUp/messageUserLevelUpTemplate";
	private static String NODE_TITLE_NAME = "/setting/messageUserLevelUp";
	private static String SUB_NODE_NAME = "levelUp";
	private static Map<String, Object> template = new HashMap<String, Object>();
	@Override
	public EntryEnum getEntry() {
		return EntryEnum.GOLD;
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
	 * values[0]=userId(用户ID), values[2]=level(级别)
	 */
	@Override
	public Map<String, Object> getParameters(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		String content = this.createTemplate();
		params.put(ConsValues.ENTRY, this.getEntry().getIdentify());
		params.put(ConsValues.TITLE, this.getTitle());
		String level = null;
		if(values != null && values.length > 0){
			Integer userId = Integer.parseInt(values[0].toString());
			level = values[1].toString();
			params.put(ConsValues.USER_ID, userId);
			params.put("level", level);
		}
		if(!this.getPractics().isEmpty()){
			PracticVirtual practic = this.getPractics().get(0);
			params.put(ConsValues.CONTENT, MessageFormat.format(content, level, practic.getQuantity()));
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
		PracticVirtual practic = new PracticVirtual();
		practic.setId(0);
		practic.setQuantity(20);
		core.setAdapter(new MessageUserLevelUpTemplate()).transfer(practic, "4", 30).send();
	}
}

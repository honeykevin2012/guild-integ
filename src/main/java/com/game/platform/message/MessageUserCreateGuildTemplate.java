package com.game.platform.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
/**
 * 用户签到系统奖励邮件
 * @author kevin
 */
public class MessageUserCreateGuildTemplate extends MessageTemplate{
	private static String NODE_NAME = "/setting/messageCreateGuild/messageCreateGuildTemplate";
	private static String NODE_TITLE_NAME = "/setting/messageCreateGuild";
	private static String SUB_NODE_NAME = "createGuild";
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
		String valueTemplate = MessageFormat.format(entity.getValue(), entity.getExp(), entity.getGold());
		builder.append(valueTemplate).append("\r\n");
		return builder.toString();
	}
	
	private int getQuantity(){
		MessageTemplateEntity entity = (MessageTemplateEntity) template.get(SUB_NODE_NAME);
		return Integer.parseInt(entity.getGold());
	}

	@Override
	public Map<String, Object> getParameters(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ConsValues.CONTENT, this.createTemplate());
		params.put(ConsValues.ENTRY, this.getEntry().getIdentify());
		params.put(ConsValues.TITLE, this.getTitle());
		params.put(ConsValues.RECEIVE, this.getQuantity());
		if(values != null && values.length > 0){
			Integer userId = Integer.parseInt(values[0].toString());
			params.put(ConsValues.USER_ID, userId);
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
				String gold = node.attributeValue("gold");
				String exp = node.attributeValue("exp");
				MessageTemplateEntity entity = new MessageTemplateEntity();
				entity.setKey(key);
				entity.setValue(value);
				entity.setGold(gold);
				entity.setExp(exp);
				template.put(key, entity);
			}
		}
	}

	public static void main(String[] args) {
		MessageCore core = new MessageCore();
		core.setAdapter(new MessageUserCreateGuildTemplate()).transfer(new PracticVirtual(), "4", "29").send();
	}
}

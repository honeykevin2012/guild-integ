package com.game.platform.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
/**
 * 用户快速注册邮件提醒
 * @author kevin
 */
public class MessageUserATRegisterTemplate extends MessageTemplate{
	private static String NODE_NAME = "/setting/messageUserAtRegister/messageUserAtRegisterTemplate";
	private static String NODE_TITLE_NAME = "/setting/messageUserAtRegister";
	private static String SUB_NODE_NAME = "atRegister";
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
	
	@Override
	public Map<String, Object> getParameters(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ConsValues.ENTRY, this.getEntry().getIdentify());
		params.put(ConsValues.TITLE, this.getTitle());
		String userName = null;
		String password = null;
		if(values != null && values.length > 0){
			Integer userId = Integer.parseInt(values[0].toString());
			params.put(ConsValues.USER_ID, userId);
			userName = values[1].toString();
			password = values[2].toString();
		}
		String content = this.createTemplate();
		content = MessageFormat.format(content, userName, password);
		params.put(ConsValues.CONTENT, content);
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
		core.setAdapter(new MessageUserATRegisterTemplate()).transfer(new PracticVirtual(), "4", "madoka", "123456").send();
	}
}

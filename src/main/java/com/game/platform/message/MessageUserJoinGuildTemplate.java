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
public class MessageUserJoinGuildTemplate extends MessageTemplate{
	private static String NODE_NAME = "/setting/messageUserJoinGuild/messageUserJoinGuildTemplate";
	private static String NODE_TITLE_NAME = "/setting/messageUserJoinGuild";
	private static String SUB_NODE_NAME = "joinGuild";
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
	 * values[0]=userId(用户ID), values[2]=level(级别)
	 */
	@Override
	public Map<String, Object> getParameters(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		String content = this.createTemplate();
		params.put(ConsValues.ENTRY, this.getEntry().getIdentify());
		params.put(ConsValues.TITLE, this.getTitle());
		String applyUserName = null;
		if(values != null && values.length > 0){
			Integer userId = Integer.parseInt(values[0].toString());
			applyUserName = values[1].toString();
			params.put(ConsValues.USER_ID, userId);
			params.put(ConsValues.CONTENT, MessageFormat.format(content, applyUserName));
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
		core.setAdapter(new MessageUserJoinGuildTemplate()).transfer(new PracticVirtual(), "4", "无上荣耀").send();
	}
}

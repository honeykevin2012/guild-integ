package com.game.platform.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
/**
 * 用户红包奖励邮件
 * @author kevin
 */
public class MessageUserRedEnvelopeTemplate extends MessageTemplate{
	private static String NODE_NAME = "/setting/messageUserRedEnvelope/messageUserRedEnvelopeTemplate";
	private static String NODE_TITLE_NAME = "/setting/messageUserRedEnvelope";
	private static String SUB_NODE_NAME = "redEnvelope";
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
	 * 0=用户ID, 1=红包金额
	 */
	@Override
	public Map<String, Object> getParameters(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ConsValues.ENTRY, this.getEntry().getIdentify());
		if(values != null && values.length > 0){
			Integer userId = Integer.parseInt(values[0].toString());
			String from = values[1].toString();
			params.put(ConsValues.TITLE, MessageFormat.format(this.getTitle(), from));
			params.put(ConsValues.USER_ID, userId);
		}
		if(!this.getPractics().isEmpty()){
			StringBuilder practicNames = new StringBuilder();
			for(PracticVirtual practic : this.getPractics()){
				String content = MessageFormat.format(this.createTemplate(), practic.getQuantity());
				practicNames.append(EntryEnum.GOLD.getIcon()).append(" ").append(content);
			}
			params.put(ConsValues.CONTENT, practicNames.toString());
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
		PracticVirtual practic = new PracticVirtual();
		practic.setId(0);
		practic.setQuantity(5);
		core.setAdapter(new MessageUserRedEnvelopeTemplate()).transfer(practic, "4", "无上荣耀").send();
	}
}

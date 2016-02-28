package com.game.platform.message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
/**
 * 实物赠送邮件
 * @author kevin
 */
public class MessageUserPracticVirtualTemplate extends MessageTemplate{
	private static String NODE_NAME = "/setting/messageUserPracticVirtual/messageUserPracticVirtualTemplate";
	private static String NODE_TITLE_NAME = "/setting/messageUserPracticVirtual";
	private static String SUB_NODE_NAME = "virtual";
	private static Map<String, Object> template = new HashMap<String, Object>();
	@Override
	public EntryEnum getEntry() {
		return EntryEnum.VIRTUAL;
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
	 * 0=用户ID, 1=金额, 2=来源(公会名称)
	 */
	@Override
	public Map<String, Object> getParameters(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ConsValues.ENTRY, this.getEntry().getIdentify());
		String from = null;
		if(values != null && values.length > 0){
			Integer userId = Integer.parseInt(values[0].toString());
			Integer guildId = Integer.parseInt(values[1].toString());
			from = values[2].toString();//谁送的
			params.put(ConsValues.TITLE, MessageFormat.format(this.getTitle(), from));
			params.put(ConsValues.USER_ID, userId);
			params.put(ConsValues.GUILD_ID, guildId);
		}
		if(!this.getPractics().isEmpty()){
			StringBuilder practicNames = new StringBuilder();
			for(PracticVirtual practic : this.getPractics()){
				practicNames.append(EntryEnum.VIRTUAL.getIcon()).append(" ").append(practic.getName()).append(".\r\n");
			}
			params.put(ConsValues.CONTENT, MessageFormat.format(this.createTemplate(), from == null ? "" : from, practicNames));
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
		List<PracticVirtual> attacts = new ArrayList<PracticVirtual>();
		PracticVirtual practic = new PracticVirtual();
		practic.setId(100053);
		practic.setName("nut智能寻物防丢贴片(阳光橙)");
		practic.setQuantity(5);
		PracticVirtual practic1 = new PracticVirtual();
		practic1.setId(100055);
		practic1.setName("激活码大礼包");
		practic1.setQuantity(2);
		practic1.setOrderId("172");
		attacts.add(practic1);
		attacts.add(practic);
		core.setAdapter(new MessageUserPracticVirtualTemplate()).transfer(attacts, "4", "哈哈哈").send();
	}
}

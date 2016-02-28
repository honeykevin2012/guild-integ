package com.game.platform.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
/**
 * 资料完善系统奖励邮件
 * @author kevin
 */
public class MessageUserEditTemplate extends MessageTemplate {
	private static String NODE_NAME = "/setting/messageUserEdit/messageUserEditTemplate";
	private static String NODE_TITLE_NAME = "/setting/messageUserEdit";
	private static Map<String, Object> template = new HashMap<String, Object>();
	private Long receiveAmount = 0L;
	private Set<String> set = new HashSet<String>();
	public MessageUserEditTemplate(Set<String> set){
		this.set = set;
	}
	public void loadTemplate() {
		List<Element> nodes = getNodeList(NODE_NAME);
		if (nodes != null && !nodes.isEmpty()) {
			for (Element node : nodes) {
				String key = node.attributeValue("key");
				String value = node.attributeValue("value");
				String gold = node.attributeValue("gold");
				MessageTemplateEntity entity = new MessageTemplateEntity();
				entity.setKey(key);
				entity.setValue(value);
				entity.setGold(gold);
				template.put(key, entity);
			}
		}
	}
	
	private String getTitle(){
		Element node = getSingleNode(NODE_TITLE_NAME);
		return node.attributeValue(ConsValues.TITLE);
	}
	
	/**
	 * 构造邮件模板
	 * @param set 用户修改的信息项标识（如：sex, qq, phone, avatar, email, nick）
	 * @return
	 */
	public String createTemplate() {
		if (template.isEmpty()) this.loadTemplate();
		if (set.isEmpty()) return null;
		StringBuilder builder = new StringBuilder();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String key = it.next();
			MessageTemplateEntity entity = (MessageTemplateEntity) template.get(key);
			if(entity == null) continue;
			String valueTemplate = MessageFormat.format(entity.getValue(), entity.getGold());
			builder.append(valueTemplate).append("\r\n");
			if(entity.getGold() != null && !"".equals(entity.getGold().trim())) receiveAmount += Long.valueOf(entity.getGold());
		}
		return builder.toString();
	}
	
	@Override
	public EntryEnum getEntry() {
		return EntryEnum.GOLD;
	}
	
	@Override
	public Map<String, Object> getParameters(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ConsValues.CONTENT, this.createTemplate());
		params.put(ConsValues.RECEIVE, receiveAmount);
		params.put(ConsValues.ENTRY, this.getEntry().getIdentify());
		params.put(ConsValues.TITLE, this.getTitle());
		if(values != null && values.length > 0){
			Integer userId = Integer.parseInt(values[0].toString());
			params.put(ConsValues.USER_ID, userId);
		}
		return params;
	}

	public Long getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Long receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public Set<String> getSet() {
		return set;
	}
	public void setSet(Set<String> set) {
		this.set = set;
	}
	
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		set.add("sex");
		set.add("qq");
		set.add("phone");
		set.add("avatar");
		set.add("email");
		//MessageUserEditTemplate t = new MessageUserEditTemplate(set);
		//System.out.println(t.createTemplate());
		//System.out.println(t.getEntry().getIdentify());
		//System.out.println(t.getParameters("300", "200"));
		
		MessageCore core = new MessageCore();
		core.setAdapter(new MessageUserEditTemplate(set)).transfer(new PracticVirtual(), "4").send();
	}
}

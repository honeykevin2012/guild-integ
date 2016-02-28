package com.game.platform.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.game.init.CommonXMLValues;

public abstract class MessageTemplate {
	public static final String TEMPLATE_PATH = CommonXMLValues.SETTING_XML_PATH;

	public static org.dom4j.Element getSingleNode(String path) {
		return CommonXMLValues.getSingleNode(path);
	}

	public static List<org.dom4j.Element> getNodeList(String path) {
		return CommonXMLValues.getNodeList(path);
	}
	
	public List<PracticVirtual> practics = new ArrayList<PracticVirtual>();
	/**
	 * 加载模板
	 */
	public abstract void loadTemplate();

	/**
	 * 定义该消息类型EntryEnum(NB、实物、虚拟物)
	 * @return
	 */
	public abstract EntryEnum getEntry();

	/**
	 * 初始化模板后， 需要返回业务参数
	 * @param values
	 * @return
	 */
	public abstract Map<String, Object> getParameters(Object... values);
	
	public List<PracticVirtual> getPractics() {
		return practics;
	}

	public void setPractics(List<PracticVirtual> practics) {
		this.practics = practics;
	}

	public class MessageTemplateEntity{
		private String key;
		private String value;
		private String gold;
		private String exp;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getGold() {
			return gold;
		}
		public void setGold(String gold) {
			this.gold = gold;
		}
		public String getExp() {
			return exp;
		}
		public void setExp(String exp) {
			this.exp = exp;
		}
	}
}

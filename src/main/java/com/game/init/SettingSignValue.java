//package com.game.init;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.dom4j.DocumentException;
//import org.dom4j.io.SAXReader;
//import org.springframework.core.io.ClassPathResource;
//
//public class SettingSignValue {
//	public static Map<Integer, SettingSignValue> valueMap = new HashMap<Integer, SettingSignValue>();
//	private Integer count;// 签到次数
//	private Integer experience;// 奖励经验
//	private Integer gold;// 奖励N币
//
//	static {
//		org.dom4j.Document document = null;
//		try {
//			File settingXmlFile = new ClassPathResource(CommonXMLValues.SETTING_XML_PATH).getFile();
//			document = new SAXReader().read(settingXmlFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		//Element element = (Element) document.selectSingleNode("/setting/template[@id='" + id + "']");
//		@SuppressWarnings("unchecked")
//		List<org.dom4j.Element> nodes = document.selectNodes("/setting/sign");
//		for (org.dom4j.Element element : nodes) {
//			SettingSignValue value = new SettingSignValue();
//			Integer count = Integer.valueOf(element.attributeValue("count"));
//			int experience = Integer.valueOf(element.attributeValue("experience"));
//			int gold = Integer.valueOf(element.attributeValue("experience"));
//			value.setCount(count);
//			value.setExperience(experience);
//			value.setGold(gold);
//			valueMap.put(count, value);
//		}
//	}
//
//	public Integer getCount() {
//		return count;
//	}
//
//	public void setCount(Integer count) {
//		this.count = count;
//	}
//
//	public Integer getExperience() {
//		return experience;
//	}
//
//	public void setExperience(Integer experience) {
//		this.experience = experience;
//	}
//
//	public Integer getGold() {
//		return gold;
//	}
//
//	public void setGold(Integer gold) {
//		this.gold = gold;
//	}
//}

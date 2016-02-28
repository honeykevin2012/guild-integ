package com.game.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class SettingListener implements ApplicationListener<ContextRefreshedEvent> {

	public static Map<String, String> CACHE = new HashMap<String, String>();// 加载setting配置文件

	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		loadSetting();
	}

	/**
	 * 获取游戏的查询、扣款、退款接口地址
	 * @return
	 */
	public static void loadSetting() {
		List<Element> elements = CommonXMLValues.getNodeList("/setting/setting");
		for (Element element : elements) {
			String name = element.attributeValue("name");
			String value = element.attributeValue("value");
			CACHE.put(name, value);
		}
	}

	public static String getValue(String key) {
		if (CACHE.isEmpty()) loadSetting();
		return CACHE.get(key);
	}
}
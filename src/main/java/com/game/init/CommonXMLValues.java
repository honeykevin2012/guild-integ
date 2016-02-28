package com.game.init;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

public class CommonXMLValues {
	/** setting.xml文件路径 */
	public static final String SETTING_XML_PATH = "/setting.xml";

	public static org.dom4j.Element getSingleNode(String path) {
		File shopxxXmlFile;
		org.dom4j.Document document = null;
		try {
			shopxxXmlFile = new ClassPathResource(SETTING_XML_PATH).getFile();
			document = new SAXReader().read(shopxxXmlFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		org.dom4j.Element element = (org.dom4j.Element) document.selectSingleNode(path);
		return element;
	}

	public static List<org.dom4j.Element> getNodeList(String path) {
		File shopxxXmlFile;
		org.dom4j.Document document = null;
		try {
			shopxxXmlFile = new ClassPathResource(SETTING_XML_PATH).getFile();
			document = new SAXReader().read(shopxxXmlFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		List<org.dom4j.Element> nodeList = document.selectNodes(path);
		return nodeList;
	}

	public static void main(String[] args) throws IOException, DocumentException {
		// String name = "levelOne";
		// org.dom4j.Element element = getElement(name);
		// System.out.println(element.attributeValue("count"));
		System.out.println(getSingleNode("/setting/signMessage").attributeValue("title"));
	}
}

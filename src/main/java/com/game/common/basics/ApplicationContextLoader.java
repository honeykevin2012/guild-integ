package com.game.common.basics;

import java.io.IOException;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextLoader implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextLoader.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static Object getBean(String name, Class<?> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
	
	public static Object getMe(){
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11211"));
        XMemcachedClient memcachedClient=null;
		try {
			memcachedClient = (XMemcachedClient) builder.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return memcachedClient;
	}
}

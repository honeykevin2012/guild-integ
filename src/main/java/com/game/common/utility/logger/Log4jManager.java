package com.game.common.utility.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Log4jManager {
	public static Log defaultLog(Class<?> clazz) {
		return LogFactory.getLog(clazz);
	}

	public static Log defaultLog(String name) {
		return LogFactory.getLog(name);
	}

	public static Log getLog(Class<?> clazz) {
		return LogFactory.getLog(clazz);
	}

	public static Log getLog(String name) {
		return LogFactory.getLog(name);
	}
}
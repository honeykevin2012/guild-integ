package com.game.common.basics.persistence;

import org.springframework.util.Assert;

public class DataSourceSwitcher {
	
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal contextHolder = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public static void setDataSource(String dataSource) {
		Assert.notNull(dataSource, "dataSource cannot be null");
		contextHolder.set(dataSource);
	}

	public static void setMaster(){
		clearDataSource();
		System.out.println("切换到: master");
    }
	
	public static void setSlave(int slaveId) {
		setDataSource("slave" + slaveId);
		System.out.println("切换到: slave"+slaveId);
	}
	
	public static String getDataSource() {
		return (String) contextHolder.get();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}
}



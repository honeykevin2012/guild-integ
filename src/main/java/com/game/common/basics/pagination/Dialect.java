package com.game.common.basics.pagination;
public abstract class Dialect {
	
	public static enum Type{
		MYSQL,
		ORACLE
	}
	public abstract String getLimitString(String sql, int skipResults, int maxResults);

}
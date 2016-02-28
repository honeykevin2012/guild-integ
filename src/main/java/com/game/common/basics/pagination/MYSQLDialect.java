package com.game.common.basics.pagination;
public class MYSQLDialect extends Dialect{
	
	protected static final String SQL_END_DELIMITER = ";";
	
	public String getLimitString(String sql, boolean hasOffset) {
		return MYSQLHepler.getLimitString(sql,-1,-1);
	}

	public String getLimitString(String sql, int offset, int limit) {
		return MYSQLHepler.getLimitString(sql, offset, limit);
	}

	public boolean supportsLimit() {
		return true;
	}
}
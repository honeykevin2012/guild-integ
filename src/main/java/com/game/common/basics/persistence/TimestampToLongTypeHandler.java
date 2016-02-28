package com.game.common.basics.persistence;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class TimestampToLongTypeHandler implements TypeHandler<Long> {

	@Override
	public Long getResult(ResultSet arg0, String arg1) throws SQLException {
		Timestamp result = arg0.getTimestamp(arg1);
		return result.getTime();
	}

	@Override
	public Long getResult(ResultSet arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public Long getResult(CallableStatement arg0, int arg1)
			throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, Long arg2,
			JdbcType arg3) throws SQLException {

	}
}

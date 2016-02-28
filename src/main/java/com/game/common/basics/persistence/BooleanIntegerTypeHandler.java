package com.game.common.basics.persistence;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class BooleanIntegerTypeHandler implements TypeHandler<String> {

	@Override
	public String getResult(ResultSet arg0, String arg1) throws SQLException {
		int result = arg0.getInt(arg1);
		return (result == 1) ? "true" : "false";
	}

	@Override
	public String getResult(ResultSet arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public String getResult(CallableStatement arg0, int arg1)
			throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, String arg2,
			JdbcType arg3) throws SQLException {
	}

}

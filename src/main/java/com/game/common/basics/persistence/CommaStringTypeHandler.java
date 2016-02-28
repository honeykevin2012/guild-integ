package com.game.common.basics.persistence;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class CommaStringTypeHandler implements TypeHandler<Object> {

	@Override
	public List<String> getResult(ResultSet arg0, String arg1) throws SQLException {
		String result = arg0.getString(arg1);
		String[] returnObj =result.split(",");
		List<String> list = Arrays.asList(returnObj);
		return list;
	}

	@Override
	public List<String> getResult(ResultSet arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public List<String> getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, Object arg2,
			JdbcType arg3) throws SQLException {
	}
}

package com.game.common.basics.pagination;
 
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;
 
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class}) })
public class PageInterceptor implements Interceptor {
	private static Logger logger = Logger.getLogger(PageInterceptor.class);
    private String dbType;
   
    public Object intercept(Invocation invocation) throws Throwable {
       RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
       StatementHandler delegate = (StatementHandler)ReflectUtil.getFieldValue(handler, "delegate");
       BoundSql boundSql = delegate.getBoundSql();
       Object obj = boundSql.getParameterObject();
       if (obj instanceof PageQuery) {
           PageQuery page = (PageQuery) obj;
           MappedStatement mappedStatement = (MappedStatement)ReflectUtil.getFieldValue(delegate, "mappedStatement");
           Connection connection = (Connection)invocation.getArgs()[0];
           String sql = boundSql.getSql();
           if(page.isFlag()){
        	  this.setTotalRecord(page, mappedStatement, connection);
           }
           String pageSql = this.getPageSql(page, sql);
           logger.info("paginationSQL : " + pageSql);
           ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
       }else{
    	   logger.info(boundSql.getSql());
       }
       return invocation.proceed();
    }
 
 
    /**
     */
    public Object plugin(Object target) {
       return Plugin.wrap(target, this);
    }
 
    /**
     */
    public void setProperties(Properties properties) {
       this.dbType = properties.getProperty("dbType");
    }
   
    private String getPageSql(PageQuery page, String sql) {
    	Dialect.Type dt = Dialect.Type.valueOf(dbType);
    	Dialect dialect = null;
    	switch (dt) {
			case MYSQL:
				dialect = new MYSQLDialect();
				break;
			case ORACLE:
				dialect = new OracleDialect();
				break;
		}
       int offset = (page.getPageNo() - 1) * page.getPageSize();
       return dialect.getLimitString(sql, offset, page.getPageSize());
    }
   
    private void setTotalRecord(PageQuery page, MappedStatement mappedStatement, Connection connection) {
       BoundSql boundSql = mappedStatement.getBoundSql(page);
       String sql = boundSql.getSql();
       String countSql = this.getCountSql(sql);
       logger.debug("Count SQL : " + countSql);
       List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
       BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
       ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = connection.prepareStatement(countSql);
           parameterHandler.setParameters(pstmt);
           rs = pstmt.executeQuery();
           if (rs.next()) {
              int totalRecord = rs.getInt(1);
              page.setTotalCount(totalRecord);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           try {
              if (rs != null)
                  rs.close();
               if (pstmt != null)
                  pstmt.close();
           } catch (SQLException e) {
              e.printStackTrace();
           }
       }
    }
   
    private String getCountSql(String sql) {
    	return MYSQLHepler.getCountString(sql);
    }
   
    private static class ReflectUtil {
       /**
        * 利用反射获取指定对象的指定属性
        * @param obj 目标对象
        * @param fieldName 目标属性
        * @return 目标属性的值
        */
       public static Object getFieldValue(Object obj, String fieldName) {
           Object result = null;
           Field field = ReflectUtil.getField(obj, fieldName);
           if (field != null) {
              field.setAccessible(true);
              try {
                  result = field.get(obj);
              } catch (IllegalArgumentException e) {
                  e.printStackTrace();
              } catch (IllegalAccessException e) {
                  e.printStackTrace();
              }
           }
           return result;
       }
      
       /**
        * 利用反射获取指定对象里面的指定属性
        * @param obj 目标对象
        * @param fieldName 目标属性
        * @return 目标字段
        */
       private static Field getField(Object obj, String fieldName) {
          Field field = null;
          for (Class<?> clazz=obj.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {
              try {
                  field = clazz.getDeclaredField(fieldName);
                  break;
              } catch (NoSuchFieldException e) {
                  //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
              }
           }
           return field;
       }
 
       /**
        * 利用反射设置指定对象的指定属性为指定的值
        * @param obj 目标对象
        * @param fieldName 目标属性
         * @param fieldValue 目标值
        */
       public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
           Field field = ReflectUtil.getField(obj, fieldName);
           if (field != null) {
              try {
                  field.setAccessible(true);
                  field.set(obj, fieldValue);
              } catch (IllegalArgumentException e) {
                  e.printStackTrace();
              } catch (IllegalAccessException e) {
                  e.printStackTrace();
              }
           }
        }
    }
}
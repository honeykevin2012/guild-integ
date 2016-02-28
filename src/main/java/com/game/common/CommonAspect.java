package com.game.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.game.common.basics.JsonResult;
import com.game.common.basics.Result;
import com.game.common.utility.DateUtils;
import com.game.common.utility.DebugUtils;
import com.game.log.SwitchLogger;

/**
 * 共通切片处理类
 * @author zhaolianjun
 */
@Component
@Aspect
public class CommonAspect {
	
	private static final Logger logger = Logger.getLogger(CommonAspect.class);
	private static final String lineStart = "log start #############################################################\r\n";
	private static final String lineEnd = "log end #################################################################\r\n";
	private static final String LOGGER_METHOD = "logger";
	private static final String actionName = "@@@@@@@@@@@@@@@@@ Action method : ";
	private static final String actionDesc = ", \nResult is :";
	
	@Pointcut(value="execution(public * com.game.*.*.*(..))")
	public void controllerCut(){}
	
	/**
	 * 控制器类调用【围绕】切片处理：系统错误记录及数据返回
	 * 
	 * @param point JoinPoint
	 */
	@Around("controllerCut()")
    public Object controllerAround(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		try{
	        retVal = pjp.proceed();
		} catch(Exception ex) {
			ex.printStackTrace();
			String methodName = pjp.getSignature().getName();
			StackTraceElement[] ele = ex.getStackTrace();
			StringBuilder build = new StringBuilder();
			build.append(lineStart).append("\r\n");
			build.append(ex.getMessage());
			for(StackTraceElement el : ele){
				build.append(el.toString()).append("\r\n");
			}
			build.append(lineEnd);
			logger.error(build.toString());
			com.game.common.basics.Error error = new com.game.common.basics.Error();
			error.setState("error");
			error.setMsg("系统错误" + ", Method:" + methodName + ", Reason:" + ex.getMessage());
			return error;
		}
//		Object[] join = pjp.getArgs();
//		if(join != null && join.length > 0){
//			for(Object o : join){
//				if(o instanceof BaseForm){
//					BaseForm form = (BaseForm)o;
//					String data = form.getData();
//					if(data != null && !"".equals(data)){
//						String ck = SessionTokenUtils.getSessionCK(data);
//						try {
//							retVal = ThreeDESUtils.encryptThreeDESECB(JSONObject.fromObject(retVal).toString(), ck);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						break;
//					}
//				}
//			}
//		}
		return retVal;
    }
	@Before("controllerCut()")
	public void doBefore(JoinPoint joinPoint){
		if(!LOGGER_METHOD.equals(joinPoint.getSignature().getName())){
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			logger.info("URL: " + request.getRequestURL());
			logger.info(lineStart + DebugUtils.debug(request) + "\r\n" + lineEnd);
		}
	}
	/**
	 * 控制器类调用【后】切片处理：调用参数及返回结果写入日志
	 * 
	 * @param point JoinPoint
	 */
	@AfterReturning(pointcut="controllerCut()", returning="retVal")
	public void doAfterReturning(JoinPoint joinPoint, Object retVal) {
		Object[] args = joinPoint.getArgs();
		BaseForm form = null;
		if(args[0] instanceof BaseForm){
			form = (BaseForm) args[0];
		}
		String dateTime = null;
		Object res = null;
		String methodName = joinPoint.getSignature().getName();
		if(retVal instanceof Result){
			res = (Result)retVal;
		}else if(retVal instanceof Result){
			res = (Result)retVal;
		}else if(retVal instanceof JsonResult){
			res = (JsonResult)retVal;
		}else if(retVal instanceof Map){
			res = (HashMap<?, ?>)retVal;
		}
		if(!LOGGER_METHOD.equals(methodName)){
			dateTime = ", At time " + DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");
			logger.info(actionName + methodName + dateTime + actionDesc + JSONObject.fromObject(res) + "\n");
		}
		
		//write log to mongo db
		try{
			SwitchLogger.create(methodName, form);//mongodb logger
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
	}
} 
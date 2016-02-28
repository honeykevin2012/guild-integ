package com.game.common.basics.aop;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ognl.Ognl;
import ognl.OgnlException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.game.common.basics.cache.CbaseHelper;
import com.game.common.basics.pagination.PageQuery;
 
@Component
@Aspect
public class MemcachedCacheInterceptor {
    
    private static final String GET = "@annotation(LoadFromMemcached)";
    private static final String UPDATE = "@annotation(UpdateForMemcached)";
    private static final String ZERO_STRING = "";
    private static final String SPLIT = ":";
    private static final String EQUALS = "=";//相等
    private static final String NOT_EQUALS = "<>";//不等
    //private static final String MORE_THAN = ">";
    //private static final String LESS_THAN = "<";
    private static CbaseHelper cache = CbaseHelper.getInstance();
    private static Logger log = LoggerFactory.getLogger(MemcachedCacheInterceptor.class);
    
    /**
     * 
     * @Title: get
     * @Description: 首先从缓存中加载数据，缓存命中则返回数据，未命中则从数据库查找，并加入缓存
     * @param @param call
     * @param @return
     * @param @throws Throwable
     * @return Object
     * @throws
     */
    @Around(GET)
    public Object get(ProceedingJoinPoint call) throws Throwable {
        LoadFromMemcached anno = getAnnotation(call,LoadFromMemcached.class);
        boolean isSavaCache = casevar(anno.casevar(), call);
        if(!isSavaCache) return call.proceed();//若缓存条件不符合时, 直接读取数据库并且不进行缓存
        	
        String prefixKey = anno.value();
        String suffixKey = createKey(anno.condition(), call);
        String key = prefixKey + suffixKey;
        key = trimEndWith(key);
        int timeout = anno.timeout();
        Object value = null;
        value = cache.get(key);
        if(value == null){
            value = call.proceed();
            if(value != null){
        		cache.add(key, timeout, value);
        		cache.setKeys(key);
        		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> Add data for memcached success! About Key : " + key);
            }
        }
        return value;
    }
    
    /**
     * 
     * @Title: update
     * @Description: 执行方法的同时更新缓存中的数据
     * @param @param call
     * @param @return
     * @param @throws Throwable
     * @return Object
     * @throws
     */
    @Around(UPDATE)
    public Object update(ProceedingJoinPoint call) throws Throwable {
        UpdateForMemcached anno = getAnnotation(call,UpdateForMemcached.class);
        String[] key = anno.value();//可能需要更新多个key
        Set<String> keySet = parserKey(anno.condition(), key, call);
        Object value = call.proceed();
        if(value != null){
        	Iterator<String> it = keySet.iterator();
        	while(it.hasNext()){
        		String k = trimEndWith(it.next());
        		cache.delete(k);
        		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> Update Data For Memcached Success!About Key : "+ k);
        	}
        }
        return value;
    }
    
    /**
     * 
     * @Title: getAnnotation
     * @Description: 获得Annotation对象
     * @param @param <T>
     * @param @param jp
     * @param @param clazz
     * @param @return
     * @return T
     * @throws
     */
    private <T  extends Annotation> T getAnnotation(ProceedingJoinPoint jp,Class<T> clazz){
        MethodSignature joinPointObject = (MethodSignature) jp.getSignature();  
        Method method = joinPointObject.getMethod();
        return method.getAnnotation(clazz);  
    }
    
    /**
     * 
     * @Title: getKeyNameFromParam
     * @Description: 获得组合后的KEY值
     * @param @param key
     * @param @param jp
     * @param @return
     * @return String
     * @throws
     
    private String getKeyNameFromParam(String key,ProceedingJoinPoint jp){
        if(!key.contains("$")){
            return key;
        }
        String regexp = "\\$\\{[^\\}]+\\}";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(key);
        List<String> names = new ArrayList<String>();
        try{
            while(matcher.find()){
                names.add(matcher.group());
            }
            key = executeNames(key,names,jp);
        }catch (Exception e) {
            log.error("Regex Parse Error!", e);
        }
        return key;
    }
    */
    
    /**
     * 
     * @Title: executeNames
     * @Description: 对KEY中的参数进行替换
     * @param @param key
     * @param @param names
     * @param @param jp
     * @param @return
     * @param @throws OgnlException
     * @return String
     * @throws
     
    private String executeNames(String key, List<String> names,ProceedingJoinPoint jp) throws OgnlException {
        Method method = ((MethodSignature)jp.getSignature()).getMethod();
        //形参列表
        List<String> param = MethodParamNamesScaner.getParamNames(method);
        
		if (names == null || names.size() == 0) {
			return key;
		}
        Object[] params = jp.getArgs();
        Map<String,Object> map = new HashMap<String,Object>();
        for(int i=0;i<param.size();i++){
            map.put(param.get(i), params[i]);
        }
        for(String name:names){
            String temp = name.substring(2);
            temp = temp.substring(0,temp.length()-1);
            key = myReplace(key,name, (String)Ognl.getValue(temp, map));
        }
        return key;
    }
    */
    
    /**
     * 
     * @Title: myReplace
     * @Description: 不依赖Regex的替换，避免$符号、{}等在String.replaceAll方法中当做Regex处理时候的问题。
     * @param @param src
     * @param @param from
     * @param @param to
     * @param @return
     * @return String
     * @throws
     
	private String myReplace(String src, String from, String to) {
		int index = src.indexOf(from);
		if (index == -1) {
			return src;
		}

		return src.substring(0, index) + to + src.substring(index + from.length());
	}
    */
 
    /**
     * 
     * @Title: executeCondition
     * @Description: 判断是否需要进行缓存操作
     * @param @param condition parm
     * @param @return
     * @return boolean true:需要 false：不需要
     * @throws
     
	private boolean executeCondition(String condition, ProceedingJoinPoint jp) {
		if ("".equals(condition)) {
			return true;
		}
		Method method = ((MethodSignature) jp.getSignature()).getMethod();
		// 形参列表
		List<String> param = MethodParamNamesScaner.getParamNames(method);
		if (param == null || param.size() == 0) {
			return true;
		}
		Object[] params = jp.getArgs();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < param.size(); i++) {
			map.put(param.get(i), params[i]);
		}
		boolean returnVal = false;
		try {
			System.out.println(Ognl.getValue(condition, map));
			returnVal = (Boolean) Ognl.getValue(condition, map);
		} catch (OgnlException e) {
			e.printStackTrace();
		}
		return returnVal;
	}
    */
    
    /**
     * 更新缓存时，解析cache key
     * @param condition 条件表达式
     * @param value  key
     * @param jp
     * @return
     */
    private Set<String> parserKey(String[] condition, String[] value, ProceedingJoinPoint jp){
    	Set<String> cacheKey = new HashSet<String>();
    	if(value.length == 0) return cacheKey;//当key没有定义时，不做处理
    	if(condition.length == 0){//当没有条件时候，不做拼接，直接返回KEY
    		for(String v : value){
    			cacheKey.add(v);
    		}
    		return cacheKey;
    	}
    	Method method = ((MethodSignature)jp.getSignature()).getMethod();
        //形参列表
        List<String> param = MethodParamNamesScaner.getParamNames(method);
		if (param == null || param.size() == 0) {
			return null;
		}
        Object[] params = jp.getArgs();
        
        if(params.length == 1){
        	String codeParam = param.get(0);//形参变量
        	Object parameter = params[0];
        	if(parameter instanceof Map){
            	@SuppressWarnings("unchecked")
				Map<String, Object> p = (Map<String, Object>)parameter;
                for(int i = 0; i < condition.length; i++){
                	String[] single = condition[i].split(SPLIT);//
                	String suffix = ZERO_STRING;
                	for(String s : single){
	               	 	Object o = p.get(s);
	               	 	if(o == null) continue;
	               	 	suffix += s + SPLIT + o + SPLIT;
                	}
                	cacheKey.add(value[i] + suffix);//构造cache key
                }
            }else if (parameter instanceof String){
            	parameter = (String) parameter;
            }else if (parameter instanceof Integer){
            	parameter = (Integer) parameter;
            }else if (parameter instanceof Double){
            	parameter = (Double) parameter;
            }else if (parameter instanceof Boolean){
            	parameter = (Boolean) parameter;
            }else if (parameter instanceof String[]){
            	//doing...
            }else if (parameter instanceof Integer[]){
            	//doing...
            }else if (parameter instanceof List){
            	//doing...
            }else{//object
            	try {
					for (int i = 0; i < condition.length; i++) {
						String[] single = condition[i].split(SPLIT);//
						String suffix = ZERO_STRING;
						for (String s : single) {
							Object v = Ognl.getValue(s, parameter);
							suffix += s + SPLIT + v + SPLIT;
						}
						cacheKey.add(value[i] + suffix);
					}
				} catch (OgnlException e) {
					e.printStackTrace();
				}
            }
        	if(condition[0].equals(codeParam)){
        		cacheKey.add(value[0] + condition[0] + SPLIT + parameter);
        	}
        }else{
        	 Map<String,Object> map = new HashMap<String,Object>();
             for(int i=0;i<param.size();i++){
                 map.put(param.get(i), params[i]);
             }
             for(int i = 0; i < condition.length; i++){
            	 String[] single = condition[i].split(SPLIT);//
            	 String suffix = ZERO_STRING;
            	 for(String s : single){
            		Object o = map.get(s);
               	 	if(o == null) continue;
               	 	suffix += s + SPLIT + o + SPLIT;
            	 }
            	 cacheKey.add(value[i] + suffix);//构造cache key
             }
        }
    	return cacheKey;
    }
    
    
	private String createKey(String condition, ProceedingJoinPoint jp){
        if(condition == null){
            return ZERO_STRING;
        }
        Method method = ((MethodSignature)jp.getSignature()).getMethod();
        //形参列表
        List<String> param = MethodParamNamesScaner.getParamNames(method);
        
		if (param == null || param.size() == 0) {
			return ZERO_STRING;
		}
        Object[] params = jp.getArgs();
        if(params.length == 1){
        	String codeParam = param.get(0);//形参变量
        	Object parameter = params[0];
        	if(parameter instanceof PageQuery){
        		parameter = (PageQuery)parameter;
        		return parameter.toString();
        	}else if(parameter instanceof Map){
            	@SuppressWarnings("unchecked")
				Map<String, Object> p = (Map<String, Object>)parameter;
            	String[] keys = condition.split(SPLIT);
                if(keys == null || keys.length == 0) return ZERO_STRING;
                StringBuilder builder = new StringBuilder();
                for(String key : keys){
               	 	Object o = p.get(key);
               	 	if(o == null) continue;
               	 	builder.append(key).append(SPLIT).append(o).append(SPLIT);
                }
                return builder.toString();
            }else if (parameter instanceof String){
            	parameter = (String) parameter;
            }else if (parameter instanceof Integer){
            	parameter = (Integer) parameter;
            }else if (parameter instanceof Double){
            	parameter = (Double) parameter;
            }else if (parameter instanceof Boolean){
            	parameter = (Boolean) parameter;
            }else if (parameter instanceof String[]){
            	//doing...
            	return ZERO_STRING;
            }else if (parameter instanceof Integer[]){
            	//doing...
            	return ZERO_STRING;
            }else if (parameter instanceof List){
            	//doing...
            	return ZERO_STRING;
            }else{
            	//doing...
            	return ZERO_STRING;
            }
        	return (condition.equals(codeParam) ? (condition + SPLIT + parameter) : ZERO_STRING);
        }else{
        	 Map<String,Object> map = new HashMap<String,Object>();
             for(int i=0;i<param.size();i++){
                 map.put(param.get(i), params[i]);
             }
             String[] keys = condition.split(SPLIT);
             if(keys == null || keys.length == 0) return "";
             StringBuilder builder = new StringBuilder();
             for(String key : keys){
            	 Object o = map.get(key);
            	 if(o == null) continue;
            	 builder.append(key).append(SPLIT).append(o).append(SPLIT);
             }
             return builder.toString();
        }
    }
	
	private boolean casevar(String[] casevar, ProceedingJoinPoint jp){
		if(casevar.length == 0) return true;
		String var = casevar[0];//目前只处理一个表达式
		Method method = ((MethodSignature)jp.getSignature()).getMethod();
	        //形参列表
        List<String> param = MethodParamNamesScaner.getParamNames(method);
        
		if (param == null || param.size() == 0) {
			return true;
		}
        Object[] params = jp.getArgs();
        
        Map<String,Object> map = new HashMap<String,Object>();
        for(int i=0;i<param.size();i++){
            map.put(param.get(i), params[i]);
        }
        if(var.indexOf(EQUALS) != -1){//表达式为=
        	String[] key = var.split(EQUALS);
        	String paramName = key[0];//参数名
        	String paramValue = key[1];//表达式参数值
        	Object realParamValue = map.get(paramName);//实际参数值
        	if(paramValue.equals(String.valueOf(realParamValue))) return true;//若符合条件，则进行缓存
        }else if(var.indexOf(NOT_EQUALS) != -1){//表达式为!=
        	String[] key = var.split(NOT_EQUALS);
        	String paramName = key[0];//参数名
        	String paramValue = key[1];//表达式参数值
        	Object realParamValue = (String) map.get(paramName);//实际参数值
        	if(!paramValue.equals(String.valueOf(realParamValue))) return true;//若符合条件，则进行缓存
        }else{
        	return false;
        }
		return false;
	}
	
	private String trimEndWith(String src){
		if(src.endsWith(SPLIT)){
			return src.substring(0, src.length() - 1);
		}
		return src;
	}
}

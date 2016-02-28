package com.game.common.basics.aop;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoadFromMemcached {
    
    String value();//缓存的key
    
    int timeout() default 600;//默认过期时间，单位秒
    
    String condition() default "";//执行缓存查询的条件
    
    String[] casevar() default {};//查询符合条件的进行缓存表达式为 xxx=xx 或 xx!=xx 返回值为true
}

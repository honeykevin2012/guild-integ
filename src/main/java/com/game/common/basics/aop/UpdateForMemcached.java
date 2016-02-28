package com.game.common.basics.aop;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UpdateForMemcached {
    
    String[] value() default {};//可能有多个key需要更新
    
    String[] condition() default {};//执行缓存的条件

}


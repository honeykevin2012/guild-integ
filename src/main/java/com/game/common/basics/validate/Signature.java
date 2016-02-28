package com.game.common.basics.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SignatureValidator.class)
@Documented
public @interface Signature {
	
	String message() default "1001#请求验证失败.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	String signature();
	String deviceId();
	String deviceName();
	String gameId();
	String os();// (android、ios) 操作系统
	String osVersion();// 操作系统版本
	String gameVersion();// (游戏升级用)
	String sdkVersion();// （当前SDK的版本号）
	String screenResolution();// 屏幕分辨率
	String timestamp();// （后续安全验证）
	String data();//请求参数RSA加密密文
	String nuid();//校验用户登录后请求资源的用户标识
}
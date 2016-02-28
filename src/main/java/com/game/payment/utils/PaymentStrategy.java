package com.game.payment.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class PaymentStrategy {
	private static Logger logger = Logger.getLogger(PaymentStrategy.class.getName());
	private static final String PAYMENT_CONFIG = "payment";
	private static Map<String, String> map = new HashMap<String, String>();
	public static PaymentConfig config(String definedPaymentType){
		 ResourceBundle rb = ResourceBundle.getBundle(PAYMENT_CONFIG, Locale.getDefault());
		 PaymentConfig config = new PaymentConfig();
		 String value = null;
		 try{
			 value = rb.getString(definedPaymentType);
		 }catch(MissingResourceException e){
			 logger.info("*******Order card type: " + definedPaymentType + " is not exists******");
			 config.setChannelId("-1");
        	 config.setChannelName("无");
        	 config.setPaymentId("-1");
		 }
         if(value != null){
        	 String[] values = value.split(",");
        	 config.setChannelId(values[0]);
        	 config.setChannelName(values[2]);
        	 config.setPaymentId(values[1]);
         }
         return config;
	}
	
	public static String paymentValue(String paymentId){
		if(map != null && !map.isEmpty()) {
			String name = map.get(paymentId);
			if(name == null || "".equals(name)) return "无";
			return name;
		}
		 ResourceBundle rb = ResourceBundle.getBundle(PAYMENT_CONFIG, Locale.getDefault());
		 Enumeration<String> en = rb.getKeys();
		 while(en.hasMoreElements()){
			 String key = en.nextElement();
			 PaymentConfig config = config(key);
			 map.put(config.getPaymentId(), config.getChannelName());
		 }
		 String name = map.get(paymentId);
		 if(name == null || "".equals(name)) return "无";
		 return name;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PaymentConfig config = PaymentStrategy.config("0");
		System.out.println(config);
		System.out.println(paymentValue(config.getPaymentId()));
	}

}

package com.game.common;

/**
 * @author yangchengwei
 *订单类型
 */
public enum OrderTypeEnum {
	
	PHONEBILL("PHB",1), WATER("WAB",2),
	ELECTRIC("ELB",3),GAS("GAB",4);
    
    private final int value;
    private final String prefix;

    OrderTypeEnum(String prefix,int value) {
        this.value = value;
        this.prefix = prefix;
    }
    
    public int getValue() {
        return value;
    }
    public String getPrefix() {
        return prefix;
    }
}

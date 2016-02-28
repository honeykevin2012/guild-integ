package com.game.log.util;

public enum PageEventEnum {
	
	SHOP("商城"),GUILD("公会"),
	BBS("论坛"),NEWS("新闻"),UCENTER("个人中心"),SIGN("签到"),
	PRDOUCTINFO("商品详情"),CART("购物车"),CREATE_ORDER("结算"),
	PAY_ORDER("支付"),HOT_GAME("热推游戏"),GUILD_SHOP("公会商城");
	
	
	private final String name;

	PageEventEnum(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}
	
}

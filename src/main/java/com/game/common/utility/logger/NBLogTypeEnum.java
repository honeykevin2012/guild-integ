package com.game.common.utility.logger;


/**
 * @author yangchengwei
 *日志类型
 */
public enum NBLogTypeEnum {
	
	UserReg(1),UserLogin(2),UserInfoBind(3),UserVisit(4),
	GuildsCreate(5),GuildsDissolve(6),GuildsContribute(7),
	GuildsGameSign(8),GuildsReg(9),GuildsRegConfirm(10),
	GuildsRegReject(11),GuildsDeleteUser(12),GuildsGift(13),
	GuildsSetAdmin(14),GuildsCancelAdmin(15),MallAddShopCar(16),
	MallOrderReg(17),MallPlatformOrderSubmit(18),MallGuildsOrderSubmit(19),
	MallOrderGoods(20),PayMsg(21),GuildsNameChange(22);
	
	private int value; 
	private NBLogTypeEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static NBLogTypeEnum getProvider(int cpValue){
		NBLogTypeEnum cps[] = NBLogTypeEnum.values();
		for(NBLogTypeEnum cp : cps){
			if(cp.value == cpValue) return cp;
		}
		return null;
	}
}

//package com.game.log;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.dataeye.sdk.client.DCAgent;
//import com.dataeye.sdk.client.domain.DCTask;
//import com.dataeye.sdk.client.domain.TaskType;
//import com.dataeye.sdk.proto.DCServerSync.DCMessage.DCUserInfo;
//import com.dataeye.sdk.proto.DCServerSync.DCMessage.NetType;
//import com.dataeye.sdk.proto.DCServerSync.DCMessage.PlatformType;
//import com.game.log.util.PageEventEnum;
//
///**
// * @author yangchengwei
// *
// */
//public class DataEyeAgent {
//	// 服务器名，自行填写，方便后面查问题
//	private static final String dataServName = "NBGame";
//	// SDK文件存储根目录，保证有5G以上空间
//	private static final String localStoreDir = "/data/dataeye/log";
//	// SDK运行日志保存的最大天数
//	private static int storeFileMaxDays = 7;
//	// SDK保存的最大文件数目
//	private static int storeFileMaxNumbers = 1000;
//	// appid
//	private static final String appId = "DA69F1CFCB984D5A01F7E8674F0F859A";
//	// 渠道号
//	private static final String channel = "NB100001";
//
//	static {
//		DCAgent.setBaseConf(dataServName, localStoreDir, storeFileMaxDays, storeFileMaxNumbers);
//	}
//	private static DCAgent dcAgent = DCAgent.getInstance(appId);;
//
//	/**
//	 * 用户注册
//	 * 
//	 * @param accountId
//	 *            操作人账号ID          
//	 * @param mac
//	 *            设置MAC地址
//	 * @param imei
//	 *            设置Imei号
//	 * @param platform
//	 *            设置平台类型
//	 * @param brand
//	 *            设置机型
//	 * @param ip
//	 *            设置玩家IP
//	 * @param net
//	 *            设置网络类型
//	 * @param osVersion
//	 *            设置操作系统版本
//	 * @param resolution
//	 *            设置分辨率
//	 */
//	public static void register(String... str){}
//	public static void register(String accountId,String platform, String mac, String imei, String brand, String ip, String net, String osVersion, String resolution) {
//		dcAgent.reg(DCUserInfo.newBuilder()
//				.setAccountId(accountId == null ? "0000" : accountId) // 操作人账号ID，必填
//				.setMac(mac) // 设置MAC地址
//				.setImei(imei) // 设置Imei号
//				.setPlatform(platformConvert(platform)) // 设置平台类型，android
//				.setBrand(brand == null ? "":brand) // 设置机型
//				.setChannel(channel) // 设置渠道
//				.setIp(ip == null ? "" : ip) // 设置玩家IP
//				.setNetType(netConvert(net)) // 设置网络类型
//				.setOsVersion(osVersion) // 设置操作系统版本
//				.setResolution(resolution)// 设置分辨率
//				.build(),
//				(int) (System.currentTimeMillis() / 1000)
//		);
//		// 添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//				.setAccountId(accountId == null ? "0000" : accountId)
//				.setChannel(channel) // 设置渠道
//				.setPlatform(platformConvert(platform)) // 设置平台类型，android
//				.build()
//				, null, "账号", "注册");
//	}
//
//	/**
//	 * 用户登录
//	 * 
//	 * @param accountId
//	 *            操作人账号ID
//	 * @param mac
//	 *            设置MAC地址
//	 * @param imei
//	 *            设置Imei号
//	 * @param platform
//	 *            设置平台类型
//	 * @param brand
//	 *            设置机型
//	 * @param ip
//	 *            设置玩家IP
//	 * @param net
//	 *            设置网络类型
//	 * @param osVersion
//	 *            设置操作系统版本
//	 * @param resolution
//	 *            设置分辨率
//	 */
//	public static void login(String accountId, String mac, String imei, String platform, String brand, String ip, String net, String osVersion, String resolution) {
//		Map<String, String> labelMap = new HashMap<String, String>();
//		dcAgent.onEvent(DCUserInfo.newBuilder().setAccountId(accountId == null ? "0000" : accountId) // 操作人账号ID，必填
//				.setMac(mac) // 设置MAC地址
//				.setImei(imei) // 设置Imei号
//				.setPlatform(platformConvert(platform)) // 设置平台类型，android
//				.setBrand(brand == null ? "":brand) // 设置机型
//				.setChannel(channel) // 设置渠道
//				.setIp(ip == null ? "" : ip) // 设置玩家IP
//				.setNetType(netConvert(net)) // 设置网络类型
//				.setOsVersion(osVersion) // 设置操作系统版本
//				.setResolution(resolution)// 设置分辨率
//				.build(), "账号登录",// 事件ID
//				labelMap, // 事件属性map
//				155,// 事件耗时 秒
//				null// 无角色信息，参数填null
//		);
//		dcAgent.addTag(DCUserInfo.newBuilder()
//				.setAccountId(accountId == null ? "0000" : accountId)
//				.setChannel(channel) // 设置渠道
//				.setPlatform(platformConvert(platform)) // 设置平台类型，android
//				.build(), null, "账号", "登陆");
//	}
//
//	/**
//	 * 页面事件
//	 * 
//	 * @param accountId 操作人账号
//	 * @param platform 平台
//	 * @param mac mac
//	 * @param imei 设备编号
//	 * @param platform 平台类型
//	 * @param brand 设备型号
//	 * @param ip ip
//	 * @param net 网络类型
//	 * @param osVersion 系统版本
//	 * @param resolution 分辨率
//	 * @param type 页面事件类型
//	 */
//	public static void pageEvent(String accountId,String platform, String mac, String imei, String brand, String ip, String net, String osVersion, String resolution, PageEventEnum type) {
//		dcAgent.taskComplete(DCUserInfo.newBuilder()
//			.setAccountId(accountId == null ? "0000" : accountId) // 操作人账号ID，必填
//			.setMac(mac) // 设置MAC地址
//			.setImei(imei) // 设置Imei号
//			.setPlatform(platformConvert(platform)) // 设置平台类型，android
//			.setBrand(brand == null ? "" : brand) // 设置机型
//			.setChannel(channel) // 设置渠道
//			.setIp(ip == null ? "" : ip) // 设置玩家IP
//			.setNetType(netConvert(net)) // 设置网络类型
//			.setOsVersion(osVersion) // 设置操作系统版本
//			.setResolution(resolution)// 设置分辨率
//			.build(), DCTask.newBuilder().taskId("页面访问") // 任务ID，必填
//			.taskType(TaskType.GuideLine) // 任务类型，必填
//			.duration(1000) // 任务耗时 1000秒，必填
//			.build(), null);
//
//		// 添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//				.setAccountId(accountId == null ? "0000" : accountId)
//				.setChannel(channel) // 设置渠道
//				.setPlatform(platformConvert(platform)) // 设置平台类型，android
//				.build(), null, "页面访问", type.getName());
//	}
//	
//	/**
//	 * 商品详情
//	 * 
//	 * @param accountId 操作人账号
//	 * @param platform 平台
//	 * @param mac mac
//	 * @param imei 设备编号
//	 * @param platform 平台类型
//	 * @param brand 设备型号
//	 * @param ip ip
//	 * @param net 网络类型
//	 * @param osVersion 系统版本
//	 * @param resolution 分辨率
//	 * @param type 页面事件类型
//	 */
//	public static void productInfo(String accountId,String platform, String mac, String imei, String brand, String ip, String net, String osVersion, String resolution, PageEventEnum type,String productId) {
//		dcAgent.taskComplete(DCUserInfo.newBuilder()
//			.setAccountId(accountId == null ? "0000" : accountId) // 操作人账号ID，必填
//			.setMac(mac) // 设置MAC地址
//			.setImei(imei) // 设置Imei号
//			.setPlatform(platformConvert(platform)) // 设置平台类型，android
//			.setBrand(brand == null ? "" : brand) // 设置机型
//			.setChannel(channel) // 设置渠道
//			.setIp(ip == null ? "" : ip) // 设置玩家IP
//			.setNetType(netConvert(net)) // 设置网络类型
//			.setOsVersion(osVersion) // 设置操作系统版本
//			.setResolution(resolution)// 设置分辨率
//			.build(), DCTask.newBuilder().taskId("页面访问") // 任务ID，必填
//			.taskType(TaskType.GuideLine) // 任务类型，必填
//			.duration(1000) // 任务耗时 1000秒，必填
//			.build(), null);
//
//		// 添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//				.setAccountId(accountId == null ? "0000" : accountId)
//				.setChannel(channel) // 设置渠道
//				.setPlatform(platformConvert(platform)) // 设置平台类型，android			
//				.build(), null, "页面访问", type.getName()+"_"+productId);
//	}
//
//	/**
//	 * 添加购物车
//	 * 
//	 * @param userName 操作人名称
//	 * @param accountId 操作人id
//	 * @param platform  平台
//	 * @param productName 商品名称
//	 * @param count 商品数量
//	 */
//	public static void addCart(String userName,String accountId,String platform,String productName,String count) {
//		//添加购物车
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put(productName,count); //<物品名，数量>
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setChannel(channel) // 设置渠道
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.build(),
//		"添加购物车",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setChannel(channel) // 设置渠道
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android	
//		.build(),
//		null,
//		"个人购买" ,//一级标签
//		"添加购物车" //二级标签
//		);
//	}
//	
//	
//	/**创建订单
//	 * @param userName 用户名称
//	 * @param accountId 操作人id
//	 * @param platform 平台
//	 * @param products 商品
//	 * @param orderId 订单号
//	 * @param totalAmount 订单总金额
//	 */
//	public static void createOrder(String userName,String accountId,String platform,String[] products,String orderId,String totalAmount) {
//		//订单提交
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",accountId == null ? "0000" : accountId);//操作人名称 
//		for (String p : products) {
//			String[] dd=p.split("-");
//			labelMap.put(dd[0],dd[1]); //<物品名，数量> 
//		}
//		labelMap.put("orderId",orderId); 
//		labelMap.put("NBCount",totalAmount);//订单总金额 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"订单提交",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"个人购买" ,//一级标签
//		"订单提交" //二级标签
//		);
//	}
//	
//	/**
//	 * 订单支付
//	 * @param userName 用户名称
//	 * @param accountId 操作人id
//	 * @param platform 平台
//	 * @param products 订单商品
//	 * @param gameDucts 游戏扣款数组
//	 * @param orderId 订单号
//	 * @param totalAmount 订单总金额
//	 * @param reciverName 收货人
//	 * @param reciverPhone 收货人电话
//	 * @param reciverAdd 收货地址
//	 */
//	public static void payOrder(String userName,String accountId,String platform,String[] products,String[] gameDucts,String orderId,String totalAmount,String reciverName,String reciverPhone,String reciverAdd) {
//		//订单结算
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",accountId == null ? "0000" : accountId);//操作人名称 
//		for (String p : products) {
//			String[] dd=p.split("-");
//			labelMap.put(dd[0],dd[1]); //<物品名，数量> 
//		}
//		labelMap.put("orderId",orderId); 
//		labelMap.put("NBCount",totalAmount);//订单总金额 
//		for (String g : gameDucts) {
//			String[] dd=g.split("-");
//			labelMap.put(dd[0],dd[1]); ////角色1扣币 
//		}
//		labelMap.put("reciverName",reciverName);//收货人姓名 
//		labelMap.put("reciverPhone",reciverPhone);//收货人手机 
//		labelMap.put("reciverAdd",reciverAdd);//收货人地址 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.build(),
//		"订单结算",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.build(),
//		null,
//		"个人购买" ,//一级标签
//		"订单结算" //二级标签
//		);
//	}
//	
//	
//
//	/**创建公会
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 */
//	public static void guildCreate(String userName,String accountId,String platform,String guildId,String guildName) {
//		//公会创建
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		"公会创建",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"创建" //二级标签
//		);
//	}
//
//	
//	/**
//	 * 公会设置管理员
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param targetUserId 目标用户id
//	 * @param targetUsername 目标用户名称
//	 */
//	public static void guildSetAdmin(String userName,String accountId,String platform,String guildId,String guildName,String targetUserId,String targetUsername) {
//		//公会设置管理员
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		labelMap.put("adminId",targetUserId);//管理员信息
//		labelMap.put("adminName",targetUsername);
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //设置操作人员账号ID，必填
//		.setChannel(channel) // 设置渠道
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.build(),
//		"公会设置管理员",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"设置管理员" //二级标签
//		);
//	}
//	
//	/**
//	 * 公会删除管理员
//	 * 
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param targetUserId 目标用户id
//	 * @param targetUsername 目标用户名称
//	 */
//	public static void guildCancelAdmin(String userName,String accountId,String platform,String guildId,String guildName,String targetUserId,String targetUsername) {
//		//公会删除管理员
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		labelMap.put("adminId",targetUserId);//管理员信息
//		labelMap.put("adminName",targetUsername);
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //设置操作人员账号ID，必填
//		.setChannel(channel) // 设置渠道
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.build(),
//		"公会删除管理员",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setChannel(channel) // 设置渠道
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"删除管理员" //二级标签
//		);
//	}
//	
//	/**
//	 * 公会解散
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 */
//	public static void guildsDissolve(String userName,String accountId,String platform,String guildId,String guildName) {
//		//公会解散
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"公会解散",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"解散" //二级标签
//		);
//	}
//	
//	
//	/**
//	 * 公会捐赠
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param gameDucts 游戏扣款数组
//	 * @param nbAmount 捐赠前N币数量
//	 * @param contributeAmount 捐赠N币数量
//	 */
//	public static void guildsContribute(String userName,String accountId,String platform,String guildId,String guildName,String[] gameDucts,String nbAmount,String contributeAmount) {
//		//公会捐赠
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		labelMap.put("捐赠前N币数量",nbAmount); 
//		labelMap.put("捐赠N币数量",contributeAmount); 
//		for (String g : gameDucts) {
//			String[] dd=g.split("-");
//			labelMap.put(dd[0],dd[1]); ////角色1扣币 
//		}
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"公会捐赠",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setChannel(channel) // 设置渠道
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"捐赠" //二级标签
//		);
//	}
//	
//
//	
//	/**
//	 * 公会签到
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param societyExp 签到经验值
//	 */
//	public static void guildsGameSign(String userName,String accountId,String platform,String guildId,String guildName,String societyExp) {
//		//公会签到
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		labelMap.put("societyExp",societyExp); 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"公会签到",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"签到" //二级标签
//		);
//	}
//	
//	
//	/**
//	 * 申请加入公会
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param membercount 成员数量
//	 */
//	public static void guildsReg(String userName,String accountId,String platform,String guildId,String guildName,String membercount) {
//		//申请加入公会
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//申请人名称
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		labelMap.put("societyPeopleCount",membercount);//当前公会人数
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"申请加入公会",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setChannel(channel) // 设置渠道
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"申请加入" //二级标签
//		);
//	}
//	
//	/**
//	 * 同意加入公会
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param targetUserId 目标用户id
//	 * @param targetUserName 目标用户名称
//	 * @param membercount 成员数量
//	 */
//	public static void guildsRegConfirm(String userName,String accountId,String platform,String guildId,String guildName,String targetUserId,String targetUserName,String membercount) {
//		//同意加入公会
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		labelMap.put("applicantId",targetUserId);
//		labelMap.put("applicantName",targetUserName); 
//		labelMap.put("societyPeopleCount",membercount);//当前公会人数
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"同意加入公会",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"同意加入" //二级标签
//		);
//	}
//	
//	
//	/**
//	 * 拒绝加入公会
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param targetUserId 目标用户id
//	 * @param targetUserName 目标用户名称
//	 * @param membercount 成员数量
//	 */
//	public static void guildsRegReject(String userName,String accountId,String platform,String guildId,String guildName,String targetUserId,String targetUserName,String membercount) {
//		//拒绝加入公会
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		labelMap.put("applicantId",targetUserId);
//		labelMap.put("applicantName",targetUserName); 
//		labelMap.put("societyPeopleCount",membercount);//当前公会人数
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"拒绝加入公会",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"拒绝加入" //二级标签
//		);
//	}
//	
//	/**
//	 * 公会踢人
//	 * @param userName 操作人名称
//	 * @param accountId 操作id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param targetUserId 目标用户id
//	 * @param targetUserName 目标用户名称
//	 * @param membercount 成员数量
//	 */
//	public static void guildsDeleteUser(String userName,String accountId,String platform,String guildId,String guildName,String targetUserId,String targetUserName,String membercount) {
//		//公会踢人
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称  
//		labelMap.put("societyId",guildId);
//		labelMap.put("societyName",guildName); 
//		labelMap.put("rejecterId",targetUserId);
//		labelMap.put("rejecterName",targetUserName); 
//		labelMap.put("societyPeopleCount",membercount);//当前公会人数
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"公会踢人",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setChannel(channel) // 设置渠道
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android				
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"踢人" //二级标签
//		);
//	}
//	
//	/**
//	 * 公会改名
//	 * @param userName 操作人名称
//	 * @param accountId 操作人id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param guildNewName 公会新名称
//	 */
//	public static void guildsNameChange(String userName,String accountId,String platform,String guildId,String guildName,String guildNewName) {
//		//公会改名
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId); 
//		labelMap.put("societyOldName",guildName); 
//		labelMap.put("societyNewName",guildNewName); 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		"公会改名",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"改名" //二级标签
//		);
//	}
//	
//	/**
//	 * 公会添加购物车
//	 * @param userName 操作人名称
//	 * @param accountId 操作人id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param productName 商品名称
//	 * @param count 商品数量
//	 */
//	public static void guildMallAddShopCar(String userName,String accountId,String platform,String guildId,String guildName,String productName,String count) {
//		//添加购物车
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId); 
//		labelMap.put("societyName",guildName); 
//		labelMap.put(productName,count); //<物品名，数量>
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		"添加购物车",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"添加购物车" //二级标签
//		);
//	}
//	
//	
//	/**
//	 * 公会下订单
//	 * @param userName 操作人名称
//	 * @param accountId 操作人id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param products 商品数组
//	 * @param orderId 订单号
//	 * @param totalAmount 订单总金额
//	 */
//	public static void guildsCreateOrder(String userName,String accountId,String platform,String guildId,String guildName,String[] products,String orderId,String totalAmount) {
//		//公会下订单
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId); 
//		labelMap.put("societyName",guildName); 
//		for (String p : products) {
//			String[] dd=p.split("-");
//			labelMap.put(dd[0],dd[1]); //<物品名，数量> 
//		}
//		labelMap.put("orderId",orderId); 
//		labelMap.put("NBCount",totalAmount);//订单总金额 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		"公会下订单",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"下订单" //二级标签
//		);
//	}
//	
//	
//	/**
//	 * 公会结算
//	 * @param userName 操作人名称
//	 * @param accountId 操作人id
//	 * @param platform 平台
//	 * @param guildId 公会id
//	 * @param guildName 公会名称
//	 * @param products 商品数组
//	 * @param gameDucts 游戏扣款数组
//	 * @param orderId 订单号
//	 * @param totalAmount 订单总金额
//	 * @param reciverName 收货人名称
//	 * @param reciverPhone 收货人电话
//	 * @param reciverAdd 收货地址
//	 */
//	public static void guildsPayOrder(String userName,String accountId,String platform,String guildId,String guildName,String[] products,String orderId,String totalAmount,String reciverName,String reciverPhone,String reciverAdd) {
//		//公会结算
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId); 
//		labelMap.put("societyName",guildName); 
//		for (String p : products) {
//			String[] dd=p.split("-");
//			labelMap.put(dd[0],dd[1]); //<物品名，数量> 
//		}
//		labelMap.put("orderId",orderId); 
//		labelMap.put("NBCount",totalAmount);//订单总金额 
//		labelMap.put("reciverName",reciverName);//收货人姓名 
//		labelMap.put("reciverPhone",reciverPhone);//收货人手机 
//		labelMap.put("reciverAdd",reciverAdd);//收货人地址 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		"公会结算",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"结算" //二级标签
//		);
//	}
//	
//	/**
//	 * 公会赠送
//	 * @param userName 操作人名称
//	 * @param accountId 操作人账号ID
//	 * @param platform 平台
//	 * @param guildId 公会ID
//	 * @param guildName 公会名称
//	 * @param products 商品数组
//	 * @param targetUserId 目标用id
//	 * @param targetUserName 目标用户名称
//	 */
//	public static void guildsGift(String userName,String accountId,String platform,String guildId,String guildName,String[] products,String targetUserId,String targetUserName) {
//		//公会赠送
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		labelMap.put("societyId",guildId); 
//		labelMap.put("societyName",guildName); 
//		for (String p : products) {
//			String[] dd=p.split("-");
//			labelMap.put(dd[0],dd[1]); //<物品名，数量> 
//		}
//		labelMap.put("sendedId",targetUserId);//被赠予人账号ID
//		labelMap.put("sendedName",targetUserName);// 被赠予人名称
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		"公会赠送",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //申请人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"公会" ,//一级标签
//		"赠送" //二级标签
//		);
//	}
//	
//	/**个人签到
//	 * 
//	 * @param userName 用户名称
//	 * @param accountId 用户id
//	 * @param platform 平台
//	 */
//	public static void userSign(String userName,String accountId,String platform) {
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName);//操作人名称 
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		"账号个人签到",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//添加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道
//		.build(),
//		null,
//		"账号" ,//一级标签
//		"个人签到" //二级标签
//		);
//	}
//	
//	/**账号信息绑定
//	 * 
//	 * @param userName 用户名称
//	 * @param accountId 用户id
//	 * @param platform 平台
//	 * @param email 邮箱
//	 * @param mobile 电话
//	 */
//	public static void bindAccountInfo(String userName,String accountId,String platform,String email,String mobile) {
//		Map<String, String> labelMap = new HashMap<String, String>();
//		labelMap.put("accountName",userName==null?"":userName);//操作人名称 
//		labelMap.put("mail",email==null?"":email);
//		labelMap.put("phone",mobile==null?"":email);
//		dcAgent.onEvent(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		"账号信息绑定",//事件ID
//		labelMap, //事件属性map
//		155 ,//事件耗时 秒
//		null//无角色信息，参数填null
//		);
//		//dcAgent加标签
//		dcAgent.addTag(DCUserInfo.newBuilder()
//		.setAccountId(accountId == null ? "0000" : accountId) //操作人账号ID，必填
//		.setPlatform(platformConvert(platform)) // 设置平台类型，android
//		.setChannel(channel) // 设置渠道		
//		.build(),
//		null,
//		"账号" ,//一级标签
//		"信息绑定" //二级标签
//		);
//	}
//	
//	/**设备激活
//	 * 
//	 * @param mac mac地址
//	 * @param deviceId 设备id
//	 * @param platform 平台
//	 * @param deviceName 设备名称
//	 * @param ip IP地址
//	 * @param net 网络类型
//	 * @param osVersion 系统版本
//	 * @param resolution 分辨率
//	 */
//	public static void accountActive(String mac,String deviceId,String platform,String deviceName,String ip,String net,String osVersion,String resolution) {
//		dcAgent.act(DCUserInfo.newBuilder()
//				.setMac(mac) //设置MAC地址
//				.setImei(deviceId)  //设置Imei号
//				.setPlatform(platformConvert(platform)) //设置平台类型，android
//				.setBrand(deviceName) //设置机型
//				.setChannel(channel) //设置渠道
//				.setIp(ip == null ? "" : ip) //设置玩家IP
//				.setNetType(netConvert(net)) //设置网络类型
//				.setOsVersion(deviceName) //设置操作系统版本
//				.setResolution(resolution)//设置分辨率
//				.build(),
//				(int) (System.currentTimeMillis() / 1000));
//	}
//	/**
//	 * 客户端类型转换
//	 * 
//	 * @param sourcePlatform
//	 * @return
//	 */
//	private static PlatformType platformConvert(String sourcePlatform) {
//		if (sourcePlatform == null)
//			return null;
//		if ("ANDROID".equals(sourcePlatform.toUpperCase()))
//			return PlatformType.ADR;
//		else if ("IOS".equals(sourcePlatform.toUpperCase()))
//			return PlatformType.IOS;
//		else
//			return PlatformType.WP;
//	}
//
//	/**
//	 * 网络类型转换
//	 * 
//	 * @param net
//	 * @return
//	 */
//	private static NetType netConvert(String net) {
//		if (net == null)
//			return null;
//		if ("2G".equals(net.toUpperCase()))
//			return NetType._2G;
//		else if ("3G".equals(net.toUpperCase()))
//			return NetType._3G;
//		else if ("4G".equals(net.toUpperCase()))
//			return NetType._4G;
//		else if ("WIFI".equals(net.toUpperCase()))
//			return NetType.WIFI;
//		else
//			return NetType.OTHER;
//	}
//}
